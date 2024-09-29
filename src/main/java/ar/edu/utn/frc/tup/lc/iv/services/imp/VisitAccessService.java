package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.VisitAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitAccessService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing visit access records and converting them to DTOs.
 */
@Service
@NoArgsConstructor
public class VisitAccessService implements IVisitAccessService {
    /**
     * Visit Access Repository dependency injection.
     */
    @Autowired
    private VisitAccessRepository visitAccessRepository;
    /**
     * Model Mapper dependency injection.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of visit access records.
     * @param plotId the ID of the plot associated with the visit access records
     * @param visitorId the ID of the visitor associated with the access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link VisitAccessDTO} containing the mapped access records
     */
    @Override
    public List<VisitAccessDTO> getVisitAccessesByID(Long plotId, Long visitorId, LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> visitAccesses = visitAccessRepository.getVisitAccessesList(plotId, visitorId,
                                                dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<VisitAccessDTO> newVisitAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity : visitAccesses) {
            newVisitAccesses.add(mapVisitAccessDTO(accessesEntity));
        }
        return newVisitAccesses;
    }

    /**
     * Retrieves a list of visit access records.
     * @param plotId the ID of the plot
     * @param dateFrom the start date of the range
     * @param dateTo the end date of the range
     * @return a list of {@link VisitAccessDTO} the mapped access records
     */
    @Override
    public List<VisitAccessDTO> getVisitAccesses(Long plotId, LocalDate dateFrom, LocalDate dateTo) {
        List<AccessesEntity> visitAccesses = visitAccessRepository.getVisitAccessesList(plotId, dateFrom.atStartOfDay(),
                                                dateTo.atStartOfDay());
        List<VisitAccessDTO> newVisitAccesses = new ArrayList<>();
        for (AccessesEntity accessesEntity : visitAccesses) {
            newVisitAccesses.add(mapVisitAccessDTO(accessesEntity));
        }
        return newVisitAccesses;
    }

    /**
     * Maps an AccessesEntity object to a VisitAccessDTO object.
     * @param accessesEntity the AccessesEntity object to map
     * @return a mapped {@link VisitAccessDTO} object
     */
    @Override
    public VisitAccessDTO mapVisitAccessDTO(AccessesEntity accessesEntity) {
        modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                .addMapping(src -> src.getVehicleType().getCarDescription(), VisitAccessDTO::setCarDescription);

        if (accessesEntity.getAuthRange() == null) {
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getVisitor().getName(), VisitAccessDTO::setName);
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getVisitor().getLastName(), VisitAccessDTO::setLastName);
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getVisitor().getDocNumber(), VisitAccessDTO::setDocNumber);
        } else {
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getName(), VisitAccessDTO::setName);
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getLastName(), VisitAccessDTO::setLastName);
            modelMapper.typeMap(AccessesEntity.class, VisitAccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getDocNumber(), VisitAccessDTO::setDocNumber);
        }
        return modelMapper.map(accessesEntity, VisitAccessDTO.class);
    }
}
