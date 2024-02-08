package classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class hhelper {
	public LineBorder line = new LineBorder(Color .blue, 1, true);
	public LineBorder linep = new LineBorder(Color .red, 1, true);
	public Border borderz = BorderFactory.createLineBorder(Color.GREEN, 2);
	public Border borderp = BorderFactory.createLineBorder(Color.RED, 2);
	public Border borderf = BorderFactory.createLineBorder( Color .BLACK, 1);
	public Border borderf2 = BorderFactory.createLineBorder( Color .BLACK, 2);
	
	public Color   tablavszurke = new Color (220, 222, 227);
	public Color  tegla = new Color (249, 123, 62);
	public Color  cian = new  Color (0, 153, 102);
	public Color  kzold = new  Color (112, 171, 105);
	public Color  vpiros = new Color (255, 91, 30);
	public Color  svoros = new Color (199, 17, 17);
   public Color  vzold = new Color (0, 141, 54);
   public Color  vvzold = new Color (85,240,86);
	public Color  szold = new Color (12, 78, 12);
	public Color  piros = new Color (249, 73, 58);
	public Color  kekes = new Color (21, 44, 82);
	public Color  vkek = new Color (151, 202, 245);
	public Color  vvkek = new Color (225, 234, 243);
	public Color  vvlilla = new Color (127, 123, 242);
	public Color  vvvkek = new Color (175, 201, 207);
	 public Color  narancs = new Color (254, 179, 0);
	public Color  homok = new Color (200, 137, 60);
	public Color  vbarna = new Color (223, 196, 155);
	public Color  narancs1 = new Color (251, 191, 99);
	public Color  lnarancs = new Color (255, 142, 1);
	public Color  slilla = new  Color (204, 0, 153);	
	public Color  feher = new Color (255,255,255);
	 public Color  sarga = new Color (252,210,1);
	 public Color  skek = new Color (1,1,99);
	
	 public Font textf = new Font("Tahoma", Font.BOLD, 16);
	 public Font textf1 = new Font("Tahoma", Font.BOLD, 12);
	
	public void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
		double total = 0;
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			total += percentages[i];
		}
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			if (percentages[i] > 0.0) {
				int seged = (int) (tablePreferredWidth * (percentages[i] / total));
				column.setPreferredWidth(seged);
			} else {
				// column.setPreferredWidth(0);
				column.setMinWidth(0);
				column.setMaxWidth(0);
				column.setWidth(0);
			}
		}
	}
	public int stoi(String szam) {
		int seged = 0;
		try {
		if (!zempty(szam)) {
			seged = Integer.parseInt(szam);
		}
		} catch (Exception e) {
			System.out.println("Error ! string to int convert !!!");
		}
		return seged;
	}
	public Boolean zempty(String itext) {
	     Boolean log=false;
	     if(itext ==null || itext.isEmpty() || itext.trim().isEmpty()) {
	    	 log = true;
	     }
	     return log;
	 }
	public static void setSelectedValue(JComboBox comboBox, int value)
    {		
		 Customer customer;
	     Guest guest;
	     Roomtype roomtype;
	     Servicetype servicetype;
		
	    if("customer".equals(comboBox.getName())){
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            customer = ( Customer)comboBox.getItemAt(i);
            if (customer.getCust_id() == value)
            {
                comboBox.setSelectedIndex(i);
                break;
            }          
        }
	    } else if("guest".equals(comboBox.getName())) {
	    	 for (int i = 0; i < comboBox.getItemCount(); i++)
	         {
	             guest = ( Guest)comboBox.getItemAt(i);
	             if (guest.getGuest_id() == value)
	             {
	                 comboBox.setSelectedIndex(i);
	                 break;
	             }          
	         }	    	
	    }else if("roomtype".equals(comboBox.getName())) { 
	    	 for (int i = 0; i < comboBox.getItemCount(); i++)
	         {
	             roomtype= (Roomtype) comboBox.getItemAt(i);
	             if (roomtype.getType_id() == value)
	             {
	                 comboBox.setSelectedIndex(i);
	                 break;
	             }          
	         }	
	    } else {
	    	 for (int i = 0; i < comboBox.getItemCount(); i++)
	         {
	             servicetype= (Servicetype) comboBox.getItemAt(i);
	             if (servicetype.getStype_id() == value)
	             {
	                 comboBox.setSelectedIndex(i);
	                 break;
	             }          
	         }		    	
	    }
    }

	 public String currentDate() {
			LocalDate date = LocalDate.now();
			String sdate = date.format(DateTimeFormatter.ISO_DATE);
			return sdate;
		}
	 public String currenttime() {
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	     LocalTime time = LocalTime.now(); 		
	    String stime = time.format(formatter);
	    return stime;
	}
	 
	 public JTable ztable() {
			JTable tabla = new JTable();
			tabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color (0, 0, 0)));	
			tabla.setSelectionForeground(Color .BLACK);
			tabla.setSelectionBackground(vvzold);
			tabla.setForeground( Color .black);
			tabla.setRowHeight(25);
			tabla.setGridColor(new Color (0, 0, 0));
			tabla.setFont(new Font("tahoma", Font.BOLD, 12));
			tabla.setAutoscrolls(true);
			// to make a JTable non-editable
			tabla.setDefaultEditor(Object.class, null);
			tabla.setFocusable(false);
			tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabla.getTableHeader().setReorderingAllowed(false);
			return tabla;
		}

		public JTableHeader madeheader(JTable table) {
			JTableHeader header = table.getTableHeader();
			header.setBorder(new LineBorder(Color .BLACK, 1));
			Font font = new Font("tahoma", Font.BOLD, 12);
			header.setFont(font);
			header.setBackground(svoros);
			header.setForeground(Color .white);
			table.getTableHeader().setReorderingAllowed(false);		
			return header;
		}
		
		public JButton cbutton(String string) {
			JButton bbutton = new JButton(string);	
			bbutton.setFont(new Font("Tahoma", Font.BOLD, 16));	
			bbutton.setForeground(new Color (255, 255, 255));		
			bbutton.setPreferredSize(new Dimension(100, 30));
			bbutton.setMargin(new Insets(10, 10, 10, 10));
			bbutton.setFocusable(false);
			bbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));	
			bbutton.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.GRAY));
			return bbutton;
		}
		public JLabel clabel(String string) {
			JLabel llabel = new JLabel(string);	
			llabel.setFont(new Font("Tahoma", 1, 16));
			llabel.setForeground(new Color (0, 0, 0));	
			llabel.setPreferredSize(new Dimension(120, 30));
			llabel.setHorizontalAlignment(JLabel.RIGHT);
			return llabel;
		}
		public JComboBox cbcombo() {
			JComboBox ccombo = new JComboBox();	
			ccombo.setFont(textf1);
			ccombo.setBorder(borderf);		
			ccombo.setBackground(feher);
			ccombo.setPreferredSize(new Dimension(250, 30));
			ccombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			ccombo.setSelectedItem("");		
			return ccombo;
		}
		public String countdays(String startdate, String enddate) {
			String days = "";
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate sdate = LocalDate.parse(startdate, formatter);
				LocalDate vdate = LocalDate.parse(enddate, formatter);
				long ldays = ChronoUnit.DAYS.between(sdate, vdate);				
				days = Long.toString(ldays);
			} catch (Exception e) {
				 System.err.println("date error");
			}
			return days;
		}
		public LocalDate stringtodate(String indate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate date = LocalDate.parse(indate,formatter);		
			return date;
		}
		
		public String itos(int szam) {
			String ss = "";
			try {
				ss = Integer.toString(szam);
			} catch (Exception e) {
				System.out.println("itos hiba");
			}
			return ss;
		}
		
		public boolean twodate(String startdate, String enddate) {	
			String mess="";
			try {				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = simpleDateFormat.parse(startdate);
				Date date2 = simpleDateFormat.parse(enddate);
				if (date2.compareTo(date1) < 0) {
					mess = " startdate bigger then enddate !";
					JOptionPane.showMessageDialog(null, mess,"Alert", JOptionPane.ERROR_MESSAGE);			
					return false;
				}
			} catch (Exception e) {
				mess = "Date error!";
				return false;
			}
			return true;
		}
		
		public void ztmessage(String mess, String header) {
			JOptionPane op = new JOptionPane(mess, JOptionPane.INFORMATION_MESSAGE);
			final JDialog dialog = op.createDialog(header);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					dialog.setVisible(false);
					dialog.dispose();
				}
			}, 1000);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setAlwaysOnTop(true);
			dialog.setModal(false);
			dialog.setVisible(true);
		}	
		public KeyListener MUpper() {
			KeyListener keyListener= new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			       JTextField tf = (JTextField) e.getSource();
			   	if (tf.getText().length() == 0) { 
					   if (Character.isLowerCase(c)) 				
					       e.setKeyChar(Character.toUpperCase(c));				
					}}	    
			    };			 		
		return keyListener;
		}
		public KeyListener Onlydate() {
			KeyListener keyListener= new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      JTextField tf = (JTextField) e.getSource();
				   	if (tf.getText().length() > 9) { 
				   		e.consume();
				   	}
			      if (c == '-') {
			      }else  if (!((c >= '0') && (c <= '9') ||
			         (c == KeyEvent.VK_BACK_SPACE) ||
			         (c == KeyEvent.VK_DELETE))) {			    
			        e.consume();
			      }
			    }};			    	 		
		return keyListener;
		}		
		public KeyListener Onlynum() {
			KeyListener keyListener= new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if (c == '-' || c=='.') {
			      }else  if (!((c >= '0') && (c <= '9') ||
			         (c == KeyEvent.VK_BACK_SPACE) ||
			         (c == KeyEvent.VK_DELETE))) {			    
			        e.consume();
			      }
			    }};			    	 		
		return keyListener;
		}		
		public KeyListener Onlyphone() {
			KeyListener keyListener= new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if (c == '-' || c=='+' || c=='/' || c==' ' || c=='(' || c==')') {
			      }else  if (!((c >= '0') && (c <= '9') ||
			         (c == KeyEvent.VK_BACK_SPACE) ||
			         (c == KeyEvent.VK_DELETE))) {			    
			        e.consume();
			      }
			    }};			    	 		
		return keyListener;
		}		
		public boolean validatedate(String strDate , String write) {
			SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
			if (zempty(strDate)) {
				if (write == "Y") {
				JOptionPane.showMessageDialog(null, "Empty date !");
				}
				return false;
			}
			sdfrmt.setLenient(false);
			String ss = strDate.trim();
			try {
				Date javaDate = sdfrmt.parse(ss);
			} catch (ParseException e) {
				if (write == "I") {
				JOptionPane.showMessageDialog(null, "Incorrect date !");
				}
				return false;
			}
			return true;
		}
	
		public String padLeftZeros(String ss, int length) {
		    if (ss.length() >= length) {
		        return ss;
		    }
		    StringBuilder sb = new StringBuilder();
		    while (sb.length() < length - ss.length()) {
		        sb.append('0');
		    }
		    sb.append(ss);
		    return sb.toString();
		}
		public Double stodd(String szam) {
			Double seged = 0.0;
			try {
				if (!zempty(szam)) {
					seged = Double.parseDouble(szam);
				}
			} catch (Exception e) {
				System.out.println("cnvert error !!!");
			}
			return seged;
		}
		public String  ddtos(double szam) {
			String  seged = "";
			try {		
					seged = Double.toString(szam);			
			} catch (Exception e) {
				System.out.println("convert error !!!");
			}
			return seged;
		}
	
		public static String repeat(String s, int times) {
			   if (times <= 0) return "";
			   else if (times % 2 == 0) return repeat(s+s, times/2);
			   else return s + repeat(s+s, times/2);
			}		
		
		public String padr(String ss, int length) {
		    if (ss.length() >= length) {
		        return ss;
		    }
		    StringBuilder sb = new StringBuilder();
		    while (sb.length() < length - ss.length()) {
		        sb.append(" ");
		    }
		   String sss = ss+sb;	
		    return sss.toString();
		}
		
		public String padl(String ss, int length) {
		    if (ss.length() >= length) {
		        return ss;
		    }
		    StringBuilder sb = new StringBuilder();
		    while (sb.length() < length - ss.length()) {
		        sb.append(" ");
		    }
		    sb.append(ss);
		    return sb.toString();
		}
		public String sf (Double num) {	
			String output="";
			try {
			Locale.setDefault(Locale.ENGLISH);
			DecimalFormat ddd = new DecimalFormat("#########0.00");
			output = ddd.format(num);	
			}catch (Exception e) {
				System.out.println("convert error !!!");				
			}
			return output;
		}
		public String bsf(String number) {	
			Locale.setDefault(Locale.ENGLISH);
			String sc ="";
			if (!zempty(number)) {
			try {
			    BigDecimal bb = new BigDecimal(number);			
		       sc = String.format("%.2f", bb);
		   return sc;		
			}catch (Exception e) {
				   System.out.println("convert error !!!");				
			}
			}
			 return sc;
		}
		public String zmultiply(String num1, String num2) {
			String result = "";
			Locale.setDefault(Locale.ENGLISH);
			if (!zempty(num1) && !zempty(num2)) {
			try {
				BigDecimal b1 =  new BigDecimal(num1);
				BigDecimal b2 =  new BigDecimal(num2); 
				b1 = b1.multiply(b2);
				result = String.format("%.2f", b1);
			}catch (Exception e) {
				   System.out.println("convert error !!!");				
			}}			
			return result;
		}
		public  String  whichpanel(JPanel ppanel) {
			JPanel card = null;
			for (Component comp : ppanel.getComponents()) {
			    if (comp.isVisible() == true) {
			        card = (JPanel) comp;
			    }
			}
		System.out.print(card.getName());
			return card.getName();
			}
}


