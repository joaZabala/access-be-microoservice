package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for the Authorized entity that represents an authorized person.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorizedDTO {

    /**
     * Unique identifier for the authorized person.
     */
    private Long authId;

    /**
     * First name of the authorized person. Maximum of 50 characters.
     */
    private String name;

    /**
     * Last name of the authorized person. Maximum of 50 characters.
     */
    private String lastname;

    /**
     * Document number of the authorized person.
     */
    private Long docNumber;

    /**
     * Birthdate of the authorized person.
     */
    private LocalDate birthDate;
}
