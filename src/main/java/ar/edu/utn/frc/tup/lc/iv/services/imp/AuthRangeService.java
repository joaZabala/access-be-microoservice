package ar.edu.utn.frc.tup.lc.iv.services.imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;

import java.util.List;

import java.util.stream.Collectors;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthRangeService;

@Service
public class AuthRangeService implements IAuthRangeService {

    @Autowired
    private AuthRangeRepository authRangeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuthRangeDTO> getAuthRanges() {
        return authRangeRepository.findAll().stream()
                .map(authRange -> modelMapper.map(authRange, AuthRangeDTO.class))
                .collect(Collectors.toList());
    }

}
