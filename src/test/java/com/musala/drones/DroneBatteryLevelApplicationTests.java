package com.musala.drones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class DroneBatteryLevelApplicationTests {
  @Autowired private MockMvc mockMvc;

  @Test
  void getMedicationItems() throws Exception {
    // Drone serial 5 batteryCapacity 38
    mockMvc
        .perform(
            get("/api/drone/{id}/battery-capacity", "66a1690a-e5e5-4cb5-87fe-b3c0e5650c82")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
            .andExpect(content().string("38.0"));
 }
}
