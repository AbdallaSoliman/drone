package com.musala.drones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.dto.DroneRequestDto;
import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.model.Drone;
import com.musala.drones.model.DroneMedication;
import com.musala.drones.model.Medication;
import com.musala.drones.model.enumerate.Model;
import com.musala.drones.model.enumerate.State;
import com.musala.drones.repository.DroneRepository;
import com.musala.drones.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    properties = {
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.liquibase.enabled=false",
      "spring.flyway.enabled=false"
    })
@AutoConfigureMockMvc
class LoadingDronesApplicationTests {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void loadingMedicationItems() throws Exception {

    List<LoadingMedicationDto> loadingMedicationDtoList = new ArrayList<>();
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            5, UUID.fromString("7c6c3022-8099-451e-a7d2-7bd4407e84b6"))); // 5 *10
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            1, UUID.fromString("e69d0ab5-9fa9-45fa-b7c7-2aa97d101615"))); // 1*80
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            3, UUID.fromString("7c6c3022-8099-451e-a7d2-7bd4407e84b6"))); // 3*10

    // Drone serial 9 weight 500
    mockMvc
        .perform(
            post("/api/drone/{id}/medication", "50f4c99b-0ee3-4c4b-aa73-ee3523a3364a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadingMedicationDtoList)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{\"header\":\"Loading a drone with medication items have been registered successfully\",\"messageType\":\"SUCCESS\",\"messages\":null}"));
  }

  @Test
  void canHandleWeightValidator() throws Exception {

    List<LoadingMedicationDto> loadingMedicationDtoList = new ArrayList<>();
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            5, UUID.fromString("7c6c3022-8099-451e-a7d2-7bd4407e84b6"))); // 5 *10
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            8, UUID.fromString("e69d0ab5-9fa9-45fa-b7c7-2aa97d101615"))); // 8*80
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            3, UUID.fromString("7c6c3022-8099-451e-a7d2-7bd4407e84b6"))); // 3*10

    // Drone serial 2 weight 80
    mockMvc
        .perform(
            post("/api/drone/{id}/medication", "d7b2dfb0-5bd4-470d-91ea-1a7c755b4fff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadingMedicationDtoList)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(
                    "{\"header\":\"Constraint Violation\",\"messageType\":\"VALIDATION\",\"messages\":[\"A drone cannot be loaded with more weight than it can handle\"]}"));
  }

  @Test
  void lowBatteryValidator() throws Exception {

    List<LoadingMedicationDto> loadingMedicationDtoList = new ArrayList<>();
    loadingMedicationDtoList.add(
        new LoadingMedicationDto(
            5, UUID.fromString("7c6c3022-8099-451e-a7d2-7bd4407e84b6"))); // 5 *10

    // Drone serial 6 battery 14
    mockMvc
        .perform(
            post("/api/drone/{id}/medication", "4c554d39-6bd4-477d-adea-f78aa29b9de2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadingMedicationDtoList)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(
                    "{\"header\":\"Constraint Violation\",\"messageType\":\"VALIDATION\",\"messages\":[\"Low battery\"]}"));
  }
}
