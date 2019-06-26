package org.superbiz.moviefun.moviesapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    private final MovieClient movieclient;
    private final AlbumsClient albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MovieClient movieclient, AlbumsClient albumsBean, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.movieclient = movieclient;
        this.albumsBean = albumsBean;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movie : movieFixtures.load()) {
            movieclient.addMovie(movie);
        }

        for (AlbumInfo album : albumFixtures.load()) {
            albumsBean.addAlbum(album);
        }

        model.put("movies", movieclient.getMovies().getBody());
        model.put("albums", albumsBean.getAlbums().getBody());

        return "setup";
    }
}
