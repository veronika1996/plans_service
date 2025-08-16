package com.plans_service.repository;

import com.plans_service.entity.PlanEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    Optional<PlanEntity> findByDateAndUsername(LocalDate date, String username);
}
