package main;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
	MainFrame() {
		init();
		menumaker();
	}

	private void init() {
		setSize(1290, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		JLabel lbmain = new JLabel();
		lbmain.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/hotelz.jpg")));
		lbmain.setBounds(0, 0, 1300, 700);
		add(lbmain);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int x, y, d;
				x = 1000;
				y = 600;
				d = 10;
				while (x > 0 && y > 0) {
					setSize(x, y);
					x = x - 2 * d;
					y = y - d;
					setVisible(true);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("A hiba ez:" + e);
					}
				}
				dispose();
			}
		});
	}

	private void menumaker() {
		// create a menu bar
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(500, 30));
		Font f = new Font("Tahoma", Font.BOLD, 16);
		UIManager.put("Menu.font", f);
		// create menus
		JMenu revMenu = new JMenu("Reservations");
		JMenu persMenu = new JMenu("Persons");
		JMenu servMenu = new JMenu("Services");
		JMenu payMenu = new JMenu("Payment");
		JMenu infMenu = new JMenu("Informations");
		menuBar.add(revMenu);
		menuBar.add(persMenu);
		menuBar.add(servMenu);
		menuBar.add(payMenu);
		menuBar.add(infMenu);

		// create menu items
		JMenuItem revMenuItem = new JMenuItem("Reservations");
		revMenuItem.setFont(f);
		revMenuItem.setMnemonic(KeyEvent.VK_K);
		revMenuItem.setActionCommand("reservation");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setFont(f);
		exitMenuItem.setActionCommand("Exit");
		revMenu.add(revMenuItem);
		revMenu.add(exitMenuItem);

		JMenuItem custMenuItem = new JMenuItem("Customers");
		custMenuItem.setFont(f);
		custMenuItem.setActionCommand("customer");
		JMenuItem guestMenuItem = new JMenuItem("Guests");
		guestMenuItem.setFont(f);
		guestMenuItem.setActionCommand("guest");
		persMenu.add(custMenuItem);
		persMenu.add(guestMenuItem);

		JMenuItem servMenuItem = new JMenuItem("Services");
		servMenuItem.setFont(f);
		servMenuItem.setActionCommand("service");
		
		JMenuItem servtypeMenuItem = new JMenuItem("Servicetype");
		servtypeMenuItem.setFont(f);
		servtypeMenuItem.setActionCommand("servicetype");
		
		JMenuItem roomMenuItem = new JMenuItem("Rooms");
		roomMenuItem.setFont(f);
		roomMenuItem.setActionCommand("room");
		
		JMenuItem roomtypeMenuItem = new JMenuItem("Roomtypes");
		roomtypeMenuItem.setFont(f);
		roomtypeMenuItem.setActionCommand("roomtype");
		
		
		servMenu.add(servMenuItem);
		servMenu.add(servtypeMenuItem);
		servMenu.add(roomMenuItem);
		servMenu.add(roomtypeMenuItem);

		JMenuItem payMenuItem = new JMenuItem("Payment");
		payMenuItem.setFont(f);
		payMenuItem.setActionCommand("payment");
		payMenu.add(payMenuItem);

		JMenuItem roomhistMenuItem = new JMenuItem("Room history");
		roomhistMenuItem.setFont(f);
		roomhistMenuItem.setActionCommand("roomhist");
		infMenu.add(roomhistMenuItem);
		
		JMenuItem guesthistMenuItem = new JMenuItem("Guest  history");
		guesthistMenuItem.setFont(f);
		guesthistMenuItem.setActionCommand("guesthist");
		infMenu.add(guesthistMenuItem);
		
		JMenuItem currentlygMenuItem = new JMenuItem("Currently guest");
		currentlygMenuItem.setFont(f);
		currentlygMenuItem.setActionCommand("currently");
		infMenu.add(currentlygMenuItem);
		
		JMenuItem freeroomsMenuItem = new JMenuItem("Free rooms");
		freeroomsMenuItem.setFont(f);
		freeroomsMenuItem.setActionCommand("freerooms");
		infMenu.add(freeroomsMenuItem);		
		
		this.setJMenuBar(menuBar);		
		MenuItemListener menuItemListener = new MenuItemListener();
		revMenuItem.addActionListener(menuItemListener);
		custMenuItem.addActionListener(menuItemListener);		
		guestMenuItem.addActionListener(menuItemListener);
		roomMenuItem.addActionListener(menuItemListener);
		servMenuItem.addActionListener(menuItemListener);
		servtypeMenuItem.addActionListener(menuItemListener);
		exitMenuItem.addActionListener(menuItemListener);
		payMenuItem.addActionListener(menuItemListener);
		roomtypeMenuItem.addActionListener(menuItemListener);
		roomhistMenuItem.addActionListener(menuItemListener);
		guesthistMenuItem.addActionListener(menuItemListener);
		currentlygMenuItem.addActionListener(menuItemListener);
		freeroomsMenuItem.addActionListener(menuItemListener);	
	}

	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String par = e.getActionCommand();
			if (par == "Exit") {
				System.exit(0);
			} else if (par == "reservation") {
				Custreservation ob = new Custreservation();
				ob.setSize(1230, 650);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "customer") {
				Customersinput ob = new Customersinput();
				ob.setSize(1230, 510);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);		
			} else if (par == "guest") {
				Guestsinput ob = new Guestsinput();
				ob.setSize(1230, 550);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "room") {
				Roomsinput ob = new Roomsinput();
				ob.setSize(1250, 550);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "service") {
				Servicesinput ob = new Servicesinput();
				ob.setSize(1230, 650);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "servicetype") {
				Servicetypes ob = new Servicetypes();
				ob.setSize(870, 450);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "roomtype") {
				Roomtypes ob = new Roomtypes();
				ob.setSize(870, 450);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "payment") {
				Billmaking ob = new Billmaking();
				ob.setSize(1170, 600);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
		    	ob.setVisible(true);	
		} else if (par == "guesthist") {
			Guesthistory ob = new Guesthistory();
			ob.setSize(790, 500);
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
			ob.setVisible(true);	
		} else if (par == "currently") {
			Currentlyguest ob = new Currentlyguest();
			ob.setSize(790, 500);
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
			ob.setVisible(true);		
		} else if (par == "freerooms") {
			Freerooms ob = new Freerooms();
			ob.setSize(790, 500);
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
			ob.setVisible(true);		
		} else if (par == "roomhist") {
			Roomhistory rom = new Roomhistory();
			rom.setSize(790, 500);
			rom.setLayout(null);
			rom.setLocationRelativeTo(null);
			rom.setVisible(true);			
		}	
		}
	}
	

	public static void main(String args[]) {
		MainFrame Main = new MainFrame();
		Main.setVisible(true);
	}
	}
