package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.QRService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@NoArgsConstructor
public class NotificationRestClient {

    @Autowired
    private QRService qrService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notification.service.url}")
    private String EMAIL_SERVICE_BASE_URL; // Cambiar por la URL real
    private Long templateId; // Guardar el ID de la plantilla una vez creada

    public void initializeTemplate() {
        // Crear la plantilla HTML base64 para el email con el QR
        String htmlTemplate = "<html><body>"
                + "<h2>Tu código QR</h2>"
                + "<p>Hola {{nombre}},</p>"
                + "<p>Aquí está tu código QR:</p>"
                + "<img src='data:image/png;base64,{{qr_code}}' alt='QR Code'/>"
                + "<p>Por favor, guárdalo de forma segura.</p>"
                + "</body></html>";

        String base64Template = Base64.getEncoder().encodeToString(htmlTemplate.getBytes());

        // Crear el objeto para la solicitud de creación de plantilla
        var templateRequest = new EmailTemplateRequest();
        templateRequest.setName("QR_Code_Template");
        templateRequest.setBase64body(base64Template);

        // Realizar la solicitud POST para crear la plantilla
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailTemplateRequest> request = new HttpEntity<>(templateRequest, headers);

        var response = restTemplate.postForEntity(
                 EMAIL_SERVICE_BASE_URL + "/email-templates",
                request,
                EmailTemplateResponse.class
        );

        // Guardar el ID de la plantilla para uso futuro
        this.templateId = Objects.requireNonNull(response.getBody()).getId();
    }

    public void sendQRCodeEmail(String recipientEmail, String recipientName, Long docNumber) throws IOException {
        // Generar el QR en base64
        String qrBase64 = qrService.generateQRBase64(docNumber);
        System.out.println(qrBase64);

        // Crear la lista de variables para la plantilla
        List<EmailVariable> variables = new ArrayList<>();
        variables.add(new EmailVariable("nombre", recipientName));
        variables.add(new EmailVariable("qr_code", qrBase64));

        // Crear la solicitud de envío de email
        var emailRequest = new EmailRequest();
        emailRequest.setRecipient(recipientEmail);
        emailRequest.setSubject("Tu código QR");
        emailRequest.setVariables(variables);
        emailRequest.setTemplateId(templateId);

        // Enviar el email
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailRequest> request = new HttpEntity<>(emailRequest, headers);

        restTemplate.postForEntity(
                EMAIL_SERVICE_BASE_URL + "/emails/send",
                request,
                Void.class
        );
    }
}

