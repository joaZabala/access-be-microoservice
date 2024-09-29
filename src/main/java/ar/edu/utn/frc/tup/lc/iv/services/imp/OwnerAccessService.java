package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.OwnerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IOwnerAccessService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing owner access records and converting them to DTOs.
 */
@Service
@NoArgsConstructor
public class OwnerAccessService implements IOwnerAccessService {
    /**
     * Owner Access Repository dependency injection.
     */
    @Autowired
    private OwnerAccessRepository ownerAccessRepository;
    /**
     * Model Mapper dependency injection.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of owner access records.
     * @param plotId   the ID of the plot
     * @param dateFrom the start date of the range
     * @param dateTo   the end date of the range
     * @return a list of {@link OwnerAccessDTO} the mapped access records
     */
    @Override
    public List<OwnerAccessDTO> getOwnerAcessList(Long plotId, LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> ownerAccesses = ownerAccessRepository.getOwnerAccessesList(plotId, dateFrom.atStartOfDay(),
                                                dateTo.atStartOfDay());
        List<OwnerAccessDTO> newOwnerAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity: ownerAccesses) {
            newOwnerAccesses.add(mapOwnerAccessDto(accessesEntity));
        }
        return newOwnerAccesses;
    }

    /**
     * Maps an AccessesEntity object to an OwnerAccessDTO
     * object using ModelMapper, with specific mappings
     * for name and last name from the visitor ID.
     * @param accessesEntity the AccessesEntity object to map
     * @return a mapped {@link OwnerAccessDTO} object
     */
    @Override
    public OwnerAccessDTO mapOwnerAccessDto(AccessesEntity accessesEntity) {
        modelMapper.typeMap(AccessesEntity.class, OwnerAccessDTO.class)
                .addMapping(src -> src.getAuthRange().getVisitorId().getName(), OwnerAccessDTO::setName);
        modelMapper.typeMap(AccessesEntity.class, OwnerAccessDTO.class)
                .addMapping(src -> src.getAuthRange().getVisitorId().getLastName(), OwnerAccessDTO::setLastName);
        return modelMapper.map(accessesEntity, OwnerAccessDTO.class);
    }
}
