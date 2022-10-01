package com.musala.drones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.dto.DroneRequestDto;
import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.model.Drone;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class LoadedMedicationItemsApplicationTests {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private DroneRepository droneRepository;
  @Autowired private MedicationRepository medicationRepository;

  @Test
  void getMedicationItems() throws Exception {
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

    // Drone serial 8 weight 499
    mockMvc
        .perform(
            post("/api/drone/{id}/medication", "19976dee-b5da-4953-98d0-c43cbec1eff2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadingMedicationDtoList)))
        .andDo(print())
        .andExpect(status().isOk());

    mockMvc
        .perform(
            get("/api/drone/{id}/medication", "19976dee-b5da-4953-98d0-c43cbec1eff2")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "[{\"id\":\"e69d0ab5-9fa9-45fa-b7c7-2aa97d101615\",\"name\":\"medication4\",\"weight\":40.0,\"code\":\"CODE_4\"},{\"id\":\"7c6c3022-8099-451e-a7d2-7bd4407e84b6\",\"name\":\"medication1\",\"weight\":10.0,\"code\":\"CODE_1\"}]"));
  }
}
