package com.example.demo.service;

import com.example.demo.model.WeeklyValueEntity;
import com.example.demo.repository.WeeklyValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class WeeklyValueService {
    private static final long WEEK_DURATION = ChronoUnit.WEEKS.getDuration().toDays();
    private static final long WORKDAYS = WEEK_DURATION - 2;
    private static final BigDecimal DIVIDER = BigDecimal.valueOf(WORKDAYS);

    private final WeeklyValueRepository weeklyValueRepository;

    @Transactional
    public List<MonthlyValue> fetchAsMonthlyValues(Integer month) {
        final List<MonthlyValue> result = prepareResult(month);
        final Map<Integer, MonthlyValue> resultAsMap = result.stream()
            .collect(Collectors.toMap(MonthlyValue::getMonth, Function.identity()));
        List<WeeklyValueEntity> weeklyValues = weeklyValueRepository.findAll();
        for (WeeklyValueEntity value : weeklyValues) {
            LocalDate date = value.getDate();
            int dayOfMonth = date.getDayOfMonth();
            int monthValue = date.getMonthValue();
            BigDecimal money = value.getMoney();

            if (dayOfMonth < WEEK_DURATION) {
                long part1 = WEEK_DURATION - dayOfMonth;
                long part2 = dayOfMonth - 2;
                if (part2 <= 0) {
                    updateMoney(monthValue - 1, resultAsMap, money);
                } else {
                    updateMoney(monthValue - 1, resultAsMap, money
                        .multiply(BigDecimal.valueOf(part1))
                        .divide(DIVIDER, RoundingMode.HALF_DOWN));
                    updateMoney(monthValue, resultAsMap, money
                        .multiply(BigDecimal.valueOf(part2))
                        .divide(DIVIDER, RoundingMode.HALF_UP));
                }
            } else {
                updateMoney(monthValue, resultAsMap, money);
            }
        }
        return result;
    }

    // update value if exists only
    private void updateMoney(Integer month, Map<Integer, MonthlyValue> resultAsMap, BigDecimal money) {
        resultAsMap.computeIfPresent(month, (key, value) -> value.addMoney(money));
    }

    private List<MonthlyValue> prepareResult(Integer month) {
        if (month == null) {
            return IntStream.rangeClosed(1, 12)
                .mapToObj(MonthlyValue::new)
                .collect(Collectors.toList());
        } else {
            return Collections.singletonList(new MonthlyValue(month));
        }
    }

}
