package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * The {@code BaseEntity} class is a base class
 * that provides common auditing fields
 * for entities, such as the user who created
 * or last updated the entity, and the
 * respective timestamps.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AuditBaseEntity {
    /**
     * The ID of the user who created the entity.
     */
    @Column(name = "created_user")
    private Long createdUser;

    /**
     * The date and time when the entity was created.
     */
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * The ID of the user who last updated the entity.
     */
    @Column(name = "last_updated_user")
    private Long lastUpdatedUser;

    /**
     * The date and time when the entity was last updated.
     */
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;
    /**
     * Constructor for AuditBaseEntity.
     *
     * @param initialCreatedUser the ID of the user who created the entity
     * @param initialLastUpdatedUser the ID of the user who last updated the entity
     */
    public AuditBaseEntity(Long initialCreatedUser, Long initialLastUpdatedUser) {
        this.createdUser = initialCreatedUser;
        this.lastUpdatedUser = initialLastUpdatedUser;
        this.createdDate = LocalDateTime.now();
        this.lastUpdatedDate = LocalDateTime.now();
    }

}
