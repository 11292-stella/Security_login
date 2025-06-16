package com.example.Security_login.Service;

import com.example.Security_login.Dto.LoginDto;
import com.example.Security_login.Exception.NotFoundException;
import com.example.Security_login.Model.User;
import com.example.Security_login.Repository.UserRepository;
import com.example.Security_login.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    public String login(LoginDto loginDto) throws  NotFoundException {

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() ->new NotFoundException("Utente con questo username/password non trovato " ));

        if (loginDto.getPassword().equals(user.getPassword())) {

            return jwtTool.createToken(user);

        } else {
            throw new NotFoundException("Utente con questo username/password non trovato");
        }



    }
}
