import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.geometry.HPos;
import java.time.LocalDate;
import java.util.Locale;
import javafx.util.Callback;
import java.io.*;
import java.nio.file.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class


class Student implements Serializable{
	private String password; //1234
	private String name; //userid Kirk 
	private ArrayList <Booking> bookings;
	private final String role;
	private boolean isSuspended;
	private LocalDateTime login;
	private LocalDateTime logout;
	
	public Student (String name, String password){
		this.name = name;
		this.password = password;
		this.role = "Student"; 
		this.bookings = new ArrayList <Booking> ();
		this.isSuspended = false;
		this.login = LocalDateTime.now();
		this.logout = LocalDateTime.now();
	}
	
	public String getName (){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getRole (){
		return role;
	}
	
	public ArrayList<Booking> getBookings (){
		return bookings;
	}
	
	public void addBookings(Booking b){
		bookings.add(b);
	}
	
	public boolean getIsSuspended(){
		return isSuspended;
	}
	
	public void setIsSuspended(boolean isSuspended){
		this.isSuspended = isSuspended;
	}
	
	public String getLogin(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return login.format(myFormatObj);
	}
	
	public void setLogin(LocalDateTime login){
		this.login = login;
	}
	
	public String getLogout(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return logout.format(myFormatObj);
	}
	
	public void setLogout(LocalDateTime logout){
		this.logout = logout;
	}
}

class Staff implements Serializable{
	private String password;
	private String name;
	private final String role;
	private boolean isSuspended;
	private LocalDateTime login;
	private LocalDateTime logout;
	
	public Staff (){
		this.password = "";
		this.name = "";
		this.role = "Staff";
		this.isSuspended = false;
		this.login = LocalDateTime.now();
		this.logout = LocalDateTime.now();
	}
	
	public Staff (String name, String password) {
		this.name = name;
		this.password = password;
		this.role = "Staff";
		this.isSuspended = false;
		this.login = LocalDateTime.now();
		this.logout = LocalDateTime.now();
	}
	
	public String getName (){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getRole (){
		return role;
	}
	
	public boolean getIsSuspended(){
		return isSuspended;
	}
	
	public void setIsSuspended(boolean isSuspended){
		this.isSuspended = isSuspended;
	}
	
	public String getLogin(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return login.format(myFormatObj);
	}
	
	public void setLogin(LocalDateTime login){
		this.login = login;
	}
	
	public String getLogout(){
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return logout.format(myFormatObj);
	}
	
	public void setLogout(LocalDateTime logout){
		this.logout = logout;
	}
}

class Booking implements Serializable{
	private Student student;
	private Room room;
	private String roomName; 
	private LocalDate checkInDate;
	private LocalDate checkOutDate; 
	private boolean promoUsed; //check if promo is used
	
	public Booking (Student student, Room room, LocalDate checkInDate, LocalDate checkOutDate){
		this.student = student;
		this.room = room;
		this.roomName = room.getName();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate; 
	}
	
	public Student getStudent(){
		return student;
	}
	
	public void setStudent (Student student){
		this.student = student;
	}
	
	public Room getRoom (){
		return room;
	}
	
	public void setRoom(Room room){
		this.room = room;
	}
	
	public String getRoomName(){
		return roomName;
	}
	
	public void setRoomName(String roomName){
		this.roomName = roomName;
	}
	
	public LocalDate getCheckInDate(){
		return checkInDate;
	}
	
	public LocalDate getCheckOutDate(){
		return checkOutDate;
	}
	
	public void setCheckInDate(LocalDate checkInDate){
		this.checkInDate = checkInDate;
	}
	
	public void setCheckOutDate(LocalDate checkOutDate){
		this.checkOutDate = checkOutDate; 
	}
	
	public boolean getPromoUsed(){
		return promoUsed;
	}
	
	public void setPromoUsed(boolean promoUsed){
		this.promoUsed = promoUsed;
	}
}

public class roomSystem extends Application {
	//arraylist for staff,student and room
	public static ArrayList <Staff> staffs = new ArrayList <Staff>(); 
	public static ArrayList <Student> students = new ArrayList <Student>();
	public static ArrayList <Room> rooms = new ArrayList<Room> ();
	
	//instance variables for file processing
	private static ObjectInputStream studentInput;
	private static ObjectInputStream staffInput;
	private static ObjectInputStream roomInput;
	private static ObjectOutputStream studentOutput;
	private static ObjectOutputStream staffOutput;
	private static ObjectOutputStream roomOutput;
	private static Formatter output;
	
	//TableView, show rooms window
	public static TableView<Room> table;
	public static TableView<Booking> bTable;
	
	//current profile
	private static Student currentStudent;
	private static Staff currentStaff;
	
	//test variable for show reservation
	public static int counter;
	
	public static void main(String[] args){
		//saveSession();
		readSession();
        launch(args);
    }
	
    @Override
    public void start(Stage stage) {
		//superUserLogInPage();
		logInPage();
		//viewStaffAccount();
    }
	
	//default login page
	public void logInPage (){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Login Page");
		
		//create GridPane layout 
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 75)); //top, right, bottom, left
		grid.setVgap(8);
		grid.setHgap(10);
		
		//Vbox layout to bandaid 
		VBox vbox = new VBox();
		
		//image 
		Image image = new Image("UOWLogo.png");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(150);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		StackPane sp = new StackPane();
		sp.getChildren().add(imageView);
		vbox.getChildren().add(sp);
		
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
			if (nameInput.getText().trim().equals("superUser") && passInput.getText().trim().equals("password")){
				superUserLogInPage();
				window.close();
			} else if(verifyLogin (nameInput.getText().trim(), passInput.getText().trim())){
				window.close();
			} else {
				System.out.println("Username or password is incorrect");
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
		vbox.getChildren().add(grid);
		//set save on close
		window.setOnCloseRequest(e -> {
			saveSession();
			System.out.println("Session saved");
		});
		
		//add layout to scene and window
		Scene scene = new Scene (vbox, 450, 310);
		window.setScene (scene);
		window.show();
	}
	
	//verify login (check if user exist and password is correct)
	public boolean verifyLogin (String userID, String password){
		for (Staff s : staffs){
			if (s.getName().equals(userID)){
				if (s.getPassword().equals(password)){
					setStaffSession(s);
					staffLogInPage();
					return true;
				}
			}
		}
		
		for (Student s : students){
			if (s.getName().equals(userID)){
				if (s.getPassword().equals(password)){
					setStudentSession(s);
					studentLogInPage();
					return true;
				}
			}
		}
		return false;
	}
	
