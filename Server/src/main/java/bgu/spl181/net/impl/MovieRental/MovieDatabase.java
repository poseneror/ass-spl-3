package bgu.spl181.net.impl.MovieRental;

import bgu.spl181.net.impl.UserService.User;
import bgu.spl181.net.impl.UserService.UserDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Or on 01/01/2018.
 */
public class MovieDatabase {
    private final static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static String DatabaseURL = "Database/Movies.json";

    public static MovieLibrary getMovies() throws IOException{
        lock.readLock().lock();
        MovieLibrary movies;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class,
                        (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                                new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();
        Reader reader = new FileReader(DatabaseURL);
        movies = gson.fromJson(reader, MovieLibrary.class);
        if(movies == null){
            movies = new MovieLibrary();
        }
        reader.close();
        lock.readLock().unlock();
        return movies;
    }

    public static void setMovies(MovieLibrary movies) throws IOException{
        lock.writeLock().lock();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class,
                        (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                                new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();
        Writer writer = new FileWriter(DatabaseURL);
        gson.toJson(movies, writer);
        writer.close();
        lock.writeLock().unlock();
    }

    public static Movie addMovie(Movie movie) throws IOException{
        lock.writeLock().lock();
        MovieLibrary movies = getMovies();
        if(movies.addMovie(movie)){
            setMovies(movies);
            lock.writeLock().unlock();
            return movie;
        }
        lock.writeLock().unlock();
        return null;
    }

    public static boolean removeMovie(String movieName) throws IOException{
        lock.writeLock().lock();
        MovieLibrary movies = getMovies();
        for(Movie m : movies.getMovies()){
            if(m.getName().equalsIgnoreCase(movieName)){
                if(m.getAvailableAmount() == m.getTotalAmount()){
                    movies.removeMovie(movieName);
                    setMovies(movies);
                    lock.writeLock().unlock();
                    return true;
                }
            }
        }
        lock.writeLock().unlock();
        return false;
    }

    public static Movie getMovieInfo(String movieName) throws IOException{
        lock.readLock().lock();
        MovieLibrary movieLibrary = getMovies();
        for(Movie m : movieLibrary.getMovies()){
            if(m.getName().equalsIgnoreCase(movieName)){
                lock.readLock().unlock();
                return m;
            }
        }
        lock.readLock().unlock();
        return null;
    }

    public static Movie rentMovie(String movieName, String username) throws IOException{
        lock.writeLock().lock();
        User user = UserDatabase.getUserInfo(username);
        Movie movie = getMovieInfo(movieName);
        if(movie != null && user != null) {
            if (movie.getAvailableAmount() > 0 && user.getBalance() >= movie.getPrice()
                    && (user.getCountry() == null ||(user.getCountry() != null && !movie.getBannedCountries().contains(user.getCountry())))
                    && !user.hasMovie(movie)){
                user.setBalance(user.getBalance() - movie.getPrice());
                user.addMovie(new MiniMovie(movie));
                UserDatabase.updateUser(user.getUsername(), user.getBalance(), user.getMovies());
                movie.setAvailableAmount(movie.getAvailableAmount() - 1);
                updateMovie(movie.getName(), movie.getAvailableAmount());
                lock.writeLock().unlock();
                return movie;
            }
        }
        lock.writeLock().unlock();
        return null;
    }

    public static Movie returnMovie(String movieName, String username) throws IOException{
        lock.writeLock().lock();
        User user = UserDatabase.getUserInfo(username);
        Movie movie = getMovieInfo(movieName);
        if(movie != null && user != null) {
            if (user.hasMovie(movie)){
                user.removeMovie(movie.getName());
                UserDatabase.updateUser(user.getUsername(), user.getBalance(), user.getMovies());
                movie.setAvailableAmount(movie.getAvailableAmount() + 1);
                updateMovie(movie.getName(), movie.getAvailableAmount());
                lock.writeLock().unlock();
                return movie;
            }
        }
        lock.writeLock().unlock();
        return null;
    }

    public static void updateMovie(String name, int available) throws IOException{
        lock.writeLock().lock();
        MovieLibrary movies = getMovies();
        for(Movie m : movies.getMovies()){
            if(m.getName().equalsIgnoreCase(name)){
                m.setAvailableAmount(available);
                setMovies(movies);
            }
        }
        lock.writeLock().unlock();
    }

    public static Movie updateMoviePrice(String name, int price) throws IOException{
        lock.writeLock().lock();
        if(price > 0) {
            MovieLibrary movies = getMovies();
            for (Movie m : movies.getMovies()) {
                if (m.getName().equalsIgnoreCase(name)) {
                    m.setPrice(price);
                    setMovies(movies);
                    lock.writeLock().unlock();
                    return m;
                }
            }
        }
        lock.writeLock().unlock();
        return null;
    }
}