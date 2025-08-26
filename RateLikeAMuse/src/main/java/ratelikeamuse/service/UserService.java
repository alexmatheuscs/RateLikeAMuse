package ratelikeamuse.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import ratelikeamuse.entity.Funcao;
import ratelikeamuse.entity.User;
import ratelikeamuse.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    // O método de registro continua igual
    public User registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existente");
        }
        User user = new User(null, username, passwordEncoder.encode(password), Funcao.USER);
        return userRepository.save(user); 
    }
    
    // O método para criar um admin inicial continua útil
    @PostConstruct
    public void createAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            String adminPassword = "admin";
            User adminUser = new User(null, "admin", passwordEncoder.encode(adminPassword), Funcao.ADMIN);
            userRepository.save(adminUser);
            System.out.println("Usuário ADMIN criado com sucesso! Senha: " + adminPassword);
        }
    }
}