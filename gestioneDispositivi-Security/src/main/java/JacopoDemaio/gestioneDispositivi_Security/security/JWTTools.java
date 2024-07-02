package JacopoDemaio.gestioneDispositivi_Security.security;

import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jtw.secret}")
    private String secret;

    public String createToken(Dipendente dipendente){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // <-- data di emissione del nostro Token in millisecondi
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24 * 7)) // <-- data di scadenza anche essa in millisecondi, va fatto un piccolo calcolo.
                .subject(String.valueOf(dipendente.getId())) // <-- andiamo a prendere l'id del nostro dipendente per assegnare il nostro token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // <-- necessario per creare la nostra signature mettendo il segreto
                .compact(); // <-- uniamo il tutto
    }

    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }catch(Exception ex){
            throw  new UnauthorizedException("Problemi col token! Riprova");
        }
    }

    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
