package bgu.spl181.net.impl.UserService;

import java.util.HashMap;

/**
 * Created by Or on 04/01/2018.
 */
public class SharedProtocolData {

    private HashMap<Integer, String> loggedUsers;

    public SharedProtocolData(){
        loggedUsers = new HashMap<>();
    }

    synchronized public String getLoggedUser(int connectionId){
        if(loggedUsers.containsKey(connectionId)){
            return loggedUsers.get(connectionId);
        }
        return null;
    }


    synchronized public boolean isUserInClient(int connectionId){
        return loggedUsers.containsKey(connectionId);
    }

    synchronized public boolean isUserConnected(String username){
        return loggedUsers.containsValue(username);
    }

    synchronized public void assignUser(int connectionId, String user){
        loggedUsers.put(connectionId, user);
    }

    synchronized public void disconnect(int connectionId) {
        loggedUsers.remove(connectionId);
    }

    public HashMap<Integer, String> getLoggedUsers() {
        return loggedUsers;
    }
}
