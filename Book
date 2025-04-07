package bookstore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Book
{
    private String name;
    private double price;
    private String author;
    private String ISBN;
    private int stock;
    private final BooleanProperty selection = new SimpleBooleanProperty(false); //Allows selection (selected = true, unselected = false)
    
    public Book (String name, double price, String author, String ISBN, int stock){
        this.name = name;
        this.price = price;
        this.author = author;
        this.ISBN = ISBN;
        this.stock = stock;
    }
    
    public String getName (){
        return this.name;
    }
    
    public double getPrice (){
        return this.price;
    }
    
    public String getAuthor (){
        return this.author;
    }
    
    public String getISBN (){
        return this.ISBN;
    }
    
    public void setName (String name){
        this.name = name;
    }
    
    public void setPrice (double price){
        this.price = price;
    }
    
    public void setStock (int SetStockNum){
        this.stock = SetStockNum;
    }
    
    public int getStock () {
        return stock;
    }
    
    //Getter to return if the book was specifically selected
    public BooleanProperty getSelectedBook() {
        return selection;
    }
    
    //Returns if book(s) are selected (true) or unselected (false)
    public boolean isSelectedChecker() {
        return selection.get();
    }
    
    //Changes checkbox to be true or false 
    public void setSelected(boolean selected) {
        this.selection.set(selected);
    }
    
}
