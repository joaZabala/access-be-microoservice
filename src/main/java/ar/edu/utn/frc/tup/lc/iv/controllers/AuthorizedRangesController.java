package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthorizedRangesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing authorized ranges.
 * This class provides endpoints to create, retrieve,
 * update and authorized ranges.
 */
@RestController
@RequestMapping("/authorized-ranges")
public class AuthorizedRangesController {
    /**
     * Service for managing authorized ranges.
     * This service provides methods for handling
     * the business logic related to authorized ranges.
     */
    @Autowired
    private AuthorizedRangesService authorizedRangesService;

    /**
     * Endpoint to register a new authorized range.
     *
     * @param authorizedRangeDto the data transfer object containing the details
     *                           of the authorized range to be registered.
     * @return a ResponseEntity containing the status and any additional information.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthorizedRanges> registerAuthorizedRange(@RequestBody RegisterAuthorizationRangesDTO authorizedRangeDto) {
        AuthorizedRanges response = authorizedRangesService.registerAuthorizedRange(authorizedRangeDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Checks if a person with the given document number has a valid invitation.
     *
     * @param documentNumber the document number of the user to check
     * @return {@code ResponseEntity<Boolean>} returns {@code true} if a valid invitation exists,
     *         {@code false} otherwise, with an appropriate HTTP status code.
     */
    @GetMapping("{document-number}/valid")
    public ResponseEntity<Boolean> hasInvitationValid(@PathVariable("document-number") Long documentNumber) {
        Boolean hasInvitation = authorizedRangesService.HasInvitation(documentNumber);
        return new ResponseEntity<>(hasInvitation, HttpStatus.OK);
    }
}
