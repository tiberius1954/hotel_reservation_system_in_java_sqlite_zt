package Databaseop;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import classes.Customer;
import classes.Guest;
import classes.Room;
import classes.Roomtype;
import classes.Servicetype;
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Databaseop {
	DatabaseHelper dh = new DatabaseHelper();
	hhelper hh = new hhelper();

	public void countrycombofill(JComboBox ccombo) {
		String Sql = " select countryname from country order by  countryname";
		try {
			ResultSet res = dh.GetData(Sql);
			while (res.next()) {
				ccombo.addItem(res.getString("countryname"));
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rtable_delete(JTable dtable, String sql) {
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.setRowCount(0);
		}
	}

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag == 1)
				d1.removeRow(sIndex);
		}
		return flag;
	}

	public void costumercombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Customer A = new Customer(0, "", "", "");
		mycombo.addItem(A);
		String sql = "select Cust_id, Firstname, LastName, phone from customers order by lastname, firstname";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Customer(rs.getInt("cust_id"), rs.getString("firstname"), rs.getString("lastname"),
					rs.getString("phone"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void guestcombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Guest A = new Guest(0, "", "", "");
		mycombo.addItem(A);
		String sql = "select guest_id, Firstname, LastName, phone from guests order by lastname, firstname";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Guest(rs.getInt("guest_id"), rs.getString("firstname"), rs.getString("lastname"),
					rs.getString("phone"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void roomtypecombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Roomtype A = new Roomtype(0, "");
		mycombo.addItem(A);
		String sql = "select type_id, name from roomtype order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Roomtype(rs.getInt("type_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void servicetypecombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Servicetype A = new Servicetype(0, "", "");
		mycombo.addItem(A);
		String sql = "select stype_id, name, unit from servicetype order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Servicetype(rs.getInt("stype_id"), rs.getString("name"), rs.getString("unit"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void freeroomscombofill(JComboBox mycombo, String startdate, String enddate) throws SQLException {
		mycombo.removeAllItems();
		Room A = new Room(0, "", "");
//		mycombo.addItem(A);
		String sql = "select r.room_id, r.roomno, t.name from rooms r join roomtype t on r.roomtype_id = t.type_id "
				+ "	WHERE room_id not in ( " + " select  room_id from reservations where " + "  (checkin >= date('"
				+ startdate + "')" + " and checkin < date('" + enddate + "'))" + " or (checkout > date('" + startdate
				+ "')" + " and checkout < date('" + enddate + "'))" + " or (checkin < date('" + startdate + "')"
				+ " and  checkout > date('" + enddate + "')))";

		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Room(rs.getInt("room_id"), rs.getString("roomno"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void roomscombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Room A = new Room(0, "", "");
		mycombo.addItem(A);
		String sql = "select r.room_id, r.roomno, t.name from rooms r join roomtype t on r.roomtype_id = t.type_id ";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Room(rs.getInt("room_id"), rs.getString("roomno"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}

	public void res_table_update(JTable dtable, String what) {
		String Sql;
		if (what == "") {
			Sql = "select r.res_id, r.cust_id, c.firstname || ' ' || c.lastname as custname, c.city, c.phone, i.room_id, i.roomno, r.checkin,"
					+ "r.checkout, r.days,r. status, r.reservation_date from reservations r "
					+ " join customers c on r.cust_id = c.cust_id  join rooms i on r.room_id = i.room_id";

		} else {
			Sql = "select r.res_id, r.cust_id, c.firstname || ' ' || c.lastname as custname, c.city, c.phone, i.room_id, i.roomno, r.checkin,"
					+ "r.checkout, r.days,r. status, r.reservation_date from reservations r "
					+ " join customers c on r.cust_id = c.cust_id  join rooms i on r.room_id = i.room_id where " + what;
		}
		ResultSet res = dh.GetData(Sql);
		dtable.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "RES_ID", "Cust_id", "Customer Name", "City", "Phone", "Room_id", "Room_no", "Checkin",
				"Checkout", "Days", "Status", "Reserv_date" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 1030, 0, 0, 25, 10, 13, 0, 10, 10, 10, 4, 8, 10);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		dtable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}

	public void rooms_table_update(JTable dtable, String id) {
		String Sql;
		Sql = "select r.gur_id, r.res_id, r.guest_id , g.Firstname, g.lastname,g.phone,g.city, g.dob, r.in_date, r.Out_date from guestreservation r"
				+ " join guests g on r.guest_id = g.guest_id where res_id =" + id;
		ResultSet res = dh.GetData(Sql);
		dtable.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Gur_ID", "res_id", "Guest_id", "Guest Firstname", "Lastname", "Phone", "City", "Dob",
				"In_date", "Out_date" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 1010, 0, 0, 0, 18, 18, 18, 16, 10, 10, 10);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
	}
	
	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		ResultSet res = dh.GetData(sql);
		 try {
			if(res.next()){ 				
				 found = true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();		
			return found;						
	}
}
