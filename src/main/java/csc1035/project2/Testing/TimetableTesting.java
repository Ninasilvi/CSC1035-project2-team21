package csc1035.project2.Testing;

import csc1035.project2.Timetable;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TimetableTesting {

    private Timetable timetable = new Timetable();

    @Test
    public void testOverlap() {
        assertFalse(timetable.timeOverlap("18:00", "18:30", "18:30", "19:00"));
    }
    @Test
    public void testOverlap2() {
        assertFalse(timetable.timeOverlap("08:00", "08:30", "08:40", "09:00"));
    }
    @Test
    public void testOverlap3() {
        assertFalse(timetable.timeOverlap("08:00", "08:30", "08:40", "09:00"));
    }
    @Test
    public void testOverlap4() {
        assertFalse(timetable.timeOverlap("09:00", "09:30", "08:30", "09:00"));
    }
    @Test
    public void testOverlap5() {
        assertTrue(timetable.timeOverlap("18:00", "18:30", "18:20", "19:20"));
    }
    @Test
    public void testOverlap6() {
        assertTrue(timetable.timeOverlap("08:00", "08:30", "08:20", "09:00"));
    }
    @Test
    public void testOverlap7() {
        assertTrue(timetable.timeOverlap("18:00", "18:30", "17:30", "19:00"));
    }
    @Test
    public void testOverlap8() {
        assertTrue(timetable.timeOverlap("08:00", "08:30", "08:10", "08:20"));
    }
    @Test
    public void testOverlap9() {
        assertTrue(timetable.timeOverlap("12:00", "13:30", "13:10", "14:20"));
    }
    @Test
    public void testOverlap10() {
        assertFalse(timetable.timeOverlap("12:30", "13:00", "12:00", "12:30"));
    }
    @Test
    public void testOverlap11() {
        assertTrue(timetable.timeOverlap("12:30", "13:00", "12:20", "12:40"));
    }
    @Test
    public void testOverlap12() {
        assertTrue(timetable.timeOverlap("12:00", "13:00", "12:00", "13:00"));
    }
}