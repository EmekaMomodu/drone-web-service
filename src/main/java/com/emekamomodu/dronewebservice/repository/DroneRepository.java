package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:40 PM
 */
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Boolean existsBySerialNumberIgnoreCase(String serialNumber);

}
