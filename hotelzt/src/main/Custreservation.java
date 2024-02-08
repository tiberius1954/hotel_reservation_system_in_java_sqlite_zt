package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import com.toedter.calendar.JDateChooser;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import classes.Customer;
import classes.Guest;
import classes.Resvalidation;
import classes.Room;
import classes.hhelper;

public class Custreservation extends JFrame {
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sresid = "";
	JDateChooser checkin = new JDateChooser(new Date());
	JDateChooser checkout = new JDateChooser(new Date());
	JDateChooser indate = new JDateChooser(new Date());
	JDateChooser outdate = new JDateChooser(new Date());
	JFrame myframe = this;
	public JComboBox cmbguest;
	Resvalidation rvad = new Resvalidation();	

	Custreservation() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		try {
			dd.costumercombofill(cmbcustomer);
			dd.guestcombofill(cmbguest);
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
					//	MainFrame ob = new MainFrame();
					//	ob.setVisible(true);
						dispose();
					}else if(hh.whichpanel(cardPanel)=="edit") {						
						cards.show(cardPanel, "edit");	
					}else {
						cards.show(cardPanel, "gedit");
						cmbguest.requestFocus();
					}	
				}
		});

		setTitle("Reservations");
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setBorder(hh.line);
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1200, 600);
		tPanel = maketpanel();
		tPanel.setName("tabla");
		ePanel = makeepanel();
		ePanel.setName("edit");
		gePanel = makegepanel();
		gePanel.setName("gedit");
		cardPanel.add(tPanel, "tabla");
		cardPanel.add(ePanel, "edit");
		cardPanel.add(gePanel, "gedit");
		add(cardPanel);
		cards.show(cardPanel, "tabla");
