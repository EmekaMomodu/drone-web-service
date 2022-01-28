package com.emekamomodu.dronewebservice.boostrap;

import com.emekamomodu.dronewebservice.model.DroneModel;
import com.emekamomodu.dronewebservice.model.MedicationModel;
import com.emekamomodu.dronewebservice.service.DroneService;
import com.emekamomodu.dronewebservice.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@SuppressWarnings("Duplicates")
@Component
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @Override
    public void run(String... args) throws Exception {

        logger.info("Loading data");

        // Register drones
        droneService.registerDrone(new DroneModel("123456789", 150, 100));
        droneService.registerDrone(new DroneModel("223456789", 150, 20));
        droneService.registerDrone(new DroneModel("323456789", 250, 80));
        droneService.registerDrone(new DroneModel("423456789", 250, 24));
        droneService.registerDrone(new DroneModel("523456789", 350, 95));
        droneService.registerDrone(new DroneModel("623456789", 350, 30));
        droneService.registerDrone(new DroneModel("723456789", 500, 50));
        droneService.registerDrone(new DroneModel("823456789", 500, 13));
        droneService.registerDrone(new DroneModel("923456789", 350, 35));
        droneService.registerDrone(new DroneModel("023456789", 250, 60));

        // Register medications
        medicationService.registerMedication(new MedicationModel("Paracetamol", 20, "PCM_01"), null);
        medicationService.registerMedication(new MedicationModel("Dalacin", 10, "DCC"), null);
        medicationService.registerMedication(new MedicationModel("FungBact-A", 30, "FBA"), null);
        medicationService.registerMedication(new MedicationModel("Steriod", 40, "STRD_09"), null);
        medicationService.registerMedication(new MedicationModel("Androbycin", 50, "ABCN"), null);
        medicationService.registerMedication(new MedicationModel("Vitamin-C", 60, "VTC"), null);
        medicationService.registerMedication(new MedicationModel("Pro-Cold", 70, "PRC"), null);
        medicationService.registerMedication(new MedicationModel("Andrew-Liver-Salt", 80, "ALS_34"), null);
        medicationService.registerMedication(new MedicationModel("Anti-Biotic", 100, "ABC_01"), null);
        medicationService.registerMedication(new MedicationModel("Lem-Sip", 90, "LSP_22"), null);

        logger.info("Data loaded successfully");

    }

}
