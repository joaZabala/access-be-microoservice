package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AuthorizedRangesRepositoryTest {

    @Autowired
    AuthRangeRepository authRangeRepository;

    //simula base de datos
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(100L);
        visitorEntity.setDocNumber(12345678L);
        visitorEntity = testEntityManager.merge(visitorEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(visitorEntity);
        authEntity.setActive(true);
        authEntity = testEntityManager.merge(authEntity);

        AuthRangeEntity authRangeEntity = new AuthRangeEntity();
        authRangeEntity.setDateFrom(LocalDate.of(2024, 10, 1));
        authRangeEntity.setDateTo(LocalDate.of(2024, 10, 2));
        authRangeEntity.setActive(true);
        authRangeEntity.setDaysOfWeek("MONDAY");
        authRangeEntity.setAuthId(authEntity);
        authRangeEntity.setHourFrom(LocalTime.of(9, 0));
        authRangeEntity.setHourTo(LocalTime.of(17, 0));
//        authRangeEntity.setPlotId(1L);

        testEntityManager.persist(authRangeEntity);
        testEntityManager.flush();
    }

    @Test
    void hasInvitation() {
//        assertTrue(authorizedRangesRepository.hasInvitation(
//                LocalDate.of(2024, 10, 1), LocalTime.of(9, 1),
//                12345678L, "MONDAY"));
    }

    @Test
    void hasInvitationNo() {
//        assertFalse(authorizedRangesRepository.hasInvitation(
//                LocalDate.of(2024, 10, 3), LocalTime.of(9, 0),
//                12345678L, "MONDAY"));
    }

}