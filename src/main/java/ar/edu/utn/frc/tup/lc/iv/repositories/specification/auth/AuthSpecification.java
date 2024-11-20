package ar.edu.utn.frc.tup.lc.iv.repositories.specification.auth;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Provides query filters for AuthEntity.
 */
public class AuthSpecification {
    /**
     * Builds a dynamic query filter.
     * @param filter dto filter params
     * @return A Specification for
     * querying AuthEntity with applied filters.
     */
    public static Specification<AuthEntity> withFilters(AuthFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String visitorTable = "visitor";

            if (filter.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("authId"), filter.getId()));
            }

            if (filter.getTextFilter() != null) {
                String filterPattern = "%" + filter.getTextFilter().toLowerCase(Locale.ROOT) + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder
                                .lower(root.get(visitorTable).get("name")), filterPattern),
                        criteriaBuilder
                                .like(criteriaBuilder.lower(root.get(visitorTable).get("lastName")), filterPattern),
                        criteriaBuilder.like(criteriaBuilder
                                .lower(root.get(visitorTable).get("docNumber").as(String.class)), filterPattern)
                ));
            }

            if (filter.getDocumentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get(visitorTable).get("documentType"),
                        filter.getDocumentType()));
            }

            if (filter.getVisitorType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("visitorType"), filter.getVisitorType()));
            }

            if (filter.getCreatedUser() != null && filter.getCreatedUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("createdUser"), filter.getCreatedUser()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
