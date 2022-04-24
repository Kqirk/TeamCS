import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.util.*;


class Student implements Serializable{
	private String password; //1234
	private String name; //userid Kirk 
	private ArrayList <Booking> bookings;
	private final String role;
	
	public Student (String name, String password){
		this.name = name;
		this.password = password;
		this.role = "Student"; 
		bookings = new ArrayList <Booking> ();
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
}

class Staff implements Serializable{
	private String password;
	private String name;
	private final String role;
	
	public Staff (){
		password = "";
		name = "";
		this.role = "Staff";
	}
	public Staff (String name, String password) {
		this.name = name;
		this.password = password;
		this.role = "Staff";
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
}

class Booking implements Serializable{
	private Student student;
	private Room r;
	private LocalDate checkInDate;
	private LocalDate checkOutDate; 
	
	public Booking (Student student, Room r, LocalDate checkInDate, LocalDate checkOutDate){
		this.student = student;
		this.r = r;
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
		return r;
	}
	
	public void setRoom(Room r){
		this.r = r;
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
	
	//current profile
	private static Student currentStudent;
	
	public static void main(String[] args){
		//openStaffFile("staffFile2.txt");
		//createStaffFile();
		//closeStaffFile();
		readStaffFile("staffFile2.txt");
		processStaffFile();
		closeStaffInputFile();
		Staff staff = new Staff ("Kirk", "1234");
		students.add(new Student ("Study", "1234"));
		rooms.add(new Room("Deluxe", "Double", 150));
		rooms.add(new Room("Premium", "Triple", 180));
		rooms.add(new Room("Suite", "Double", 250));
		Room r = new Room("Viewable", "Double", 300);
		r.setViewable(true);
		rooms.add(r);
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
		Button loginButton = new Button ("Login");
		grid.getChildren().add(loginButton);
		
		//adding action lister
		loginButton.setOnAction (e -> {
			if(verifyLogin (nameInput.getText().trim(), passInput.getText().trim())){
				if (getRole(nameInput.getText().trim()).equals("Staff")){
					staffLogInPage();
				} else {
					setStudentSession(nameInput.getText().trim());
					studentLogInPage();
				}
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
	public boolean verifyLogin (String userID, String password){
		for (Staff s : staffs){
			if (s.getName().equals(userID)){
				if (s.getPassword().equals(password)){
					System.out.println("Authenticated");
					return true;
				}
			}
		}
		
		for (Student s : students){
			if (s.getName().equals(userID)){
				if (s.getPassword().equals(password)){
					System.out.println("Authenticated");
					return true;
				}
			}
		}
		return false;
	}
	
	public String getRole (String userID){
		for (Staff s : staffs){
			if (s.getName().equals(userID)){
				return s.getRole();
			}
		}
		
		for (Student st : students){
			if(st.getName().equals(userID)){
				return st.getRole();
			}
		}
		return "Role not found";
	}
	
	public void setStudentSession (String userID){
			for (Student s : students){
				if(s.getName().equals(userID)){
				currentStudent = s; 
			}
		}
	}
	
	//sign up page 
	//maybe add re-enter password and logic?
	public void staffSignUpPage (){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Signup Page");
		
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
		Button signupButton = new Button ("Signup");
		
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
		Scene scene = new Scene (grid, 300, 200);
		window.setScene (scene);
		window.show();
	}
	
	//student sign up page
	public void studentSignUpPage(){
		//create new stage 
		Stage window = new Stage ();
		
		//set title of stage
		window.setTitle ("Student Signup Page");
		
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
		Button signupButton = new Button ("Signup");
		
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
			staffSignUpPage();
			window.close();
		});
		
		student.setOnAction(e ->{
			studentSignUpPage();
			window.close();
		});
		
		Scene scene = new Scene (grid, 200, 300);
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
		Stage window = new Stage ();
		window.setTitle("(Staff) You have logged in");
		
		//Label label = new Label ("Welcome to UOW accomodations");
		GridPane grid = new GridPane();
		
		//grid.getChildren().add(label);
		
		Button b = new Button ("Show all rooms");
		b.setOnAction(e -> showAllRooms());
		grid.getChildren().add(b);
		
		Button createRoom = new Button ("Create Room");
		createRoom.setOnAction(e -> createRoom());
		grid.add(createRoom, 0, 1);
		Scene scene = new Scene (grid, 640, 480);
		window.setScene (scene);
		window.show();
	}
	
	//Staff window to create room
	public void createRoom (){
		Stage window = new Stage ();
		window.setTitle("Create new room");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
        grid.setVgap(10);
		
		
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
			Room r = new Room(nameInput.getText(), capInput.getText(), Double.valueOf(priceInput.getText()),
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
	
	public void studentLogInPage(){
		Stage window = new Stage ();
		window.setTitle("(Student) You have logged in");
		
		Label label = new Label ("Welcome to UOW accomodations");
		VBox vbox = new VBox ();
		
		vbox.getChildren().add(label);
		
		Button bookRoom = new Button("Book Room");
		bookRoom.setOnAction(e -> showAvailableRooms());
		vbox.getChildren().add(bookRoom);
		Button showRooms = new Button("Show reservations");
		showRooms.setOnAction (e -> viewReservations());
		vbox.getChildren().add(showRooms);
		Scene scene = new Scene (vbox, 640, 480);
		window.setScene (scene);
		window.show();
	}	
	
	//student to view his own reservations
	public void viewReservations(){
		ArrayList<Booking> bookings = new ArrayList<>();
		bookings = currentStudent.getBookings();
		
		for (Booking b : bookings){
			System.out.printf("Room name: %s%nRoom price: %.2f%nCheck In: %s%nCheck Out: %s%n", b.getRoom().getName(), 
							b.getRoom().getPrice(), b.getCheckInDate().toString(), b.getCheckOutDate().toString());
		}
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
                           
                            if (item.isBefore(r.getAvailableOn().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }   
							
							for (LocalDate[] array : r.getReservedDates()){
								if (item.isBefore(array[1].plusDays(1)) && item.isAfter(array[0].minusDays(1))){
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
		
        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
		
        checkInDatePicker.setValue(LocalDate.now());
		checkOutDatePicker.setValue(checkInDatePicker.getValue().plusDays(1));
		
		checkInDatePicker.setDayCellFactory(dayCellFactory);
		checkOutDatePicker.setDayCellFactory(dayCellFactory);
        
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
		
		Button book = new Button ("Book");
		book.setOnAction(e -> bookButtonClicked(r, checkInDatePicker.getValue(), checkOutDatePicker.getValue()));
		gridPane.add(book, 0, 5);
		
		window.show();
	}
	
	public static void bookButtonClicked(Room r, LocalDate checkIn, LocalDate checkOut){
		r.addReservedDates(checkIn, checkOut);
		Booking b = new Booking (currentStudent, r, checkIn, checkOut);
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
				
		table = new TableView<Room>();
		table.setItems(getAllRooms());
		table.getColumns().addAll(priceColumn, capacityColumn,nameColumn );
		
		//delete button test
		Button delete = new Button ("Delete");
		delete.setOnAction(e-> deleteButtonClicked());
		
		//create layout
		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, delete);
		
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
				
		table = new TableView<Room>();
		table.setItems(getAvailableRooms());
		table.getColumns().addAll(priceColumn, capacityColumn,nameColumn );
		
		//delete button test
		Button delete = new Button ("View Room");
		delete.setOnAction(e-> viewButtonClicked());
		
		//create layout
		VBox vBox = new VBox();
		vBox.getChildren().addAll(table, delete);
		
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
	//file processing methods
	//create StaffFile (for closing application)
	private static void openStaffFile (String fileName){
		try{
			staffOutput = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening file");
		}
	}
	
	private static void createStaffFile (){
		try {
			//staffOutput.writeObject (new Staff ("Kqirk", 1234));
			staffOutput.writeObject (new Staff ("Admin", "1234"));
			for (Staff s : staffs){
				staffOutput.writeObject(s);
			}
			System.out.println("Kqirk written to staffoutput");
		} catch (IOException e){
			System.out.println("Error in writing to file");
		}
	}
	
	private static void closeStaffFile(){
		try {
			if (staffOutput != null){
				staffOutput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing file");
		}
	}
	
	//process StaffFile (when opening application for past sessions)
	private static void readStaffFile (String fileName){
		try{
			staffInput = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
		} catch (IOException e){
			System.out.println("Error in opening file");
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
			System.out.println ("Wrong casting");
		} catch (IOException e){
			System.out.println("Error in processing file");
		}
	}
	
	private static void closeStaffInputFile(){
		try{
			if(staffInput != null){
				staffInput.close();
			}
		} catch (IOException e){
			System.out.println("Error in closing file");
		}
	}
}



