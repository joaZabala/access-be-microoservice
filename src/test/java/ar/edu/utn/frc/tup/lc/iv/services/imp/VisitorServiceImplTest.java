package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserService;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
class VisitorServiceImplTest {

    @MockBean
    private VisitorRepository visitorRepository;

    @MockBean
    private UserService userService;

    @SpyBean
    private VisitorServiceImpl visitorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllVisitors() {

        VisitorEntity visitorEntity = new VisitorEntity(1L,"juan","perez",40252203L
                , LocalDate.now(),true,1L);

        VisitorEntity visitorEntity1 = new VisitorEntity(1L,"joaquin","perez",40252255L
                , LocalDate.now(),true,1L);

        List<VisitorEntity> visitorEntityList = new ArrayList<>();
        visitorEntityList.add(visitorEntity);
        visitorEntityList.add(visitorEntity1);

        when(visitorRepository.findAll()).thenReturn(visitorEntityList);

        List<VisitorDTO> listResult =  visitorService.getAllVisitors();

        assertEquals(listResult.size() , 2);
    }

    @Test
    void saverOrUpdateVisitorExistingVisitor() {
        //given
        VisitorRequestDto visitorRequestDto =
                new VisitorRequestDto("joaquin","zabala",12345678L,LocalDate.of(2005,3,17),1L,true);

        //when
        VisitorEntity visitorEntity = new VisitorEntity(1L,"","",0L,LocalDate.now(),false,1L);
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(visitorEntity);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userService.getUserById(1L)).thenReturn(userDto);

        VisitorEntity visitorEntitySave = new VisitorEntity(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true,1L);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequestDto);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }

    @Test
    void saveOrUpdateVisitorNoExistVisitor() {
        //given
        VisitorRequestDto visitorRequestDto =
                new VisitorRequestDto("joaquin","zabala",12345678L,LocalDate.of(2005,3,17),1L,true);

        //when
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(null);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userService.getUserById(1L)).thenReturn(userDto);

        VisitorEntity visitorEntitySave = new VisitorEntity(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true,1L);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L,"joaquin","zabala",12345678L,LocalDate.of(2005,3,17),true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequestDto);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }


}