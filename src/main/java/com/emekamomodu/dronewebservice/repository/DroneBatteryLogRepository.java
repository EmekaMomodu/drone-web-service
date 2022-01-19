package com.emekamomodu.dronewebservice.repository;

import com.emekamomodu.dronewebservice.entity.DroneBatteryLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/19/22 10:42 PM
 */
public interface DroneBatteryLogRepository extends JpaRepository<DroneBatteryLog, Long> {
}
