package com.musala.drones.validation;

import com.musala.drones.message.util.MessageUtilities;
import com.musala.drones.model.Drone;
import com.musala.drones.repository.DroneRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;
import java.util.UUID;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class LowBatteryValidator implements ConstraintValidator<LowBattery, Object[]> {

  private final DroneRepository droneRepository;

  public LowBatteryValidator(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  @Override
  public boolean isValid(Object[] value, ConstraintValidatorContext context) {
    // 0->UUID id and 1 -> List<LoadingMedicationDto> loadingMedicationDtoList
    if (value[0] == null || value[1] == null) {
      return false;
    }

    Optional<Drone> drone = droneRepository.findById((UUID) value[0]);
    if (drone.isPresent()) {

      if (drone.get().getBatteryCapacity() < 25) {
        context.disableDefaultConstraintViolation();
        String thereIsNoDroneWithThisId = new MessageUtilities().getMessage("LowBattery");
        context
            .buildConstraintViolationWithTemplate(thereIsNoDroneWithThisId)
            .addConstraintViolation();
        return false;
      }
    } else {
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
