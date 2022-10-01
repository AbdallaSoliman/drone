package com.musala.drones.controller;

import com.musala.drones.dto.DroneRequestDto;
import com.musala.drones.dto.DroneResponseDto;
import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.model.Medication;
import com.musala.drones.service.DroneService;
import com.musala.drones.validation.CanHandleWeight;
import com.musala.drones.validation.LowBattery;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/drone")
public class DroneController {
  private final DroneService droneService;

  public DroneController(DroneService droneService) {
    this.droneService = droneService;
  }

  @PostMapping
  public ResponseEntity<?> registeringDrone(@Validated @RequestBody DroneRequestDto droneRequestDto) {

    return droneService.registeringDrone(droneRequestDto);
  }

  @PostMapping("{id}/medication")
  @CanHandleWeight
  @LowBattery
  public ResponseEntity<?> loadingMedicationItems(
      @PathVariable UUID id,
      @Validated @RequestBody List<LoadingMedicationDto> loadingMedicationDtoList) {

    return droneService.loadingMedicationItems(id, loadingMedicationDtoList);
  }

  @GetMapping("{id}/medication")
  public List<Medication> getMedicationItems(@PathVariable UUID id) {

    return droneService.getMedicationItems(id);
  }

  @GetMapping("available-for-loading")
  public Page<DroneResponseDto> getAvailableDronesForLoading(@ParameterObject Pageable pageable) {
    return droneService.getAvailableDronesForLoading(pageable);
  }

  @GetMapping("{id}/battery-capacity")
  public Float getBatteryCapacity(@PathVariable UUID id) {
    return droneService.getBatteryCapacity(id);
  }
}
