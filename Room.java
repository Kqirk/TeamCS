import java.io.*;
import java.nio.file.*;

public class Room implements Serializable {
	private boolean booked; 
	private boolean viewable; //staff will make this viewable
	private double price; //per night (fixed)
	private String capacity; //single, double, triple
	private String name; //suite 
	private String promo;
	//date i have no idea 
	// booked on
	// avaible from 
	// noOfNights

	public Room (String name, String capacity, double price){
		booked = false;
		viewable = false; 
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		this.promo = "";
	}
	
	//accessor methods
	public boolean getBooked(){
		return booked;
	}
	
	public void setBooked(boolean booked){
		this.booked = booked;
	}
	
	public boolean getViewable(){
		return viewable;
	}
	
	public void setViewable(boolean viewable){
		this.viewable = viewable;
	}
	
	public String getPromo(){
		return promo;
	}
	
	public void setPromo(String promo){
		this.promo = promo;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getCapacity(){
		return capacity;
	}
	
	public void setCapacity(String capacity){
		this.capacity = capacity;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	@Override
	public String toString (){
		return String.format("Room name: %s%nCapacity: %s%nPrice: %.2f%n", name, capacity, price);
	}
}
	