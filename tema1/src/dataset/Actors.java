package dataset;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import utils.Utils;


public class Actors {
  private final ArrayList<Actor> actors = new ArrayList<>();

  public Actors() {

  }

  /**
   * functia de adaugare actori in array.
   */
  public void addActors(final List<ActorInputData> actors) {
    for (ActorInputData actor : actors) {
      this.actors.add(new Actor(actor.getName(), actor.getCareerDescription(),
              actor.getFilmography(),
              actor.getAwards()));
    }
  }

  /**
   * functia care face trigger la calcul rating pentru toti acotrii.
   */
  public void computeRating(final Shows shows) {
    for (Actor actor : actors) {
      actor.calculateRating(shows);
    }
  }

  /**
   * returneaza rezultatul query-ului average pentru actori.
   */
  public ArrayList<Actor> getAverage(final int number, final String sortType) {
    ArrayList<Actor> actorsNew = new ArrayList<>();

    for (Actor actor : this.actors) {
      if (actor.getRating() != 0) {
        actorsNew.add(actor);
      }
    }

    Comparator<Actor> compareByRating = (Actor o1, Actor o2) -> {
      if (o1.getRating() == o2.getRating()) {
        return o1.getName().compareTo(o2.getName());
      } else {
        return Double.compare(o1.getRating(), o2.getRating());
      }
    };
    ArrayList<Actor> actorsFinal = new ArrayList<>();

    if (sortType.equals("asc")) {
      actorsNew.sort(compareByRating);
    } else {
      actorsNew.sort(compareByRating.reversed());

    }

    for (int i = 0; i < number; i++) {
      if (i == actorsNew.size()) {
        break;
      }
      actorsFinal.add(actorsNew.get(i));
    }

    return actorsFinal;
  }

  /**
   * returneaza rezultatul query-ului awards pentru actori.
   */
  public ArrayList<Actor> getAwards(final List<String> awards, final String sortType) {
    ArrayList<Actor> actorsFinal = new ArrayList<>();

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

  /**
   * returneaza rezultatul query-ului filter description pentru actori.
   */
  public ArrayList<Actor> getFilter(final List<String> words, final String sortType) {
    ArrayList<Actor> actorsFinal = new ArrayList<>();

    for (Actor actor : actors) {
      boolean isGood = true;
      for (String word : words) {
        String wordX = " " + word + " ";
        String replaceString = actor.getCareerDescription().toLowerCase().replace(","," ");
        replaceString = replaceString.replace("\n"," ");
        replaceString = replaceString.replace("."," ");
        replaceString = replaceString.replace("-"," ");

        replaceString = " " + replaceString;
        if (!replaceString.toLowerCase().contains(wordX.toLowerCase())) {
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
