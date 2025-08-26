package ratelikeamuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ratelikeamuse.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Este método apenas mostra a página de registro
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Este método processa o registro de um novo usuário
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(username, password);
            redirectAttributes.addFlashAttribute("success", "Usuário registrado com sucesso! Por favor, faça o login.");
            return "redirect:/"; 
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return "register";
        }
    }

    // OS MÉTODOS DE LOGIN E LOGOUT FORAM REMOVIDOS DAQUI
    // O Spring Security agora cuida deles automaticamente.
}

