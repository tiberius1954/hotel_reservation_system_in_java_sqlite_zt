package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import classes.Guest;
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Guesthistory extends JFrame {
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	JDateChooser begdate = new JDateChooser();
	JDateChooser enddate = new JDateChooser();

	Guesthistory() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		try {		
			dd.guestcombofill(cmbguest);
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

		setTitle("Guest history");
		tPanel = maketpanel();
		add(tPanel);	
	}

	private JPanel maketpanel() {
		String thisYear = new SimpleDateFormat("yyyy").format(new Date());
		try {
		Date date;
		String dd = thisYear +"-01-01";
		date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
		begdate.setDate(date);
		dd = thisYear +"-12-31";
		date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
		enddate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JPanel  ttpanel = new JPanel(null);	
		
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(4,4, 766, 450);
		lbheader = new JLabel("Guest history");
		lbheader.setBounds(30, 10, 110, 25);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);
		
		qpanel = new JPanel(null);
		qpanel.setBounds(160,50,455,150);
		qpanel.setBorder(hh.line);	
		
		lbguest= hh.clabel("Guest");
		lbguest.setBounds(20, 20, 50, 25);
		lbguest.setFont(new Font("tahoma", Font.BOLD, 16));
		qpanel.add(lbguest);
		
		cmbguest = hh.cbcombo();
		cmbguest.setName("guest");
		cmbguest.setBounds(80, 20, 350, 25);
		qpanel.add(cmbguest);
		
		lbterm = hh.clabel("Term");
		lbterm.setBounds(20, 60, 50, 25);
		qpanel.add(lbterm);

		begdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		begdate.setDateFormatString("yyyy-MM-dd");
		begdate.setFont(new Font("Arial", Font.BOLD, 16));
		begdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});
		begdate.setBounds(90, 60, 160, 25);
		qpanel.add(begdate);
		
		lbmark = hh.clabel("- ");
		lbmark.setFont(new Font("Arial", Font.BOLD, 16));
		lbmark.setBounds(245, 60, 20, 25);
		qpanel.add(lbmark);
		
		enddate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		enddate.setDateFormatString("yyyy-MM-dd");
		enddate.setFont(new Font("Arial", Font.BOLD, 16));
		enddate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});
		enddate.setBounds(270, 60, 150, 25);
		qpanel.add(enddate);
		
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(200, 110, 90, 25);	
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		qpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int rid = ((Guest) cmbguest.getSelectedItem()).getGuest_id();
				String srid = hh.itos(rid);		
				if (rid > 0) {
						table_update( srid);	
						}
			}
		});		
		ttpanel.add(qpanel);	
		
		
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
				new Object[][] { { null, null, null, null, null, null},
						{ null, null, null, null, null, null}, { null, null, null, null, null, null },
						{ null, null, null, null, null, null}, { null, null, null, null, null, null},
						{ null, null, null, null, null, null }, { null, null, null, null, null, null}, },
				new String[] { "res_id", "Guest_id", "Room_no","In_date", "Out_date","Customer" }));

		hh.setJTableColumnsWidth(rooms_table, 600, 0, 0, 20, 20, 20, 40);
		roomPane.setViewportView(rooms_table);
		roomPane.setBounds(95, 230, 600, 180);
        ttpanel.add(roomPane);
		
		return ttpanel;
	}
	public void table_update(String id) {
		String Sql;
		String bdate = ((JTextField) begdate.getDateEditor().getUiComponent()).getText();
		String edate = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();

		Sql = "select  g.res_id, g.guest_id , i.roomno,  g.in_date, g.Out_date, c.firstname || ' ' || c.lastname as custname "
		+ "from guestreservation g join reservations r on g.res_id =  r.res_id "  
		+ "join rooms i on r.room_id = i.room_id join customers c on  r.cust_id = c.cust_id where g.guest_id ='"+id+"'"
		+ " and ((in_date >= date('" + bdate +"') "
		+ "	 and in_date < date('" + edate + "')) or (out_date > date('" + bdate +"')"
		+ " and out_date < date('" + edate + "')) or (in_date < date('" + bdate +"')"
		+ " and  out_date > date('" + edate + "')))";
		ResultSet res = dh.GetData(Sql);
		rooms_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Res_id", "Guest_id", "Room_no","In_date", "Out_date","Customer" };
		((DefaultTableModel) rooms_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(rooms_table, 600, 0, 0, 20, 20, 20, 40);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rooms_table.getDefaultRenderer(Object.class);
	}

	public static void main(String args[]) {
		Guesthistory ges = new Guesthistory();
		ges.setSize(790, 500);
		ges.setLayout(null);
		ges.setLocationRelativeTo(null);
		ges.setVisible(true);
	}
	JScrollPane  roomPane;
	JTable rooms_table;
	JPanel tPanel, qpanel;
	JLabel lbheader, lbterm, lbmark, lbguest;
	JComboBox cmbguest;
	JButton btnsearch;

}
