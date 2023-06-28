package com.zamora.security.service;

import com.zamora.security.config.AuthenticationRequest;
import com.zamora.security.config.AuthenticationResponse;
import com.zamora.security.config.JwtService;
import com.zamora.security.config.RegisterRequest;
import com.zamora.security.repository.UserRepository;
import com.zamora.security.user.Rol;
import com.zamora.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse registrar(RegisterRequest request){
        var user = User.builder()
                .apellidoPaterno(request.getFirstname())
                .apellidoMaterno(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.USER)
                .build();
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                        .token(jwt)
                        .build();
    }

    public AuthenticationResponse authenticar(AuthenticationRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        var user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
               .token(jwt)
               .build();
    }



}
