package classes;

public class Room {		    
	    private int room_id;
		private String roomno;
		private int floor;	
		private String roomtype;
		private String status;	
		
		public Room( int room_id, String roomno,  String roomtype ) 
		  { 
		  this.room_id = room_id; 
		  this.roomno = roomno; 
		  this.roomtype = roomtype; 
		  } 			
		
	  @Override
		  public String toString() 
		  { 		
		     	return roomno + " "+ roomtype; 		
		  }  
	    
	    public Room(String roomNo)
	    {
	        roomno = roomNo;
	    }

	    public int getRoom_id() {
	        return room_id;
	    }

	    public void setRoom_id(int room_id) {
	        this.room_id = room_id;
	    }

	    public String getRoomno() {
	        return roomno;
	    }

	    public void setRoomno(String roomno) {
	        this.roomno = roomno;
	    }
	  
	    public void setRoomtype(String roomtype) {
	        this.roomtype = roomtype;
	    }

	    public String getRoomtype() {
	        return roomtype;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getStatus() {
	        return status;
	    }

}
