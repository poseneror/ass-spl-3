package bgu.spl181.net.impl.MovieRental;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Or on 02/01/2018.
 */
public class MovieLibrary {
    List<Movie> movies;


    public MovieLibrary(){
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
        movie.setId(getLastId());
        movies.add(movie);
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
        int lastId = 0;
        for(Movie m : getMovies()){
            if(m.getId() > lastId){
                lastId = m.getId();
            }
        }
        lastId++;
        return lastId;
    }

}
