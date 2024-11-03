package ar.edu.utn.frc.tup.lc.iv.repositories.specification.accesses;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessSpecificationTest {

    @Mock
    private Root<AccessEntity> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Join<Object, Object> authJoin;

    @Mock
    private Join<Object, Object> visitorJoin;

    @Mock
    private Path<Object> pathObject;

    @Mock
    private Predicate predicate;

    private void setupMocksForTest() {
        lenient().when(root.join(anyString())).thenReturn(authJoin);
        lenient().when(authJoin.join(anyString())).thenReturn(visitorJoin);
        lenient().when(root.get(anyString())).thenReturn(pathObject);
        lenient().when(authJoin.get(anyString())).thenReturn(pathObject);
        lenient().when(visitorJoin.get(anyString())).thenReturn(pathObject);

        lenient().when(criteriaBuilder.like(any(), anyString())).thenReturn(predicate);
        lenient().when(criteriaBuilder.or(any(Predicate[].class))).thenReturn(predicate);
        lenient().when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(predicate);
        lenient().when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
        lenient().when(criteriaBuilder.greaterThanOrEqualTo(any(), any(LocalDateTime.class))).thenReturn(predicate);
        lenient().when(criteriaBuilder.lessThanOrEqualTo(any(), any(LocalDateTime.class))).thenReturn(predicate);
        //lenient().when(criteriaBuilder.lower(any())).thenReturn(pathObject);
    }

    @Test
    void withFiltersNoFilters() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).and(any(Predicate[].class));
    }

    @Test
    void withFiltersTextFilter() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setTextFilter("test");

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).or(any(Predicate[].class));
    }

    @Test
    void withFiltersDateRange() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        LocalDateTime fromDate = LocalDateTime.now().minusDays(1);
        LocalDateTime toDate = LocalDateTime.now();

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, fromDate, toDate);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).greaterThanOrEqualTo(any(), eq(fromDate));
        verify(criteriaBuilder).lessThanOrEqualTo(any(), eq(toDate));
    }

    @Test
    void withFiltersVisitorType() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setVisitorType(VisitorType.OWNER);

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).equal(any(), eq(VisitorType.OWNER));
    }

    @Test
    void withFiltersActionType() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setActionType(ActionTypes.ENTRY);

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).equal(any(), eq(ActionTypes.ENTRY));
    }

    @Test
    void withFiltersDocumentType() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setDocumentType(DocumentType.DNI);

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).equal(any(), eq(DocumentType.DNI));
    }

    @Test
    void withFiltersExternalId() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setExternalId(1L);

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, null, null);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).equal(any(), eq(1L));
    }

    @Test
    void withFiltersAllFilters() {
        setupMocksForTest();
        AccessesFilter filter = new AccessesFilter();
        filter.setTextFilter("test");
        filter.setVisitorType(VisitorType.OWNER);
        filter.setActionType(ActionTypes.ENTRY);
        filter.setDocumentType(DocumentType.DNI);
        filter.setExternalId(1L);
        LocalDateTime fromDate = LocalDateTime.now().minusDays(1);
        LocalDateTime toDate = LocalDateTime.now();

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, fromDate, toDate);
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        assertNotNull(result);
        verify(criteriaBuilder).and(any(Predicate[].class));
        verify(criteriaBuilder).or(any(Predicate[].class));
    }
}