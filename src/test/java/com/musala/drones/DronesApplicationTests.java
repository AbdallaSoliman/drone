package com.musala.drones;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.model.Drone;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import com.musala.drones.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false",
        "spring.flyway.enabled=false"
})
@AutoConfigureMockMvc
class DronesApplicationTests {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private DroneRepository droneRepository;

  @Test
  void registeringDrone() throws Exception {
    DroneDto droneDto = new DroneDto("ssfdsf", Model.CRUISER_WEIGHT, 10f, 100f, State.LOADED);

    mockMvc
        .perform(
            post("/api/drone")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(droneDto)))
        .andExpect(status().isOk());

    Drone droneEntity = droneRepository.findBySerial("ssfdsf");
    assertThat(droneEntity.getWeightLimit()).isEqualTo(10f);
  }
}
