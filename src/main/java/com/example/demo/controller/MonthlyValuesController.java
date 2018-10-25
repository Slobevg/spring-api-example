package com.example.demo.controller;

import com.example.demo.service.MonthlyValue;
import com.example.demo.service.WeeklyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/monthly")
public class MonthlyValuesController {

    private final WeeklyValueService weeklyValueService;

    @GetMapping
    public List<MonthlyValue> list(@RequestParam(name = "MonthNumber", required = false) Integer month) {
        return weeklyValueService.fetchAsMonthlyValues(month);
    }
}
