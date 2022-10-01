package com.musala.drones.service;

import com.googlecode.jmapper.JMapper;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.message.util.MessageUtilities;
import com.musala.drones.model.Drone;
import com.musala.drones.repository.DroneRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DroneService {
  private final DroneRepository droneRepository;
  JMapper<Drone, DroneDto> batchMapper = new JMapper<>(Drone.class, DroneDto.class);

  public DroneService(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  public ResponseEntity<?> registeringDrone(DroneDto droneDto) {

    droneRepository.save(batchMapper.getDestination(droneDto));
    return new MessageUtilities().getSuccessMessage("DroneHaveBeenRegisteredSuccessfully");
  }
}
