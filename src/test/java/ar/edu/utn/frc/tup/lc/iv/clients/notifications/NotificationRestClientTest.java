package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.VisitorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
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
    private NotificationRestClient notificationRestClient;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private VisitorService visitorService;

    private static final String EMAIL_SERVICE_BASE_URL = "http://host.docker.internal:8011";
    private static final String QR_SERVICE_BASE_URL = "http://host.docker.internal:8012";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationRestClient, "emailServiceBaseUrl", EMAIL_SERVICE_BASE_URL);
        ReflectionTestUtils.setField(notificationRestClient, "qrServiceBaseUrl", QR_SERVICE_BASE_URL);
    }

    @Test
    void initializeTemplateShouldCreateTemplateSuccessfully() {
        EmailTemplateResponse mockResponse = new EmailTemplateResponse();
        mockResponse.setId(1L);
        mockResponse.setName("QR_Code_Template");
        mockResponse.setBody("template_body");
        mockResponse.setActive(true);

        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        notificationRestClient.initializeTemplate();

        verify(restTemplate).postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class));
    }

    @Test
    void initializeTemplateWhenServiceFailsShouldThrowException() {
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        assertThrows(RestClientException.class, () ->
                notificationRestClient.initializeTemplate()
        );
    }

    @Test
    void sendQRCodeEmailWithValidAuthShouldSendEmailSuccessfully() throws IOException {
        // Preparar datos
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

        // Mock de respuestas
        EmailTemplateResponse templateResponse = new EmailTemplateResponse();
        templateResponse.setId(1L);

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(docNumber)).thenReturn(Collections.singletonList(authDTO));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(templateResponse, HttpStatus.OK));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber);

        verify(visitorService).getVisitorByDocNumber(docNumber);
        verify(authService).getValidAuthsByDocNumber(docNumber);
        verify(restTemplate, times(2)).postForEntity(
                anyString(),
                any(HttpEntity.class),
                any(Class.class));
    }

    @Test
    void sendQRCodeEmailWithEmptyAuthRangesShouldSendEmailWithFreeValues() throws IOException {
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(docNumber);
        visitorDTO.setName("Jane");
        visitorDTO.setLastName("Doe");

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthRanges(new ArrayList<>());

        EmailTemplateResponse templateResponse = new EmailTemplateResponse();
        templateResponse.setId(1L);

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(docNumber)).thenReturn(Collections.singletonList(authDTO));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(templateResponse, HttpStatus.OK));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber);

        verify(restTemplate, times(2)).postForEntity(
                anyString(),
                any(HttpEntity.class),
                any(Class.class));
    }

    @Test
    void sendQRCodeEmailWithNoAuthsShouldSendEmailWithFreeValues() throws IOException {
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(docNumber);
        visitorDTO.setName("Jane");
        visitorDTO.setLastName("Doe");

        EmailTemplateResponse templateResponse = new EmailTemplateResponse();
        templateResponse.setId(1L);

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(docNumber)).thenReturn(new ArrayList<>());
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(templateResponse, HttpStatus.OK));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber);

        verify(restTemplate, times(2)).postForEntity(
                anyString(),
                any(HttpEntity.class),
                any(Class.class));
    }

    @Test
    void sendQRCodeEmailWhenVisitorNotFoundShouldThrowException() {
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber));

        //verify(visitorService).getVisitorByDocNumber(docNumber);
        verify(authService, never()).getValidAuthsByDocNumber(any());
    }

    @Test
    void sendQRCodeEmailWhenEmailServiceFailsShouldThrowException() {
        String recipientEmail = "test@example.com";
        String invitorName = "John Doe";
        Long docNumber = 123456789L;

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(docNumber);
        visitorDTO.setName("Jane");
        visitorDTO.setLastName("Doe");

        EmailTemplateResponse templateResponse = new EmailTemplateResponse();
        templateResponse.setId(1L);

        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(visitorDTO);
        when(authService.getValidAuthsByDocNumber(docNumber)).thenReturn(new ArrayList<>());
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/email-templates"),
                any(HttpEntity.class),
                eq(EmailTemplateResponse.class)))
                .thenReturn(new ResponseEntity<>(templateResponse, HttpStatus.OK));
        when(restTemplate.postForEntity(
                eq(EMAIL_SERVICE_BASE_URL + "/emails/send"),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenThrow(new RestClientException("Failed to send email"));

        assertThrows(RestClientException.class, () ->
                notificationRestClient.sendQRCodeEmail(recipientEmail, invitorName, docNumber));
    }
}
