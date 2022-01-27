package com.emekamomodu.dronewebservice.controller;

import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.LoadDroneModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllDrones() {
        return ResponseEntity.ok(droneService.getAllDrones());
    }

    @PostMapping("/load")
    public ResponseEntity<Response> loadDrone(@RequestBody LoadDroneModel loadDroneModel) {
        return ResponseEntity.ok(droneService.loadDrone(loadDroneModel));
    }

    @GetMapping("/get-available-drones-for-loading")
    public ResponseEntity<Response> getAvailableDronesForLoading() {
        return ResponseEntity.ok(droneService.getAvailableDronesForLoading());
    }

    @GetMapping("/get-battery-level")
    public ResponseEntity<Response> getDronesBatteryLevel(@RequestParam(value = "droneId") Long droneId) {
        return ResponseEntity.ok(droneService.getDronesBatteryLevel(droneId));
    }


}
