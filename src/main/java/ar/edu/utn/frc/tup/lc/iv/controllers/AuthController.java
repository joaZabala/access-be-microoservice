package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
