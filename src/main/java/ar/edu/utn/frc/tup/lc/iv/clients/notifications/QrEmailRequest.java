package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  The {@code QrEmailRequest} class represents
 *  a request to send an email with a QR code.
 *  It is used in the service layer to send an
 *  email with a QR code.
 */
@Data
@NoArgsConstructor
public class QrEmailRequest {
    /**
     * Email address of the recipient.
     */
    private String email;

    /**
     * Name of the invitor.
     */
    @JsonProperty("invitor_name")
    private String invitorName;

    /**
     * Document number of the invitor.
     */
    @JsonProperty("doc_number")
    private Long docNumber;
}
