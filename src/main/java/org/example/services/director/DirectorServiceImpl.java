package org.example.services.director;

import org.example.models.Director;
import org.example.utils.FileReaderUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DirectorServiceImpl implements IDirectorService {
    private final List<Director> directors = new ArrayList<>();

    @Override
    public void loadDirectors(String filePath) {
        try {
            List<String[]> data = FileReaderUtil.readCSV(filePath);

            for (int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                directors.add(new Director(
                        Integer.parseInt(row[0]),
                        row[1],
                        LocalDate.parse(row[2]),
                        row[3]
                ));
            }
        } catch (IOException e) {
            System.err.println("Error loading directors: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Director getDirectorById(int directorId) {
        return directors.stream()
                .filter(director -> director.getDirectorId() == directorId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Director> getDirectorsByIds(List<Integer> directorIds) {
        return directors.stream()
                .filter(director -> directorIds.contains(director.getDirectorId()))
                .collect(Collectors.toList());
    }

    public List<Director> getAllDirectors() {
        return new ArrayList<>(directors);
    }
}
