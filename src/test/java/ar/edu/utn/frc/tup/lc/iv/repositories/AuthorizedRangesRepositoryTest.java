package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import jakarta.transaction.Transactional;
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
    AuthorizedRangesRepository authorizedRangesRepository;

    //simula base de datos
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(100L);
        visitorEntity.setDocNumber(12345678L);
        visitorEntity = testEntityManager.merge(visitorEntity);

        AuthorizedRangesEntity authorizedRangesEntity = new AuthorizedRangesEntity();
        authorizedRangesEntity.setDateFrom(LocalDate.of(2024, 10, 1));
        authorizedRangesEntity.setDateTo(LocalDate.of(2024, 10, 2));
        authorizedRangesEntity.setActive(true);
        authorizedRangesEntity.setDays("MONDAY");
        authorizedRangesEntity.setVisitorId(visitorEntity); // This should now be managed
        authorizedRangesEntity.setHourFrom(LocalTime.of(9, 0));
        authorizedRangesEntity.setHourTo(LocalTime.of(17, 0));
        authorizedRangesEntity.setPlotId(1L);

        testEntityManager.persist(authorizedRangesEntity);
        testEntityManager.flush();
    }

    @Test
    void hasInvitation() {
        assertTrue(authorizedRangesRepository.hasInvitation(
                LocalDate.of(2024, 10, 1), LocalTime.of(9, 1),
                12345678L, "MONDAY"));
    }

    @Test
    void hasInvitationNo() {
        assertFalse(authorizedRangesRepository.hasInvitation(
                LocalDate.of(2024, 10, 3), LocalTime.of(9, 0),
                12345678L, "MONDAY"));
    }

}