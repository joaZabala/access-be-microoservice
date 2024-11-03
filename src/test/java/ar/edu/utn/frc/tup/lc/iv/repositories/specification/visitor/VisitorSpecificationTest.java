package ar.edu.utn.frc.tup.lc.iv.repositories.specification.visitor;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
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
class VisitorSpecificationTest {

    @Mock
    private Root<VisitorEntity> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Path<Object> pathObject;

    @Mock
    private Predicate predicate;

    /**
     * lenient() es un modificador en Mockito que hace
     * que el framework sea más permisivo con los stubs
     * que no se utilizan en una prueba específica.
     */
    private void setupMocks() {
        lenient().when(root.get(anyString())).thenReturn(pathObject);
        //lenient().when(pathObject.as(String.class)).thenReturn(pathObject);

        lenient().when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
        //lenient().when(criteriaBuilder.lower(any())).thenReturn(pathObject);
        lenient().when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
        lenient().when(criteriaBuilder.or(any(Predicate[].class))).thenReturn(predicate);
        lenient().when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(predicate);
    }

    @Test
    void shouldCreateSpecificationWithNoTextFilter() {
        setupMocks();
        VisitorFilter filter = new VisitorFilter();
        filter.setActive(true);

        Specification<VisitorEntity> spec = VisitorSpecification.withVisitorFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).equal(any(), eq(true));
        verify(criteriaBuilder).and(any(Predicate[].class));
    }

    @Test
    void shouldCreateSpecificationWithTextFilter() {
        setupMocks();
        VisitorFilter filter = new VisitorFilter();
        filter.setTextFilter("test");
        filter.setActive(true);

        Specification<VisitorEntity> spec = VisitorSpecification.withVisitorFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder, times(3)).lower(any());
        verify(criteriaBuilder, times(3)).like(any(), anyString());
        verify(criteriaBuilder).or(any(Predicate[].class));
    }

    @Test
    void shouldCreateSpecificationWithDocumentType() {
        setupMocks();
        VisitorFilter filter = new VisitorFilter();
        filter.setDocumentType(DocumentType.DNI);
        filter.setActive(true);

        Specification<VisitorEntity> spec = VisitorSpecification.withVisitorFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(root).get("documentType");
        verify(criteriaBuilder).equal(any(), eq(DocumentType.DNI));
    }

    @Test
    void shouldCreateSpecificationWithAllFilters() {
        setupMocks();
        VisitorFilter filter = new VisitorFilter();
        filter.setTextFilter("test");
        filter.setDocumentType(DocumentType.DNI);
        filter.setActive(true);

        Specification<VisitorEntity> spec = VisitorSpecification.withVisitorFilters(filter);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).or(any(Predicate[].class));
        verify(criteriaBuilder).equal(any(), eq(DocumentType.DNI));
        verify(criteriaBuilder).equal(any(), eq(true));
        verify(criteriaBuilder).and(any(Predicate[].class));
    }
}