package org.example;

import java.time.ZonedDateTime;

public interface DueDateCalculator {

    /**
     * Calculates the due date based on the submit date and turnaround time in hours.
     * Assumes input date-times are in the UTC timezone.
     * Considers working hours to be 9 AM to 5 PM, Monday to Friday.
     *
     * @param submitDateTime the ZonedDateTime the task was submitted
     * @param turnaroundInHours the turnaround time in working hours
     * @return the calculated due ZonedDateTime
     */
    ZonedDateTime calculateDueDate(ZonedDateTime submitDateTime, int turnaroundInHours);
}
