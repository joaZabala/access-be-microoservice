package ar.edu.utn.frc.tup.lc.iv.services.imp;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor

public class AccessesService implements IAccessService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccessesRepository accessesRepository;

    @Override
    public List<AccessDTO> getAccessesList(Long authType, Long idType, LocalDate dateFrom, LocalDate dateTo) {
        return List.of();
    }

    public AccessDTO getAccessFromID(Long id){
       AccessesEntity accessesEntity = accessesRepository.getReferenceById(id);
       modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
             .addMapping(src -> src.getVehicleType().getCarDescription(), AccessDTO::setCarDescription);
       if(accessesEntity.getAuthRange() == null){
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getVisitor().getName(), AccessDTO::setName);
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getVisitor().getLastName(), AccessDTO::setLastName);
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getVisitor().getDocNumber(), AccessDTO::setDocNumber);
       }else{
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getAuthRange().getVisitorId().getName(), AccessDTO::setName);
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getAuthRange().getVisitorId().getLastName(), AccessDTO::setLastName);
           modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                   .addMapping(src -> src.getAuthRange().getVisitorId().getDocNumber(), AccessDTO::setDocNumber);
       }
        return modelMapper.map(accessesEntity, AccessDTO.class);
    }

    public List<AccessDTO> getVisitAccessesByID(Long owner, Long visitorId, LocalDate dateFrom, LocalDate dateTo){
        List<AccessesEntity> visitAccesses = accessesRepository.getVisitAccessesList(owner, visitorId,dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<AccessDTO> newVisitAccesses = new ArrayList<>();
        for(AccessesEntity accessesEntity: visitAccesses){
            newVisitAccesses.add(mapVisitAccessDTO(accessesEntity));
        }
        return newVisitAccesses;
    }

    public List<AccessDTO> getVisitAccesses(Long owner, LocalDate dateFrom, LocalDate dateTo){
        List<AccessesEntity> visitAccesses = accessesRepository.getVisitAccessesList(owner,dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<AccessDTO> newVisitAccesses = new ArrayList<>();
        for(AccessesEntity accessesEntity: visitAccesses){
            newVisitAccesses.add(mapVisitAccessDTO(accessesEntity));
        }
        return newVisitAccesses;
    }

    public  List<AccessDTO> getExternalAccessList(String authTypeDescription, Long externalId, LocalDate dateFrom, LocalDate dateTo){
        List<AccessesEntity> externalAccesses = accessesRepository.getExternalAccessesList(authTypeDescription, externalId,dateFrom.atStartOfDay(), dateTo.atStartOfDay());
        List<AccessDTO> newExternalAccesses = new ArrayList<>();
        for(AccessesEntity accessesEntity: externalAccesses){
            newExternalAccesses.add(mapVisitAccessDTO(accessesEntity));
        }
        return newExternalAccesses;
    }




    public AccessDTO mapVisitAccessDTO(AccessesEntity accessesEntity){
        modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                .addMapping(src -> src.getVehicleType().getCarDescription(), AccessDTO::setCarDescription);
        if(accessesEntity.getAuthRange() == null){
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getVisitor().getName(), AccessDTO::setName);
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getVisitor().getLastName(), AccessDTO::setLastName);
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getVisitor().getDocNumber(), AccessDTO::setDocNumber);
        }else{
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getName(), AccessDTO::setName);
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getLastName(), AccessDTO::setLastName);
            modelMapper.typeMap(AccessesEntity.class, AccessDTO.class)
                    .addMapping(src -> src.getAuthRange().getVisitorId().getDocNumber(), AccessDTO::setDocNumber);
        }
        return modelMapper.map(accessesEntity, AccessDTO.class);
    }
}
