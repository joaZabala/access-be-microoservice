package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
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
    public PaginatedResponse<AccessDTO> getAllAccess(AccessesFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("actionDate"));
        LocalDateTime fromDateTime = filter.getFromDate() != null
                ? filter.getFromDate().atStartOfDay()
                : null;
        if (fromDateTime != null) {
            System.out.println("From DateTime: " + fromDateTime);
        } else {
            System.out.println("From DateTime is null");
        }
        LocalDateTime toDateTime = filter.getToDate() != null
                ? filter.getToDate().atTime(LocalTime.MAX)
                : null;

        Page<AccessEntity> accesses = accessesRepository.findAccesses(filter.getTextFilter(),
                        filter.getVisitorType(),
                        fromDateTime,
                        toDateTime,
                        filter.getActionType(),
                        filter.getDocumentType(),
                        filter.getExternalId(),
                        pageable);

        PaginatedResponse<AccessDTO> response = new PaginatedResponse<>();
        response.setItems(accesses.stream()
                .map(this::mapToAccessDTO).sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
                .collect(Collectors.toList()));
        response.setTotalElements(accesses.getTotalElements());
        return response;
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
                .sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
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
                .sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
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
                .map(this::mapToAccessDTO).collect(Collectors.toList());
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
                .map(this::mapToAccessDTO)
                .collect(Collectors.toList());
    }

    /**
     * Registers a new access entry in the repository.
     *
     * @param accessEntity The access entity to be registered.
     * @return AccessDTO representing the registered access.
     */
    @Override
    public AccessDTO registerAccess(AccessEntity accessEntity) {
        Long userId = UserHeaderInterceptor.getCurrentUserId();
        accessEntity.setCreatedUser(userId);
        accessEntity.setCreatedDate(LocalDateTime.now());

        AccessEntity savedAccess = accessesRepository.save(accessEntity);
        AccessDTO accessDTO = modelMapper.map(savedAccess, AccessDTO.class);
        accessDTO.setName(savedAccess.getAuth().getVisitor().getName());
        accessDTO.setLastName(savedAccess.getAuth().getVisitor().getLastName());
        accessDTO.setDocNumber(savedAccess.getAuth().getVisitor().getDocNumber());
        return accessDTO;
    }

    @Override
    public Boolean canDoAction(String carPlate, ActionTypes action) {
        AccessEntity acc = accessesRepository.findByVehicleReg(carPlate).stream()
                .max(Comparator.comparing(AccessEntity::getActionDate))
                .orElse(null);
        if (acc == null) {return true;}
        return !acc.getAction().equals(action);
    }

    /**
     * Maps an AccessEntity to an AccessDTO.
     * @param accessEntity AccessEntity to be mapped.
     * @return AccessDTO representing the AccessEntity.
     */
    private AccessDTO mapToAccessDTO(AccessEntity accessEntity) {
        AccessDTO accessDTO = modelMapper.map(accessEntity, AccessDTO.class);

        accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
        accessDTO.setDocType(accessEntity.getAuth().getVisitor().getDocumentType());
        accessDTO.setName(accessEntity.getAuth().getVisitor().getName());
        accessDTO.setLastName(accessEntity.getAuth().getVisitor().getLastName());
        accessDTO.setDocNumber(accessEntity.getAuth().getVisitor().getDocNumber());
        accessDTO.setVisitorType(accessEntity.getAuth().getVisitorType());

        return accessDTO;
    }
}
