package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QRController.class)
public class QRControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

        verify(qrService, times(1)).generateQrForVisitor(docNumber);
    }

    @Test
    void generateQrTestIOError() throws Exception {
        Long docNumber = 12345678L;

        when(qrService.generateQrForVisitor(docNumber))
                .thenThrow(new IOException("Error generating QR code"));

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));

        verify(qrService, times(1)).generateQrForVisitor(docNumber);
    }
}