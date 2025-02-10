package org.example.services.movie;

import org.example.models.Movie;

import java.util.List;
import java.util.Map;

public interface IMovieService {
    void loadMovies(String filePath);
    Movie getMovieById(int movieId);
    Movie getMovieByTitle(String title);
    Movie getMovieByGenre(String genre);
    Movie getMovieByDirector(String director);
    Movie getMovieByReleaseYear(int releaseYear);
    List<Movie> getMoviesByReleaseYearRange(int startYear, int endYear);
    List<Movie> getTop10Movies();

    void addMovie(Movie movie);
    Movie updateMovie(int movieId, double newRating);
    void deleteMovie(int movieId);

    List<Movie> sortMoviesByReleaseYear();
    Map<String, Long> getDirectorsWithMostMovies();
    Map<String, Long> getActorsWithMostMovies();
    List<Movie> getMoviesOfYoungestActor();
}
