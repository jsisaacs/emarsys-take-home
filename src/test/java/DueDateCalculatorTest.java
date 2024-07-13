import org.example.DueDateCalculator;
import org.example.DueDateCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DueDateCalculatorTest {
    private DueDateCalculator dueDateCalculator;

    @BeforeEach
    public void setUp() {
        dueDateCalculator = new DueDateCalculatorImpl();
    }

    @Test
    public void testCalculateDueDate() {
        ZonedDateTime submitDateTime = ZonedDateTime.of(2024, 7, 15, 14, 12, 0, 0, ZoneId.of("UTC"));
        int turnaroundInHours = 16;

        ZonedDateTime expectedDueDateTime = ZonedDateTime.of(2024, 7, 16, 14, 12, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime actualDueDateTime = dueDateCalculator.calculateDueDate(submitDateTime, turnaroundInHours);

        assertEquals(expectedDueDateTime, actualDueDateTime);
    }
}
