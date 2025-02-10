package org.example.services.actor;

import org.example.models.Actor;

import java.util.List;

public interface IActorService {
    void loadActors(String filePath);

    Actor getActorById(int actorId);

    List<Actor> getActorsByIds(List<Integer> actorIds);

    Actor getYoungestActor();
}
