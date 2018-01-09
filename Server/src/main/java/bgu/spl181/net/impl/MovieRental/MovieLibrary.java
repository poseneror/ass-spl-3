package bgu.spl181.net.impl.MovieRental;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 02/01/2018.
 */
public class MovieLibrary {
    int lastId;
    List<Movie> movies;


    public MovieLibrary(){
        this.lastId = 1;
        movies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public boolean addMovie(Movie movie){
        for(Movie m : movies){
            if(m.getName().equals(movie.getName())){
                return false;
            }
        }
        movie.setId(lastId);
        movies.add(movie);
        lastId++;
        return true;
    }

    public boolean removeMovie(String name){
        for(Movie m : movies){
            if(m.getName().equals(name)){
                movies.remove(m);
                return true;
            }
        }
        return false;
    }

    public int getLastId() {
        return lastId;
    }

}
