package bookstore;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


/**
 *
 * @author a34hasan
 */
public class Bookstore extends Application {
        private ArrayList<Book> books;
        private ArrayList<Customer> customers;
    
    
        public Bookstore()
        {
            books=new ArrayList<Book>();
            customers=new ArrayList<Customer>();
        }
        public void addBook(Book bookName) {
            books.add(bookName);
            System.out.println(bookName + " added to the store.");
        }
    
        public void removeBook(Book bookName) {
            if (books.remove(bookName)) {
                System.out.println(bookName + " removed from the store.");
            } else {
                System.out.println("Book not found.");
            }
        }
    
        public ArrayList<Book> getBooks() {
            return books;
        }
    
        public void displayBooks() {
            System.out.println("Books available in the store: ");
        }

    @Override
    public void start(Stage primaryStage)
    {
        loadData();
        Button btn = new Button();
        Button btnLogin=new Button("Login");
        TextField txtUsername=new TextField();
        TextField txtPassword=new TextField();
        Label lblUsername=new Label("Username: ");
        Label lblPassword=new Label("Password");
        GridPane paneLogin=new GridPane();
        //paneLogin.getChildren().addAll(lblUserName,txtUserName,lblPassword,txtPassword,btnLogin);
        paneLogin.add(lblUsername, 0, 0,1,1);
        paneLogin.add(txtUsername,1,0,1,1);
        paneLogin.add(lblPassword,0,1,1,1);
        paneLogin.add(txtPassword,1,1,1,1);
        paneLogin.add(btnLogin,0,2,1,1);
        
        Scene sceneS = new Scene(btn,200,200);
        
        Scene scene = new Scene(paneLogin, 1000, 600);
        
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        
        btn.setText("Say 'Hello World'");
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event)
            {
                String userGuess=txtUsername.getText();
                String passwordGuess=txtPassword.getText();
                for(int k=0;k<customers.size();k++)
                {
                    Customer check=customers.get(k);
                    //System.out.println(check.getUsername()+","+check.getPassword());
                    String passCheck=check.getPassword();
                    
                    String userCheck=check.getUsername();
                    if((userGuess.equals(userCheck)) && passwordGuess.equals(passCheck))
                    {
                        System.out.println("Successful login");
                        primaryStage.setScene(new Scene(customerHomeGridPane(k), 1000, 600));
                        
                    }
                }
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent e)
            {
                saveData();
            }
        });
            
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        
        primaryStage.show();
    }
    
    public VBox customerHomeGridPane(int customerIndex) {
        VBox customerHomeVB = new VBox(); //Uses Vbox layout pane for customer start page
        
        customerHomeVB.setPadding(new Insets(20,0,0,0)); //Spacing of welcome message from top window
        
        //Top part of customer start screen
        Customer currentCustomer = customers.get(customerIndex);
        Label customerGreetLbl = new Label("Welcome " + currentCustomer.getName() + ". You have " + currentCustomer.getPoints() + " points. Your status is " + currentCustomer.getState(currentCustomer.getPoints()) + ".");
        customerHomeVB.setAlignment(Pos.TOP_CENTER); //Alligns to be top center
        
        TableView<Book> table = new TableView<>(); //Creates table
        ObservableList<Book> bookData = FXCollections.observableArrayList();
        
        //Iterates through all book objects in arraylist and adds to the ObservableList
        for (int i = 0; i < books.size(); i++) {
            bookData.add(books.get(i));
        }
        
        
        //Book name column
        TableColumn<Book,String> bookNameCol = new TableColumn<>("Book Name");
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookNameCol.setMinWidth(200);
        
        //Book price column
        TableColumn<Book,Double> bookPriceCol = new TableColumn<>("Book Price");
        bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPriceCol.setMinWidth(100);
        
        //Check box column
        TableColumn<Book,Boolean> selectionCol = new TableColumn<>("Select");
        selectionCol.setCellValueFactory(cellData -> cellData.getValue().getSelectedBook()); //Links checkbox to the selected object instance of books in book arraylist
        selectionCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectionCol));
        selectionCol.setMinWidth(100);
        
        table.setEditable(true); //Allows checkboxes to be ticked or unticked
        
        table.getColumns().addAll(bookNameCol, bookPriceCol, selectionCol);
        table.setItems(bookData);
        
        //Bottom buttons
        Button buyBtn = new Button("Buy"); 
        Button pointsBuyBtn = new Button("Redeem Points and Buy");
        Button logoutBtn = new Button("Logout");
        
        buyBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle (ActionEvent event) {
                Stage stage = (Stage) buyBtn.getScene().getWindow();
                stage.setScene(new Scene(customerCostScreen(customerIndex, false), 1000, 600));
            }
        });
        
        pointsBuyBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle (ActionEvent event) {
                Stage stage = (Stage) pointsBuyBtn.getScene().getWindow();
                stage.setScene(new Scene(customerCostScreen(customerIndex,true), 1000, 600));
            }
        });
        
        //Creates button with HBox layout pane
        HBox buttonBox = new HBox(buyBtn, pointsBuyBtn, logoutBtn);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER); //Places buttons to always be at the bottom of screen
        buttonBox.setSpacing(100); //Distances buttons
        buttonBox.setPadding(new Insets(0,0,20,0)); //Adds spacing from bottom
        
        
        VBox.setMargin(table, new Insets(20, 0, 20, 0));
        
        customerHomeVB.getChildren().addAll(customerGreetLbl, table, buttonBox);
        
        return customerHomeVB;
    }
    
    public VBox customerCostScreen(int customerIndex, boolean redeemPoints) {
        VBox costScreenVB = new VBox(); //Uses Vbox layout pane for customer cost page
        costScreenVB.setAlignment(Pos.CENTER);
        costScreenVB.setPadding(new Insets(20)); //Equal 20 space on all sides
        costScreenVB.setSpacing(30); //Spacing between GUI elements
        
        
        Customer currentCustomer = customers.get(customerIndex); //Refers to specific arraylist customer element
        ArrayList<Book> selectedBooks = new ArrayList<>();
        double totalCost = 0.0; //Total cost on books
        
        //Iterates through books arraylist
        for (Book book: books) {
            if (book.isSelectedChecker()) { //Sums total cost of books that are selected with the checkboxes
                selectedBooks.add(book);
                totalCost += book.getPrice();
                
            }
        }
        
        if (redeemPoints == true) {
            
            int pointsToRedeem = Math.min(currentCustomer.getPoints(), (int)(totalCost * 100));
            double discount = pointsToRedeem / 100;
            
            totalCost = Math.max(totalCost - discount, 0);
            
            currentCustomer.redeemPoints("Purchase", pointsToRedeem);
            
        }
        
        currentCustomer.buyBook("Purchase", totalCost);
        
        Label totalCostLbl = new Label("Total Cost: $" + String.format("%.2f", totalCost));
        totalCostLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); //Increases font size and makes it bold
        totalCostLbl.setAlignment(Pos.CENTER);
        
        Label pointsStatusLbl = new Label("Points: " + currentCustomer.getPoints() + ", Status: " + currentCustomer.getState(currentCustomer.getPoints()));
        pointsStatusLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        pointsStatusLbl.setAlignment(Pos.CENTER);
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-font-size: 16px;");
        //ADD CODE FOR LOGOUT BUTTON ACTION HERE!
        
        costScreenVB.getChildren().setAll(totalCostLbl, pointsStatusLbl, logoutBtn);
        return costScreenVB;
    }
    
    
 public void loadData()
    {
        int bookCounter=0;
            int i=0;
            int j=0;
            double price=0;
            int stock=0;
            int points=0;
            System.out.println("loadData() test");
            try {
                
                FileReader bookRecord=new FileReader("books.txt");
                Scanner scan=new Scanner(bookRecord);
                while(scan.hasNextLine())
                {
                    i=0;
                    bookCounter++;
                    String bookLine=scan.nextLine();
                    Scanner bookToken=new Scanner(bookLine);
                    bookToken.useDelimiter(",");
                    String properties[]=new String[5];
                    //Each line is name,price,author,ISBN,stock
                    //Means you always know what value is which 
                    //Make sure to avoid tokenizing ISBN number as integer though or double
                    while(bookToken.hasNext())
                    {
                        if(bookToken.hasNextInt() && i==4) //The way the tokenizer is set up will always ensure that the index of 3 will be an 
                        {
                            stock=bookToken.nextInt();
                        }
                        else if(bookToken.hasNextDouble()&& i==1)
                        {
                            price=bookToken.nextDouble();
                        }
                        else
                        {
                            properties[i]=bookToken.next();
                        }
                        i++;
                    }
                    
                    Book bookAdd=new Book(properties[0],price,properties[2],properties[3],stock);
                    System.out.println(properties[0]+","+price+","+properties[2]+","+properties[3]+","+stock);
                    books.add(bookAdd);
                    stock=0;
                    price=0;
                }
                bookRecord.close();
                //Completed acquiry of bookstore data
                FileReader customerRecord=new FileReader("customers.txt");
                Scanner scanC=new Scanner(customerRecord);
                while(scanC.hasNextLine())
                {
                    j=0;
                    String customerLine=scanC.nextLine();
                    Scanner customerToken=new Scanner(customerLine);
                    customerToken.useDelimiter(",");
                    String customerProperties[]=new String[4];
                    while(customerToken.hasNext())
                    {
                        if(customerToken.hasNextInt() && j==3)
                        {
                            points=customerToken.nextInt();
                        }
                        else
                        {
                            customerProperties[j]=customerToken.next();
                        }
                        j++;
                    }
                    Customer customerAdd=new Customer(customerProperties[0],customerProperties[1],customerProperties[2],points);
                    System.out.println(customerProperties[0]+","+customerProperties[1]+","+customerProperties[2]+","+points);
                    customers.add(customerAdd);
                    points=0;
                }
                customerRecord.close();
                
                
            } catch (IOException e)
            {
                
            }
    }
    public void saveData()
    {
        try{
        FileWriter bookFile=new FileWriter("books.txt");
        for(int i=0;i<books.size();i++)
        {
            Book bookWrite=books.get(i);
            bookFile.write(bookWrite.getName()+","+bookWrite.getPrice()+","+bookWrite.getAuthor()+","+bookWrite.getISBN()+","+bookWrite.getStock()+"\n");
        }
        bookFile.close();
        FileWriter customerFile=new FileWriter("customers.txt");
        for(int j=0;j<customers.size();j++)
        {
            Customer customerWrite=customers.get(j);
            customerFile.write(customerWrite.getName()+","+customerWrite.getUsername()+","+customerWrite.getPassword()+","+customerWrite.getPoints()+"\n");
        }
        
        customerFile.close();
        }
        catch(IOException e)
        {
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