//	cards.show(cardPanel, "edit");
		// cards.show(cardPanel, "gedit");
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(10, 10, 1100, 580);
		lbheader = new JLabel("Reservations");
		lbheader.setBounds(30, 60, 300, 25);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);

		lbgheader = new JLabel("Guests in room");
		lbgheader.setBounds(30, 360, 300, 40);
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
				dd.res_table_update(res_table,"");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(590, 60, 150, 25);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		// cmbsearch.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		// cmbsearch.setForeground( Color.BLACK);
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));	
		cmbsearch.setBackground(Color.ORANGE);	
		cmbsearch.addItem("Name");		
		cmbsearch.addItem("Phone");
		cmbsearch.addItem("Room_no");
		ttpanel.add(cmbsearch);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(745, 60, 90, 25);
	//	btnsearch.setBorder(hh.borderf2);
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
						dd.rooms_table_update(rooms_table, id);
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
		hh.setJTableColumnsWidth(res_table, 1030, 0, 0, 25, 10, 13, 0, 10, 10, 10, 4, 8, 10);
		resPane.setViewportView(res_table);
		resPane.setBounds(30, 120, 1030, 230);
		ttpanel.add(resPane);

		btnnew = hh.cbutton("New");
		btnnew.setBounds(1080, 160, 100, 30);
		btnnew.setBackground(hh.svoros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_new();
			}
		});

		btnupdate = hh.cbutton("Update");
		btnupdate.setBounds(1080, 200, 100, 30);
		btnupdate.setBackground(hh.szold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_update();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(1080, 240, 100, 30);
		btndelete.setBackground(hh.skek);
		ttpanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		rooms_table = hh.ztable();
		DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) rooms_table.getDefaultRenderer(Object.class);
		rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
		rooms_table.setTableHeader(new JTableHeader(rooms_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rooms_table);
		roomPane = new JScrollPane(rooms_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rooms_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null }, },
				new String[] { "Gur_ID", "res_id", "Guest_id", "Guest Firstname", "Lastname", "Phone", "City", "Dob",
						"In_date", "Out_date" }));

		hh.setJTableColumnsWidth(rooms_table, 1010, 0, 0, 0, 18, 18, 18, 16, 10, 10, 10);
		roomPane.setViewportView(rooms_table);
		roomPane.setBounds(30, 400, 1030, 150);

		btngnew = hh.cbutton("New");
		btngnew.setBounds(1080, 420, 100, 30);
		btngnew.setBackground(hh.svoros);
		ttpanel.add(btngnew);
		btngnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gdata_new();
			}
		});
		btngupdate = hh.cbutton("Update");
		btngupdate.setBounds(1080, 460, 100, 30);
		btngupdate.setBackground(hh.szold);
		ttpanel.add(btngupdate);
		btngupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				gdata_update();
			}
		});

		btngdelete = hh.cbutton("Delete");
		btngdelete.setBounds(1080, 500, 100, 30);
		btngdelete.setBackground(hh.skek);
		ttpanel.add(btngdelete);
		btngdelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(rooms_table, "delete from guestreservation  where gur_id =");
			}
		});

		ttpanel.add(roomPane);
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBorder(hh.line);
		eepanel.setBounds(20, 20, 1100, 580);
		eepanel.setBackground(new Color(230, 231, 232));
		lbpicture = new JLabel();
		lbpicture.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/reservation.jpg")));
		lbpicture.setBounds(510, 55, 670, 410);
		lbpicture.setBorder(hh.line);
		eepanel.add(lbpicture);

		lbcustomer = hh.clabel("Customer");
		lbcustomer.setBounds(20, 20, 120, 25);
		eepanel.add(lbcustomer);

		cmbcustomer = hh.cbcombo();
		cmbcustomer.setName("customer");
		cmbcustomer.setBounds(150, 20, 350, 25);
		eepanel.add(cmbcustomer);
		cmbcustomer.addFocusListener(cFocusListener);

		btnnewcust = hh.cbutton("New Customer");
		btnnewcust.setBounds(355, 60, 145, 25);
		btnnewcust.setBackground(hh.skek);
		eepanel.add(btnnewcust);
		btnnewcust.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Customersinput cue = new Customersinput(myframe);
				cue.setSize(1230, 510);
				cue.setLayout(null);
				cue.setLocationRelativeTo(null);
				cue.setVisible(true);
			}
		});

		lbcheckin = hh.clabel("Checkin");
		lbcheckin.setBounds(20, 60, 120, 25);
		eepanel.add(lbcheckin);

		checkin.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		checkin.setDateFormatString("yyyy-MM-dd");
		checkin.setFont(new Font("Arial", Font.BOLD, 16));
		checkin.setBounds(150, 60, 200, 25);
		eepanel.add(checkin);
		checkin.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					countd();
				}
			}
		});

		lbcheckout = hh.clabel("Checkout");
		lbcheckout.setBounds(20, 100, 120, 25);
		eepanel.add(lbcheckout);

		checkout.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		checkout.setDateFormatString("yyyy-MM-dd");
		checkout.setFont(new Font("Arial", Font.BOLD, 16));
		checkout.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					countd();
				}
			}
		});
		checkout.setBounds(150, 100, 200, 25);
		eepanel.add(checkout);

		lbdaysnum = hh.clabel("Number of days");
		lbdaysnum.setBounds(10, 140, 130, 25);
		eepanel.add(lbdaysnum);

		txdaysnum = cTextField(25);
		txdaysnum.setBounds(150, 140, 200, 25);
		txdaysnum.setHorizontalAlignment(JTextField.LEFT);
		eepanel.add(txdaysnum);

		lbfreerooms = hh.clabel("Free rooms");
		lbfreerooms.setBounds(20, 180, 120, 25);
		eepanel.add(lbfreerooms);

		cmbfreerooms = hh.cbcombo();
		cmbfreerooms.setBounds(150, 180, 200, 25);
		cmbfreerooms.setName("cmbfreeroom");
		eepanel.add(cmbfreerooms);
		cmbfreerooms.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Room room = (Room) cmbfreerooms.getSelectedItem();
					txroomno.setText(room.getRoomno());
					int rid = room.getRoom_id();
					String srid = hh.itos(rid);
					txroom_id.setText(srid);
				}
			}
		});

		btnroomno = hh.cbutton("Free rooms");
		btnroomno.setBounds(360, 180, 120, 25);
		btnroomno.setBackground(hh.slilla);
		eepanel.add(btnroomno);

		btnroomno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					String startdate = ((JTextField) checkin.getDateEditor().getUiComponent()).getText();
					String enddate = ((JTextField) checkout.getDateEditor().getUiComponent()).getText();
					if (!hh.twodate(startdate, enddate) == false) {
						dd.freeroomscombofill(cmbfreerooms, startdate, enddate);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		lbroomno = hh.clabel("Roomno");
		lbroomno.setBounds(20, 220, 120, 25);
		eepanel.add(lbroomno);

		txroomno = cTextField(25);
		txroomno.setBounds(150, 220, 200, 25);
		eepanel.add(txroomno);

		txroom_id = cTextField(25); // not visible

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(20, 260, 120, 25);
		eepanel.add(lbstatus);

		cmbstatus = hh.cbcombo();
		cmbstatus.setBounds(150, 260, 200, 25);
		cmbstatus.setModel(new DefaultComboBoxModel(new String[] { "", "Ready", "Reserved", "Occupied","Closed" }));
		cmbstatus.setName("status");
		eepanel.add(cmbstatus);
		cmbstatus.addFocusListener(cFocusListener);

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(190, 310, 110, 30);
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
		btncancel.setBounds(190, 350, 110, 30);
		eepanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields("R");
				cards.show(cardPanel, "tabla");
			}
		});
		return eepanel;
	}

	private JPanel makegepanel() {
		JPanel gepanel = new JPanel(null);
		gepanel.setBorder(hh.line);
		gepanel.setBounds(20, 20, 1100, 580);
		gepanel.setBackground(new Color(230, 231, 232));
		lbpictureg = new JLabel();
		lbpictureg.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/room.jpg")));
		lbpictureg.setBounds(510, 55, 670, 410);
		lbpictureg.setBorder(hh.line);
		gepanel.add(lbpictureg);

		lbguestroom = hh.clabel("Guest  in room");
		lbguestroom.setBounds(150, 20, 200, 25);
		gepanel.add(lbguestroom);

		lbguest = hh.clabel("Guests");
		lbguest.setBounds(20, 60, 120, 25);
		gepanel.add(lbguest);

		cmbguest = hh.cbcombo();
		cmbguest.setName("guest");
		cmbguest.setBounds(150, 60, 350, 25);
		gepanel.add(cmbguest);
		cmbguest.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int rid = ((Guest) cmbguest.getSelectedItem()).getGuest_id();
					String srid = hh.itos(rid);
					txguest_id.setText(srid);
					if (rid > 0) {
						textfieldsfill(rid);
					}
				}
			}
		});

		txguest_id = new JTextField(25); // hidden

		btngnewguest = hh.cbutton("New guest");
		btngnewguest.setBounds(360, 100, 140, 25);
		btngnewguest.setBackground(hh.skek);
		gepanel.add(btngnewguest);
		btngnewguest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Guestsinput gue = new Guestsinput(myframe);
				gue.setSize(1230, 550);
				gue.setLayout(null);
				gue.setLocationRelativeTo(null);
				gue.setVisible(true);
			}
		});

		lbfirstname = hh.clabel("Firstname");
		lbfirstname.setBounds(20, 100, 120, 25);
		gepanel.add(lbfirstname);

		txfirstname = cTextField(25);
		txfirstname.setBounds(150, 100, 200, 25);
		txfirstname.setEnabled(false);
		txfirstname.setDisabledTextColor(Color.magenta);
		gepanel.add(txfirstname);

		lblastname = hh.clabel("Lastname");
		lblastname.setBounds(20, 140, 120, 25);
		gepanel.add(lblastname);

		txlastname = cTextField(25);
		txlastname.setBounds(150, 140, 200, 25);
		txlastname.setEnabled(false);
		txlastname.setDisabledTextColor(Color.magenta);
		gepanel.add(txlastname);

		lbcity = hh.clabel("City");
		lbcity.setBounds(20, 180, 120, 25);
		gepanel.add(lbcity);

		txcity = cTextField(25);
		txcity.setBounds(150, 180, 200, 25);
		txcity.setEnabled(false);
		txcity.setDisabledTextColor(Color.magenta);
		gepanel.add(txcity);

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(20, 220, 120, 25);
		gepanel.add(lbphone);

		txphone = cTextField(25);
		txphone.setBounds(150, 220, 200, 25);
		txphone.setEnabled(false);
		txphone.setDisabledTextColor(Color.magenta);
		gepanel.add(txphone);

		lbdob = hh.clabel("Date of birth");
		lbdob.setBounds(20, 260, 120, 25);
		gepanel.add(lbdob);

		txdob = cTextField(25);
		txdob.setBounds(150, 260, 200, 25);
		txdob.setEnabled(false);
		txdob.setDisabledTextColor(Color.magenta);
		gepanel.add(txdob);

		lbindate = hh.clabel("Indate");
		lbindate.setBounds(20, 300, 120, 25);
		gepanel.add(lbindate);

		indate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		indate.setDateFormatString("yyyy-MM-dd");
		indate.setFont(new Font("Arial", Font.BOLD, 16));
		indate.setBounds(150, 300, 200, 25);
		gepanel.add(indate);
		indate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lboutdate = hh.clabel("Outdate");
		lboutdate.setBounds(20, 340, 120, 25);
		gepanel.add(lboutdate);

		outdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		outdate.setDateFormatString("yyyy-MM-dd");
		outdate.setFont(new Font("Arial", Font.BOLD, 16));
		outdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});
		outdate.setBounds(150, 340, 200, 25);
		gepanel.add(outdate);

		btngsave = hh.cbutton("Save");
		btngsave.setBounds(190, 410, 110, 30);
		btngsave.setBackground(hh.svoros);
		gepanel.add(btngsave);

		btngsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savegbuttrun();
			}
		});
		btngcancel = hh.cbutton("Cancel");
		btngcancel.setBackground(hh.szold);
		btngcancel.setBounds(190, 460, 110, 30);
		gepanel.add(btngcancel);
		btngcancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields("G");
				cards.show(cardPanel, "tabla");
			}
		});
		return gepanel;
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		int cust_id = 0;
		int room_id = 0;
		cust_id = ((Customer) cmbcustomer.getSelectedItem()).getCust_id();
		Customer ccustomer = (Customer) cmbcustomer.getSelectedItem();
		String customer = ccustomer.toString();			
		cust_id = ccustomer.getCust_id();
		room_id = hh.stoi(txroom_id.getText());
		String scheckin = ((JTextField) checkin.getDateEditor().getUiComponent()).getText();
		String scheckout = ((JTextField) checkout.getDateEditor().getUiComponent()).getText();
		String days = txdaysnum.getText();
		String status = (String) cmbstatus.getSelectedItem();
		String reservation_date = hh.currentDate();
		String roomno = txroomno.getText();
		
		if (rsvalidation(roomno,days, customer,  status)==false) {
			return;
		}	
		if (rowid != "") {
			jel = "UP";
			sql = "update  reservations set cust_id= " + cust_id + ", room_id= " + room_id + "," + "checkin = '"
					+ scheckin + "', checkout = '" + scheckout + "'," + "status= '" + status + "', reservation_date= '"
					+ reservation_date + "'," + "days = '" + days + "'" + " where res_id = " + rowid;
		} else {
			sql = "insert into reservations (cust_id, room_id, checkin, checkout, status, reservation_date, days) "
					+ "values (" + cust_id + "," + room_id + ",'" + scheckin + "','" + scheckout + "','" + status
					+ "','" + reservation_date + "'," + days + ")";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh();
				} else {
					dd.res_table_update(res_table, "");
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields("R");
		cards.show(cardPanel, "tabla");
	}

	private void data_new() {
		rowid = "";
		cards.show(cardPanel, "edit");
		cmbcustomer.requestFocus();
	}

	private void data_update() {
		DefaultTableModel d1 = (DefaultTableModel) res_table.getModel();
		int row = res_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			String cnum = d1.getValueAt(row, 1).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbcustomer, number);
			cmbcustomer.updateUI();
			cmbfreerooms.removeAllItems();
			Date date;
			Date date1;
			try {
				String dd = d1.getValueAt(row, 7).toString();
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
				checkin.setDate(date);
				dd = d1.getValueAt(row, 8).toString();
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
				checkout.setDate(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			txroom_id.setText(d1.getValueAt(row, 5).toString());
			txdaysnum.setText(d1.getValueAt(row, 9).toString());
			txroomno.setText(d1.getValueAt(row, 6).toString());
			countd();
			if (d1.getValueAt(row, 10) != null) {
				cmbstatus.setSelectedItem(d1.getValueAt(row, 10).toString());
			} else {
				cmbstatus.setSelectedItem("");
			}
			cards.show(cardPanel, "edit");
			cmbcustomer.requestFocus();
		}
	}

	private void data_delete() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) res_table.getModel();
		int sIndex = res_table.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		flag = dd.data_delete(res_table, "delete from reservations  where res_id =");
		if (flag == 1) {
			dd.rtable_delete(rooms_table, "delete from guestreservation  where res_id =" + iid);
		}
	}

	private void savegbuttrun() {
		String sql = "";
		String jel = "";
		int guest_id = 0;
		int resid = hh.stoi(sresid);
		guest_id = ((Guest) cmbguest.getSelectedItem()).getGuest_id();
		String sindate = ((JTextField) indate.getDateEditor().getUiComponent()).getText();
		String soutdate = ((JTextField) outdate.getDateEditor().getUiComponent()).getText();

		if (hh.zempty(sindate) || hh.zempty(soutdate) || guest_id <= 0) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  guestreservation set res_id= " + resid + ", guest_id= " + guest_id + "," + "in_date = '"
					+ sindate + "', out_date = '" + soutdate + "' where gur_id = " + rowid;
		} else {
			sql = "insert into guestreservation (res_id , guest_id, in_date, out_date) " + "values (" + resid + ","
					+ guest_id + ",'" + sindate + "','" + soutdate + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				// JOptionPane.showMessageDialog(null, "Success !");
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					rooms_table_rowrefresh();
				} else {
					dd.rooms_table_update(rooms_table, sresid);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields("G");
		cards.show(cardPanel, "tabla");
	}

	private void gdata_new() {
		DefaultTableModel d1 = (DefaultTableModel) res_table.getModel();
		int row = res_table.getSelectedRow();
		if (row>-1) {
		sresid = d1.getValueAt(row, 0).toString();
		rowid = "";
		cards.show(cardPanel, "gedit");
		cmbguest.requestFocus();
		}
	}

	private void gdata_update() {
		DefaultTableModel d1 = (DefaultTableModel) rooms_table.getModel();
		int row = rooms_table.getSelectedRow();
		myrow = 0;
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
			hh.setSelectedValue(cmbguest, number);
			cmbguest.updateUI();
			Date date;
			Date date1;
			try {
				String dd = d1.getValueAt(row, 8).toString();
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
				indate.setDate(date);
				dd = d1.getValueAt(row, 9).toString();
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
				outdate.setDate(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			txfirstname.setText(d1.getValueAt(row, 3).toString());
			txlastname.setText(d1.getValueAt(row, 4).toString());
			txphone.setText(d1.getValueAt(row, 5).toString());
			txcity.setText(d1.getValueAt(row, 6).toString());
			txdob.setText(d1.getValueAt(row, 7).toString());
			cards.show(cardPanel, "gedit");
			cmbguest.requestFocus();
		}
	}

	private void table_rowrefresh() {
		DefaultTableModel d1 = (DefaultTableModel) res_table.getModel();
		String sql = "select r.res_id, r.cust_id, c.firstname || ' ' || c.lastname as custname, c.city, c.phone, i.room_id, i.roomno, r.checkin,"
				+ "r.checkout, r.days,r. status, r.reservation_date from reservations r "
				+ " join customers c on r.cust_id = c.cust_id  join rooms i on r.room_id = i.room_id "
				+ "where res_id =" + rowid;
		ResultSet res = dh.GetData(sql);
		try {
			while (res.next()) {
				d1.setValueAt(res.getInt("cust_id"), myrow, 1);
				d1.setValueAt(res.getString("custname"), myrow, 2);
				d1.setValueAt(res.getString("city"), myrow, 3);
				d1.setValueAt(res.getString("phone"), myrow, 4);
				d1.setValueAt(res.getInt("room_id"), myrow, 5);
				d1.setValueAt(res.getString("roomno"), myrow, 6);
				d1.setValueAt(res.getString("checkin"), myrow, 7);
				d1.setValueAt(res.getString("checkout"), myrow, 8);
				d1.setValueAt(res.getString("days"), myrow, 9);
				d1.setValueAt(res.getString("status"), myrow, 10);
				d1.setValueAt(res.getString("reservation_date"), myrow, 11);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
	}

	private void rooms_table_rowrefresh() {
		DefaultTableModel d1 = (DefaultTableModel) rooms_table.getModel();
		String sql = "select r.gur_id, r.res_id, r.guest_id , g.Firstname, g.lastname,g.phone,g.city, g.dob, r.in_date, r.Out_date from guestreservation r"
				+ " join guests g on r.guest_id = g.guest_id where gur_id =" + rowid;
		ResultSet res = dh.GetData(sql);
		try {
			while (res.next()) {
				d1.setValueAt(res.getInt("guest_id"), myrow, 2);
				d1.setValueAt(res.getString("firstname"), myrow, 3);
				d1.setValueAt(res.getString("lastname"), myrow, 4);
				d1.setValueAt(res.getString("phone"), myrow, 5);
				d1.setValueAt(res.getString("city"), myrow, 6);
				d1.setValueAt(res.getString("dob"), myrow, 7);
				d1.setValueAt(res.getString("in_date"), myrow, 8);
				d1.setValueAt(res.getString("out_date"), myrow, 9);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
	}

	private void clearFields(String which) {
		Date date = new Date();
		if (which == "R") {
			txroomno.setText("");
			txroom_id.setText("");
			txdaysnum.setText("");
			cmbfreerooms.removeAllItems();
			hh.setSelectedValue(cmbguest, 0);
			cmbguest.updateUI();
			cmbstatus.setSelectedItem(-1);
			checkin.setDate(date);
			checkout.setDate(date);
			rowid = "";
			myrow = 0;
		} else if (which == "G") {
			txguest_id.setText("");
			txfirstname.setText("");
			txlastname.setText("");
			txphone.setText("");
			txcity.setText("");
			txdob.setText("");
			hh.setSelectedValue(cmbguest, 0);
			cmbguest.updateUI();
			cmbfreerooms.removeAllItems();
			indate.setDate(date);
			outdate.setDate(date);
			rowid = "";
			myrow = 0;
		}
	}

	private void textfieldsfill(int rid) {
		String sql = " select firstname,lastname,city,phone,dob from guests where guest_id=" + rid;
		ResultSet res = dh.GetData(sql);
		try {
			while (res.next()) {
				txfirstname.setText(res.getString("firstname"));
				txlastname.setText(res.getString("lastname"));
				txphone.setText(res.getString("phone"));
				txcity.setText(res.getString("city"));
				txdob.setText(res.getString("dob"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
	}

	private void countd() {
		String sdate = ((JTextField) checkin.getDateEditor().getUiComponent()).getText();
		String vdate = ((JTextField) checkout.getDateEditor().getUiComponent()).getText();
		String napok = hh.countdays(sdate, vdate);
		txdaysnum.setText(napok);
		return;
	}

	public void passtocmb(int number) {
		try {
			dd.guestcombofill(cmbguest);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hh.setSelectedValue(cmbguest, number);
		cmbguest.updateUI();
	}

	public void passtocmbc(int number) {
		try {
			dd.costumercombofill(cmbcustomer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hh.setSelectedValue(cmbcustomer, number);
		cmbcustomer.updateUI();
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
				
				if (bb.getName()=="customer") {				
					Customer customer = (Customer) cmbcustomer.getSelectedItem();
					String ccustomer = customer.toString();					
					  ret = rvad.customervalid(ccustomer);		
				}  else  if (bb.getName()=="status") {	
					String status = (String) cmbstatus.getSelectedItem().toString();				
					  ret = rvad.statusvalid(status);		
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
			if (Txt == txroomno) {
				Txt.setText(content);
				ret = rvad.roomnovalid(content);
			} else if (Txt == txdaysnum) {
				Txt.setText(content);
				ret = rvad.daysnumvalid(content);
			} 		

			if (ret == true) {
				Txt.setBorder(hh.borderf);
			} else {
				Txt.setBorder(hh.borderp);
			}		
		}
	};
	 private Boolean rsvalidation(String roomno, String daysnum, String customer, String status) {
			Boolean ret = true;
			 ArrayList<String> err = new ArrayList<String>();
			 
			 if (!rvad.customervalid(customer)) {				
				 err.add(rvad.mess);			
		    	ret = false;
		    	}
			 if (!rvad.daysnumvalid(daysnum)) {
				 err.add(rvad.mess);			
			    	ret = false;
			}			
			 if (!rvad.roomnovalid(roomno)) {
				 err.add(rvad.mess);			
			    	ret = false;
			}			
			 if (!rvad.statusvalid(status)) {				
				 err.add(rvad.mess);			
		    	ret = false;
		    	}
			
			 if (err.size() > 0) {
	             JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
	         }			
			return ret;
		}

	public static void main(String args[]) {
		Custreservation res = new Custreservation();
		res.setSize(1230, 650);
		res.setLayout(null);
		res.setLocationRelativeTo(null);
		res.setVisible(true);
	}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		// textField.setHorizontalAlignment(JTextField.RIGHT)
		textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}

	JTable res_table, rooms_table;
	JPanel cardPanel, tPanel, ePanel, gePanel;
	CardLayout cards;
	JComboBox cmbcustomer, cmbstatus, cmbfreerooms, cmbsearch;
	JScrollPane resPane, roomPane;
	JLabel lbheader, lbcustomer, lbcheckin, lbcheckout, lbroomno, lbstatus, lbfreerooms, lbdaysnum, lbgheader;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnroomno, btnnewcust;
	JTextField txroomno, txroom_id, txdaysnum, txsearch;
	JLabel lbguestname, lbindate, lboutdate, lbguestroom, lbguest, lbsearch;
	JTextField txguest_id, txfirstname, txlastname, txphone, txcity, txdob;
	JLabel lbfirstname, lblastname, lbphone, lbcity, lbdob;
	JLabel lbpicture, lbpictureg;
	JButton btngsave, btngcancel, btngnewguest;
	JButton btngnew, btngupdate, btngdelete, btnsearch, btnclear;
}
