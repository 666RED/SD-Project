package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.*;
import java.util.regex.*;

public class ManageSupplierFrame extends JFrame{
	
	JPanel panHeader, panFooter, panContent;
	JLabel lblPageName, lblSupplierName, lblSupplierPhoneNumber, lblSupplierLocation, lblBackIcon;
	JTextField tfSupplierName, tfSupplierPhoneNumber;
	JTextArea taSupplierLocation;
	JButton btnEditAndSave;
	final Color CONFIRM_AND_SAVE_BUTTON_COLOR = new Color(246, 84, 70);
	Font labelFont = new Font("Arial", Font.PLAIN, 15);
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon savedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	
	private int supplierID;

	public ManageSupplierFrame(final int supplierID) {
		this.setSupplierID(supplierID);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Manage Supplier");
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
		
		lblPageName = new JLabel("Manage Supplier");
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
		
		retrieveSupplierInfo();
		
		btnEditAndSave = new JButton("Edit and Save");
		btnEditAndSave.setBounds(1100, 480, 250, 50);
		btnEditAndSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditAndSave.setBackground(CONFIRM_AND_SAVE_BUTTON_COLOR);
		btnEditAndSave.setForeground(Color.WHITE);
		btnEditAndSave.setFont(new Font("Arial", Font.BOLD, 25));
		btnEditAndSave.setFocusable(false);
		btnEditAndSave.addActionListener(new ActionListener() {
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
		panContent.add(btnEditAndSave);
		
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
			
			String sql = "UPDATE supplier "
					   + "SET supplier_name = \"" + tfSupplierName.getText() + "\", "
					   + "supplier_phone_number = \"" + tfSupplierPhoneNumber.getText() + "\", "
					   + "supplier_location = \"" + taSupplierLocation.getText() + "\" "
					   + "WHERE supplier_id = " + supplierID;
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
			int response = JOptionPane.showConfirmDialog(null, "Supplier Data Updated Successfully", "Updated", 
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
	
	public void retrieveSupplierInfo() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name, supplier_phone_number, supplier_location "
					   + "FROM supplier "
					   + "WHERE supplier_id = " + supplierID + ";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			 
			if(rs != null) {
				while(rs.next()) {
					tfSupplierName.setText(rs.getString("supplier_name"));
					tfSupplierPhoneNumber.setText(rs.getString("supplier_phone_number"));
					taSupplierLocation.setText(rs.getString("supplier_location"));
				}
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
	
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public boolean repeatedSupplierInfo() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name, supplier_phone_number, supplier_location "
					   + "FROM supplier "
					   + "WHERE supplier_id != " + supplierID + ";";
			
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
