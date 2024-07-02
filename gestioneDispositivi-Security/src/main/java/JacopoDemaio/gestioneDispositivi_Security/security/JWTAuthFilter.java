package JacopoDemaio.gestioneDispositivi_Security.security;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.UnauthorizedException;
import JacopoDemaio.gestioneDispositivi_Security.services.DipendenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

// componente necessario per la gestione del filtro per verificare il nostro token
@Component
// va estesa l'interfaccia che ci dara il metodo con override
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private DipendenteService dipendenteService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// come prima cosa bisogna accedere nell header e prenderemo il valore AUTHORIZATION
        String authHeader = request.getHeader("Authorization");
// qui facciamo un controllo per verificare se il nostro token è null oppure se inizia con bearer
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw  new UnauthorizedException("Per favore inserisci correttamente il tuo token");
// qui prenderemo tutto quello che è presente dopo bearer, in questo caso 7 caratteri perchè prendiamo in considerazione 6 lettere + lo spazio, quindi 7
        String accessToken = authHeader.substring(7);
// qui richiamiamo il nostro metodo custom per verificare il token
        jwtTools.verifyToken(accessToken);

        String dipendenteId = jwtTools.extractIdFromToken(accessToken); // <-- tramite il metodo andiamo ad estrarre l'id

        Dipendente current = dipendenteService.findDipendenteById(UUID.fromString(dipendenteId));
//        grazie all'autowired accediamo ai metodi del service del dipendente

        Authentication authentication = new UsernamePasswordAuthenticationToken(current, null, current.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request,response);
    }
// questo è il filtro che ci garantisce che non richiederà il token se c'è quel path che decidiamo noi
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
