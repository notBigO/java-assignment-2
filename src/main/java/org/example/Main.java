package org.example;

import org.example.models.Movie;
import org.example.services.movie.IMovieService;
import org.example.services.movie.MovieServiceImpl;

public class Main {
    public static void main(String[] args) {
        IMovieService movieService = new MovieServiceImpl();
        movieService.loadMovies("/Users/varunpanyam/dev/webknot/java-assignment-2/src/main/resources/csv/movies.csv");

    }
}