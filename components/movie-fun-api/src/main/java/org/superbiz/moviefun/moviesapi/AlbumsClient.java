package org.superbiz.moviefun.moviesapi; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class AlbumsClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RestOperations restOperations;
    private String url;

    public AlbumsClient(String url, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.url = url;
    }

    public void addAlbum(AlbumInfo album) {
        logger.debug("Creating movie with title {}, and year {}", album.getTitle(), album.getYear());
        this.restOperations.postForEntity(url, album, AlbumInfo.class);
    }

    public ResponseEntity<AlbumInfo> find(long id) {
        return this.restOperations.getForEntity(url+"/"+id, AlbumInfo.class);
    }

    public ResponseEntity<List<AlbumInfo>> getAlbums() {
        return this.restOperations.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumInfo>>() {
        });
    }

    public void deleteAlbum(AlbumInfo album) {
        this.restOperations.delete(url+"/"+album.getId());
    }

    public void updateAlbum(AlbumInfo album) {
        this.restOperations.put(url+"/"+album.getId(), album);
    }
}
