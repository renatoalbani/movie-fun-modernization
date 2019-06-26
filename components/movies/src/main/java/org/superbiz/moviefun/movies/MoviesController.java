package org.superbiz.moviefun.movies;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {

    MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping("/movies/{id}")
    public Movie get(@PathVariable("id") long id){
        return moviesRepository.find(id);
    }

    @GetMapping("/movies")
    public List<Movie> list(){
        return moviesRepository.getMovies();
    }

    @GetMapping(value = "/movies", params = {"firstResult", "maxResults"})
    public List<Movie> findAll(@RequestParam("firstResult") int firstResult, @RequestParam("maxResults") int maxResults){
        return this.moviesRepository.findAll(firstResult, maxResults);
    }


    @GetMapping(value = "/movies/count", params = {"field", "searchTerm"})
    public MoviesCount count(@RequestParam("field") String field, @RequestParam("searchTerm") String searchTerm){
        return new MoviesCount(this.moviesRepository.count(field, searchTerm));
    }

    @GetMapping(value = "/movies/count")
    public MoviesCount countAll(){
        return new MoviesCount(this.moviesRepository.countAll());
    }

    @GetMapping(value = "/movies", params = {"field", "searchTerm", "firstResult", "maxResults"})
    public List<Movie> findRange(@RequestParam("field") String field,@RequestParam("searchTerm") String searchTerm,@RequestParam("firstResult") int firstResult, @RequestParam("maxResults") int maxResults){
        return this.moviesRepository.findRange(field, searchTerm, firstResult, maxResults);
    }

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody  Movie movie){
        moviesRepository.addMovie(movie);
    }

    @DeleteMapping("/movies/{id}")
    public void remove(@PathVariable("id") long id){
        moviesRepository.deleteMovieId(id);
    }

    @PutMapping("/movies/{id}")
    public void put(@PathVariable("id") long id, @RequestBody Movie movie){
        Movie movieOld = this.moviesRepository.find(id);
        this.moviesRepository.updateMovie(movie);
    }


}