	public void setStudentSession (Student s){
		currentStudent = s; 
		currentStudent.setLogin(LocalDateTime.now());
	}
	
	public void setStaffSession (Staff s){
		currentStaff = s; 
		currentStaff.setLogin(LocalDateTime.now());
	}
	
	//sign up page 
	//maybe add re-enter password and logic?
	public void staffSignUpPage (){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Staff Sign Up");
		
		//create GridPane layout 
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
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
		Button signupButton = new Button ("Sign up");
		
		//adding action lister
		signupButton.setOnAction (e -> {
			
			if (!userExists(nameInput.getText().trim()) && nameInput.getText().trim() != "" && passInput.getText().trim()!= ""){ //get unique id
				signUpStaff(nameInput.getText().trim(), passInput.getText().trim());
				window.close();
			} else {
				System.out.println("User already exists");
			}
		});
		
		GridPane.setConstraints(signupButton, 1, 2); //first column, third row
		
		//add button 
		grid.getChildren().add(signupButton);
		Scene scene = new Scene (grid, 300, 150);
		window.setScene (scene);
		window.show();
	}
	
	//student sign up page
	public void studentSignUpPage(){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Student Sign Up");
		
		//create GridPane layout 
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
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
		Button signupButton = new Button ("Sign up");
		
		//adding action lister
		signupButton.setOnAction (e -> {
			
			if (!userExists(nameInput.getText().trim()) && nameInput.getText().trim() != "" && passInput.getText().trim()!= ""){ //get unique id
				signUpStudent(nameInput.getText().trim(), passInput.getText().trim());
				window.close();
			} else {
				System.out.println("Username already exists");
			}
		});
		
		GridPane.setConstraints(signupButton, 1, 2); //first column, third row
		
		//add button 
		grid.getChildren().add(signupButton);
		Scene scene = new Scene (grid, 300, 150);
		window.setScene (scene);
		window.show();
	}
	
	//sign up helper mathods
	public boolean userExists (String username){ //username is unique
		if (username.equals("superUser")){
			return true; 
		}
		
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
		Label question = new Label ("	    Are you a student or staff?");
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
			staffSignUpPage();
			window.close();
		});
		
		student.setOnAction(e ->{
			studentSignUpPage();
			window.close();
		});
		
