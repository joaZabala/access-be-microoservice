package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 */
@RestController
@RequestMapping("/auths")
public class AuthController {

    /**
     * Service for managing authorized persons.
     */
    @Autowired
    private IAuthService authService;

    /**
     * Retrieve authorizations for authorized persons by document number.
     *
     * @param filter object with filters.
     * @return list of authorizations.
     */
    @GetMapping
    public List<AuthDTO> getAuth(@Valid AuthFilter filter) {
        return authService.getAllAuths(filter);
    }

    /**
     * retrieve valid authorizations for authorized persons
     * by document number.
     *
     * @param docNumber number of document.
     * @return list of valid authorizations.
     */
    @GetMapping("/valid")
    public List<AuthDTO> getValidAuths(@RequestParam Long docNumber) {
        return authService.getValidAuthsByDocNumber(docNumber);
    }

    /**
     * Authorize visitor with authorized ranges.
     * @param visitorAuthRequest request.
     * @param userId             request.
     * @return authorization created.
     */
    @PostMapping("/authorization")
    public ResponseEntity<AuthDTO> createAuthorization(@RequestBody VisitorAuthRequest visitorAuthRequest,
            @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.createAuthorization(visitorAuthRequest, userId));
    }

    /**
     * check if the visitor have access.
     *
     * @param docNumber request.
     * @return authorization created.
     */
    @GetMapping("/authorization/{docNumber}")
    public ResponseEntity<Boolean> checkAuthorization(@PathVariable Long docNumber) {
        return ResponseEntity.ok(authService.isAuthorized(docNumber));
    }
    /**
     * Deletes the authorization.
     *
     * @param authId the ID of the authorization to delete
     * @param userId the ID of the user requesting the deletion
     * @return ResponseEntity containing the deleted {@link AuthDTO}
     */
    @DeleteMapping("/authorization")
    public ResponseEntity<AuthDTO> deleteAuthorization(@RequestHeader("auth-id") Long authId,
                                                       @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.deleteAuthorization(authId));
    }
    /**
     * Activates the authorization.
     *
     * @param authId the ID of the authorization to activate
     * @param userId the ID of the user requesting the activation
     * @return ResponseEntity containing the activated {@link AuthDTO}
     */
    @PutMapping("/authorization/activate")
    public ResponseEntity<AuthDTO> activateAuthorization(@RequestHeader("auth-id") Long authId,
                                                         @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.activateAuthorization(authId));
    }

}


