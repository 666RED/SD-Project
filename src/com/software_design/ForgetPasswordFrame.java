package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class ForgetPasswordFrame extends JFrame{
	
	JPanel panForm;
	JLabel lblTitle, lblBackIcon;
	JTextArea taInfo;
	JTextField tfPhoneNumber;
	JButton btnConfirm;
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	final Color REGISTER_BUTTON_COLOR = new Color(221, 63, 28);

	public ForgetPasswordFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Forget Password");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		panForm = new JPanel();
		panForm.setBounds(525, 150, 350, 400);
		panForm.setBackground(Color.WHITE);
		panForm.setLayout(null);
		panForm.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(10, 0, 40, 50);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				dispose();
				new LoginFrame();
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
		
		lblTitle = new JLabel("Reset Password");
		lblTitle.setBounds(20, 70, 350, 40);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 40));
		
		taInfo = new JTextArea("Enter your phone number that is\naccosiated with your account and\nwe'll send you a new password");
		taInfo.setFont(new Font("Arial", Font.PLAIN, 18));
		taInfo.setBounds(40, 150, 280, 70);
		taInfo.setEditable(false);
		
		tfPhoneNumber = new JTextField();
		tfPhoneNumber.setBounds(50, 250, 250, 40);
		tfPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 18));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(100, 320, 150, 40);
		btnConfirm.setFont(new Font("Arial", Font.BOLD, 20));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setBackground(REGISTER_BUTTON_COLOR);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// need to change after
				dispose();
				new LoginFrame();
			}
		});
		
		panForm.add(lblBackIcon);
		panForm.add(lblTitle);
		panForm.add(taInfo);
		panForm.add(tfPhoneNumber);
		panForm.add(btnConfirm);
		
		add(panForm);

		setVisible(true);

		
	}
}
