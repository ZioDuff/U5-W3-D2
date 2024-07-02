package JacopoDemaio.gestioneDispositivi_Security.repository;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
    Optional<Dipendente> findByEmail(String email);
    Optional<Dipendente> findByUsername(String username);
}
