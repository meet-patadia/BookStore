package bookstore;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private static Owner instance;
    private List<Book> books;
    private List<Customer> customers;

    private Owner() {  // Private constructor for Singleton
        this.books = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public static Owner getInstance() {
        if (instance == null) {
            instance = new Owner();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
}
