package com.musala.drones.repository;

import com.musala.drones.dto.DroneResponseDto;
import com.musala.drones.model.enumerate.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepositoryCustom {
     Page<DroneResponseDto> getDroneResponseByState(State state, Pageable pageable);
}
