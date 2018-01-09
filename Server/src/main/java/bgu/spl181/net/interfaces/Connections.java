package bgu.spl181.net.interfaces;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void broadcast(T msg);

    void disconnect(int connectionId);

    int connect(ConnectionHandler<T> handler);
}
