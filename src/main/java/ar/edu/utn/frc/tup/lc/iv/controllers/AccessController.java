package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for managing access-related operations.
 * Provides endpoints for retrieving access records
 * based on visitor type, external ID,
 * and different access states (entries, exits, missing exits).
 */
@RestController
@RequestMapping("/access")
public class AccessController {

    /** Service for handling access data operations. */
    @Autowired
    private IAccessesService accessesService;
    /** Service for handling auth data operations. */
    @Autowired
    private AuthService authService;

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param accessDTO request.
     * @param userId creator.
     * @return authorization created.
     */
    @PostMapping("/authorize")
    public ResponseEntity<AccessDTO> authorizeVisitor(@RequestBody AccessDTO accessDTO,  @RequestHeader("x-user-id") Long userId) {
        AccessDTO access = authService.authorizeVisitor(accessDTO, userId);
        return access == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(access);
    }

    /**
     * Retrieves all access records, optionally filtered by
     * visitor type and external ID.
     * @param visitorType Type of the visitor (optional).
     * @param externalID  External identifier (optional).
     * @return List of AccessDTOs.
     */
    @GetMapping
    public List<AccessDTO> getAllAccess(@RequestParam(required = false) VisitorType visitorType,
            @RequestParam(required = false) Long externalID) {
        if (visitorType != null) {
            if (externalID != null) {
                return accessesService.getAllAccessByTypeAndExternalID(visitorType, externalID);
            }
            return accessesService.getAllAccessByType(visitorType);
        }
        return accessesService.getAllAccess();
    }

    /**
     * Retrieves all access entries.
     * @return List of AccessDTOs representing entries.
     */
    @GetMapping("/entries")
    public List<AccessDTO> getAllEntries() {
        return accessesService.getAllEntries();
    }

    /**
     * Retrieves all access exits.
     * @return List of AccessDTOs representing exits.
     */
    @GetMapping("/exits")
    public List<AccessDTO> getAllExits() {
        return accessesService.getAllExits();
    }


    //TODO Implement missing exits.

//    @GetMapping("/missing-exits")
//    public List<AccessDTO> getMissingExits() {
//        return accessesService.getMissingExits();
//    }

}
