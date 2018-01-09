package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class LoginCommand extends Command {
    public LoginCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "login";
    }

    @Override
    public boolean execute() {
        if(args.size() != 2) {
            return false;
        }
        String username = args.get(0);
        String password = args.get(1);
        if(isLoggedIn() || isUserConnected(username)){
            return false;
        }
        try {
            if(UserDatabase.authenticate(username, password)){
                protocol.getSharedData().assignUser(connectionId, username);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
