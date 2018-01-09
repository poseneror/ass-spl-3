package bgu.spl181.net.impl.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class User {
    private String username;
    private String password;
    private String country;
    private boolean admin;
    private List<String> movies;
    private int balance;

    public User(String username, String password, String country){
        this.username = username;
        this.password = password;
        this.country = country;
        admin = false;
        movies = new ArrayList<>();
        balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getMovies() {
        return movies;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public void addMovie(String movie){
        movies.add(movie);
    }

    public void removeMovie(String movie){
        String toRemove = null;
        for(String m : movies){
            if(m.equals(movie)){
                toRemove = m;
                break;
            }
        }
        if(toRemove != null) {
            movies.remove(toRemove);
        }
    }
}
