package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import data_set.Shows;
import data_set.Users;
import entertainment.Season;
import fileio.*;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import user.User;
import video.Movie;
import video.Serial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

        // add all users to the dataSet
        Users users = new Users();
        for (UserInputData user : input.getUsers()) {
            User userBuff = new User(user.getUsername(), user.getSubscriptionType(),
                    user.getHistory(), user.getFavoriteMovies());
            users.addUser(user.getUsername(), userBuff);
        }

        // add all shows to the dataset
        Shows shows = new Shows();
        // movies
        for (MovieInputData movie : input.getMovies()) {
            Movie movieBuff = new Movie(movie.getTitle(), movie.getCast(),
                                        movie.getGenres(), movie.getYear(),
                                        movie.getDuration());
            shows.addShow(movie.getTitle(), movieBuff);
        }
        // serials
        for (SerialInputData serial : input.getSerials()) {
            Serial serialBuff = new Serial(serial.getTitle(), serial.getCast(),
                    serial.getGenres(), serial.getNumberSeason(), serial.getSeasons(),
                    serial.getYear());
            shows.addShow(serial.getTitle(), serialBuff);
        }



        List<ActionInputData> commandsData = input.getCommands();
        for (ActionInputData command : commandsData) {
            System.out.println(command);
            int id = command.getActionId();
            if (command.getActionType().equals("command")) {
                String message = commands(command, users, shows);
            }
        }


        fileWriter.closeJSON(arrayResult);
    }

    public static String commands(ActionInputData command, Users users, Shows shows) {
        return switch (command.getType()) {
            case "favorite" -> commandFavorite(command, users, shows);
            case "view" -> commandView(command, users, shows);
            case "rating" -> commandRating(command, users, shows);
            default -> "";
        };
    }

    public static String commandFavorite(ActionInputData command, Users users, Shows shows) {
        String username = command.getUsername();
        String title = command.getTitle();
        users.addFavorite(username, title);
        shows.addFavorite(title);
        return "success -> " + title + " was added as favourite";
    }

    public static String commandView(ActionInputData command, Users users, Shows shows) {
        String username = command.getUsername();
        String title = command.getTitle();
        int views = users.addView(username, title);
        shows.addView(title);
        return "success -> " + title + " was viewed with total views of " + views;
    }

    public static String commandRating(ActionInputData command, Users users, Shows shows) {
        String username = command.getUsername();
        String title = command.getTitle();
        double grade = command.getGrade();
        int season = command.getSeasonNumber();
        if (users.addRating(username, title, grade) == 0) {
            return "error -> " + title + " is not seen";
        } else {
            shows.addRating(title, grade, season);
            return "success -> " + title + " was rated with " + String.format("%.1f",grade) + " by " + username;
        }
    }
}
