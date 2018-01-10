package bgu.spl181.net.impl.UserService;

import bgu.spl181.net.impl.MovieRental.MiniMovie;
import bgu.spl181.net.impl.MovieRental.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class User {
    private String username;
    private String password;
    private String country;
    private String type;
    private List<MiniMovie> movies;
    private int balance;

    public User(String username, String password, String country){
        this.username = username;
        this.password = password;
        this.country = country;
        type = "normal";
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

    public List<MiniMovie> getMovies() {
        return movies;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return type.equals("admin");
    }

    public void setMovies(List<MiniMovie> movies) {
        this.movies = movies;
    }

    public void addMovie(MiniMovie movie){
        movies.add(movie);
    }
    public boolean hasMovie(Movie movie){
        for(MiniMovie m : getMovies()){
            if(m.getName().equalsIgnoreCase(movie.getName())){
                return true;
            }
        }
        return false;
    }
    public void removeMovie(String movie){
        MiniMovie toRemove = null;
        for(MiniMovie m : movies){
            if(m.getName().equalsIgnoreCase(movie)){
                toRemove = m;
                break;
            }
        }
        if(toRemove != null) {
            movies.remove(toRemove);
        }
    }
}
