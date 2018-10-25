package com.example.demo.model;

import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "test")
@Setter
public class WeeklyValueEntity {

    private LocalDate date;
    private BigDecimal money;

    @Id
    @Column
    public LocalDate getDate() {
        return date;
    }

    @Column
    public BigDecimal getMoney() {
        return money;
    }

}
