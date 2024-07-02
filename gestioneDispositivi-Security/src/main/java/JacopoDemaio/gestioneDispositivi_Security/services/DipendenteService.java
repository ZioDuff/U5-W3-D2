package JacopoDemaio.gestioneDispositivi_Security.services;


import JacopoDemaio.gestioneDispositivi_Security.entities.Dipendente;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.BadRequestException;
import JacopoDemaio.gestioneDispositivi_Security.exceptions.NotFoundException;
import JacopoDemaio.gestioneDispositivi_Security.payloads.DipendenteDTO;
import JacopoDemaio.gestioneDispositivi_Security.repository.DipendenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

//    metodo per salvare l'utente
    public Dipendente saveDipendente(DipendenteDTO payload){
//        controllo per verificare se è gia esistente un email
        dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {
            throw  new BadRequestException("Attenzione! l'email: "+ payload.email() + " è gia in uso");
        });
//        controllo per verificare se l'userName è gia presente
        dipendenteRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
            throw  new BadRequestException("Attenzione! l'username: "+ payload.username() + " è gia in uso");
        });

        Dipendente dipendente = new Dipendente(payload.username(), payload.name(),payload.surname(), payload.email(),payload.password());

        dipendente.setAvatarURL("https://ui-avatars.com/api/?name="+ payload.name()+ "+"+ payload.surname());

        return dipendenteRepository.save(dipendente);
    }

//    metodo per tornare una tutti i dipendenti, uso page per gestire e limitare la visualizzazione dei dipendenti
    public Page<Dipendente> getDipendenteList(int page, int size, String sortedBy){
        if (size > 20) size= 20;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortedBy));
        return dipendenteRepository.findAll(pageable);
    }

//    metodo per tornare un singolo dipendente
    public Dipendente findDipendenteById(UUID dipendenteId){
        Dipendente found = dipendenteRepository.findById(dipendenteId).orElseThrow(()-> new NotFoundException(dipendenteId));
        return found;
    }

//    metodo per la modifica del dipendente
    public Dipendente findDipendenteByIdAndUpdate(UUID dipendenteId,Dipendente payload){
        Dipendente found = dipendenteRepository.findById(dipendenteId).orElseThrow(()-> new NotFoundException(dipendenteId));
        found.setUsername(payload.getUsername());
        found.setName(payload.getName());
        found.setSurname(payload.getSurname());
        found.setEmail(payload.getEmail());
        found.setPassword(payload.getPassword());
        found.setAvatarURL("https://ui-avatars.com/api/?name="+ payload.getName()+ "+"+ payload.getSurname());
        return dipendenteRepository.save(found);
    }

//    metodo per eliminare un dipendente dal db
    public void findDipendenteByIdAndDelete(UUID dipendenteId){
        Dipendente found = dipendenteRepository.findById(dipendenteId).orElseThrow(()-> new NotFoundException(dipendenteId));
        dipendenteRepository.delete(found);
    }

    public Dipendente  uploadImage(MultipartFile file, UUID dipendenteId) throws IOException {
        String img = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        Dipendente found = dipendenteRepository.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        found.setAvatarURL(img);
        dipendenteRepository.save(found);
        return found;
    }

    public Dipendente findByEmail(String email){
        return  dipendenteRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("il dipendente con email: " + email + " non è stato trovato"));
    }
}
