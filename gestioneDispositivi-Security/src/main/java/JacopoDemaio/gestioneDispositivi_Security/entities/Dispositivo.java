package JacopoDemaio.gestioneDispositivi_Security.entities;


import JacopoDemaio.gestioneDispositivi_Security.enums.StatoDispositivo;
import JacopoDemaio.gestioneDispositivi_Security.enums.TipoDispositivo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "dispositivi")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dispositivo {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_di_dispositivo")
    private TipoDispositivo tipoDispositivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_del_dispositivo")
    private StatoDispositivo statoDispositivo;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    public Dispositivo(TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo, Dipendente dipendente) {
        this.tipoDispositivo = tipoDispositivo;
        this.statoDispositivo = statoDispositivo;
        this.dipendente = null;
    }
}
