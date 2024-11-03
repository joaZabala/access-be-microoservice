package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QrEmailRequest {
    private String email;
    @JsonProperty("invitor_name")
    private String invitorName;
    @JsonProperty("doc_number")
    private Long doc_number;
}
