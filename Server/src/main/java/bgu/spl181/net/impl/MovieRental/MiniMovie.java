package bgu.spl181.net.impl.MovieRental;

import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class MiniMovie {
    private int id;
    private String name;

    public MiniMovie(Movie movie){
        this.name = movie.getName();
        this.id = movie.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
