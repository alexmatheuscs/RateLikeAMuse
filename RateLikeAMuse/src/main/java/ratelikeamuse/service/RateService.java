package ratelikeamuse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ratelikeamuse.entity.Rate;
import ratelikeamuse.repository.RateRepository;

@Service
public class RateService {
    private final RateRepository rateRepository;
    
    public RateService(RateRepository rateRepository ) {
        this.rateRepository = rateRepository;
    }
    
public Rate saveOrUpdateRate(Long userId, Long movieId, int score) {
    Optional<Rate> existingRateOpt = rateRepository.findByUserIdAndMovieId(userId, movieId);

    if (existingRateOpt.isPresent()) {
        Rate existingRate = existingRateOpt.get();
        existingRate.setScore(score);
        return rateRepository.save(existingRate);
    } else {
        Rate newRate = new Rate(null, userId, movieId, score);
        return rateRepository.save(newRate);
    }
}

    public List<Rate> myRates (Long userId) {
        return rateRepository.findByUserId(userId);
    }

    public int movieRate(Long movieId) {
        List<Rate> movieRates = rateRepository.findByMovieId(movieId);
        if (movieRates.isEmpty()) {
            return 0; // Evita divisão por zero
        }
        int rates = 0;
        for(int i = 0; i < movieRates.size() ; i++) {
            rates += movieRates.get(i).getScore();
        }
        int averageRate = rates / movieRates.size();
        return averageRate;
    }
    
    public void deleteRate(Long id) {
        rateRepository.deleteById(id);
    }
}