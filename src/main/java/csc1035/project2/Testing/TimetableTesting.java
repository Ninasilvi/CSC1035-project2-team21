package csc1035.project2.Testing;

import csc1035.project2.Timetable;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTesting {

    private Timetable timetable = new Timetable();

    @Test
    public void testOverlap() {
        assertEquals(timetable.timeOverlap("18:00", "18:30", "18:30", "19:00"), false);
    }
    @Test
    public void testOverlap2() {
        assertEquals(timetable.timeOverlap("08:00", "08:30", "08:40", "09:00"), false);
    }
    @Test
    public void testOverlap3() {
        assertEquals(timetable.timeOverlap("08:00", "08:30", "08:40", "09:00"), false);
    }
    @Test
    public void testOverlap4() {
        assertEquals(timetable.timeOverlap("09:00", "09:30", "08:30", "09:00"), false);
    }
    @Test
    public void testOverlap5() {
        assertEquals(timetable.timeOverlap("18:00", "18:30", "18:20", "19:20"), true);
    }
    @Test
    public void testOverlap6() {
        assertEquals(timetable.timeOverlap("08:00", "08:30", "08:20", "09:00"), true);
    }
    @Test
    public void testOverlap7() {
        assertEquals(timetable.timeOverlap("18:00", "18:30", "17:30", "19:00"), true);
    }
    @Test
    public void testOverlap8() {
        assertEquals(timetable.timeOverlap("08:00", "08:30", "08:10", "08:20"), true);
    }
    @Test
    public void testOverlap9() {
        assertEquals(timetable.timeOverlap("12:00", "13:30", "13:10", "14:20"), true);
    }
}