package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;

import java.io.IOException;

/**
 * Created by Or on 02/01/2018.
 */
public class RequestBalanceAddCommand extends Command {
    public RequestBalanceAddCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "balance";
    }

    @Override
    public boolean execute() {
        if(!isLoggedIn()){
            name = "request balance";
            return false;
        }
        if(args.size() >= 3){
            int toAdd = Integer.parseInt(args.get(2));
            int balance = balanceAddRequest(toAdd);
            if(balance != -1){
                output = balance + " added " + toAdd;
                return true;
            }
            name = "request balance";
            return false;
        }
        name = "request balance";
        return false;
    }

    private int balanceAddRequest(int toAdd){
        try {
            return UserDatabase.updateUserBalance(getLoggedUser(connectionId), toAdd);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
