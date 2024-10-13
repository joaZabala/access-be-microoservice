package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    @MockBean
    private VisitorService visitorService;

    @MockBean
    private AuthRepository authRepository;

    @MockBean
    private AuthRangeRepository authRangeRepository;

    @MockBean
    private VisitorRepository visitorRepository;

    @MockBean
    private AuthorizedRangesService authorizedRangesService;

    @SpyBean
    private AuthService authService;

    private VisitorEntity visitorEntity;
    private AuthEntity authEntity;
    private List<AuthEntity> authEntities;
    private AuthRangeEntity authRangeEntity;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        visitorEntity = new VisitorEntity();
        visitorEntity.setDocNumber(123456L);

        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setVisitor(visitorEntity);

        authRangeEntity = new AuthRangeEntity();
        authRangeEntity.setAuthRangeId(1L);
        authRangeEntity.setAuthId(authEntity);

        authEntities = new ArrayList<>();
        authEntities.add(authEntity);
    }

    @Test
    void getAuthsByDocNumberTest(){
        Long docNumber = 123456L;

        // Mocking the repository calls
        when(visitorRepository.findByDocNumber(docNumber)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeRepository.findByAuthId(authEntity)).thenReturn(Collections.singletonList(authRangeEntity));

        List<AuthDTO> result = authService.getAuthsByDocNumber(docNumber);

        assertNotNull(result);
        assertEquals(1, result.size());

        AuthDTO authDTO = result.get(0);
        assertEquals(authEntity.getAuthId(), authDTO.getAuthId());

        assertNotNull(authDTO.getAuthRanges());
        assertEquals(1, authDTO.getAuthRanges().size());
        assertEquals(authRangeEntity.getAuthRangeId(), authDTO.getAuthRanges().get(0).getAuthRangeId());

        verify(visitorRepository).findByDocNumber(docNumber);
        verify(authRepository).findByVisitor(visitorEntity);
        verify(authRangeRepository).findByAuthId(authEntity);
    }
    @Test
    public void findExistingAuthorizationTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);

        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(1L);
        visitorAuthRequest.setVisitorRequest(visitorRequest);

        List<AuthDTO> authDTOList = new ArrayList<>();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setVisitorType(VisitorType.OWNER);
        authDTO.setAuthRanges(new ArrayList<>());
        authDTO.setVisitor(new VisitorDTO());
        authDTOList.add(authDTO);

        when(authService.getAuthsByDocNumber(1L)).thenReturn(authDTOList);

        Method method = AuthService.class.getDeclaredMethod("findExistingAuthorization", VisitorAuthRequest.class);
        method.setAccessible(true);
        Optional<AuthDTO> result = (Optional<AuthDTO>) method.invoke(authService, visitorAuthRequest);

        // Verificar los resultados
        assertTrue(result.isPresent());
        assertEquals(authDTO, result.get());
    }


   @Test
    void updateAuthorizationTest(){


   }
}


