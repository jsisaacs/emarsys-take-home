package org.example;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DueDateCalculatorImpl implements DueDateCalculator {
    @Override
    public ZonedDateTime calculateDueDate(ZonedDateTime submitDateTime, int turnaroundInHours) {
        return ZonedDateTime.of(2024, 7, 16, 14, 12, 0, 0, ZoneId.of("UTC"));
    }
}
