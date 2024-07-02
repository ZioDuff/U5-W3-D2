package JacopoDemaio.gestioneDispositivi_Security.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerConfiguration {
    // creazione classe di configurazione, questa ci servira per comunicare con cloudinary per l'upload delle nostre immagini
        @Bean
//    richiamiamo la classe cloudinary nel nostro metodo e nei parametri vuole i nostri dati presenti nel nostro file .properties
//    ATTENZIONE bisogna esattamente richiamare i nomi giusti, anche un piccolo typo non ci permettere di eseguire il codice
        public Cloudinary uploader(@Value("${cloudinary.name}") String name,
                                   @Value("${cloudinary.secret}") String secret,
                                   @Value("${cloudinary.key}") String key) {
//        cloudinary vuole esattamente una Map in questo caso HashMap
            Map<String, String> configuration = new HashMap<>();
//        ATTENZIONE! anche qua bisogna esattamente inserire questi nomi!!
            configuration.put("cloud_name", name);
            configuration.put("api_key", key);
            configuration.put("api_secret", secret);
            return new Cloudinary(configuration);
        }


    }

