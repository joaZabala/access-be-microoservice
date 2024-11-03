package ar.edu.utn.frc.tup.lc.iv.repositories.specification.auth;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthSpecificationTest {

    @Mock
    private Root<AuthEntity> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Path<Object> pathObject;

    @Mock
    private Path<Object> visitorPath;

    @Mock
    private Predicate predicate;

    /**
     * lenient() es un modificador en Mockito que hace
     * que el framework sea más permisivo con los stubs
     * que no se utilizan en una prueba específica.
     */
    private void setupMocksForTest() {
        lenient().when(root.get(anyString())).thenReturn(pathObject);
        lenient().when(root.get("visitor")).thenReturn(visitorPath);
        lenient().when(visitorPath.get(anyString())).thenReturn(pathObject);
       // lenient().when(pathObject.as(String.class)).thenReturn(pathObject);

        lenient().when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
       // lenient().when(criteriaBuilder.lower(any())).thenReturn(pathObject);
        lenient().when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
        lenient().when(criteriaBuilder.or(any(Predicate[].class))).thenReturn(predicate);
        lenient().when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(predicate);
    }

    @Test
    void withFiltersNoFilters() {
        setupMocksForTest();
        AuthFilter filter = new AuthFilter();

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).and(any(Predicate[].class));
    }

    @Test
    void withFiltersId() {
        setupMocksForTest();
        AuthFilter filter = new AuthFilter();
        filter.setId(1L);

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(root).get("authId");
        verify(criteriaBuilder).equal(any(), eq(1L));
    }

    @Test
    void withFiltersTextFilter() {
        setupMocksForTest();
        AuthFilter filter = new AuthFilter();
        filter.setTextFilter("test");

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder, times(3)).lower(any());
        verify(criteriaBuilder, times(3)).like(any(), anyString());
        verify(criteriaBuilder).or(any(Predicate[].class));
    }

    @Test
    void withFiltersDocumentType() {
        setupMocksForTest();
        AuthFilter filter = new AuthFilter();
        filter.setDocumentType(DocumentType.DNI);

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(root).get("visitor");
        verify(visitorPath).get("documentType");
        verify(criteriaBuilder).equal(any(), eq(DocumentType.DNI));
    }

    @Test
    void withFiltersVisitorType() {
        setupMocksForTest();
        AuthFilter filter = new AuthFilter();
        filter.setVisitorType(VisitorType.OWNER);

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(root).get("visitorType");
        verify(criteriaBuilder).equal(any(), eq(VisitorType.OWNER));
    }

}