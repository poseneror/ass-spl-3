package bgu.spl181.net.impl.UserService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class UserDatabase {
    private static String DatabaseURL = "Database/example_Users.json";
    synchronized public static List<User> getUsers() throws IOException{
        List<User> users;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<ArrayList<User>>(){}.getType();
        Reader reader = new FileReader(DatabaseURL);
        users = gson.fromJson(reader, listType);
        if(users == null){
            users = new ArrayList<User>();
        }
        reader.close();
        return users;
    }

    synchronized public static void setUsers(List<User> users) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(DatabaseURL);
        gson.toJson(users, writer);
        writer.close();
    }

    synchronized public static void updateUser(String username, int balance, List<String> movies) throws IOException{
        List<User> users = getUsers();
            for(User u : users){
            if(u.getUsername().equals(username)){
                u.setBalance(balance);
                u.setMovies(movies);
                setUsers(users);
            }
        }
    }

    synchronized public static User getUserInfo(String username) throws IOException{
        List<User> users = getUsers();
        for(User u : users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    synchronized public static int updateUserBalance(String username, int toAdd) throws IOException{
        List<User> users = getUsers();
        for(User u : users){
            if(u.getUsername().equals(username)){
                u.setBalance(u.getBalance() + toAdd);
                setUsers(users);
                return u.getBalance();
            }
        }
        return -1;
    }

    /**
     * @param user
     * @return false if the user already exists
     * @throws IOException
     */
    synchronized public static boolean addUser(User user) throws IOException{
            List<User> users = getUsers();
            for(User u : users){
                if(u.getUsername().equals(user.getUsername())){
                    return false;
                }
            }
            users.add(user);
            setUsers(users);
            return true;
    }

    synchronized public static boolean authenticate(String username, String password) throws IOException{
        List<User> users = getUsers();
        for(User u : users){
            if(u.getUsername().equals(username)){
                return u.getPassword().equals(password);
            }
        }
        return false;
    }

    synchronized public static boolean isAdmin(String username) throws IOException{
        return getUserInfo(username).isAdmin();
    }

}