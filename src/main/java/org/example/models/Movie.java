package org.example.models;

import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private int releaseYear;
    private String genre;
    private double rating;
    private int duration;
    private int directorId;
    private List<Integer> actorIds;


    public Movie(int movieId, String title, int releaseYear, String genre, double rating, int duration, int directorId, List<Integer> actorIds) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.directorId = directorId;
        this.actorIds = actorIds;
    }

    // getter and setters
    public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
    public int getDuration() { return duration; }
    public int getDirectorId() { return directorId; }
    public List<Integer> getActorIds() { return actorIds; }

//    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", duration=" + duration +
                ", directorId=" + directorId +
                ", actorIds=" + actorIds +
                '}';
    }
}