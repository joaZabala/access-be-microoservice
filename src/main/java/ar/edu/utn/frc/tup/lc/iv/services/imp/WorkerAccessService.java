package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.EmployeesAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.WorkerAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IWorkerAccessService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing worker access records and converting them to DTOs.
 */
@Service
@NoArgsConstructor
public class WorkerAccessService implements IWorkerAccessService {
    /**
     * Employees Access Repository dependency injection.
     */
    @Autowired
    private EmployeesAccessRepository employeesAccessRepository;
    /**
     * Worker Access Repository dependency injection.
     */
    @Autowired
    private WorkerAccessRepository workerAccessRepository;
    /**
     * Model Mapper dependency injection.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of worker access records.
     * @param authTypeDescription the description of the authorization type
     * @param workerId the ID of the worker
     * @param dateFrom the start date of the range
     * @param dateTo the end date of the range
     * @return a list of {@link WorkerAccessDTO}  the mapped access records
     */
    @Override
    public List<WorkerAccessDTO> getWorkerAccessListById(String authTypeDescription, Long workerId, LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> externalAccesses = employeesAccessRepository.getEmployeeAccessesList(authTypeDescription, workerId,
                dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<WorkerAccessDTO> newExternalAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity : externalAccesses) {
            newExternalAccesses.add(mapWorkerAccessDTO(accessesEntity));
        }
        return newExternalAccesses;
    }

    /**
     * Retrieves a list of worker access records.
     * @param authTypeDescription the description of the authorization type
     * @param plotId the ID of the plot
     * @param dateFrom the start date of the range
     * @param dateTo the end date of the range
     * @return a list of {@link WorkerAccessDTO} containing the mapped access records
     */
    @Override
    public List<WorkerAccessDTO> getWorkerAccessListByPlot(String authTypeDescription, Long plotId,
                                                           LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> externalAccesses = workerAccessRepository.getWorkerAccessesListByPlot(authTypeDescription,
                                                plotId, dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<WorkerAccessDTO> newExternalAccesses = new ArrayList<>();

        for (AccessesEntity accessesEntity : externalAccesses) {
            newExternalAccesses.add(mapWorkerAccessDTO(accessesEntity));
        }
        return newExternalAccesses;
    }

    /**
     * Maps an AccessesEntity object to a WorkerAccessDTO object.
     * @param accessesEntity the AccessesEntity object to map
     * @return a mapped {@link WorkerAccessDTO} object
     */
    @Override
    public WorkerAccessDTO mapWorkerAccessDTO(AccessesEntity accessesEntity) {
        if (accessesEntity.getSupplierEmployeeId() == null) {
            modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getExternalId(), WorkerAccessDTO::setExternalId);
        } else {
            modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)
                    .addMapping(AccessesEntity::getSupplierEmployeeId, WorkerAccessDTO::setExternalId);
        }
        modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)
                .addMapping(src -> src.getVehicleType().getCarDescription(), WorkerAccessDTO::setCarDescription);
        modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)
                .addMapping(src -> src.getVehicleType().getCarDescription(), WorkerAccessDTO::setCarDescription);
        return modelMapper.map(accessesEntity, WorkerAccessDTO.class);
    }
}
