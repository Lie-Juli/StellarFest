package model;

public class Event {
	private int id;
	private String name;
	private String date;
	private String location;
	private String description;
	public Event(int id, String name, String date, String location, String description) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
