package bgu.spl181.net.impl.UserService;

import bgu.spl181.net.impl.UserService.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 02/01/2018.
 */
public class UserLibrary {
    List<User> users;


    public UserLibrary(){
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

}
