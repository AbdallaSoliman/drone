package com.musala.drones.service;

import com.googlecode.jmapper.JMapper;
import com.musala.drones.dto.DroneRequestDto;
import com.musala.drones.dto.DroneResponseDto;
import com.musala.drones.dto.LoadingMedicationDto;
import com.musala.drones.message.util.MessageUtilities;
import com.musala.drones.model.Drone;
import com.musala.drones.model.DroneMedication;
import com.musala.drones.model.Medication;
import com.musala.drones.model.enumerate.State;
import com.musala.drones.repository.DroneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DroneService {
  private final DroneRepository droneRepository;
  JMapper<Drone, DroneRequestDto> batchMapper = new JMapper<>(Drone.class, DroneRequestDto.class);
  JMapper<DroneMedication, LoadingMedicationDto> droneMedicationLoadingMedicationDtoJMapper =
      new JMapper<>(DroneMedication.class, LoadingMedicationDto.class);

  public DroneService(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  public ResponseEntity<?> registeringDrone(DroneRequestDto droneRequestDto) {

    droneRepository.save(batchMapper.getDestination(droneRequestDto));
    return new MessageUtilities().getSuccessMessage("DroneHaveBeenRegisteredSuccessfully");
  }

  public ResponseEntity<?> loadingMedicationItems(
      UUID id, List<LoadingMedicationDto> loadingMedicationDtoList) {
    Optional<Drone> drone = droneRepository.findById(id);
    List<DroneMedication> droneMedications =
        loadingMedicationDtoList.stream()
            .map(elm -> droneMedicationLoadingMedicationDtoJMapper.getDestination(elm))
            .toList();
    droneMedications.forEach(elm -> drone.get().addMedication(elm.getMedication(), elm.getCount()));
    droneRepository.save(drone.get());
    return new MessageUtilities()
        .getSuccessMessage("LoadingADroneWithMedicationItemsHaveBeenRegisteredSuccessfully");
  }

  public List<Medication> getMedicationItems(UUID id) {
    return droneRepository.findById(id).get().getDroneMedications().stream()
        .map(DroneMedication::getMedication)
        .toList();
  }

  public Page<DroneResponseDto> getAvailableDronesForLoading(Pageable pageable) {
    return droneRepository.getDroneResponseByState(State.IDLE, pageable);
  }

  public Float getBatteryCapacity(UUID id) {
    return droneRepository.getBatteryCapacityById(id);
  }
}
