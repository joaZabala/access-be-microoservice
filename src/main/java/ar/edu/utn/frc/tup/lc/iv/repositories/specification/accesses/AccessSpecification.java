package ar.edu.utn.frc.tup.lc.iv.repositories.specification.accesses;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.criteria.Predicate;
/**
 * Provides query filters for AccessEntity.
 */
public class AccessSpecification {
    /**
     * Builds a dynamic query filter.
     * @param filter dto filter params
     * @param fromDate filter from date
     * @param toDate filter to date
     * @return A Specification for
     * querying AccessEntity with applied filters.
     */
    public static Specification<AccessEntity> withFilters(
            AccessesFilter filter, LocalDateTime fromDate, LocalDateTime toDate) {

    return (root, query, criteriaBuilder) -> {
        List<Predicate> predicates = new ArrayList<>();
        String visitorTable = "visitor";
        String authTable = "auth";
        if (filter.getTextFilter() != null) {
            String filterPattern = "%" + filter.getTextFilter().toLowerCase(Locale.ROOT) + "%";
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("vehicleReg")), filterPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join(authTable).join(visitorTable)
                            .get("docNumber").as(String.class)), filterPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join(authTable).join(visitorTable)
                            .get("name")), filterPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join(authTable).join(visitorTable)
                            .get("lastName")), filterPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("comments")), filterPattern)
            ));
        }

        if (filter.getVisitorType() != null) {
            predicates.add(criteriaBuilder.equal(root.join(authTable).get("visitorType"), filter.getVisitorType()));
        }

        if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("actionDate"), fromDate));
        }

        if (toDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("actionDate"), toDate));
        }

        if (filter.getActionType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("action"), filter.getActionType()));
        }

        if (filter.getDocumentType() != null) {
            predicates.add(criteriaBuilder.equal(root.join(authTable).join(visitorTable).get("documentType"),
                    filter.getDocumentType()));
        }

        if (filter.getExternalId() != null) {
            predicates.add(criteriaBuilder.equal(root.join(authTable).get("externalID"), filter.getExternalId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
}
}
