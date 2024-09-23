package ar.edu.utn.frc.tup.lc.iv.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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

import java.time.LocalDate;

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
@Table(name = "Visitors")
public class VisitorsEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Authorized.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long visitorId;
    /**
     * Name of the authorized person.
     */
    @Column(length = NAME_MAX_LENGTH, name = "name")
    private String name;
    /**
     * LastName of the authorized person.
     */
    @Column(length = LAST_NAME_MAX_LENGTH, name = "lastname")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    /**
     * If a visitor is active or not.
     */
    @Column(name = "is_active")
    private boolean isActive;

    /**
     * Constant for the maximum length of the 'name' field.
     */
    public static final int NAME_MAX_LENGTH = 50;

    /**
     * Constant for the maximum length of the 'lastName' field.
     */
    public static final int LAST_NAME_MAX_LENGTH = 50;
}
