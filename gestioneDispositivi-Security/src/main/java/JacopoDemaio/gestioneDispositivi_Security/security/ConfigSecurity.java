package JacopoDemaio.gestioneDispositivi_Security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// classe di configurazione che gestira totalmente spring
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // <-- annotazione necessaria per dichiarare le regole di autorizzazione sui singoli endPoint
public class ConfigSecurity {

//    per gestire la nostra chain di sicurezza bisogna creare un bean apposito
//    dove al suo intento andremo a gestire i filtri che ci occorrono
//    e dove è necessario togliere alcune impostazione di default
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity.formLogin(http -> http.disable()); // <-- questo ci fa disabilitare il form precompilato che ci mette a disposizione il framework
        httpSecurity.csrf(http-> http.disable()); // <--  questo disabilita alcune configurazione di sicurezza che al momento non ci servono
        httpSecurity.sessionManagement(http-> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // <-- disabilita le sessioni presenti di deafult perchè non ci serviranno poichè useremo il token
        httpSecurity.authorizeHttpRequests(http-> http.requestMatchers("/**").permitAll()); // <-- questo ci evita di avere dei 401 a ogni richiesta
        return httpSecurity.build();
    }
//    bean necessario per la creazione della nostra hash password
    @Bean
    PasswordEncoder getBCrypt(){
        return new BCryptPasswordEncoder(11);
    }
}
