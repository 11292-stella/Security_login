package com.example.Security_login.Service;


import com.example.Security_login.Dto.PrenotazioneDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Exception.ValidationException;
import com.example.Security_login.Model.Dipendente;
import com.example.Security_login.Model.Prenotazione;
import com.example.Security_login.Model.Viaggio;
import com.example.Security_login.Repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private ViaggioService viaggioService;

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException, ValidationException {
        Dipendente dipendente = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
        Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());

        List<Prenotazione> prenotazioniEsistenti = prenotazioneRepository.findByDipendenteAndViaggio(dipendente, viaggio);
        if (!prenotazioniEsistenti.isEmpty()) {
            throw new ValidationException("Il dipendente ha già prenotato questo viaggio");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazione.setNote(prenotazioneDto.getNote());

        Prenotazione nuovaPrenotazione = prenotazioneRepository.save(prenotazione);

        sendMailConferma(dipendente.getEmail(), viaggio.getDestinazione());
        return nuovaPrenotazione;



    }

    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con " + id + "non presente"));
    }

    public Prenotazione updatePrenotazione(int id , PrenotazioneDto prenotazioneDto) throws NotFoundException {
        Dipendente dipendente = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
        Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());

        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        prenotazioneDaAggiornare.setViaggio(viaggio);
        prenotazioneDaAggiornare.setDipendente(dipendente);
        prenotazioneDaAggiornare.setNote(prenotazioneDto.getNote());
        prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());

        return prenotazioneRepository.save(prenotazioneDaAggiornare);
        }

    public Page<Prenotazione> getAllPrenotazione(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public void deletePrenotazione(int id) throws NotFoundException {
        Prenotazione prenotazioneDaCancellare = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneDaCancellare);
    }

    private void sendMailConferma(String email, String destinazione) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Conferma Prenotazione Viaggio");
        message.setText("La prenotazione per il viaggio verso " + destinazione + " è stata confermata con successo.");
        javaMailSender.send(message);
    }


    }




