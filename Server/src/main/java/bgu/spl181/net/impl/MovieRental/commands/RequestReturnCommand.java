package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.Movie;
import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestReturnCommand extends Command {

    public RequestReturnCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "return";
    }

    @Override
    public boolean execute() {
        if(!isLoggedIn() || args.size()!= 2){
            return false;
        }
        String movieName = args.get(1);
        try {
            Movie movie = MovieDatabase.returnMovie(movieName, getLoggedUser(connectionId));
            if(movie != null){
                output = wrap(movieName) + " success";
                broadcast = "movie " + wrap(movie.getName()) + " " + movie.getAvailable() + " " + movie.getPrice();
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static String wrap(String s){
        return "\"" + s + "\"";
    }

}
