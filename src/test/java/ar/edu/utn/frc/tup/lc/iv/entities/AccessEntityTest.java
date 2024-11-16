package ar.edu.utn.frc.tup.lc.iv.entities;

import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccessEntityTest {

    @Test
    void testNoArgsConstructor() {
        AccessEntity access = new AccessEntity();

        assertNotNull(access);
        assertNull(access.getAccessId());
        assertNull(access.getAuth());
        assertNull(access.getAction());
        assertNull(access.getActionDate());
        assertNull(access.getVehicleType());
        assertNull(access.getVehicleReg());
        assertNull(access.getVehicleDescription());
        assertNull(access.getPlotId());
        assertNull(access.getSupplierEmployeeId());
        assertNull(access.getComments());
    }

    @Test
    void testAllArgsConstructor() {
        Long accessId = 1L;
        AuthEntity auth = new AuthEntity();
        ActionTypes action = ActionTypes.ENTRY;
        LocalDateTime actionDate = LocalDateTime.now();
        VehicleTypes vehicleType = VehicleTypes.CAR;
        String vehicleReg = "ABC123";
        String vehicleDescription = "Red Car";
        Long plotId = 2L;
        Long supplierEmployeeId = 3L;
        String comments = "Test comments";

        AccessEntity access = new AccessEntity(
                accessId,
                auth,
                action,
                actionDate,
                vehicleType,
                vehicleReg,
                vehicleDescription,
                plotId,
                supplierEmployeeId,
                comments,
                false,
                false,
                false
        );

        assertEquals(accessId, access.getAccessId());
        assertEquals(auth, access.getAuth());
        assertEquals(action, access.getAction());
        assertEquals(actionDate, access.getActionDate());
        assertEquals(vehicleType, access.getVehicleType());
        assertEquals(vehicleReg, access.getVehicleReg());
        assertEquals(vehicleDescription, access.getVehicleDescription());
        assertEquals(plotId, access.getPlotId());
        assertEquals(supplierEmployeeId, access.getSupplierEmployeeId());
        assertEquals(comments, access.getComments());
    }

    @Test
    void testBuilder() {
        LocalDateTime actionDate = LocalDateTime.now();
        AuthEntity auth = new AuthEntity();

        AccessEntity access = AccessEntity.builder()
                .accessId(1L)
                .auth(auth)
                .action(ActionTypes.ENTRY)
                .actionDate(actionDate)
                .vehicleType(VehicleTypes.CAR)
                .vehicleReg("ABC123")
                .vehicleDescription("Red Car")
                .plotId(2L)
                .supplierEmployeeId(3L)
                .comments("Test comments")
                .build();

        assertEquals(1L, access.getAccessId());
        assertEquals(auth, access.getAuth());
        assertEquals(ActionTypes.ENTRY, access.getAction());
        assertEquals(actionDate, access.getActionDate());
        assertEquals(VehicleTypes.CAR, access.getVehicleType());
        assertEquals("ABC123", access.getVehicleReg());
        assertEquals("Red Car", access.getVehicleDescription());
        assertEquals(2L, access.getPlotId());
        assertEquals(3L, access.getSupplierEmployeeId());
        assertEquals("Test comments", access.getComments());
    }

    @Test
    void testSettersAndGetters() {
        AccessEntity access = new AccessEntity();
        AuthEntity auth = new AuthEntity();
        LocalDateTime actionDate = LocalDateTime.now();

        access.setAccessId(1L);
        access.setAuth(auth);
        access.setAction(ActionTypes.ENTRY);
        access.setActionDate(actionDate);
        access.setVehicleType(VehicleTypes.CAR);
        access.setVehicleReg("ABC123");
        access.setVehicleDescription("Red Car");
        access.setPlotId(2L);
        access.setSupplierEmployeeId(3L);
        access.setComments("Test comments");

        assertEquals(1L, access.getAccessId());
        assertEquals(auth, access.getAuth());
        assertEquals(ActionTypes.ENTRY, access.getAction());
        assertEquals(actionDate, access.getActionDate());
        assertEquals(VehicleTypes.CAR, access.getVehicleType());
        assertEquals("ABC123", access.getVehicleReg());
        assertEquals("Red Car", access.getVehicleDescription());
        assertEquals(2L, access.getPlotId());
        assertEquals(3L, access.getSupplierEmployeeId());
        assertEquals("Test comments", access.getComments());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime actionDate = LocalDateTime.now();
        AuthEntity auth = new AuthEntity();

        AccessEntity access1 = AccessEntity.builder()
                .accessId(1L)
                .auth(auth)
                .action(ActionTypes.ENTRY)
                .actionDate(actionDate)
                .vehicleType(VehicleTypes.CAR)
                .build();

        AccessEntity access2 = AccessEntity.builder()
                .accessId(1L)
                .auth(auth)
                .action(ActionTypes.ENTRY)
                .actionDate(actionDate)
                .vehicleType(VehicleTypes.CAR)
                .build();

        AccessEntity access3 = AccessEntity.builder()
                .accessId(2L)
                .action(ActionTypes.EXIT)
                .vehicleType(VehicleTypes.MOTORBIKE)
                .build();

        assertEquals(access1, access2);
        assertNotEquals(access1, access3);
        assertEquals(access1.hashCode(), access2.hashCode());
        assertNotEquals(access1.hashCode(), access3.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime actionDate = LocalDateTime.now();
        AuthEntity auth = new AuthEntity();

        AccessEntity access = AccessEntity.builder()
                .accessId(1L)
                .auth(auth)
                .action(ActionTypes.ENTRY)
                .actionDate(actionDate)
                .vehicleType(VehicleTypes.CAR)
                .vehicleReg("ABC123")
                .vehicleDescription("Red Car")
                .plotId(2L)
                .supplierEmployeeId(3L)
                .comments("Test comments")
                .build();

        String toString = access.toString();

        assertTrue(toString.contains("accessId=1"));
        assertTrue(toString.contains("action=ENTRY"));
        assertTrue(toString.contains("vehicleType=CAR"));
        assertTrue(toString.contains("vehicleReg=ABC123"));
        assertTrue(toString.contains("vehicleDescription=Red Car"));
        assertTrue(toString.contains("plotId=2"));
        assertTrue(toString.contains("supplierEmployeeId=3"));
        assertTrue(toString.contains("comments=Test comments"));
    }
}