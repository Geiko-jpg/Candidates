package com.proj.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.proj.model.Candidate;

public class UserModal extends JFrame {
	// - - > NEEDED VARIABLES DECLARATION
	private static final long serialVersionUID = 1L;
	private Candidate userCandidate; // CANDIDATE MODEL VARIABLE
	// - - > GUI VARIABLES
	private JPanel detailsPanel, gridWrapper;
	private JLabel name, sex, age, party, position, education, pinfo;
	private JTextField nameField, sexField, ageField, partyField, positionField, educField, pinfoField;
	
	public UserModal(Candidate xcandidates) {
		// - - > SET UP MODAL DIMENSION
		this.userCandidate = xcandidates; // PASS CANDIDATE MODEL
		renderFrame(); // CALL RENDERING METHOD 
	}
	
	// - - > RENDERING GUI METHODS
	private void renderFrame() {
		this.setSize(500, 370);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(this.userCandidate.getName() + " - Candidate Details");
		this.getContentPane().setLayout(new BorderLayout());
		
		detailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 13));
		
		gridWrapper = new JPanel(new GridLayout(0,2));
		gridWrapper.setPreferredSize(new Dimension(430, 300));
		
		// - - > ADDING LABELS AND FIELDS IN THE GRID WRAPPER
		name = new JLabel("Name: "); // NAME LABEL
		gridWrapper.add(name);
		nameField = new JTextField(); // NAME FIELD
		nameField.setHorizontalAlignment(JTextField.CENTER);
		nameField.setText(this.userCandidate.getName());
		gridWrapper.add(nameField);
		
		sex = new JLabel("Sex: "); // SEX LABEL
		gridWrapper.add(sex);
		sexField = new JTextField(); // SEX FIELD
		sexField.setHorizontalAlignment(JTextField.CENTER);
		//sexField.setText(xcandidate.getName());
		gridWrapper.add(sexField);
		
		age = new JLabel("Age: "); // AGE LABEL
		gridWrapper.add(age);
		ageField = new JTextField(); // AGE FIELD
		ageField.setHorizontalAlignment(JTextField.CENTER);
		//ageField.setText(xcandidate.getName());
		gridWrapper.add(ageField);
		
		party = new JLabel("Party: "); // PARTY LABEL
		gridWrapper.add(party);
		partyField = new JTextField(); // PARTY FIELD
		partyField.setHorizontalAlignment(JTextField.CENTER);
		partyField.setText(this.userCandidate.getParty());
		gridWrapper.add(partyField);
		
		position = new JLabel("Position: "); // POSITION LABEL
		gridWrapper.add(position);
		positionField = new JTextField(); // POSITION FIELD
		positionField.setHorizontalAlignment(JTextField.CENTER);
		positionField.setText(this.userCandidate.getPosition());
		gridWrapper.add(positionField);
		
		education = new JLabel("Educational Background: "); //EDUCATION LABEL
		gridWrapper.add(education);
		educField = new JTextField(); // EDUCATION FIELD
		educField.setHorizontalAlignment(JTextField.CENTER);
		//educField.setText(xcandidate.getParty());
		gridWrapper.add(educField);
		
		pinfo = new JLabel("Personal Info: "); // PERSONAL INFO LABEL
		gridWrapper.add(pinfo);
		pinfoField = new JTextField(); // PERSONAL INFO FIELD
		pinfoField.setHorizontalAlignment(JTextField.CENTER);
		//pinfoField.setText(xcandidate.getParty());
		gridWrapper.add(pinfoField);
			
		detailsPanel.add(gridWrapper);
		detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.add(detailsPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
}
