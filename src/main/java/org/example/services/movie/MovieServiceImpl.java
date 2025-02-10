package org.example.services.movie;

import org.example.models.Director;
import org.example.models.Movie;
import org.example.utils.FileReaderUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MovieServiceImpl implements IMovieService {
    private final List<Movie> movies = new ArrayList<>();

    @Override
    public void loadMovies(String filePath) {
        try {
            // load csv into data
            List<String[]> data = FileReaderUtil.readCSV(filePath);
            System.out.println("Loaded " + data.size() + " movies");


            for (int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);

                int movieId = Integer.parseInt(row[0]);
                String title = row[1];
                int releaseYear = Integer.parseInt(row[2]);
                String genre = row[3];
                double rating = Double.parseDouble(row[4]);
                int duration = Integer.parseInt(row[5]);
                int directorId = Integer.parseInt(row[6]);
                List<Integer> actorIds = Arrays.stream(row[7].split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty() && s.matches("\\d+"))
                        .map(Integer::parseInt)
                        .toList();


                movies.add(new Movie(movieId, title, releaseYear, genre, rating, duration, directorId, actorIds));
            }

        } catch (IOException e) {
            System.err.println("Error loading movies: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie getMovieById(int movieId) {
        return movies.stream()
                .filter(movie -> movie.getMovieId() == movieId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        return movies.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMoviesByDirector(String directorName, List<Director> directors) {
        Director director = directors.stream()
                .filter(d -> d.getName().equalsIgnoreCase(directorName))
                .findFirst()
                .orElse(null);

        if (director == null) {
            return List.of();
        }

        int directorId = director.getDirectorId();
        return movies.stream()
                .filter(movie -> movie.getDirectorId() == directorId)
                .collect(Collectors.toList());
    }


    @Override
    public Movie getMovieByReleaseYear(int releaseYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() == releaseYear)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Movie> getMoviesByReleaseYearRange(int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getTop10Movies() {
        return movies.stream()
                .sorted((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    public Movie updateMovie(int movieId, double newRating) {
        Movie movie = getMovieById(movieId);
        if (movie != null) {
            movies.remove(movie);
            Movie updatedMovie = new Movie(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getReleaseYear(),
                    movie.getGenre(),
                    newRating,
                    movie.getDuration(),
                    movie.getDirectorId(),
                    movie.getActorIds()
            );
            movies.add(updatedMovie);
            return updatedMovie;
        }
        return null;
    }

    @Override
    public void deleteMovie(int movieId) {
        Movie movie = getMovieById(movieId);
        if (movie != null) {
            movies.removeIf(m -> m.getMovieId() == movieId);
        }
    }

    @Override
    public List<Movie> sortMoviesByReleaseYear() {
        return movies.stream()
                .sorted(Comparator.comparingInt(Movie::getReleaseYear))
                .limit(15)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getDirectorsWithMostMovies() {
        return movies.stream()
                .collect(Collectors.groupingBy(
                        movie -> String.valueOf(movie.getDirectorId()),
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getActorsWithMostMovies() {
        return movies.stream()
                .flatMap(movie -> movie.getActorIds().stream())
                .collect(Collectors.groupingBy(
                        String::valueOf,
                        Collectors.counting()
                ));
    }

    @Override
    public List<Movie> getMoviesOfYoungestActor() {
        return List.of();
    }
}
