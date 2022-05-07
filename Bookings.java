import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ArrayList;

public class Booking implements Serializable{
	private Student student;
	private Room room;
	private String roomName; 
	private LocalDate checkInDate;
	private LocalDate checkOutDate; 
	
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
	
}