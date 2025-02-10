package org.example.services.actor;

import org.example.models.Actor;
import org.example.utils.FileReaderUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActorServiceImpl implements IActorService {
    private final List<Actor> actors = new ArrayList<>();

    @Override
    public void loadActors(String filePath) {
        try {
            List<String[]> data = FileReaderUtil.readCSV(filePath);

            for (int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                actors.add(new Actor(
                        Integer.parseInt(row[0]),
                        row[1],
                        LocalDate.parse(row[2]),
                        row[3]
                ));
            }
        } catch (IOException e) {
            System.err.println("Error loading actors: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Actor getActorById(int actorId) {
        return actors.stream()
                .filter(actor -> actor.getActorId() == actorId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Actor> getActorsByIds(List<Integer> actorIds) {
        return actors.stream()
                .filter(actor -> actorIds.contains(actor.getActorId()))
                .collect(Collectors.toList());
    }

    public Actor getYoungestActor() {
        return actors.stream()
                .max((a1, a2) -> a1.getDateOfBirth().compareTo(a2.getDateOfBirth()))
                .orElse(null);
    }
}
