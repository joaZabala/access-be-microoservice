package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for handling
 * operations related to authorized access entities.
 * This service interacts with the repository
 * to manage and retrieve access records.
 */
@Service
public class AccessesService implements IAccessesService {

    /**
     * Repository for managing access entities.
     */
    @Autowired
    private AccessesRepository accessesRepository;

    /**
     * ModelMapper for converting between
     * AccessEntity and AccessDTO.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all access records from the repository.
     * @return List of AccessDTO representing access records.
     */
    @Override
    public List<AccessDTO> getAllAccess() {
        return accessesRepository.findAll().stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all entries (access actions of type ENTRY).
     *
     * @return List of AccessDTO representing entry records.
     */
    @Override
    public List<AccessDTO> getAllEntries() {
        return accessesRepository.findByAction(ActionTypes.ENTRY).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all exits (access actions of type EXIT).
     *
     * @return List of AccessDTO representing exit records.
     */
    @Override
    public List<AccessDTO> getAllExits() {
        return accessesRepository.findByAction(ActionTypes.EXIT).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves access records filtered by visitor type.
     * @param visitorType The type of the visitor.
     * @return List of AccessDTO for the specified visitor type.
     */
    @Override
    public List<AccessDTO> getAllAccessByType(VisitorType visitorType) {
        return accessesRepository.findByAuthVisitorType(visitorType).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves access records filtered by visitor type and external ID.
     * @param visitorType The type of the visitor.
     * @param externalId  External identifier associated with the access.
     * @return List of AccessDTO for the specified visitor type
     * and external ID.
     */
    @Override
    public List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId) {
        return accessesRepository.findByAuthVisitorTypeAndAuthExternalID(visitorType, externalId).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    //todo: implement this
//    @Override
//    public List<AccessDTO> getMissingExits() {
//        return null;  // Implementation to be added later
//    }

    /**
     * Registers a new access entry in the repository.
     *
     * @param accessEntity The access entity to be registered.
     * @return AccessDTO representing the registered access.
     */
    @Override
    public AccessDTO registerAccess(AccessEntity accessEntity) {

        AccessEntity savedAccess = accessesRepository.save(accessEntity);

        AccessDTO accessDTO = modelMapper.map(savedAccess, AccessDTO.class);
        accessDTO.setName(savedAccess.getAuth().getVisitor().getName());
        accessDTO.setLastName(savedAccess.getAuth().getVisitor().getLastName());
        accessDTO.setDocNumber(savedAccess.getAuth().getVisitor().getDocNumber());

        return accessDTO;
    }

}
