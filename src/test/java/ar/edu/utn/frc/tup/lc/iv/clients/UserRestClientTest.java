package ar.edu.utn.frc.tup.lc.iv.clients;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserRestClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private UserRestClient userRestClient;
    @Test
    void getUserById() {

        UserDto userDto = new UserDto(1L , "LMaldonado");

        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/1" , UserDto.class)).thenReturn(ResponseEntity.ok(userDto));

        UserDto userDtoResult = userRestClient.getUserById(1L);

        Assertions.assertEquals(userDtoResult.getUserName() , userDto.getUserName());
        Assertions.assertEquals(userDtoResult.getId() , userDto.getId());
    }

    @Test
    void getUserByIdNotFoundExceptionWhenBodyIsNull() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUserName("LMaldonado");

        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/" + userDto.getId(), UserDto.class))
                .thenReturn(ResponseEntity.ok(null));


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getUserById(1L);
        });

        Assertions.assertEquals("El usuario con el id " + userDto.getId() + " no existe", exception.getMessage());
    }

}