package com.emekamomodu.dronewebservice.controller;

import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/21/22 12:35 AM
 */
@RestController
@RequestMapping(value = "/drone")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping(value = "/register")
    public ResponseEntity<Response> registerDrone(@RequestBody DroneModel droneModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(droneService.registerDrone(droneModel));
    }

}
