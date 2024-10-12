package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;

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
    private AuthService authService;

    /**
     *  Retrieve authorizations for authorized persons by document number.
     * @param docNumber document number.
     * @return list of authorizations.
     */
    @GetMapping
    public List<AuthDTO> getAuth(@RequestParam Long docNumber) {
        return authService.getAuthsByDocNumber(docNumber); 
    }

    /**
     * Authorize visitor with authorized ranges.
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    @PostMapping()
    public ResponseEntity<AuthDTO> authorizeVisitor(@RequestBody VisitorAuthRequest visitorAuthRequest) {
        return ResponseEntity.ok(authService.authorizeVisitor(visitorAuthRequest));
    }
}
