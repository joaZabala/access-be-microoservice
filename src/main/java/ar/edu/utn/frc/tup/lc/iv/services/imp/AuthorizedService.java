package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthorizedDTO;
import lombok.NoArgsConstructor;

/**
 * Service implementation for handling operations related to Authorized
 * entities.
 * This service converts Authorized entities to AuthorizedDTOs.
 */
@NoArgsConstructor
@Service
public class AuthorizedService {
    /**
     * Repository to access Authorized entities from the database.
     */
    @Autowired
    private VisitorsRepository authorizedRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all authorized entities from the repository and maps them to
     * a list of {@link AuthorizedDTO}.
     *
     * @return a list of AuthorizedDTO representing the authorized entities.
     */
    public List<AuthorizedDTO> getAuthorized() {
        return authorizedRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, AuthorizedDTO.class))
                .collect(Collectors.toList());
    }
}
