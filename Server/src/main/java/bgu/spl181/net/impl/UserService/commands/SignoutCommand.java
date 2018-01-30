package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserServiceProtocol;

/**
 * Created by Or on 02/01/2018.
 */
public class SignoutCommand extends Command {

    public SignoutCommand(String args, UserServiceProtocol protocol){
        super(args, protocol);
        name = "signout";
    }

    @Override
    public boolean execute() {
        if(args.size() != 0 || !isLoggedIn()) {
            return false;
        }
        protocol.getSharedData().disconnect(connectionId);
        return true;
    }
}
