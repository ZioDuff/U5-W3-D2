package JacopoDemaio.gestioneDispositivi_Security.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    //    attributo necessario per gestire i messaggi presenti nel nostro DTO
    private List<ObjectError> errorList;

    public BadRequestException(String message) {
        super(message);
    }
    // secondo costruttore in caso ci dovesse servire la nostra lista di errori
    public BadRequestException(List<ObjectError> errorList){
        super("errori di validazione del payload");
        this.errorList= errorList;
    }
}
