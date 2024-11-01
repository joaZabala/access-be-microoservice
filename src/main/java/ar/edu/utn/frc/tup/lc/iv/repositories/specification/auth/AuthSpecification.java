package ar.edu.utn.frc.tup.lc.iv.repositories.specification.auth;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
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

}