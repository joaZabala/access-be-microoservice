package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailTemplateRequest {
    private String name;
    private String base64body;
}
