package com.proj.view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Home extends JPanel implements ActionListener { // STARTING PAGE
	// - - > DECLARATIONS
	private JLabel appName;
	private JButton usersButton, adminButton;
	private static final long serialVersionUID = 1L;
	
	public Home() { // HOME PAGE / PANEL CONSTRUCTOR
		this.setLayout(null);
		
		// SETTING UP APPLICATION NAME
		appName = new JLabel("ELECTION CANDIDATES MANAGEMENT APPLICATION");
		appName.setFont(new Font("Arial", Font.BOLD, 25));
		appName.setSize(750, 30);
		appName.setLocation(42, 50);
		this.add(appName);
		
		JLabel minorLabel = new JLabel("- Select Type -");
		minorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		minorLabel.setSize(150, 25);
		minorLabel.setLocation(305, 115);
		this.add(minorLabel);
		
		// - - > ADDING A BUTTON TO APPLICATION
		usersButtonProc();
		adminButtonProc();
	}
	
	private void usersButtonProc() { // method for generating the users button
		ImageIcon img = new ImageIcon("src\\com\\proj\\resources\\users.png");
		
		JLabel usersLabel = new JLabel("USER", SwingConstants.CENTER);
		usersLabel.setSize(100, 25);
		usersLabel.setLocation(130, 180);
		usersLabel.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(usersLabel);
		
		usersButton = new JButton("USER");
		usersButton.setSize(100, 60);
		usersButton.setLocation(150, 230);
		usersButton.addActionListener(this);
		this.add(usersButton);
		
		JLabel usersIcon = new JLabel(img);
		usersIcon.setSize(100, 90);
		usersIcon.setLocation(190, 145);
		this.add(usersIcon);
	}
	
	private void adminButtonProc() { // method for generating the admin button
		ImageIcon img = new ImageIcon("src\\com\\proj\\resources\\admin.png");
		
		JLabel adminLabel = new JLabel("ADMIN", SwingConstants.CENTER);
		adminLabel.setSize(100, 25);
		adminLabel.setLocation(450, 180);
		adminLabel.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(adminLabel);
		
		adminButton = new JButton("ADMIN");
		adminButton.setSize(100, 60);
		adminButton.setLocation(470, 230);
		adminButton.addActionListener(this);
		this.add(adminButton);
		
		JLabel adminIcon = new JLabel(img);
		adminIcon.setSize(25,25);
		adminIcon.setLocation(555, 180);
		this.add(adminIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // BUTTONS ACTION LISTENER
		// TODO Auto-generated method stub
		if(e.getSource().equals(adminButton)) { // go to admin login page
			System.out.println("ADMIN CLICKED");
			
			MainFrame.mainFrame.setResizable(true);
			MainFrame.mainFrame.setSize(600, 400);
			CardLayout cl = (CardLayout) MainFrame.deck.getLayout();
			cl.show(MainFrame.deck, MainFrame.adminLoginCard);
			
		}else if(e.getSource().equals(usersButton)) {
			System.out.println("USER BUTTON CLICKED");
			
			MainFrame.mainFrame.setSize(950, 650);
			MainFrame.mainFrame.setResizable(true);
			CardLayout cl = (CardLayout) MainFrame.deck.getLayout();
			cl.show(MainFrame.deck, MainFrame.userListCard);
		}
	}
}
