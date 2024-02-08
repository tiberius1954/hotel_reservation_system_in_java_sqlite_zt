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

public class Servicetypes  extends JFrame {	
	ResultSet res;
	 Connection con = null;
	 PreparedStatement pst = null;
	hhelper hh = new hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid="";
	private int myrow=0;
	private String sfrom = "";
	private JFrame pframe;
	
	Servicetypes(JFrame parent) {
		sfrom = "servfrom";
		pframe = parent;
		System.out.print(sfrom);
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		table_update("");			
	}
	
	Servicetypes(){
		System.out.print("no adat");
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		table_update("");	
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
				if (hh.zempty(sfrom)) {			
				dispose();
			}else {
				pframe.setVisible(true);			
				dispose();
			}
				}
		});
		setTitle("Servicetypes");

		lbheader = new JLabel("Servicetypes");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 830, 340);
		bPanel.setBackground(new Color(241, 241, 242));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 350, 320);
		ePanel.setBorder(hh.line);
		tPanel = new JPanel(null);
		tPanel.setBounds(370, 10, 450, 320);
		tPanel.setBorder(hh.line);
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 100, 20);
		ePanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(120, 20, 200, 25);
		ePanel.add(txname);
		
		lbprice = hh.clabel("Price");
		lbprice.setBounds(10, 60, 100, 20);
		ePanel.add(lbprice);

		txprice = cTextField(25);
		txprice.setHorizontalAlignment(JTextField.RIGHT);
		txprice.setBounds(120,60, 200, 25);
		ePanel.add(txprice);
		txprice.addKeyListener(hh.Onlynum());
		
		lbunit = hh.clabel("Unit");
		lbunit.setBounds(10, 100, 100, 20);
		ePanel.add(lbunit);

		txunit = cTextField(25);
		txunit.setBounds(120,100, 200, 25);
		ePanel.add(txunit);
		
		lbdesc = hh.clabel("Description");
		lbdesc.setBounds(10, 140, 100, 20);
		ePanel.add(lbdesc);

		txdesc = cTextField(25);
		txdesc.setBounds(120,140, 200, 25);
		ePanel.add(txdesc);
		
		btnsave = hh.cbutton("Save");
		btnsave.setBounds(150, 185, 130, 30);
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
		btncancel.setBounds(150, 225, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(150, 265, 130, 30);
		btndelete.setBackground(hh.skek);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				serdata_delete();	
			}
		});
		
		stype_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) stype_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		stype_table.setTableHeader(new JTableHeader(stype_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});	

		hh.madeheader(stype_table);

		stype_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stype_table.scrollRectToVisible(stype_table.getCellRect(stype_table.getRowCount() - 1, 0, true));
			}
		});
		stype_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		jScrollPane1 = new JScrollPane(stype_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		stype_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null,null },
						{ null, null, null, null,null}, { null, null, null, null,null},
						{ null, null, null, null,null}, { null, null, null, null,null },
						{ null, null, null, null,null }, { null, null, null, null,null} },
				new String[] { "Type_id", "Name", "Price", "Unit","Description" }));
		hh.setJTableColumnsWidth(stype_table, 420, 0, 35, 15, 15,35);		
		jScrollPane1.setViewportView(stype_table);
		jScrollPane1.setBounds(10, 10, 420, 250);
		tPanel.add(jScrollPane1);	
		
		btnsendto = hh.cbutton("Send to services");
		btnsendto.setBounds(160, 275, 150, 30);
		btnsendto.setBackground(hh.narancs);

	if (sfrom == "servfrom") {
			tPanel.add(btnsendto);
	}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});
	}
	
	private void savebuttrun() {
		String sql = "";
		String jel = "";

		String name = txname.getText();
		String price = hh.bsf(txprice.getText());
		String unit = txunit.getText();
		String sdesc = txdesc.getText();

		if (hh.zempty(name) || hh.zempty(unit)) {
			JOptionPane.showMessageDialog(null, "Please fill name and unit Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  servicetype set name= '" + name + "', price= '" + price + "'," + "unit = '" + unit +"',"
					+ " description='" + sdesc +"' where stype_id = " + rowid;
		} else {
			sql = "insert into servicetype (name,price,unit, description) " + "values ('" + name + "','" + price + "'" + ",'"
					+ unit + "','" + sdesc+"')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(name, price, unit, sdesc);
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

	void table_rowrefresh(String name, String price, String unit , String sdesc) {
		DefaultTableModel d1 = (DefaultTableModel) stype_table.getModel();
		d1.setValueAt(name, myrow, 1);
		d1.setValueAt(price, myrow, 2);
		d1.setValueAt(unit, myrow, 3);
		d1.setValueAt(sdesc, myrow, 4);
	}
	 private void table_update(String what) {	
		DefaultTableModel model = (DefaultTableModel) stype_table.getModel();
		model.setRowCount(0);
			String Sql;
			if (what == "") {
				Sql = "select  stype_id, name, price, unit, description from servicetype order by name";
			} else {
				Sql = "select * from servicetype where ";
			}
			try {
			  con = dh.getConnection();
			  pst = con.prepareStatement(Sql);
	          res = pst.executeQuery();	
				while (res.next()) {
					String stype_id= res.getString("stype_id");
					String name= res.getString("name");
					String ss = res.getString("price");
					String price = hh.bsf(ss);
					String unit= res.getString("unit");
					String description= res.getString("description");				
				model.addRow(new Object[] { stype_id, name,  price, unit, description });			
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
			 String[] fej = { "Type_id", "Name", "Price", "Unit","Description" };
			((DefaultTableModel) stype_table.getModel()).setColumnIdentifiers(fej);	
			hh.setJTableColumnsWidth(stype_table, 420, 0, 35, 15, 15,35);
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) stype_table.getDefaultRenderer(Object.class);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
			stype_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
	//		stype_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
			stype_table.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					stype_table.scrollRectToVisible(stype_table.getCellRect(stype_table.getRowCount() - 1, 0, true));
				}
			});
//			if (stype_table.getRowCount() > 0) {
//				int row = stype_table.getRowCount() - 1;
//				stype_table.setRowSelectionInterval(row, row);
//			}
		}
	 
	 private void tableMouseClicked(java.awt.event.MouseEvent evt) {
			int row = stype_table.getSelectedRow();
			if (row >= 0) {	
				txname.setText(stype_table.getValueAt(row, 1).toString());
				txprice.setText(stype_table.getValueAt(row, 2).toString());
				txunit.setText(stype_table.getValueAt(row, 3).toString());	
				txdesc.setText(stype_table.getValueAt(row, 4).toString());
				rowid = stype_table.getValueAt(row, 0).toString();
				myrow = row;
			}
		}

		private void clearFields() {			
			txname.setText("");
			txprice.setText("");
			txunit.setText("");
			txdesc.setText("");
			txname.requestFocus();	
			rowid = "";
			myrow = 0;
		}
		private int serdata_delete() {
			String sql ="delete from servicetype  where stype_id =";		
		
			int flag = 0;
			DefaultTableModel d1 = (DefaultTableModel) stype_table.getModel();
			int sIndex = stype_table.getSelectedRow();
			if (sIndex < 0) {
				return flag;
			}
			String iid = d1.getValueAt(sIndex, 0).toString();
			if (iid.equals("")) {
				return flag;
			}
			if (dd.cannotdelete("select stype_id from service  where stype_id ="+ iid)==true) {
				 JOptionPane.showMessageDialog(null, "You can not delete this item !");
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
		
		private void data_send() {
			DefaultTableModel d1 = (DefaultTableModel) stype_table.getModel();
			int row = stype_table.getSelectedRow();
			int number = 0;
			String cnum = "";
			if (row > -1) {
				cnum = d1.getValueAt(row, 0).toString();
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				((Servicesinput) pframe).passtocmb(number,txprice.getText(), txunit.getText());			
				pframe.setVisible(true);
				// setVisible(false);
				dispose();
			}
		}		
	
	public static void main(String args[]) {
		Servicetypes rom = new Servicetypes();
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
		// textField.setHorizontalAlignment(JTextField.RIGHT)
		// textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}

JLabel lbheader, lbstype_id,lbname,lbprice,lbunit,lbdesc;
JTextField txstype_id, txname, txprice, txunit,txdesc;
JScrollPane jScrollPane1;
JPanel bPanel, tPanel, ePanel;	
JTable stype_table;
JButton btnnew, btndelete, btnupdate, btnsave, btncancel,btnsendto;
}
