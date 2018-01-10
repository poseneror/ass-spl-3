package bgu.spl181.net.impl.MovieRental;

import java.util.List;

/**
 * Created by Or on 01/01/2018.
 */
public class Movie {
    private int id;
    private String name;
    private int price;
    private List<String> bannedCountries;
    private int availableAmount;
    private int totalAmount;

    public Movie(String name, int total, int price, List<String> banned){
        this.name = name;
        this.totalAmount = total;
        this.price = price;
        this.id = id;
        this.availableAmount = total;
        this.bannedCountries = banned;
    }

    public String getName() {
        return name;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getBannedCountries() {
        return bannedCountries;
    }

    public void setId(int id) {
        this.id = id;
    }
}
