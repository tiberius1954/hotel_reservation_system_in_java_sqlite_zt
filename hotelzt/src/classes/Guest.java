package classes;

public class Guest {
	private int guest_id;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String dob;
    private String gender;
    
    public Guest( int guest_id, String firstname, String lastname, String phone ) 
	  { 
	  this.guest_id = guest_id; 
	  this.firstname = firstname; 
	  this.lastname = lastname;
	  this.phone = phone;
	  } 	
@Override
	  public String toString() 
	  { 
	  if (phone =="") {
	     	return firstname + " "+ lastname; 
	  }else {
	       return firstname + " "+ lastname + "  phone:"+ phone; 
	  }
	  } 

  public int getGuest_id() {
      return guest_id;
  }

  public void setGuest_id(int guest_id) {
      this.guest_id = guest_id;
  }

  public String getFirstname() {
      return firstname;
  }
  public void setFirstname(String firstname) {
      this.firstname = firstname;
  }

  public String getLastname() {
      return lastname;
  }
  
  public void setLastname(String lastname) {
      this.lastname = lastname;
  }   

  public String getAddress() {
      return address;
  }

  public void setAddress(String address) {
      this.address = address;
  }
  
  public String getCity() {
      return city;
  }
  public void setCity(String city) {
      this.city = city;
  }

  public void setCountry(String country) {
      this.country = country;
  }
  public String getCountry() {
      return country;
  }    
  public void setPhone(String phone) {
      this.phone = phone;
  }
  public String getPhone() {
      return phone;
  }

  public void setEmail(String email) {
      this.email = email;
  }
  public String getEmail() {
      return email;
  }

  public String getGender() {
      return gender;
  }

  public void setGender(String gender) {
      this.gender = gender;
  }   
}



