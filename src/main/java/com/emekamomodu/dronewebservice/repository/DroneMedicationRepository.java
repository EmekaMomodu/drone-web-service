package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:44 PM
 */
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, Long> {
}
