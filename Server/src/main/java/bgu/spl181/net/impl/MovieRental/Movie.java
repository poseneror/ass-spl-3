package bgu.spl181.net.impl.MovieRental;

import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class Movie {
    private int id;
    private String name;
    private int price;
    private List<String> banned;
    private int available;
    private int total;

    public Movie(String name, int total, int price, List<String> banned){
        this.name = name;
        this.total = total;
        this.price = price;
        this.id = id;
        this.available = total;
        this.banned = banned;
    }

    public String getName() {
        return name;
    }

    public int getAvailable() {
        return available;
    }

    public int getPrice() {
        return price;
    }

    public int getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getBanned() {
        return banned;
    }

    public void setId(int id) {
        this.id = id;
    }
}
