package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RegisterFrame extends JFrame{
	
	JLabel lblSystemName, lblStaffID, lblPassword, lblInfoIcon, lblInfo;
	JButton btnRegisterOption, btnLoginOption, btnNext;
	JPasswordField pfPassword;
	JTextField tfStaffID, tfPassword;
	JCheckBox cbViewPassword;
	JPanel panForm;
	Font myFont = new Font("Arial", Font.PLAIN, 15);
	ImageIcon infoIcon = IconClass.createIcon("Image\\information-button.png", 20, 20);
	final Color NEXT_BUTTON_COLOR = new Color(93, 88, 88);
	boolean showPassword = false;

	RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Register");	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblSystemName = new JLabel("KMS");
		lblSystemName.setBounds(625, 125, 120, 50);
		lblSystemName.setFont(new Font("Arial", Font.BOLD, 45));
		lblSystemName.setForeground(Color.RED);
		
		panForm = new JPanel();
		panForm.setBounds(450, 175, 450, 420);
		panForm.setBackground(Color.WHITE);
		panForm.setLayout(null);
		panForm.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		btnRegisterOption = new JButton("Register");
		btnRegisterOption.setBounds(0, 0, 225, 50);
		btnRegisterOption.setFont(new Font("Arial", Font.PLAIN, 18));
		btnRegisterOption.setForeground(Color.RED);
		btnRegisterOption.setFocusable(false);
		
		btnLoginOption = new JButton("Login");
		btnLoginOption.setBounds(225, 0, 225, 50);
		btnLoginOption.setFont(new Font("Arial", Font.PLAIN, 18));
		btnLoginOption.setForeground(Color.GRAY);
		btnLoginOption.setFocusable(false);
		btnLoginOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();
			}
		});
		
		lblStaffID = new JLabel("Shop Owner Staff ID");
		lblStaffID.setBounds(10, 70, 150, 20);
		lblStaffID.setFont(myFont);
		
		tfStaffID = new JTextField();
		tfStaffID.setBounds(10, 100, 430, 50);
		tfStaffID.setFont(myFont);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 170, 100, 20);
		lblPassword.setFont(myFont);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(10, 200, 430, 50);
		pfPassword.setFont(myFont);
		
		tfPassword = new JTextField();
		tfPassword.setBounds(10, 200, 430, 50);
		tfPassword.setFont(myFont);
		tfPassword.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		tfPassword.setVisible(false);
		
		cbViewPassword = new JCheckBox("View password");
		cbViewPassword.setBounds(10, 260, 150, 20);
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
		
		String info = "Enter the shop owner staff ID and password before registering for new employee";
		lblInfo = new JLabel("<html>" + info + "</html>");
		lblInfo.setFont(myFont);
		lblInfo.setBounds(45, 300, 405, 40);
		lblInfo.setBorder(null);
		lblInfo.setVisible(false);
		
		lblInfoIcon = new JLabel(infoIcon);
		lblInfoIcon.setBounds(10, 300, 30, 30);
		lblInfoIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				lblInfo.setVisible(false);
			}
			
			public void mouseEntered(MouseEvent e) {
				lblInfo.setVisible(true);
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		btnNext = new JButton("Next >>");
		btnNext.setBounds(175, 370, 100, 30);
		btnNext.setFont(new Font("Arial", Font.BOLD, 15));
		btnNext.setBackground(NEXT_BUTTON_COLOR);
		btnNext.setForeground(Color.WHITE);
		btnNext.setFocusable(false);
		btnNext.addActionListener(new ActionListener() {

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
					new RegisterEmployeeFrame();
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect staff ID or staff password", "INCORRECT INPUT", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		panForm.add(btnRegisterOption);
		panForm.add(btnLoginOption);
		panForm.add(lblStaffID);	
		panForm.add(tfStaffID);
		panForm.add(lblPassword);
		panForm.add(tfPassword);
		panForm.add(pfPassword);
		panForm.add(cbViewPassword);
		panForm.add(lblInfoIcon);
		panForm.add(lblInfo);
		panForm.add(btnNext);	
		
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
		}else if(!tfStaffID.getText().equals("0001")) {
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
					   + "WHERE staff_id = 1;";
			
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
