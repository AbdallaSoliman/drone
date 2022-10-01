package com.musala.drones.repository;

import com.musala.drones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID>, DroneRepositoryCustom {
  Drone findBySerial(String serial);

  @Query("SELECT d.batteryCapacity FROM Drone d WHERE d.id = :id")
  Float getBatteryCapacityById(UUID id);
}
