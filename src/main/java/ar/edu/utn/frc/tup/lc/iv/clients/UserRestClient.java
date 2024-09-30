package ar.edu.utn.frc.tup.lc.iv.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
     * name of circuit breaker.
     */
    private static final String INSTANCE_NAME = "user-service";

    /**
     *  name of circuit breaker fallback method.
     */
    private static  final String FALLBACK_METHOD = "fallback";

    /**
     * Fetches user details by ID.
     *
     * @param id the user ID.
     * @return the UserDto if found.
     * @throws IllegalArgumentException if the user does not exist.
     */
    @CircuitBreaker(name = INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public UserDto getUserById(Long id) {

        ResponseEntity<UserDto> response =
                restTemplate.getForEntity(userServiceUrl + "/" + id, UserDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new EntityNotFoundException("El usuario con el id " + id + " no existe");
        }
    }

    /**
     * @param ex exeption captured.
     * @return response entity with error.
     */
    public ResponseEntity<String> fallback(EntityNotFoundException ex) {
        System.out.println("CircuitBreaker Fallback: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Circuit breaker fallback method.
     * @param ex exeption captured.
     * @return response entity with error.
     */
    public ResponseEntity<String> fallback(Exception ex) {
        System.out.println("CircuitBreaker Fallback (General): " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Servicio no disponible, por favor intente más tarde.");
    }

    /**
     * fetches all users details.
     *
     * @return list of users.
     */
    public List<UserDto> getAllUsers() {

        ResponseEntity<UserDto[]> users = restTemplate.getForEntity(userServiceUrl, UserDto[].class);

        if (users.getStatusCode().is2xxSuccessful() && users.getBody() != null) {

            return List.of(users.getBody());
        } else {
            throw new EntityNotFoundException("No existen usuarios");
        }
    }
}
