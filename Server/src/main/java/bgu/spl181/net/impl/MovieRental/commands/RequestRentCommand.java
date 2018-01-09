package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.Movie;
import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestRentCommand extends Command {

    public RequestRentCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "rent";
    }

    @Override
    public boolean execute() {
        if(!isLoggedIn() || args.size()!= 2){
            name = "request rent";
            return false;
        }
        String movieName = args.get(1);
        try {
            Movie movie = MovieDatabase.rentMovie(movieName, getLoggedUser(connectionId));
            if(movie != null){
                output = wrap(movieName) + " success";
                broadcast = "movie " + wrap(movie.getName()) + " " + movie.getAvailable() + " " + movie.getPrice();
                return true;
            } else {
                name = "request rent";
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            name = "request rent";
            return false;
        }
    }


    private static String wrap(String s){
        return "\"" + s + "\"";
    }

}
