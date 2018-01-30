package bgu.spl181.net.impl.MovieRental;

import bgu.spl181.net.impl.MovieRental.commands.*;
import bgu.spl181.net.impl.UserService.SharedProtocolData;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.impl.UserService.commands.Command;

/**
 * Created by Or on 01/01/2018.
 */
public class MovieRentalProtocol extends UserServiceProtocol {

    public MovieRentalProtocol(SharedProtocolData sharedData){
        super(sharedData);
    }

    @Override
    public void process(String message) {
        super.process(message);
        if(message.startsWith("REQUEST info")){
            Command command = new RequestInfoCommand(message,this);
            command.run();
        } else if(message.startsWith("REQUEST rent")){
            Command command = new RequestRentCommand(message, this);
            command.run();
        } else if(message.startsWith("REQUEST return")){
            Command command = new RequestReturnCommand(message, this);
            command.run();
        } else if(message.startsWith("REQUEST addmovie")){
            Command command = new RequestAddMovieCommand(message, this);
            command.run();
        } else if(message.startsWith("REQUEST remmovie")){
            Command command = new RequestRemMovieCommand(message, this);
            command.run();
        } else if(message.startsWith("REQUEST changeprice")){
            Command command = new RequestChangePriceCommand(message, this);
            command.run();
        }
    }
}
