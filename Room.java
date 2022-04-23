import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ArrayList;

public class Room implements Serializable {
	private boolean booked; 
	private boolean viewable; //staff will make this viewable
	private double price; //per night (fixed)
	private String capacity; //single, double, triple
	private String name; //suite 
	private String promo;
	private boolean promoUsed; //check if promo is used
	private LocalDate availableOn;
	private ArrayList <LocalDate[]> reservedDates;
	private String bookedBy; //booked by which username


	public Room (String name, String capacity, double price){
		booked = false;
		viewable = false; 
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		this.promo = "";
		bookedBy = "";
		promoUsed = false; 
		reservedDates = new ArrayList <LocalDate[]>();
		availableOn = LocalDate.now();
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
	
	public LocalDate getAvailableOn (){
		return availableOn;
	}
	
	public void setAvailableOn (LocalDate availableOn){
		this.availableOn = availableOn;
	}
	
	public ArrayList <LocalDate[]> getReservedDates(){
		return reservedDates;
	}
	
	public void addReservedDates(LocalDate checkIn, LocalDate checkOut){
		LocalDate[] array = new LocalDate[2];
		array[0] = checkIn;
		array[1] = checkOut;
		reservedDates.add(array);
	}
	
	@Override
	public String toString (){
		return String.format("Room name: %s%nCapacity: %s%nPrice: %.2f%n", name, capacity, price);
	}
}
	