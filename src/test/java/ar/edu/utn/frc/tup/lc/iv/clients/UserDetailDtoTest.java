package ar.edu.utn.frc.tup.lc.iv.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class UserDetailDtoTest {
    private UserDetailDto userDetail;

    @BeforeEach
    void setUp() {
        userDetail = new UserDetailDto();
    }

    @Test
    void testSettersAndGetters() {
        userDetail.setId(1L);
        assertEquals(Long.valueOf(1), userDetail.getId());
        userDetail.setFirstName("John");
        assertEquals("John", userDetail.getFirstName());
        userDetail.setLastName("Doe");
        assertEquals("Doe", userDetail.getLastName());
        userDetail.setUserName("johndoe");
        assertEquals("johndoe", userDetail.getUserName());
        userDetail.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", userDetail.getEmail());
        userDetail.setActive(true);
        assertTrue(userDetail.isActive());
        userDetail.setOwnerId(1001L);
        assertEquals(Long.valueOf(1001), userDetail.getOwnerId());
        userDetail.setPlotId(2001L);
        assertEquals(Long.valueOf(2001), userDetail.getPlotId());
        AddressDTO address = new AddressDTO(1L, "123 Main St", 100, 2, "A", "City", "Province", "Country", 12345);
        userDetail.setAddresses(List.of(address));
        assertEquals(1, userDetail.getAddresses().size());
        assertEquals(address, userDetail.getAddresses().get(0));
        ContactDTO contact = new ContactDTO(1L, "email", "john.doe@example.com", List.of("sub1", "sub2"));
        userDetail.setContacts(List.of(contact));
        assertEquals(1, userDetail.getContacts().size());
        assertEquals(contact, userDetail.getContacts().get(0));
        RoleDTO role = new RoleDTO(1L, 2L,"Admin", "Admin", "Admin", true);
        userDetail.setRoles(List.of(role));
        assertEquals(1, userDetail.getRoles().size());
        assertEquals(role, userDetail.getRoles().get(0));

        RoleDTO role1 = new RoleDTO();
        assertNotNull(role1);

        role1.setId(1L);
        role1.setCode(1L);
        role1.setName("A");
        role1.setDescription("A");
        role1.setIsActive(true);
        role1.setPrettyName("A");
        assertEquals(1L, role1.getId());
        assertEquals(1L, role1.getCode());
        assertEquals("A", role1.getName());
        assertEquals("A", role1.getDescription());
        assertEquals("A", role1.getPrettyName());
        assertTrue(role1.getIsActive());
    }
    @Test
    void testContactDTOSettersAndGetters() {
        ContactDTO contact = new ContactDTO();
        assertNotNull(contact);
        contact.setId(1L);
        assertEquals(Long.valueOf(1), contact.getId());

        contact.setContactType("email");
        assertEquals("email", contact.getContactType());

        contact.setContactValue("user@example.com");
        assertEquals("user@example.com", contact.getContactValue());

        contact.setSubscriptions(Arrays.asList("sub1", "sub2"));
        assertEquals(2, contact.getSubscriptions().size());
        assertEquals("sub1", contact.getSubscriptions().get(0));
        assertEquals("sub2", contact.getSubscriptions().get(1));
    }

    void testAddressDTOSettersAndGetters() {

        AddressDTO address = new AddressDTO();

        assertNotNull(address);

        address.setId(1L);
        assertEquals(Long.valueOf(1), address.getId());

        address.setStreetAddress("123 Main St");
        assertEquals("123 Main St", address.getStreetAddress());

        address.setNumber(100);
        assertEquals(Integer.valueOf(100), address.getNumber());

        address.setFloor(2);
        assertEquals(Integer.valueOf(2), address.getFloor());

        address.setApartment("A");
        assertEquals("A", address.getApartment());

        address.setCity("City");
        assertEquals("City", address.getCity());

        address.setProvince("Province");
        assertEquals("Province", address.getProvince());

        address.setCountry("Country");
        assertEquals("Country", address.getCountry());

        address.setPostalCode(12345);
        assertEquals(Integer.valueOf(12345), address.getPostalCode());
    }

    @Test
    void testUserDtoSettersAndGetters() {
        UserDto user = new UserDto();
        assertNotNull(user);

        user.setId(1L);
        assertEquals(Long.valueOf(1), user.getId());

        user.setUserName("testUser");
        assertEquals("testUser", user.getUserName());
    }
}
