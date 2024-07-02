package JacopoDemaio.gestioneDispositivi_Security.controller;


import JacopoDemaio.gestioneDispositivi_Security.exceptions.BadRequestException;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DipendenteDTO;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DipendenteLoginDTO;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DipendenteLoginResponseDTO;
import JacopoDemaio.gestioneDispositivi_Security.payloads.NewDipendenteResponseDTO;
import JacopoDemaio.gestioneDispositivi_Security.services.AuthService;
import JacopoDemaio.gestioneDispositivi_Security.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
// per l'autenticazione creiamo un nuovo controller con un path diverso
@RestController
@RequestMapping("/auth")
public class AuthController {
// richiamiamo il service dell'autenticazione
    @Autowired
    private AuthService authService;
// e quello del nostro dipendente
    @Autowired
    private DipendenteService dipendenteService;
// qui faremo un post con path login, questo perchè ci serve per forza un payload da mandare
//    se tutto procede senza errori, questo ci genera il nostro bearer token per poter proseguire con altre chiamate
    @PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody DipendenteLoginDTO payload){
        return new DipendenteLoginResponseDTO(authService.authenticateDipendentiAndGenerateToken(payload));
    }
// qui invece facciamo un'altra post con path diverso per potersi registrare
//    faremo ritornare solamente l'id del nostro dipendente
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteResponseDTO saveDipendente(@RequestBody @Validated DipendenteDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw  new BadRequestException(validationResult.getAllErrors());
        }else return new NewDipendenteResponseDTO(dipendenteService.saveDipendente(payload).getId());
    }

//    le post di login e register sono state spostate perchè abbiamo fatto il filtro in modo tale che con il path /auth non richieda il token
}
