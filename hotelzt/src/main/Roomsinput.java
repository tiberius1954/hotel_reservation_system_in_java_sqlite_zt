package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import classes.Roomtype;
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Roomsinput extends JFrame {
	ResultSet result;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid="";
	private int myrow=0;

	Roomsinput() {
		initcomponents();
		try {
			dd.roomtypecombofill(cmbroomtype);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
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
		setTitle("Rooms");

		lbheader = new JLabel("Rooms");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);

		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 1220, 480);
		bPanel.setBackground(new Color(230, 231, 232));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 400, 440);
		ePanel.setBorder(hh.line);
		tPanel = new JPanel(null);
		tPanel.setBounds(411, 10, 800, 440);
		tPanel.setBorder(hh.line);
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);

		lbroomno = hh.clabel("Room number");
		lbroomno.setBounds(20, 20, 130, 20);
		ePanel.add(lbroomno);

		txroomno = cTextField(25);
		txroomno.setBounds(160, 20, 200, 25);
		ePanel.add(txroomno);

		lbfloor = hh.clabel("Floor");
		lbfloor.setBounds(20, 60, 130, 20);
		ePanel.add(lbfloor);

		txfloor = cTextField(25);
		txfloor.setBounds(160, 60, 200, 25);
		txfloor.setHorizontalAlignment(JTextField.RIGHT);
		ePanel.add(txfloor);
	
		lbroomtype = hh.clabel("Roomtype");
		lbroomtype.setBounds(20, 100, 130, 20);
		ePanel.add(lbroomtype);

		cmbroomtype = hh.cbcombo();
	    cmbroomtype.setName("roomtype");
		cmbroomtype.setBounds(160, 100, 200, 25);
		ePanel.add(cmbroomtype);

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(20, 140, 130, 20);
		ePanel.add(lbstatus);

		cmbstatus = hh.cbcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(new String[] { "", "Ready", "Reserved", "Occupied" }));
		cmbstatus.setBounds(160, 140, 200, 25);
		ePanel.add(cmbstatus);

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(190, 200, 130, 30);
		btnsave.setBackground(hh.svoros);
		ePanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(190, 240, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(190, 280, 130, 30);
		btndelete.setBackground(hh.skek);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(rom_table, "delete from rooms  where room_id =");	
			}
		});

		rom_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rom_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		rom_table.setTableHeader(new JTableHeader(rom_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rom_table);
		rom_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rom_table.scrollRectToVisible(rom_table.getCellRect(rom_table.getRowCount() - 1, 0, true));
			}
		});
		rom_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		jScrollPane1 = new JScrollPane(rom_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rom_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null,null},
						{ null, null, null, null, null,null }, { null, null, null, null, null,null},
						{ null, null, null, null, null,null}, { null, null, null, null, null,null},
						{ null, null, null, null, null,null}, { null, null, null, null, null,null } },
				new String[] { "Room_id", "Roomno", "Floor","Rtype_id","Roomtype", "Status" }));
		hh.setJTableColumnsWidth(rom_table, 780, 0, 25, 25, 0, 25, 25);
		jScrollPane1.setViewportView(rom_table);
		jScrollPane1.setBounds(10, 10, 780, 400);
		tPanel.add(jScrollPane1);
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		String status = (String) cmbstatus.getSelectedItem();	
		int roomtype_id = ((Roomtype) cmbroomtype.getSelectedItem()).getType_id();
		String typename =((Roomtype) cmbroomtype.getSelectedItem()).getName();
		String roomno = txroomno.getText();
		String floor = txfloor.getText();	

		if (hh.zempty(roomno) || hh.zempty(floor) || roomtype_id<=0) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  rooms set roomno= '" + roomno + "', floor= '" + floor + "'," 
					+  " roomtype_id = " +roomtype_id + "," + "status = '" + status + "' where room_id = "
					+ rowid;
		} else {
			sql = "insert into rooms (roomno,floor,roomtype_id,status) " + "values ('"
					+ roomno + "','" + floor + "'" + "," + roomtype_id + ",'" + status +"')";
		}
		try {
			int flag = dh.Insupdel(sql);

			if (flag == 1) {		
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {			
					table_rowrefresh(roomno, floor, roomtype_id, typename, status);
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
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = rom_table.getSelectedRow();
		if (row >= 0) {	
			txroomno.setText(rom_table.getValueAt(row, 1).toString());
			txfloor.setText(rom_table.getValueAt(row, 2).toString());			
			String cnum = rom_table.getValueAt(row, 3).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbroomtype, number);
			cmbroomtype.updateUI();
			cmbstatus.setSelectedItem(rom_table.getValueAt(row, 5).toString());
			rowid = rom_table.getValueAt(row, 0).toString();
			myrow = row;
		}
	}
	void table_rowrefresh(String roomno, String floor, int roomtype_id, String typename, String status){
		DefaultTableModel d1 = (DefaultTableModel) rom_table.getModel();
		d1.setValueAt(roomno, myrow, 1);
		d1.setValueAt(floor, myrow, 2);	
		String stype_id = hh.itos(roomtype_id);
		d1.setValueAt(stype_id, myrow, 3);
		d1.setValueAt(typename, myrow, 4);
		d1.setValueAt(status, myrow, 5);
	}
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select r.room_id, r.roomno, r.floor, r.roomtype_id, t.name, r.status from rooms r "
					+ "join roomtype t on r.roomtype_id = t.type_id  ";
		} else {
			Sql = "select r.room_id, r.roomno, r.floor, r.roomtype_id, t.name, r.status from rooms r "
					+ "join roomtype t on r.roomtype_id = t.type_id  where ";
		}
		ResultSet res = dh.GetData(Sql);
		rom_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		 String[] fej = { "Room_id", "Roomno", "Floor","Rtype_id","Roomtype", "Status" };
		((DefaultTableModel) rom_table.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(rom_table, 780, 0,  25, 25, 0, 25, 25);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rom_table.getDefaultRenderer(Object.class);		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		rom_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
	}

	private void clearFields() {
		cmbroomtype.setSelectedIndex(-1);
		cmbstatus.setSelectedIndex(-1);	
		txroomno.setText("");
		txfloor.setText("");	
		txroomno.requestFocus();
		rom_table.clearSelection();
		rowid = "";
		myrow = 0;
	}	

	public static void main(String args[]) {
		Roomsinput rom = new Roomsinput();
		rom.setSize(1250, 550);
		rom.setLayout(null);
		rom.setLocationRelativeTo(null);
		rom.setVisible(true);
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

	JTable rom_table;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel;
	JScrollPane jScrollPane1;
	JPanel bPanel, tPanel, ePanel;	
	JTextField txfirstname;
	JLabel lbheader, lbroomno, lbfloor,  lbroomtype, lbstatus;
	JTextField txroom_id, txroomno, txfloor;
	JComboBox cmbroomtype, cmbstatus;
}
