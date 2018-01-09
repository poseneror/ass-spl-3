package bgu.spl181.net.impl.MovieRental;

import bgu.spl181.net.impl.UserService.User;
import bgu.spl181.net.impl.UserService.UserDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by Or on 01/01/2018.
 */
public class MovieDatabase {
    private static String DatabaseURL = "Database/example_Movies.json";
    synchronized public static MovieLibrary getMovies() throws IOException{
        MovieLibrary movies;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Reader reader = new FileReader(DatabaseURL);
        movies = gson.fromJson(reader, MovieLibrary.class);
        if(movies == null){
            movies = new MovieLibrary();
        }
        reader.close();
        return movies;
    }

    synchronized public static void setMovies(MovieLibrary movies) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(DatabaseURL);
        gson.toJson(movies, writer);
        writer.close();
    }

    synchronized public static Movie addMovie(Movie movie) throws IOException{
        MovieLibrary movies = getMovies();
        if(movies.addMovie(movie)){
            setMovies(movies);
            return movie;
        }
        return null;
    }

    synchronized public static boolean removeMovie(String movieName) throws IOException{
        MovieLibrary movies = getMovies();
        for(Movie m : movies.getMovies()){
            if(m.getName().equals(movieName)){
                if(m.getAvailable() == m.getTotal()){
                    movies.removeMovie(movieName);
                    setMovies(movies);
                    return true;
                }
            }
        }
        return false;
    }

    synchronized public static Movie getMovieInfo(String movieName) throws IOException{
        MovieLibrary movieLibrary = getMovies();
        for(Movie m : movieLibrary.getMovies()){
            if(m.getName().equals(movieName)){
                return m;
            }
        }
        return null;
    }

    synchronized public static Movie rentMovie(String movieName, String username) throws IOException{
        User user = UserDatabase.getUserInfo(username);
        Movie movie = getMovieInfo(movieName);
        if(movie != null && user != null) {
            if (movie.getAvailable() > 0 && user.getBalance() >= movie.getPrice()
                    && user.getCountry() != null && !movie.getBanned().contains(user.getCountry())
                    && !user.getMovies().contains(movie.getName())){
                user.setBalance(user.getBalance() - movie.getPrice());
                user.addMovie(movie.getName());
                UserDatabase.updateUser(user.getUsername(), user.getBalance(), user.getMovies());
                movie.setAvailable(movie.getAvailable() - 1);
                updateMovie(movie.getName(), movie.getAvailable());
                return movie;
            }
        }
        return null;
    }

    synchronized public static Movie returnMovie(String movieName, String username) throws IOException{
        User user = UserDatabase.getUserInfo(username);
        Movie movie = getMovieInfo(movieName);
        if(movie != null && user != null) {
            if (user.getMovies().contains(movie.getName())){
                user.removeMovie(movie.getName());
                UserDatabase.updateUser(user.getUsername(), user.getBalance(), user.getMovies());
                movie.setAvailable(movie.getAvailable() + 1);
                updateMovie(movie.getName(), movie.getAvailable());
                return movie;
            }
        }
        return null;
    }

    synchronized public static void updateMovie(String name, int available) throws IOException{
        MovieLibrary movies = getMovies();
        for(Movie m : movies.getMovies()){
            if(m.getName().equals(name)){
                m.setAvailable(available);
                setMovies(movies);
            }
        }
    }

    synchronized public static Movie updateMoviePrice(String name, int price) throws IOException{
        if(price > 0) {
            MovieLibrary movies = getMovies();
            for (Movie m : movies.getMovies()) {
                if (m.getName().equals(name)) {
                    m.setPrice(price);
                    setMovies(movies);
                    return m;
                }
            }
        }
        return null;
    }
}