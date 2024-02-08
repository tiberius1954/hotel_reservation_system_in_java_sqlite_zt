package classes;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import classes.hhelper;

public class Resvalidation {
	hhelper hh = new hhelper();
	public String mess = "";
	String ss;
	Boolean yes = false;
	
	public boolean roomnovalid(String content) {
		if (hh.zempty(content)) {
			mess= "Roomno is empty !";
			return false;
		}
		return true;
	}
	public boolean daysnumvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Number of days is empty !";
			return false;
		}
		return true;
	}
	public boolean customervalid(String content) {
		if (hh.zempty(content)) {
			mess= "Customer  is empty !";
			return false;
		}
		return true;
	}
	public boolean statusvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Status  is empty !";
			return false;
		}
		return true;
	}
}
