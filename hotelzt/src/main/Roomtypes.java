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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
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
import classes.hhelper;
import net.proteanit.sql.DbUtils;

public class Roomtypes extends JFrame {
	ResultSet res;	
	 Connection con = null;
	 PreparedStatement pst = null;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;

	Roomtypes() {
		initcomponents();
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
		setTitle("Roomtypes");

		lbheader = new JLabel("Roomtypes");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 830, 340);
		bPanel.setBackground(new Color(241, 241, 242));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 350, 310);
		ePanel.setBorder(hh.line);
		tPanel = new JPanel(null);
		tPanel.setBounds(370, 10, 450, 310);
		tPanel.setBorder(hh.line);
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 80, 20);
		ePanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(100, 20, 200, 25);
		ePanel.add(txname);

		lbprice = hh.clabel("Price");
		lbprice.setBounds(10, 70, 80, 20);
		ePanel.add(lbprice);

		txprice = cTextField(25);
		txprice.setHorizontalAlignment(JTextField.RIGHT);
		txprice.setBounds(100, 70, 200, 25);
		ePanel.add(txprice);
		txprice.addKeyListener(hh.Onlynum());

		lbcapacity = hh.clabel("Capacity");
		lbcapacity.setBounds(10, 120, 80, 20);
		ePanel.add(lbcapacity);

		txcapacity = cTextField(25);
		txcapacity.setHorizontalAlignment(JTextField.RIGHT);
		txcapacity.setBounds(100, 120, 200, 25);
		ePanel.add(txcapacity);
		txcapacity.addKeyListener(hh.Onlynum());

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(130, 170, 130, 30);
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
		btncancel.setBounds(130, 210, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(130, 250, 130, 30);
		btndelete.setBackground(hh.skek);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(rtype_table, "delete from roomtype  where type_id =");
			}
		});
		rtype_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rtype_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		rtype_table.setTableHeader(new JTableHeader(rtype_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rtype_table);

		rtype_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rtype_table.scrollRectToVisible(rtype_table.getCellRect(rtype_table.getRowCount() - 1, 0, true));
			}
		});
		rtype_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		jScrollPane1 = new JScrollPane(rtype_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rtype_table
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Type_id", "Name", "Price", "Capacity" }));
		hh.setJTableColumnsWidth(rtype_table, 420, 0, 40, 30, 30);
		jScrollPane1.setViewportView(rtype_table);
		jScrollPane1.setBounds(10, 10, 420, 250);
		tPanel.add(jScrollPane1);
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";

		String name = txname.getText();
		String price =  hh.bsf(txprice.getText());
		String capacity = txcapacity.getText();

		if (hh.zempty(name) || hh.zempty(price) || hh.zempty(capacity)) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  roomtype set name= '" + name + "', price= '" + price + "'," + "capacity = '" + capacity
					+ "' where type_id = " + rowid;
		} else {
			sql = "insert into roomtype (name,price,capacity) " + "values ('" + name + "','" + price + "'" + ",'"
					+ capacity + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(name, price, capacity);
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

	void table_rowrefresh(String name, String price, String capacity) {
		DefaultTableModel d1 = (DefaultTableModel) rtype_table.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(price, myrow, 2);
		d1.setValueAt(capacity, myrow, 3);
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = rtype_table.getSelectedRow();
		if (row >= 0) {
			txname.setText(rtype_table.getValueAt(row, 1).toString());
			txprice.setText(rtype_table.getValueAt(row, 2).toString());
			txcapacity.setText(rtype_table.getValueAt(row, 3).toString());
			rowid = rtype_table.getValueAt(row, 0).toString();
			myrow = row;
		}
	}

	private void table_update(String what) {
		DefaultTableModel model = (DefaultTableModel) rtype_table.getModel();
		model.setRowCount(0);
		String Sql;
		if (what == "") {
			Sql = "select  type_id, name, price, capacity from roomtype";
		} else {
			Sql = "select * from roomtype where ";
		}
		try {
			  con = dh.getConnection();
			  pst = con.prepareStatement(Sql);
	          res = pst.executeQuery();	
				while (res.next()) {
					String type_id= res.getString("type_id");
					String name= res.getString("name");
					String ss = res.getString("price");
					String price = hh.bsf(ss);
					String capacity= res.getString("capacity");						
				model.addRow(new Object[] { type_id, name,  price, capacity });			
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
		String[] fej = { "Type_id", "Name", "Price", "Capacity" };
		((DefaultTableModel) rtype_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(rtype_table, 420, 0, 40, 30, 30);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rtype_table.getDefaultRenderer(Object.class);	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		rtype_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		rtype_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	}

	private void clearFields() {
		txname.setText("");
		txprice.setText("");
		txcapacity.setText("");
		txname.requestFocus();	
		rowid = "";
		myrow = 0;
	}

	public static void main(String args[]) {
		Roomtypes rom = new Roomtypes();
		rom.setSize(870, 450);
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

	JLabel lbheader, lbtype_id, lbname, lbprice, lbcapacity;
	JTextField txtype_id, txname, txprice, txcapacity;
	JScrollPane jScrollPane1;
	JPanel bPanel, tPanel, ePanel;
	JTable rtype_table;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel;
}
