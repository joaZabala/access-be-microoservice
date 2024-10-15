package ar.edu.utn.frc.tup.lc.iv.services.imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;

import java.util.List;

import java.util.stream.Collectors;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthRangeService;

/**
 * Service for AuthRange.
*/
@Service
public class AuthRangeService implements IAuthRangeService {

    /**
     * Repository for AuthRange.
     */
    @Autowired
    private AuthRangeRepository authRangeRepository;

    /**
     * Mapper for AuthRange.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all AuthRanges.
     * @return List<AuthRangeDTO>
     */
    @Override
    public List<AuthRangeDTO> getAuthRanges() {
        return authRangeRepository.findAll().stream()
                .map(authRange -> modelMapper.map(authRange, AuthRangeDTO.class))
                .collect(Collectors.toList());
    }

}
