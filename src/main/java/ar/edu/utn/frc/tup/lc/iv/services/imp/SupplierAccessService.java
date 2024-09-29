package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.SupplierAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.services.ISupplierAccessService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing supplier access records and converting them to DTOs.
 */
@Service
@NoArgsConstructor
public class SupplierAccessService implements ISupplierAccessService {
    /**
     * Supplier Access Repository dependency injection.
     */
    @Autowired
    private SupplierAccessRepository supplierAccessRepository;
    /**
     * Model Mapper dependency injection.
     */
    @Autowired
    private WorkerAccessService workerAccessService;

    /**
     * Retrieves a list of supplier access records .
     * @param authTypeDescription the description of the authorization type
     * @param supplierId the ID of the supplier associated with the access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link WorkerAccessDTO} containing the mapped access records
     */
    @Override
    public List<WorkerAccessDTO> getSupplierAccessList(String authTypeDescription, Long supplierId, LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> externalAccesses = supplierAccessRepository.getSupplierAccessesList(authTypeDescription, supplierId,
                                                dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<WorkerAccessDTO> newExternalAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity : externalAccesses) {
            newExternalAccesses.add(workerAccessService.mapWorkerAccessDTO(accessesEntity));
        }
        return newExternalAccesses;
    }
}
