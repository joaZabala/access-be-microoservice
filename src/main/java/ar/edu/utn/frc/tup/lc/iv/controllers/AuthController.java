package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
     * @param docNumber document number.
     * @return list of authorizations.
     */
    @GetMapping
    public List<AuthDTO> getAuth(@RequestParam(required = false) Long docNumber) {

        if (docNumber == null) {
            return authService.getAllAuths();
        }
        return authService.getAuthsByDocNumber(docNumber);
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
     * @param creatorID request.
     * @return authorization created.
     */
    @PostMapping("/authorization")
    public ResponseEntity<AuthDTO> createAuthorization(@RequestBody VisitorAuthRequest visitorAuthRequest, @RequestParam Long creatorID) {
        return ResponseEntity.ok(authService.createAuthorization(visitorAuthRequest, creatorID));
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
}
