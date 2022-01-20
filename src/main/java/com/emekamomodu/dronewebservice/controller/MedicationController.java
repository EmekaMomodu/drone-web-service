package com.emekamomodu.dronewebservice.controller;

import com.emekamomodu.dronewebservice.model.MedicationModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 9:16 AM
 */
@RestController
@RequestMapping(value = "/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response> registerMedication(@RequestPart("medication") MedicationModel medicationModel, @RequestPart("image") MultipartFile medicationImage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationService.registerMedication(medicationModel, medicationImage));
    }

}
