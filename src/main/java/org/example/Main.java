package org.example;

import org.example.models.Actor;
import org.example.models.Director;
import org.example.models.Movie;
import org.example.services.actor.ActorServiceImpl;
import org.example.services.actor.IActorService;
import org.example.services.director.DirectorServiceImpl;
import org.example.services.director.IDirectorService;
import org.example.services.movie.IMovieService;
import org.example.services.movie.MovieServiceImpl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static IMovieService movieService;
    private static IActorService actorService;
    private static IDirectorService directorService;

    private static <T> void measurePerformance(String operationName, Runnable operation) {
        Instant start = Instant.now();
        operation.run();
        Instant end = Instant.now();
        System.out.printf("\nTime taken for %s: %d ms%n",
                operationName, Duration.between(start, end).toMillis());
    }

    private static void initialize() {
        movieService = new MovieServiceImpl();
        actorService = new ActorServiceImpl();
        directorService = new DirectorServiceImpl();

        movieService.loadMovies("/Users/varunpanyam/dev/webknot/java-assignment-2/src/main/resources/csv/movies.csv");
        actorService.loadActors("/Users/varunpanyam/dev/webknot/java-assignment-2/src/main/resources/csv/actors.csv");
        directorService.loadDirectors("/Users/varunpanyam/dev/webknot/java-assignment-2/src/main/resources/csv/directors.csv");
    }

    private static void getMovieInformation() {
        System.out.println("Search by:");
        System.out.println("1. Movie ID");
        System.out.println("2. Movie Title");

        int choice = getValidIntInput("Enter your choice: ");
        Movie movie = null;

        if (choice == 1) {
            int movieId = getValidIntInput("Enter Movie ID: ");
            movie = movieService.getMovieById(movieId);
        } else if (choice == 2) {
            System.out.print("Enter Movie Title: ");
            String title = scanner.nextLine();
            movie = movieService.getMovieByTitle(title);
        }

        if (movie != null) {
            displayMovieDetails(movie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static int getValidIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Movie Management System ===");
        System.out.println("1. Get Movie Information");
        System.out.println("2. Get Top 10 Rated Movies");
        System.out.println("3. Get Movies by Genre");
        System.out.println("4. Get Movies by Director");
        System.out.println("5. Get Movies by Release Year");
        System.out.println("6. Get Movies by Release Year Range");
        System.out.println("7. Add New Movie");
        System.out.println("8. Update Movie Rating");
        System.out.println("9. Delete Movie");
        System.out.println("10. Display Sorted Movies by Release Year");
        System.out.println("11. Get Top 5 Directors");
        System.out.println("12. Get Actors in Multiple Movies");
        System.out.println("13. Get Movies of Youngest Actor");
        System.out.println("14. Exit");
    }

    private static void displayTop10Movies() {
        List<Movie> topMovies = movieService.getTop10Movies();
        System.out.println("\nTop 10 Rated Movies:");
        topMovies.forEach(movie -> {
            System.out.printf("%s (%.1f stars)%n", movie.getTitle(), movie.getRating());
        });
    }

    private static void getMoviesByGenre() {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        List<Movie> movies = movieService.getMoviesByGenre(genre);

        if (movies.isEmpty()) {
            System.out.println("No movies found in the " + genre + " genre.");
            return;
        }

        System.out.println("\nMovies in " + genre + " genre:");
        movies.forEach(movie -> {
            System.out.printf("%s (%d)%n", movie.getTitle(), movie.getReleaseYear());
        });
    }

    private static void displayMovieDetails(Movie movie) {
        System.out.println("\nMovie Details:");
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Release Year: " + movie.getReleaseYear());
        System.out.println("Genre: " + movie.getGenre());
        System.out.println("Rating: " + movie.getRating());
        System.out.println("Duration: " + movie.getDuration() + " minutes");

        Director director = directorService.getDirectorById(movie.getDirectorId());
        if (director != null) {
            System.out.println("Director: " + director.getName());
        }

        List<Actor> actors = actorService.getActorsByIds(movie.getActorIds());
        if (!actors.isEmpty()) {
            System.out.println("\nCast:");
            actors.forEach(actor -> System.out.println("- " + actor.getName()));
        }
    }

    private static void getMoviesByDirector() {
        System.out.print("Enter director name: ");
        String directorName = scanner.nextLine();
        List<Movie> movies = movieService.getMoviesByDirector(directorName, directorService.getAllDirectors());

        if (movies.isEmpty()) {
            System.out.println("No movies found for director: " + directorName);
            return;
        }

        System.out.println("\nMovies directed by " + directorName + ":");
        movies.forEach(movie -> {
            System.out.printf("%s (%d)%n", movie.getTitle(), movie.getReleaseYear());
        });
    }

    private static void getMoviesByYear() {
        int year = getValidIntInput("Enter release year: ");
        Movie movie = movieService.getMovieByReleaseYear(year);

        if (movie != null) {
            displayMovieDetails(movie);
        } else {
            System.out.println("No movies found for year: " + year);
        }
    }

    private static void getMoviesByYearRange() {
        int startYear = getValidIntInput("Enter start year: ");
        int endYear = getValidIntInput("Enter end year: ");

        if (startYear > endYear) {
            System.out.println("Start year must be less than or equal to end year.");
            return;
        }

        List<Movie> movies = movieService.getMoviesByReleaseYearRange(startYear, endYear);

        if (movies.isEmpty()) {
            System.out.println("No movies found between " + startYear + " and " + endYear);
            return;
        }

        System.out.printf("\nMovies released between %d and %d:%n", startYear, endYear);
        movies.forEach(movie -> {
            System.out.printf("%s (%d)%n", movie.getTitle(), movie.getReleaseYear());
        });
    }

    private static void addNewMovie() {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            int releaseYear = getValidIntInput("Enter release year: ");

            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            double rating = getValidDoubleInput("Enter rating (0-10): ");
            if (rating < 0 || rating > 10) {
                System.out.println("Rating must be between 0 and 10.");
                return;
            }

            int duration = getValidIntInput("Enter duration (minutes): ");
            int directorId = getValidIntInput("Enter director ID: ");

            System.out.print("Enter actor IDs (comma-separated): ");
            String[] actorIdsStr = scanner.nextLine().split(",");
            List<Integer> actorIds = new ArrayList<>();
            for (String id : actorIdsStr) {
                actorIds.add(Integer.parseInt(id.trim()));
            }

            Movie newMovie = new Movie(
                    (int) (Math.random() * 10000), // Generate random ID
                    title,
                    releaseYear,
                    genre,
                    rating,
                    duration,
                    directorId,
                    actorIds
            );

            movieService.addMovie(newMovie);
            System.out.println("Movie added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
    }

    private static void updateMovieRating() {
        int movieId = getValidIntInput("Enter movie ID: ");
        double newRating = getValidDoubleInput("Enter new rating (0-10): ");

        if (newRating < 0 || newRating > 10) {
            System.out.println("Rating must be between 0 and 10.");
            return;
        }

        Movie updatedMovie = movieService.updateMovie(movieId, newRating);
        if (updatedMovie != null) {
            System.out.println("Rating updated successfully!");
            displayMovieDetails(updatedMovie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void deleteMovie() {
        int movieId = getValidIntInput("Enter movie ID: ");
        Movie movie = movieService.getMovieById(movieId);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println("Are you sure you want to delete this movie?");
        displayMovieDetails(movie);
        System.out.print("Enter 'YES' to confirm: ");
        String confirmation = scanner.nextLine();

        if (confirmation.equals("YES")) {
            movieService.deleteMovie(movieId);
            System.out.println("Movie deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void displaySortedMovies() {
        List<Movie> sortedMovies = movieService.sortMoviesByReleaseYear();
        System.out.println("\nFirst 15 Movies Sorted by Release Year:");
        sortedMovies.forEach(movie -> {
            System.out.printf("%s (%d)%n", movie.getTitle(), movie.getReleaseYear());
        });
    }

    private static void displayTopDirectors() {
        Map<String, Long> directorsWithMovies = movieService.getDirectorsWithMostMovies();
        System.out.println("\nTop 5 Directors by Number of Movies:");

        directorsWithMovies.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> {
                    Director director = directorService.getDirectorById(Integer.parseInt(entry.getKey()));
                    if (director != null) {
                        System.out.printf("%s: %d movies%n", director.getName(), entry.getValue());
                    }
                });
    }

    private static void displayTopActors() {
        List<Movie> allMovies = movieService.getAllMovies();

        
        Map<Integer, Long> actorMovieCounts = allMovies.stream()
                .flatMap(movie -> movie.getActorIds().stream())
                .collect(Collectors.groupingBy(
                        actorId -> actorId,
                        Collectors.counting()
                ));


        List<Map.Entry<Integer, Long>> topActors = actorMovieCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(5)
                .toList();

        System.out.println("\nTop Actors by Number of Movies:");
        topActors.forEach(entry -> {
            Actor actor = actorService.getActorById(entry.getKey());
            if (actor != null) {
                System.out.printf("%s: %d movies%n", actor.getName(), entry.getValue());
            }
        });
    }

    private static void displayYoungestActorMovies() {
        List<Movie> movies = movieService.getMoviesOfYoungestActor();
        if (movies.isEmpty()) {
            System.out.println("No movies found for the youngest actor.");
            return;
        }

        // Get the youngest actor
        Actor youngestActor = null;
        int youngestAge = Integer.MAX_VALUE;
        LocalDate currentDate = LocalDate.of(2025, 2, 10);

        for (Movie movie : movies) {
            for (Integer actorId : movie.getActorIds()) {
                Actor actor = actorService.getActorById(actorId);
                if (actor != null) {
                    int age = Period.between(actor.getDateOfBirth(), currentDate).getYears();
                    if (age < youngestAge) {
                        youngestAge = age;
                        youngestActor = actor;
                    }
                }
            }
        }

        if (youngestActor != null) {
            System.out.printf("\nMovies featuring %s (Age: %d):%n", youngestActor.getName(), youngestAge);
            movies.forEach(movie -> {
                System.out.printf("%s (%d)%n", movie.getTitle(), movie.getReleaseYear());
            });
        }
    }

    public static void main(String[] args) {
        initialize();

        while (true) {
            displayMenu();
            int choice = getValidIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> getMovieInformation();
                case 2 -> displayTop10Movies();
                case 3 -> getMoviesByGenre();
                case 4 -> getMoviesByDirector();
                case 5 -> getMoviesByYear();
                case 6 -> getMoviesByYearRange();
                case 7 -> addNewMovie();
                case 8 -> updateMovieRating();
                case 9 -> deleteMovie();
                case 10 -> displaySortedMovies();
                case 11 -> displayTopDirectors();
                case 12 -> displayTopActors();
                case 13 -> displayYoungestActorMovies();
                case 14 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}