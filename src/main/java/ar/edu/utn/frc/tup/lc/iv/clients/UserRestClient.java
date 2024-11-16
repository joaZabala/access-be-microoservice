package ar.edu.utn.frc.tup.lc.iv.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
     * name of circuit breaker fallback method.
     */
    private static final String FALLBACK_METHOD = "fallback";

    /**
     * Fetches user details by ID.
     *
     * @param id the user ID.
     * @return the UserDto if found.
     * @throws IllegalArgumentException if the user does not exist.
     */
    // @CircuitBreaker(name = INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<UserDto> getUserById(Long id) {

        try {
            ResponseEntity<UserDto> response =
                    restTemplate.getForEntity(userServiceUrl + "/" + id, UserDto.class);

            if (response.getStatusCode().is4xxClientError()) {
                throw new EntityNotFoundException("El usuario con el id " + id + " no existe");
            } else {
                return response;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new EntityNotFoundException("El usuario con el id " + id + " no existe", e);
            } else {
                throw e;
            }
        }
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
    /**
     * fetches users by ids.
     * @param ids all ids for filter
     * @return list of users.
     */
    @CircuitBreaker(name = INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public List<UserDetailDto> getUsersByIds(List<Long> ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = userServiceUrl + "/users/byIds";

        HttpEntity<List<Long>> requestEntity = new HttpEntity<>(ids, headers);

        try {
            ResponseEntity<UserDetailDto[]> response = restTemplate.postForEntity(
                    url,
                    requestEntity,
                    UserDetailDto[].class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return List.of(response.getBody());
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            return new ArrayList<>();
        } //ver qeu hacer, si esto devuelve null, mostarlo en el front o no?
    }
    /**
     * fallback for fetches users by ids.
     * @param ids all ids for filter
     * @param e Exception
     * @return list of users.
     */
    public List<UserDetailDto> fallback(List<Long> ids, Exception e) {
        return new ArrayList<>();
    }
}
