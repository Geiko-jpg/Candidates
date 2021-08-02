package com.proj.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.proj.model.Candidate;

public class UserList extends JPanel implements ActionListener, ItemListener{
	// - - > DECLARATIONS
	private static final long serialVersionUID = 1L;
	private JPanel upperPanel, middlePanel;
	private JComboBox filterPartyBox, filterPositionBox;
	private JTextField searchField;
	private JButton searchButton, returnHomeButton, viewAll, importFile, loadDb;
	// - - > DECLARING COMBO BOX CHOICES
	private String[] positionsArray = {"(Filter Position - DEFAULT)", "President", "Vice President", "Executive Secretary",
									  "Secretary of Agrarian Reform", "Secretary of Agriculture",
									  "Secretary of Education", "Secretary of Finance", "Secretary of Energy",
									  "Secretary of Health", "Secretary of Justice", "Warrior"};
	private String[] partiesArray = {"(Filter Party - DEFAULT)", "PDP-LABAN", "LIBERAL PARTY", "ANAKBAYAN", "AXIE PH"};
	// - - > TABLE COMPONENTS
	private DefaultTableModel dtm;
	private JScrollPane jsp;
	private JTable uCandidatesTable;
	// - - > CANDIDATES DETAILED FRAME
	private UserModal userModal;
	// - - > ARRAY LIST VARIABLES
	private ArrayList<Candidate> testCandidates = new ArrayList<Candidate>();
	// - - > FILE CHOOSER
	private final JFileChooser fc = new JFileChooser();
	
	public UserList() { // USER LIST CONSTRUCTOR
		this.setLayout(new BorderLayout());
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
		upperPanel.setPreferredSize(new Dimension(0, 80));
		
		// - - > ADDING STARTING LABEL
		JLabel startingMast = new JLabel("USERS' CANDIDATES LIST");
		startingMast.setFont(new Font("Arial", Font.BOLD, 25));
		upperPanel.add(startingMast);
		
		JPanel sideNotesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		sideNotesPanel.setPreferredSize(new Dimension(380, 50));
		sideNotesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Instructions"));
		
		JLabel sideNotes = new JLabel("Select Candidate's Row to View Candidate's Information");
		sideNotesPanel.add(sideNotes);
		upperPanel.add(sideNotesPanel);
		
		returnHomeButton = new JButton("Return to Home");
		returnHomeButton.setPreferredSize(new Dimension(130, 30));
		returnHomeButton.addActionListener(this);
		upperPanel.add(returnHomeButton);
		
		this.add(upperPanel, BorderLayout.PAGE_START);
		
		// - - > MIDDLE PANEL
		middlePanel();
	}
	
