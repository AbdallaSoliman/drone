package com.musala.drones.controller;

import com.musala.drones.dto.DroneRequestDto;
import com.musala.drones.dto.DroneResponseDto;
import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.model.Medication;
import com.musala.drones.service.DroneService;
import com.musala.drones.validation.CanHandleWeight;
import com.musala.drones.validation.LowBattery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
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

  @Tag(name = "1. Add a new drone")
  @Operation(
      operationId = "registeringDrone",
      summary = "Add a new drone",
      description = "registering a drone")
  @PostMapping
  public ResponseEntity<?> registeringDrone(
      @Validated @RequestBody DroneRequestDto droneRequestDto) {

    return droneService.registeringDrone(droneRequestDto);
  }

  @Tag(name = "2. Add medication to specific drone")
  @Operation(
      operationId = "loadingMedicationItems",
      summary = "Add medication to specific drone",
      description = "loading a drone with medication items")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content =
          @Content(
              examples = {
                @ExampleObject(
                    name = "Medication sample",
                    summary = "Medication example",
                    description =
                        "we add 6 medication (2 + 4) with weight 30gr and 1 medication weight 90gr "
                            + "as total 270gr",
                    value =
                        "[\n"
                            + "  {\n"
                            + "    \"count\": 2,\n"
                            + "    \"id\": \"089a59c8-edaf-4df8-a2fc-8ebd13a0fdb2\"\n"
                            + "  },\n"
                            + " {\n"
                            + "    \"count\": 1,\n"
                            + "    \"id\": \"33dd7f3a-dae5-44c1-96b1-ea4571ed9f0c\"\n"
                            + "  },\n"
                            + " {\n"
                            + "    \"count\": 4,\n"
                            + "    \"id\": \"089a59c8-edaf-4df8-a2fc-8ebd13a0fdb2\"\n"
                            + "  }\n"
                            + "]")
              }))
  @PostMapping("{id}/medication")
  @CanHandleWeight
  @LowBattery
  public ResponseEntity<?> loadingMedicationItems(
      @PathVariable
          @Parameter(
              example = "683f6a30-3459-4b6d-bd80-7558d9a6f408",
              description = "id for drone serial 7")
          UUID id,
      @Validated @RequestBody List<LoadingMedicationDto> loadingMedicationDtoList) {

    return droneService.loadingMedicationItems(id, loadingMedicationDtoList);
  }

  @Tag(name = "3. checking loaded medication items for a given drone")
  @Operation(
      operationId = "getMedicationItems",
      summary = "checking loaded medication items for a given drone",
      description = "get medications by drone id")
  @GetMapping("{id}/medication")
  public List<Medication> getMedicationItems(
      @PathVariable
          @Parameter(
              example = "b7f799ad-585a-4404-a94b-e6c78c841b6c",
              description = "id for drone with serial 10,we loading drone with default data")
          UUID id) {
    return droneService.getMedicationItems(id);
  }

  @Tag(name = "4. checking available drones for loading")
  @Operation(
      operationId = "getAvailableDronesForLoading",
      summary = "checking available drones for loading",
      description =
          "get drones that has state IDLE as assumptions that all IDLE drones are available for loading"
              + "and another assumptions supporting Pageable")
  @GetMapping("available-for-loading")
  public Page<DroneResponseDto> getAvailableDronesForLoading(@ParameterObject Pageable pageable) {
    return droneService.getAvailableDronesForLoading(pageable);
  }

  @Tag(name = "5. check drone battery level for a given drone")
  @Operation(
      operationId = "getBatteryCapacity",
      summary = "get BatteryCapacity by drone id",
      description =
          "returning float number represent BatteryCapacity percentage")
  @GetMapping("{id}/battery-capacity")
  public Float getBatteryCapacity(@PathVariable    @Parameter(
          example = "683f6a30-3459-4b6d-bd80-7558d9a6f408",
          description = "id for drone serial 7 -> 80% ") UUID id) {
    return droneService.getBatteryCapacity(id);
  }
}
