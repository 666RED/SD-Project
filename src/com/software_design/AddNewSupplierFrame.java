package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import java.util.regex.*;

public class AddNewSupplierFrame extends JFrame{
	
	JPanel panHeader, panFooter, panContent;
	JLabel lblPageName, lblSupplierName, lblSupplierPhoneNumber, lblSupplierLocation, lblBackIcon;
	JTextField tfSupplierName, tfSupplierPhoneNumber;
	JTextArea taSupplierLocation;
	JButton btnClearAll, btnConfirmAndSave;
	final Color CONFIRM_AND_SAVE_BUTTON_COLOR = new Color(246, 84, 70);
	Font labelFont = new Font("Arial", Font.PLAIN, 15);
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon savedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);

	public AddNewSupplierFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Add New Supplier");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(15, 50, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {

			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new ViewSupplierFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		panContent = new JPanel();
		panContent.setBounds(15, 90, 1340, 530);
		panContent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		panContent.setLayout(null);
		
		lblPageName = new JLabel("Add New Supplier");
		lblPageName.setBounds(0, 10, 300, 30);
		lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageName.setForeground(Color.BLACK);
		
		lblSupplierName = new JLabel("Supplier Name              :");
		lblSupplierName.setBounds(0, 90, 160, 20);
		lblSupplierName.setFont(labelFont);
		
		tfSupplierName = new JTextField();
		tfSupplierName.setBounds(170, 80, 310, 40);
		tfSupplierName.setFont(labelFont);
		
		lblSupplierPhoneNumber = new JLabel("Supplier Phone Number:");
		lblSupplierPhoneNumber.setBounds(0, 170, 160, 20);
		lblSupplierPhoneNumber.setFont(labelFont);
		
		tfSupplierPhoneNumber = new JTextField();
		tfSupplierPhoneNumber.setBounds(170, 160, 310, 40);
		tfSupplierPhoneNumber.setFont(labelFont);
		
		lblSupplierLocation = new JLabel("Supplier Location           :");
		lblSupplierLocation.setBounds(0, 270, 160, 20);
		lblSupplierLocation.setFont(labelFont);
		
		taSupplierLocation = new JTextArea(5, 10);
		taSupplierLocation.setLineWrap(true);
		taSupplierLocation.setBounds(170, 240, 310, 80);
		taSupplierLocation.setFont(labelFont);
		taSupplierLocation.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		taSupplierLocation.setWrapStyleWord(true);
		
		btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(0, 480, 150, 50);
		btnClearAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClearAll.setBackground(Color.RED);
		btnClearAll.setForeground(Color.WHITE);
		btnClearAll.setFont(new Font("Arial", Font.BOLD, 25));
		btnClearAll.setFocusable(false);
		btnClearAll.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSupplierName.setText("");
				tfSupplierPhoneNumber.setText("");
				taSupplierLocation.setText("");
				tfSupplierName.grabFocus();
			}
		});
		
		btnConfirmAndSave = new JButton("Confirm and Save");
		btnConfirmAndSave.setBounds(1100, 480, 250, 50);
		btnConfirmAndSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfirmAndSave.setBackground(CONFIRM_AND_SAVE_BUTTON_COLOR);
		btnConfirmAndSave.setForeground(Color.WHITE);
		btnConfirmAndSave.setFont(new Font("Arial", Font.BOLD, 25));
		btnConfirmAndSave.setFocusable(false);
		btnConfirmAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateInput();
			}
		});
		
		panContent.add(lblPageName);
		panContent.add(lblSupplierName);
		panContent.add(tfSupplierName);
		panContent.add(lblSupplierPhoneNumber);
		panContent.add(tfSupplierPhoneNumber);
		panContent.add(lblSupplierLocation);
		panContent.add(taSupplierLocation);
		panContent.add(btnClearAll);
		panContent.add(btnConfirmAndSave);
		
		panHeader = Header.setHeader(this, "Inventory");
		panFooter = Footer.setFooter(this);
		
		add(panHeader);
		add(panFooter);
		add(lblBackIcon);
		add(panContent);
		
		setVisible(true);
	}
	
	public void validateInput() {
		// after validate all input
		boolean validSupplierName = true;
		boolean validSupplierPhoneNumber= true;
		
		boolean validPhoneNumber1 = Pattern.matches("\\d\\d-\\d{7}", tfSupplierPhoneNumber.getText());
		boolean validPhoneNumber2 = Pattern.matches("\\d\\d\\d-\\d{7}", tfSupplierPhoneNumber.getText());
		boolean validPhoneNumber3 = Pattern.matches("\\d\\d\\d\\d-\\d{7}", tfSupplierPhoneNumber.getText());
		
		boolean incompleteInput = false;
		
		if(tfSupplierName.getText().equals("")) {
			tfSupplierName.setBorder(BorderFactory.createLineBorder(Color.RED));
			incompleteInput = true;
		}else if(!Pattern.matches("[a-zA-Z\\s/]*", tfSupplierName.getText())) {
			validSupplierName = false;
		}else {
			tfSupplierName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(tfSupplierPhoneNumber.getText().equals("")) {
			tfSupplierPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
			incompleteInput = true;
		}else if(!validPhoneNumber1 && !validPhoneNumber2 && !validPhoneNumber3) {
			validSupplierPhoneNumber = false;
		}else {
			tfSupplierPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(taSupplierLocation.getText().equals("")) {
			taSupplierLocation.setBorder(BorderFactory.createLineBorder(Color.RED));
			incompleteInput = true;
		}else {
			taSupplierLocation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(incompleteInput) {
			JOptionPane.showMessageDialog(this, "Please enter all supplier information", "Incomplete Input", JOptionPane.WARNING_MESSAGE);
			return;
		}else if(!validSupplierName) {
			tfSupplierName.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid supplier name", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else if(!validSupplierPhoneNumber) {
			tfSupplierPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid supplier phone number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else {
			saveData();
		}
	}
	
	public void saveData() {
		
		if(repeatedSupplierInfo()) {
			return;
		}
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "INSERT INTO supplier(supplier_name, supplier_phone_number, supplier_location) "
					       + "VALUES(?, ?, ?);";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, tfSupplierName.getText());
			stmt.setString(2, tfSupplierPhoneNumber.getText());
			stmt.setString(3, taSupplierLocation.getText());
			stmt.executeUpdate();
			
			int response = JOptionPane.showConfirmDialog(null, "New Supplier Added Successfully", "Added", 
						                                 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, savedIcon);
			
			if(response == JOptionPane.YES_OPTION) {
				dispose();
				new ViewSupplierFrame();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean repeatedSupplierInfo() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name, supplier_phone_number, supplier_location "
					   + "FROM supplier;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					if(tfSupplierName.getText().equals(rs.getString("supplier_name"))) {
						JOptionPane.showMessageDialog(null, "Supplier name already existed", "REPEATED SUPPLIER NAME", JOptionPane.WARNING_MESSAGE);
						return true;
					}else if(tfSupplierPhoneNumber.getText().equals(rs.getString("supplier_phone_number"))) {
						JOptionPane.showMessageDialog(null, "Supplier phone number already existed", "REPEATED SUPPLIER PHONE NUMBER", JOptionPane.WARNING_MESSAGE);
						return true;
					}else if(taSupplierLocation.getText().equals(rs.getString("supplier_location"))) {
						JOptionPane.showMessageDialog(null, "Supplier location already existed", "REPEATED SUPPLIER LOCATION", JOptionPane.WARNING_MESSAGE);
						return true;
					}
				}
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
