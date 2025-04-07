package bookstore;

import java.util.ArrayList;

public abstract class CustomerState {
    protected Customer customer;

    public CustomerState(Customer customer) {
        this.customer = customer;
    }

    public abstract void buyBook(String bookName, double price);
    public abstract void redeemPoints(String bookName, int points);
}
