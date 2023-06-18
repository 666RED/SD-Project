package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.*;

public class RegisterEmployeeFrame extends JFrame{
	
	JLabel lblSystemName, lblBackIcon, lblTitle, lblStaffID, lblPassword, lblConfirmedPassword, lblPhoneNumber;
	JButton btnRegister;
	JTextField tfStaffID, tfPhoneNumber;
	JPasswordField pfPassword, pfConfirmedPassword;
	JPanel panForm, panTitle, panContent;
	Font myFont = new Font("Arial", Font.PLAIN, 15);
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon successIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	final Color LOGIN_BUTTON_COLOR = new Color(221, 63, 28);
	final Color TITLE_COLOR = new Color(235, 40, 40);
	
	
	RegisterEmployeeFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Register Employee");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblSystemName = new JLabel("KMS");
		lblSystemName.setBounds(625, 50, 120, 50);
		lblSystemName.setFont(new Font("Arial", Font.BOLD, 45));
		lblSystemName.setForeground(Color.RED);
		
		panForm = new JPanel();
		panForm.setBounds(450, 100, 450, 590);
		panForm.setBackground(Color.WHITE);
		panForm.setLayout(null);
		panForm.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		panTitle = new JPanel();
		panTitle.setBounds(0, 0, 450, 50);
		panTitle.setBackground(null);
		panTitle.setLayout(null);
		panTitle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(10, 0, 40, 50);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				dispose();
				new RegisterFrame();
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		lblTitle = new JLabel("Register For Employee");
		lblTitle.setBounds(120, 5, 250, 50);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(TITLE_COLOR);
		
		panTitle.add(lblBackIcon);
		panTitle.add(lblTitle);
		
		panContent = new JPanel();
		panContent.setBounds(0, 50, 450, 540);
		panContent.setBackground(null);
		panContent.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.GRAY));
		panContent.setLayout(null);
		
		lblStaffID = new JLabel("Staff ID");
		lblStaffID.setBounds(10, 20, 100, 20);
		lblStaffID.setFont(myFont);
		
		tfStaffID = new JTextField();
		tfStaffID.setBounds(10, 50, 430, 50);
		tfStaffID.setFont(myFont);
		tfStaffID.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(tfStaffID.getText().equals("")) {
					tfStaffID.setText("4 digit: e.g. 1234");
					tfStaffID.setForeground(Color.GRAY);
				}
			}
			
			public void focusGained(FocusEvent e) {
				if(tfStaffID.getText().equals("4 digit: e.g. 1234")) {
					tfStaffID.setText("");
					tfStaffID.setForeground(Color.BLACK);
				}
			}
		});
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 120, 100, 20);
		lblPassword.setFont(myFont);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(10, 150, 430, 50);
		pfPassword.setFont(myFont);
		
		lblConfirmedPassword= new JLabel("Confirmed Password");
		lblConfirmedPassword.setBounds(10, 220, 150, 20);
		lblConfirmedPassword.setFont(myFont);
		
		pfConfirmedPassword = new JPasswordField();
		pfConfirmedPassword.setBounds(10, 250, 430, 50);
		pfConfirmedPassword.setFont(myFont);
		
		lblPhoneNumber = new JLabel("Employee Phone Number");
		lblPhoneNumber.setBounds(10, 320, 180, 20);
		lblPhoneNumber.setFont(myFont);
		
		tfPhoneNumber = new JTextField("e.g. 012-3456789");
		tfPhoneNumber.setBounds(10, 350, 430, 50);
		tfPhoneNumber.setForeground(Color.GRAY);
		tfPhoneNumber.setFont(myFont);
		tfPhoneNumber.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(tfPhoneNumber.getText().equals("")) {
					tfPhoneNumber.setText("e.g. 012-3456789");
					tfPhoneNumber.setForeground(Color.GRAY);
				}
			}
			
			public void focusGained(FocusEvent e) {
				if(tfPhoneNumber.getText().equals("e.g. 012-3456789")) {
					tfPhoneNumber.setText("");
					tfPhoneNumber.setForeground(Color.BLACK);
				}
			}
		});
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 450, 430, 60);
		btnRegister.setFont(new Font("Arial", Font.BOLD, 20));
		btnRegister.setBackground(LOGIN_BUTTON_COLOR);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFocusable(false);
		btnRegister.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!validateInput()) {
					return;
				}
				
				if(repeatedStaffID() || repeatedPhoneNumber()) {
					return;
				}else {
					registerNewEmployee();
					JOptionPane.showMessageDialog(null, "Register new employee successfully", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE, successIcon);
					dispose();
					new LoginFrame();
				}
			}
		});
		
		panContent.add(lblStaffID);
		panContent.add(tfStaffID);
		panContent.add(lblPassword);
		panContent.add(pfPassword);
		panContent.add(lblConfirmedPassword);
		panContent.add(pfConfirmedPassword);
		panContent.add(lblPhoneNumber);
		panContent.add(tfPhoneNumber);
		panContent.add(btnRegister);

		panForm.add(panTitle);
		panForm.add(panContent);
		
		add(lblSystemName);
		add(panForm);
		
		setVisible(true);
	}
	
	public boolean validateInput() {
		
		boolean validPhoneNumber1 = Pattern.matches("\\d\\d-\\d{7}", tfPhoneNumber.getText());
		boolean validPhoneNumber2 = Pattern.matches("\\d\\d\\d-\\d{7}", tfPhoneNumber.getText());
		boolean validPhoneNumber3 = Pattern.matches("\\d\\d\\d\\d-\\d{7}", tfPhoneNumber.getText());
		
		if(tfStaffID.getText().equals("") || String.valueOf(pfPassword.getPassword()).equals("") || String.valueOf(pfConfirmedPassword.getPassword()).equals("")
		   || tfPhoneNumber.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter all staff information", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(!Pattern.matches("[0-9]{4}", tfStaffID.getText())) {
			JOptionPane.showMessageDialog(null, "Invalid staff ID", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!String.valueOf(pfPassword.getPassword()).equals(String.valueOf(pfConfirmedPassword.getPassword()))) {
			JOptionPane.showMessageDialog(null, "Passwords are not the same", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!validPhoneNumber1 && !validPhoneNumber2 && !validPhoneNumber3) {
			JOptionPane.showMessageDialog(null, "Invalid phone number", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			return true;
		}
	}
	
	public boolean repeatedStaffID() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT staff_id FROM staff;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					if(Integer.parseInt(tfStaffID.getText()) == rs.getInt("staff_id")) {
						JOptionPane.showMessageDialog(null, "User already existed", "ERROR", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean repeatedPhoneNumber() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT staff_phone_number FROM staff;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					if(tfPhoneNumber.getText().equals(rs.getString("staff_phone_number"))) {
						JOptionPane.showMessageDialog(null, "Phone number had been registered", "ERROR", JOptionPane.ERROR_MESSAGE);
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
	
	public void registerNewEmployee() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "INSERT INTO staff "
					   + "VALUES(" + Integer.parseInt(tfStaffID.getText()) + ", \"" + String.valueOf(pfPassword.getPassword()) + "\", \"" + tfPhoneNumber.getText() + "\");";
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
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
}
