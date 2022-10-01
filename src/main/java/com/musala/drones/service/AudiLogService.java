package com.musala.drones.service;

import com.musala.drones.model.AuditLog;
import com.musala.drones.repository.AuditLogRepository;
import com.musala.drones.repository.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AudiLogService {
  private final DroneRepository droneRepository;
  private final AuditLogRepository auditLogRepository;

  public AudiLogService(DroneRepository droneRepository, AuditLogRepository auditLogRepository) {
    this.droneRepository = droneRepository;
    this.auditLogRepository = auditLogRepository;
  }

  @Scheduled(fixedRate = 20000)
  public void checkDronesBatteryLevels() {
    auditLogRepository.saveAll(
        droneRepository.findAll().stream()
            .map(elm -> new AuditLog(elm, elm.getBatteryCapacity()))
            .toList());
  }
}
