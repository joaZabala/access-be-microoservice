package ar.edu.utn.frc.tup.lc.iv.models;


import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccessesTest {

    @Test
    void testNoArgsConstructor() {
        // When
        Accesses access = new Accesses();

        // Then
        assertNotNull(access);
        assertNull(access.getAccessId());
        assertNull(access.getAuthRangeId());
        assertNull(access.getEntryDate());
        assertNull(access.getExitDate());
        assertNull(access.getVehicleType());
        assertNull(access.getVehicleReg());
        assertNull(access.getVehicleDescription());
        assertNull(access.getVisitorId());
        assertNull(access.getComments());
    }

    @Test
    void testAllArgsConstructor() {
        Long accessId = 1L;
        Long authRangeId = 2L;
        LocalDateTime entryDate = LocalDateTime.now();
        LocalDateTime exitDate = LocalDateTime.now().plusHours(2);
        VehicleTypes vehicleType = VehicleTypes.CAR;
        String vehicleReg = "ABC123";
        String vehicleDescription = "Red Toyota";
        Long visitorId = 3L;
        String comments = "Test comments";

        Accesses access = new Accesses(
                accessId,
                authRangeId,
                entryDate,
                exitDate,
                vehicleType,
                vehicleReg,
                vehicleDescription,
                visitorId,
                comments
        );

        assertEquals(accessId, access.getAccessId());
        assertEquals(authRangeId, access.getAuthRangeId());
        assertEquals(entryDate, access.getEntryDate());
        assertEquals(exitDate, access.getExitDate());
        assertEquals(vehicleType, access.getVehicleType());
        assertEquals(vehicleReg, access.getVehicleReg());
        assertEquals(vehicleDescription, access.getVehicleDescription());
        assertEquals(visitorId, access.getVisitorId());
        assertEquals(comments, access.getComments());
    }

    @Test
    void testSettersAndGetters() {
        Accesses access = new Accesses();
        Long accessId = 1L;
        Long authRangeId = 2L;
        LocalDateTime entryDate = LocalDateTime.now();
        LocalDateTime exitDate = LocalDateTime.now().plusHours(2);
        VehicleTypes vehicleType = VehicleTypes.CAR;
        String vehicleReg = "ABC123";
        String vehicleDescription = "Red Toyota";
        Long visitorId = 3L;
        String comments = "Test comments";

        access.setAccessId(accessId);
        access.setAuthRangeId(authRangeId);
        access.setEntryDate(entryDate);
        access.setExitDate(exitDate);
        access.setVehicleType(vehicleType);
        access.setVehicleReg(vehicleReg);
        access.setVehicleDescription(vehicleDescription);
        access.setVisitorId(visitorId);
        access.setComments(comments);

        assertEquals(accessId, access.getAccessId());
        assertEquals(authRangeId, access.getAuthRangeId());
        assertEquals(entryDate, access.getEntryDate());
        assertEquals(exitDate, access.getExitDate());
        assertEquals(vehicleType, access.getVehicleType());
        assertEquals(vehicleReg, access.getVehicleReg());
        assertEquals(vehicleDescription, access.getVehicleDescription());
        assertEquals(visitorId, access.getVisitorId());
        assertEquals(comments, access.getComments());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Accesses access1 = new Accesses(1L, 2L, now, now.plusHours(2),
                VehicleTypes.CAR, "ABC123", "Red Toyota", 3L, "Test comments");
        Accesses access2 = new Accesses(1L, 2L, now, now.plusHours(2),
                VehicleTypes.CAR, "ABC123", "Red Toyota", 3L, "Test comments");
        Accesses access3 = new Accesses(2L, 3L, now, now.plusHours(2),
                VehicleTypes.TRUCK, "XYZ789", "Blue Honda", 4L, "Different comments");

        assertEquals(access1, access2);
        assertNotEquals(access1, access3);
        assertEquals(access1.hashCode(), access2.hashCode());
        assertNotEquals(access1.hashCode(), access3.hashCode());
    }

    @Test
    void testToString() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Accesses access = new Accesses(1L, 2L, now, now.plusHours(2),
                VehicleTypes.CAR, "ABC123", "Red Toyota", 3L, "Test comments");

        // When
        String toString = access.toString();

        // Then
        assertTrue(toString.contains("accessId=1"));
        assertTrue(toString.contains("authRangeId=2"));
        assertTrue(toString.contains("vehicleType=CAR"));
        assertTrue(toString.contains("vehicleReg=ABC123"));
        assertTrue(toString.contains("vehicleDescription=Red Toyota"));
        assertTrue(toString.contains("visitorId=3"));
        assertTrue(toString.contains("comments=Test comments"));
    }
}