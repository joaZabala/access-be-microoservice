package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VehicleTypesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitAccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class VisitAccessServiceTest {

    @Mock
    VisitAccessRepository visitAccessRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private VisitAccessService visitAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }
    @Test
    void getVisitAccessesByIDTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setAuthRange(null);
        accessesEntity.setEntryDate(LocalDateTime.of(2024,9,29,18,5));
        accessesEntity.setExitDate(LocalDateTime.of(2024,9,29,18,23));
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setName("Carlos");
        visitorEntity.setLastName("Ochoa");
        visitorEntity.setDocNumber(25652321L);
        visitorEntity.setBirthDate(LocalDate.of(1985, 6, 25));
        visitorEntity.setActive(true);
        accessesEntity.setVisitor(visitorEntity);
        accessesEntity.setPlotId(1025L);
        List<AccessesEntity> accessesEntityList = new ArrayList<>();
        accessesEntityList.add(accessesEntity);
        when (visitAccessRepository.getVisitAccessesList(any (Long.class), any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(accessesEntityList);
        assertEquals(visitAccessService.getVisitAccessesByID(1025L,1L,
                LocalDate.of(2024,1,1), LocalDate.of(2024,12,31)).get(0).getLastName(), "Ochoa");
    }

    @Test
    void getVisitAccessesTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setAuthRange(null);
        accessesEntity.setEntryDate(LocalDateTime.of(2024,9,29,18,5));
        accessesEntity.setExitDate(LocalDateTime.of(2024,9,29,18,23));
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setName("Carlos");
        visitorEntity.setLastName("Ochoa");
        visitorEntity.setDocNumber(25652321L);
        visitorEntity.setBirthDate(LocalDate.of(1985, 6, 25));
        visitorEntity.setActive(true);
        accessesEntity.setVisitor(visitorEntity);
        accessesEntity.setPlotId(1025L);
        List<AccessesEntity> accessesEntityList = new ArrayList<>();
        accessesEntityList.add(accessesEntity);
        when (visitAccessRepository.getVisitAccessesList(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(accessesEntityList);
        assertEquals(visitAccessService.getVisitAccesses(1025L,
                LocalDate.of(2024,1,1), LocalDate.of(2024,12,31)).get(0).getLastName(), "Ochoa");
    }

    @Test
    void mapVisitAccessDTOTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        VehicleTypesEntity vehicleTypesEntity = new VehicleTypesEntity();
        vehicleTypesEntity.setVehicleTypeId(1L);
        vehicleTypesEntity.setCarDescription("Automóvil");
        accessesEntity.setVehicleType(vehicleTypesEntity);
        accessesEntity.setAuthRange(null);
        assertEquals(visitAccessService.mapVisitAccessDTO(accessesEntity).getCarDescription(), "Automóvil");
        AuthorizedRangesEntity authorizedRangesEntity = new AuthorizedRangesEntity();
        accessesEntity.setAuthRange(authorizedRangesEntity);
        assertEquals(visitAccessService.mapVisitAccessDTO(accessesEntity).getCarDescription(), "Automóvil");
    }
}