package org.example.models;

import java.time.LocalDate;

public class Director {
    private int directorId;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;

    public Director(int directorId, String name, LocalDate dateOfBirth, String nationality) {
        this.directorId = directorId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    // getter and setters
    public int getDirectorId() { return directorId; }
    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }

//    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}