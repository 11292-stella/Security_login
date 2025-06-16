package com.example.Security_login.Controller;

import com.example.Security_login.Dto.LoginDto;
import com.example.Security_login.Dto.UserDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Exception.ValidationException;
import com.example.Security_login.Model.User;
import com.example.Security_login.Service.AuthService;
import com.example.Security_login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {

        if(bindingResult.hasErrors()){
            throw new ValidationException((bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()))
                    .reduce("",(s,e)->s+e));
        }
        return userService.saveUser(userDto);
    }



    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException((bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()))
                    .reduce("",(s,e)->s+e));
        }

        /*
         * verificare che l'utente esiste
         * se l'utente non esisite lancia un eccezione
         * se l'utente esisite genera il token i inviarlo al client
         */

        return authService.login(loginDto);

    }



}
