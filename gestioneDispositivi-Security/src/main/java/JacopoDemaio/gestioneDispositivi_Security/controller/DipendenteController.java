package JacopoDemaio.gestioneDispositivi_Security.controller;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import JacopoDemaio.gestioneDispositivi_Security.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

//    REST



    @GetMapping
    public Page<Dipendente> getDipendenteList(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortedBy){
        return dipendenteService.getDipendenteList(page,size,sortedBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findDipendenteById(@PathVariable UUID dipendenteId){
        return dipendenteService.findDipendenteById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findDipendenteByIdAndUpdate(@PathVariable UUID dipendenteId,@RequestBody Dipendente payload){
        return dipendenteService.findDipendenteByIdAndUpdate(dipendenteId,payload);
    }
    //
    @DeleteMapping("/{dipendenteId}")
    public void findDipendenteByIdAndDelete(@PathVariable UUID dipendenteId){
        dipendenteService.findDipendenteByIdAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    public Dipendente uploadAvatar (@PathVariable UUID dipendenteId, @RequestParam("avatar") MultipartFile file ) throws IOException {
        return dipendenteService.uploadImage(file,dipendenteId);
    }

}
