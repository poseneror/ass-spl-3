package bgu.spl181.net.impl.UserService;

import bgu.spl181.net.interfaces.ConnectionHandler;
import bgu.spl181.net.interfaces.Connections;

import java.util.HashMap;

/**
 * Created by Or on 01/01/2018.
 */
public class UserConnections<T> implements Connections<T> {

    private HashMap<Integer, ConnectionHandler<T>> clients;
    private int currentId;

    public UserConnections(){
        clients = new HashMap<>();
        currentId = 1;
    }

    @Override
    synchronized public boolean send(int connectionId, T msg) {
        if(clients.containsKey(connectionId)){
            clients.get(connectionId).send(msg);
            return true;
        }
        return false;
    }

    @Override
    synchronized public void broadcast(T msg) {
        for(ConnectionHandler client : clients.values()){
            client.send(msg);
        }
    }


    @Override
    synchronized public void disconnect(int connectionId) {
        clients.remove(connectionId);
    }

    @Override
    synchronized public int connect(ConnectionHandler handler) {
        clients.put(currentId, handler);
        //TODO: maybe this is fucking shit
        return currentId++;
    }
}
