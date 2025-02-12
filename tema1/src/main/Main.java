package main;

import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import dataset.Actors;
import dataset.Shows;
import dataset.Users;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import user.User;
import video.Movie;
import video.Serial;
import video.Video;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
  /**
   * for coding style
   */
  private Main() {
  }

  /**
   * Call the main checker and the coding style checker
   *
   * @param args from command line
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void main(final String[] args) throws IOException {
    File directory = new File(Constants.TESTS_PATH);
    Path path = Paths.get(Constants.RESULT_PATH);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }

    File outputDirectory = new File(Constants.RESULT_PATH);

    Checker checker = new Checker();
    checker.deleteFiles(outputDirectory.listFiles());

    for (File file : Objects.requireNonNull(directory.listFiles())) {

      String filepath = Constants.OUT_PATH + file.getName();
      File out = new File(filepath);
      boolean isCreated = out.createNewFile();
      if (isCreated) {
        action(file.getAbsolutePath(), filepath);
      }
    }

    checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
    Checkstyle test = new Checkstyle();
    test.testCheckstyle();
  }

  /**
   * @param filePath1 for input file
   * @param filePath2 for output file
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void action(final String filePath1,
                            final String filePath2) throws IOException {
    InputLoader inputLoader = new InputLoader(filePath1);
    Input input = inputLoader.readData();

    Writer fileWriter = new Writer(filePath2);
    JSONArray arrayResult = new JSONArray();


    //TODO add here the entry point to your implementation

    // add all shows to the dataset
    Shows shows = new Shows();
    ArrayList<String> showsTitle = new ArrayList<>();
    // movies
    for (MovieInputData movie : input.getMovies()) {
      Movie movieBuff = new Movie(movie.getTitle(), movie.getCast(),
              movie.getGenres(), movie.getYear(),
              movie.getDuration());
      shows.addShow(movie.getTitle(), movieBuff);
      showsTitle.add(movie.getTitle());
    }
    // serials
    for (SerialInputData serial : input.getSerials()) {
      Serial serialBuff = new Serial(serial.getTitle(), serial.getCast(),
              serial.getGenres(), serial.getNumberSeason(), serial.getSeasons(),
              serial.getYear());
      shows.addShow(serial.getTitle(), serialBuff);
      showsTitle.add(serial.getTitle());

    }

    // add all users to the dataSet
    Users users = new Users();
    for (UserInputData user : input.getUsers()) {
      User userBuff = new User(user.getUsername(), user.getSubscriptionType(),
              user.getHistory(), user.getFavoriteMovies(), shows);
      users.addUser(user.getUsername(), userBuff);
    }

    // actors
    Actors actors = new Actors();
    actors.addActors(input.getActors());


    List<ActionInputData> commandsData = input.getCommands();
    for (ActionInputData command : commandsData) {
      int id = command.getActionId();
      String message;
      String commandType = command.getActionType();
      if (commandType.equals("command")) {
        message = commands(command, users, shows);
      } else if (commandType.equals("query")) {
        String objectType = command.getObjectType();
        if (objectType.equals("actors")) {
          message = queryActors(command, actors, shows);
        } else if (objectType.equals("users")) {
          message = queryUsers(command, users);
        } else {
          message = queryVideos(command, shows);
        }
      } else {
        message = recommendationUsers(command, users, shows, showsTitle);
      }
      JSONObject jo = new JSONObject();
      jo.put("id", id);
      jo.put("message", message);

      arrayResult.add(jo);
    }

    fileWriter.closeJSON(arrayResult);
  }

  /**
   * functia care filtreaza comenzile.
   */
  public static String commands(final ActionInputData command,
                                final Users users, final Shows shows) {
    return switch (command.getType()) {
      case "favorite" -> commandFavorite(command, users, shows);
      case "view" -> commandView(command, users, shows);
      case "rating" -> commandRating(command, users, shows);
      default -> "";
    };
  }

  /**
   * functia care face trigger la comanda add favorite.
   */
  public static String commandFavorite(final ActionInputData command,
                                       final Users users, final Shows shows) {
    String username = command.getUsername();
    String title = command.getTitle();
    int output = users.addFavorite(username, title);
    if (output == 1) {
      shows.addFavorite(title);
      return "success -> " + title + " was added as favourite";
    } else if (output == 0) {
      return "error -> " + title + " is already in favourite list";
    } else {
      return "error -> " + title + " is not seen";
    }

  }

  /**
   * functia care face trigger la comanda add view.
   */
  public static String commandView(final ActionInputData command,
                                   final Users users, final Shows shows) {
    String username = command.getUsername();
    String title = command.getTitle();
    int views = users.addView(username, title);
    shows.addView(title);
    return "success -> " + title + " was viewed with total views of " + views;
  }

  /**
   * functia care face trigger la comanda add rating.
   */
  public static String commandRating(final ActionInputData command,
                                     final Users users, final Shows shows) {
    String username = command.getUsername();
    String title = command.getTitle();
    double grade = command.getGrade();
    int season = command.getSeasonNumber();
    int output = users.addRating(username, title, grade, season);
    if (output == 0) {
      return "error -> " + title + " is not seen";
    } else if (output == 1) {
      shows.addRating(title, grade, season);
      return "success -> " + title + " was rated with "
              + String.format(Locale.US, "%.1f", grade) + " by " + username;
    } else {
      return "error -> " + title + " has been already rated";

    }
  }

  /**
   * functia care filtreaza queryurile pe actori.
   */
  public static String queryActors(final ActionInputData command,
                                   final Actors actors, final Shows shows) {
    return switch (command.getCriteria()) {
      case "average" -> queryActorsAverage(command, actors, shows);
      case "awards" -> queryActorAwards(command, actors);
      case "filter_description" -> queryActorsFilter(command, actors);
      default -> "";
    };
  }

  /**
   * functia care face array to string pentru actori.
   */
  public static String[] getStringArrayActor(final ArrayList<Actor> arr) {

    // declaration and initialise String Array
    String[] str = new String[arr.size()];

    // ArrayList to Array Conversion
    for (int j = 0; j < arr.size(); j++) {

      // Assign each value to String array
      str[j] = arr.get(j).toString();
    }

    return str;
  }

  /**
   * functia care face array to string pentru useri.
   */
  public static String[] getStringArrayUser(final ArrayList<User> arr) {

    // declaration and initialise String Array
    String[] str = new String[arr.size()];

    // ArrayList to Array Conversion
    for (int j = 0; j < arr.size(); j++) {

      // Assign each value to String array
      str[j] = arr.get(j).toString();
    }

    return str;
  }

  /**
   * functia care face array to string pentru videos.
   */
  public static String[] getStringArrayVideo(final ArrayList<Video> arr) {

    // declaration and initialise String Array
    String[] str = new String[arr.size()];

    // ArrayList to Array Conversion
    for (int j = 0; j < arr.size(); j++) {

      // Assign each value to String array
      str[j] = arr.get(j).toString();
    }

    return str;
  }

  /**
   * functia care face trigger la query average actors.
   */
  public static String queryActorsAverage(final ActionInputData command,
                                          final Actors actors, final Shows shows) {
    int number = command.getNumber();

    actors.computeRating(shows);
    ArrayList<Actor> averageActors = actors.getAverage(number, command.getSortType());

    return "Query result: " + Arrays.toString(getStringArrayActor(averageActors));
  }

  /**
   * functia care face trigger la query awards actors.
   */
  public static String queryActorAwards(final ActionInputData command, final Actors actors) {
    final int awardsNo = 3;
    ArrayList<Actor> awardsActors = actors.getAwards(command.getFilters().get(awardsNo),
                                                     command.getSortType());

    return "Query result: " + Arrays.toString(getStringArrayActor(awardsActors));
  }

  /**
   * functia care face trigger laquery filter description actors.
   */
  public static String queryActorsFilter(final ActionInputData command, final Actors actors) {
    ArrayList<Actor> filterActors = actors.getFilter(command.getFilters().get(2),
                                                     command.getSortType());

    return "Query result: " + Arrays.toString(getStringArrayActor(filterActors));
  }

  /**
   * functia care face trigger la query users.
   */
  public static String queryUsers(final ActionInputData command, final Users users) {
    ArrayList<User> ratingUsers = users.getRatingList(command.getNumber(), command.getSortType());

    return "Query result: " + Arrays.toString(getStringArrayUser(ratingUsers));
  }

  /**
   * functia care face trigger la query videos.
   */
  public static String queryVideos(final ActionInputData command, final Shows shows) {
    ArrayList<Video> videos = shows.getListQuery(command.getNumber(),
            command.getFilters().get(0).get(0),
            command.getFilters().get(1).get(0),
            command.getObjectType(),
            command.getSortType(),
            command.getCriteria());

    return "Query result: " + Arrays.toString(getStringArrayVideo(videos));

  }

  /**
   * functia care filtreaza recomandarile.
   */
  public static String recommendationUsers(final ActionInputData command, final Users users,
                                           final Shows shows, final ArrayList<String> showsTitle) {
    return switch (command.getType()) {
      case "standard" -> recommendationUsersStandard(command, users, shows, showsTitle);
      case "best_unseen" -> recommendationUsersBestUnseen(command, users, shows, showsTitle);
      case "popular" -> recommendationPopular(command, users, shows, showsTitle);
      case "favorite" -> recommendationFavorite(command, users, shows, showsTitle);
      case "search" -> recommendationSearch(command, users, shows, showsTitle);
      default -> "";
    };
  }

  /**
   * functia care face trigger recomandarea standard.
   */
  public static String recommendationUsersStandard(final ActionInputData command, final Users users,
                                                   final Shows shows,
                                                   final ArrayList<String> showsTitle) {
    String exportTitle = users.recommendationStandard(command.getUsername(),
                                                      shows, showsTitle);

    if (exportTitle.equals("x")) {
      return "StandardRecommendation cannot be applied!";
    } else {
      return "StandardRecommendation result: " + exportTitle;
    }
  }

  /**
   * functia care face trigger recomandarea best unseen.
   */
  public static String recommendationUsersBestUnseen(final ActionInputData command,
                                                     final Users users, final Shows shows,
                                                     final ArrayList<String> showsTitle) {
    String exportTitle = users.recommendationBestUnseen(command.getUsername(), shows, showsTitle);
    if (exportTitle.equals("x")) {
      return "BestRatedUnseenRecommendation cannot be applied!";
    } else {
      return "BestRatedUnseenRecommendation result: " + exportTitle;
    }
  }

  /**
   * functia care face trigger recomandarea popular.
   */
  public static String recommendationPopular(final ActionInputData command,
                                             final Users users, final Shows shows,
                                             final ArrayList<String> showsTitle) {
    String exportTitle = users.recommendationPopular(command.getUsername(), shows, showsTitle);

    if (exportTitle.equals("x")) {
      return "PopularRecommendation cannot be applied!";
    } else {
      return "PopularRecommendation result: " + exportTitle;
    }
  }

  /**
   * functia care face trigger recomandarea favorite.
   */
  public static String recommendationFavorite(final ActionInputData command,
                                              final Users users, final Shows shows,
                                              final ArrayList<String> showsTitle) {
    String exportTitle = users.recommendationFavorite(command.getUsername(), shows, showsTitle);

    if (exportTitle.equals("x")) {
      return "FavoriteRecommendation cannot be applied!";
    } else {
      return "FavoriteRecommendation result: " + exportTitle;
    }
  }

  /**
   * functia care face trigger recomandarea search.
   */
  public static String recommendationSearch(final ActionInputData command,
                                            final Users users, final Shows shows,
                                            final ArrayList<String> showsTitle) {
    ArrayList<Video> videos = users.recommendationSearch(command.getUsername(),
                                                         command.getGenre(), shows);

    if (videos.size() == 0) {
      return "SearchRecommendation cannot be applied!";
    } else {
      return "SearchRecommendation result: " + Arrays.toString(getStringArrayVideo(videos));
    }
  }
}
