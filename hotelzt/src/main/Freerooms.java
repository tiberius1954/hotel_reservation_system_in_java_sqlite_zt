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

public class Freerooms extends JFrame {
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	JDateChooser begdate = new JDateChooser();
	JDateChooser enddate = new JDateChooser();

	Freerooms() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
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
		setTitle("Free rooms");
		tPanel = maketpanel();
		add(tPanel);
	}

	private JPanel maketpanel() {
		try {
			Date date;
			String sdate = hh.currentDate();
			date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
			begdate.setDate(date);
			enddate.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(4, 4, 766, 450);

		qpanel = new JPanel(null);
		qpanel.setBounds(200, 70, 365, 100);
		qpanel.setBorder(hh.line);

		lbheader = new JLabel("Free rooms in this period");
		lbheader.setBounds(30, 15, 400, 25);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);

		begdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		begdate.setDateFormatString("yyyy-MM-dd");
		begdate.setFont(new Font("Arial", Font.BOLD, 16));

		begdate.setBounds(30, 15, 150, 25);
		qpanel.add(begdate);

		lbmark = hh.clabel("- ");
		lbmark.setFont(new Font("Arial", Font.BOLD, 16));
		lbmark.setBounds(175, 15, 20, 25);
		qpanel.add(lbmark);

		enddate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		enddate.setDateFormatString("yyyy-MM-dd");
		enddate.setFont(new Font("Arial", Font.BOLD, 16));
		enddate.setBounds(200, 15, 140, 25);
		qpanel.add(enddate);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(140, 60, 90, 25);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		qpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
             table_update();
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
			new Object[][] { { null, null, null, null, null},
					{ null, null, null, null, null}, { null, null, null, null, null},
					{ null, null, null, null, null}, { null, null, null, null, null},
					{ null, null, null, null, null}, { null, null, null, null, null},
					{ null, null, null, null, null }, { null, null, null, null, null}, },
			new String[] { "Room_no","Floor", "Roomtype","Capacity","Price" }));

	hh.setJTableColumnsWidth(rooms_table, 600, 20, 20, 20, 20,20);
	roomPane.setViewportView(rooms_table);
	roomPane.setBounds(95, 190, 600, 230);
    ttpanel.add(roomPane);
	
	return ttpanel;
}
public void table_update() {
	String Sql;
	String bdate = ((JTextField) begdate.getDateEditor().getUiComponent()).getText();
	String edate = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();	
	Sql = "select  r.roomno,r.floor, t.name, t.capacity, t.price from rooms r "
			+ " join roomtype t on r.roomtype_id = t.type_id " 
			+ "	WHERE room_id not in ( "
			+ " select  room_id from reservations where " + "  (checkin >= date('" + bdate +"')"
			+ " and checkin < date('" + edate + "'))" + " or (checkout > date('" + bdate +"')"
			+ " and checkout < date('" + edate + "'))" + " or (checkin < date('" + bdate +"')"
			+ " and  checkout > date('" + edate + "')))";	
	
	ResultSet res = dh.GetData(Sql);
	rooms_table.setModel(DbUtils.resultSetToTableModel(res));
	dh.CloseConnection();
    String[] fej = { "Room_no","Floor", "Roomtype","Capacity","Price" };	
	((DefaultTableModel) rooms_table.getModel()).setColumnIdentifiers(fej);
	hh.setJTableColumnsWidth(rooms_table, 600,  20, 20, 20, 20,20);
	DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rooms_table.getDefaultRenderer(Object.class);
	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	rooms_table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
	rooms_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	rooms_table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
}
	public static void main(String args[]) {
		Freerooms ges = new Freerooms();
		ges.setSize(790, 500);
		ges.setLayout(null);
		ges.setLocationRelativeTo(null);
		ges.setVisible(true);
	}

	JPanel tPanel, qpanel;
	JLabel lbheader, lbmark;
	JButton btnsearch;
	JTable rooms_table;
	JScrollPane  roomPane;
}
