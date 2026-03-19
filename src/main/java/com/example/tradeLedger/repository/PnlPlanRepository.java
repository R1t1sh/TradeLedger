package com.example.tradeLedger.repository;

import com.example.tradeLedger.entity.PnlPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PnlPlanRepository extends JpaRepository<PnlPlan, Long> {

    Optional<PnlPlan> findFirstByUser_IdAndActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDesc(
            Long userId,
            LocalDate tradeDateStart,
            LocalDate tradeDateEnd
    );

    List<PnlPlan> findByUser_IdOrderByStartDateDesc(Long userId);

    Optional<PnlPlan> findByIdAndUser_Id(Long id, Long userId);

    List<PnlPlan> findByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate dateStart, LocalDate dateEnd);
}
