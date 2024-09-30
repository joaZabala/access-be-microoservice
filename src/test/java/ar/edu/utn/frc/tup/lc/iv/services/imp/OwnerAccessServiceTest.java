package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.OwnerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerAccessRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OwnerAccessServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TypeMap<AccessesEntity, OwnerAccessDTO> typeMap;

    @Mock
    private OwnerAccessRepository ownerAccessRepository;

    @InjectMocks
    private OwnerAccessService ownerAccessService;

    private AccessesEntity accessesEntity;
    private OwnerAccessDTO expectedOwnerAccessDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        VisitorEntity visitor = new VisitorEntity();
        visitor.setName("Arturo");
        visitor.setLastName("Gonzalez");

        AuthorizedRangesEntity authorizedRange = new AuthorizedRangesEntity();
        authorizedRange.setVisitorId(visitor);

        accessesEntity = new AccessesEntity();
        accessesEntity.setAuthRange(authorizedRange);

        expectedOwnerAccessDTO = new OwnerAccessDTO();
        expectedOwnerAccessDTO.setName("Renzo");
        expectedOwnerAccessDTO.setLastName("Aguirre");

        when(modelMapper.typeMap(AccessesEntity.class, OwnerAccessDTO.class)).thenReturn(typeMap);
        when(typeMap.addMapping(any(), any())).thenReturn(typeMap);
        when(modelMapper.map(any(AccessesEntity.class), eq(OwnerAccessDTO.class)))
                .thenReturn(expectedOwnerAccessDTO);
    }

    @Test
    void testMapOwnerAccessDto() {
        OwnerAccessDTO actualOwnerAccessDTO = ownerAccessService.mapOwnerAccessDto(accessesEntity);

        assertEquals(expectedOwnerAccessDTO.getName(), actualOwnerAccessDTO.getName());
        assertEquals(expectedOwnerAccessDTO.getLastName(), actualOwnerAccessDTO.getLastName());

        verify(modelMapper, times(1)).map(any(AccessesEntity.class), eq(OwnerAccessDTO.class));
    }

    @Test
    void testGetOwnerAccessList() {
        Long plotId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<AccessesEntity> mockAccessesEntities = new ArrayList<>();
        mockAccessesEntities.add(accessesEntity);

        List<OwnerAccessDTO> expectedOwnerAccessDTOs = new ArrayList<>();
        expectedOwnerAccessDTOs.add(expectedOwnerAccessDTO);

        when(ownerAccessRepository.getOwnerAccessesList(eq(plotId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockAccessesEntities);

        when(modelMapper.map(any(AccessesEntity.class), eq(OwnerAccessDTO.class)))
                .thenReturn(expectedOwnerAccessDTO);

        List<OwnerAccessDTO> actualOwnerAccessDTOs = ownerAccessService.getOwnerAcessList(plotId, dateFrom, dateTo);

        assertEquals(expectedOwnerAccessDTOs.size(), actualOwnerAccessDTOs.size());
        assertEquals(expectedOwnerAccessDTOs.get(0).getName(), actualOwnerAccessDTOs.get(0).getName());
        assertEquals(expectedOwnerAccessDTOs.get(0).getLastName(), actualOwnerAccessDTOs.get(0).getLastName());

        verify(ownerAccessRepository, times(1)).getOwnerAccessesList(plotId, dateFrom.atStartOfDay(),
                dateTo.atStartOfDay());
    }
}