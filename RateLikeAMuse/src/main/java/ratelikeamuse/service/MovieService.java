package ratelikeamuse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import ratelikeamuse.entity.Movie;
import ratelikeamuse.repository.MovieRepository;

@Service
public class MovieService implements MovieInterface {
    private final MovieRepository movieRepository;
    
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    

@PostConstruct
public void initializeMovies() {
    if (movieRepository.count() == 0) {
        System.out.println("Banco de dados de filmes vazio. Inicializando com dados padrão...");
        
        // PASSO 2: Se estiver vazio, adiciona a lista de filmes.
        movieRepository.save(new Movie(null, "Anora", "Comédia dramática, romance", 2024, "anora.jpg"));
        movieRepository.save(new Movie(null, "The Brutalist", "Drama, histórico", 2024, "brutalist.jpg"));
        movieRepository.save(new Movie(null, "A Complete Unknown", "Drama, biografia, musical", 2024, "a-complete-unknown.jpg"));
        movieRepository.save(new Movie(null, "Conclave", "Suspense, mistério, drama", 2024, "conclave.jpg"));
        movieRepository.save(new Movie(null, "Dune: Part Two", "Ficção científica, aventura, ação", 2024, "duna-part2.jpg"));
        movieRepository.save(new Movie(null, "Emilia Pérez", "Musical, comédia criminal, suspense", 2024, "emilia-perez.jpg"));
        movieRepository.save(new Movie(null, "I'm Still Here", "Drama", 2024, "ainda-estou-aqui.jpeg"));
        movieRepository.save(new Movie(null, "Nickel Boys", "Drama histórico", 2024, "nickel-boys.jpg"));
        movieRepository.save(new Movie(null, "The Substance", "Terror corporal, suspense", 2024, "The-Substance.jpg"));
        movieRepository.save(new Movie(null, "Wicked", "Fantasia, comédia musical", 2024, "wicked.jpg"));
        movieRepository.save(new Movie(null, "Sing Sing", "Drama, prisão", 2024, "Sing-Sing.jpg"));
        movieRepository.save(new Movie(null, "The Apprentice", "Cinebiografia, drama", 2024, "the-apprentice.jpg"));
        movieRepository.save(new Movie(null, "A Real Pain", "Comédia dramática", 2024, "a-real-pain.jpg"));
        movieRepository.save(new Movie(null, "September 5", "Drama histórico, suspense", 2024, "september-5.jpg"));
    } else {
        System.out.println("Banco de dados de filmes já populado. Nenhuma ação necessária.");
    }
}

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}