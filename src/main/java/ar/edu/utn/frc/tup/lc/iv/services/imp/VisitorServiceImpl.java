package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.NoArgsConstructor;

/**
 * Service implementation for handling operations related to Authorized
 * entities.
 * This service converts Authorized entities to AuthorizedDTOs.
 */
@NoArgsConstructor
@Service
public class VisitorServiceImpl implements VisitorService {
    /**
     * Repository to access Authorized entities from the database.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     * service of the user.
     */
    @Autowired
    private UserRestClient userRestClient;

    /**
     * Retrieves all authorized entities from the repository and maps them to
     * a list of {@link VisitorDTO}.
     *
     * @param page the number of the page to retrieve.
     * @param size the number of records per page.
     * @return a list of {@link VisitorDTO} representing the authorized entities.
     */
    @Override
    public List<VisitorDTO> getAllVisitors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VisitorEntity> visitorPage = visitorRepository.findAll(pageable);

        return visitorPage.stream()
                .map(entity -> modelMapper.map(entity, VisitorDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Creates or updates a visitor.
     *
     * @param visitorRequestDto request DTO with visitor details.
     * @return the VisitorDTO with the authorization details.
     */
    @Override
    public VisitorDTO saveOrUpdateVisitor(VisitorRequestDto visitorRequestDto) {
        VisitorEntity existVisitorEntity =
                visitorRepository.findByDocNumber(visitorRequestDto.getDocNumber());

        UserDto owner = userRestClient.getUserById(visitorRequestDto.getOwnerId());

        VisitorEntity visitorEntity;
        if (Objects.nonNull(existVisitorEntity)) {
            visitorEntity = existVisitorEntity;
        } else {
            visitorEntity = new VisitorEntity();
            visitorEntity.setCreatedDate(LocalDateTime.now());
        }

        visitorEntity.setName(visitorRequestDto.getName());
        visitorEntity.setLastName(visitorRequestDto.getLastname());
        visitorEntity.setDocNumber(visitorRequestDto.getDocNumber());
        visitorEntity.setBirthDate(visitorRequestDto.getBirthDate());
        visitorEntity.setOwnerId(owner.getId());
        visitorEntity.setActive(visitorRequestDto.isActive());
        visitorEntity.setLastUpdatedDate(LocalDateTime.now());
        return modelMapper.map(visitorRepository.save(visitorEntity), VisitorDTO.class);
    }
}
