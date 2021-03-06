package com.emekamomodu.dronewebservice.service.implementation;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.entity.DroneMedication;
import com.emekamomodu.dronewebservice.entity.Medication;
import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectNotFoundException;
import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.LoadDroneMedicationModel;
import com.emekamomodu.dronewebservice.model.LoadDroneModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.model.enums.EDroneModel;
import com.emekamomodu.dronewebservice.model.enums.EDroneState;
import com.emekamomodu.dronewebservice.repository.DroneMedicationRepository;
import com.emekamomodu.dronewebservice.repository.DroneRepository;
import com.emekamomodu.dronewebservice.repository.MedicationRepository;
import com.emekamomodu.dronewebservice.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:53 PM
 */
@SuppressWarnings("Duplicates")
@Service
public class DroneServiceImpl implements DroneService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private DroneMedicationRepository droneMedicationRepository;

    @Override
    public Response registerDrone(DroneModel droneModel) throws InvalidRequestObjectException, ObjectAlreadyExistsException {

        logger.info("registerDrone initiated");
        logger.info("request::: {}", droneModel);

        // validate arguments
        validateRegisterDroneRequest(droneModel);

        // Check that Serial number does not already exist
        if (droneRepository.existsBySerialNumberIgnoreCase(droneModel.getSerialNumber())) {
            throw new ObjectAlreadyExistsException("Drone with serialNumber '" + droneModel.getSerialNumber() + "' already exists");
        }

        // set battery capacity to 100 if not specified in request
        droneModel.setBatteryLevel(droneModel.getBatteryLevel() == null ? 100 : droneModel.getBatteryLevel());

        // set drone's model based on specified weight limit
        droneModel.setModel(getModelByWeightLimit(droneModel.getWeightLimit()));

        Drone registeredDrone = droneRepository.save(new Drone(droneModel));

        Response response = new Response(true, "Drone Registered Successfully", new DroneModel(registeredDrone));

        logger.info("response::: " + response);

        return response;
    }

    @Override
    public Response getAllDrones() {

        logger.info("Getting all Drones");

        List<DroneModel> droneModels = new ArrayList<>();

        for (Drone drone : droneRepository.findAll()) {
            DroneModel droneModel = new DroneModel(drone);
            droneModels.add(droneModel);
        }

        if (droneModels.size() > 0) {
            logger.info("Drones fetched successfully : {}", droneModels);
            return new Response(true, "All Drones fetched Successfully", droneModels);
        }

        logger.info("No Drone was found : {}", droneModels);
        throw new ObjectNotFoundException("No Drone was found, Register some first");

    }

    @Override
    public Response loadDrone(LoadDroneModel loadDroneModel) throws ObjectNotFoundException {

        logger.info("loadDrone initiated");
        logger.info("request::: {}", loadDroneModel);

        // get drone by id, throw not found exception if not found
        Long droneId = loadDroneModel.getDroneId();
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ObjectNotFoundException("Drone with specified ID '" + droneId + "' not found"));

        // check drone state must be IDLE or LOADED, else throw exception = current drone state not loadable
        EDroneState droneInitState = drone.getState();
        if (!(droneInitState == EDroneState.IDLE || droneInitState == EDroneState.LOADED)) {
            throw new InvalidRequestObjectException("Current drone state = '" + droneInitState + "' is not loadable");
        }

        // check drone battery level is not less than 25, else throw exception = current drone battery level is not loadable
        Integer droneBatteryLevel = drone.getBatteryLevel();
        if (droneBatteryLevel < 25) {
            throw new InvalidRequestObjectException("Current drone battery level = '" + droneBatteryLevel + "' is not loadable");
        }

        //update drone's state to Loading
        droneRepository.updateDronesState(droneId, EDroneState.LOADING);

        Integer dronesAvailableWeight = drone.getAvailableWeight();
        Integer totalWeightOfMedicationsToLoad = 0;

        // loop through medication list
        // check frequency > 0 else throw invalid frequency, update state to initial state
        // get medication by id, throw not found exception if not found, update state to initial state
        // if found then get weight,
        // multiply weight by frequency, add to totalWeightOfMedicationsToLoad
        for (LoadDroneMedicationModel medicationModel : loadDroneModel.getMedicationList()) {

            Long medicationId = medicationModel.getMedicationId();
            Integer frequency = medicationModel.getFrequency();

            if (frequency <= 0) {
                //update drones state back to initial
                droneRepository.updateDronesState(droneId, droneInitState);
                throw new InvalidRequestObjectException("Medication with specified ID = '" + medicationId + "' has and invalid specified frequency = '" + frequency + "'");
            }

            Optional<Medication> optionalMedication = medicationRepository.findById(medicationId);

            if (!optionalMedication.isPresent()) {
                //update drones state back to initial
                droneRepository.updateDronesState(droneId, droneInitState);
                throw new ObjectNotFoundException("Medication with specified ID = '" + medicationId + "' not found");
            }

            Medication medication = optionalMedication.get();

            totalWeightOfMedicationsToLoad += frequency * medication.getWeight();

        }

        // if drone's available weight less than totalWeightOfMedicationsToLoad throw medications exceed available weight
        if (dronesAvailableWeight < totalWeightOfMedicationsToLoad) {
            //update drones state back to initial
            droneRepository.updateDronesState(droneId, droneInitState);
            throw new InvalidRequestObjectException("Total specified medications weight = '" + totalWeightOfMedicationsToLoad + "' exceeds dronesAvailableWeight = '" + dronesAvailableWeight + "'");
        }

        // loop through medication list again and save to DroneMedicationRepo
        for (LoadDroneMedicationModel medicationModel : loadDroneModel.getMedicationList()) {

            Optional<DroneMedication> loadedMedicationOnDrone = droneMedicationRepository.findByDroneAndMedication(new Drone(droneId), new Medication(medicationModel.getMedicationId()));

            // if medication already exist for drone, update frequency by adding previous frequency to new frequency
            if (loadedMedicationOnDrone.isPresent()) {
                Integer updateFrequency = loadedMedicationOnDrone.get().getMedicationFrequency() + medicationModel.getFrequency();
                droneMedicationRepository.updateFrequencyOfMedicationForDrone(new Drone(droneId), new Medication(medicationModel.getMedicationId()), updateFrequency, LocalDateTime.now());
                continue;
            }

            // else load new medication on drone
            DroneMedication droneMedication = new DroneMedication(new Drone(droneId), new Medication(medicationModel.getMedicationId()), medicationModel.getFrequency());
            droneMedicationRepository.save(droneMedication);

        }

        // update drone's available weight ( available weight - totalWeightOfMedicationsToLoad ) and state
        dronesAvailableWeight -= totalWeightOfMedicationsToLoad;
        droneRepository.updateDronesAvailableWeightAndState(droneId, dronesAvailableWeight, EDroneState.LOADED);

        logger.info("Drone loaded successfully");
        return new Response(true, "Drone loaded Successfully");

    }

    @Override
    public Response getAvailableDronesForLoading() {

        // a drone is available for loading if:
        // 1. drones state is IDLE or Loaded
        // 2. drones battery level is not less than 25
        // 3. drones available weight is greater than zero

        logger.info("Getting all Drones available for loading");

        List<DroneModel> droneModels = new ArrayList<>();

        for (Drone drone : droneRepository.findAll()) {
            EDroneState droneState = drone.getState();
            Integer droneAvailableWeight = drone.getAvailableWeight();
            Integer droneBatteryLevel = drone.getBatteryLevel();
            if ((droneState == EDroneState.IDLE || droneState == EDroneState.LOADED) && (droneBatteryLevel >= 25) && (droneAvailableWeight > 0)) {
                DroneModel droneModel = new DroneModel(drone);
                droneModels.add(droneModel);
            }
        }

        if (droneModels.size() > 0) {
            logger.info("All Drones available for loading fetched Successfully : {}", droneModels);
            return new Response(true, "All Drones available for loading fetched Successfully", droneModels);
        }

        logger.info("No Drone available for loading was found : {}", droneModels);
        throw new ObjectNotFoundException("No Drone available for loading was found");

    }

    @Override
    public Response getDronesBatteryLevel(Long droneId) {

        logger.info("Getting battery level for drone with ID = '{}'", droneId);

        // get drone by id, throw not found exception if not found
        Integer droneBatteryLevel = droneRepository.findById(droneId)
                .orElseThrow(() -> new ObjectNotFoundException("Drone with specified ID '" + droneId + "' not found")).getBatteryLevel();

        Response response = new Response(true, "Drone's Battery Level fetched Successfully", droneBatteryLevel);

        logger.info("response::: " + response);

        return response;

    }

    @Override
    public Response updateDronesBatteryLevel(DroneModel droneModel) {

        validateUpdateDronesBatteryLevelRequest(droneModel);

        logger.info("Updating battery level for drone with ID = '{}'", droneModel.getDroneId());

        // get drone by id, throw not found exception if not found
        droneRepository.findById(droneModel.getDroneId()).orElseThrow(() -> new ObjectNotFoundException("Drone with specified ID '" + droneModel.getDroneId() + "' not found"));

        // update drone by id
        droneRepository.updateDronesBatteryLevel(droneModel.getDroneId(), droneModel.getBatteryLevel());

        Response response = new Response(true, "Drone's Battery Level updated Successfully");

        logger.info("response::: " + response);

        return response;

    }

    private void validateUpdateDronesBatteryLevelRequest(DroneModel droneModel) throws InvalidRequestObjectException {

        // Check compulsory request parameters and sub-fields are valid
        if (droneModel == null
                || droneModel.getDroneId() == null
                || droneModel.getBatteryLevel() == null) {
            throw new InvalidRequestObjectException("ID or Battery Level not specified");
        }

        Integer batteryLevel = droneModel.getBatteryLevel();

        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new InvalidRequestObjectException("batteryLevel should be between 0-100");
        }

    }

    private void validateRegisterDroneRequest(DroneModel droneModel) throws InvalidRequestObjectException {

        // Check compulsory request parameters and sub-fields are valid
        if (droneModel == null
                || droneModel.getSerialNumber() == null
                || droneModel.getWeightLimit() == null
                || droneModel.getSerialNumber().contains(" ")
                || droneModel.getSerialNumber().equals("")
                || droneModel.getSerialNumber().length() > 100
                || droneModel.getWeightLimit() <= 0
                || droneModel.getWeightLimit() > 500) {
            throw new InvalidRequestObjectException("Invalid serialNumber and/or weightLimit");
        }

        Integer batteryLevel = droneModel.getBatteryLevel();

        if (batteryLevel != null && (batteryLevel < 0 || batteryLevel > 100)) {
            throw new InvalidRequestObjectException("batteryLevel should be between 0-100");
        }

    }

    private EDroneModel getModelByWeightLimit(Integer weightLimit) {
        if (weightLimit <= EDroneModel.LIGHT_WEIGHT.getMaximumWeight()) {
            return EDroneModel.LIGHT_WEIGHT;
        }
        if (weightLimit <= EDroneModel.MIDDLE_WEIGHT.getMaximumWeight()) {
            return EDroneModel.MIDDLE_WEIGHT;
        }
        if (weightLimit <= EDroneModel.CRUISER_WEIGHT.getMaximumWeight()) {
            return EDroneModel.CRUISER_WEIGHT;
        }
        if (weightLimit <= EDroneModel.HEAVY_WEIGHT.getMaximumWeight()) {
            return EDroneModel.HEAVY_WEIGHT;
        }
        return null;
    }

}
