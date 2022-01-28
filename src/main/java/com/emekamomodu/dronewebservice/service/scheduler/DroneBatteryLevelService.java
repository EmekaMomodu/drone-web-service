package com.emekamomodu.dronewebservice.service.scheduler;

import com.emekamomodu.dronewebservice.entity.Drone;
import com.emekamomodu.dronewebservice.entity.DroneBatteryLog;
import com.emekamomodu.dronewebservice.repository.DroneBatteryLogRepository;
import com.emekamomodu.dronewebservice.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 1/28/22 9:40 AM
 */
@Service
@Transactional
@EnableScheduling
@ConditionalOnProperty(name = "battery-log.scheduler.enabled", havingValue = "true")
public class DroneBatteryLevelService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneBatteryLogRepository droneBatteryLogRepository;

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds:60000}", initialDelayString = "${initialDelay.in.milliseconds:30000}")
    public void run() {
        logger.info("Scheduled Service Running");
        getAndLogAllDronesBatteryLevel();
        logger.info("Scheduled Service Completed");
    }

    private void getAndLogAllDronesBatteryLevel() {
        logger.info("Getting and logging Battery Level of All Registered Drones");
        // get all drones
        List<Drone> droneList = droneRepository.findAll();
        if (droneList.size() == 0) {
            logger.error("No Registered Drone Found");
        }
        // log battery level
        for (Drone drone : droneList) {
            Integer batteryLevel = drone.getBatteryLevel();
            DroneBatteryLog droneBatteryLog = new DroneBatteryLog(batteryLevel, drone);
            droneBatteryLogRepository.save(droneBatteryLog);
        }

    }

}
