package org.example.services.director;

import org.example.models.Director;

import java.util.List;

public interface IDirectorService {
    void loadDirectors(String filePath);

    // tentative functions
    // Director getDirectorById(int directorId);
    // List<Director> getDirectorsByIds(List<Integer> directorIds);
}