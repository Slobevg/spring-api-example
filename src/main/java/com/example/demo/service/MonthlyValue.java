package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyValue {

    private int month;
    private BigDecimal summa;

    public MonthlyValue(int month) {
        this(month, BigDecimal.ZERO);
    }

    public MonthlyValue addMoney(BigDecimal money) {
        this.summa = this.summa.add(money);
        return this;
    }
}
