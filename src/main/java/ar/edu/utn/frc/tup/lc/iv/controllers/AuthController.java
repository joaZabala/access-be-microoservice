package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;

import java.util.List;

@RestController
@RequestMapping("/auths")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @GetMapping
    public List<AuthDTO> getAuth(@RequestParam Long docNumber) {
        return authService.getAuthsByDocNumber(docNumber); 
    }

    @PostMapping()
    public ResponseEntity<AuthDTO> authorizeVisitor(@RequestBody VisitorAuthRequest visitorAuthRequest) {
        return ResponseEntity.ok(authService.authorizeVisitor(visitorAuthRequest));
    }
}
