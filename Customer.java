package bookstore;

import java.util.ArrayList;

public class Customer extends User{
    private String name;
    private String username;
    private String password;
    private int points;
    private CustomerState state;

    public Customer(String name, String username, String password, int points)
    {
        super(username,password);
        this.points = points;
        this.state = (points >= 1000) ? new GoldState(this) : new SilverState(this);
        this.name = name;
    }

    public void buyBook(String bookName, double price) {
        state.buyBook(bookName, price);
    }

    public void redeemPoints(String bookName, int points) {
        state.redeemPoints(bookName, points);
    }

    public void setState(CustomerState state) {
        this.state = state;
    }
    
    public String getState (int points) {
        if (points < 1000) {
            return "Silver";
        }
        else {
            return "Gold";
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
    
    public String getUsername() {
        return super.getUsername();
    }
    
    public String getName() {
        return name;
    }
}
