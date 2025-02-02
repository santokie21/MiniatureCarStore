package com.project.carstore.security;

import com.project.carstore.cart.CartException;
import com.project.carstore.exceptions.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO request) throws CustomerException, CartException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/validate")
    public boolean validateUser(@RequestParam("token") String token) {
        try {
            return authenticationService.isValidToken(token);
        } catch (Exception e) {
            return false;
        }
    }
}

