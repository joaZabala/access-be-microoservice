package ar.edu.utn.frc.tup.lc.iv.clients;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {
    /**
     * Mapper for converting between models and DTOs.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Fetches user details by ID.
     *
     * @param id the user ID.
     * @return the UserDto if found.
     * @throws IllegalArgumentException if the user does not exist.
     */
    public UserDto getUserById(Long id) {

        ResponseEntity<UserDto> response =
                restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/" + id, UserDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else if (Objects.isNull(response.getBody())) {
            throw new EntityNotFoundException("El usuario con el id " + id + " no existe");
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

    }
}
