package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.MovieRental.Movie;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestInfoCommand extends Command {

    public RequestInfoCommand(String args, UserServiceProtocol protocol) {
        super(args, protocol);
        name = "info";
    }

    @Override
    public boolean execute() {
        if (!isLoggedIn()) {
            name = "request info";
            return false;
        }
        if (args.size() == 1) {
            output = "";
            try {
                for (Movie movie : MovieDatabase.getMovies().getMovies()) {
                    output += wrap(movie.getName()) + " ";
                }
                output = output.trim();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                name = "request info";
                return false;
            }

        } else {
            String movieName = args.get(1);
            try {
                Movie movie = MovieDatabase.getMovieInfo(movieName);
                if (movie != null) {
                    output = wrap(movie.getName()) + " " + movie.getAvailableAmount() + " " + movie.getPrice() + " ";
                    if (movie.getBannedCountries() != null) {
                        for (String country : movie.getBannedCountries()) {
                            output += wrap(country) + " ";
                        }
                    }
                    output.trim();
                    return true;
                } else {
                    name = "request info";
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                name = "request info";
                return false;
            }
        }
    }


    private static String wrap(String s) {
        return "\"" + s + "\"";
    }

}
