package ar.edu.utn.frc.tup.lc.iv.listeners;

import ar.edu.utn.frc.tup.lc.iv.entities.AuditBaseEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditEntityListener {
    @PrePersist
    public void prePersist(AuditBaseEntity entity) {
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setCreatedUser(getCurrentUserId());
        entity.setLastUpdatedUser(getCurrentUserId());
    }

    @PreUpdate
    public void preUpdate(AuditBaseEntity entity) {
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setLastUpdatedUser(getCurrentUserId());
    }

    private Long getCurrentUserId() {
        return UserHeaderInterceptor.getCurrentUserId();
    }
}
