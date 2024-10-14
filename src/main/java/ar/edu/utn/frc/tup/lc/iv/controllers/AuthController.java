package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private IAuthService authService;

    /**
     *  Retrieve authorizations for authorized persons by document number.
     * @param docNumber document number.
     * @return list of authorizations.
     */
    @GetMapping
    public List<AuthDTO> getAuth(@RequestParam(required = false) Long docNumber) {

        if(docNumber == null) {
            return authService.getAllAuths();
        }
        return authService.getAuthsByDocNumber(docNumber); 
    }

    @GetMapping("/valid")
    public List<AuthDTO> getValidAuths(@RequestParam Long docNumber) {
        return authService.getValidAuthsByDocNumber(docNumber);
    }

    /**
     * Authorize visitor with authorized ranges.
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    @PostMapping("/authorize")
    public ResponseEntity<AuthDTO> authorizeVisitor(@RequestBody VisitorAuthRequest visitorAuthRequest) {
        return ResponseEntity.ok(authService.authorizeVisitor(visitorAuthRequest));
    }
}
