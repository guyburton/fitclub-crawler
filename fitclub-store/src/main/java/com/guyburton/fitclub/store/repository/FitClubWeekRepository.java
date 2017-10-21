package com.guyburton.fitclub.store.repository;

import com.guyburton.fitclub.store.entities.JpaFitClubWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FitClubWeekRepository extends JpaRepository<JpaFitClubWeek, Integer> {
}
