package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:46 PM
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Boolean existsByNameIgnoreCase(String name);

    Boolean existsByCodeIgnoreCase(String code);

}
