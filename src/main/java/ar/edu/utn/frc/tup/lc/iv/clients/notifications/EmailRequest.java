package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class EmailRequest {
    private String recipient;
    private String subject;
    private List<EmailVariable> variables;
    @JsonProperty("template_id")
    private Long templateId;

}
