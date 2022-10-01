package com.musala.drones.repository;

import com.musala.drones.dto.DroneResponseDto;
import com.musala.drones.model.Drone;
import com.musala.drones.model.enumerate.State;
import org.hibernate.query.criteria.internal.CriteriaQueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DroneRepositoryCustomImp implements DroneRepositoryCustom {
  @PersistenceContext EntityManager entityManager;

  @Override
  public Page<DroneResponseDto> getDroneResponseByState(State state, Pageable pageable) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<DroneResponseDto> cq = cb.createQuery(DroneResponseDto.class);
    Root<Drone> root = cq.from(Drone.class);
    List<Predicate> predicates = new ArrayList<>();

    predicates.add(cb.equal(root.get("state"), state));
    cq.multiselect(
            root.get("id"),
            root.get("serial"),
            root.get("model"),
            root.get("weightLimit"),
            root.get("batteryCapacity"),
            root.get("state"))
        .where(cb.and(predicates.toArray(new Predicate[0])));
    TypedQuery<DroneResponseDto> query = entityManager.createQuery(cq);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    return new PageImpl<>(query.getResultList(), pageable, getTotalRows(cb, root, predicates));
  }

  public Long getTotalRows(CriteriaBuilder cb, Root<Drone> root, List<Predicate> predicates) {
    CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
    cqCount.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));
    ((CriteriaQueryImpl) cqCount).getRoots().add(root);
    return entityManager.createQuery(cqCount).getSingleResult();
  }
}
