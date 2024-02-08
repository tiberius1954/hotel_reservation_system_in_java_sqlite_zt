package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
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
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Currentlyguest extends JFrame{
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	JDateChooser qdate = new JDateChooser();	
	 Currentlyguest(){
		 initcomponents();
			this.getContentPane().setBackground(new Color(241, 241, 242));
		 
	 }
	 private void initcomponents(){
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
			setTitle("List all guests currently staying in the hotel");
			tPanel = maketpanel();		
			add(tPanel);			
			
	 }
	 private JPanel maketpanel() {		
			try {
			Date date;
			String sdate = hh.currentDate();
			date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			qdate.setDate(date);				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			JPanel  ttpanel = new JPanel(null);				
			ttpanel.setBorder(hh.line);
			ttpanel.setBackground(new Color(230, 231, 232));
			ttpanel.setBounds(4,4, 766, 450);
			
			qpanel = new JPanel(null);
			qpanel.setBounds(190,70,350,55);
			qpanel.setBorder(hh.line);
			
			lbheader = new JLabel("List all guests currently staying in the hotel");
			lbheader.setBounds(30, 15, 400, 25);
			lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
			ttpanel.add(lbheader);
			
			qdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
			qdate.setDateFormatString("yyyy-MM-dd");
			qdate.setFont(new Font("Arial", Font.BOLD, 16));
		
			qdate.setBounds(30, 15, 160, 25);
			qpanel.add(qdate);
			
			btnsearch = hh.cbutton("Filter");
			btnsearch.setForeground(Color.black);
			btnsearch.setBackground(Color.ORANGE);
			btnsearch.setBorder(hh.borderf2);
			btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
			btnsearch.setBounds(210, 15, 120, 27);
			btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			btnsearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {				
					table_update();
				}
				});
			qpanel.add(btnsearch);			
			ttpanel.add(qpanel);
			
			guest_table = hh.ztable();
			DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) guest_table.getDefaultRenderer(Object.class);
			rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
			guest_table.setTableHeader(new JTableHeader(guest_table.getColumnModel()) {
				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 25;
					return d;
				}
			});

			hh.madeheader(guest_table);
			roomPane = new JScrollPane(guest_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			guest_table.setModel(new javax.swing.table.DefaultTableModel(
					new Object[][] { { null, null, null, null, null, null},
							{ null, null, null, null, null, null}, { null, null, null, null, null, null },
							{ null, null, null, null, null, null}, { null, null, null, null, null, null},
							{ null, null, null, null, null, null }, { null, null, null, null, null, null}, },
					new String[] { "Guest", "Room_no","In_date", "Out_date" }));
			hh.setJTableColumnsWidth(guest_table,600, 40,20,20,20);
			roomPane.setViewportView(guest_table);
			roomPane.setBounds(95, 150, 600, 270);
	        ttpanel.add(roomPane);
			return ttpanel;
	 }
	 public void table_update() {
			String Sql;
			String currdate = ((JTextField) qdate.getDateEditor().getUiComponent()).getText();
			Sql = "select  c.firstname || ' ' || c.lastname as guestname,  i.roomno,  g.in_date, g.Out_date "
			+ "from guestreservation g join reservations r on g.res_id =  r.res_id "  
			+ "join rooms i on r.room_id = i.room_id join guests c on  g.guest_id = c.guest_id where "
			+ " g.in_date <= date('" + currdate + "') and g.out_date >=date('" + currdate + "')";
			ResultSet res = dh.GetData(Sql);
			guest_table.setModel(DbUtils.resultSetToTableModel(res));
			dh.CloseConnection();
			String[] fej = { "Guest", "Room_no","In_date", "Out_date" };
			((DefaultTableModel) guest_table.getModel()).setColumnIdentifiers(fej);
			hh.setJTableColumnsWidth(guest_table, 600, 40, 20, 20, 20);
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) guest_table.getDefaultRenderer(Object.class);
	 }
	
	public static void main(String args[]) {
		Currentlyguest ges = new Currentlyguest();
		ges.setSize(790, 500);
		ges.setLayout(null);
		ges.setLocationRelativeTo(null);
		ges.setVisible(true);
	}
	
	JLabel lbheader;
	JScrollPane  roomPane;
	JTable guest_table;
	JPanel tPanel, qpanel;
	JButton btnsearch;
}
