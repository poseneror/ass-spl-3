package bgu.spl181.net.impl.UserService.commands;

import bgu.spl181.net.impl.UserService.UserDatabase;
import bgu.spl181.net.impl.UserService.UserServiceProtocol;
import bgu.spl181.net.interfaces.Connections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Or on 01/01/2018.
 */
public abstract class Command {

    protected List<String> args;
    protected String name;
    protected String output;
    protected int connectionId;
    protected Connections connections;
    protected String broadcast = null;
    protected UserServiceProtocol protocol;

    public Command(String args, UserServiceProtocol protocol){
        this.args = new ArrayList<String>();
        Matcher m = Pattern.compile("(\\S*=\".+?\"|[^\"]\\S*|\".+?\")\\s*").matcher(args);
        while (m.find())
            this.args.add(m.group(1).replace("\"", ""));
        this.args.remove(0);
        this.name = "command";
        this.output = "succeeded";
        this.connectionId = protocol.getConnectionId();
        this.connections = protocol.getClients();
        this.protocol = protocol;
    }
    public void run(){
        if(execute()){
            success();
            if(broadcast != null){
                sendBroadcast();
            }
        } else {
            fail();
        }
    }
    public abstract boolean execute();

    public String getName() {
        return name;
    }

    public String getOutput() {
        return output;
    }

    private void success(){
        connections.send(connectionId, "ACK " + getName() + " " + getOutput());
    }
    private void fail(){
        connections.send(connectionId, "ERROR " + getName() + " failed");
    }
    private void sendBroadcast(){
        protocol.broadcastToLogged("BROADCAST " + broadcast);
    }


    protected boolean isUserConnected(String username){
        return protocol.getSharedData().isUserConnected(username);
    }

    protected String getLoggedUser(int connectionId){
        return protocol.getSharedData().getLoggedUser(connectionId);
    }

    protected boolean isLoggedIn(){
        return protocol.getSharedData().isUserInClient(connectionId);
    }

    protected boolean isAdmin(){
        if(!isLoggedIn()){
            return false;
        }
        try {
            return UserDatabase.isAdmin(protocol.getSharedData().getLoggedUser(connectionId));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
