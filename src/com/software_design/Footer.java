package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.*;

public class Footer extends JFrame{
	
	public static JPanel setFooter(final JFrame myFrame) {
		JPanel panFooter, panUpper;
		JLabel lblBookIcon, lblPhoneIcon, lblTermsOfUse, lblContactUs, lblSystemName;
		ImageIcon bookIcon = IconClass.createIcon("Image\\bookicon.png", 35, 35);
		ImageIcon phoneIcon = IconClass.createIcon("Image\\telephoneicon.png", 25, 25);
		Font myFont = new Font("Arial", Font.PLAIN, 15);
		
		panFooter = new JPanel();
		panFooter.setBounds(0, 635, 1400, 70);
		panFooter.setLayout(null);
		panFooter.setBackground(Color.BLACK);
		
		panUpper = new JPanel();
		panUpper.setBounds(280, 0, 840, 55);
		panUpper.setLayout(null);
		panUpper.setBackground(null);
		panUpper.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		
		lblBookIcon = new JLabel(bookIcon);
		lblBookIcon.setBounds(130, 5, 50, 50);
		lblBookIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblTermsOfUse = new JLabel("<HTML><U>Terms of use</U></HTML>");
		lblTermsOfUse.setBounds(180, 5, 100, 50);
		lblTermsOfUse.setForeground(Color.WHITE);
		lblTermsOfUse.setFont(myFont);
		lblTermsOfUse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTermsOfUse.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://docdro.id/KNk4HoY"));
				}catch(Exception e1) {
					e1.printStackTrace();
				}
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

		lblPhoneIcon = new JLabel(phoneIcon);
		lblPhoneIcon.setBounds(500, 5, 50, 50);
		lblPhoneIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblContactUs = new JLabel("<HTML><U>Contact us: 012-xxxxxxx</U></HTML>");
		lblContactUs.setBounds(550, 5, 200, 50);
		lblContactUs.setForeground(Color.WHITE);
		lblContactUs.setFont(myFont);
		
		lblSystemName = new JLabel("PASAR MINI KMS SALES SYSTEM");
		lblSystemName.setBounds(635, 57, 130, 10);
		lblSystemName.setFont(new Font("Arial", Font.PLAIN, 8));
		lblSystemName.setForeground(Color.WHITE);
		lblSystemName.setBackground(Color.BLACK);
		
		panUpper.add(lblBookIcon);
		panUpper.add(lblTermsOfUse);
		panUpper.add(lblPhoneIcon);
		panUpper.add(lblContactUs);
		
		panFooter.add(panUpper);
		panFooter.add(lblSystemName);
		
		return panFooter;
	}
}
