package com.emekamomodu.dronewebservice.service;

import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectNotFoundException;
import com.emekamomodu.dronewebservice.model.MedicationModel;
import com.emekamomodu.dronewebservice.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 11:13 PM
 */
public interface MedicationService {

    /**
     * Register new medication
     *
     * @param medicationModel MedicationModel object.
     * @param medicationImage Medication image file.
     * @return Response object with required data
     */
    Response registerMedication(MedicationModel medicationModel, MultipartFile medicationImage) throws InvalidRequestObjectException, IOException, ObjectAlreadyExistsException;

    /**
     * Get all registered medication
     *
     * @return Response object with required data
     */
    Response getAllMedication() throws ObjectNotFoundException;


}
