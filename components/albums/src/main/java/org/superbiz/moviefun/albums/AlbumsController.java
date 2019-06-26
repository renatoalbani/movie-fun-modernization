package org.superbiz.moviefun.albums;

import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AlbumsBean albumsBean;

    public AlbumsController(AlbumsBean albumsBean) {
        this.albumsBean = albumsBean;
    }


    @GetMapping
    public List<Album> index() {
        return albumsBean.getAlbums();
    }

    @GetMapping("/{albumId}")
    public Album details(@PathVariable long albumId) {
        return albumsBean.find(albumId);
    }

    @PostMapping
    public void save(@RequestBody Album album) {
        albumsBean.addAlbum(album);
    }

    @PutMapping("/{albumId}")
    public void update(@PathVariable long albumId, @RequestBody Album album) {
        albumsBean.updateAlbum(album);
    }
}
