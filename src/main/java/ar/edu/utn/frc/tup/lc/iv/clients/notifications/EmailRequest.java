package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  The {@code EmailRequest} class represents
 *  a request to send an email.
 *  It is used in the service layer to send an
 *  email.
 */
@Data
@NoArgsConstructor
public class EmailRequest {
    /**
     * Email address of the recipient.
     */
    private String recipient;
    /**
     * subject.
     */
    private String subject;
    /**
     * The list of variables to be replaced in the email template.
     */
    private List<EmailVariable> variables;
    /**
     * The id of the email template.
     */
    @JsonProperty("template_id")
    private Long templateId;

}
