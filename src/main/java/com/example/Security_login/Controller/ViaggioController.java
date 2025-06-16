package com.example.Security_login.Controller;


import com.example.Security_login.Dto.ViaggioDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Exception.ValidationException;
import com.example.Security_login.Model.Viaggio;
import com.example.Security_login.Service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio saveViaggio(@RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return viaggioService.saveViaggio(viaggioDto);
    }

    @GetMapping("")
    public Page<Viaggio> getAllViaggio(@RequestParam(defaultValue = "0")int page,
                                             @RequestParam(defaultValue = "10")int size,
                                             @RequestParam(defaultValue = "id") String sortBy){
        return viaggioService.getAllViaggi(page, size, sortBy);
    }

    @PutMapping("/{id}")
    public Viaggio updateViaggio(@PathVariable int id,@RequestBody @Validated ViaggioDto viaggioDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return viaggioService.updateViaggio(id,viaggioDto);
    }

    @DeleteMapping("/{id}")
    public void deleteViaggio(@PathVariable int id) throws NotFoundException {
        viaggioService.deleteViaggio(id);
    }




}
