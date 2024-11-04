package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing authorized persons.
 * This class provides endpoints to create, retrieve,
 * and update authorized persons.
 */
@RestController
@RequestMapping("/visitors")
@Tag(name = "Visitor Management", description = "APIs for managing visitor information")
public class VisitorController {

    /** Response code for successful operations. */
    private static final String HTTP_OK = "200";

    /** Response code for not found errors. */
    private static final String HTTP_NOT_FOUND = "404";

    /** Response code for bad request errors. */
    private static final String HTTP_BAD_REQUEST = "400";

    /**
     * Authorized Service dependency injection.
     */
    @Autowired
    private IVisitorService visitorService;

    /**
     * Retrieves a list of all visitors.
     *
     * @param page   the page number for pagination
     * @param size   the size of the page
     * @param filter dto with filters
     * @return a list of VisitorDTO objects
     */
    @Operation(summary = "Get all visitors", description = "Retrieves a paginated list of visitors with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Successfully retrieved visitors list",
                content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
    })
    @GetMapping()
    public PaginatedResponse<VisitorDTO> getAllVisitors(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter criteria for visitors") @Valid VisitorFilter filter) {
        return visitorService.getAllVisitors(page, size, filter);
    }

    /**
     * Retrieves a specific authorized person by their ID.
     *
     * @param docNumber The identifier of the visitor person.
     * @return The VisitorDto object representing the authorized
     *         person with the specified ID.
     */
    @Operation(summary = "Get visitor by document number", description = "Retrieves a specific visitor using their document number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Successfully retrieved visitor",
                content = @Content(schema = @Schema(implementation = VisitorDTO.class))),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = "Visitor not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/by-doc-number/{docNumber}")
    public ResponseEntity<VisitorDTO> getVisitorByDocNumber(
            @Parameter(description = "Document number of the visitor", required = true) @PathVariable Long docNumber) {
        return ResponseEntity.ok(visitorService.getVisitorByDocNumber(docNumber));
    }

    /**
     * Updates an existing visitor or create a new visitor.
     *
     * @param visitorRequest The DTO with the visitor data.
     * @param visitorId      id of visitor
     * @param userId         The identifier of the user.
     * @return VisitorDto.
     */
    @Operation(summary = "Create or update visitor",
        description = "Creates a new visitor or updates an existing one based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Visitor successfully created/updated",
                content = @Content(schema = @Schema(implementation = VisitorDTO.class))),
            @ApiResponse(responseCode = HTTP_BAD_REQUEST, description = "Invalid request data",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @PutMapping()
    public ResponseEntity<VisitorDTO> generateVisitor(
            @Parameter(description = "Visitor details", required = true) @Valid @RequestBody VisitorRequest visitorRequest,
            @Parameter(description = "ID of the visitor to update (optional)") @RequestParam(required = false) Long visitorId,
            @Parameter(description = "ID of the user making the request", required = true) @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(visitorService.saveOrUpdateVisitor(visitorRequest, visitorId));
    }

    /**
     * Deactivate visitor by docNumber.
     *
     * @param visitorId The identifier of the visitor.
     * @param userId    The identifier of the user.
     * @return VisitorDTO.
     */
    @Operation(summary = "Delete visitor", description = "Deactivates a visitor by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Visitor successfully deactivated",
                content = @Content(schema = @Schema(implementation = VisitorDTO.class))),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = "Visitor not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @DeleteMapping("/{visitorId}")
    public ResponseEntity<VisitorDTO> deleteVisitor(
            @Parameter(description = "ID of the visitor to delete", required = true) @PathVariable Long visitorId,
            @Parameter(description = "ID of the user making the request", required = true) @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(visitorService.deleteVisitor(visitorId));
    }

    /**
     * Retrieves a specific visitor by their ID.
     *
     * @param visitorId unique identifier of the visitor
     * @return VisitorDTO
     */
    @Operation(summary = "Get visitor by ID", description = "Retrieves a specific visitor using their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_OK, description = "Successfully retrieved visitor",
                content = @Content(schema = @Schema(implementation = VisitorDTO.class))),
            @ApiResponse(responseCode = HTTP_NOT_FOUND, description = "Visitor not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/{visitorId}")
    public ResponseEntity<VisitorDTO> getVisitorById(
            @Parameter(description = "Unique identifier of the visitor", required = true) @PathVariable Long visitorId) {
        return ResponseEntity.ok(visitorService.getVisitorById(visitorId));
    }
}
