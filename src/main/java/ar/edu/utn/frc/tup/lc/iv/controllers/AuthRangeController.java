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

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthRangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller class for managing authorized ranges.
 */
@RestController
@RequestMapping("/auth-ranges")
@Tag(name = "Authorization Range Management", description = "APIs for managing visitor authorization ranges and permissions")
public class AuthRangeController {

    /**
     * Service for managing authorized ranges.
     */
    @Autowired
    private AuthRangeService authRangeService;

    /**
     * Creates a new authorized range.
     * 
     * @param visitorType the type of visitor.
     * @param docNumber   the document number of the visitor.
     * @param plotId      the plot id.
     * @return the created authorized range.
     */

    @Operation(summary = "Get authorization ranges", description = "Retrieves authorization ranges based on visitor type, document number, and plot ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authorization ranges", content = @Content(schema = @Schema(implementation = AuthRangeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Visitor or plot id not found.", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping
    public List<AuthRangeDTO> getAuthRanges(@RequestParam(required = true) VisitorType visitorType,
            @RequestParam(required = true) Long docNumber,
            @RequestParam(required = true) Long plotId) {
        return authRangeService.getAuthRanges(visitorType, docNumber, plotId);

    }

    /**
     * Deletes an authorized range.
     * 
     * @param id     the id of the authorized range.
     * @param userId the id of the user making the request.
     * @return the deleted authorized range.
     */

    @Operation(summary = "Delete authorization range", description = "Removes an existing authorization range by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorization range successfully deleted", content = @Content(schema = @Schema(implementation = AuthRangeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Authorization range not found", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @DeleteMapping
    public ResponseEntity<AuthRangeDTO> deleteAuthRange(@RequestParam(required = true) Long id,
            @RequestHeader("x-user-id") Long userId) {
        return ResponseEntity.ok(authRangeService.deleteAuthRange(id));
    }

    /**
     * Updates an existing authorized range.
     * 
     * @param authRangeId id of the authorized range.
     * @param request     the updated authorized range.
     * @param id          the id of the user making the request.
     * @return the updated authorized range.
     */

    @Operation(summary = "Update authorization range", description = "Updates an existing authorization range with new information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorization range successfully updated", content = @Content(schema = @Schema(implementation = AuthRangeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data or ID format", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "404", description = "Authorization range not found", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @PutMapping
    public ResponseEntity<AuthRangeDTO> updateAuthRange(@RequestParam(required = true) Long authRangeId,
            @RequestBody AuthRangeRequestDTO request,
            @RequestHeader("x-user-id") Long id) {
        return ResponseEntity.ok(authRangeService.updateAuthRange(authRangeId, request));
    }
}
