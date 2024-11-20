package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup.SetupDTO;
import ar.edu.utn.frc.tup.lc.iv.services.ISetupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Controller for handling setup requests.
 * This class exposes a REST API endpoint to
 * accesses setup.
 */
@RestController
@RequestMapping("/setup")
@Tag(name = "Accesses SETUP", description = "setup")
public class SetupController {
    /**
     * Setup Service dependency injection.
     */
    @Autowired
    private ISetupService setupService;

    /**
     * Endpoint to retrieve the current setup configuration.
     *
     * @return the current setup configuration.
     */
    @GetMapping
    @Operation(summary = "Retrieve setup configuration", description = "Gets the current setup configuration")
    public ResponseEntity<SetupDTO> getSetup() {
        try {
            SetupDTO setup = setupService.getSetup();
            return ResponseEntity.ok(setup);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
    /**
     * Endpoint to update the setup configuration.
     *
     * @param setupDTO the new setup configuration.
     * @param userId request.
     * @return the updated setup configuration.
     */
    @PutMapping
    @Operation(summary = "Update setup configuration", description = "Updates the current setup configuration")
    public ResponseEntity<SetupDTO> updateSetup(@RequestBody SetupDTO setupDTO,
        @RequestHeader("x-user-id") Long userId) {
        try {
            SetupDTO updatedSetup = setupService.updateSetup(setupDTO);
            return ResponseEntity.ok(updatedSetup);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
