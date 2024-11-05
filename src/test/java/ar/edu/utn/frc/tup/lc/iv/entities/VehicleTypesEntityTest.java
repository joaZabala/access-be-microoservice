package ar.edu.utn.frc.tup.lc.iv.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTypesEntityTest {

    @Test
    void testNoArgsConstructor() {
        VehicleTypesEntity vehicleType = new VehicleTypesEntity();

        assertNotNull(vehicleType);
        assertNull(vehicleType.getVehicleTypeId());
        assertNull(vehicleType.getCarDescription());
    }

    @Test
    void testAllArgsConstructor() {
        Long vehicleTypeId = 1L;
        String carDescription = "Test Car Description";

        VehicleTypesEntity vehicleType = new VehicleTypesEntity(vehicleTypeId, carDescription);

        assertEquals(vehicleTypeId, vehicleType.getVehicleTypeId());
        assertEquals(carDescription, vehicleType.getCarDescription());
    }

    @Test
    void testSettersAndGetters() {
        VehicleTypesEntity vehicleType = new VehicleTypesEntity();
        Long vehicleTypeId = 1L;
        String carDescription = "Test Car Description";

        vehicleType.setVehicleTypeId(vehicleTypeId);
        vehicleType.setCarDescription(carDescription);

        assertEquals(vehicleTypeId, vehicleType.getVehicleTypeId());
        assertEquals(carDescription, vehicleType.getCarDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        VehicleTypesEntity vehicleType1 = new VehicleTypesEntity(1L, "Test Car Description");
        VehicleTypesEntity vehicleType2 = new VehicleTypesEntity(1L, "Test Car Description");
        VehicleTypesEntity vehicleType3 = new VehicleTypesEntity(2L, "Different Car Description");

        assertEquals(vehicleType1, vehicleType2);
        assertNotEquals(vehicleType1, vehicleType3);
        assertEquals(vehicleType1.hashCode(), vehicleType2.hashCode());
        assertNotEquals(vehicleType1.hashCode(), vehicleType3.hashCode());
    }

    @Test
    void testToString() {
        VehicleTypesEntity vehicleType = new VehicleTypesEntity(1L, "Test Car Description");

        String toString = vehicleType.toString();

        assertTrue(toString.contains("vehicleTypeId=1"));
        assertTrue(toString.contains("carDescription=Test Car Description"));
    }

    @Test
    void testDescriptionMaxLength() {
        assertEquals(100, VehicleTypesEntity.DESCRIPTION_MAX_LENGTH);
    }
}