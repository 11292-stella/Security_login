package com.example.Security_login.Repository;


import com.example.Security_login.Model.Dipendente;
import com.example.Security_login.Model.Prenotazione;
import com.example.Security_login.Model.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {

    List<Prenotazione> findByDipendenteAndViaggio(Dipendente dipendente, Viaggio viaggio);

}
