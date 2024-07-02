package JacopoDemaio.gestioneDispositivi_Security.repository;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {

}
