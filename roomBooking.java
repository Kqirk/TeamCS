import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Room {
	private boolean booked;
	private boolean viewable;
	private double price; //per night (fixed)
	private String capacity; //single, double, triple
	private String name; 
	
	public Room (String name, String capacity, double price){
		booked = false;
		viewable = false; 
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		
	}
	
	public void launchRoom() {
		
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
			StaffPage lf = new StaffPage ();
			lf.setVisible (true);
			lf.setSize (400, 600);
			lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		}
	}
}

class StaffPage extends JFrame {
	private JButton createRoom;
	
	public StaffPage () {
		super ("Staff page");
		setLayout (new FlowLayout());
		
		createRoom = new JButton ("Create room");
		add (createRoom);
		
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
		lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
}