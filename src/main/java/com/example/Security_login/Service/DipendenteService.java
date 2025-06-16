package com.example.Security_login.Service;


import com.cloudinary.Cloudinary;
import com.example.Security_login.Dto.DipendenteDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Model.Dipendente;
import com.example.Security_login.Repository.DipendenteRepository;
import com.example.Security_login.Repository.PrenotazioneRepository;
import com.example.Security_login.Repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Dipendente saveDipendente(DipendenteDto dipendenteDto){

        Dipendente dipendente = new Dipendente();
        dipendente.setUserName(dipendenteDto.getUserName());
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setEmail(dipendenteDto.getEmail());

        Dipendente saved = dipendenteRepository.save(dipendente);
        sendMail(saved.getEmail());
        return saved;
    }

    public Page<Dipendente> getAllDipendnete(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente getDipendente(int id) throws NotFoundException {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dipendnete con id" + id + "non presente"));
    }

    public Dipendente updateDipendente(int id,DipendenteDto dipendenteDto) throws NotFoundException {
        Dipendente dipendenteDaAggiornare = getDipendente(id);
        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());
        dipendenteDaAggiornare.setImmagineProfilo(dipendenteDto.getImmagineProfilo());
         return dipendenteRepository.save(dipendenteDaAggiornare);


    }

    public void delelteDipendente(int id) throws NotFoundException {
        Dipendente dipendneteDaCancellare = getDipendente(id);
        dipendenteRepository.delete(dipendneteDaCancellare);

    }

    public String patchDipendente(int id, MultipartFile file) throws NotFoundException, IOException {
        Dipendente dipendenteDaPatchare = getDipendente(id);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");
        dipendenteDaPatchare.setImmagineProfilo(url);
        dipendenteRepository.save(dipendenteDaPatchare);
        return url;
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }





}
