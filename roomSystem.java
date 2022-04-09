import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.*;
class Room {
	private boolean booked;
	private boolean viewable;
	public double price; //per night (fixed)
	public String capacity; //single, double, triple
	public String name; 
	
	public Room (String name, String capacity, double price){
		booked = false;
		viewable = false; 
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		
	}
	
	public void launchRoom() {
		
	}
	
	@Override
	public String toString (){
		return String.format("Room name: %s%nCapacity: %s%nPrice: %.2f%n", name, capacity, price);
	}
}

class Student {
	//private final int id;
	private int password;
	private String name;
	ArrayList <Room> bookedRooms;
	private final String role;
	
	public Student (String name, int password){
		bookedRooms = new ArrayList <Room>();
		this.name = name;
		this.password = password;
		this.role = "Student";
	}
	
	public String getName (){
		return name;
	}
	
	public int getPassword(){
		return password;
	}
}

class Staff {
	//private final int id;
	private int password;
	private String name;
	private final String role;
	
	public Staff (String name, int password) {
		this.name = name;
		this.password = password;
		this.role = "Staff";
	}
	
	public String getName (){
		return name;
	}
	
	public int getPassword(){
		return password;
	}
}

public class roomSystem extends Application {
	public static ArrayList <Staff> staffs = new ArrayList <Staff>(); 
	public static ArrayList <Student> students = new ArrayList <Student>();

    @Override
    public void start(Stage stage) {
		//logInPage(stage);
        
		/*Button b = new Button ("Test button");
        Label l = new Label("Welcome to UOW accomodations");
		VBox sp = new VBox ();
		sp.getChildren().add(b);
		sp.getChildren().add(l);
		b.setOnAction(e -> LoggedIn());
			
			
        Scene scene = new Scene(sp, 640, 480);

        stage.setScene(scene);
        stage.show();*/
		
		logInPage();
    }

    public static void main(String[] args) {
		Staff staff = new Staff ("Kirk", 1234);
		staffs.add(staff);
        launch(args);
    }
	
	//default login page
	public void logInPage (){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Login Page");
		
		//create GridPane layout 
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners, don't ask me what's insets
		grid.setVgap(8);
		grid.setHgap(10);
		
		//labels
		Label nameLabel = new Label ("Username");
		GridPane.setConstraints(nameLabel, 0, 0); //first column, first row
		
		Label passLabel = new Label ("Password");
		GridPane.setConstraints(passLabel, 0, 1); //first column, second row
		
		//textfields
		TextField nameInput = new TextField ();
		GridPane.setConstraints(nameInput, 1, 0); //second column, first row
		
		TextField passInput = new TextField ();
		GridPane.setConstraints(passInput, 1, 1);
		
		//add nodes to layout
		grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput);
		
		//add login button
		Button loginButton = new Button ("Login");
		
		//adding action lister
		loginButton.setOnAction (e -> {
			verifyLogin (nameInput.getText(), Integer.valueOf(passInput.getText()));
		});
		
		GridPane.setConstraints(loginButton, 1, 2); //first column, third row
		
		//add sign up label and button
		Label signup = new Label ("Don't have an account yet?");
		GridPane.setConstraints(signup, 0, 3);
		grid.getChildren().add(signup);
		
		Button signupButton = new Button ("Sign up");
		GridPane.setConstraints(signupButton, 1, 3);
		signupButton.setOnAction (e -> signUpPage());
		grid.getChildren().add(signupButton);
		
		
		
		//add button 
		grid.getChildren().add(loginButton);
		Scene scene = new Scene (grid, 300, 200);
		window.setScene (scene);
		window.show();
		
		
	}
	
	//verify login (check if user exist and password is correct)
	public boolean verifyLogin (String userID, int password){
		for (Staff s : staffs){
			if (s.getName().equals(userID)){
				if (s.getPassword() == password){
					System.out.println("Authenticated");
					return true;
				}
			}
		}
		return false;
		
	}
	
	//sign up page 
	//add re-enter password and logic
	public void signUpPage (){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Signup Page");
		
		//create GridPane layout 
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners, don't ask me what's insets
		grid.setVgap(8);
		grid.setHgap(10);
		
		//labels
		Label nameLabel = new Label ("Username");
		GridPane.setConstraints(nameLabel, 0, 0); //first column, first row
		
		Label passLabel = new Label ("Password");
		GridPane.setConstraints(passLabel, 0, 1); //first column, second row
		
		//textfields
		TextField nameInput = new TextField ();
		GridPane.setConstraints(nameInput, 1, 0); //second column, first row
		
		TextField passInput = new TextField ();
		GridPane.setConstraints(passInput, 1, 1);
		
		//add nodes to layout
		grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput);
		
		//add login button
		Button loginButton = new Button ("Signup");
		
		//adding action lister
		loginButton.setOnAction (e -> {
			String userName = nameInput.getText();
			int password = Integer.valueOf(passInput.getText());
			
			
			
		});
		
		GridPane.setConstraints(loginButton, 1, 2); //first column, third row
		
		//add button 
		grid.getChildren().add(loginButton);
		Scene scene = new Scene (grid, 300, 200);
		window.setScene (scene);
		window.show();
	}
	
	//sign up helper mathods
	public boolean userExists (String username){
		for (Staff s : staffs){
			if (s.getName().equals(username)){
				return true;
			}
		}
		for (Student s : students){
			if (s.getName().equals(username)){
				return true;
			}
		}
		return false;
	}
	
	public void signUpStaff(String username, int password){
		Staff staff = new Staff (username, password);
		staffs.add(staff);
	}
	
	public void LoggedIn () {
		Stage window = new Stage ();
		window.setTitle("You have logged in");
		
		Label label = new Label ("Welcome to UOW accomodations");
		StackPane sp = new StackPane ();
		
		sp.getChildren().add(label);
		Scene scene = new Scene (sp, 640, 480);
		window.setScene (scene);
		window.show();
	}
}

