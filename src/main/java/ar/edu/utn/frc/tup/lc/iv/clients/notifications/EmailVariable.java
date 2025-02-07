package ar.edu.utn.frc.tup.lc.iv.clients.notifications;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to model the type of object expected to be
 * provided by another service.
 */
@Data
@NoArgsConstructor
public class EmailVariable {
    /**
     * key of the variable.
     */
    private String key;
    /**
     * value of the variable.
     */
    private String value;

    /**
     * Constructor.
     * @param keyValue key of the variable.
     * @param initialValue value of the variable.
     */
    public EmailVariable(String keyValue, String initialValue) {
        this.key = keyValue;
        this.value = initialValue;
    }

}
