package JacopoDemaio.gestioneDispositivi_Security.controller;

import JacopoDemaio.gestioneDispositivi_Security.entities.Dispositivo;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.BadRequestException;
import JacopoDemaio.gestioneDispositivi_Security.payloads.AssignDispositivoDipendenteDTO;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DispositivoDTO;
import JacopoDemaio.gestioneDispositivi_Security.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

//    REST

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivo saveDispositivo(@RequestBody @Validated DispositivoDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw  new BadRequestException(validationResult.getAllErrors());
        }else  return dispositivoService.saveDispositivo(payload);
    }

    @GetMapping
    public Page<Dispositivo> getDispositivoList(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortedBy){
        return dispositivoService.getDispositivoList(page,size,sortedBy);
    }

    @GetMapping("/{dispositivoId}")
    public Dispositivo findDispositivoById(@PathVariable UUID dispositivoId){
        return dispositivoService.findDispositivoById(dispositivoId);
    }

    @PutMapping("/{dispositivoId}")
    public Dispositivo findDispositivoByIdAndUpdate(@PathVariable UUID dispositivoId,@RequestBody @Validated  DispositivoDTO payload , BindingResult validationResult){

            if (validationResult.hasErrors()){
                throw new BadRequestException(validationResult.getAllErrors());
            }else return dispositivoService.findDispositivoByIdAndUpdate(dispositivoId,payload);



    }

    @DeleteMapping("/{dispositivoId}")
    public void findDispositivoByIdAndDelete(@PathVariable UUID dispositivoId){
        dispositivoService.findDispositivoByIdAndDelete(dispositivoId);
    }

    @PatchMapping("/{dispositivoId}/dipendente")
    public Dispositivo updateDispositivo (@PathVariable UUID dispositivoId, @RequestBody AssignDispositivoDipendenteDTO payload){
        return dispositivoService.assignDispositivo(dispositivoId,payload);
    }
}
