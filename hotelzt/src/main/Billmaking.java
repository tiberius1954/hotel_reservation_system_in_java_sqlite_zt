package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
//import classes.Custvalidation;
import classes.Room;
import classes.hhelper;
import classes.Customer;
import classes.Billprint;
import net.proteanit.sql.DbUtils;

public class Billmaking extends JFrame {
	Connection con;
	PreparedStatement pst = null;
	Statement stmt = null;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sfrom = "";
	DefaultTableModel model, imodel;
	private JFrame pframe;
	Vector vec;
	String Finvoicenumber = "";
	String Cust_ID = "";
	Double Ototal;

	Billmaking() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		try {
			dd.costumercombofill(cmbcustomer);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				dispose();
			}
		});

		setTitle("Invoice maker");
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setBorder(hh.line);
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1140, 540);
		tPanel = maketpanel();
		cardPanel.add(tPanel, "tabla");
		add(cardPanel);
		cards.show(cardPanel, "tabla");

	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(10, 10, 1100, 540);
		lbheader = new JLabel("Invoice");
		lbheader.setBounds(30, 20, 200, 25);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);

		lbcustomer = hh.clabel("Customer");
		lbcustomer.setBounds(280, 20, 120, 25);
		ttpanel.add(lbcustomer);

		cmbcustomer = hh.cbcombo();
		cmbcustomer.setName("customer");
		cmbcustomer.setBounds(410, 20, 350, 25);
		ttpanel.add(cmbcustomer);

		res_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) res_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		res_table.setTableHeader(new JTableHeader(res_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(res_table);
		res_table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		resPane = new JScrollPane(res_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		String[] fej = { "RES_ID", "Cust_id", "Customer Name", "City", "Phone", "Room_id", "Room_no", "Checkin",
				"Checkout", "Days", "Status", "Mark" };
	
		Object[][] data = { { null, null, null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null, null, null, null, null } };
	
		model = new DefaultTableModel(data, fej) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				case 6:
					return String.class;
				case 7:
					return String.class;
				case 8:
					return String.class;
				case 9:
					return String.class;
				case 10:
					return String.class;
				case 11:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		res_table.setModel(model);
		hh.setJTableColumnsWidth(res_table, 1080, 0, 0, 29, 15, 10, 0, 8, 8, 10, 4, 11, 5);
		resPane.setViewportView(res_table);
		resPane.setBounds(30, 70, 1080, 130);
		ttpanel.add(resPane);

		btngenerate = hh.cbutton("Generate payment");
		btngenerate.setBounds(770, 17, 180, 30);
		btngenerate.setBackground(hh.vzold);
		ttpanel.add(btngenerate);
		btngenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				generate();
			}
		});

		bill_table = hh.ztable();
		DefaultTableCellRenderer brenderer = (DefaultTableCellRenderer) bill_table.getDefaultRenderer(Object.class);
		brenderer.setHorizontalAlignment(SwingConstants.LEFT);
		bill_table.setTableHeader(new JTableHeader(bill_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(bill_table);
		bill_table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		billPane = new JScrollPane(bill_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String ifej[] = { "Name", "Quantity", "Unit", "Price", "Total", " sres_id", " itemtype", "room_serv_id" };
		Object[][] idata = { { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null } };

		imodel = new DefaultTableModel(idata, ifej);
		bill_table.setModel(imodel);
		hh.setJTableColumnsWidth(bill_table, 600, 50, 10, 10, 10, 10, 0, 0, 0);
		billPane.setViewportView(bill_table);
		billPane.setBounds(260, 230, 610, 228);
		ttpanel.add(billPane);

		btnbillprint = hh.cbutton("Invoice print");
		btnbillprint.setBounds(510, 480, 140, 30);
		btnbillprint.setBackground(hh.skek);
		ttpanel.add(btnbillprint);
		btnbillprint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				bill_print();
			}
		});
		txtotal = new JTextField(15);
		txtotal.setBounds(790, 458, 80, 25);
		txtotal.setFont(new Font("tahoma", Font.BOLD, 12));
		txtotal.setBorder(hh.borderf);
		txtotal.setHorizontalAlignment(JTextField.RIGHT);
		txtotal.setForeground(Color.black);
		ttpanel.add(txtotal);

		cmbcustomer.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cmbcustomer.getSelectedIndex() > 0) {
						Customer ccustomer = (Customer) cmbcustomer.getSelectedItem();
						String customer = ccustomer.toString();
						int cust_id = ccustomer.getCust_id();
						String scust_id = hh.itos(cust_id);
						Cust_ID = scust_id;
						String swhere = "r.status !='Closed'  and r.cust_id = " + scust_id;
						table_update(swhere);
					}
				}
			}
		});

		return ttpanel;
	}

	private void generate() {
		Finvoicenumber = "";
		Ototal = 0.0;
		vec = new Vector();
		String sres_id;
		String sroom_id;
		String sdays;
		int rowCount = model.getRowCount();
		for (int row = 0; row < rowCount; row++) {
			if (model.getValueAt(row, 0) != null) {
				Boolean bbb = ((Boolean) model.getValueAt(row, 11));
				if (bbb == true) {
					sres_id = model.getValueAt(row, 0).toString();
					vec.add(sres_id);
					if (hh.zempty(Finvoicenumber)) {
						Finvoicenumber = "INVOICE-" + hh.padLeftZeros(sres_id, 6);
					}
					sroom_id = model.getValueAt(row, 5).toString();
					sdays = model.getValueAt(row, 9).toString();
					rowmaker(sres_id, sroom_id, sdays);
				}
			}
		}
	}

	void rowmaker(String sres_id, String sroom_id, String sdays) {
		Double price = 0.0;
		String roomno = "";
		String name;
		int quantity = hh.stoi(sdays);
		String unit = "Night";
		Double total;
		String itemtype = "R";
		String room_serv_id = sroom_id;
		imodel.setRowCount(0);
		String sql = "select r.roomno, t.price from rooms r join roomtype t on r.roomtype_id = t.type_id where room_id ="
				+ sroom_id;
		ResultSet res = dh.GetData(sql);
		try {
			while (res.next()) {
				price = res.getDouble("price");
				roomno = res.getString("roomno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		name = roomno;
		total = quantity * price;
		Ototal = Ototal + total;
		imodel.addRow(new Object[] { name, quantity, unit, price, total, sres_id, itemtype, room_serv_id });
		itemtype = "S";
		sql = "select r.stype_id, r.quantity, r.unit, r.price, r.total, t.name from service r join servicetype t on r.stype_id = t.stype_id "
				+ " where res_id =" + sres_id;
		res = dh.GetData(sql);
		try {
			while (res.next()) {
				name = res.getString("name");
				room_serv_id = res.getString("stype_id");
				quantity = res.getInt("quantity");
				total = res.getDouble("total");
				unit = res.getString("unit");
				price = res.getDouble("price");
				total = quantity * price;
				Ototal = Ototal + total;
				imodel.addRow(new Object[] { name, quantity, unit, price, total, sres_id, itemtype, room_serv_id });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		txtotal.setText(hh.ddtos(Ototal));
	}

	private void bill_print() {
		String sql = "";
		String fsql = "insert into invoiceitem (name, quantity, unit, price, total, res_id, itemtype , room_serv_id, invoice_number"
				+ ")values";
		String date = hh.currentDate();
		try {
			con = dh.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			int rowCount = imodel.getRowCount();
			for (int row = 0; row < rowCount; row++) {
				if (imodel.getValueAt(row, 0) != null) {
					String name = imodel.getValueAt(row, 0).toString();
					int quantity = (int) imodel.getValueAt(row, 1);
					String unit = imodel.getValueAt(row, 2).toString();
					Double price = (Double) imodel.getValueAt(row, 3);
					Double total = (Double) imodel.getValueAt(row, 4);
					String sres_id = imodel.getValueAt(row, 5).toString();
					String itemtype = imodel.getValueAt(row, 6).toString();
					String room_serv_id = imodel.getValueAt(row, 7).toString();
					sql = fsql + "('" + name + "'," + quantity + ",'" + unit + "'," + price + "," + total + ",'"
							+ sres_id + "','" + itemtype + "','" + room_serv_id + "','" + Finvoicenumber + "')";
					stmt.executeUpdate(sql);
				}
			}
			sql = "insert into invoiceheader (cust_id, date, total, invoice_number)" + "values ('" + Cust_ID + "','"
					+ date + "'," + Ototal + ",'" + Finvoicenumber + "')";
			stmt.executeUpdate(sql);
			con.commit();
			con.setAutoCommit(true);
			stmt.close();
			con.close();
			String sqla = "update  reservations" + " set checked_out= 1, status= 'Closed' where res_id = ";
			for (int i = 0; i < vec.size(); i++) {
				String sres_int = (String) vec.get(i);
				sql = sqla + "'" + sres_int + "'";
				int flag = dh.Insupdel(sql);
			}
			vec.clear();
			imodel.setRowCount(0);
			model.setRowCount(0);
			txtotal.setText("");
			cmbcustomer.requestFocus();
			hh.setSelectedValue(cmbcustomer, 0);
			goPrint(Finvoicenumber);

		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					 e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	};

	public void table_update(String what) {
		String Sql;
		Boolean mark = Boolean.FALSE;
		if (what == "") {
			Sql = "select r.res_id, r.cust_id, c.firstname || ' ' || c.lastname as custname, c.city, c.phone, i.room_id, i.roomno, r.checkin,"
					+ "r.checkout, r.days,r. status from reservations r "
					+ " join customers c on r.cust_id = c.cust_id  join rooms i on r.room_id = i.room_id";

		} else {
			Sql = "select r.res_id, r.cust_id, c.firstname || ' ' || c.lastname as custname, c.city, c.phone, i.room_id, i.roomno, r.checkin,"
					+ "r.checkout, r.days,r. status  from reservations r "
					+ " join customers c on r.cust_id = c.cust_id  join rooms i on r.room_id = i.room_id where " + what;
		}
		String[] fej = { "RES_ID", "Cust_id", "Customer Name", "City", "Phone", "Room_id", "Room_no", "Checkin",
				"Checkout", "Days", "Status", "Mark" };

		model = (DefaultTableModel) res_table.getModel();
		model.setColumnIdentifiers(fej);
		model.setRowCount(0);
		ResultSet res = dh.GetData(Sql);
		int nrow = 0;
		try {
			while (res.next()) {
				int res_id = res.getInt(1);
				int cust_id = res.getInt(2);
				String custname = res.getString(3);
				String city = res.getString(4);
				String phone = res.getString(5);
				int room_id = res.getInt(6);
				String roomno = res.getString(7);
				String checkin = res.getString(8);
				String checkout = res.getString(9);
				String days = res.getString(10);
				String status = res.getString(11);
				Object[] rows = { res_id, cust_id, custname, city, phone, room_id, roomno, checkin, checkout, days,
						status, mark };
				model.addRow(rows);
				nrow++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		hh.setJTableColumnsWidth(res_table, 1080, 0, 0, 29, 15, 10, 0, 8, 8, 10, 4, 11, 5);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		res_table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
	}

	private void goPrint(String invnumber) {		
		String name =""; String address=""; String city=""; String country="";
	    String phone=""; String email="";
	    String quantity =""; String unit = ""; String total = ""; String price = "";
	    String htotal=""; String hdate="";
	    
		String sql = "select Firstname, LastName, address, city, country, phone ,"
			+ "email from customers where cust_id ='"+ Cust_ID +"'";
			ResultSet rs = dh.GetData(sql);
				try {
					while (rs.next()) {
					name = rs.getString("firstname")+ " "+rs.getString("lastname");
					address = rs.getString("address");
					city = rs.getString("city");
					country = rs.getString("country");
					phone = rs.getString("phone");
					email= rs.getString("email");
					}					
					dh.CloseConnection();
				} catch (SQLException e) {			
					e.printStackTrace();
				}	
				sql = "select date, total from invoiceheader where invoice_number='"+invnumber+"'";
				rs = dh.GetData(sql);
				try {
					while (rs.next()) {
					hdate = rs.getString("date");
					htotal = rs.getString("total");					
					}					
					dh.CloseConnection();
				} catch (SQLException e) {			
					e.printStackTrace();
				}				
		
		Billprint bp = new Billprint();		
		bp.addFHeaderLine(hh.padr("INVOICE Number: " + invnumber,33)+hh.padl(hdate,37 ));
		bp.addFHeaderLine("");
		bp.addFHeaderLine("Budapest Hotel                                                                            Customer: ");
		String ss =hh.padr("Budapest Leonardo Da Vinci Street 50.",40)+  hh.padl("Name: ",10)  + name;
		bp.addFHeaderLine(ss);	
		 ss =hh.padl("Address: ", 50) + address;	
		bp.addFHeaderLine(ss);	
		 ss =hh.padl("City: ", 50) + city;	
		bp.addFHeaderLine(ss);	
		 ss =hh.padl("Country: ", 50) + country;		 
		bp.addFHeaderLine(ss);
		 ss =hh.padl("Phone: ", 50) + phone;	
		bp.addFHeaderLine(ss);
		 ss =hh.padl("Email: ", 50) + email;	
		bp.addFHeaderLine(ss);	
		String header =hh.padr("Description",25) + " "+ hh.padl("Quantity", 10)+" " +hh.padr("Unit",10)
			+" " +	hh.padl("Price",10) +" "+ hh.padl("Total",12);
		 bp.addHeaderLine(header);	
		 sql = "select  name, quantity, unit, price, total from invoiceitem where invoice_number='"+invnumber+"'";
			rs = dh.GetData(sql);
			String row="";
				try {
					while (rs.next()) {
					name = rs.getString("name");
					quantity = rs.getString("quantity");
					unit  = rs.getString("unit");
				    price = rs.getString("price");
					total = rs.getString("total");	
					row  =hh.padr(name,25)+" "+hh.padl(quantity, 10)+" "+ hh.padr("Unit",10)+" "+hh.padl(price,10)
					+" " +hh.padl(total,12)+" ";
					bp.addBodyLine(row);					
					}
					dh.CloseConnection();
					row  = hh.padl("Total : ",67)+htotal.substring(0, htotal.indexOf("."));
					bp.addBodyLine(row);		
				
				} catch (SQLException e) {			
					e.printStackTrace();
				}	
		
       bp.printReport();
	}

	public static void main(String args[]) {
		Billmaking bi = new Billmaking();
		bi.setSize(1170, 600);
		bi.setLayout(null);
		bi.setLocationRelativeTo(null);
		bi.setVisible(true);
	}

	JComboBox cmbcustomer;
	JPanel cardPanel, tPanel, ePanel;
	CardLayout cards;
	JLabel lbheader, lbcustomer;
	JTable res_table, bill_table;
	JScrollPane resPane, billPane;
	JButton btngenerate, btnbillprint;
	JTextField txtotal;
}
