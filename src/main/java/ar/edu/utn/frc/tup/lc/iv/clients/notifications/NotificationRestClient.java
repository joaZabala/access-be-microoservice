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

    @Value("${qr.url}")
    private String QR_SERVICE_BASE_URL; // Cambiar por la URL real
    private Long templateId; // Guardar el ID de la plantilla una vez creada

    public void initializeTemplate() {
        // Crear la plantilla HTML base64 para el email con el QR
        String htmlTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email con Código QR y Logo</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f7f7f7;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #343a40;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "            padding: 20px;\n" +
                "            color: #ffffff;\n" +
                "        }\n" +
                "        .logo {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .invitation-details {\n" +
                "            background-color: #495057;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 8px;\n" +
                "            margin: 20px 0;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .qr-code {\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        h2, p {\n" +
                "            margin: 5px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"logo\">\n" +
                "            <img src=\"https://www.villadelcondor.com/imagenes/villa_del_condor.png\" alt=\"Logo de la Empresa\" style=\"max-width: 100%; height: auto;\">\n" +
                "        </div>\n" +
                "        <div class=\"invitation-details\">\n" +
                "            <h2>¡Federico Tahan te ha invitado!</h2>\n" +
                "            <p><strong>Desde:</strong> 23/02/2024</p>\n" +
                "            <p><strong>Hasta:</strong> 01/12/2024</p>\n" +
                "            <p><strong>Hora:</strong> 10:30 AM - 3:40 PM</p>\n" +
                "            <p><strong>Día:</strong> Lunes</p>\n" +
                "        </div>\n" +
                "        <div class=\"qr-code\">\n" +
                "            <img src=\"http://api.qrserver.com/v1/create-qr-code/?size=200x200&format=png&data=Name:fede,%20LastName:%20Tahan,%20Document:%C2%A02043673720\" alt=\"Código QR\" style=\"max-width: 100%; height: auto;\">\n" +
                "        </div>\n" +
                "        <p style=\"text-align: center; margin-top: 20px;\">\n" +
                "            Escanea el código QR para más información.\n" +
                "        </p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

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

