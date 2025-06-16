package com.example.Security_login.Service;


import com.example.Security_login.Dto.ViaggioDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Model.Viaggio;
import com.example.Security_login.Repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {


    @Autowired
    private ViaggioRepository viaggioRepository;


    public Viaggio saveViaggio(ViaggioDto viaggioDto){
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setData(viaggioDto.getData());
        viaggio.setStato(viaggioDto.getStato());
        return viaggioRepository.save(viaggio);

    }

    public Page<Viaggio> getAllViaggi(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    public Viaggio getViaggio(int id)throws NotFoundException {
        return viaggioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaggio con id" + id + "non presente"));
    }

    public Viaggio updateViaggio(int id, ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggioDaAggiornare = getViaggio(id);

        viaggioDaAggiornare.setData(viaggioDto.getData());
        viaggioDaAggiornare.setDestinazione(viaggioDto.getDestinazione());
        viaggioDaAggiornare.setStato(viaggioDto.getStato());

        return viaggioRepository.save(viaggioDaAggiornare);
    }

    public void deleteViaggio(int id) throws NotFoundException {
        Viaggio viaggioDaCancellare = getViaggio(id);
        viaggioRepository.delete(viaggioDaCancellare);
    }



}
