package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 * This class provides endpoints to create, retrieve,
 * and update authorized persons.
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    /**
     * Authorized Service dependency injection.
     */
    @Autowired
    private VisitorService visitorService;

    /**
     * Retrieves a list of all visitors.
     *
     * @param page the page number for pagination
     * @param size the size of the page
     * @return a list of VisitorDTO objects
     */
    @GetMapping()
    public List<VisitorDTO> getAllVisitors(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return visitorService.getAllVisitors(page, size);
    }

    /**
     * Retrieves a specific authorized person by their ID.
     *
     * @param docNumber The identifier of the visitor person.
     * @return The VisitorDto object representing the authorized
     * person with the specified ID.
     */
    @GetMapping("/{docNumber}")
    public VisitorDTO getVisitorByDocNumber(@PathVariable Long docNumber) {
        return null;
    }

    /**
     * Updates an existing visitor or create a new visitor.
     *
     * @param visitorRequest The DTO containing the details
     *                       to create or update Visitor.
     * @return VisitorDto
     */
    @PutMapping()
    public ResponseEntity<VisitorDTO> updateAuthorized(@RequestBody VisitorRequestDto visitorRequest) {
        return ResponseEntity.ok(visitorService.saveOrUpdateVisitor(visitorRequest));
    }
}
