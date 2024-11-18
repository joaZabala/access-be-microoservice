package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
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
     * Grace period in minutes before marking an entry as late.
     */
    private LocalTime timeOfGrace;
    /**
     * Final time after which employees
     * must leave the facility.
     */
    private LocalTime targetTime;
}
