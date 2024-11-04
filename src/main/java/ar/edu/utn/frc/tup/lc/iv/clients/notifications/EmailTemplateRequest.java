package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Email template request.
 */
@Data
@NoArgsConstructor
public class EmailTemplateRequest {
    /**
     * Name of the template.
     */
    private String name;
    /**
     * Base64 encoded body of the template.
     */
    private String base64body;
}
