package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing an authorized person.
 * Each authorized person has personal details such as name,
 * last name, document number and birthdate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Authorized  extends AuditBase {
    /**
     * Unique identifier of the Authorized.
     */
    private Long authId;
    /**
     * Name of the authorized person.
     */
    private String name;
    /**
     * LastName of the authorized person.
     */
    private String lastName;
    /**
     * Document Number of the authorized person.
     */
    private Long docNumber;
    /**
     * Birth Date of the authorized person.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime birthDate;
}
