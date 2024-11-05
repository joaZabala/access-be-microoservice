package ar.edu.utn.frc.tup.lc.iv.models;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthRangeTest {

    @Test
    void testNoArgsConstructor() {
        AuthRange authRange = new AuthRange();

        assertNotNull(authRange);
        assertNull(authRange.getAuthRangeId());
        assertNull(authRange.getDateFrom());
        assertNull(authRange.getDateTo());
        assertNull(authRange.getHourFrom());
        assertNull(authRange.getHourTo());
        assertNull(authRange.getDaysOfWeek());
        assertNull(authRange.getPlotId());
        assertNull(authRange.getComment());
        assertFalse(authRange.isActive());
    }

    @Test
    void testAllArgsConstructor() {
        Long authRangeId = 1L;
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(7);
        LocalTime hourFrom = LocalTime.of(9, 0);
        LocalTime hourTo = LocalTime.of(17, 0);
        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        Long plotId = 2L;
        String comment = "Test comment";
        boolean isActive = true;

        AuthRange authRange = new AuthRange(
                authRangeId, dateFrom, dateTo, hourFrom, hourTo,
                daysOfWeek, plotId, comment, isActive
        );

        assertEquals(authRangeId, authRange.getAuthRangeId());
        assertEquals(dateFrom, authRange.getDateFrom());
        assertEquals(dateTo, authRange.getDateTo());
        assertEquals(hourFrom, authRange.getHourFrom());
        assertEquals(hourTo, authRange.getHourTo());
        assertEquals(daysOfWeek, authRange.getDaysOfWeek());
        assertEquals(plotId, authRange.getPlotId());
        assertEquals(comment, authRange.getComment());
        assertTrue(authRange.isActive());
    }

    @Test
    void testEntityConstructorWithValidDaysOfWeek() {
        AuthRangeEntity entity = new AuthRangeEntity();
        entity.setAuthRangeId(1L);
        entity.setDateFrom(LocalDate.now());
        entity.setDateTo(LocalDate.now().plusDays(7));
        entity.setHourFrom(LocalTime.of(9, 0));
        entity.setHourTo(LocalTime.of(17, 0));
        entity.setDaysOfWeek("MONDAY,WEDNESDAY");
        entity.setComment("Test comment");
        entity.setActive(true);

        AuthRange authRange = new AuthRange(entity);

        assertEquals(entity.getAuthRangeId(), authRange.getAuthRangeId());
        assertEquals(entity.getDateFrom(), authRange.getDateFrom());
        assertEquals(entity.getDateTo(), authRange.getDateTo());
        assertEquals(entity.getHourFrom(), authRange.getHourFrom());
        assertEquals(entity.getHourTo(), authRange.getHourTo());
        assertEquals(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY), authRange.getDaysOfWeek());
        assertEquals(entity.getComment(), authRange.getComment());
        assertEquals(entity.isActive(), authRange.isActive());
    }

    @Test
    void testEntityConstructorWithNullDaysOfWeek() {
        AuthRangeEntity entity = new AuthRangeEntity();
        entity.setAuthRangeId(1L);
        entity.setDaysOfWeek(null);

        AuthRange authRange = new AuthRange(entity);

        assertNull(authRange.getDaysOfWeek());
    }

    @Test
    void testSettersAndGetters() {
        AuthRange authRange = new AuthRange();
        Long authRangeId = 1L;
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(7);
        LocalTime hourFrom = LocalTime.of(9, 0);
        LocalTime hourTo = LocalTime.of(17, 0);
        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        Long plotId = 2L;
        String comment = "Test comment";

        authRange.setAuthRangeId(authRangeId);
        authRange.setDateFrom(dateFrom);
        authRange.setDateTo(dateTo);
        authRange.setHourFrom(hourFrom);
        authRange.setHourTo(hourTo);
        authRange.setDaysOfWeek(daysOfWeek);
        authRange.setPlotId(plotId);
        authRange.setComment(comment);
        authRange.setActive(true);

        assertEquals(authRangeId, authRange.getAuthRangeId());
        assertEquals(dateFrom, authRange.getDateFrom());
        assertEquals(dateTo, authRange.getDateTo());
        assertEquals(hourFrom, authRange.getHourFrom());
        assertEquals(hourTo, authRange.getHourTo());
        assertEquals(daysOfWeek, authRange.getDaysOfWeek());
        assertEquals(plotId, authRange.getPlotId());
        assertEquals(comment, authRange.getComment());
        assertTrue(authRange.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthRange authRange1 = new AuthRange(1L, LocalDate.now(), LocalDate.now().plusDays(7),
                LocalTime.of(9, 0), LocalTime.of(17, 0),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                2L, "Test comment", true);

        AuthRange authRange2 = new AuthRange(1L, LocalDate.now(), LocalDate.now().plusDays(7),
                LocalTime.of(9, 0), LocalTime.of(17, 0),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                2L, "Test comment", true);

        AuthRange authRange3 = new AuthRange(2L, LocalDate.now(), LocalDate.now().plusDays(14),
                LocalTime.of(10, 0), LocalTime.of(18, 0),
                Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                3L, "Different comment", false);

        assertEquals(authRange1, authRange2);
        assertNotEquals(authRange1, authRange3);
        assertEquals(authRange1.hashCode(), authRange2.hashCode());
        assertNotEquals(authRange1.hashCode(), authRange3.hashCode());
    }

    @Test
    void testToString() {
        AuthRange authRange = new AuthRange(1L, LocalDate.now(), LocalDate.now().plusDays(7),
                LocalTime.of(9, 0), LocalTime.of(17, 0),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                2L, "Test comment", true);

        String toString = authRange.toString();

        assertTrue(toString.contains("authRangeId=1"));
        assertTrue(toString.contains("plotId=2"));
        assertTrue(toString.contains("comment=Test comment"));
        assertTrue(toString.contains("isActive=true"));
    }
}