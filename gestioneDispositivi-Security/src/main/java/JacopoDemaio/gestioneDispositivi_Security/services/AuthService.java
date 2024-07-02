package JacopoDemaio.gestioneDispositivi_Security.services;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.UnauthorizedException;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DipendenteLoginDTO;
import JacopoDemaio.gestioneDispositivi_Security.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;


    public String authenticateDipendentiAndGenerateToken(DipendenteLoginDTO payload) {

        Dipendente dipendente = this.dipendenteService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(),dipendente.getPassword())) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedException("credenziali non corrette");
        }
    }
}
