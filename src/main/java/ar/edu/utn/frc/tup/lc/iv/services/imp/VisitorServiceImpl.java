package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserService;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorsEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorsRepository;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VisitorsRepository visitorsRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     *  service of the user.
     */
    @Autowired
    private UserService userService;

    /**
     * Retrieves all authorized entities from the repository and maps them to
     * a list of {@link VisitorDTO}.
     *
     * @return a list of AuthorizedDTO representing the authorized entities.
     */
    @Override
    public List<VisitorDTO> getAllVisitors() {
        return visitorsRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, VisitorDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Creates or updates a visitor.
     *
     * @param  visitorRequestDto request DTO with visitor details.
     * @return the VisitorDTO with the authorization details.
     */
    @Override
    public VisitorDTO createNewVisitor(VisitorRequestDto visitorRequestDto) {
        VisitorsEntity existVisitordEntity =
                visitorsRepository.findByDocNumber(visitorRequestDto.getDocNumber());
        UserDto user = userService.getUserById(visitorRequestDto.getIdAuthorizedUser());

        VisitorsEntity visitorEntity;

        if (Objects.nonNull(existVisitordEntity)) {
            visitorEntity = existVisitordEntity;
        } else {
            visitorEntity = new VisitorsEntity();
            visitorEntity.setCreatedDate(LocalDateTime.now());
        }

        visitorEntity.setName(visitorRequestDto.getName());
        visitorEntity.setBirthDate(visitorRequestDto.getBirthDate());
        visitorEntity.setLastName(visitorRequestDto.getLastname());
        visitorEntity.setDocNumber(visitorRequestDto.getDocNumber());
        visitorEntity.setLastUpdatedDate(LocalDateTime.now());
        visitorEntity.setCreatedUser(user.getId());
        return modelMapper.map(visitorsRepository.save(visitorEntity), VisitorDTO.class);
    }
}
