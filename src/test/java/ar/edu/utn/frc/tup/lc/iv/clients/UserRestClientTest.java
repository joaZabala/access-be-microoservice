package ar.edu.utn.frc.tup.lc.iv.clients;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRestClientTest {

    private final String userService = "http://host.docker.internal:8283/users";

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private UserRestClient userRestClient;
    @Test
    void getUserById() {
        UserDto userDto = new UserDto(1L , "LMaldonado");

        when(restTemplate.getForEntity(userService + "/1" , UserDto.class)).thenReturn(ResponseEntity.ok(userDto));

        UserDto userDtoResult = userRestClient.getUserById(1L).getBody();

        assertEquals(userDtoResult.getUserName() , userDto.getUserName());
        assertEquals(userDtoResult.getId() , userDto.getId());
    }

    @Test
    void getUserByIdNotFoundExceptionWhenBodyIsNull() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUserName("LMaldonado");

        when(restTemplate.getForEntity(userService + "/" + userDto.getId(), UserDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getUserById(1L);
        });

        assertEquals("El usuario con el id " + userDto.getId() + " no existe", exception.getMessage());
    }

    @Test
    void getUserByIdThrowsHttpClientErrorException() {
        // Configuramos el mock para lanzar la excepción cuando se llama al método
        when(restTemplate.getForEntity(userService + "/1", UserDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            userRestClient.getUserById(1L);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
    @Test
    void getUserByIdThrowsEntityNotFoundException() {
        when(restTemplate.getForEntity(userService + "/11", UserDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getUserById(11L);
        });

        assertEquals("El usuario con el id 11 no existe", exception.getMessage());
    }
    @Test
    void getAllUsers() {

        UserDto[] userDtos = {
                new UserDto(1L, "LMaldonado"),
                new UserDto(2L, "AMartinez")
        };

        when(restTemplate.getForEntity(userService, UserDto[].class))
                .thenReturn(ResponseEntity.ok(userDtos));

        List<UserDto> result = userRestClient.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("LMaldonado", result.get(0).getUserName());
        assertEquals("AMartinez", result.get(1).getUserName());
    }

    @Test
    void getAllUsersThrowsExceptionWhenNoUsersFound() {
        when(restTemplate.getForEntity(userService, UserDto[].class))
                .thenReturn(ResponseEntity.ok(null));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getAllUsers();
        });

        assertEquals("No existen usuarios", exception.getMessage());
    }

    @Test
    void testGetUsersByIds_SuccessfulResponse() {
        List<Long> ids = List.of(1L, 2L);

        AddressDTO address = new AddressDTO();
        address.setId(1L);
        address.setStreetAddress("123 Main St");
        address.setNumber(100);
        address.setFloor(2);
        address.setApartment("A");
        address.setCity("City");
        address.setProvince("Province");
        address.setCountry("Country");
        address.setPostalCode(12345);

        ContactDTO contact = new ContactDTO();
        contact.setId(1L);
        contact.setContactType("email");
        contact.setContactValue("user@example.com");
        contact.setSubscriptions(List.of("sub1", "sub2"));
        RoleDTO role = new RoleDTO(1L, 100L, "Admin", "Administrator", "Admin role", true);

        UserDetailDto[] userDetails = {
                new UserDetailDto(1L, "John", "Doe", "johndoe", "johndoe@example.com", true, 100L, 200L,
                        List.of(address), List.of(contact), List.of(role)),
                new UserDetailDto(2L, "Jane", "Doe", "janedoe", "janedoe@example.com", true, 101L, 201L,
                        List.of(address), List.of(contact), List.of(role))
        };
        ResponseEntity<UserDetailDto[]> responseEntity = new ResponseEntity<>(userDetails, HttpStatus.OK);

        when(restTemplate.postForEntity(
                eq(userService + "/users/byIds"),
                any(HttpEntity.class),
                eq(UserDetailDto[].class)
        )).thenReturn(responseEntity);

        List<UserDetailDto> result = userRestClient.getUsersByIds(ids);

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("123 Main St", result.get(0).getAddresses().get(0).getStreetAddress());
        assertEquals(100, result.get(0).getAddresses().get(0).getNumber());
        assertEquals(2, result.get(0).getAddresses().get(0).getFloor());
        assertEquals("A", result.get(0).getAddresses().get(0).getApartment());
        assertEquals("City", result.get(0).getAddresses().get(0).getCity());
        assertEquals("Province", result.get(0).getAddresses().get(0).getProvince());
        assertEquals("Country", result.get(0).getAddresses().get(0).getCountry());
        assertEquals(12345, result.get(0).getAddresses().get(0).getPostalCode());
        assertEquals("email", result.get(0).getContacts().get(0).getContactType());
        assertEquals("user@example.com", result.get(0).getContacts().get(0).getContactValue());
        assertEquals(List.of("sub1", "sub2"), result.get(0).getContacts().get(0).getSubscriptions());
        assertEquals("Admin", result.get(0).getRoles().get(0).getName());
    }

    @Test
    void testGetUsersByIds_EmptyResponse() {
        List<Long> ids = List.of(1L, 2L);

        UserDetailDto[] userDetails = {
        };
        ResponseEntity<UserDetailDto[]> responseEntity = new ResponseEntity<>(userDetails, HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(
                eq(userService + "/byIds"),
                any(HttpEntity.class),
                eq(UserDetailDto[].class)
        )).thenReturn(responseEntity);

        List<UserDetailDto> result = userRestClient.getUsersByIds(ids);

        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByIds_ExeptionResponse() {
        List<Long> ids = List.of(1L, 2L);

        UserDetailDto[] userDetails = {
        };
        ResponseEntity<UserDetailDto[]> responseEntity = new ResponseEntity<>(userDetails, HttpStatus.BAD_REQUEST);

        when(restTemplate.postForEntity(
                eq(userService + "/byIds"),
                any(HttpEntity.class),
                eq(UserDetailDto[].class)
        )).thenThrow(new RuntimeException("Simulated exception"));

        List<UserDetailDto> result = userRestClient.getUsersByIds(ids);

        assertEquals(0, result.size());
    }
}