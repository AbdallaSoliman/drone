package com.musala.drones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile({"!prod"})
public class OpenApiConfig implements WebMvcConfigurer {

  @Bean
  public OpenAPI springShopOpenAPI() {

    return new OpenAPI()
        .info(
            new Info()
                .title("Drone API")
                .description("Spring application")
                .version("v0.0.1"));
  }


}
