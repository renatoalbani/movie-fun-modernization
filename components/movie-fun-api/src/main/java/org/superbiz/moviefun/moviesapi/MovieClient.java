package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MovieClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestOperations restOperations;
    private String url;

    public MovieClient(String url, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.url = url;
    }

    public ResponseEntity<MovieInfo> find(Long id) {
        return this.restOperations.getForEntity(url+"/"+id, MovieInfo.class);
    }

    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());
        this.restOperations.postForEntity(url, movie, MovieInfo.class);
    }

    public void updateMovie(MovieInfo movie) {
        this.restOperations.put(url+"/"+movie.getId(), movie);
    }

    public void deleteMovie(MovieInfo movie) {
        this.restOperations.delete(url+"/"+movie.getId());
    }

    public void deleteMovieId(long id) {
        this.restOperations.delete(url+"/"+id);
    }

    public ResponseEntity<List<MovieInfo>> getMovies() {
        return this.restOperations.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieInfo>>() {
        });
    }

    public ResponseEntity<List<MovieInfo>> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("firstResult", firstResult)
                .queryParam("maxResults", maxResults);

        return this.restOperations.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieInfo>>() {
        });
    }

    public ResponseEntity<MoviesCountInfo> countAll() {
        return this.restOperations.getForEntity(url+"/count", MoviesCountInfo.class);

    }

    public ResponseEntity<MoviesCountInfo> count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url+"/count")
                .queryParam("field", field)
                .queryParam("searchTerm", searchTerm);
        return this.restOperations.getForEntity(builder.toUriString(), MoviesCountInfo.class);
    }

    public ResponseEntity<List<MovieInfo>> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("field", field)
                .queryParam("searchTerm", searchTerm)
                .queryParam("firstResult", firstResult)
                .queryParam("maxResults", maxResults);
        return this.restOperations.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieInfo>>() {
        });
    }

    public void clean() {
        return;
    }

}
