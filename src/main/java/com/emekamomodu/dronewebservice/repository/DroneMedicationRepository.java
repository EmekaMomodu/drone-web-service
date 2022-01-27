package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.entity.DroneMedication;
import com.emekamomodu.dronewebservice.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:44 PM
 */
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, Long> {

    List<DroneMedication> findAllByDrone(Drone drone);

    Optional<DroneMedication> findByDroneAndMedication(Drone drone, Medication medication);

    @Transactional
    @Modifying
    @Query("update DroneMedication droneMedication " +
            "set droneMedication.medicationFrequency = :medicationFrequency, " +
            "droneMedication.modifiedDate = :modifiedDate " +
            "where droneMedication.drone = :drone and droneMedication.medication = :medication")
    void updateFrequencyOfMedicationForDrone(@Param(value = "drone") Drone drone,
                                             @Param(value = "medication") Medication medication,
                                             @Param(value = "medicationFrequency") Integer medicationFrequency,
                                             @Param(value = "modifiedDate") LocalDateTime modifiedDate);

}
