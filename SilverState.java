package bookstore;

import java.util.ArrayList;

public class SilverState extends CustomerState {
    public SilverState(Customer customer) {
        super(customer);
    }

    @Override
    public void buyBook(String bookName, double price) {
        int pointsEarned = (int)(price * 10);
        customer.addPoints(pointsEarned); //Adds 10 points per dollar spent
        
        if (customer.getPoints() >= 1000) {
            customer.setState(new GoldState(customer));
        }
    }

    @Override
    public void redeemPoints(String bookName, int points) {
        if (customer.getPoints() >= points) {
            customer.addPoints(-points);
        }
    }

    
}
