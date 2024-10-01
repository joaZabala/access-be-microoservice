package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.EmployeeAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.EmployeesAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IEmployeeAccessService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing employee access records and converting them to DTOs.
 */
@Service
@NoArgsConstructor
public class EmployeeAccessService implements IEmployeeAccessService {

    /**
     * Employee Access Repository dependency injection.
     */
    @Autowired
    private EmployeesAccessRepository employeesAccessRepository;
    /**
     * Model Mapper dependency injection.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of employee access records.
     * @param authTypeDescription the description of the authorization type
     * @param externalId          the external ID
     * @param dateFrom            the start date of the range
     * @param dateTo              the end date of the range
     * @return a list of {@link EmployeeAccessDTO} the mapped access records
     */
    @Override
    public List<EmployeeAccessDTO> getEmployeeAccessList(String authTypeDescription, Long externalId,
                                                         LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> externalAccesses = employeesAccessRepository.getEmployeeAccessesList(authTypeDescription,
                                                        externalId, dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<EmployeeAccessDTO> newExternalAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity: externalAccesses) {
            newExternalAccesses.add(modelMapper.map(accessesEntity, EmployeeAccessDTO.class));
        }
        return newExternalAccesses;
    }
}
