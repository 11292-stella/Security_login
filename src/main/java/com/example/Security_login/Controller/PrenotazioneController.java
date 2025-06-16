package com.example.Security_login.Controller;


import com.example.Security_login.Dto.PrenotazioneDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Exception.ValidationException;
import com.example.Security_login.Model.Prenotazione;
import com.example.Security_login.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @GetMapping("")
    public Page<Prenotazione> getAllPrenotazioni(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "10")int size,
                                       @RequestParam(defaultValue = "id") String sortBy){
        return prenotazioneService.getAllPrenotazione(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable int id,@RequestBody @Validated PrenotazioneDto prenotazioneDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return prenotazioneService.updatePrenotazione(id,prenotazioneDto);
    }

    @DeleteMapping("/{id}")
    public void deletePrenotazione(@PathVariable int id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }
}
