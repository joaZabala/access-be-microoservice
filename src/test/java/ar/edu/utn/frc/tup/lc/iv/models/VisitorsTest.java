package ar.edu.utn.frc.tup.lc.iv.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsTest {

    @Test
    void testNoArgsConstructor() {
        Visitors visitor = new Visitors();

        assertNotNull(visitor);
        assertNull(visitor.getVisitorId());
        assertNull(visitor.getName());
        assertNull(visitor.getLastName());
        assertNull(visitor.getDocNumber());
        assertNull(visitor.getBirthDate());
        assertNull(visitor.getOwnerId());
        assertFalse(visitor.isActive());
    }

    @Test
    void testAllArgsConstructor() {
        Long visitorId = 1L;
        String name = "John";
        String lastName = "Doe";
        Long docNumber = 12345678L;
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Long ownerId = 2L;
        boolean isActive = true;

        Visitors visitor = new Visitors(
                visitorId,
                name,
                lastName,
                docNumber,
                birthDate,
                ownerId,
                isActive
        );

        assertEquals(visitorId, visitor.getVisitorId());
        assertEquals(name, visitor.getName());
        assertEquals(lastName, visitor.getLastName());
        assertEquals(docNumber, visitor.getDocNumber());
        assertEquals(birthDate, visitor.getBirthDate());
        assertEquals(ownerId, visitor.getOwnerId());
        assertTrue(visitor.isActive());
    }

    @Test
    void testSettersAndGetters() {
        Visitors visitor = new Visitors();
        Long visitorId = 1L;
        String name = "John";
        String lastName = "Doe";
        Long docNumber = 12345678L;
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Long ownerId = 2L;
        boolean isActive = true;

        visitor.setVisitorId(visitorId);
        visitor.setName(name);
        visitor.setLastName(lastName);
        visitor.setDocNumber(docNumber);
        visitor.setBirthDate(birthDate);
        visitor.setOwnerId(ownerId);
        visitor.setActive(isActive);

        assertEquals(visitorId, visitor.getVisitorId());
        assertEquals(name, visitor.getName());
        assertEquals(lastName, visitor.getLastName());
        assertEquals(docNumber, visitor.getDocNumber());
        assertEquals(birthDate, visitor.getBirthDate());
        assertEquals(ownerId, visitor.getOwnerId());
        assertTrue(visitor.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Visitors visitor1 = new Visitors(1L, "John", "Doe", 12345678L,
                birthDate, 2L, true);
        Visitors visitor2 = new Visitors(1L, "John", "Doe", 12345678L,
                birthDate, 2L, true);
        Visitors visitor3 = new Visitors(2L, "Jane", "Smith", 87654321L,
                LocalDate.of(1995, 5, 5), 3L, false);

        assertEquals(visitor1, visitor2);
        assertNotEquals(visitor1, visitor3);
        assertEquals(visitor1.hashCode(), visitor2.hashCode());
        assertNotEquals(visitor1.hashCode(), visitor3.hashCode());
    }

    @Test
    void testToString() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Visitors visitor = new Visitors(1L, "John", "Doe", 12345678L,
                birthDate, 2L, true);

        String toString = visitor.toString();

        assertTrue(toString.contains("visitorId=1"));
        assertTrue(toString.contains("name=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("docNumber=12345678"));
        assertTrue(toString.contains("birthDate=" + birthDate));
        assertTrue(toString.contains("ownerId=2"));
        assertTrue(toString.contains("isActive=true"));
    }

    @Test
    void testIsActiveDefaultValue() {
        Visitors visitor = new Visitors();

        assertFalse(visitor.isActive());
    }

    @Test
    void testSetActiveAndIsActive() {
        Visitors visitor = new Visitors();
        
        assertFalse(visitor.isActive());

        visitor.setActive(true);
        assertTrue(visitor.isActive());

        visitor.setActive(false);
        assertFalse(visitor.isActive());
    }
}