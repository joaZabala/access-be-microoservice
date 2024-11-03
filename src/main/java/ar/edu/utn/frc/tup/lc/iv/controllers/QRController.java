package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller for handling QR code-related requests.
 * This class exposes a REST API endpoint to generate QR codes
 * for visitors based on their document number.
 */
@RestController
@RequestMapping("/qr")
@Tag(name = "QR Code Generation", description = "API for generating QR codes for visitors")
public class QRController {
    /**
     * QR Service dependency injection.
     */
    @Autowired
    private IQRService qrService;

    /**
     * Endpoint to generate a QR code for a visitor based on their document number.
     * 
     * @param docNumber The document number of the visitor
     *                  for whom the QR code should be generated.
     * @return A ResponseEntity containing the QR code image in byte format,
     *         or an error response in case of failure.
     */
    @Operation(summary = "Generate QR code for visitor", description = "Generates a QR code image containing visitor information based on their document number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code successfully generated", content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE, schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "Visitor not found", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Error generating QR code", content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/{docNumber}")
    public ResponseEntity<byte[]> generateQr(@PathVariable Long docNumber) throws IOException {
        byte[] qrImage = qrService.generateQrForVisitor(docNumber);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}
