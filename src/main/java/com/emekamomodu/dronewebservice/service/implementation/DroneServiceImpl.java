package com.emekamomodu.dronewebservice.service.implementation;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.entity.DroneMedication;
import com.emekamomodu.dronewebservice.entity.Medication;
import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectNotFoundException;
import com.emekamomodu.dronewebservice.model.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:53 PM
 */
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
        droneModel.setBatteryCapacity(droneModel.getBatteryCapacity() == null ? 100 : droneModel.getBatteryCapacity());

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
        if(!(droneInitState == EDroneState.IDLE || droneInitState == EDroneState.LOADED)){
            throw new InvalidRequestObjectException("Current drone state = '" + droneInitState + "' is not loadable");
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
        for(LoadDroneMedicationModel medicationModel: loadDroneModel.getMedicationList()){
            
            Long medicationId = medicationModel.getMedicationId();
            Integer frequency = medicationModel.getFrequency();
            
            if(frequency <= 0){
                //update drones state back to initial
                droneRepository.updateDronesState(droneId, droneInitState);
                throw new InvalidRequestObjectException("Medication with specified ID = '" + medicationId + "' has and invalid specified frequency = '" + frequency + "'");
            }
            
            Optional<Medication> optionalMedication = medicationRepository.findById(medicationId);

            if(!optionalMedication.isPresent()){
                //update drones state back to initial
                droneRepository.updateDronesState(droneId, droneInitState);
                throw new ObjectNotFoundException("Medication with specified ID = '" + medicationId + "' not found");
            }

            Medication medication = optionalMedication.get();

            totalWeightOfMedicationsToLoad += frequency * medication.getWeight();

        }

        // if drone's available weight <= totalWeightOfMedicationsToLoad throw medications exceed available weight
        if(dronesAvailableWeight <= totalWeightOfMedicationsToLoad){
            //update drones state back to initial
            droneRepository.updateDronesState(droneId, droneInitState);
            throw new InvalidRequestObjectException("Total specified medications weight = '" + totalWeightOfMedicationsToLoad + "' exceeds dronesAvailableWeight = '" + dronesAvailableWeight + "'");
        }

        // loop through medication list again and save to DroneMedicationRepo
        for(LoadDroneMedicationModel medicationModel: loadDroneModel.getMedicationList()){
            DroneMedication droneMedication = new DroneMedication(new Drone(droneId), new Medication(medicationModel.getMedicationId()), medicationModel.getFrequency());
            droneMedicationRepository.save(droneMedication);
        }

        // update drone's available weight ( available weight - totalWeightOfMedicationsToLoad ) and state
        dronesAvailableWeight -= totalWeightOfMedicationsToLoad;
        droneRepository.updateDronesAvailableWeightAndState(droneId, dronesAvailableWeight, EDroneState.LOADED);

        logger.info("Drone loaded successfully");
        return new Response(true, "Drone loaded Successfully");

    }

    private void validateRegisterDroneRequest(DroneModel droneModel) throws InvalidRequestObjectException{

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

        Integer batterCapacity = droneModel.getBatteryCapacity();

        if (batterCapacity != null && (batterCapacity < 0 || batterCapacity > 100)) {
            throw new InvalidRequestObjectException("batterCapacity should be between 0-100");
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
