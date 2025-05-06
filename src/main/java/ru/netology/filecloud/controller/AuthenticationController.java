package ru.netology.filecloud.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.filecloud.request.AuthRequest;
import ru.netology.filecloud.response.AuthResponse;
import ru.netology.filecloud.security.JWTUtil;


@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtTokenUtil;

    //Конфигураия входв и выхода пользователя на эндпоинтах, присвоение токена
    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    public Object createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest
                        .getLogin(), authRequest.getPassword()));

        if (authentication == null) {
            //если аутентификация не пройдена
            throw new BadCredentialsException("Bad credentials");
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> exit() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}