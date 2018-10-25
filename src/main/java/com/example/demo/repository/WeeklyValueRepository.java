package com.example.demo.repository;

import com.example.demo.model.WeeklyValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface WeeklyValueRepository extends JpaRepository<WeeklyValueEntity, LocalDate>{

}
