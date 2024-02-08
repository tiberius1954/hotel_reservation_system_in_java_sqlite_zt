package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
import com.toedter.calendar.JDateChooser;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import classes.Customer;
import classes.Guest;
import classes.Room;
import classes.Servicetype;
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Servicesinput extends JFrame {
	ResultSet res;
	 Connection con = null;
	 PreparedStatement pst = null;	
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sresid = "";
	String sdate = hh.currentDate();
	String stime;
	JFrame myframe = this;  
	
	Servicesinput() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		try {
			dd.servicetypecombofill(cmbservices);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dd.res_table_update(res_table, "");		
	}

	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
	
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel)=="tabla") {		
				dispose();
			}else {							
				cards.show(cardPanel, "edit");
				cmbservices.requestFocus();						
			}
			}
		});
		setTitle("Services");
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setBorder(hh.line);
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1200, 600);
		tPanel = maketpanel();
		tPanel.setName("tabla");
		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(tPanel, "tabla");		
		cardPanel.add(ePanel, "edit");
		add(cardPanel);
		cards.show(cardPanel, "tabla");
//		cards.show(cardPanel, "edit");      
	}	

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(10, 10, 1100, 580);
		lbheader = new JLabel("Services");
		lbheader.setBounds(30, 60, 300, 25);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);
		lbgheader = new JLabel("Service items");
		lbgheader.setBounds(30, 310, 300, 30);
		lbgheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbgheader);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(280, 60, 70, 25);
		ttpanel.add(lbsearch);

		txsearch = cTextField(25);
		txsearch.setBounds(360, 60, 200, 25);
		// txsearch.setFocusable(false);
		ttpanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(560, 60, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				dd.res_table_update(res_table, "");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(590, 60, 150, 25);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));	
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.addItem("Name");
		cmbsearch.addItem("Phone");
		cmbsearch.addItem("Room_no");
		cmbsearch.setBackground(Color.ORANGE);
		ttpanel.add(cmbsearch);
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(745, 60, 90, 25);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 sqlgyart();
			}
		});
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
		res_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) res_table.getModel();
				try {
					int row = res_table.getSelectedRow();
					if (row > -1) {
						String id = model.getValueAt(row, 0).toString();
						servs_table_update(servs_table, id);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(res_table);
		res_table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		resPane = new JScrollPane(res_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		res_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "RES_ID", "Cust_id", "Customer Name", "City", "Phone", "Room_id", "Room_no", "Checkin",
						"Checkout", "Days", "Status", "Reserv_date" }));
		hh.setJTableColumnsWidth(res_table, 1150, 0, 0, 25, 10, 13, 0, 10, 10, 10, 4, 8, 10);
		resPane.setViewportView(res_table);
		resPane.setBounds(30, 120, 1150, 180);
		ttpanel.add(resPane);

		servs_table = hh.ztable();
		DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) servs_table.getDefaultRenderer(Object.class);
		rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
		servs_table.setTableHeader(new JTableHeader(servs_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(servs_table);
		servPane = new JScrollPane(servs_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		servs_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null }, },
				new String[] { "Serv_ID", "res_id", "stype_id", "Name", "Quantity", "Unit", "Price", "Total", "Date",
						"Time" }));

		hh.setJTableColumnsWidth(servs_table, 1010, 0, 0, 0, 30, 10, 5, 10, 10, 10, 10, 5);
		servPane.setViewportView(servs_table);
		servPane.setBounds(30, 340, 1030, 227);

		btngnew = hh.cbutton("New");
		btngnew.setBounds(1080, 400, 100, 30);
		btngnew.setBackground(hh.svoros);
		ttpanel.add(btngnew);
		btngnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gdata_new();
			}
		});
		btngupdate = hh.cbutton("Update");
		btngupdate.setBounds(1080, 440, 100, 30);
		btngupdate.setBackground(hh.szold);
		ttpanel.add(btngupdate);
		btngupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gdata_update();
			}
		});

		btngdelete = hh.cbutton("Delete");
		btngdelete.setBounds(1080, 480, 100, 30);
		btngdelete.setBackground(hh.skek);
		ttpanel.add(btngdelete);
		btngdelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(servs_table, "delete from service  where serv_id =");
			}
		});
		ttpanel.add(servPane);
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBounds(20, 20, 1100, 580);	
		eepanel.setBackground(new Color(230, 231, 232));
		JPanel sepanel = new JPanel(null);
		sepanel.setBounds(30, 55, 450, 410);
		sepanel.setBorder(hh.line);
		eepanel.add(sepanel);

		lbpicture = new JLabel();
		lbpicture.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/service.jpg")));
		lbpicture.setBounds(495, 55, 670, 410);
		lbpicture.setBorder(hh.line);
		eepanel.add(lbpicture);

		lbservices = hh.clabel("Service name");
		lbservices.setBounds(10, 20, 120, 25);
		sepanel.add(lbservices);

		cmbservices = hh.cbcombo();
		cmbservices.setName("services");
		cmbservices.setBounds(140, 20, 200, 25);
		sepanel.add(cmbservices);
		cmbservices.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Servicetype service = (Servicetype) cmbservices.getSelectedItem();
					String unit = service.getUnit();
					int stype_id = service.getStype_id();
					txunit.setText(unit);
				}
			}
		});
		txstype_id = cTextField(25); // hidden
		
		btnnewtype= hh.cbutton("New type");
		btnnewtype.setBounds(350, 20, 90, 25);
		btnnewtype.setBackground(hh.skek);
		sepanel.add(btnnewtype);
		btnnewtype.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Servicetypes ser = new Servicetypes(myframe);
				ser.setSize(870, 450);
				ser.setLayout(null);
				ser.setLocationRelativeTo(null);
				ser.setVisible(true);			
			}
		});

		lbquantity = hh.clabel("Quantity");
		lbquantity.setBounds(10, 70, 120, 25);
		sepanel.add(lbquantity);

		txquantity = cTextField(25);
		txquantity.setBounds(140, 70, 200, 25);
		txquantity.setHorizontalAlignment(JTextField.RIGHT);
		sepanel.add(txquantity);
		txquantity.addKeyListener(hh.Onlynum());

		lbunit = hh.clabel("Unit");
		lbunit.setBounds(10, 120, 120, 25);
		sepanel.add(lbunit);

		txunit = cTextField(25);
		txunit.setBounds(140, 120, 200, 25);
		sepanel.add(txunit);

		lbprice = hh.clabel("Price");
		lbprice.setBounds(10, 170, 120, 25);
		sepanel.add(lbprice);

		txprice = cTextField(25);
		txprice.setBounds(140, 170, 200, 25);
		txprice.setHorizontalAlignment(JTextField.RIGHT);
		sepanel.add(txprice);
		// this is working with VK_TAB
		txprice.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		txprice.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '.' || c == '-') {
				} else if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == evt.VK_TAB || evt.getKeyCode() == evt.VK_ENTER) {					
				txtotal.setText(hh.zmultiply(txquantity.getText(), txprice.getText()));	
				txtotal.grabFocus();
				}
			}
		});

		lbtotal = hh.clabel("Total");
		lbtotal.setBounds(10, 220, 120, 25);
		sepanel.add(lbtotal);

		txtotal = cTextField(25);
		txtotal.setBounds(140, 220, 200, 25);
		txtotal.setHorizontalAlignment(JTextField.RIGHT);
		sepanel.add(txtotal);
		txtotal.addKeyListener(hh.Onlynum());

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(190, 280, 110, 30);
		btnsave.setBackground(hh.svoros);
		sepanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(190, 320, 110, 30);
		sepanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
				cards.show(cardPanel, "tabla");
			}
		});
		return eepanel;
	}

	private void gdata_new() {
		DefaultTableModel d1 = (DefaultTableModel) res_table.getModel();
		int row = res_table.getSelectedRow();
		if (row > -1) {
			sresid = d1.getValueAt(row, 0).toString();
			rowid = "";
			myrow = 0;
			cards.show(cardPanel, "edit");
			cmbservices.requestFocus();
		}
	}

	private void gdata_update() {
		DefaultTableModel d1 = (DefaultTableModel) servs_table.getModel();
		int row = servs_table.getSelectedRow();
		myrow = 0;
		sresid = "";
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			sresid = d1.getValueAt(row, 1).toString();
			String cnum = d1.getValueAt(row, 2).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbservices, number);
			cmbservices.updateUI();
			txquantity.setText(d1.getValueAt(row, 4).toString());
			txunit.setText(d1.getValueAt(row, 5).toString());
			txprice.setText(d1.getValueAt(row, 6).toString());
			txtotal.setText(d1.getValueAt(row, 7).toString());
			cards.show(cardPanel, "edit");
			cmbservices.requestFocus();
		}
	}

	public void servs_table_update(JTable dtable, String id) {
		DefaultTableModel model = (DefaultTableModel) dtable.getModel();	
		model.setRowCount(0);
		String Sql;
		Sql = "select s.serv_id, s.res_id, s.stype_id , t.name, s.quantity, s.unit, s.price,s.total, s.date, s.time from service s "
				+ " join servicetype t on s.stype_id = t.stype_id where res_id =" + id;
		try {
			con = dh.getConnection();
			  pst = con.prepareStatement(Sql);
          res = pst.executeQuery();	
			while (res.next()) {
				String serv_id= res.getString("serv_id");
				String res_id= res.getString("res_id");
				String stype_id= res.getString("stype_id");
				String name= res.getString("name");
				String quantity= res.getString("quantity");
				String unit= res.getString("unit");
				String ss = res.getString("price");
				String price = hh.bsf(ss);
				ss = res.getString("total");
				String total =  hh.bsf(ss);
				String date = res.getString("date");
				String time = res.getString("time");
				model.addRow(new Object[] {serv_id, res_id, stype_id, name, quantity, unit, price, total, date, time});			
				}
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally {
			try {
				res.close();
				pst.close();
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}		
	
		String[] fej = { "Serv_ID", "res_id", "stype_id", "Name", "Quantity", "Unit", "Price", "Total", "Date",
				"Time" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(servs_table, 1010, 0, 0, 0, 30, 10, 5, 10, 10, 10, 10, 5);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		dtable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		dtable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		dtable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
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

	void savebuttrun() {
		stime = hh.currenttime();
		String sql = "";
		String jel = "";
		int resid = hh.stoi(sresid);
		Servicetype service = (Servicetype) cmbservices.getSelectedItem();
		int stype_id = service.getStype_id();
		String name = service.getName();
		String unit = txunit.getText();
		String quantity = txquantity.getText();
		String price = hh.bsf(txprice.getText());
		String total = hh.bsf(txtotal.getText());

		if (hh.zempty(price) || hh.zempty(total) || stype_id <= 0) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  service set  stype_id = " + stype_id + "," + "quantity = '" + quantity + "', unit = '" + unit
					+ "', price ='" + price + "', total = '" + total + "', date= '" + sdate + "', time='" + stime
					+ "' where serv_id = " + rowid;
		} else {
			sql = "insert into service (res_id , stype_id, quantity,unit, price,total,date, time) " + "values (" + resid
					+ "," + stype_id + ",'" + quantity + "','" + unit + "','" + price + "','" + total + "','" + sdate
					+ "','" + stime + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(stype_id, name, quantity, unit, price, total, stime);
				} else {
					servs_table_update(servs_table, sresid);
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

	void table_rowrefresh(int stype_id, String name, String quantity, String unit, String price, String total,
			String stime) {
		DefaultTableModel d1 = (DefaultTableModel) servs_table.getModel();
		String sstype_id = hh.itos(stype_id);
		d1.setValueAt(sstype_id, myrow, 2);
		d1.setValueAt(name, myrow, 3);
		d1.setValueAt(quantity, myrow, 4);
		d1.setValueAt(unit, myrow, 5);
		d1.setValueAt(price, myrow, 6);
		d1.setValueAt(total, myrow, 7);
		d1.setValueAt(sdate, myrow, 8);
		d1.setValueAt(stime, myrow, 9);
	}

	private void clearFields() {
		txstype_id.setText("");
		txquantity.setText("");
		txunit.setText("");
		txprice.setText("");
		txtotal.setText("");
		rowid = "";
		myrow = 0;
		hh.setSelectedValue(cmbservices, 0);
		cmbservices.updateUI();
	}
	
	private void sqlgyart() {
		String stext= txsearch.getText().trim().toLowerCase();
		String scmbtxt = (String) cmbsearch.getSelectedItem();
		String swhere = "";
		if (!hh.zempty(stext)) {			
				if ( scmbtxt== "Name") {
					swhere= " lower(custname) like '%" + stext.trim() + "%'";
				} else if (scmbtxt == "Phone") {
					swhere = " phone like '%" + stext.trim() + "%' ";
				} else if (scmbtxt== "Room_no") {
					swhere= " lower(roomno) like '%" + stext.trim() + "%' ";
				}				
				dd.res_table_update(res_table, swhere);
		} else {
			JOptionPane.showMessageDialog(null, "Empty condition !", "Error", 1);
			return;
		}	
	}
	public void passtocmb(int number, String sprice, String sunit) {
		try {
			dd.servicetypecombofill(cmbservices);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hh.setSelectedValue(cmbservices, number);
		cmbservices.updateUI();
		txquantity.setText("");
		txunit.setText(sunit);
		txprice.setText(sprice);
		txtotal.setText("");
	}

	public static void main(String args[]) {
		Servicesinput ser = new Servicesinput();
		ser.setSize(1230, 650);
		ser.setLayout(null);
		ser.setLocationRelativeTo(null);
		ser.setVisible(true);
	}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);		
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}

	CardLayout cards;
	JTable res_table, servs_table;
	JPanel cardPanel, tPanel, ePanel, sepanel;
	JScrollPane resPane, servPane;
	JLabel lbheader, lbgheader, lbsearch, lbpicture, lbservices, lbquantity, lbunit, lbprice, lbtotal;
	JButton btnsearch, btnclear, btngnew, btngupdate, btngdelete, btnnewtype;
	JButton btnsave, btncancel;
	JComboBox cmbsearch, cmbservices;
	JTextField txsearch, txstype_id, txquantity, txunit, txprice, txtotal;
}
