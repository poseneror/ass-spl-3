package bgu.spl181.net.impl.UserService;

import bgu.spl181.net.impl.UserService.commands.*;
import bgu.spl181.net.interfaces.BidiMessagingProtocol;
import bgu.spl181.net.interfaces.Connections;

/**
 * Created by Or on 01/01/2018.
 */
public abstract class UserServiceProtocol implements BidiMessagingProtocol<String> {
    protected Connections clients;
    protected int connectionId;
    private boolean terminate;
    private SharedProtocolData sharedData;

    public UserServiceProtocol(SharedProtocolData sharedData){
        this.sharedData = sharedData;
    }

    /**
     * This method initialize the protocol with the current connections, and the client connectionId
     * @param connectionId
     * @param connections
     */
    //TODO: statr must end before
    @Override
    public void start(int connectionId, Connections connections) {
        this.clients = connections;
        this.connectionId = connectionId;
        this.terminate = false;
    }

    public SharedProtocolData getSharedData() {
        return sharedData;
    }

    public Connections getClients() {
        return clients;
    }

    public int getConnectionId() {
        return connectionId;
    }

    @Override
    public void process(String message) {
        if(message.startsWith("REGISTER")){
            Command command = new RegisterCommand(message, this);
            command.run();
        } else if(message.startsWith("LOGIN")){
            Command command = new LoginCommand(message, this);
            command.run();
        } else if(message.startsWith("SIGNOUT")){
            Command command = new SignoutCommand(message, this);
            if(command.run()) {
                Terminate();
            }
        } else if(message.startsWith("REQUEST balance info")){
            Command command = new RequestBalanceInfoCommand(message, this);
            command.run();
        } else if(message.startsWith("REQUEST balance add")){
            Command command = new RequestBalanceAddCommand(message, this);
            command.run();
        }
    }

    public void Terminate(){
        clients.disconnect(connectionId);
        terminate = true;
    }

    @Override
    public boolean shouldTerminate() {
        // the thread will get here after the signout process and terminate would be true
        return terminate;
    }

    public void broadcastToLogged(String msg) {
        for(int user_id : sharedData.getLoggedUsers().keySet()){
            clients.send(user_id, msg);
        }
    }
}
