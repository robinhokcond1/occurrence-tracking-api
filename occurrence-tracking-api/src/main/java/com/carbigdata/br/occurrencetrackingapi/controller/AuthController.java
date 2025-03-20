package com.carbigdata.br.occurrencetrackingapi.controller;

import com.carbigdata.br.occurrencetrackingapi.dto.UserDTO;
import com.carbigdata.br.occurrencetrackingapi.entity.UserEntity;
import com.carbigdata.br.occurrencetrackingapi.repository.UserRepository;
import com.carbigdata.br.occurrencetrackingapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        Optional<UserEntity> user = userRepository.findByUsername(userDTO.getUsername());

        if (user.isPresent() && passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(userDTO.getUsername());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Credenciais inválidas!");
    }
}

