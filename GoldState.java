package bookstore;

public class GoldState extends CustomerState {
    public GoldState(Customer customer) {
        super(customer);
    }

    @Override
    public void buyBook(String bookName, double price) {
        customer.addPoints((int)(price * 10)); //Adds 10 points per dollar spent on book
    }

    @Override
    public void redeemPoints(String bookName, int points) {
        if (customer.getPoints() >= points) {
            customer.addPoints(-points);
            
            if (customer.getPoints() < 1000) {
                customer.setState(new SilverState(customer));
            }
        }
    }
}
