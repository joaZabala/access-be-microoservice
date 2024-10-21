package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Retrieves all access records, optionally filtered by
     * visitor type and external ID.
     * @param visitorType Type of the visitor (optional).
     * @param externalID External identifier (optional).
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

    /**
     * Retrieves access records where exits are missing.
     * @return List of AccessDTOs with missing exits.
     */
    @GetMapping("/missing-exits")
    public List<AccessDTO> getMissingExits() {
        return accessesService.getMissingExits();
    }
}
