package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Supplier Access Service, which defines
 * methods for managing and retrieving supplier access records.
 */
public interface ISupplierAccessService {

    /**
     * Retrieves a list of worker access records for a specified supplier.
     * @param authTypeDescription the description of the authorization type
     * @param supplierId the ID of the supplier
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link WorkerAccessDTO} worker access records for a supplier
     */
    List<WorkerAccessDTO> getSupplierAccessList(String authTypeDescription, Long supplierId, LocalDate dateFrom, LocalDate dateTo);
}

