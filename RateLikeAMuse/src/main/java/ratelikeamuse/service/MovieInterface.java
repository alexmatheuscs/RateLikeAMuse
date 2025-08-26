package ratelikeamuse.service;

import java.util.List;
import java.util.Optional;

import ratelikeamuse.entity.Movie;

public interface MovieInterface {

    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Long id);
    Movie saveMovie(Movie movie);
    void deleteMovie(Long id);
}