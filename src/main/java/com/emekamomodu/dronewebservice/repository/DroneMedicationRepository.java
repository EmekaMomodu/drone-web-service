package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.entity.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:44 PM
 */
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, Long> {
    List<DroneMedication> findAllByDrone(Drone drone);
}
