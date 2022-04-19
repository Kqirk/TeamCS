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

import java.io.*;
import java.nio.file.*;

import java.util.*;


class Student implements Serializable{
	private String password; //1234
	private String name; //userid Kirk 
	ArrayList <Room> bookedRooms;
	private final String role;
	
	public Student (String name, String password){
		bookedRooms = new ArrayList <Room>();
		this.name = name;
		this.password = password;
		this.role = "Student";
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
	
	public static void main(String[] args){
		openStaffFile("staffFile2.txt");
		createStaffFile();
		closeStaffFile();
		readStaffFile("staffFile2.txt");
		processStaffFile();
		closeStaffInputFile();
		Staff staff = new Staff ("Kirk", "1234");
		students.add(new Student ("Study", "1234"));
		rooms.add(new Room("Deluxe", "Double", 150));
		rooms.add(new Room("Premium", "Triple", 180));
		rooms.add(new Room("Suite", "Double", 250));
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
	
	//sign up page 
	//maybe add re-enter password and logic?
	public void signUpPage (){
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
		
		Label label = new Label ("Welcome to UOW accomodations");
		StackPane sp = new StackPane ();
		
		sp.getChildren().add(label);
		
		Button b = new Button ("Show all rooms");
		b.setOnAction(e -> showAllRooms());
		sp.getChildren().add(b);
		Scene scene = new Scene (sp, 640, 480);
		window.setScene (scene);
		window.show();
	}
	
	public void studentLogInPage(){
		Stage window = new Stage ();
		window.setTitle("(Student) You have logged in");
		
		Label label = new Label ("Welcome to UOW accomodations");
		VBox vbox = new VBox ();
		
		vbox.getChildren().add(label);
		
		Button bookRoom = new Button("Book Room");
		bookRoom.setOnAction(e -> bookRoom());
		vbox.getChildren().add(bookRoom);
		Scene scene = new Scene (vbox, 640, 480);
		window.setScene (scene);
		window.show();
	}	
	
	//view rooms window
	public static ObservableList<Room> getRooms(){
		ObservableList<Room> allRooms = FXCollections.observableArrayList();
		for (Room r : rooms){
			allRooms.add(r);
		}
		
		return allRooms;
	}
	
	//book room window
	public static void bookRoom(){
		DatePicker checkInDatePicker;
		DatePicker checkOutDatePicker;
		
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
		window.show();
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
		table.setItems(getRooms());
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
	
	//delete buton test
	public static void deleteButtonClicked(){
		ObservableList<Room> roomSelected, allRooms;
		allRooms = table.getItems();
		roomSelected = table.getSelectionModel().getSelectedItems();
		
		roomSelected.forEach(allRooms::remove);
		rooms.forEach(e-> System.out.println(e));
		roomSelected.forEach(rooms::remove);
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



