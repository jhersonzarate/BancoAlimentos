error id: file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/controller/AuthController.java:_empty_/HttpStatus#
file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/controller/AuthController.java
empty definition using pc, found symbol in pc: _empty_/HttpStatus#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1983
uri: file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/controller/AuthController.java
text:
```scala
package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.AuthDTO;
import com.bancoalimentos.backend.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "https://banco-alimentos-mcrn.vercel.app",
    "http://localhost:4200"
})
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        try {
            AuthDTO.AuthResponse response = authService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        try {
            AuthDTO.AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String auth) {
        // Verificación simple: si existe el header con "Bearer <token>" es válido
        if (auth != null && auth.startsWith("Bearer ") && auth.length() > 7) {
            return ResponseEntity.ok(Map.of("valid", true));
        }
        return ResponseEntity.status(HttpStat@@us.UNAUTHORIZED).body(Map.of("valid", false));
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/HttpStatus#