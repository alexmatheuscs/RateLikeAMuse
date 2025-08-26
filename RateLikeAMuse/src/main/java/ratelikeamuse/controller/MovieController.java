package ratelikeamuse.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.service.MovieService;

@Controller
@RequestMapping("/movies")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
    	this.movieService = movieService;
	}

	@GetMapping
	public String listMovies(Model model) {
    	model.addAttribute("movies", movieService.getAllMovies());
    	return "movies/list";
	}

	@GetMapping("/new")
	public String showCreateForm() {
    	return "movies/form";  // Retorna formulário para criar filme
	}

	@PostMapping("/save")
	public String saveMovie(
    	@RequestParam String nome,
    	@RequestParam String genero,
    	Model model) {
    	try {
			Movie movie = new Movie();
			movie.setNome(nome);
			movie.setGenero(genero);

        	movieService.saveMovie(movie);
        	return "redirect:/movies?success";
    	} catch (RuntimeException e) {
        	model.addAttribute("error", e.getMessage());
        	model.addAttribute("nome", nome);
        	model.addAttribute("genero", genero);
        	return "movies/form";
    	}
	}

	@GetMapping("/edit")
	public String showEditForm(@RequestParam Long id, Model model) {
    	Optional<Movie> movieOpt = movieService.getMovieById(id);
    	if (movieOpt.isPresent()) {
        	Movie movie = movieOpt.get();
        	model.addAttribute("movie", movie);
        	model.addAttribute("nome", movie.getNome());
        	model.addAttribute("genero", movie.getGenero());
        	return "movies/form";
    	} else {
        	return "redirect:/movies?error=notfound";
    	}
	}

	@PostMapping("/edit")
	public String editMovie(
    	@RequestParam Long id,
    	@RequestParam String nome,
    	@RequestParam String genero,
    	Model model) {
    	try {
        	Movie movie = new Movie();
        	movie.setId(id);
        	movie.setNome(nome);
        	movie.setGenero(genero);
        	movieService.saveMovie(movie);
        	return "redirect:/movies?success";
    	} catch (RuntimeException e) {
        	model.addAttribute("error", e.getMessage());
        	model.addAttribute("id", id);
        	model.addAttribute("nome", nome);
        	model.addAttribute("genero", genero);
        	return "movies/form";
    	}
	}

	@GetMapping("/delete")
	public String deleteMovie(@RequestParam Long id) {
    	movieService.deleteMovie(id);
    	return "redirect:/movies?deleted";
	}
}