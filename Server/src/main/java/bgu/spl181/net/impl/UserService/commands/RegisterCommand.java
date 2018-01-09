package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserDatabase;
import bgu.spl181.net.impl.UserService.User;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;

import java.io.*;

/**
 * Created by Or on 01/01/2018.
 */
public class RegisterCommand extends Command {
    public RegisterCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "registration";
    }


    /**
     * Failure:
     * 1. The client performing the register call is already logged in.
     * 2. The username requested is already registered in the system.
     * 3. Missing info (username/password).
     * 4. Data block does not fit service requirements (defined in rental service section).
     */
    @Override
    public boolean execute() {
        if(args.size() < 2 || args.size() > 3) {
            return false;
        }
        String username = args.get(0);
        String password = args.get(1);
        String country = null;
        if (args.size() == 3) {
            if(!args.get(2).startsWith("country=")){
                return false;
            }
            country = args.get(2).substring(args.get(2).indexOf("=") + 1);
        }
        if(isLoggedIn()){
            return false;
        }
        User user = new User(username, password, country);
        try {
            return UserDatabase.addUser(user);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
