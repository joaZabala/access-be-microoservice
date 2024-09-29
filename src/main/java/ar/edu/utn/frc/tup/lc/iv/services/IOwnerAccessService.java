package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.OwnerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Owner Access Service, which defines
 * methods for managing and retrieving owner access records.
 */
public interface IOwnerAccessService {

    /**
     * Retrieves a list of owner access records filtered by plot ID and date range.
     * @param plotId the ID of the plot for which to retrieve owner access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link OwnerAccessDTO} representing the owner access records
     */
    List<OwnerAccessDTO> getOwnerAcessList(Long plotId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Maps an AccessesEntity object to an OwnerAccessDTO object.
     *
     * @param accessesEntity the AccessesEntity object to map
     * @return the mapped {@link OwnerAccessDTO} object
     */
    OwnerAccessDTO mapOwnerAccessDto(AccessesEntity accessesEntity);
}
