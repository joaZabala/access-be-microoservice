package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.clients.notifications.NotificationRestClient;
import ar.edu.utn.frc.tup.lc.iv.clients.notifications.QrEmailRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;


import java.io.IOException;

/**
 * Controller for handling QR code-related requests.
 * This class exposes a REST API endpoint to generate QR codes
 * for visitors based on their document number.
 */
@RestController
@RequestMapping("/qr")
public class QRController {
    /**
     * QR Service dependency injection.
     */
    @Autowired
    private IQRService qrService;
    /**
     * Rest Client dependency injection.
     */
    @Autowired
    private NotificationRestClient notificationRestClient;
    /**
     * Const for a API ERROR 500.
     */
    private static final int NUMBER = 500;

    /**
     * Endpoint to generate a QR code for a visitor based on their document number.
     *
     * @param docNumber The document number of the visitor
     *                  for whom the QR code should be generated.
     * @return A ResponseEntity containing the QR code image in byte format,
     * or an error response in case of failure.
     */
    @GetMapping("/{docNumber}")
    public ResponseEntity<byte[]> generateQr(@PathVariable Long docNumber) {
        try {
            byte[] qrImage = qrService.generateQrForVisitor(docNumber);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(NUMBER).body(null);
        }
    }

    /**
     * Endpoint to generate a QR code for a visitor based on their document number.
     *
     * @param id id of the user making the request.
     * @return a message indicating the status of the request.
     */
    @PostMapping("/initialize-template")

    public ResponseEntity<String> initializeTemplate(@RequestHeader("x-user-id") Long id) {
        try {
            notificationRestClient.initializeTemplate();
            return ResponseEntity.ok("Plantilla inicializada correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al inicializar la plantilla: " + e.getMessage());
        }
    }

    /**
     * Endpoint to send a QR code email to a visitor.
     *
     * @param request the request containing the email and
     *                the document number of the visitor.
     * @param id      id of the user making the request.
     * @return a message indicating the status of the request.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendQREmail(@RequestBody QrEmailRequest request, @RequestHeader("x-user-id") Long id) {
        try {
            notificationRestClient.sendQRCodeEmail(
                    request.getEmail(),
                    request.getInvitorName(),
                    request.getDocNumber()
            );
            return ResponseEntity.ok("Email con QR enviado correctamente a " + request.getEmail());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Error en los datos proporcionados: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error al generar o enviar el QR: " + e.getMessage());
        }
    }
}
