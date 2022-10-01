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
    //        .andExpect(
    //            content()
    //                .json(
    //
    // "{\"content\":[{\"id\":\"7d00bb7c-ce60-472f-81dd-5d1ff3c0dc4f\",\"serial\":\"1\",\"model\":\"LIGHT_WEIGHT\",\"weightLimit\":50.0,\"batteryCapacity\":77.0,\"state\":\"IDLE\"},{\"id\":\"50f4c99b-0ee3-4c4b-aa73-ee3523a3364a\",\"serial\":\"9\",\"model\":\"HEAVY_WEIGHT\",\"weightLimit\":500.0,\"batteryCapacity\":42.0,\"state\":\"IDLE\"},{\"id\":\"b7f799ad-585a-4404-a94b-e6c78c841b6c\",\"serial\":\"10\",\"model\":\"MIDDLE_WEIGHT\",\"weightLimit\":140.0,\"batteryCapacity\":100.0,\"state\":\"IDLE\"}],\"pageable\":{\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"offset\":0,\"pageSize\":20,\"pageNumber\":0,\"paged\":true,\"unpaged\":false},\"totalPages\":1,\"last\":true,\"totalElements\":3,\"size\":20,\"number\":0,\"sort\":{\"empty\":true,\"sorted\":false,\"unsorted\":true},\"first\":true,\"numberOfElements\":3,\"empty\":false}"));
  }
}
