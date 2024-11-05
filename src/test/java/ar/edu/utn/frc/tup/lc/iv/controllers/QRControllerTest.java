package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.clients.notifications.NotificationRestClient;
import ar.edu.utn.frc.tup.lc.iv.clients.notifications.QrEmailRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QRController.class)
public class QRControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationRestClient notificationRestClient;

    @MockBean
    private IQRService qrService;

    @Test
    void generateQrTestSuccess() throws Exception {
        Long docNumber = 12345678L;
        byte[] qrCode = new byte[]{1, 2, 3};

        when(qrService.generateQrForVisitor(docNumber)).thenReturn(qrCode);

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(qrCode));

        verify(qrService, times(1)).generateQrForVisitor(docNumber);
    }

    @Test
    void generateQrTestVisitorNotFound() throws Exception {
        Long docNumber = 12345678L;

        when(qrService.generateQrForVisitor(docNumber))
                .thenThrow(new IllegalArgumentException("No se encontró el visitante"));

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().is5xxServerError());

        verify(qrService, times(1)).generateQrForVisitor(docNumber);
    }

    @Test
    void generateQrTestIOError() throws Exception {
        Long docNumber = 12345678L;

        when(qrService.generateQrForVisitor(docNumber))
                .thenThrow(new IOException("Error generating QR code"));

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().is5xxServerError());

        verify(qrService, times(1)).generateQrForVisitor(docNumber);
    }

    @Test
    void sendQREmailTestSuccess() throws Exception {
        QrEmailRequest emailRequest = new QrEmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setInvitorName("John Doe");
        emailRequest.setDocNumber(12345678L);

        doNothing().when(notificationRestClient)
                .sendQRCodeEmail(
                        eq("test@example.com"),
                        eq("John Doe"),
                        eq(12345678L)
                );

        mockMvc.perform(post("/qr/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-user-id", "1")
                        .content(objectMapper.writeValueAsString(emailRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email con QR enviado correctamente a test@example.com"));

        verify(notificationRestClient).sendQRCodeEmail(
                eq("test@example.com"),
                eq("John Doe"),
                eq(12345678L)
        );
    }

    @Test
    void sendQREmailTestBadRequest() throws Exception {
        QrEmailRequest emailRequest = new QrEmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setInvitorName("John Doe");
        emailRequest.setDocNumber(12345678L);

        doThrow(new IllegalArgumentException("Invalid email"))
                .when(notificationRestClient).sendQRCodeEmail(
                        eq("test@example.com"),
                        eq("John Doe"),
                        eq(12345678L)
                );

        mockMvc.perform(post("/qr/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-user-id", "1")
                        .content(objectMapper.writeValueAsString(emailRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Error en los datos proporcionados: Invalid email"));
    }

    @Test
    void sendQREmailTestIOException() throws Exception {
        QrEmailRequest emailRequest = new QrEmailRequest();
        emailRequest.setEmail("test@example.com");
        emailRequest.setInvitorName("John Doe");
        emailRequest.setDocNumber(12345678L);

        doThrow(new IOException("Error generating QR"))
                .when(notificationRestClient).sendQRCodeEmail(
                        eq("test@example.com"),
                        eq("John Doe"),
                        eq(12345678L)
                );

        mockMvc.perform(post("/qr/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-user-id", "1")
                        .content(objectMapper.writeValueAsString(emailRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error al generar o enviar el QR: Error generating QR"));
    }
}