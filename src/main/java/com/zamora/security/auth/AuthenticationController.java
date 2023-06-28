package com.zamora.security.auth;

import com.zamora.security.config.AuthenticationRequest;
import com.zamora.security.config.AuthenticationResponse;
import com.zamora.security.config.JwtService;
import com.zamora.security.config.RegisterRequest;
import com.zamora.security.repository.UserRepository;
import com.zamora.security.service.AuthenticationService;
import com.zamora.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/registrarUsuario")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        AuthenticationResponse registerUser = authenticationService.registrar(request);
        return new ResponseEntity<AuthenticationResponse>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/Authenticate")
    public ResponseEntity<AuthenticationResponse> generarToken(@RequestBody AuthenticationRequest userAuthenticate) throws Exception {

        AuthenticationResponse authenticateUser = authenticationService.authenticar(userAuthenticate);
        return new ResponseEntity<AuthenticationResponse>(authenticateUser, HttpStatus.CREATED);

    }



}
