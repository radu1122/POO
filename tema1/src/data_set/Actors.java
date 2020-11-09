package data_set;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActorInputData;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Actors {
    private ArrayList<Actor> actors = new  ArrayList<Actor>();

    public Actors(){

    }

    public void addActors(List<ActorInputData> actors) {
        for (ActorInputData actor : actors) {
            this.actors.add(new Actor(actor.getName(), actor.getCareerDescription(),
                                      actor.getFilmography(),
                                      actor.getAwards()));
        }
    }

    public void computeRating(Shows shows){
        for (Actor actor : actors) {
            actor.calculateRating(shows);
        }
    }

    public ArrayList<Actor> getAverage(int number, String sortType) {
        ArrayList<Actor> actorsNew = (ArrayList<Actor>) this.actors.clone();

        Comparator<Actor> compareByRating = (Actor o1, Actor o2) -> {
            if (o1.getRating() == o2.getRating()) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        };
        ArrayList<Actor> actorsFinal = new ArrayList<Actor>();

        if (sortType.equals("asc")) {
            actorsNew.sort(compareByRating);
        } else {
            actorsNew.sort(compareByRating.reversed());

        }

        for(int i = 0; i < number; i++) {
            actorsFinal.add(actorsNew.get(i));
        }

        return actorsFinal;
    }

    public ArrayList<Actor> getAwards(List<String> awards, String sortType) {
        ArrayList<Actor> actorsFinal = new ArrayList<Actor>();

        for (Actor actor : actors) {
            boolean isGood = true;
            for (String award : awards) {
                ActorsAwards awardEnum = Utils.stringToAwards(award);
                if (!actor.getAwards().containsKey(awardEnum)) {
                    isGood = false;
                    break;
                }
            }
            if (isGood) {
                actorsFinal.add(actor);
            }
        }

        Comparator<Actor> compareByAward = (Actor o1, Actor o2) -> {
            if (o1.getAwardsCount() == o2.getAwardsCount()) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return o1.getAwardsCount() - o2.getAwardsCount();
            }
        };
        if (sortType.equals("asc")) {
            actorsFinal.sort(compareByAward);
        } else {
            actorsFinal.sort(compareByAward.reversed());
        }

        return actorsFinal;
    }

    public ArrayList<Actor> getFilter(List<String> words, String sortType) {
        ArrayList<Actor> actorsFinal = new ArrayList<Actor>();

        for (Actor actor : actors) {
            boolean isGood = true;
            for (String word : words) {
                if (!actor.getCareerDescription().contains(word)) {
                    isGood = false;
                    break;
                }
            }
            if (isGood) {
                actorsFinal.add(actor);
            }
        }

        Comparator<Actor> compareByName = Comparator.comparing(Actor::getName);

        if (sortType.equals("asc")) {
            actorsFinal.sort(compareByName);
        } else {
            actorsFinal.sort(compareByName.reversed());
        }
        return actorsFinal;
    }
}
