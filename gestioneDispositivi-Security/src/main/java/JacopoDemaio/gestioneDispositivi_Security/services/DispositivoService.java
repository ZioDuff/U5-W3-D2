package JacopoDemaio.gestioneDispositivi_Security.services;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dispositivo;
import JacopoDemaio.gestioneDispositivi_Security.enums.StatoDispositivo;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.NotFoundException;
import JacopoDemaio.gestioneDispositivi_Security.payloads.AssignDispositivoDipendenteDTO;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DispositivoDTO;
import JacopoDemaio.gestioneDispositivi_Security.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DipendenteService dipendenteService;


//    metodo per salvare il dispositivo
    public Dispositivo saveDispositivo(DispositivoDTO payload){

        Dispositivo dispositivo = new Dispositivo(payload.tipoDispositivo(),payload.statoDispositivo(),null);

        return dispositivoRepository.save(dispositivo);
    }

//    metodo per tonare tutti i dispositivi
    public Page<Dispositivo> getDispositivoList(int page, int size, String sortedBy){
        if(size >20) size = 20;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortedBy));
        return dispositivoRepository.findAll(pageable);
    }

//    metodo per tornare un singolo elemento
    public Dispositivo findDispositivoById(UUID dispositivoId){
        return dispositivoRepository.findById(dispositivoId).orElseThrow(()-> new NotFoundException(dispositivoId));
    }

//    metodo per modificare il dispositivo
    public Dispositivo findDispositivoByIdAndUpdate(UUID dispositivoId, DispositivoDTO payload){
        Dispositivo found = this.findDispositivoById(dispositivoId);
        found.setTipoDispositivo(payload.tipoDispositivo());
        found.setStatoDispositivo(payload.statoDispositivo());
        return dispositivoRepository.save(found);

    }

//    metodo per eliminare un singolo dispositivo
    public void findDispositivoByIdAndDelete(UUID dispositivoId){
        dispositivoRepository.delete(this.findDispositivoById(dispositivoId));
    }


//    assegnazione dispositivo a dipendente
//    public Dispositivo assignDispositivo(UUID dispositivoId, UUID dipendenteId){
//        Dispositivo found = this.findDispositivoById(dispositivoId);
//        Dipendente dipendente = dipendenteService.findDipendenteById(dipendenteId);
//        found.setDipendente(dipendente);
//        found.setStatoDispositivo(StatoDispositivo.ASSEGNATO);
//        return this.dispositivoRepository.save(found);
//    }

    public Dispositivo assignDispositivo(UUID dispositivoId, AssignDispositivoDipendenteDTO payload){
        Dispositivo found = this.findDispositivoById(dispositivoId);

        found.setDipendente(dipendenteService.findDipendenteById(payload.dipendenteId()));
        found.setStatoDispositivo(StatoDispositivo.ASSEGNATO);
        return this.dispositivoRepository.save(found);
    }


}
