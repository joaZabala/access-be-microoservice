package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing visit access records from the database.
 */
@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {


    /**
     * find Authorization by visitor.
     * @param visitor visitor
     * @return auth list
     */
    //find by doc number
    List<AuthEntity> findByVisitor(VisitorEntity visitor);

}

