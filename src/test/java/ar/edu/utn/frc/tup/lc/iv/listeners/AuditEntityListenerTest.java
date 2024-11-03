package ar.edu.utn.frc.tup.lc.iv.listeners;

import ar.edu.utn.frc.tup.lc.iv.entities.AuditBaseEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

class AuditEntityListenerTest {

    private AuditEntityListener auditEntityListener;
    private AuditBaseEntity auditBaseEntity;

    @BeforeEach
    void setUp() {
        auditEntityListener = new AuditEntityListener();
        auditBaseEntity = new AuditBaseEntity();
    }

    @Test
    void prePersist_ShouldSetAllAuditFields() {
        // Given
        Long expectedUserId = 1L;
        LocalDateTime beforeTest = LocalDateTime.now();

        // When
        try (MockedStatic<UserHeaderInterceptor> mockedStatic = mockStatic(UserHeaderInterceptor.class)) {
            mockedStatic.when(UserHeaderInterceptor::getCurrentUserId).thenReturn(expectedUserId);

            auditEntityListener.prePersist(auditBaseEntity);
        }

        // Then
        assertNotNull(auditBaseEntity.getCreatedDate());
        assertNotNull(auditBaseEntity.getLastUpdatedDate());
        assertEquals(expectedUserId, auditBaseEntity.getCreatedUser());
        assertEquals(expectedUserId, auditBaseEntity.getLastUpdatedUser());

        // Verify timestamps are recent
        assertEquals(true, auditBaseEntity.getCreatedDate().isAfter(beforeTest) ||
                auditBaseEntity.getCreatedDate().isEqual(beforeTest));
        assertEquals(true, auditBaseEntity.getLastUpdatedDate().isAfter(beforeTest) ||
                auditBaseEntity.getLastUpdatedDate().isEqual(beforeTest));
    }

    @Test
    void preUpdate_ShouldUpdateLastModifiedFields() {
        // Given
        Long expectedUserId = 1L;
        LocalDateTime beforeTest = LocalDateTime.now();

        // Set initial values
        LocalDateTime initialCreatedDate = LocalDateTime.now().minusDays(1);
        Long initialCreatedUser = 2L;
        auditBaseEntity.setCreatedDate(initialCreatedDate);
        auditBaseEntity.setCreatedUser(initialCreatedUser);

        // When
        try (MockedStatic<UserHeaderInterceptor> mockedStatic = mockStatic(UserHeaderInterceptor.class)) {
            mockedStatic.when(UserHeaderInterceptor::getCurrentUserId).thenReturn(expectedUserId);

            auditEntityListener.preUpdate(auditBaseEntity);
        }

        // Then
        // Verify last updated fields were modified
        assertNotNull(auditBaseEntity.getLastUpdatedDate());
        assertEquals(expectedUserId, auditBaseEntity.getLastUpdatedUser());
        assertEquals(true, auditBaseEntity.getLastUpdatedDate().isAfter(beforeTest) ||
                auditBaseEntity.getLastUpdatedDate().isEqual(beforeTest));

        // Verify created fields remain unchanged
        assertEquals(initialCreatedDate, auditBaseEntity.getCreatedDate());
        assertEquals(initialCreatedUser, auditBaseEntity.getCreatedUser());
    }
}