package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Class to model the type of object expected
 *  to be provided by another service.
 */
@Data
@NoArgsConstructor
public class EmailTemplateResponse {
    /**
     * Identification number.
     */
    private Long id;

    /**
     * The email's name.
     */
    private String name;

    /**
     * What the email's body will be.
     */
    private String body;

    /**
     * Boolean representing if the
     * template is active or not.
     */
    private Boolean active;
}
