import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ArrayList;

public class Room implements Serializable {
	private boolean viewable; //staff will make this viewable
	private double price; //per night (fixed)
	private String capacity; //single, double, triple
	private String name; //suite 
	private String promo;
	private boolean promoUsed; //check if promo is used
	private LocalDate availableOn;
	private ArrayList <LocalDate[]> reservedDates;

	public Room (String name, String capacity, double price){
		viewable = false; 
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		this.promo = "";
		promoUsed = false; 
		reservedDates = new ArrayList <LocalDate[]>();
		availableOn = LocalDate.now();
	}
	
	public Room (String name, String capacity, double price, String promo, LocalDate availableOn, boolean viewable){
		this.name = name; 
		this.capacity = capacity;
		this.price = price;
		this.promo = promo;
		this.availableOn = availableOn;
		this.viewable = viewable;
		reservedDates = new ArrayList <LocalDate[]>();
	}
	
	//accessor methods
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
	