package ratelikeamuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ratelikeamuse.service.RateService;

@Controller
public class RateController {
    private final RateService rateService;
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

@PostMapping("/rate")
    public String saveRating(@RequestParam Long movieId,
                             @RequestParam Long userId,
                             @RequestParam int score) {
        
        rateService.saveOrUpdateRate(userId, movieId, score);

        return "redirect:/movies";
    }
}