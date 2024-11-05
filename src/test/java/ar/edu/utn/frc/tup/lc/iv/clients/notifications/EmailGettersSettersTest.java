package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class EmailGettersSettersTest {

    @Test
    void emailRequestNoArgsConstructor() {
        // Verificar que el constructor sin argumentos cree un objeto vacío
        EmailRequest emailRequest = new EmailRequest();
        assertNull(emailRequest.getRecipient());
        assertNull(emailRequest.getSubject());
        assertNull(emailRequest.getVariables());
        assertNull(emailRequest.getTemplateId());
    }

    @Test
    void emailRequestShouldSerialize() throws JsonProcessingException {
        // Probar que el nombre del campo templateId se serialice correctamente como template_id
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTemplateId(123L);

        // Serialización usando ObjectMapper de Jackson
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(emailRequest);

        // Verificar que el JSON contiene el campo con el nombre correcto
        assertTrue(json.contains("\"template_id\":123"));
    }

    @Test
    void emailTemplateResponse_ShouldInitializeFieldsCorrectly() {
        // Crear una instancia de EmailTemplateResponse
        EmailTemplateResponse templateResponse = new EmailTemplateResponse();

        // Establecer los valores de los campos
        templateResponse.setId(1L);
        templateResponse.setName("Welcome Template");
        templateResponse.setBody("Welcome to our service!");
        templateResponse.setActive(true);

        // Verificar los valores de los campos
        assertEquals(1L, templateResponse.getId());
        assertEquals("Welcome Template", templateResponse.getName());
        assertEquals("Welcome to our service!", templateResponse.getBody());
        assertTrue(templateResponse.getActive());
    }

    @Test
    void emailTemplateResponse_NoArgsConstructor_ShouldCreateEmptyObject() {
        // Crear una instancia usando el constructor sin argumentos
        EmailTemplateResponse templateResponse = new EmailTemplateResponse();

        // Verificar que todos los campos están inicializados en null o false (por defecto)
        assertNull(templateResponse.getId());
        assertNull(templateResponse.getName());
        assertNull(templateResponse.getBody());
        assertNull(templateResponse.getActive());
    }

    @Test
    void emailRequest_ShouldInitializeFieldsCorrectly() {
        // Crear una lista de variables de ejemplo
        EmailVariable variable1 = new EmailVariable("name", "John Doe");
        EmailVariable variable2 = new EmailVariable("date", "2024-11-02");
        List<EmailVariable> variables = Arrays.asList(variable1, variable2);

        // Crear una instancia de EmailRequest y establecer los valores de los campos
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient("example@example.com");
        emailRequest.setSubject("Welcome");
        emailRequest.setVariables(variables);
        emailRequest.setTemplateId(123L);

        // Verificar los valores de los campos
        assertEquals("example@example.com", emailRequest.getRecipient());
        assertEquals("Welcome", emailRequest.getSubject());
        assertEquals(variables, emailRequest.getVariables());
        assertEquals(123L, emailRequest.getTemplateId());
    }

    @Test
    void emailRequest_NoArgsConstructor_ShouldCreateEmptyObject() {
        // Crear una instancia usando el constructor sin argumentos
        EmailRequest emailRequest = new EmailRequest();

        // Verificar que todos los campos están inicializados en null
        assertNull(emailRequest.getRecipient());
        assertNull(emailRequest.getSubject());
        assertNull(emailRequest.getVariables());
        assertNull(emailRequest.getTemplateId());
    }

    @Test
    void emailTemmplateRequest() throws JsonProcessingException {
        EmailTemplateRequest request = new EmailTemplateRequest();
        request.setBase64body("base64body");
        request.setName("name");

        assertEquals(request.getBase64body(), "base64body");
        assertEquals(request.getName(), "name");
    }

    @Test
    void emailVariables (){
        EmailVariable emailVariable = new EmailVariable();
        emailVariable.setKey("key");
        emailVariable.setValue("value");

        assertEquals(emailVariable.getKey(), "key");
        assertEquals(emailVariable.getValue(), "value");
    }
}