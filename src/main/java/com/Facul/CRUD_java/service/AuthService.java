package com.Facul.CRUD_java.service;

import com.Facul.CRUD_java.model.User;
import com.Facul.CRUD_java.repository.UserRepository;
import com.Facul.CRUD_java.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean enviarCodigoParaEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        String codigo = gerarCodigo();
        User user = userOpt.get();
        user.setCodigoRecuperacao(codigo);
        userRepository.save(user);

        System.out.println("Código enviado: " + codigo);
        return true;
    }

    public boolean verificarCodigo(String email, String codigo) {
        return userRepository.findByEmailAndCodigoRecuperacao(email, codigo).isPresent();
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setSenha(encoder.encode(novaSenha)); // agora com criptografia
        user.setCodigoRecuperacao(null);
        userRepository.save(user);
        return true;
    }
    private String gerarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // 6 dígitos
        return String.valueOf(codigo);
    }

    public String loginComJwt(String email, String senhaDigitada) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return null;

        User user = userOpt.get();
        String senhaSalva = user.getSenha();

        if (senhaSalva.startsWith("$2a$")) {
            if (encoder.matches(senhaDigitada, senhaSalva)) {
                return jwtUtil.generateToken(user.getEmail(), user.getRole());
            }
        } else if (senhaSalva.equals(senhaDigitada)) {
            // converte senha antiga para hash
            user.setSenha(encoder.encode(senhaDigitada));
            userRepository.save(user);
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        }

        return null;
    }
}
