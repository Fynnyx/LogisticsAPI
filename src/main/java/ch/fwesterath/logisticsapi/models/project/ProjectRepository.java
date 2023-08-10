package ch.fwesterath.logisticsapi.models.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT p FROM Project p WHERE p.keyName = ?1")
    Project findByKeyName(String keyName);
}
