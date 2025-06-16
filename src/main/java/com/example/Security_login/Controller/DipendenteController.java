package com.example.Security_login.Controller;


import com.example.Security_login.Dto.DipendenteDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Exception.ValidationException;
import com.example.Security_login.Model.Dipendente;
import com.example.Security_login.Service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return dipendenteService.saveDipendente(dipendenteDto);
    }

    @GetMapping("")
    public Page<Dipendente> getAllDipendente(@RequestParam(defaultValue = "0")int page,
                                             @RequestParam(defaultValue = "10")int size,
                                             @RequestParam(defaultValue = "id") String sortBy){
        return dipendenteService.getAllDipendnete(page, size, sortBy);
    }

    @PutMapping("/{id}")
    public Dipendente updateDipendente(@PathVariable int id,@RequestBody @Validated DipendenteDto dipendenteDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return dipendenteService.updateDipendente(id,dipendenteDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDipendente(@PathVariable int id) throws NotFoundException {
        dipendenteService.delelteDipendente(id);
    }

    @PatchMapping("/{id}")
    public String patchDipendente(@PathVariable int id, @RequestBody MultipartFile file) throws NotFoundException, IOException {
        return String.valueOf(dipendenteService.patchDipendente(id,file));
    }
}
