package com.emekamomodu.dronewebservice.service;

import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.Response;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:52 PM
 */
public interface DroneService {

    /**
     * Register new medication
     *
     * @param droneModel DroneModel object.
     * @return Response object with required data
     */
    Response registerDrone(DroneModel droneModel) throws InvalidRequestObjectException, ObjectAlreadyExistsException;


}
