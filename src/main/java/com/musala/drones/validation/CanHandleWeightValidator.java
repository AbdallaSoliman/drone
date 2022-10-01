package com.musala.drones.validation;

import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.message.util.MessageUtilities;
import com.musala.drones.model.Drone;
import com.musala.drones.model.Medication;
import com.musala.drones.repository.DroneRepository;
import com.musala.drones.repository.MedicationRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class CanHandleWeightValidator implements ConstraintValidator<CanHandleWeight, Object[]> {

  private final DroneRepository droneRepository;
  private final MedicationRepository medicationRepository;

  public CanHandleWeightValidator(
      DroneRepository droneRepository, MedicationRepository medicationRepository) {
    this.droneRepository = droneRepository;
    this.medicationRepository = medicationRepository;
  }

  @Override
  public boolean isValid(Object[] value, ConstraintValidatorContext context) {
    // 0->UUID id and 1 -> List<LoadingMedicationDto> loadingMedicationDtoList
    if (value[0] == null || value[1] == null) {
      return false;
    }

    Optional<Drone> drone = droneRepository.findById((UUID) value[0]);
    if (drone.isPresent()) {
      Double oldWeight =
          drone.get().getDroneMedications().stream()
              .mapToDouble(elm -> elm.getMedication().getWeight() * elm.getCount())
              .sum();
      List<LoadingMedicationDto> loadingMedicationDtoList = (List<LoadingMedicationDto>) value[1];
      List<Medication> medicationList =
          medicationRepository.findAllById(
              loadingMedicationDtoList.stream().map(LoadingMedicationDto::getId).toList());

      Double newWeight =
          medicationList.stream()
              .mapToDouble(
                  elm ->
                      elm.getWeight()
                          * loadingMedicationDtoList.stream()
                              .filter(dto -> elm.getId().equals(dto.getId()))
                              .mapToDouble(LoadingMedicationDto::getCount)
                              .sum())
              .sum();

      if (oldWeight + newWeight > drone.get().getWeightLimit()) {
        context.disableDefaultConstraintViolation();
        String thereIsNoDroneWithThisId =
            new MessageUtilities().getMessage("ADroneCannotBeLoadedWithMoreWeightThanItCanHandle");
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
