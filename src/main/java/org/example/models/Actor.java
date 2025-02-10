package org.example.models;

import java.time.LocalDate;

public class Actor {
    private int actorId;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;

    public Actor(int actorId, String name, LocalDate dateOfBirth, String nationality) {
        this.actorId = actorId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    // getter and setters
    public int getActorId() { return actorId; }
    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }

//    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}