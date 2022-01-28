package com.emekamomodu.dronewebservice.service;

import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.LoadDroneModel;
import com.emekamomodu.dronewebservice.model.Response;

import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:52 PM
 */
public interface DroneService {

    /**
     * Register new drone
     *
     * @param droneModel DroneModel object.
     * @return Response object with required data
     */
    Response registerDrone(DroneModel droneModel) throws InvalidRequestObjectException, ObjectAlreadyExistsException;

    /**
     * Get all registered drones
     *
     * @return Response object with required data
     */
    Response getAllDrones();

    /**
     * load drone with medication
     *
     * @param loadDroneModel contains drone and medication info to be loaded.
     * @return Response object with required data
     */
    Response loadDrone(LoadDroneModel loadDroneModel);

    /**
     * Get all available drones for loading
     *
     * @return Response object with required data
     */
    Response getAvailableDronesForLoading();

    /**
     * Get drones battery level
     *
     * @return Response object with required data
     */
    Response getDronesBatteryLevel(Long droneId);

    /**
     * update drones battery level
     *
     * @param droneModel DroneModel object.
     * @return Response object with required data
     */
    Response updateDronesBatteryLevel(DroneModel droneModel);

}
