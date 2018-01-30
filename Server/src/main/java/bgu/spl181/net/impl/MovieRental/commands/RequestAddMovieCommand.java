package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.Movie;
import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestAddMovieCommand extends Command {
    public RequestAddMovieCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "addmovie";
    }

    @Override
    public boolean execute() {
        if(!isAdmin()){
            name = "request addmovie";
            return false;
        }
        if(args.size() >= 4){
            String movieName = args.get(1);
            int amount = Integer.parseInt(args.get(2));
            int price = Integer.parseInt(args.get(3));
            if(amount <= 0 || price <= 0){
                name = "request addmovie";
                return false;
            }
            List<String> banned = new ArrayList<>();
            if(args.size() >= 5){
                for(int i = 4; i < args.size(); i++){
                    banned.add(args.get(i));
                }
            }
            Movie movie = new Movie(movieName, amount, price, banned);
            try {
                Movie added = MovieDatabase.addMovie(movie);
                if(added != null) {
                    output = wrap(added.getName()) + " success";
                    broadcast = "movie " + wrap(added.getName()) + " " + added.getAvailableAmount() + " " + added.getPrice();
                    return true;
                } else {
                    name = "request addmovie";
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                name = "request addmovie";
                return false;
            }
        }
        name = "request addmovie";
        return false;
    }

    private static String wrap(String s){
        return "\"" + s + "\"";
    }
}
