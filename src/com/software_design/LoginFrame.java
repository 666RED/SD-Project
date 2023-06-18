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

public class LoginFrame extends JFrame{	
	
	JLabel lblSystemName, lblStaffID, lblPassword, lblForgetPassword;
	JButton btnRegisterOption, btnLoginOption, btnLogin;
	JTextField tfStaffID, tfPassword;
	JPasswordField pfPassword;
	JCheckBox cbViewPassword;
	JPanel panForm;
	JRadioButton rbShopOwner, rbEmployee;
	ButtonGroup bg;
	Font myFont = new Font("Arial", Font.PLAIN, 15);
	final Color LOGIN_BUTTON_COLOR = new Color(221, 63, 28);
	boolean showPassword = false;

	LoginFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Login");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblSystemName = new JLabel("KMS");
		lblSystemName.setBounds(625, 100, 120, 50);
		lblSystemName.setFont(new Font("Arial", Font.BOLD, 45));
		lblSystemName.setForeground(Color.RED);
		
		panForm = new JPanel();
		panForm.setBounds(450, 150, 450, 450);
		panForm.setBackground(Color.WHITE);
		panForm.setLayout(null);
		panForm.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		btnRegisterOption = new JButton("Register");
		btnRegisterOption.setBounds(0, 0, 225, 50);
		btnRegisterOption.setFont(new Font("Arial", Font.PLAIN, 18));
		btnRegisterOption.setForeground(Color.GRAY);
		btnRegisterOption.setFocusable(false);
		btnRegisterOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RegisterFrame();
			}
		});
		
		btnLoginOption = new JButton("Login");
		btnLoginOption.setBounds(225, 0, 225, 50);
		btnLoginOption.setFont(new Font("Arial", Font.PLAIN, 18));
		btnLoginOption.setForeground(Color.RED);
		btnLoginOption.setFocusable(false);
		
		rbShopOwner = new JRadioButton("Shop Owner");
		rbShopOwner.setBounds(320, 70, 120, 20);
		rbShopOwner.setFont(myFont);
		rbShopOwner.setBackground(null);
		rbShopOwner.setSelected(true);
		rbShopOwner.setFocusable(false);

		rbEmployee = new JRadioButton("Employee");
		rbEmployee.setBounds(320, 90, 120, 20);
		rbEmployee.setFont(myFont);
		rbEmployee.setBackground(null);
		rbEmployee.setFocusable(false);
		
		bg = new ButtonGroup();
		bg.add(rbShopOwner);
		bg.add(rbEmployee);
		
		lblStaffID = new JLabel("Staff ID");
		lblStaffID.setBounds(10, 120, 100, 20);
		lblStaffID.setFont(myFont);
		
		tfStaffID = new JTextField();
		tfStaffID.setBounds(10, 150, 430, 50);
		tfStaffID.setFont(myFont);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 220, 100, 20);
		lblPassword.setFont(myFont);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(10, 250, 430, 50);
		pfPassword.setFont(myFont);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(10, 250, 430, 50);
		tfPassword.setFont(myFont);
		tfPassword.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		tfPassword.setVisible(false);
		
		cbViewPassword = new JCheckBox("View password");
		cbViewPassword.setBounds(10, 310, 150, 20);
		cbViewPassword.setBackground(null);
		cbViewPassword.setFont(myFont);
		cbViewPassword.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(showPassword && String.valueOf(pfPassword.getPassword()).equals("")) {
					pfPassword.setText(tfPassword.getText());
				}
				tfPassword.setText(String.valueOf(pfPassword.getPassword()));
				if(!showPassword) {
					pfPassword.setVisible(false);
					tfPassword.setVisible(true);
					showPassword = true;
				}else {
					pfPassword.setVisible(true);
					tfPassword.setVisible(false);
					showPassword = false;
				}
			}
		});

		btnLogin = new JButton("Login");
		btnLogin.setBounds(10, 350, 430, 60);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 20));
		btnLogin.setBackground(LOGIN_BUTTON_COLOR);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(showPassword && !tfPassword.getText().equals("")) {
					pfPassword.setText(tfPassword.getText());
				}

				boolean validInput = validateInput();
				if(!validInput) {
					return;
				}
				
				boolean validUser = authenticateUser();
				if(validUser) {
					dispose();
					new PurchaseFrame();
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect staff ID or staff password", "INCORRECT INPUT", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblForgetPassword = new JLabel("Forget Password?");
		lblForgetPassword.setBounds(10, 420, 150, 20);
		lblForgetPassword.setForeground(Color.RED);
		lblForgetPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblForgetPassword.setFont(myFont);
		lblForgetPassword.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				dispose();
				new ForgetPasswordFrame();
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
		
		panForm.add(btnRegisterOption);
		panForm.add(btnLoginOption);
		panForm.add(rbShopOwner);
		panForm.add(rbEmployee);
		panForm.add(lblStaffID);	
		panForm.add(tfStaffID);
		panForm.add(lblPassword);
		panForm.add(pfPassword);
		panForm.add(tfPassword);
		panForm.add(cbViewPassword);
		panForm.add(btnLogin);	
		panForm.add(lblForgetPassword);	
		
		add(lblSystemName);
		add(panForm);
		
		setVisible(true);
	}
	
	public boolean validateInput() {
		if(tfStaffID.getText().equals("") && String.valueOf(pfPassword.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(null, "Enter staff ID and staff password", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(tfStaffID.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter the staff ID", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(!Pattern.matches("[0-9]{4}", tfStaffID.getText())) {
			JOptionPane.showMessageDialog(null, "Invalid staff ID", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(String.valueOf(pfPassword.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(null, "Enter the staff password", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if(rbShopOwner.isSelected() && !tfStaffID.getText().equals("0001") || rbEmployee.isSelected() && tfStaffID.getText().equals("0001")) {
			JOptionPane.showMessageDialog(null, "Incorrect staff ID or staff password", "INCORRECT INPUT", JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			return true;
		}
	}
	
	public boolean authenticateUser() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT staff_password "
					   + "FROM staff "
					   + "WHERE staff_id = " + Integer.parseInt(tfStaffID.getText());
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					if(String.valueOf(pfPassword.getPassword()).equals(rs.getString("staff_password"))) {
						return true;
					}else {
						return false;
					}
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
		return false;
	}
}
