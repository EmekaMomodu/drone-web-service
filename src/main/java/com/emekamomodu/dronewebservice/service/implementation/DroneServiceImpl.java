package com.emekamomodu.dronewebservice.service.implementation;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.model.enums.EDroneModel;
import com.emekamomodu.dronewebservice.repository.DroneRepository;
import com.emekamomodu.dronewebservice.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Response registerDrone(DroneModel droneModel) throws InvalidRequestObjectException, ObjectAlreadyExistsException {

        logger.info("registerDrone initiated");
        logger.info("request::: " + droneModel);

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
