package classes;

public class Customer {	
   
    private int cust_id;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String gender;
    
    public Customer( int cust_id, String firstname, String lastname, String phone ) 
	  { 
	  this.cust_id = cust_id; 
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

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
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
