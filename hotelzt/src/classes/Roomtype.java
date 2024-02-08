package classes;

public class Roomtype {
	private int type_id;
	private String name;
	private Long price;
	private int capacity;
	public Roomtype( int type_id, String name,  Long price, int capacity) 
	  { 
	  this.type_id = type_id; 
	  this.name = name; 
	  this.price = price; 
	  this.capacity = capacity;
	  } 	
	public Roomtype( int type_id, String name) 
	  { 
	  this.type_id = type_id; 
	  this.name = name; 	
	  } 	
	
@Override
	  public String toString() 
	  { 		
	     	return name; 		
	  }  

public int getType_id() {
    return type_id;
}

public void setType_id(int type_id) {
    this.type_id = type_id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
public long getPrice() {
    return price;
}

public void setPrice(long price) {
    this.price = price;
}

public int getCapacity() {
    return capacity;
}

public void setCapacity(int capacity) {
    this.capacity = capacity;
}
}
