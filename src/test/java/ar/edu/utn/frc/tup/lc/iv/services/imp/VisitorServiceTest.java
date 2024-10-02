package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class VisitorServiceTest {

    @MockBean
    private VisitorRepository visitorRepository;

    @MockBean
    private UserRestClient userRestClient;

    @SpyBean
    private VisitorService visitorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllVisitorsTest() {
        // Given
        VisitorEntity visitorEntity = new VisitorEntity(1L, "juan", "Perez", 40252203L, LocalDate.now(), true, 1L);
        VisitorEntity visitorEntity1 = new VisitorEntity(2L, "joaquin", "Perez", 40252255L, LocalDate.now(), true, 1L);
       // VisitorEntity visitorEntity2 = new VisitorEntity(2L, "joaquin", "Perez", 40252288L, LocalDate.now(), false, 1L);

        List<VisitorEntity> visitorEntityList = Arrays.asList(visitorEntity, visitorEntity1);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName").and(Sort.by("name")));

        Page<VisitorEntity> visitorPage = new PageImpl<>(visitorEntityList, pageable, visitorEntityList.size());

        //when
        when(visitorRepository.findAllByActive(true, pageable)).thenReturn(visitorPage);

        //then
        List<VisitorDTO> listResult = visitorService.getAllVisitors(0, 10);

        assertEquals(2, listResult.size());
        assertEquals("juan", listResult.get(0).getName());
        assertEquals("joaquin", listResult.get(1).getName());

        verify(visitorRepository, times(1)).findAllByActive(true, pageable);
    }

    @Test
    void saverOrUpdateVisitorExistingVisitorTest() {
        //given
        VisitorRequestDto visitorRequestDto =
                new VisitorRequestDto("joaquin","zabala",12345678L,LocalDate.of(2005,3,17),1L,true);

        //when
        VisitorEntity visitorEntity = new VisitorEntity(1L,"","",0L,LocalDate.now(),false,1L);
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(visitorEntity);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userRestClient.getUserById(1L)).thenReturn(userDto);

        VisitorEntity visitorEntitySave = new VisitorEntity(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true,1L);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L,1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequestDto);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }

    @Test
    void saveOrUpdateVisitorNoExistVisitorTest() {
        //given
        VisitorRequestDto visitorRequestDto =
                new VisitorRequestDto("joaquin","zabala",12345678L,LocalDate.of(2005,3,17),1L,true);

        //when
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(null);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userRestClient.getUserById(1L)).thenReturn(userDto);

        VisitorEntity visitorEntitySave = new VisitorEntity(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true,1L);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L,1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequestDto);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }

    @Test
    void getBydocNumberTest() {
        VisitorEntity visitorEntity = new VisitorEntity(1L, "juan", "perez", 40252203L, LocalDate.now(), true, 1L);
        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(visitorEntity);

        VisitorDTO visitorDTO = visitorService.getVisitorByDocNumber(40252203L);
        assertEquals(visitorEntity.getDocNumber(), visitorDTO.getDocNumber());
    }

    @Test
    void getBydocNumberNoExistTest() {
        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(null);

       EntityNotFoundException exception=
               assertThrows(EntityNotFoundException.class,
                       () -> visitorService.getVisitorByDocNumber(40252203L));

       assertEquals("No existe el visitante con el numero de documento 40252203", exception.getMessage());
    }

    @Test
    void deleteVisitorTest() {
        VisitorEntity visitorEntity = new VisitorEntity(1L, "juan", "perez", 40252203L, LocalDate.now(), true, 1L);
        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(visitorEntity);

        VisitorEntity visitorEntitySave = new VisitorEntity(1L, "juan", "perez", 40252203L, LocalDate.now(), false, 1L);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        VisitorDTO visitorDTO = visitorService.deleteVisitor(40252203L);

        assertEquals(visitorEntity.getDocNumber(), visitorDTO.getDocNumber());
        assertFalse(visitorDTO.isActive());
    }

    @Test
    void deleteVisitorNoExistTest() {

        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(null);

        EntityNotFoundException exception =
                Assertions.assertThrows(EntityNotFoundException.class, () -> {
            visitorService.deleteVisitor(40252203L);
        });

        assertEquals("No existe el visitante con el numero de documento 40252203", exception.getMessage());

    }
}