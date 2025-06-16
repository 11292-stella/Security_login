package com.example.Security_login.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.formLogin(http->http.disable()); //serve per creare in automatico una pag di log per adesso non serve

        //serve per evitare utilizzi di sessioni aperte (la sessione viene chiusa in automatico)ma al momento non serve
        httpSecurity.csrf(http->http.disable());

        //pure questo non serve perche è per le sessioni
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        //serve per bloccare richieste che provengono da domini esterni a quelli del servizio(indirizzo ip porta)
        httpSecurity.cors(Customizer.withDefaults());


        //------------------------------------------------


        //autorizza i servizi con i path personalizzati
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/studenti/**").permitAll());

        //permetti il tipo di servizio get/post per qualsiasi endpoint
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST).permitAll());

        //quando decidi quali servizi permettere nega il resto
        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());

        return httpSecurity.build();
    }
}
