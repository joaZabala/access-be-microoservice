package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 */
@RestController
@RequestMapping("/auths")
@Tag(name = "Authorization Management", description = "APIs for managing visitor access authorizations")
public class AuthController {

    /**
     * Service for managing authorized persons.
     */
    @Autowired
    private IAuthService authService;

    /**
     * Retrieve authorizations for authorized persons by document number.
     *
     * @param filter object with filters.
     * @param page   The page number for pagination (default is 0).
     * @param size   The number of records per page (default is 10).
     * @return list of authorizations.
     */
    @Operation(summary = "Get all neighborhood access authorizations",
        description = "Retrieves a paginated list of visitor access authorizations based on specified filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Successfully retrieved access authorizations",
                content = @Content(schema = @Schema(implementation = AuthDTO.class))),
    })
    @GetMapping
    public List<AuthDTO> getAuth(@Valid AuthFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return authService.getAllAuths(filter, page, size);
    }

    /**
     * retrieve valid authorizations for authorized persons
     * by document number.
     *
     * @param docNumber number of document.
     * @return list of valid authorizations.
     */

    @Operation(summary = "Get valid neighborhood access authorizations by document number",
        description = "Retrieves all active and valid access permits for a visitor identified by their document number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved valid access authorizations",
                content = @Content(schema = @Schema(implementation = AuthDTO.class))),
            @ApiResponse(responseCode = "404", description = "Visitor with document number not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/valid")
    public List<AuthDTO> getValidAuths(@RequestParam Long docNumber) {
        return authService.getValidAuthsByDocNumber(docNumber);
    }

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @param userId             request.
     * @return authorization created.
     */

    @Operation(summary = "Create new neighborhood access authorization",
        description = "Creates a new authorization for a visitor to enter the private neighborhood during specified time ranges")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access authorization successfully created",
                content = @Content(schema = @Schema(implementation = AuthDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters or time ranges",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @PostMapping("/authorization")
    public ResponseEntity<AuthDTO> createAuthorization(@RequestBody VisitorAuthRequest visitorAuthRequest,
            @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.createAuthorization(visitorAuthRequest, userId));
    }

    /**
     * check if the visitor have access.
     *
     * @param docNumber request.
     * @return authorization created.
     */

    @Operation(summary = "Verify visitor's access permission",
        description = "Checks if a visitor has current valid authorization to enter the private neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access verification completed successfully",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Document number not found in the system",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/authorization/{docNumber}")
    public ResponseEntity<Boolean> checkAuthorization(@PathVariable Long docNumber) {
        return ResponseEntity.ok(authService.isAuthorized(docNumber));
    }

    /**
     * Deletes the authorization.
     *
     * @param authId the ID of the authorization to delete
     * @param userId the ID of the user requesting the deletion
     * @return ResponseEntity containing the deleted {@link AuthDTO}
     */

    @Operation(summary = "Delete a neighborhood access authorization",
        description = "Revokes and removes an existing access authorization for a visitor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access authorization successfully deleted",
                content = @Content(schema = @Schema(implementation = AuthDTO.class))),
            @ApiResponse(responseCode = "404", description = "Authorization not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @DeleteMapping("/authorization")
    public ResponseEntity<AuthDTO> deleteAuthorization(@RequestHeader("auth-id") Long authId,
            @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.deleteAuthorization(authId));
    }

    /**
     * Activates the authorization.
     *
     * @param authId the ID of the authorization to activate
     * @param userId the ID of the user requesting the activation
     * @return ResponseEntity containing the activated {@link AuthDTO}
     */
    @PutMapping("/authorization/activate")
    public ResponseEntity<AuthDTO> activateAuthorization(@RequestHeader("auth-id") Long authId,
            @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authService.activateAuthorization(authId));
    }

}
