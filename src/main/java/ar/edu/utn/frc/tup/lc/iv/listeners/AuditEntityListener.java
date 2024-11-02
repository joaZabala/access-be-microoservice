package ar.edu.utn.frc.tup.lc.iv.listeners;

import ar.edu.utn.frc.tup.lc.iv.entities.AuditBaseEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
/**
 * AuditEntityListener automatically
 * manages auditing for entities extending AuditBaseEntity.
 */
public class AuditEntityListener {
    /**
     * Sets creation and update timestamps
     * and user IDs before persisting the entity.
     * @param entity audit Fields.
     */
    @PrePersist
    public void prePersist(AuditBaseEntity entity) {
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setCreatedUser(getCurrentUserId());
        entity.setLastUpdatedUser(getCurrentUserId());
    }
    /**
     * Updates the last modified timestamp
     * and user ID before updating the entity.
     * @param entity audit Fields.
     */
    @PreUpdate
    public void preUpdate(AuditBaseEntity entity) {
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setLastUpdatedUser(getCurrentUserId());
    }
    /**
     * Retrieves the ID of the current user.
     * @return returns current user
     */
    private Long getCurrentUserId() {
        return UserHeaderInterceptor.getCurrentUserId();
    }
}
