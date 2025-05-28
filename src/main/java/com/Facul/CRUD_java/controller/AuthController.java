package com.Facul.CRUD_java.controller;

import com.Facul.CRUD_java.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody EmailRequest request) {
        boolean enviado = authService.enviarCodigoParaEmail(request.getEmail());
        return enviado ? "Código enviado com sucesso" : "Email não cadastrado";
    }

    @PostMapping("/verificar-codigo")
    public String verificarCodigo(@RequestBody CodigoRequest request) {
        boolean valido = authService.verificarCodigo(request.getEmail(), request.getCodigo());
        return valido ? "Código válido" : "Código inválido";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean sucesso = authService.redefinirSenha(request.getEmail(), request.getNovaSenha());
        return sucesso ? "Senha atualizada com sucesso" : "Erro ao atualizar senha";
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.loginComJwt(request.getEmail(), request.getSenha());

        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}

// DTOs
class EmailRequest {
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

class CodigoRequest {
    private String email;
    private String codigo;
    public String getEmail() { return email; }
    public String getCodigo() { return codigo; }
    public void setEmail(String email) { this.email = email; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}

class ResetPasswordRequest {
    private String email;
    private String novaSenha;
    public String getEmail() { return email; }
    public String getNovaSenha() { return novaSenha; }
    public void setEmail(String email) { this.email = email; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
}

class LoginRequest {
    private String email;
    private String senha;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
