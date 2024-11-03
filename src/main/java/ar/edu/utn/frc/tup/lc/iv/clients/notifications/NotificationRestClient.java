package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.VisitorService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Service for making HTTP requests to the notification service.
 */
@Service
@NoArgsConstructor
public class NotificationRestClient {

    /**
     * RestTemplate for making HTTP requests.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Service for handling visitors.
     */
    @Autowired
    private VisitorService visitorService;

    /**
     * Service for handling authorization.
     */
    @Autowired
    private AuthService authService;
    /**
     * URL of the notification service.
     */

    @Value("${notification.service.url}")
    private String emailServiceBaseUrl;

    /**
     * URL of the QR service.
     */
    @Value("${qr.url}")
    private String qrServiceBaseUrl; // Cambiar por la URL real

    /**
     *  ID of the template created in HTML.
     */
    private Long templateId; // Guarda el ID de la plantilla una vez creada
    /**
     * name of condition access.
     */
    private static final String FREE = "Libre";

    /**
     * CSS styles for the HTML template.
     */
    private static final String CSS_STYLES = String.join("\n",
            "body {",
            "    font-family: Arial, sans-serif;",
            "    margin: 0;",
            "    padding: 20px;",
            "    background-color: #f7f7f7;",
            "}",
            ".container {",
            "    max-width: 600px;",
            "    margin: 0 auto;",
            "    background-color: #343a40;",
            "    border-radius: 5px;",
            "    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);",
            "    padding: 20px;",
            "    color: #ffffff;",
            "}",
            ".logo {",
            "    text-align: center;",
            "    margin-bottom: 20px;",
            "}",
            ".invitation-details {",
            "    background-color: #495057;",
            "    padding: 15px;",
            "    border-radius: 8px;",
            "    margin: 20px 0;",
            "    text-align: center;",
            "}",
            ".qr-code {",
            "    text-align: center;",
            "    margin-top: 20px;",
            "}",
            "h2, p {",
            "    margin: 5px 0;",
            "}"
    );

    /**
     * HTML template for the email.
     */
    private static final String HTML_TEMPLATE = String.join("\n",
            "<!DOCTYPE html>",
            "<html lang=\"es\">",
            "<head>",
            "    <meta charset=\"UTF-8\">",
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">",
            "    <title>Email con Código QR y Logo</title>",
            "    <style>",
            CSS_STYLES,
            "    </style>",
            "</head>",
            "<body>",
            "    <div class=\"container\">",
            "        <div class=\"logo\">",
            "            <img src=\"https://www.villadelcondor.com/imagenes/villa_del_condor.png\" alt=\"Logo de la Empresa\" style=\"max-width: 100%; height: auto;\">",
            "        </div>",
            "        <div class=\"invitation-details\">",
            "            <h2>¡{{invitation}} te ha invitado!</h2>",
            "            <p><strong>Desde:</strong> {{dateFrom}}</p>",
            "            <p><strong>Hasta:</strong> {{dateTo}}</p>",
            "            <p><strong>Hora:</strong> {{hourFrom}} - {{hourTo}}</p>",
            "        </div>",
            "        <div class=\"qr-code\">",
            "            <img src=\"{{photo_qr}}\" alt=\"Código QR\" style=\"max-width: 100%; height: auto;\">",
            "        </div>",
            "        <p style=\"text-align: center; margin-top: 20px;\">",
            "            Presentá este código QR en la entrada al barrio.",
            "        </p>",
            "    </div>",
            "</body>",
            "</html>"
    );

    /**
     * Method to initialize the template.
     */
    public void initializeTemplate() {
        // Crear la plantilla HTML base64 para el email con el QR


        String base64Template = Base64.getEncoder().encodeToString(HTML_TEMPLATE.getBytes());

        // Crear el objeto para la solicitud de creación de plantilla
        var templateRequest = new EmailTemplateRequest();
        templateRequest.setName("QR_Code_Template");
        templateRequest.setBase64body(base64Template);

        // Realizar la solicitud POST para crear la plantilla
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailTemplateRequest> request = new HttpEntity<>(templateRequest, headers);

        var response = restTemplate.postForEntity(
                 emailServiceBaseUrl + "/email-templates",
                request,
                EmailTemplateResponse.class
        );

        // Guardar el ID de la plantilla para uso futuro
        this.templateId = Objects.requireNonNull(response.getBody()).getId();
    }

    /**
     * Send an email with QR code and invitation details to the recipient.
     * @param recipientEmail email address of the recipient.
     * @param invitorName name of the invitor.
     * @param docNumber document number of the visitor.
     * @throws IOException if there is an issue while generating the QR code.
     */
    public void sendQRCodeEmail(String recipientEmail, String invitorName, Long docNumber) throws IOException {

        //buscar el vistante por documento
        VisitorDTO ve = visitorService.getVisitorByDocNumber(docNumber);

        List<AuthDTO> validAuths = authService.getValidAuthsByDocNumber(ve.getDocNumber());

        // Obtener los rangos de la primera autorización válida
        LocalDate dateFrom = null;
        LocalDate dateTo = null;
        LocalTime hourFrom = null;
        LocalTime hourTo = null;

        if (!validAuths.isEmpty() && !validAuths.get(0).getAuthRanges().isEmpty()) {
            AuthRangeDTO authRange = validAuths.get(0).getAuthRanges().get(0);
            dateFrom = authRange.getDateFrom();
            dateTo = authRange.getDateTo();
            hourFrom = authRange.getHourFrom();
            hourTo = authRange.getHourTo();
        }

        // Crear la lista de variables para la plantilla
        List<EmailVariable> variables = new ArrayList<>();
        variables.add(new EmailVariable("photo_qr", qrServiceBaseUrl + "Name:" + ve.getName()
                + ",LastName:" + ve.getLastName() + ",Document:" + ve.getDocNumber()));
        variables.add(new EmailVariable("invitation", invitorName));

        // Formatear fechas y horas, o mostrar "Libre" si son nulas
        variables.add(new EmailVariable("dateFrom",
                dateFrom != null ? dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : FREE));
        variables.add(new EmailVariable("dateTo",
                dateTo != null ? dateTo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : FREE));
        variables.add(new EmailVariable("hourFrom",
                hourFrom != null ? hourFrom.format(DateTimeFormatter.ofPattern("HH:mm")) : FREE));
        variables.add(new EmailVariable("hourTo",
                hourTo != null ? hourTo.format(DateTimeFormatter.ofPattern("HH:mm")) : FREE));

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
                emailServiceBaseUrl + "/emails/send",
                request,
                Void.class
        );
    }
}