		Scene scene = new Scene (grid, 260, 100);
		window.setScene (scene);
		window.show();
		
	}
	//sign up staff
	public void signUpStaff(String username, String password){
		Staff staff = new Staff (username, password);
		staffs.add(staff);
	}
	
	//sign up students
	public void signUpStudent(String username, String password){
		Student student = new Student (username, password);
		students.add(student);
	}
	
	public void staffLogInPage(){
		if (currentStaff.getIsSuspended()){
			Stage window = new Stage();
			window.setTitle("Suspended");
			Label label = new Label ("Your account has been suspended.");
			Label label2 = new Label ("Please contact the admin.");
			
			GridPane grid = new GridPane();
			grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
			grid.setVgap(8);
			grid.setHgap(10);
			
			grid.add(label, 0, 0);
			grid.add(label2, 0, 1);
			
			Scene scene = new Scene(grid, 300, 200);
			
			window.setScene(scene);
			window.show();
		} else {
			Stage window = new Stage ();
			window.setTitle("Staff Login Page");
			
			//uow logo
			Image image = new Image("UOWLogo.png");
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(150);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			
			StackPane sp = new StackPane();
			sp.getChildren().add(imageView);
			
			VBox vbox = new VBox();
			vbox.getChildren().add(sp);
			
			GridPane grid = new GridPane();
			grid.setPadding (new Insets(10, 10, 10, 75)); //top, right, bottom, left
			grid.setVgap(8);
			grid.setHgap(10);
			
			//room image
			Image roomImage = new Image("room.png");
			ImageView iv = new ImageView();
			iv.setImage(roomImage);
			iv.setFitWidth(50);
			iv.setPreserveRatio(true);
			
			//show all rooms button
			Button b = new Button ("Show all rooms", iv);
			b.setOnAction(e -> showAllRooms());
			b.setMinHeight(150);
			b.setMinWidth(150);
			grid.add(b, 0, 0);
			
			//create room image
			Image createRoomImage = new Image("roomcreate.png");
			ImageView iv2 = new ImageView();
			iv2.setImage(createRoomImage);
			iv2.setFitWidth(50);
			iv2.setPreserveRatio(true);
			
			//create room button
			Button createRoom = new Button ("Create Room", iv2);
			createRoom.setOnAction(e -> createRoom());
			createRoom.setMinHeight(150);
			createRoom.setMinWidth(150);
			grid.add(createRoom, 1, 0);
			
			//log out button
			Button logout = new Button("Logout");
			logout.setOnAction(e -> {
				currentStaff.setLogout(LocalDateTime.now());
				currentStaff = null;
				window.close();
				logInPage();
			});
			logout.setMinHeight(150);
			logout.setMinWidth(150);
			
			grid.add(logout, 2, 0);
			
			StackPane sp2 = new StackPane();
			sp2.getChildren().add(grid);
			vbox.getChildren().add(sp2);
			Scene scene = new Scene (vbox, 640, 400);
			window.setScene (scene);
			window.show();
		}
	}
	
	//Staff window to create room
	public void createRoom (){
		Stage window = new Stage ();
		window.setTitle("Create new room");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
        grid.setVgap(10);
		grid.setPadding (new Insets(10, 10, 10, 10));
		
		Label roomName = new Label ("Room Name:");
		TextField nameInput = new TextField();
		grid.add(roomName, 0, 0);
		grid.add(nameInput, 1, 0);
		
		Label roomPrice = new Label ("Room Price: ");
		TextField priceInput = new TextField();
		grid.add(roomPrice, 0, 1);
		grid.add(priceInput, 1, 1);
		
		Label capacity = new Label ("Room Capacity:");
		TextField capInput = new TextField();
		grid.add(capacity, 0, 2);
		grid.add(capInput, 1, 2);
		
		Label promoCode = new Label ("Promo Code:");
		TextField promoInput = new TextField();
		grid.add(promoCode, 0, 3);
		grid.add(promoInput, 1, 3);
		
		Label availFrom = new Label("Available From:");
		DatePicker dateInput = new DatePicker();
		grid.add(availFrom, 0, 4);
		grid.add(dateInput, 1, 4);
		
		Label viewable = new Label("Viewable:");
		TextField viewInput = new TextField();
		viewInput.setPromptText(" 'yes' or 'no'");
		grid.add(viewable, 0, 5);
		grid.add(viewInput, 1, 5);
		
		Button createRoom = new Button ("Create");
		createRoom.setOnAction(e->{
			Room r = new Room(nameInput.getText(), capInput.getText(), Double.valueOf(priceInput.getText().trim()),
					promoInput.getText().trim(), dateInput.getValue(), viewInput.getText().trim().toUpperCase().equals("YES")? true : false);
			rooms.add(r);
			System.out.println(r.getViewable());
			window.close();
		});
		grid.add(createRoom, 0, 6);
		
		Scene scene = new Scene (grid, 300, 300);
		window.setScene(scene);
		window.show();
		
	}
	
	
	//Staff room modification details
	public static void modifyButtonClicked(){
		ObservableList<Room> availableRooms;
		Room roomSelected;
		
		availableRooms = table.getItems();
		roomSelected = table.getSelectionModel().getSelectedItem();
		modifyRoomDetails(roomSelected);
	}
	
	public static void modifyRoomDetails(Room r){
		Stage window = new Stage ();
		window.setTitle("Modify Room Details");
		
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
		grid.setVgap(8);
		grid.setHgap(10);
		
		//labels
		Label roomName = new Label ("Room Name: ");
		GridPane.setConstraints(roomName, 0, 0); //first column, first row
		Label nameDetail = new Label (r.getName());
		GridPane.setConstraints(nameDetail, 1, 0); //second column, first row
		TextField newName = new TextField();
		newName.setPromptText("Enter new name");
		GridPane.setConstraints(newName, 2, 0);
		
		Label roomPrice = new Label ("Price per night: ");
		GridPane.setConstraints(roomPrice, 0, 1); //first column, 2nd row
		Label priceDetail = new Label ("$" + r.getPrice());
		GridPane.setConstraints(priceDetail, 1, 1); //second column, first row
		TextField newPrice = new TextField();
		newPrice.setPromptText("Enter new price");
		GridPane.setConstraints(newPrice, 2, 1);
		
		Label roomCapacity = new Label ("Room capacity: ");
		GridPane.setConstraints(roomCapacity, 0, 2); //first column, 3rd row
		Label capacityDetail = new Label (r.getCapacity());
		GridPane.setConstraints(capacityDetail, 1, 2); //second column, first row
		TextField newCapacity = new TextField();
		newCapacity.setPromptText("Enter new capacity");
		GridPane.setConstraints(newCapacity, 2, 2);
	
		
		Label availableFrom = new Label ("Available after: ");
		GridPane.setConstraints(availableFrom, 0, 3); //first column, 4rd row
		Label dateValue = new Label (r.getAvailableOn().toString());
		GridPane.setConstraints(dateValue, 1, 3); //2nd column, 4rd row
		DatePicker newDate = new DatePicker();
		GridPane.setConstraints(newDate, 2, 3);
		
		Label promo = new Label ("Promo code: ");
		GridPane.setConstraints(promo, 0, 4);
		Label promoDetail = new Label(r.getPromo());
		GridPane.setConstraints(promoDetail, 1, 4);
		TextField newPromo = new TextField();
		newPromo.setPromptText("Enter new promo");
		GridPane.setConstraints(newPromo, 2, 4);
		
		Label viewable = new Label ("Viewable: ");
		GridPane.setConstraints(viewable, 0, 5);
		ChoiceBox<Boolean> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().add(true);
		choiceBox.getItems().add(false);
		choiceBox.setValue(r.getViewable());
		GridPane.setConstraints(choiceBox, 1, 5);
		
		Button change = new Button("Change");
		change.setOnAction(e -> {
			if (!newName.getText().trim().equals("")){
				r.setName(newName.getText().trim());
			}
			if (!newPrice.getText().trim().equals("")){
				r.setPrice(Double.valueOf(newPrice.getText().trim()));
			}
			if (!newCapacity.getText().trim().equals("")){
				r.setCapacity(newCapacity.getText().trim());
			}
			if (newDate.getValue() != null){
				r.setAvailableOn(newDate.getValue());
			}
			if (!newPromo.getText().trim().equals("")){
				r.setPromo(newPromo.getText().trim());
			} 
			
			r.setViewable(choiceBox.getValue());
		});
		GridPane.setConstraints(change, 0, 6);
		
		grid.getChildren().addAll(roomName, nameDetail, roomPrice, priceDetail, roomCapacity, capacityDetail);
		grid.getChildren().addAll(availableFrom, dateValue, newName, newPrice, newCapacity, newDate, viewable, choiceBox);
		grid.getChildren().addAll (promo, promoDetail, newPromo, change);
		
		Scene scene = new Scene (grid, 400, 300);
		window.setScene(scene);
		window.show();
	}
	public void studentLogInPage(){
		if (currentStudent.getIsSuspended()){
			Stage window = new Stage();
			window.setTitle("Suspended");
			Label label = new Label ("Your account has been suspended.");
			Label label2 = new Label ("Please contact the admin.");
			
			GridPane grid = new GridPane();
			grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
			grid.setVgap(8);
			grid.setHgap(10);
			
			grid.add(label, 0, 0);
			grid.add(label2, 0, 1);
			
			Scene scene = new Scene(grid, 300, 200);
			
			window.setScene(scene);
			window.show();
		} else {
			//create stage
			Stage window = new Stage ();
			window.setTitle("(Student) You have logged in");
			
			//uow logo
			Image image = new Image("UOWLogo.png");
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(150);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			
			//add image to stackpane
			StackPane sp = new StackPane();
			sp.getChildren().add(imageView);
			
			//Vbox main layout
			VBox vbox = new VBox ();
			vbox.getChildren().add(sp);
			
			//hbox below stackpane image
			HBox hbox = new HBox(8);
			hbox.setPadding (new Insets(10, 10, 10, 75)); //top, right, bottom, left
			
			//room image
			Image roomImage = new Image("room.png");
			ImageView iv = new ImageView();
			iv.setImage(roomImage);
			iv.setFitWidth(50);
			iv.setPreserveRatio(true);
			
			//Book room button
			Button bookRoom = new Button("Book Room", iv);
			bookRoom.setOnAction(e -> showAvailableRooms());
			bookRoom.setMinHeight(150);
			bookRoom.setMinWidth(150);
			hbox.getChildren().add(bookRoom);
			
			//list image
			Image listImage = new Image("list.png");
			ImageView iv2 = new ImageView();
			iv2.setImage(listImage);
			iv2.setFitWidth(50);
			iv2.setPreserveRatio(true);
			
			//show reservation button
			Button showRooms = new Button("Show reservations", iv2);
			showRooms.setOnAction (e -> showBookedRooms());
			showRooms.setMinHeight(150);
			showRooms.setMinWidth(150);
			hbox.getChildren().add(showRooms);
			
			//log out button
			Button logout = new Button("Logout");
			logout.setOnAction(e -> {
				currentStudent.setLogout(LocalDateTime.now());
				currentStudent = null;
				window.close();
				logInPage();
			});
			logout.setMinHeight(150);
			logout.setMinWidth(150);
			hbox.getChildren().add(logout);
			
			//add hbox to vbox
			vbox.getChildren().add(hbox);
			
			Scene scene = new Scene (vbox, 640, 400);
			window.setScene (scene);
			window.show();
		}
	}	
	
	//student to view his own reservations
	@SuppressWarnings("unchecked")
	public void showBookedRooms(){
		if (currentStudent.getBookings().size() == 0){
			Stage window = new Stage();
			VBox vBox = new VBox();
			Label noReservation = new Label("You have no reservations currently");
			vBox.getChildren().add(noReservation);
			vBox.setPadding (new Insets(10, 10, 10, 10));
			Scene scene = new Scene (vBox, 300, 200);
			window.setScene(scene);
			window.show();
		} else {
			//counter for resrvations
			counter = 0;
			final int max = currentStudent.getBookings().size() - 1;
			
			Stage window = new Stage ();
			window.setTitle("Show booked rooms");
			
			Label reservationNo = new Label ("Reservation " + String.valueOf(counter + 1));
			
			Label name = new Label ("Name: ");
			Label nameValue = new Label(currentStudent.getBookings().get(counter).getRoom().getName());
			
			Label price = new Label ("Price: ");
			//check if promo used
			double roomPrice = currentStudent.getBookings().get(counter).getPromoUsed() == true? (currentStudent.getBookings().get(counter).getRoom().getPrice() * 0.8) : (currentStudent.getBookings().get(counter).getRoom().getPrice());
			Label priceValue = new Label(String.valueOf(roomPrice));
	
			Label checkInDate = new Label("Check in: ");
			Label checkInValue = new Label(currentStudent.getBookings().get(counter).getCheckInDate().toString());
			
			Label checkOutDate = new Label("Check out: ");
			Label checkOutValue = new Label(currentStudent.getBookings().get(counter).getCheckOutDate().toString());
			
			Label totalPrice =  new Label ("Total price: "); 
			Label tPriceValue = new Label (String.valueOf(roomPrice * (int)currentStudent.getBookings().get(counter).getCheckInDate()
									.until(currentStudent.getBookings().get(counter).getCheckOutDate(), ChronoUnit.DAYS)));
									
									
			//delete button 
			Button delete = new Button ("Delete");
			delete.setOnAction(e -> {
				//remove date from room booking
				LocalDate[] removeDate = new LocalDate[2];
				removeDate[0] = currentStudent.getBookings().get(counter).getCheckInDate();
				removeDate[1] = currentStudent.getBookings().get(counter).getCheckOutDate();
				currentStudent.getBookings().get(counter).getRoom().removeReservedDates(removeDate);
				
				//remove booking from student arraylist
				currentStudent.getBookings().remove(currentStudent.getBookings().get(counter));
				window.close();
			});
			
			//modify button
			Button modify = new Button ("Modify");
			modify.setOnAction(e -> studentModifyButtonClicked(currentStudent.getBookings().get(counter)));
			
			//next reservation button
			Button next = new Button ("Next");
			next.setOnAction(e -> {
				if (counter != max){
					reservationNo.setText("Reservation " + String.valueOf(++counter + 1));
					nameValue.setText(currentStudent.getBookings().get(counter).getRoom().getName());
					double roomsPrice = currentStudent.getBookings().get(counter).getPromoUsed() == true? (currentStudent.getBookings().get(counter).getRoom().getPrice() * 0.8) : (currentStudent.getBookings().get(counter).getRoom().getPrice());
					priceValue.setText(String.valueOf(roomsPrice));
					checkInValue.setText(currentStudent.getBookings().get(counter).getCheckInDate().toString());
					checkOutValue.setText(currentStudent.getBookings().get(counter).getCheckOutDate().toString());
					tPriceValue.setText(String.valueOf(roomsPrice * (int)currentStudent.getBookings().get(counter).getCheckInDate()
									.until(currentStudent.getBookings().get(counter).getCheckOutDate(), ChronoUnit.DAYS)));
				}
			});
			
			//previous reservation button
			Button previous = new Button ("Previous");
			previous.setOnAction(e -> {
				if (counter != 0){
					reservationNo.setText("Reservation " + String.valueOf(--counter + 1));
					nameValue.setText(currentStudent.getBookings().get(counter).getRoom().getName());
					double roomsPrice = currentStudent.getBookings().get(counter).getPromoUsed() == true? (currentStudent.getBookings().get(counter).getRoom().getPrice() * 0.8) : (currentStudent.getBookings().get(counter).getRoom().getPrice());
					priceValue.setText(String.valueOf(roomsPrice));
					checkInValue.setText(currentStudent.getBookings().get(counter).getCheckInDate().toString());
					checkOutValue.setText(currentStudent.getBookings().get(counter).getCheckOutDate().toString());
					tPriceValue.setText(String.valueOf(roomsPrice * (int)currentStudent.getBookings().get(counter).getCheckInDate()
									.until(currentStudent.getBookings().get(counter).getCheckOutDate(), ChronoUnit.DAYS)));
				}
			});
			
			//create layout
			GridPane grid = new GridPane();
			grid.setPadding (new Insets(10, 10, 10, 10));
			grid.setVgap(8);
			grid.setHgap(10);
			
			//add components to layout
			grid.add(reservationNo, 0, 0);
			grid.add(name, 0, 1);
			grid.add(nameValue, 1, 1);
			grid.add(price, 0, 2);
			grid.add(priceValue, 1, 2);
			grid.add(checkInDate, 0, 3);
			grid.add(checkInValue, 1, 3);
			grid.add(checkOutDate, 0, 4);
			grid.add(checkOutValue, 1, 4);
			grid.add(previous, 0, 5);
			grid.add(next, 1, 5);
			grid.add(modify, 0, 6);
			grid.add(delete, 1, 6);
			grid.add(totalPrice, 0, 7);
			grid.add(tPriceValue, 1, 7);
			
			Scene scene = new Scene (grid);
			window.setScene(scene);
			window.show();
		}
	}
	
	//student modify function
	public void studentModifyButtonClicked(Booking b){
		final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(b.getRoom().getAvailableOn())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
							
							for (LocalDate[] array : b.getRoom().getReservedDates()){
								if (item.isBefore(array[1]) && item.isAfter(array[0].minusDays(1))){
									setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
								}
							}
                    }
                };
            }
        };
		Stage window = new Stage ();
		window.setTitle("Student Modify Room Details");
		
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label name = new Label ("Name: ");
		Label nameInput = new Label (b.getRoom().getName());
		
		Label checkIn = new Label ("Check in date: ");
		Label checkInValue = new Label(b.getCheckInDate().toString());
		Label newCheckIn = new Label ("New Check In Date: ");
		DatePicker newCheckInValue = new DatePicker();
		newCheckInValue.setDayCellFactory(dayCellFactory);
		
		Label checkOut = new Label ("Check out date: ");
		Label checkOutValue = new Label(b.getCheckOutDate().toString());
		Label newCheckOut = new Label ("New Check Out Date: ");
		DatePicker newCheckOutValue = new DatePicker();
		newCheckOutValue.setDayCellFactory(dayCellFactory);
		Label prompt = new Label ("");
	
		grid.add(name, 0, 0);
		grid.add(nameInput, 1, 0);
		grid.add(checkIn, 0, 1);
		grid.add(checkInValue, 1, 1);
		grid.add(newCheckIn, 0, 2);
		grid.add(newCheckInValue, 1, 2);
		grid.add(checkOut, 0, 3);
		grid.add(checkOutValue, 1, 3);
		grid.add(newCheckOut, 0, 4);
		grid.add(newCheckOutValue, 1, 4);
		grid.add(prompt, 0, 5);
		
		Button confirm = new Button ("Confirm");
		confirm.setOnAction(e -> {
			if (newCheckOutValue.getValue().isBefore(newCheckInValue.getValue())){
				prompt.setText("Check out cannot be before check in");
			} else {
				LocalDate[] newDates = new LocalDate[2];
				LocalDate[] oldDates = new LocalDate[2];
				//input values to array 
				//new dates
				newDates[0] = newCheckInValue.getValue();
				newDates[1] = newCheckOutValue.getValue();
				//old dates
				oldDates[0] = b.getCheckInDate();
				oldDates[1] =  b.getCheckOutDate();
				System.out.println(oldDates[0] + " " + oldDates[1]);
				
				if (validStay(b.getRoom().getReservedDates(), newDates[0], newDates[1])){
					System.out.println("Valid stay");
					//delete old dates from Room arraylist
					b.getRoom().removeReservedDates(oldDates);
					
					//add new dates to Room reserved dates
					b.getRoom().getReservedDates().add(newDates);
					
					//edit booking
					b.setCheckInDate(newCheckInValue.getValue());
					b.setCheckOutDate(newCheckOutValue.getValue());
				} else {
					//delete the old dates first 
					b.getRoom().removeReservedDates(oldDates);
					
					//if valid after removing the current booking
					if (validStay(b.getRoom().getReservedDates(), newDates[0], newDates[1])){
						System.out.println("Valid stay after deleting");
						//add new dates to Room reserved dates
						b.getRoom().getReservedDates().add(newDates);
						
						//edit booking
						b.setCheckInDate(newCheckInValue.getValue());
						b.setCheckOutDate(newCheckOutValue.getValue());
					} else {
						//add back the current booking
						b.getRoom().getReservedDates().add(oldDates);
						prompt.setText("Not a valid stay");
					}
				}
				
						
				
			}
		});
		grid.add(confirm, 0, 6);
		Scene scene = new Scene (grid, 300, 300);
		window.setScene(scene);
		window.show();
	}
	
	public ObservableList<Booking> getReservations(){
		ObservableList<Booking> bookedRooms = FXCollections.observableArrayList();
		ArrayList<Booking> bookings = new ArrayList<>();
		bookings = currentStudent.getBookings();
		
		for (Booking b : bookings){
			//System.out.printf("Room name: %s%nRoom price: %.2f%nCheck In: %s%nCheck Out: %s%n", b.getRoom().getName(), 
						//	b.getRoom().getPrice(), b.getCheckInDate().toString(), b.getCheckOutDate().toString());
			bookedRooms.add(b);		
		}
		
		return bookedRooms;
	}
	//view rooms window
	public static ObservableList<Room> getAllRooms(){
		ObservableList<Room> allRooms = FXCollections.observableArrayList();
		for (Room r : rooms){
			allRooms.add(r);
		}
		
		return allRooms;
	}
	
	//book room window
	public static void bookRoom(Room r){
		DatePicker checkInDatePicker;
		DatePicker checkOutDatePicker;
		
		//block out unavailable dates
		final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                           
                            if (item.isBefore(r.getAvailableOn())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
							
							for (LocalDate[] array : r.getReservedDates()){
								if (item.isBefore(array[1]) && item.isAfter(array[0].minusDays(1))){
									setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
								}
							}
                    }
                };
            }
        };
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Book Room");
		
		VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        window.setScene(scene);
		
		
		//create date picker for check in and out
        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();		
		
		//block out days that are not reservable
		checkInDatePicker.setDayCellFactory(dayCellFactory);
		checkOutDatePicker.setDayCellFactory(dayCellFactory);
        
		//don't allow user to type
		checkInDatePicker.setEditable(false);
		checkOutDatePicker.setEditable(false);
		
		//create grid layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
		
        Label checkInlabel = new Label("Check-In Date:");
        gridPane.add(checkInlabel, 0, 0);
        GridPane.setHalignment(checkInlabel, HPos.LEFT);
        gridPane.add(checkInDatePicker, 0, 1);
        Label checkOutlabel = new Label("Check-Out Date:");
        gridPane.add(checkOutlabel, 0, 2);
		
        GridPane.setHalignment(checkOutlabel, HPos.LEFT);
        gridPane.add(checkOutDatePicker, 0, 3);
        vbox.getChildren().add(gridPane);
		
		Label promoCode = new Label("Enter Promo Code");
		TextField promoValue = new TextField();
		gridPane.add(promoCode, 0, 4);
		gridPane.add(promoValue, 1, 4);
		
		Label prompt = new Label ("");
		gridPane.add(prompt, 0, 6);
		Button book = new Button ("Book");
		book.setOnAction(e -> {
			//if valid stay
			if (validStay(r.getReservedDates(), checkInDatePicker.getValue(), checkOutDatePicker.getValue())){
				//if user didn't enter promo code
				if (promoValue.getText().trim().equals("")){
					bookButtonClicked(r, checkInDatePicker.getValue(), checkOutDatePicker.getValue(), false);
					System.out.println(validStay(r.getReservedDates(), checkInDatePicker.getValue(), checkOutDatePicker.getValue()));
					window.close();
				} else if (r.getPromo().equals(promoValue.getText().trim())){ //if promo code is true
					System.out.println("Promo code true");
					bookButtonClicked(r, checkInDatePicker.getValue(), checkOutDatePicker.getValue(), true);
					window.close();
				} else if (!r.getPromo().equals(promoValue.getText().trim())){ //promo code incorrect
					prompt.setText("Promo Code Is Incorrect");
				} 
			} else {
				prompt.setText("Not A Valid Stay");
			}
		});
		gridPane.add(book, 0, 5);
		
		window.show();
	}
	
	public static boolean validStay (ArrayList<LocalDate[]> dates, LocalDate checkIn, LocalDate checkOut){
		for (LocalDate date = checkIn; date.isBefore(checkOut); date = date.plusDays(1)){
			for(LocalDate[] a : dates){
				if ((date.isBefore(a[1]) && date.isAfter(a[0].minusDays(1)))){
					return false;
				}
			}
		}
		return true;
	}
	
	public static void bookButtonClicked(Room r, LocalDate checkIn, LocalDate checkOut, boolean promoCodeUsed){
		r.addReservedDates(checkIn, checkOut);
		Booking b = new Booking (currentStudent, r, checkIn, checkOut);
		b.setPromoUsed(promoCodeUsed);
		currentStudent.addBookings(b);
		//LocalDate[] array = r.getReservedDates().get(0);
		//System.out.printf("Booked check in: %s%nCheck out: %s%n", array[0].toString(), array[1].toString());
		
	}
	
	@SuppressWarnings("unchecked")
	public static void showAllRooms(){
		Stage window = new Stage ();
		window.setTitle("Show all rooms");
		
		TableColumn<Room, String> nameColumn = new TableColumn<Room, String>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
		
		TableColumn<Room, String> capacityColumn = new TableColumn<Room, String>("Capacity");
		capacityColumn.setMinWidth(200);
		capacityColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("capacity"));		
		
		TableColumn<Room, String> priceColumn = new TableColumn<Room, String>("Price");
		priceColumn.setMinWidth(200);
		priceColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("price"));
		
		TableColumn<Room, String> dateColumn = new TableColumn<Room, String>("Available After");
		dateColumn.setMinWidth(200);
		dateColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("availableOn"));	
		
		TableColumn<Room, String> viewableColumn = new TableColumn <Room, String>("Viewable");
		viewableColumn.setMinWidth(200);
		viewableColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("viewable"));	
				
		table = new TableView<Room>();
		table.setItems(getAllRooms());
		table.getColumns().addAll(nameColumn, priceColumn, capacityColumn, dateColumn, viewableColumn);
		
		//delete button 
		Button delete = new Button ("Delete");
		delete.setOnAction(e-> deleteButtonClicked());
		
		//modify button
		Button modify = new Button ("Modify");
		modify.setOnAction(e -> modifyButtonClicked());
		
		//create layout
		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, delete, modify);
		
		Scene scene = new Scene (vBox);
		window.setScene(scene);
		window.show();
	}
	
	//view available room helper function
	public static ObservableList<Room> getAvailableRooms(){
		ObservableList<Room> availableRooms = FXCollections.observableArrayList();
		for (Room r : rooms){
			if (r.getViewable()){
				availableRooms.add(r);
			}
		}
		
		return availableRooms;
	}
	
	@SuppressWarnings("unchecked")
	//for students to book room
	public static void showAvailableRooms(){
		Stage window = new Stage ();
		window.setTitle("Show available rooms");
		
		TableColumn<Room, String> nameColumn = new TableColumn<Room, String>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
		
		TableColumn<Room, String> capacityColumn = new TableColumn<Room, String>("Capacity");
		capacityColumn.setMinWidth(200);
		capacityColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("capacity"));		
		
		TableColumn<Room, String> priceColumn = new TableColumn<Room, String>("Price");
		priceColumn.setMinWidth(200);
		priceColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("price"));	

		TableColumn<Room, String> dateColumn = new TableColumn<Room, String>("Available After");
		dateColumn.setMinWidth(200);
		dateColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("availableOn"));	
				
		table = new TableView<Room>();
		table.setItems(getAvailableRooms());
		table.getColumns().addAll(nameColumn, priceColumn, capacityColumn, dateColumn);
		
		//delete button test
		Button view = new Button ("View Room");
		view.setOnAction(e-> viewButtonClicked());
		
		//create layout
		VBox vBox = new VBox(8);
		vBox.getChildren().addAll(table, view);
		vBox.setPadding (new Insets(0, 10, 10, 10)); //top, right, bottom, left
		
		Scene scene = new Scene (vBox);
		window.setScene(scene);
		window.show();
	}
	
	//delete buton test
	public static void deleteButtonClicked(){
		ObservableList<Room>  allRooms;
		Room roomSelected; 
		
		allRooms = table.getItems();
		roomSelected = table.getSelectionModel().getSelectedItem();
		
		allRooms.remove(roomSelected);
		rooms.remove(roomSelected);
	}
	
	//view room window
	public static void viewButtonClicked(){
		ObservableList<Room> availableRooms;
		Room roomSelected;
		
		availableRooms = table.getItems();
		roomSelected = table.getSelectionModel().getSelectedItem();
		displayRoomDetails(roomSelected);
	}
	
	//display room details
	public static void displayRoomDetails(Room r){
		Stage window = new Stage ();
		window.setTitle("Room details");
		
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); //padding all four corners
		grid.setVgap(8);
		grid.setHgap(10);
		
		//labels
		Label roomName = new Label ("Room Name: ");
		GridPane.setConstraints(roomName, 0, 0); //first column, first row
		Label nameDetail = new Label (r.getName());
		GridPane.setConstraints(nameDetail, 1, 0); //second column, first row
		
		Label roomPrice = new Label ("Price per night: ");
		GridPane.setConstraints(roomPrice, 0, 1); //first column, 2nd row
		Label priceDetail = new Label ("$" + r.getPrice());
		GridPane.setConstraints(priceDetail, 1, 1); //second column, first row
		
		Label roomCapacity = new Label ("Room capacity: ");
		GridPane.setConstraints(roomCapacity, 0, 2); //first column, 3rd row
		Label capacityDetail = new Label (r.getCapacity());
		GridPane.setConstraints(capacityDetail, 1, 2); //second column, first row
		
		Label availableFrom = new Label ("Available after: ");
		GridPane.setConstraints(availableFrom, 0, 3); //first column, 4rd row
		Label dateValue = new Label (r.getAvailableOn().toString());
		GridPane.setConstraints(dateValue, 1, 3); //2nd column, 4rd row
		grid.getChildren().addAll(roomName, nameDetail, roomPrice, priceDetail, roomCapacity, capacityDetail);
		grid.getChildren().addAll(availableFrom, dateValue);
		
		Button bookRoom = new Button ("Book");
		bookRoom.setOnAction(e -> bookRoom(r));
		GridPane.setConstraints(bookRoom, 0, 4); //1st column, 5th row
		grid.getChildren().addAll(bookRoom);
		Scene scene = new Scene (grid, 300, 300);
		window.setScene(scene);
		window.show();
	}
	
	//super user methods/windows
	public void superUserLogInPage(){
		Stage window = new Stage(); 
		window.setTitle("SuperUser Login Page");
		
		//grid layout
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); 
		grid.setVgap(8);
		grid.setHgap(10);
		
		//label for create account header
		Label createAccount = new Label ("Create Account For: ");
		
		//create staff account button
		Button createStaff = new Button("Staff");
		createStaff.setOnAction(e -> staffSignUpPage());
		
		//create student account button
		Button createStudent = new Button("Student");
		createStudent.setOnAction(e -> studentSignUpPage());
		
		//label for manage account header
		Label manageAccount = new Label ("View And Manage Account For: ");
		
		//manage staff account button
		Button manageStaff = new Button ("Staff");
		manageStaff.setOnAction(e -> viewStaffAccount());
		
		//manage student account button
		Button manageStudent = new Button ("Student");
		manageStudent.setOnAction(e -> viewStudentAccount());
		
		//logout button
		Button logout = new Button ("Logout");
		logout.setOnAction(e -> {
			logInPage();
			window.close();
		});
		
		//add components to layout
		grid.add(createAccount, 0, 0);
		grid.add(createStaff, 0, 1); 
		grid.add(createStudent, 1, 1);
		grid.add(manageAccount, 0, 2);
		grid.add(manageStaff, 0, 3);
		grid.add(manageStudent, 1, 3);
		grid.add(logout, 0, 4);
		
		Scene scene = new Scene (grid, 400, 300);
		window.setScene(scene);
		window.show();
	}
	
	public void viewStaffAccount(){
		Stage window = new Stage(); 
		window.setTitle("SuperUser View Staff");
		
		//grid layout
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); 
		grid.setVgap(8);
		grid.setHgap(10);
		
		//counter 
		counter = 0; 
		final int max = staffs.size() - 1; 
		
		//Labels for displaying information
		Label staffNo = new Label ("Staff No " + (counter + 1));
		
		Label name =  new Label ("Name: ");
		Label staffName = new Label (staffs.get(counter).getName());
		
		Label password = new Label ("Password: ");
		Label staffPassword = new Label(staffs.get(counter).getPassword());
		
		Label login = new Label ("Last Login: ");
		Label lastLogin = new Label(staffs.get(counter).getLogin());
		
		Label logout = new Label("Last Logout: ");
		Label lastLogout = new Label(staffs.get(counter).getLogout());
		
		Label suspended = new Label ("Suspended: ");
		Label isSuspended = new Label(staffs.get(counter).getIsSuspended()? "Yes" : "No");
		
		//suspend button
		Button suspend = new Button (staffs.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
		suspend.setOnAction(e -> {
			staffs.get(counter).setIsSuspended(!staffs.get(counter).getIsSuspended());
			isSuspended.setText(staffs.get(counter).getIsSuspended()? "Yes" : "No");
			suspend.setText(staffs.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
		});
		
		//next button
		Button next = new Button ("Next");
		next.setOnAction(e -> {
			if (counter != max){
				staffNo.setText("Staff No " + (++counter + 1));
				staffName.setText(staffs.get(counter).getName());
				staffPassword.setText(staffs.get(counter).getPassword());
				lastLogin.setText(staffs.get(counter).getLogin());
				lastLogout.setText(staffs.get(counter).getLogout());
				isSuspended.setText(staffs.get(counter).getIsSuspended()? "Yes" : "No");
				suspend.setText(staffs.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
			}
		});
		
		//prev button
		Button previous = new Button ("Prev");
		previous.setOnAction(e -> {
			if (counter != 0){
				staffNo.setText("Staff No " + (--counter + 1));
				staffName.setText(staffs.get(counter).getName());
				staffPassword.setText(staffs.get(counter).getPassword());
				lastLogin.setText(staffs.get(counter).getLogin());
				lastLogout.setText(staffs.get(counter).getLogout());
				isSuspended.setText(staffs.get(counter).getIsSuspended()? "Yes" : "No");
				suspend.setText(staffs.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
			}
			
		});
		
		grid.add(staffNo, 0, 0);
		grid.add(name, 0, 1);
		grid.add(staffName, 1, 1);
		grid.add(password, 0, 2);
		grid.add(staffPassword, 1, 2);
		grid.add(login, 0, 3);
		grid.add(lastLogin, 1, 3);
		grid.add(logout, 0, 4);
		grid.add(lastLogout, 1, 4);
		grid.add(suspended, 0, 5);
		grid.add(isSuspended, 1, 5);
		grid.add(suspend, 2, 5);
		grid.add(previous, 0, 6);
		grid.add(next, 1, 6);
		
		Scene scene = new Scene (grid, 400, 300);
		window.setScene(scene);
		window.show();
	}
	
	//view student account
	public void viewStudentAccount(){
		Stage window = new Stage(); 
		window.setTitle("SuperUser View Student");
		
		//grid layout
		GridPane grid = new GridPane();
		grid.setPadding (new Insets(10, 10, 10, 10)); 
		grid.setVgap(8);
		grid.setHgap(10);
		
		//counter 
		counter = 0; 
		final int max = students.size() - 1; 
		
		//Labels for displaying information
		Label studentNo = new Label ("Student No " + (counter + 1));
		
		Label name =  new Label ("Name: ");
		Label studentName = new Label (students.get(counter).getName());
		
		Label password = new Label ("Password: ");
		Label studentPassword = new Label(students.get(counter).getPassword());
		
		Label login = new Label ("Last Login: ");
		Label lastLogin = new Label(students.get(counter).getLogin());
		
		Label logout = new Label("Last Logout: ");
		Label lastLogout = new Label(students.get(counter).getLogout());
		
		Label suspended = new Label ("Suspended: ");
		Label isSuspended = new Label(students.get(counter).getIsSuspended()? "Yes" : "No");
		
		//suspend button
		Button suspend = new Button (students.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
		suspend.setOnAction(e -> {
			students.get(counter).setIsSuspended(!students.get(counter).getIsSuspended());
			isSuspended.setText(students.get(counter).getIsSuspended()? "Yes" : "No");
			suspend.setText(students.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
		});
		
		//next button
		Button next = new Button ("Next");
		next.setOnAction(e -> {
			if (counter != max){
				studentNo.setText("Student No " + (++counter + 1));
				studentName.setText(students.get(counter).getName());
				studentPassword.setText(students.get(counter).getPassword());
				lastLogin.setText(students.get(counter).getLogin());
				lastLogout.setText(students.get(counter).getLogout());
				isSuspended.setText(students.get(counter).getIsSuspended()? "Yes" : "No");
				suspend.setText(students.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
			}
		});
		
		//prev button
		Button previous = new Button ("Prev");
		previous.setOnAction(e -> {
			if (counter != 0){
				studentNo.setText("Student No " + (--counter + 1));
				studentName.setText(students.get(counter).getName());
				studentPassword.setText(students.get(counter).getPassword());
				lastLogin.setText(students.get(counter).getLogin());
				lastLogout.setText(students.get(counter).getLogout());
				isSuspended.setText(students.get(counter).getIsSuspended()? "Yes" : "No");
				suspend.setText(students.get(counter).getIsSuspended()? "Unsuspend" : "Suspend");
			}
			
		});
		
		grid.add(studentNo, 0, 0);
		grid.add(name, 0, 1);
		grid.add(studentName, 1, 1);
		grid.add(password, 0, 2);
		grid.add(studentPassword, 1, 2);
		grid.add(login, 0, 3);
		grid.add(lastLogin, 1, 3);
		grid.add(logout, 0, 4);
		grid.add(lastLogout, 1, 4);
		grid.add(suspended, 0, 5);
		grid.add(isSuspended, 1, 5);
		grid.add(suspend, 2, 5);
		grid.add(previous, 0, 6);
		grid.add(next, 1, 6);
		
		Scene scene = new Scene (grid, 400, 300);
		window.setScene(scene);
		window.show();
	}
	
	//file processing methods
	//save session
	private static void saveSession(){
		openStaffFile("staffFile2.txt");
		createStaffFile();
		closeStaffFile();
		
		openStudentFile("studentFile.txt");
		createStudentFile();
		closeStudentFile();
		
		openRoomFile("roomFile.txt");
		createRoomFile();
		closeRoomFile();
	}
	
	//read session
	private static void readSession(){
		readStaffFile("staffFile2.txt");
		processStaffFile();
		closeStaffInputFile();
		
		readStudentFile("studentfile.txt");
		processStudentFile();
		closeStudentInputFile();
		
		readRoomFile("roomFile.txt");
		processRoomFile();
		closeRoomInputFile();
	}
	
	//create StaffFile (for closing application)
	private static void openStaffFile (String fileName){
		try{
			staffOutput = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening staff file");
		}
	}
	
	private static void createStaffFile (){
		try {
			//staffOutput.writeObject (new Staff ("Kqirk", 1234));
			staffOutput.writeObject (new Staff ("Admin", "1234"));
			for (Staff s : staffs){
				staffOutput.writeObject(s);
			}
		} catch (IOException e){
			System.out.println("Error in writing to staff file");
		}
	}
	
	private static void closeStaffFile(){
		try {
			if (staffOutput != null){
				staffOutput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing staff file");
		}
	}
	
	//create StudentFile (save on close)
	private static void openStudentFile (String fileName){
		try{
			studentOutput = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening student file");
		}
	}
	
	private static void createStudentFile (){
		try {
			for (Student s : students){
				studentOutput.writeObject(s);
			}
		} catch (IOException e){
			System.out.println("Error in writing to student file");
		}
	}
	
	private static void closeStudentFile(){
		try {
			if (studentOutput != null){
				studentOutput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing student file");
		}
	}
	
	//create room file (save on close)
	private static void openRoomFile (String fileName){
		try{
			roomOutput = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening room file");
		}
	}
	
	private static void createRoomFile (){
		try {
			for (Room r : rooms){
				roomOutput.writeObject(r);
			}
		} catch (IOException e){
			System.out.println("Error in writing to room file");
		}
	}
	
	private static void closeRoomFile(){
		try {
			if (roomOutput != null){
				roomOutput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing room file");
		}
	}
	
	//process StaffFile (when opening application for past sessions)
	private static void readStaffFile (String fileName){
		try{
			staffInput = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening staff input file");
		}
	}
	
	private static void processStaffFile (){
		try{
			while(true){
				Staff s = (Staff)(staffInput.readObject());
				staffs.add(s);
			}
		} catch (EOFException e){
		} catch (ClassNotFoundException e){
			System.out.println ("Wrong staff casting");
		} catch (IOException e){
			System.out.println("Error in processing staff file");
		}
	}
	
	private static void closeStaffInputFile(){
		try{
			if(staffInput != null){
				staffInput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing staff input file");
		}
	}
	
	//process student file (read previous/saved data)
	private static void readStudentFile (String fileName){
		try{
			studentInput = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening student input file");
		}
	}
	
	private static void processStudentFile (){
		try{
			while(true){
				Student s = (Student)(studentInput.readObject());
				students.add(s);
			}
		} catch (EOFException e){
		} catch (ClassNotFoundException e){
			System.out.println ("Wrong student casting");
		} catch (IOException e){
			System.out.println("Error in processing student file");
		}
	}
	
	private static void closeStudentInputFile(){
		try{
			if(studentInput != null){
				studentInput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing student input file");
		}
	}
	
	//process room file 
	private static void readRoomFile (String fileName){
		try{
			roomInput = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening room input file");
		}
	}
	
	private static void processRoomFile (){
		try{
			while(true){
				Room r = (Room)(roomInput.readObject());
				rooms.add(r);
			}
		} catch (EOFException e){
		} catch (ClassNotFoundException e){
			System.out.println ("Wrong room casting");
		} catch (IOException e){
			System.out.println("Error in processing room file");
		}
	}
	
	private static void closeRoomInputFile(){
		try{
			if(roomInput != null){
				roomInput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing room input file");
		}
	}
}



