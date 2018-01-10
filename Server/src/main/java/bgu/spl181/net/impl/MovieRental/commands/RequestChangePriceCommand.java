package bgu.spl181.net.impl.MovieRental.commands;

import bgu.spl181.net.impl.MovieRental.Movie;
import bgu.spl181.net.impl.MovieRental.MovieDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestChangePriceCommand extends Command {
    public RequestChangePriceCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "changeprice";
    }

    @Override
    public boolean execute() {
        if(!isAdmin()){
            return false;
        }
        if(args.size() == 3){
            String name = args.get(1);
            int price = Integer.parseInt(args.get(2));
            try {
                Movie movie = MovieDatabase.updateMoviePrice(name, price);
                if(movie != null){
                    output =  wrap(movie.getName()) + " success";
                    broadcast = "movie " + wrap(movie.getName()) + " " + movie.getAvailableAmount() + " " + movie.getPrice();
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
