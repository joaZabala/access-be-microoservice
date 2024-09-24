package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Creates a new visitor or update person already existing.
     *
     * @param visitorRequest The DTO containing the details of
     *                       the authorized person to create.
     * @return VisitorDto
     */
    @PostMapping("/upsert")
    public ResponseEntity<VisitorDTO> createVisitor(@RequestBody VisitorRequestDto visitorRequest) {
        return ResponseEntity.ok(visitorService.saveOrUpdateVisitor(visitorRequest));
    }

    /**
     * Retrieves a list of all visitors.
     *
     * @param page the page number for pagination
     * @param size the size of the page
     * @return a list of VisitorDTO objects
     */
    @GetMapping("/getAll")
    public List<VisitorDTO> getAllVisitors(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return visitorService.getAllVisitors(page, size);
    }

    /**
     * Retrieves a specific authorized person by their ID.
     *
     * @param visitorId The unique identifier of the authorized person.
     * @return The AuthorizedDto object representing the authorized
     * person with the specified ID.
     */
    @GetMapping("/{visitorId}")
    public VisitorDTO getAuthorizedById(@PathVariable Long visitorId) {
        return null;
    }

    /**
     * Updates an existing authorized person by their ID.
     *
     * @param visitorId  The unique identifier of the authorized
     *                   person to update.
     * @param visitorDto The DTO containing the updated
     *                   details of the authorized person.
     */
    @PutMapping("/{visitorId}")
    public void updateAuthorized(@PathVariable Long visitorId, @RequestBody VisitorDTO visitorDto) {
        // No implementation
    }
}
