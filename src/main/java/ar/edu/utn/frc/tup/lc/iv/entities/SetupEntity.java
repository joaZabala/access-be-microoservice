package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalTime;
/**
 * Entity for configuring key time settings in the system.
 * Audited in the "setup_audit" table.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "setup")
@Audited
@AuditTable("setup_audit")
public class SetupEntity {
    /**
     * Unique identifier of the Setup.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Grace period in minutes before marking an entry as late.
     */
    private LocalTime timeOfGrace;
    /**
     * Final time after which employees
     * must leave the facility.
     */
    private LocalTime targetTime;
}
