package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Worker Access Service, which defines
 * methods for managing and retrieving worker access records.
 */
public interface IWorkerAccessService {

    /**
     * Retrieves a list of worker access records.
     * @param authTypeDescription the description of the authorization type
     * @param workerId the ID of the worker for whom to retrieve access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link WorkerAccessDTO} worker access records for a worker
     */
    List<WorkerAccessDTO> getWorkerAccessListById(String authTypeDescription, Long workerId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Retrieves a list of worker access records.
     * @param authTypeDescription the description of the authorization type
     * @param plotId the ID of the plot for which to retrieve worker access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link WorkerAccessDTO} worker access records for a plot
     */
    List<WorkerAccessDTO> getWorkerAccessListByPlot(String authTypeDescription, Long plotId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Maps an {@link AccessesEntity} to a {@link WorkerAccessDTO}.
     * @param accessesEntity the entity to be mapped
     * @return a {@link WorkerAccessDTO} representing the mapped access entity
     */
    WorkerAccessDTO mapWorkerAccessDTO(AccessesEntity accessesEntity);
}
