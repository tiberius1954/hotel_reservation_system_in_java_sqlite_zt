package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import classes.Custvalidation;
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Customersinput extends JFrame {
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sfrom = "";
	private JFrame pframe;
	Custvalidation cvad = new Custvalidation();	
	
	Customersinput(JFrame parent) {
		sfrom = "custfrom";
		pframe = parent;
		initcomponents();
		dd.countrycombofill(cmbcountries);
		this.getContentPane().setBackground(new Color(241, 241, 242));
		table_update("");
	}

	Customersinput() {
		initcomponents();
		dd.countrycombofill(cmbcountries);
		this.getContentPane().setBackground(new Color(241, 241, 242));
		table_update("");
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
		setTitle("Customers");
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setBorder(hh.line);
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1200, 450);
		tPanel = maketpanel();
		ePanel = makeepanel();
		cardPanel.add(tPanel, "tabla");
		cardPanel.add(ePanel, "edit");
		add(cardPanel);
		cards.show(cardPanel, "tabla");	
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(20, 20, 1100, 430);
		lbheader = new JLabel("Customers");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(350, 20, 70, 25);
		ttpanel.add(lbsearch);
		
		rname = new JRadioButton("Name");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(430, 20, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(510, 20, 90, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnGroup = new ButtonGroup();
		btnGroup.add(rname);
		btnGroup.add(rtel);		
		ttpanel.add(rname);
		ttpanel.add(rtel);
		
		txsearch = cTextField(25);
		txsearch.setBounds(600, 20, 200, 25);	
		ttpanel.add(txsearch);
		
		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(800, 20, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");		
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        txsearch.setText("");
		        txsearch.requestFocus();
		        table_update("");
		    }
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(hh.borderf2);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(830, 20, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ttpanel.add(btnsearch);
		 btnsearch.addActionListener(new ActionListener() {  
	         public void actionPerformed(ActionEvent e)
	         { 	          
	             int jelem = 0;
	             if (rname.isSelected()) {	           
	                 jelem= 1;
	             } else if (rtel.isSelected()) {	          
	                 jelem=2;
	             } else {
	                 String qual = "Did not choose !"; 
	                 JOptionPane.showMessageDialog(null, qual);  
	                 }   
	          
	             if (jelem< 1|| hh.zempty(txsearch.getText())) {
	            	 return;
	             }   
	            sqlgyart();	             
	         }
	     });

		cus_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) cus_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		cus_table.setTableHeader(new JTableHeader(cus_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(cus_table);

		cus_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cus_table.scrollRectToVisible(cus_table.getCellRect(cus_table.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(cus_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		cus_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null } },
				new String[] { "Cust_ID", "Firstname", "LastName", "Address", "City", "Country", "Phone", "Email",
						"Gender" }));

		hh.setJTableColumnsWidth(cus_table, 1150, 0, 15, 15, 16, 15, 15, 10, 10, 4);
		jScrollPane1.setViewportView(cus_table);
		jScrollPane1.setBounds(30, 80, 1150, 200);
		ttpanel.add(jScrollPane1);

		btnnew = hh.cbutton("New");
		btnnew.setBounds(400, 320, 130, 30);
		btnnew.setBackground(hh.svoros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_new();
			}
		});

		btnupdate = hh.cbutton("Update");
		btnupdate.setBounds(540, 320, 130, 30);
		btnupdate.setBackground(hh.szold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_update();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(680, 320, 130, 30);
		btndelete.setBackground(hh.skek);
		ttpanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cusdata_delete();
			}
		});

		btnsendto = hh.cbutton("Send to reservations");
		btnsendto.setBounds(490, 370, 250, 30);
		btnsendto.setBackground(hh.narancs);

		if (sfrom == "custfrom") {
			ttpanel.add(btnsendto);
		}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});

		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBorder(hh.line);
		eepanel.setBounds(20, 20, 1100, 430);
		eepanel.setBackground(new Color(230, 231, 232));
		lbpicture = new JLabel();
		lbpicture.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/customer.jpg")));
		lbpicture.setBounds(400, 10, 780, 420);
		lbpicture.setBorder(hh.line);
		eepanel.add(lbpicture);

		lbfirstn = hh.clabel("Firstname");
		lbfirstn.setBounds(20, 20, 120, 20);
		eepanel.add(lbfirstn);

		txfirstname = cTextField(25);
		txfirstname.setBounds(150, 20, 200, 25);
		eepanel.add(txfirstname);
		txfirstname.addKeyListener( hh.MUpper());

		lblastn = hh.clabel("Lastname");
		lblastn.setBounds(20, 60, 120, 20);
		eepanel.add(lblastn);

		txlastname = cTextField(25);
		txlastname.setBounds(150, 60, 200, 25);
		eepanel.add(txlastname);
		txlastname.addKeyListener( hh.MUpper());

		lbaddr = hh.clabel("Address");
		lbaddr.setBounds(20, 100, 120, 20);
		eepanel.add(lbaddr);

		txaddress = cTextField(25);
		txaddress.setBounds(150, 100, 200, 25);
		eepanel.add(txaddress);

		lbcity = hh.clabel("City");
		lbcity.setBounds(20, 140, 120, 20);
		eepanel.add(lbcity);
	

		txcity = cTextField(25);
		txcity.setBounds(150, 140, 200, 25);
		eepanel.add(txcity);
		txcity.addKeyListener( hh.MUpper());

		lbcountry = hh.clabel("Country");
		lbcountry.setBounds(20, 180, 120, 20);
		eepanel.add(lbcountry);

		cmbcountries = hh.cbcombo();
		cmbcountries.setBounds(150, 180, 200, 25);
		eepanel.add(cmbcountries);
		cmbcountries.addFocusListener(cFocusListener);

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(20, 220, 120, 20);
		eepanel.add(lbphone);

		txphone = cTextField(25);
		txphone.setBounds(150, 220, 200, 25);
		eepanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());
	

		lbemail = hh.clabel("Email");
		lbemail.setBounds(20, 260, 120, 20);
		eepanel.add(lbemail);

		txemail = cTextField(25);
		txemail.setBounds(150, 260, 200, 25);
		eepanel.add(txemail);

		lbgender = hh.clabel("Gender");
		lbgender.setBounds(20, 300, 120, 20);
		eepanel.add(lbgender);

		cmbgender = hh.cbcombo();
		cmbgender.setModel(new DefaultComboBoxModel(new String[] { "Female", "Male" }));
		cmbgender.setBounds(150, 300, 200, 25);
		eepanel.add(cmbgender);

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(130, 360, 110, 30);
		btnsave.setBackground(hh.svoros);
		eepanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(250, 360, 110, 30);
		eepanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
				cards.show(cardPanel, "tabla");
			}
		});
		return eepanel;
	}

	private void data_new() {
		rowid = "";
		cards.show(cardPanel, "edit");
		txfirstname.requestFocus();
	}

	private void data_update() {
		DefaultTableModel d1 = (DefaultTableModel) cus_table.getModel();
		int row = cus_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			txfirstname.setText(d1.getValueAt(row, 1).toString());
			txlastname.setText(d1.getValueAt(row, 2).toString());
			txaddress.setText(d1.getValueAt(row, 3).toString());
			txcity.setText(d1.getValueAt(row, 4).toString());
			cmbcountries.setSelectedItem(d1.getValueAt(row, 5).toString());
			txphone.setText(d1.getValueAt(row, 6).toString());
			txemail.setText(d1.getValueAt(row, 7).toString());
			cmbgender.setSelectedItem(d1.getValueAt(row, 8).toString());
			cards.show(cardPanel, "edit");
			txfirstname.requestFocus();
		}
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		String country = (String) cmbcountries.getSelectedItem();
		String gender = (String) cmbgender.getSelectedItem();
		String firstname = txfirstname.getText();
		String lastname = txlastname.getText();
		String address = txaddress.getText();
		String city = txcity.getText();
		String phone = txphone.getText();
		String email = txemail.getText();		
		 if (csvalidation(firstname, lastname, address, city, phone, email, country) == false) {
				return;
		  	}

		if (rowid != "") {
			jel = "UP";
			sql = "update  customers set firstname= '" + firstname + "', lastname= '" + lastname + "'," + "address = '"
					+ address + "', city = '" + city + "'," + "phone= '" + phone + "', email= '" + email + "',"
					+ "country = '" + country + "', gender = '" + gender + "'" + " where cust_id = " + rowid;
		} else {
			sql = "insert into customers (firstname,lastname,address,city,country,phone,email,gender) " + "values ('"
					+ firstname + "','" + lastname + "'" + ",'" + address + "','" + city + "','" + country + "','"
					+ phone + "','" + email + "','" + gender + "')";
		}
		try {
			int flag = dh.Insupdel(sql);

			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(firstname, lastname, address, city, country, phone, email, gender);
				} else {
					table_update("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
		cards.show(cardPanel, "tabla");
	}

	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) cus_table.getModel();
		int row = cus_table.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			((Custreservation) pframe).passtocmbc(number);
			pframe.setVisible(true);	
			dispose();
		}
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select Cust_id, Firstname, LastName, Address,City, Country, Phone, Email, Gender from customers";
		} else {
			Sql = "select Cust_id, Firstname, LastName, Address,City, Country, Phone, Email, Gender from "
					+ "customers where "+what;
		}
		ResultSet res = dh.GetData(Sql);
		cus_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Cust_ID", "Firstname", "LastName", "Address", "City", "Country", "Phone", "Email", "Gender" };
		((DefaultTableModel) cus_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(cus_table, 1150, 0, 15, 15, 16, 15, 15, 10, 10, 4);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) cus_table.getDefaultRenderer(Object.class);	
		cus_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cus_table.scrollRectToVisible(cus_table.getCellRect(cus_table.getRowCount() - 1, 0, true));
			}
		});
		if (cus_table.getRowCount() > 0) {
			int row = cus_table.getRowCount() - 1;
			cus_table.setRowSelectionInterval(row, row);
		}
	}