	private void middlePanel() { // METHOD RESPONSIBLE FOR RENDERING THE MIDDLE PANEL
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));
		middlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setLayout(new GridLayout(3, 1));
		filtersPanel.setPreferredSize(new Dimension(230, 150));
		
		JPanel filtersLabelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25)); // TEXT PANEL
		
		JLabel filtersLabel = new JLabel("TABLE FILTERS:");
		filtersLabelWrapper.add(filtersLabel);
		filtersPanel.add(filtersLabelWrapper);
		
		// - - > ADDING FILTER PARTY COMBO BOX		
		JPanel filterPartyWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2)); // FILTER PARTY PANEL
		
		filterPartyBox = new JComboBox(); // FILTER PARTY COMBO BOX PROPER
		filterPartyBox.setPreferredSize(new Dimension(200, 30));
		
		for(String item : partiesArray) { // POPULATING FILTER PARTY BOX
			filterPartyBox.addItem(item);
		}
		filterPartyBox.addItemListener(this);
		
		filterPartyWrapper.add(filterPartyBox);
		
		filtersPanel.add(filterPartyWrapper);
		
		// - - > ADDING FILTER POSITION COMBO BOX
		JPanel filterPositionWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // FILTER POSITION PANEL
		
		filterPositionBox = new JComboBox(); // FILTER POSITION COMBO BOX
		filterPositionBox.setPreferredSize(new Dimension(200, 30));
		
		for(String item : positionsArray) { // POPULATING FILTER POSITION BOX
			filterPositionBox.addItem(item);
		}
		filterPositionBox.addItemListener(this);
		
		filterPositionWrapper.add(filterPositionBox);
		
		filtersPanel.add(filterPositionWrapper);
		
		middlePanel.add(filtersPanel);
		
		// - - > SEARCH BOX
		JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 9, 14));
		searchWrapper.setPreferredSize(new Dimension(550, 120));
		searchWrapper.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search Candidate:"));
		
		JLabel searchLabel = new JLabel("Input Candidate's Name in Search Bar");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
		searchWrapper.add(searchLabel);
		
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(400,30));
		searchWrapper.add(searchField);
		
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(80, 30));
		searchButton.addActionListener(this);
		
		searchWrapper.add(searchButton);
		
		middlePanel.add(searchWrapper);
		
		// - - > RENDER TABLE
		renderingTable();
		populateTableTesting(); // POPULATE CONTENTS OF THE TABLE
		
		// - - > BUTTON HANDLE FOR IMPORTING EXTERNAL DATA
		importFile = new JButton("IMPORT DATA"); // event handler for file chooser
		importFile.setPreferredSize(new Dimension(140,40));
		importFile.addActionListener(this);
		middlePanel.add(importFile); 
		
		// - - > VIEW ALL BUTTON 
		viewAll = new JButton("VIEW ALL");
		viewAll.setPreferredSize(new Dimension(140, 40));
		viewAll.addActionListener(this);
		viewAll.setEnabled(false);
		middlePanel.add(viewAll);
		
		// - - > BUTTON HANDLER FOR LOADING DB DATA
		loadDb = new JButton("LOAD DB DATA"); 
		loadDb.setPreferredSize(new Dimension(140,40));
		loadDb.addActionListener(this);
		middlePanel.add(loadDb);
		
		this.add(middlePanel, BorderLayout.CENTER);
	}
	
	private void renderingTable() { // rendering the table structure for users list
		Object columnsData[] = new Object[4];
		columnsData[0] = "Candidate Code";
		columnsData[1] = "Candidate Name";
		columnsData[2] = "Political Party";
		columnsData[3] = "Political Position";
		
		dtm = new DefaultTableModel(columnsData, 0);
		uCandidatesTable = new JTable(dtm);
		uCandidatesTable.addMouseListener(new java.awt.event.MouseAdapter() { // anonymous listener
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int col = 0;
				int row = uCandidatesTable.rowAtPoint(evt.getPoint());
				if(row >= 0) {
					System.out.println("ROW NUM: " + row);
					String targetCode = uCandidatesTable.getModel().getValueAt(row, col).toString();
					
					for(int i = 0; i < testCandidates.size(); i++) {
						if(targetCode == testCandidates.get(i).getCode()) {
							Candidate xcandidate = testCandidates.get(i);
							System.out.println("NAME: " + xcandidate.getName());
							System.out.println("PARTY: " + xcandidate.getParty());
							System.out.println("POSITION: " + xcandidate.getPosition());
							
							// - - > GENERATE NEW FRAME
							generateDetailsFrame(xcandidate);
							break; // end loop after searching
						}
					}
				}
			}
		});
		
		jsp = new JScrollPane(uCandidatesTable);
		jsp.setBorder(BorderFactory.createEtchedBorder());
		jsp.setPreferredSize(new Dimension(850, 310));
		middlePanel.add(jsp);
		
		DefaultTableCellRenderer centerRend = new DefaultTableCellRenderer();
		centerRend.setHorizontalAlignment(JLabel.CENTER);
		uCandidatesTable.getColumnModel().getColumn(0).setCellRenderer(centerRend);
		uCandidatesTable.getColumnModel().getColumn(1).setCellRenderer(centerRend);
		uCandidatesTable.getColumnModel().getColumn(2).setCellRenderer(centerRend);
		uCandidatesTable.getColumnModel().getColumn(3).setCellRenderer(centerRend);
	}
	
	private void generateDetailsFrame(Candidate xcandidate) { // VIEWS WHEN SELECTING A CANDIDATE
		userModal = new UserModal(xcandidate);
	}
	
	private void populateTableTesting() { // REMOVE LATER (TESTING ROW CLICKABILITY
		Candidate x = new Candidate("2019102829", "Kyle", "President", "PDP-LABAN");
		testCandidates.add(x);
		x = new Candidate("2019101323", "Drei", "President", "LIBERAL PARTY");
		testCandidates.add(x);
		x = new Candidate("2077696969", "Johnny", "Warrior", "LIBERAL PARTY");
		testCandidates.add(x);
		x = new Candidate("2018121269", "Miguel", "President", "AXIE PH");
		testCandidates.add(x);
		x = new Candidate("2069122334", "Kyle", "Vice President", "PDP-LABAN");
		testCandidates.add(x);
		x = new Candidate("2011222222", "Elijah", "Secretary", "ANAKBAYAN");
		testCandidates.add(x);
		
		// - - > SORTING METHOD
		sortingList(testCandidates);
		
		Object xrow[] = null;
		for(Candidate xcandidate : testCandidates) {
			xrow = new Object[4];
			xrow[0] = xcandidate.getCode();
			xrow[1] = xcandidate.getName();
			xrow[2] = xcandidate.getParty();
			xrow[3] = xcandidate.getPosition();
			dtm.addRow(xrow);
		}		
	}
	
	// - -  > SORTING UTILITY METHOD
	private void sortingList(ArrayList<Candidate> xlist) {
		Collections.sort(xlist, new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(searchButton)) { // if searched button is clicked!
			try {
				String searchInput = searchField.getText();
				if(searchInput.contentEquals("")) { // IF SEARCH BOX IS EMPTY
					JOptionPane.showMessageDialog(null, "USER MUST INPUT A CANDIDATE'S NAME!");
					
				}else { // IF VALID NAME IS ENTERED
					boolean there_is_candidate = false; // checks if name is in the list
					System.out.println("INPUT: " + searchInput);
					
					for(Candidate xcandidate : testCandidates) { // checks if the candidate is in the list
						if(xcandidate.getName().contentEquals(searchInput)) {
							there_is_candidate = true;
							break;
						}
					}
					
					if(there_is_candidate) { // IF THERE IS A CANDIDATE
						filterPartyBox.setEnabled(false); // DISABLE COMBO BOXES
 						filterPositionBox.setEnabled(false); // DISABLE COMBO BOXES
						viewAll.setEnabled(true); // ENABLE BUTTON THAT RETURNS TO DEFAULT TABLE LIST					
						dtm.setRowCount(0); // CLEARS TABLE
						
						Object xrow[] = null;
						for(int i = 0; i < testCandidates.size(); i++) {
							if(testCandidates.get(i).getName().contentEquals(searchInput)) {
								xrow = new Object[4]; // REPOPULATE THE TABLE
								xrow[0] = testCandidates.get(i).getCode();
								xrow[1] = testCandidates.get(i).getName();
								xrow[2] = testCandidates.get(i).getParty();
								xrow[3] = testCandidates.get(i).getPosition();
								dtm.addRow(xrow);
							}
						}
					}else { // IF THERE IS NO CANDIDATE 
						JOptionPane.showMessageDialog(null, searchInput + " IS NOT PRESENT IN THE CANDIDATE DATABASE");
					}
				}
			} catch(NullPointerException ne) {
				ne.printStackTrace();
			}
			
			searchField.setText(null);
			
		}else if(e.getSource().equals(returnHomeButton)) { // if return home button is clicked!
			System.out.println("RETURNING HOME");
			MainFrame.mainFrame.setSize(750, 400);
			MainFrame.mainFrame.setResizable(false);
			
			CardLayout cl = (CardLayout) MainFrame.deck.getLayout();
			cl.show(MainFrame.deck, MainFrame.homeCard);
			
		}else if(e.getSource().equals(viewAll)) {
			System.out.println("VIEW ALL CLICKED!");
			
			dtm.setRowCount(0); // CLEAR ROW
			
			Object xrow[];
			for(Candidate uCandidate : testCandidates) {
				xrow = new Object[4];
				xrow[0] = uCandidate.getCode();
				xrow[1] = uCandidate.getName();
				xrow[2] = uCandidate.getParty();
				xrow[3] = uCandidate.getPosition();
				dtm.addRow(xrow);
			}
			
			viewAll.setEnabled(false); // disable button again after view all has been clicked!
			filterPartyBox.setEnabled(true); // ENABLE COMBO BOXES
			filterPositionBox.setEnabled(true); // ENABLE COMBO BOXES
			
		}else if(e.getSource().equals(importFile)) {
			int returnVal = fc.showOpenDialog(this); // initialize file chooser
			System.out.println("IMPORT FILE INITIATED");
			
			
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.out.println("Opening: " + file.getName());
			} else {
				System.out.println("Open Command Cancelled by the User");
			}
		
		}else if(e.getSource().equals(loadDb)) {
			System.out.println("LOAD DB INITIATED");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) { // ITEM LISTENER FOR THE FILTERS
		// TODO Auto-generated method stub
		if(e.getStateChange() == 1 && e.getSource().equals(this.filterPartyBox)) {
			String targetParty = e.getItem().toString();
			System.out.println("FIND ALL " + targetParty);
			
			// - - > CLEAR ALL ROWS IN THE TABLE
			dtm.setRowCount(0);
			filterPositionBox.setEnabled(false); // disable filter position
			
			// - - > FILTER ACCORDINGLY
			Object xrow[] = null;
			for(int i = 0; i < testCandidates.size(); i++) {
				if(!(targetParty.contentEquals("(Filter Party - DEFAULT)"))) { // CHECKS IF SELECTED FILTER IS NOT THE DEFAULT
					if(testCandidates.get(i).getParty().contentEquals(targetParty)) { 
						xrow = new Object[4]; // REPOPULATE THE TABLE
						xrow[0] = testCandidates.get(i).getCode();
						xrow[1] = testCandidates.get(i).getName();
						xrow[2] = testCandidates.get(i).getParty();
						xrow[3] = testCandidates.get(i).getPosition();
						dtm.addRow(xrow);
					}	
				}else {
					System.out.println("DEFAULT FILTER - PARTIES");
					filterPositionBox.setEnabled(true); // RE-ENABLE FILTERING POSITION
					for(Candidate ycandidate : testCandidates) { // NESTED POPULATION ADVANCED FOR LOOP
						xrow = new Object[4];
						xrow[0] = ycandidate.getCode();
						xrow[1] = ycandidate.getName();
						xrow[2] = ycandidate.getParty();
						xrow[3] = ycandidate.getPosition();
						dtm.addRow(xrow);				
					}	
					break;
				}
			}
		}else if(e.getStateChange() == 1 && e.getSource().equals(this.filterPositionBox)) {
			String targetPosition = e.getItem().toString();
			System.out.println("FIND ALL " + targetPosition);
			
			// - - > CLEAR ALL ROWS IN THE TABLE
			dtm.setRowCount(0);
			filterPartyBox.setEnabled(false); // SET FILTER PARTY DISABLED
			
			// - - > FILTERING POSITION
			Object xrow[] = null; // establish new rows
			for(int i = 0; i < testCandidates.size(); i++) {
				if(!(targetPosition.contentEquals("(Filter Position - DEFAULT)"))) {
					if(testCandidates.get(i).getPosition().contentEquals(targetPosition)) {
						xrow = new Object[4]; // REPOPULATE THE TABLE
						xrow[0] = testCandidates.get(i).getCode();
						xrow[1] = testCandidates.get(i).getName();
						xrow[2] = testCandidates.get(i).getParty();
						xrow[3] = testCandidates.get(i).getPosition();
						dtm.addRow(xrow);
					}
				}else {
					System.out.println("DEFAULT FILTER - POSITIONS");
					filterPartyBox.setEnabled(true); // RE ENABLE PARTY FILTER
					for(Candidate zcandidate : testCandidates) { // NESTED POPULATION ADVANCED FOR LOOP
						xrow = new Object[4];
						xrow[0] = zcandidate.getCode();
						xrow[1] = zcandidate.getName();
						xrow[2] = zcandidate.getParty();
						xrow[3] = zcandidate.getPosition();
						dtm.addRow(xrow);				
					}	
					break;
				}
			}
		}
	}

}
