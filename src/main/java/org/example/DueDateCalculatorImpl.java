package org.example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;

public class DueDateCalculatorImpl implements DueDateCalculator {
    private static final int START_WORK_HOUR = 9;
    private static final int END_WORK_HOUR = 17;

    @Override
    public ZonedDateTime calculateDueDate(ZonedDateTime submitDate, int turnaroundHours) {
        validateSubmitDate(submitDate);

        ZonedDateTime dueDate = submitDate;
        int remainingHours = turnaroundHours;
        int remainingMinutes = 0;

        while (remainingHours > 0 || remainingMinutes > 0) {
            dueDate = adjustToWorkingHours(dueDate);

            long minutesToEndOfDay = calculateMinutesToEndOfDay(dueDate);
            int hoursToEndOfDay = (int) (minutesToEndOfDay / 60);

            if (remainingHours < hoursToEndOfDay) {
                dueDate = dueDate.plusHours(remainingHours);
                remainingHours = 0;
            } else {
                dueDate = moveToNextStartOfDay(dueDate);
                remainingMinutes = (int) (minutesToEndOfDay % 60);
                if (hoursToEndOfDay < 1 && remainingMinutes > 0) {
                    remainingHours--;
                    dueDate = dueDate.plusMinutes(remainingMinutes);
                    remainingMinutes = 0;
                } else {
                    remainingHours -= hoursToEndOfDay;
                }
            }
        }

        return dueDate;
    }

    private void validateSubmitDate(ZonedDateTime submitDate) {
        ZonedDateTime startOfWorkDay = submitDate.withHour(START_WORK_HOUR).withMinute(0).withSecond(0).withNano(0);
        ZonedDateTime endOfWorkDay = submitDate.withHour(END_WORK_HOUR).withMinute(0).withSecond(0).withNano(0);

        if (submitDate.isBefore(startOfWorkDay) || submitDate.isAfter(endOfWorkDay)) {
            throw new IllegalArgumentException("Submit date must be within working hours (9 AM to 5 PM).");
        }

        if (isNonWorkingDay(submitDate.getDayOfWeek())) {
            throw new IllegalArgumentException("Submit date must be on a working day (Monday to Friday).");
        }
    }

    private ZonedDateTime adjustToWorkingHours(ZonedDateTime dueDate) {
        while (dueDate.getHour() >= END_WORK_HOUR || isNonWorkingDay(dueDate.getDayOfWeek())) {
            if (dueDate.getHour() >= END_WORK_HOUR) {
                dueDate = moveToNextStartOfDay(dueDate);
            }
            if (isNonWorkingDay(dueDate.getDayOfWeek())) {
                dueDate = moveToNextStartOfDay(dueDate);
            }
        }
        return dueDate;
    }

    private long calculateMinutesToEndOfDay(ZonedDateTime dueDate) {
        ZonedDateTime endOfWorkDay = dueDate.withHour(END_WORK_HOUR).withMinute(0).withSecond(0).withNano(0);
        return Duration.between(dueDate, endOfWorkDay).toMinutes();
    }

    private boolean isNonWorkingDay(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private ZonedDateTime moveToNextStartOfDay(ZonedDateTime dateTime) {
        ZonedDateTime nextStartOfDay = dateTime.plusDays(1).withHour(START_WORK_HOUR).withMinute(0).withSecond(0).withNano(0);
        while (isNonWorkingDay(nextStartOfDay.getDayOfWeek())) {
            nextStartOfDay = nextStartOfDay.plusDays(1);
        }
        return nextStartOfDay;
    }
}
