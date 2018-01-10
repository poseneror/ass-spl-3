package bgu.spl181.net.impl.UserService;

import bgu.spl181.net.impl.MovieRental.MiniMovie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.*;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Or on 01/01/2018.
 */
public class UserDatabase {
    private final static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static String DatabaseURL = "Database/Users.json";
    public static UserLibrary getUsers() throws IOException{
        lock.readLock().lock();
        UserLibrary users;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class,
                        (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                            new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();
        Reader reader = new FileReader(DatabaseURL);
        users = gson.fromJson(reader, UserLibrary.class);
        if(users == null){
            users = new UserLibrary();
        }
        reader.close();
        lock.readLock().unlock();
        return users;
    }

    public static void setUsers(UserLibrary library) throws IOException{
        lock.writeLock().lock();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class,
                        (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                                new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();
        Writer writer = new FileWriter(DatabaseURL);
        gson.toJson(library, writer);
        writer.close();
        lock.writeLock().unlock();
    }

    public static void updateUser(String username, int balance, List<MiniMovie> movies) throws IOException{
        lock.writeLock().lock();
        UserLibrary users = getUsers();
            for(User u : users.getUsers()){
            if(u.getUsername().equalsIgnoreCase(username)){
                u.setBalance(balance);
                u.setMovies(movies);
                setUsers(users);
            }
        }
        lock.writeLock().unlock();
    }

    // read locking the following method would have no impact at all on the consequences
    public static User getUserInfo(String username) throws IOException{
        List<User> users = getUsers().getUsers();
        for(User u : users){
            if(u.getUsername().equalsIgnoreCase(username)){
                return u;
            }
        }
        return null;
    }

    public static int updateUserBalance(String username, int toAdd) throws IOException{
        lock.writeLock().lock();
        UserLibrary users = getUsers();
        for(User u : users.getUsers()){
            if(u.getUsername().equalsIgnoreCase(username)){
                u.setBalance(u.getBalance() + toAdd);
                setUsers(users);
                lock.writeLock().unlock();
                return u.getBalance();
            }
        }
        lock.writeLock().unlock();
        return -1;
    }

    /**
     * @param user
     * @return false if the user already exists
     * @throws IOException
     */
    public static boolean addUser(User user) throws IOException{
        lock.writeLock().lock();
            UserLibrary users = getUsers();
            for(User u : users.getUsers()){
                if(u.getUsername().equalsIgnoreCase(user.getUsername())){
                    lock.writeLock().unlock();
                    return false;
                }
            }
            users.addUser(user);
            setUsers(users);
            lock.writeLock().unlock();
            return true;
    }

    public static boolean authenticate(String username, String password) throws IOException{
        List<User> users = getUsers().getUsers();
        for(User u : users){
            if(u.getUsername().equalsIgnoreCase(username)){
                return u.getPassword().equals(password);
            }
        }
        return false;
    }

    public static boolean isAdmin(String username) throws IOException{
        return getUserInfo(username).isAdmin();
    }

}