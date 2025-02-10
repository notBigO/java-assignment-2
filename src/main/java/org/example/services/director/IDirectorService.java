package org.example.services.director;

import org.example.models.Director;

import java.util.List;

public interface IDirectorService {
    void loadDirectors(String filePath);


    Director getDirectorById(int directorId);

    List<Director> getDirectorsByIds(List<Integer> directorIds);

    List<Director> getAllDirectors();
}