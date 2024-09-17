package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
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
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "Authorized")
public class AuthorizedEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Authorized.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long authId;
    /**
     * Name of the authorized person.
     */
    @Column(length = NAME_MAX_LENGTH)
    private String name;
    /**
     * LastName of the authorized person.
     */
    @Column(length = LAST_NAME_MAX_LENGTH)
    private String lastName;
    /**
     * Document Number of the authorized person.
     */
    @Column(name = "doc_number")
    private Long docNumber;
    /**
     * Birth Date of the authorized person.
     */
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    /**
     * Constant for the maximum length of the 'name' field.
     */
    public static final int NAME_MAX_LENGTH = 50;

    /**
     * Constant for the maximum length of the 'lastName' field.
     */
    public static final int LAST_NAME_MAX_LENGTH = 50;
}
