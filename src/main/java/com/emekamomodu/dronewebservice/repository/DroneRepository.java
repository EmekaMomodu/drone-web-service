package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.model.enums.EDroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:40 PM
 */
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Boolean existsBySerialNumberIgnoreCase(String serialNumber);

    @Transactional
    @Modifying
    @Query("update Drone drone set drone.availableWeight = :availableWeight, drone.state = :droneState where drone.droneId = :droneId")
    void updateDronesAvailableWeightAndState(@Param(value = "droneId") Long droneId, @Param(value = "availableWeight") Integer availableWeight,  @Param(value = "droneState") EDroneState droneState);

    @Transactional
    @Modifying
    @Query("update Drone drone set drone.state = :droneState where drone.droneId = :droneId")
    void updateDronesState(@Param(value = "droneId") Long droneId, @Param(value = "droneState") EDroneState droneState);

}
