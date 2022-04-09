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
	
	public static void main(String[] args){
		Staff staff = new Staff ("Kirk", 1234);
		staffs.add(staff);
        launch(args);
    }
	
    @Override
    public void start(Stage stage) {
		logInPage();
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
		grid.getChildren().add(loginButton);
		
		//adding action lister
		loginButton.setOnAction (e -> {
			if(verifyLogin (nameInput.getText(), Integer.valueOf(passInput.getText()))){
				LoggedIn();
			}
		});
		
		GridPane.setConstraints(loginButton, 1, 2); //first column, third row
		
		//add sign up label 
		Label signup = new Label ("Don't have an account yet?");
		GridPane.setConstraints(signup, 0, 3);
		grid.getChildren().add(signup);
		
		//add sign up button to change to sign up page
		Button signupButton = new Button ("Sign up");
		GridPane.setConstraints(signupButton, 1, 3);
		signupButton.setOnAction (e -> studentOrStaff());
		grid.getChildren().add(signupButton);
		
		//add layout to scene and window
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
	//maybe add re-enter password and logic?
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
		Button signupButton = new Button ("Signup");
		
		//adding action lister
		signupButton.setOnAction (e -> {
			
			if (!userExists(nameInput.getText())){
				signUpStaff(nameInput.getText(), Integer.valueOf(passInput.getText()));
				window.close();
			} else {
				System.out.println("User already exists");
			}
		});
		
		GridPane.setConstraints(signupButton, 1, 2); //first column, third row
		
		//add button 
		grid.getChildren().add(signupButton);
		Scene scene = new Scene (grid, 300, 200);
		window.setScene (scene);
		window.show();
	}
	
	//sign up helper mathods
	public boolean userExists (String username){ //username is unique
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
	
	//student or staff page 
	public void studentOrStaff(){
		//create new stage 
		Stage window = new Stage ();
		
		//create layout
		GridPane grid = new GridPane ();
		grid.setPadding (new Insets(10, 10, 10, 10)); 
		grid.setVgap(8);
		grid.setHgap(10);
		
		//Label text "are you student or staff?"
		Label question = new Label ("Are you a student or staff");
		GridPane.setConstraints(question, 0, 0);
		grid.getChildren().add(question);
		
		//could maybe do radio/checkbox here
		Button student = new Button ("Student");
		Button staff = new Button ("Staff");
		
		//positioning
		GridPane.setConstraints(student, 0, 1);
		GridPane.setConstraints(staff, 1, 1);
		
		//add button
		grid.getChildren().addAll(student, staff);
		
		//add action listeners
		staff.setOnAction (e -> {
			signUpPage();
			window.close();
		});
		
		Scene scene = new Scene (grid, 200, 300);
		window.setScene (scene);
		window.show();
		
	}
	//sign up staff
	public void signUpStaff(String username, int password){
		Staff staff = new Staff (username, password);
		staffs.add(staff);
	}
	
	//sign up students
	public void signUpStudent(String username, int password){
		Student student = new Student (username, password);
		students.add(student);
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
	
	public void staffLogInPage(){
		
	}
}



