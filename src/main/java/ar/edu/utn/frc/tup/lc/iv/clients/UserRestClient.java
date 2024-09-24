package ar.edu.utn.frc.tup.lc.iv.clients;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * UserService communicates with the user microservice.
 */
@NoArgsConstructor
@Service
public class UserRestClient {
    /**
     * Mapper for converting between models and DTOs.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Resource url for comunicate with users.
     */
    @Value("${user.service.url}")
    private String userServiceUrl;

    /**
     * Fetches user details by ID.
     *
     * @param id the user ID.
     * @return the UserDto if found.
     * @throws IllegalArgumentException if the user does not exist.
     */
    public UserDto getUserById(Long id) {

        ResponseEntity<UserDto> response =
                restTemplate.getForEntity(userServiceUrl + "/" + id, UserDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new EntityNotFoundException("El usuario con el id " + id + " no existe");
        }
    }
}
