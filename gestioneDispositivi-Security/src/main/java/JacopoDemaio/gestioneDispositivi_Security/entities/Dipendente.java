package JacopoDemaio.gestioneDispositivi_Security.entities;

import JacopoDemaio.gestioneDispositivi_Security.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"password", "role","enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Dipendente implements UserDetails {

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
//    aggiungiamo un ruolo al nostro dipendente per diversificare poi i vari metodi
    @Enumerated(EnumType.STRING)
    private Role role;

    public Dipendente(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
//        stabiliamo che ogni dipendente creato sia sempre prima un user semplice
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        restituisce la lista dei ruoli che puo avere il dipendente
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
