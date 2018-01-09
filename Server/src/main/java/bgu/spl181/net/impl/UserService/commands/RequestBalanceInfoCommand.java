package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserDatabase;
import bgu.spl181.net.impl.UserService.User;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestBalanceInfoCommand extends Command {
    public RequestBalanceInfoCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "balance";
    }

    @Override
    public boolean execute() {
        if(!isLoggedIn()){
            name = "request balance";
            return false;
        }
        int balance = balanceInfoRequest();
        if(balance == -1){
            name = "request balance";
            return false;
        } else {
            output = Integer.toString(balance);
            return true;
        }
    }

    private int balanceInfoRequest(){
        try {
            User user = UserDatabase.getUserInfo(getLoggedUser(connectionId));
            if(user != null) {
                return user.getBalance();
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
