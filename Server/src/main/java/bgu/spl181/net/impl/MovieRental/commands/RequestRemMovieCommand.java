package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestRemMovieCommand extends Command {
    public RequestRemMovieCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "remmovie";
    }

    @Override
    public boolean execute() {
        if(!isAdmin()){
            return false;
        }
        if(args.size() == 2){
            String name = args.get(1);
            try {

                if(MovieDatabase.removeMovie(name)){
                    output =  wrap(name) + " success";
                    broadcast = "movie " + wrap(name) + " removed";
                    return true;
                } else {
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static String wrap(String s){
        return "\"" + s + "\"";
    }
}
