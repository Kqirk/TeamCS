import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	
}

class Staff {
	//private final int id;
	private int password;
	private String name;
	
	
}

class LoginPage extends JFrame implements ActionListener{
	private JTextField testField1;
	private JTextField testField2;
	private JLabel username;
	private JButton staffb;
	private JButton student;
	
	public LoginPage(){
		super("Login page");
		setLayout (new FlowLayout());
		
		username = new JLabel ("Username");
		add(username);
		testField1 = new JTextField (10);
		add(testField1);
		
		staffb = new JButton ("Staff button");
		student = new JButton ("Student button");
		add (staffb);
		add (student); 
		
		staffb.addActionListener (this);
	}
	
	@Override
	public void actionPerformed (ActionEvent e){
		if (e.getSource() == staffb){
			StaffPage sp = new StaffPage ();
			sp.setVisible (true);
			sp.setSize (400, 600);
			this.dispose(); //dispose after creating the frame 
			
			//lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		}
	}
}

class StaffPage extends JFrame implements ActionListener{
	private JButton createRoom;
	
	public StaffPage () {
		super ("Staff page");
		setLayout (new FlowLayout());
		
		createRoom = new JButton ("Create room");
		add (createRoom);
		createRoom.addActionListener(this);
	}
	
	@Override
	public void actionPerformed (ActionEvent e){
		if (e.getSource() == createRoom){
			CreateRoom lf = new CreateRoom ();
			lf.setVisible (true);
			lf.setSize (400, 600);
			//lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		}
	}
}

class CreateRoom extends JFrame implements ActionListener{
	private JTextField nameInput;
	private JLabel name;
	private JTextField capacityInput;
	private JLabel capacity;
	private JTextField priceInput;
	private JLabel price;
	private JButton create;
	
	public CreateRoom (){
		super("Create room");
		setLayout (new FlowLayout());
		
		name = new JLabel ("Name of room");
		add (name);
		nameInput = new JTextField (10);
		add (nameInput);
		
		capacity = new JLabel ("Capacity of room?");
		add (capacity);
		
		capacityInput = new JTextField (10);
		add (capacityInput);
		
		price = new JLabel ("Price: ");
		add (price);
		priceInput = new JTextField (10);
		add (priceInput);
		
		create = new JButton ("Create");
		add (create);
		create.addActionListener(this);
	}
	
	@Override
	public void actionPerformed (ActionEvent e){
		if (e.getSource() == create){
			Room room = new Room (nameInput.getText(), capacityInput.getText(), Double.parseDouble(priceInput.getText()));
			roomBooking.rooms.add(room);
			
			JOptionPane.showMessageDialog (CreateRoom.this, String.format("Room name: %s%nCapacity: %s%nPrice: %.2f%n", room.name, room.capacity, room.price));
			//System.out.print(room);
		
		}
	}
}


class roomBooking
{
	public static ArrayList <Student> students = new ArrayList <Student> ();
	public static ArrayList <Staff> staffs = new ArrayList <Staff> ();
	public static ArrayList <Room> rooms = new ArrayList <Room> ();
	
	public static void main (String [] args)
	{
		LoginPage lf = new LoginPage ();
		lf.setVisible (true);
		lf.setSize (400, 600);
		//lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		for (Room r : rooms){
			System.out.print(r);
		}
	}
}
