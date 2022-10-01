package com.musala.drones.controller;

import com.googlecode.jmapper.JMapper;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.model.Drone;
import com.musala.drones.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/drone")
public class DroneController {
  final private DroneService droneService;

  public DroneController(DroneService droneService) {
    this.droneService = droneService;
  }

  @PostMapping
  public ResponseEntity<?> registeringDrone(DroneDto droneDto) {

    return droneService.registeringDrone(droneDto);
  }
}
