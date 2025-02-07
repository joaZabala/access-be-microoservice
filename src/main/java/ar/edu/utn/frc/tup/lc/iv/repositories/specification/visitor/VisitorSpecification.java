package ar.edu.utn.frc.tup.lc.iv.repositories.specification.visitor;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Provides query filters for VisitorEntity.
 */
public class VisitorSpecification {
    /**
     * Builds a dynamic query filter.
     * @param filter dto filter params
     * @return A Specification for
     * querying VisitorEntity with applied filters.
     */
    public static Specification<VisitorEntity> withVisitorFilters(VisitorFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTextFilter() != null) {
                String filterPattern = "%" + filter.getTextFilter().toLowerCase(Locale.ROOT) + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), filterPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), filterPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("docNumber").as(String.class)), filterPattern)
                ));
            }

            if (filter.getDocumentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("documentType"), filter.getDocumentType()));
            }

            if (filter.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.getActive()));
            }

            if (filter.getCreatedUser() != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdUser"), filter.getCreatedUser()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
