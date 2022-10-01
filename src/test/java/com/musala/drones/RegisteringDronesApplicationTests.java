package com.musala.drones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.dto.DroneRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
    properties = {
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.liquibase.enabled=false",
      "spring.flyway.enabled=false"
    })
@AutoConfigureMockMvc
class RegisteringDronesApplicationTests {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private DroneRepository droneRepository;
  @Autowired private MedicationRepository medicationRepository;

  @Test
  void registeringDrone() throws Exception {
    String serial = "qewwe3142ewqeq";
    DroneRequestDto droneRequestDto = new DroneRequestDto(serial, Model.CRUISER_WEIGHT, 10f, 100f, State.LOADED);

    mockMvc
        .perform(
            post("/api/drone")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(droneRequestDto)))
        .andExpect(status().isOk());

    Drone droneEntity = droneRepository.findBySerial(serial);
    assertThat(droneEntity.getWeightLimit()).isEqualTo(10f);
  }
  @Test
  void validMedication() {
    Medication medication=new Medication();
    medication.setName("Medication_Name-12");
    medication.setWeight(54f);
    medication.setCode("CODE_50");
    assertNotNull(medicationRepository.save(medication));
  }
  @Test
  void notValidMedicationCod() {
    Medication medication=new Medication();
    medication.setName("name1");
    medication.setWeight(54f);
    medication.setCode("CODE_a");
    TransactionSystemException thrown =   assertThrows(TransactionSystemException.class,() ->medicationRepository.save(medication));
    assertTrue(thrown.getCause().getCause().getMessage().contains("code must match [A-Z\\d_]+"));
  }
  @Test
  void notValidMedicationName() {
    Medication medication=new Medication();
    medication.setName("na$me1");
    medication.setWeight(54f);
    medication.setCode("CODE");
    TransactionSystemException thrown =   assertThrows(TransactionSystemException.class,() ->medicationRepository.save(medication));
    assertTrue(thrown.getCause().getCause().getMessage().contains("name must match [\\w\\-]+"));
  }
}
