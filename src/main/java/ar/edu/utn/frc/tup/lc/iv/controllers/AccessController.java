package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Neighborhood Access Control", description = "APIs for managing and monitoring vehicle entries and exits")
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
     * @param userId    creator.
     * @return authorization created.
     */
    @Operation(summary = "Authorize visitor access", description = "Register a new access for a visitor to enter or exit the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access authorization successfully created", content = @Content(schema = @Schema(implementation = AccessDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle or authorization details not found", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @PostMapping("/authorize")
    public ResponseEntity<AccessDTO> authorizeVisitor(@RequestBody AccessDTO accessDTO,
            @RequestHeader("x-user-id") Long userId) {
        AccessDTO access = authService.authorizeVisitor(accessDTO, userId);
        return access == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(access);
    }

    /**
     * Retrieves all access records, optionally filtered
     * by criteria specified in the filter object.
     * 
     * @param filter The filtering criteria
     * @param page   The page number for pagination (default is 0).
     * @param size   The number of records per page (default is 10).
     * @return A list of {@link AccessDTO} containing access records.
     */

    @Operation(summary = "Get all access records", description = "Retrieves a paginated list of visitor accesses records with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved access records", content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters provided", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping
    public PaginatedResponse<AccessDTO> getAllAccess(@Valid AccessesFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return accessesService.getAllAccess(filter, page, size);
    }

    /**
     * Retrieves all access entries.
     * 
     * @return List of AccessDTOs representing entries.
     */

    @Operation(summary = "Get all entry records", description = "Retrieves a list of all visitor entries into the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved entry records", content = @Content(schema = @Schema(implementation = AccessDTO.class)))
    })
    @GetMapping("/entries")
    public List<AccessDTO> getAllEntries() {
        return accessesService.getAllEntries();
    }

    /**
     * Retrieves all access exits.
     * 
     * @return List of AccessDTOs representing exits.
     */

    @Operation(summary = "Get all exit records", description = "Retrieves a list of all visitor exits from the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved exit records", content = @Content(schema = @Schema(implementation = AccessDTO.class)))
    })
    @GetMapping("/exits")
    public List<AccessDTO> getAllExits() {
        return accessesService.getAllExits();
    }

    /**
     * Check if visitor can do an action.
     * 
     * @param carPlate car plate
     * @param action   action
     * @return boolean.
     */

    @Operation(summary = "Check visitor access permission", description = "Verifies if a visitor is permitted to perform a specific action (entry/exit) based on its license plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access check completed successfully", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Invalid car plate or action type", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/check-access")
    public Boolean checkAccess(@RequestParam String carPlate, @RequestParam ActionTypes action) {
        return accessesService.canDoAction(carPlate, action);
    }

}
