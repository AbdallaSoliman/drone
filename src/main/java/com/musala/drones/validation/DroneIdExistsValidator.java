package com.musala.drones.validation;

import com.musala.drones.message.util.MessageUtilities;
import com.musala.drones.model.Drone;
import com.musala.drones.repository.DroneRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.UUID;

public class DroneIdExistsValidator implements ConstraintValidator<DroneIdExists, UUID> {

  private final DroneRepository droneRepository;

  public DroneIdExistsValidator(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  @Override
  public boolean isValid(UUID id, ConstraintValidatorContext context) {
    if (id == null) {
      return false;
    }

    Optional<Drone> drone = droneRepository.findById(id);
    if (!drone.isPresent()) {
      context.disableDefaultConstraintViolation();
      String thereIsNoDroneWithThisId =
          new MessageUtilities().getMessage("ThereIsNoDroneWithThisId");
      context
          .buildConstraintViolationWithTemplate(thereIsNoDroneWithThisId)
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
