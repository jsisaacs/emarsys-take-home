package org.example;

import java.time.ZonedDateTime;

public interface DueDateCalculator {
    ZonedDateTime calculateDueDate(ZonedDateTime submitDateTime, int turnaroundInHours);
}
