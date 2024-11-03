package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.VisitorService;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationRestClientTest {

    @MockBean
    private AuthService authService;

    @SpyBean
    NotificationRestClient notificationRestClient;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    VisitorService visitorService;

    private static final String emailServiceBaseUrl = "http://host.docker.internal:8011";

    @Test
    void initializeTemplate_ShouldCreateTemplateSuccessfully() {
        // Datos de prueba
        String base64Template = "someBase64EncodedString";
        EmailTemplateResponse mockResponse = new EmailTemplateResponse();
        mockResponse.setId(1L);
        mockResponse.setName("joaquin");
        mockResponse.setBody(base64Template);
        mockResponse.setActive(true);

        // Configurar Mockito para simular el comportamiento de restTemplate
        when(restTemplate.postForEntity(
                eq(emailServiceBaseUrl + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // Llamar al método bajo prueba
        notificationRestClient.initializeTemplate();

        // Verificar que el templateId fue guardado correctamente
        assertEquals(mockResponse.getId(), notificationRestClient.getTemplateId());

    }

    @Test
    void sendQRCodeEmail_WithValidAuth_ShouldSendEmailSuccessfully() throws IOException {
        // Arrange
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(docNumber);
        visitorDTO.setName("Jane");
        visitorDTO.setLastName("Doe");

        AuthRangeDTO authRange = new AuthRangeDTO();
        authRange.setDateFrom(LocalDate.now());
        authRange.setDateTo(LocalDate.now().plusDays(7));
        authRange.setHourFrom(LocalTime.of(8, 0));
        authRange.setHourTo(LocalTime.of(18, 0));

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthRanges(Collections.singletonList(authRange));

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(docNumber)).thenReturn(Collections.singletonList(authDTO));
        when(restTemplate.postForEntity(
                eq(emailServiceBaseUrl + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ReflectionTestUtils.setField(notificationRestClient, "templateId", 1L);

        // Act
        notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber);

        // Assert
        verify(visitorService).getVisitorByDocNumber(docNumber);
        verify(authService).getValidAuthsByDocNumber(docNumber);
        verify(restTemplate).postForEntity(
                eq(emailServiceBaseUrl + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class));
    }

    @Test
    void sendQRCodeEmailWithNoAuth() throws IOException {

        QrEmailRequest qrEmailRequest = new QrEmailRequest();
        qrEmailRequest.setDocNumber(123456789L);
        qrEmailRequest.setEmail("test@example.com");
        qrEmailRequest.setInvitorName("Joaquin Zabala");

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(qrEmailRequest.getDocNumber());
        visitorDTO.setName("Jane");
        visitorDTO.setLastName("Doe");

        when(visitorService.getVisitorByDocNumber(qrEmailRequest.getDocNumber())).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(qrEmailRequest.getDocNumber())).thenReturn(new ArrayList<>());
        when(restTemplate.postForEntity(
                eq(emailServiceBaseUrl + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ReflectionTestUtils.setField(notificationRestClient, "templateId", 1L);

        // Act
        notificationRestClient.sendQRCodeEmail(qrEmailRequest.getEmail(), qrEmailRequest.getInvitorName(), qrEmailRequest.getDocNumber());

        // Assert
        verify(visitorService).getVisitorByDocNumber(qrEmailRequest.getDocNumber());
        verify(authService).getValidAuthsByDocNumber(qrEmailRequest.getDocNumber());
        verify(restTemplate).postForEntity(
                eq(emailServiceBaseUrl+ "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class));
    }

    @Test
    void sendQRCodeEmail_WhenVisitorNotFound_ShouldThrowException() {
        // Arrange
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
                notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber)
        );
    }
}