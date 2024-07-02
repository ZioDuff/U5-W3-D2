package JacopoDemaio.gestioneDispositivi_Security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dipendente {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String name;

    private String surname;

    private String email;
// aggiunto l'attributo password per fare la verifica del dipendente
    private String password;

    private String avatarURL;

    public Dipendente(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
