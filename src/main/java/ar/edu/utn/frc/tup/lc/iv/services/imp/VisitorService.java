package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.NoArgsConstructor;

/**
 * Service implementation for handling operations related to Authorized
 * entities.
 * This service converts Authorized entities to AuthorizedDTOs.
 */
@NoArgsConstructor
@Service
public class VisitorService implements IVisitorService {
    /**
     * Repository to access Authorized entities from the database.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * Repository to access Authorized entities from the database.
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves authorized entities and
     * maps them to a list of {@link VisitorDTO}.
     * @param page   the page number to retrieve.
     * @param size   the number of records per page.
     * @param filter filter.
     * @return a list of {@link VisitorDTO}.
     */

    @Override
    public PaginatedResponse<VisitorDTO> getAllVisitors(int page, int size, String filter, boolean active) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName").and(Sort.by("name")));

        Page<VisitorEntity> visitorPage;

        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            visitorPage = visitorRepository.findByFilter(filter, pageable);
        } else {
            // Si no hay filtro, obtener todos los visitantes activos
            if (!active) {
                visitorPage = visitorRepository.findAllByActive(false, pageable);
            } else {
                visitorPage = visitorRepository.findAllByActive(true, pageable);
            }

        }

        //incluimos los tipos de visitante al visitorDTO
        List<VisitorDTO> visitorDTOs = visitorPage.stream()
                .map(visitorEntity -> {
                    VisitorDTO visitorDTO = modelMapper.map(visitorEntity, VisitorDTO.class);

                    // Asigna los tipos de visitante para cada VisitorDTO
                    List<VisitorType> visitorTypes = authRepository.findByVisitor(visitorEntity).stream()
                            .map(AuthEntity::getVisitorType)
                            .distinct()
                            .collect(Collectors.toList());
                    visitorDTO.setVisitorTypes(visitorTypes);

                    return visitorDTO;
                })
                .collect(Collectors.toList());

        return new PaginatedResponse<>(visitorDTOs, visitorPage.getTotalElements());
    }

    /**
     * Creates or updates a visitor.
     *
     * @param visitorRequest request DTO with visitor details.
     * @return the VisitorDTO with the authorization details.
     */
    @Override
    public VisitorDTO saveOrUpdateVisitor(VisitorRequest visitorRequest, Long visitorId) {
        Long writerUserId = UserHeaderInterceptor.getCurrentUserId();

        VisitorEntity existVisitorEntity = null;

        if (visitorId != null) {
            Optional<VisitorEntity> visitorEntity = visitorRepository.findById(visitorId);

            if (visitorEntity.isEmpty()) {
                throw new EntityNotFoundException("No existe el visitante con el id " + visitorId);
            }

            existVisitorEntity = visitorEntity.get();
        }

        VisitorEntity visitorEntity;
        if (Objects.nonNull(existVisitorEntity)) {
            existVisitorEntity.setLastUpdatedUser(writerUserId);
            existVisitorEntity.setLastUpdatedDate(LocalDateTime.now());
            visitorEntity = existVisitorEntity;
        } else {
            visitorEntity = new VisitorEntity();
            visitorEntity.setCreatedUser(writerUserId);
            visitorEntity.setCreatedDate(LocalDateTime.now());
        }

        visitorEntity.setName(visitorRequest.getName());
        visitorEntity.setLastName(visitorRequest.getLastName());
        visitorEntity.setDocumentType(visitorRequest.getDocumentType());
        visitorEntity.setDocNumber(visitorRequest.getDocNumber());
        visitorEntity.setBirthDate(visitorRequest.getBirthDate());
        visitorEntity.setActive(true);
        return modelMapper.map(visitorRepository.save(visitorEntity), VisitorDTO.class);
    }

    /**
     * fetch visitor by docNumber.
     *
     * @param docNumber document number of the visitor.
     * @return VisitorDTO.
     */
    @Override
    public VisitorDTO getVisitorByDocNumber(Long docNumber) {
        VisitorEntity visitorEntity = visitorRepository.findByDocNumber(docNumber);

        if (Objects.isNull(visitorEntity)) {
            return null;
        } else {
            return modelMapper.map(visitorEntity, VisitorDTO.class);
        }
    }

    /**
     * Deactivate visitor by docNumber.
     *
     * @param visitorId document number of the visitor.
     * @return VisitorDTO.
     */
    @Override
    public VisitorDTO deleteVisitor(Long visitorId) {
        Optional<VisitorEntity> visitorEntity = visitorRepository.findById(visitorId);

        if (visitorEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe el visitante con el id " + visitorId);
        }
        Long writerUserId = UserHeaderInterceptor.getCurrentUserId();

        visitorEntity.get().setActive(false);
        visitorEntity.get().setLastUpdatedDate(LocalDateTime.now());
        visitorEntity.get().setLastUpdatedUser(writerUserId);
        return modelMapper.map(visitorRepository.save(visitorEntity.get()), VisitorDTO.class);
    }

    /**
     * fetch visitor by id.
     *
     * @param id unique identifier of the visitor
     * @return visitorDto with the given id
     */
    @Override
    public VisitorDTO getVisitorById(Long id) {
        Optional<VisitorEntity> visitorEntity = visitorRepository.findById(id);
        if (visitorEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe el visitante con el id " + id);
        }
        return modelMapper.map(visitorEntity.get(), VisitorDTO.class);
    }

}
