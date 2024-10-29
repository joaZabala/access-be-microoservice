package ar.edu.utn.frc.tup.lc.iv.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
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


    @GetMapping
    public List<AuthRangeDTO> getAuthRanges(@RequestParam(required = true) VisitorType visitorType, @RequestParam(required = true) Long docNumber, @RequestParam(required = true) Long plotId) {
        List<AuthRangeDTO> dtos = authRangeService.getAuthRanges(visitorType, docNumber, plotId);

        return dtos;
    }

    @DeleteMapping
    public ResponseEntity<AuthRangeDTO> deleteAuthRange(@RequestParam(required = true) Long id) {
        return ResponseEntity.ok(authRangeService.deleteAuthRange(id));
    }

    @PutMapping
    public ResponseEntity<AuthRangeDTO> updateAuthRange(@RequestParam(required = true) Long authId, @RequestBody AuthRangeRequestDTO request, @RequestHeader("x-user-id") Long id) {
        return ResponseEntity.ok(authRangeService.updateAuthRange(authId, request));
    }
    


}
