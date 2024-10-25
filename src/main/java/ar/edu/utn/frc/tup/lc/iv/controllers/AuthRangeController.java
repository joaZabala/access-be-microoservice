package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthRangeService;

/**
 * Controller class for managing authorized ranges.
 */
@RestController
@RequestMapping("/auth-ranges")
public class AuthRangeController {

    /**
     * Service for managing authorized ranges.
     */
    @Autowired
    private AuthRangeService authRangeService;
}
