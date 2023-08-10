package ch.fwesterath.logisticsapi.auth.apikey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    @Query("SELECT a FROM ApiKey a WHERE a.key = ?1 AND a.active = ?2")
    Optional<ApiKey> findByKeyAndActive(String key, boolean active);

    Optional<ApiKey> findByKey(String key);
}
