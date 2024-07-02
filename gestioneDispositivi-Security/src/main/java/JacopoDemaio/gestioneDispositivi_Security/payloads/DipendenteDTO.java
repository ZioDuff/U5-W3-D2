package JacopoDemaio.gestioneDispositivi_Security.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotEmpty(message = "Il campo userName è obbligatorio ")
        @Size(min = 4,max = 20,message = "l'userName deve essere compreso tra i 4 e i 20 caratteri ")
        String username,
        @NotEmpty(message = "il campo nome è obbligatorio ")
        @Size(min = 4,max = 20, message ="Il nome deve essere compreso tra i 4 e i 20 caratteri " )
        String name,
        @NotEmpty(message = "il campo cognome è obbligatorio ")
        @Size(min = 4,max = 20, message ="Il cognome deve essere compreso tra i 4 e i 20 caratteri " )
        String surname,
        @NotEmpty(message = "il campo email è obbligatorio ")
        @Email
        String email,
        @NotEmpty(message = "Il campo password è obbligatorio")
        @Size(min = 8,max = 16,message = "La password deve essere compresa tra gli 8 e i 16 caratteri")
        String password

) {
}
