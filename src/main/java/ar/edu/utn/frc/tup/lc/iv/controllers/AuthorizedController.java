package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthorizedDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthorizedService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * Controller class for managing authorized persons.
 * This class provides endpoints to create, retrieve,
 * and update authorized persons.
 */
@RestController
@RequestMapping("/authorized")
public class AuthorizedController {


    /**
     * Authorized Service dependency injection.
     */
    @Autowired
    private AuthorizedService authorizedService;

    /**
     * Creates a new authorized person.
     * @param authorizedDto The DTO containing the details of
     * the authorized person to create.
     */
    @PostMapping
    public void createAuthorized(@RequestBody AuthorizedDTO authorizedDto) {
        // No implementation
    }

    /**
     * Retrieves a list of all authorized persons.
     * @return A list of AuthorizedDto objects representing
     * all authorized persons.
     */
    @GetMapping
    public List<AuthorizedDTO> getAllAuthorized() {
        return authorizedService.getAuthorized();
    }

    /**
     * Retrieves a specific authorized person by their ID.
     * @param authId The unique identifier of the authorized person.
     * @return The AuthorizedDto object representing the authorized
     * person with the specified ID.
     */
    @GetMapping("/{authId}")
    public AuthorizedDTO getAuthorizedById(@PathVariable Long authId) {
        return null;
    }

    /**
     * Updates an existing authorized person by their ID.
     * @param authId The unique identifier of the authorized
     * person to update.
     * @param authorizedDto The DTO containing the updated
     * details of the authorized person.
     */
    @PutMapping("/{authId}")
    public void updateAuthorized(@PathVariable Long authId, @RequestBody AuthorizedDTO authorizedDto) {
        // No implementation
    }
}
