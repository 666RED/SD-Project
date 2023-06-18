package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class Header extends JFrame{
	final static Color SYSTEM_NAME_COLOR = new Color(235, 40, 40);
	final static Color HEADER_BACKGROUND_COLOR = new Color(35, 26, 43);
	final static Color HEADER_HOVER_COLOR = new Color(190, 190, 190);
	
	public static JPanel setHeader(final JFrame myFrame, String navigation) {
		JPanel panHeader, panMiddle, panRight;
		JLabel lblSystemName, lblPurchase, lblInventory, lblReport, lblOwner, lblUserIcon, lblExitIcon;
		ImageIcon userIcon = IconClass.createIcon("Image\\profile.png", 30, 30);
		ImageIcon exitIcon = IconClass.createIcon("Image\\logout.png", 20, 22);
		
		lblUserIcon = new JLabel(userIcon);
		lblExitIcon = new JLabel(exitIcon);
		
		lblExitIcon.setBounds(0, 0, 20, 20);
		lblExitIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblExitIcon.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "LOGOUT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					myFrame.dispose();
					new LoginFrame();
				}
			}
			public void mousePressed(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}
		});
		
		lblSystemName = new JLabel("KMS");
		lblSystemName.setBounds(5, 5, 70, 30);
		lblSystemName.setFont(new Font("Arial", Font.BOLD, 30));
		lblSystemName.setForeground(SYSTEM_NAME_COLOR);
		
		lblPurchase = new JLabel("Purchase", SwingConstants.CENTER);
		lblPurchase.setBounds(0, 0, 140, 40);
		lblPurchase.setFont(new Font("Arial", Font.BOLD, 18));
		
		lblInventory = new JLabel("Inventory", SwingConstants.CENTER);
		lblInventory.setBounds(140, 0, 140, 40);
		lblInventory.setFont(new Font("Arial", Font.BOLD, 18));
		
		
		lblReport = new JLabel("Report", SwingConstants.CENTER);
		lblReport.setBounds(280, 0, 140, 40);
		lblReport.setFont(new Font("Arial", Font.BOLD, 18));

		
		determinePage(navigation, lblPurchase, lblInventory, lblReport, myFrame);
		
		panHeader = new JPanel();
		panHeader.setBounds(0, 0, 1400, 40);
		panHeader.setLayout(null);
		panHeader.setBackground(HEADER_BACKGROUND_COLOR);
		
		panMiddle = new JPanel();
		panMiddle.setBounds(700, 0, 420, 40);
		panMiddle.setLayout(null);
		panMiddle.setBackground(null);
		panMiddle.add(lblPurchase);
		panMiddle.add(lblInventory);
		panMiddle.add(lblReport);
		
		lblUserIcon.setBounds(0, 0, 30, 30);
		
		lblOwner = new JLabel("<HTML><U>owner</U></HTML>");
		lblOwner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblOwner.setBounds(45, 0, 35, 30);
		lblOwner.setFont(new Font("Arial", Font.BOLD, 12));
		lblOwner.setForeground(Color.WHITE);
		
		lblExitIcon.setBounds(100, 0, 20, 30);
		
		panRight = new JPanel();
		panRight.setBounds(1240, 5, 300, 30);
		panRight.setLayout(null);
		panRight.setBackground(null);
		panRight.add(lblUserIcon);
		panRight.add(lblOwner);
		panRight.add(lblExitIcon);
		
		panHeader.add(lblSystemName);
		panHeader.add(panMiddle);
		panHeader.add(panRight);
		
		return panHeader;
	}
	
	public static void determinePage(String navigation, final JLabel lblPurchase, final JLabel lblInventory, final JLabel lblReport, final JFrame myFrame) {
		if(navigation.equals("Purchase")) {
			lblPurchase.setForeground(Color.LIGHT_GRAY);
			lblInventory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblInventory.setForeground(Color.WHITE);
			lblInventory.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new InventoryFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblInventory.setOpaque(false);
					lblInventory.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblInventory.setOpaque(true);
					lblInventory.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			lblReport.setForeground(Color.WHITE);
			lblReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblReport.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new DailyReportFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblReport.setOpaque(false);
					lblReport.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblReport.setOpaque(true);
					lblReport.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		}else if(navigation.equals("Inventory")) {
			lblInventory.setForeground(Color.LIGHT_GRAY);
			lblPurchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblPurchase.setForeground(Color.WHITE);
			lblPurchase.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new PurchaseFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblPurchase.setOpaque(false);
					lblPurchase.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblPurchase.setOpaque(true);
					lblPurchase.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			lblReport.setForeground(Color.WHITE);
			lblReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblReport.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new DailyReportFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblReport.setOpaque(false);
					lblReport.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblReport.setOpaque(true);
					lblReport.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		}else if(navigation.equals("Report")) {
			lblReport.setForeground(Color.LIGHT_GRAY);
			lblInventory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblInventory.setForeground(Color.WHITE);
			lblInventory.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new InventoryFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblInventory.setOpaque(false);
					lblInventory.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblInventory.setOpaque(true);
					lblInventory.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			lblPurchase.setForeground(Color.WHITE);
			lblPurchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblPurchase.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					myFrame.dispose();
					new PurchaseFrame();
				}
				
				public void mousePressed(MouseEvent e) {
					
				}
				
				public void mouseExited(MouseEvent e) {
					lblPurchase.setOpaque(false);
					lblPurchase.setBackground(null);
				}
				
				public void mouseEntered(MouseEvent e) {
					lblPurchase.setOpaque(true);
					lblPurchase.setBackground(HEADER_HOVER_COLOR);
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
		}
		

	}
}
