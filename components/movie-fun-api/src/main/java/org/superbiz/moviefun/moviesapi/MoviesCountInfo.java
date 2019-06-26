package org.superbiz.moviefun.moviesapi;

public class MoviesCountInfo {

    private int count;

    //Jackson
    public MoviesCountInfo(){}

    public MoviesCountInfo(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