private	int cusdata_delete(){
	String sql ="delete from customers  where cust_id =";
	Boolean error = false;
	int flag = 0;
	DefaultTableModel d1 = (DefaultTableModel) cus_table.getModel();
	int sIndex = cus_table.getSelectedRow();
	if (sIndex < 0) {
		return flag;
	}
	String iid = d1.getValueAt(sIndex, 0).toString();
	if (iid.equals("")) {
		return flag;
	}

	if (dd.cannotdelete("select cust_id from reservations  where cust_id ="+ iid)==true) {
		 JOptionPane.showMessageDialog(null, "You can not delete this customer !");
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
	private void table_rowrefresh(String firstname, String lastname, String address, String city, String country,
			String phone, String email, String gender) {
		DefaultTableModel d1 = (DefaultTableModel) cus_table.getModel();
		d1.setValueAt(firstname, myrow, 1);
		d1.setValueAt(lastname, myrow, 2);
		d1.setValueAt(address, myrow, 3);
		d1.setValueAt(city, myrow, 4);
		d1.setValueAt(country, myrow, 5);
		d1.setValueAt(phone, myrow, 6);
		d1.setValueAt(email, myrow, 7);
		d1.setValueAt(gender, myrow, 8);
	}

	private void clearFields() {		
		cmbcountries.setSelectedIndex(-1);
		cmbgender.setSelectedIndex(-1);
		txfirstname.setText("");
		txlastname.setText("");
		txaddress.setText("");
		txcity.setText("");
		txphone.setText("");
		txemail.setText("");
		rowid = "";
		myrow = 0;
	}
	
	 private void sqlgyart() {		 
		 String sql="";
		 String ss = txsearch.getText().trim().toLowerCase();
		 if (rname.isSelected()) {
			 sql = "lower(lastname) LIKE '%"+ ss + "%' or lower(firstname) LIKE '%"+ ss + "%' order by lastname "
					+ " COLLATE NOCASE ASC";
		 }else {
			 sql = "phone LIKE '%"+ ss + "%' order by phone COLLATE NOCASE ASC";
		 }		  
		   table_update(sql);
	 }
	 
	 private final FocusListener cFocusListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {		
				JComponent c = (JComponent) e.getSource();
				c.setBorder(hh.borderz);
			}

			@Override
			public void focusLost(FocusEvent e) {		
				boolean ret = true;			
				JComponent b = (JComponent) e.getSource();
				JComboBox bb = (JComboBox) e.getSource();
				
				if (bb.getName()=="country") {	
					String country = (String) cmbcountries.getSelectedItem();
					  ret = cvad.countryvalid(country);		
				}    
			
				if (ret == true) {
					b.setBorder(hh.borderf);
				} else {			
				    b.setBorder(hh.borderp);
			}
			}
		};
	 
	 private final FocusListener dFocusListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				JComponent c = (JComponent) e.getSource();
				c.setBorder(hh.borderz);
			}
			@Override
			public void focusLost(FocusEvent e) {
				String content = "";
				boolean ret = true;
				JTextField Txt = (JTextField) e.getSource();
				content = Txt.getText();		
				if (Txt == txfirstname) {
					Txt.setText(content);
					 ret = cvad.fnamevalid(content);
				}	else if (Txt == txlastname) {
					Txt.setText(content);
					 ret = cvad.lnamevalid(content);
				}	else if (Txt == txlastname) {
					Txt.setText(content);
					 ret = cvad.lnamevalid(content);
				}	else if (Txt == txaddress) {
					Txt.setText(content);
					 ret = cvad.addressvalid(content);
				}	else if (Txt == txcity) {
					Txt.setText(content);
					 ret = cvad.cityvalid(content);
				}	else if (Txt == txphone) {
					Txt.setText(content);
					 ret = cvad.phonevalid(content);
				}	else if (Txt == txemail) {
					Txt.setText(content);
					 ret = cvad.emailvalid(content);
				}	
				
				if (ret == true) {
					Txt.setBorder(hh.borderf);
				} else {
					Txt.setBorder(hh.borderp);
				}
			}
		};

	 private Boolean csvalidation(String fname,String lname, String address, String city,
				String phone, String email, String country) {
			Boolean ret = true;
			 ArrayList<String> err = new ArrayList<String>();
			
			 if (!cvad.fnamevalid(fname)) {
				 err.add(cvad.mess);			
			    	ret = false;
			}
			 if (!cvad.lnamevalid(lname)) {
				 err.add(cvad.mess);			
			    	ret = false;
			}
			 if (!cvad.addressvalid(address)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}
			 if (!cvad.cityvalid(city)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}
			 if (!cvad.countryvalid(country)) {
					err.add(cvad.mess);
					ret = false;
				}
			 if (!cvad.phonevalid(phone)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}
			 if (!cvad.emailvalid(email)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}	
			
			 if (err.size() > 0) {
	             JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
	         }			
			return ret;
		}

	public static void main(String args[]) {
		Customersinput cust = new Customersinput();
		cust.setSize(1230, 510);
		cust.setLayout(null);
		cust.setLocationRelativeTo(null);
		cust.setVisible(true);
	}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);	
		textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}

	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnsendto,btnclear, btnsearch;
	JScrollPane jScrollPane1;
	JTable cus_table;
	JComboBox cmbgender, cmbcountries;
	JLabel lbfirstn, lblastn, lbaddr, lbcity, lbcountry, lbphone, lbemail, lbgender, lbheader;
	JLabel lbpicture, lbsearch;
	JTextField txfirstname, txlastname, txaddress, txcity, txphone, txemail, txsearch;
	JPanel cardPanel, tPanel, ePanel;
	CardLayout cards;
	JRadioButton rname, rtel;
	ButtonGroup btnGroup = new ButtonGroup();
}
