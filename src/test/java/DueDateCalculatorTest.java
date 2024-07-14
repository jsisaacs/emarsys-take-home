import org.example.DueDateCalculator;
import org.example.DueDateCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DueDateCalculatorTest {
    private DueDateCalculator dueDateCalculator;

    @BeforeEach
    public void setUp() {
        dueDateCalculator = new DueDateCalculatorImpl();
    }

    @Test
    public void calculateDueDate_validDatetimeAndSameDayTurnaround_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 10, 0, 0, 0, ZoneId.of("UTC")); // 10:00 AM on Tuesday
        int turnaroundHours = 5; // 5 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 9, 15, 0, 0, 0, ZoneId.of("UTC")); // 3:00 PM on the same day

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_validDatetimeAndNextDayTurnaround_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 14, 0, 0, 0, ZoneId.of("UTC")); // 2:00 PM on Tuesday
        int turnaroundHours = 5; // 5 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 10, 11, 0, 0, 0, ZoneId.of("UTC")); // 11:00 AM on the next day

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_validDatetimeAndOverWeekendTurnaround_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 12, 16, 0, 0, 0, ZoneId.of("UTC")); // 4:00 PM on Friday
        int turnaroundHours = 5; // 5 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 15, 13, 0, 0, 0, ZoneId.of("UTC")); // 1:00 PM on Monday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_validDatetimeAndMultipleDaysTurnaround_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Tuesday
        int turnaroundHours = 16; // 16 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 11, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Thursday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_validDatetimeAndEndOfDayTurnaround_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 16, 0, 0, 0, ZoneId.of("UTC")); // 4:00 PM on Tuesday
        int turnaroundHours = 1; // 1 working hour
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 10, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on the next day

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_submitAtStartOfWorkingHours_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Tuesday
        int turnaroundHours = 8; // 8 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 10, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Wednesday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_submitAtEndOfWorkingHours_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 17, 0, 0, 0, ZoneId.of("UTC")); // 5:00 PM on Tuesday
        int turnaroundHours = 1; // 1 working hour
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 10, 10, 0, 0, 0, ZoneId.of("UTC")); // 10:00 AM on Wednesday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_minimumTurnaroundTime_returnsCorrectResolvedDate() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Tuesday
        int turnaroundHours = 1; // 1 working hour
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 9, 10, 0, 0, 0, ZoneId.of("UTC")); // 10:00 AM on Tuesday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }

    @Test
    public void calculateDueDate_nonWorkingDaySubmit_throwsIllegalArgumentException() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 13, 9, 0, 0, 0, ZoneId.of("UTC")); // 9:00 AM on Saturday
        int turnaroundHours = 8; // 8 working hours

        assertThrows(IllegalArgumentException.class, () -> dueDateCalculator.calculateDueDate(submitDate, turnaroundHours), "Submit date must be on a working day (Monday to Friday).");
    }

    @Test
    public void calculateDueDate_nonWorkingHourSubmit_throwsIllegalArgumentException() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 15, 8, 59, 0, 0, ZoneId.of("UTC")); // 8:59 AM on Monday
        int turnaroundHours = 8; // 8 working hours

        assertThrows(IllegalArgumentException.class, () -> dueDateCalculator.calculateDueDate(submitDate, turnaroundHours),  "Submit date must be within working hours (9 AM to 5 PM).");
    }

    @Test
    public void calculateDueDate_dueDateAfterEndOfWorkDay_movesToNextStartOfDay() {
        ZonedDateTime submitDate = ZonedDateTime.of(2024, 7, 9, 16, 30, 0, 0, ZoneId.of("UTC")); // 4:30 PM on Tuesday
        int turnaroundHours = 2; // 2 working hours
        ZonedDateTime expectedDueDate = ZonedDateTime.of(2024, 7, 10, 10, 30, 0, 0, ZoneId.of("UTC")); // 10:30 AM on Wednesday

        ZonedDateTime actualDueDate = dueDateCalculator.calculateDueDate(submitDate, turnaroundHours);
        assertEquals(expectedDueDate, actualDueDate);
    }
}
