package com.emekamomodu.dronewebservice.service.implementation;

import com.emekamomodu.dronewebservice.entity.Medication;
import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.model.MedicationModel;
import com.emekamomodu.dronewebservice.model.Response;
import com.emekamomodu.dronewebservice.repository.MedicationRepository;
import com.emekamomodu.dronewebservice.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 11:21 PM
 */
@Service
public class MedicationServiceImpl implements MedicationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${medication.image.allowed-content-types}")
    private String[] allowedContentTypes;

    @Value("${medication.image.max-size}")
    private Integer imageMaxSize;

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public Response registerMedication(MedicationModel medicationModel, MultipartFile medicationImage) throws InvalidRequestObjectException, IOException {

        logger.info("registerMedication initiated");
        logger.info("request::: " + medicationModel + " | " + medicationImage);

        // validate arguments
        validateRegisterMedicationRequest(medicationModel, medicationImage);

        byte[] image = medicationImage == null ? null : medicationImage.getBytes();
        String imageContentType = medicationImage == null ? null : medicationImage.getContentType();

        Medication registeredMedication = medicationRepository.save(new Medication(medicationModel, image, imageContentType));

        Response response = new Response(true, "Medication Registered Successfully", new MedicationModel(registeredMedication));

        logger.info("response::: " + response);

        return response;

    }

    private void validateRegisterMedicationRequest(MedicationModel medicationModel, MultipartFile medicationImage) throws InvalidRequestObjectException {

        // Check request parameters and sub-fields are not null
        if (medicationModel == null || medicationModel.getName() == null || medicationModel.getWeight() == null
                || medicationModel.getCode() == null) {
            throw new InvalidRequestObjectException("One/More Required Request Object/Field is Null");
        }

        // Validate name has allowed characters
        if (!medicationModel.getName().matches("[a-zA-Z0-9-_]+")) {
            throw new InvalidRequestObjectException("Medication NAME should have only letters(a-z/A-Z), numbers(0-9), underscores(_), and hyphen(-)");
        }


        // validate code has allowed characters
        if (!medicationModel.getCode().matches("[A-Z0-9_]+")) {
            throw new InvalidRequestObjectException("Medication CODE should have only uppercase-letters(A-Z), numbers(0-9), and underscores(_)");
        }

        if (medicationImage != null) {

            // Check medicationImage is not empty
            if (medicationImage.isEmpty() || medicationImage.getSize() == 0) {
                throw new InvalidRequestObjectException("Image is empty, kindly upload one or remove image field");
            }

            // Check medicationImage is of allowed content type
            String imageContentType = medicationImage.getContentType() == null ? "" : medicationImage.getContentType();
            if (!Arrays.asList(allowedContentTypes).contains(imageContentType.toLowerCase())) {
                throw new InvalidRequestObjectException("Invalid Image format, kindly upload image in any of these formats : " + Arrays.toString(allowedContentTypes));
            }

            // Check medicationImage is of allowed size
            if (medicationImage.getSize() > imageMaxSize) {
                throw new InvalidRequestObjectException("Image size should not be more than '" + imageMaxSize + " bytes'");
            }

        }

    }

}
