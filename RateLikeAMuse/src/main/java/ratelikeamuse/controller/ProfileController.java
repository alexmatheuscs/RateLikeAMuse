package ratelikeamuse.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.entity.Rate;
import ratelikeamuse.entity.User;
import ratelikeamuse.service.MovieService;
import ratelikeamuse.service.RateService;
import ratelikeamuse.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;
    private final RateService rateService;
    private final MovieService movieService;

    public ProfileController(UserService userService, RateService rateService, MovieService movieService) {
        this.userService = userService;
        this.rateService = rateService;
        this.movieService = movieService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        
        // Pega o username diretamente do usuário autenticado pelo Spring Security
        String username = principal.getName();
        
        // O resto da lógica é a mesma, mas agora sabemos que é para o usuário correto
        UserDetails userDetails = userService.loadUserByUsername(username);
        User user = (User) userDetails;

        List<Rate> userRates = rateService.myRates(user.getId());
        Map<Long, Movie> movieMap = movieService.getAllMovies().stream()
                .collect(Collectors.toMap(Movie::getId, movie -> movie));

        model.addAttribute("user", user);
        model.addAttribute("rates", userRates);
        model.addAttribute("movieMap", movieMap);
        
        return "profile/view";
    }

}