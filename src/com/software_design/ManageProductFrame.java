package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ManageProductFrame extends JFrame{
	
	JPanel panHeader, panFooter, panContent, panFirstColumn, panSecondColumn;
	JLabel lblBackIcon,lblPageName, lblProductName, lblCost, lblPrice, lblBarcode, lblCategory, lblQuantity,
		   lblNotifyQuantity, lblSupplierName, lblSupplierPhoneNumber, lblSupplierLocation, lblUnit;
	JTextField tfProductName, tfCost, tfPrice, tfBarcode, tfQuantity, tfNotifyQuantity, tfSupplierPhoneNumber;
	JTextArea taSupplierLocation;
	JButton btnEditAndSave;
	JComboBox cbCategory, cbSupplierName, cbUnit;
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon savedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	Font labelFont = new Font("Arial", Font.PLAIN, 15);
	final Color CONFIRM_AND_SAVE_BUTTON_COLOR = new Color(246, 84, 70);
	
	public ManageProductFrame(String productBarcode) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Manage Product");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT p.product_barcode, p.product_name, p.product_cost, p.product_price, "
					   + "p.product_category, p.product_quantity, p.product_notification_quantity, "
					   + "p.product_unit, s.supplier_name, s.supplier_phone_number, s.supplier_location "
					   + "FROM product p "
					   + "INNER JOIN supplier s ON p.supplier_id = s.supplier_id "
					   + "WHERE p.product_barcode = \"" + productBarcode + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if(rs != null) {
				while(rs.next()) {
					lblBackIcon = new JLabel(backIcon);
					lblBackIcon.setBounds(15, 50, 30, 30);
					lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblBackIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							dispose();
							new InventoryFrame();
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
					panContent = new JPanel();
					panContent.setBounds(15, 90, 1340, 530);
					panContent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
					panContent.setLayout(null);
					
					lblPageName = new JLabel("Manage Product");
					lblPageName.setBounds(0, 10, 250, 30);
					lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
					lblPageName.setForeground(Color.BLACK);
					
					panFirstColumn = new JPanel();
					panFirstColumn.setBounds(0, 70, 420, 400);
					panFirstColumn.setLayout(null);
					
					lblProductName = new JLabel("Product Name:");
					lblProductName.setBounds(0, 10, 100, 20);
					lblProductName.setFont(labelFont);
					
					tfProductName = new JTextField(rs.getString("product_name"));
					tfProductName.setBounds(110, 0, 310, 40);
					tfProductName.setFont(labelFont);
					
					lblCost = new JLabel("Cost (RM)      :");
					lblCost.setBounds(0, 60, 100, 20);
					lblCost.setFont(labelFont);
					
					tfCost = new JTextField(String.valueOf(rs.getDouble("product_cost")));
					tfCost.setBounds(110, 50, 310, 40);
					tfCost.setFont(labelFont);
					
					lblPrice = new JLabel("Price (RM)     :");
					lblPrice.setBounds(0, 110, 100, 20);
					lblPrice.setFont(labelFont);
					
					tfPrice = new JTextField(String.valueOf(rs.getDouble("product_price")));
					tfPrice.setBounds(110, 100, 310, 40);
					tfPrice.setFont(labelFont);
					
					lblBarcode = new JLabel("Barcode         :");
					lblBarcode.setBounds(0, 160, 100, 20);
					lblBarcode.setFont(labelFont);
					
					tfBarcode = new JTextField(rs.getString("product_barcode"));
					tfBarcode.setBounds(110, 150, 310, 40);
					tfBarcode.setFont(labelFont);
					tfBarcode.setEditable(false);
					tfBarcode.setBorder(BorderFactory.createLineBorder(Color.GRAY));
					tfBarcode.setBackground(Color.WHITE);	
					tfBarcode.setToolTipText("Cannot change the product barcode");
					
					lblCategory = new JLabel("Category        :");
					lblCategory.setBounds(0, 210, 100, 20);
					lblCategory.setFont(labelFont);
					
					String[] categoryList = {"Beverage", "Food", "Daily Supplies"};
					cbCategory = new JComboBox(categoryList);
					cbCategory.setBounds(110, 200, 310, 40);
					cbCategory.setBackground(Color.WHITE);
					cbCategory.setFont(labelFont);
					for(int i = 0; i < categoryList.length; i++) {
						if(categoryList[i].equals(rs.getString("product_category"))) {
							cbCategory.setSelectedIndex(i);
							break;
						}
					}
					
					panFirstColumn.add(lblProductName);
					panFirstColumn.add(tfProductName);
					panFirstColumn.add(lblCost);
					panFirstColumn.add(tfCost);
					panFirstColumn.add(lblPrice);
					panFirstColumn.add(tfPrice);
					panFirstColumn.add(lblBarcode);
					panFirstColumn.add(tfBarcode);
					panFirstColumn.add(lblCategory);
					panFirstColumn.add(cbCategory);
					
					panSecondColumn = new JPanel();
					panSecondColumn.setBounds(450, 70, 890, 400);
					panSecondColumn.setLayout(null);
					
					lblQuantity = new JLabel("Quantity                         :");
					lblQuantity.setBounds(0, 10, 160, 20);
					lblQuantity.setFont(labelFont);
					
					tfQuantity = new JTextField(String.valueOf(rs.getInt("product_quantity")));
					tfQuantity.setBounds(170, 0, 310, 40);
					tfQuantity.setFont(labelFont);
					
					lblNotifyQuantity = new JLabel("<html>Notify when quantity<br/>less than &emsp &emsp &emsp &emsp &nbsp &nbsp:</html>");
					lblNotifyQuantity.setBounds(0, 55, 160, 30);
					lblNotifyQuantity.setFont(labelFont);
					
					tfNotifyQuantity = new JTextField(String.valueOf(rs.getInt("product_notification_quantity")));
					tfNotifyQuantity.setBounds(170, 50, 310, 40);
					tfNotifyQuantity.setFont(labelFont);
					
					lblSupplierName = new JLabel("Supplier Name              :");
					lblSupplierName.setBounds(0, 110, 160, 20);
					lblSupplierName.setFont(labelFont);
					
					String[] supplierList = retrieveSupplierList();
					if(supplierList != null) {
						cbSupplierName = new JComboBox(supplierList);
						cbSupplierName.setBounds(170, 100, 310, 40);
						cbSupplierName.setBackground(Color.WHITE);
						cbSupplierName.setFont(labelFont);
					}
					
					for(int i = 0; i < supplierList.length; i++) {
						if(supplierList[i].equals(rs.getString("supplier_name"))) {
							cbSupplierName.setSelectedIndex(i);
						}
					}
					
					lblSupplierPhoneNumber = new JLabel("Supplier Phone Number:");
					lblSupplierPhoneNumber.setBounds(0, 160, 160, 20);
					lblSupplierPhoneNumber.setFont(labelFont);
					
					tfSupplierPhoneNumber = new JTextField();
					tfSupplierPhoneNumber.setBounds(170, 150, 310, 40);
					tfSupplierPhoneNumber.setFont(labelFont);
					
					lblSupplierLocation = new JLabel("Supplier Location           :");
					lblSupplierLocation.setBounds(0, 230, 160, 20);
					lblSupplierLocation.setFont(labelFont);
					
					taSupplierLocation = new JTextArea(5, 10);
					taSupplierLocation.setLineWrap(true);
					taSupplierLocation.setBounds(170, 200, 310, 80);
					taSupplierLocation.setFont(labelFont);
					taSupplierLocation.setBorder(BorderFactory.createLineBorder(Color.GRAY));
					
					retrieveSupplierInfo();
					
					cbSupplierName.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							retrieveSupplierInfo();
						}
					});
					
					lblUnit = new JLabel("Unit :");
					lblUnit.setBounds(510, 10, 40, 20);
					lblUnit.setFont(labelFont);
					
					String[] unitList = {"Piece", "Box", "Bootle"};
					cbUnit = new JComboBox(unitList);
					cbUnit.setBounds(550, 0, 310, 40);
					cbUnit.setBackground(Color.WHITE);
					cbUnit.setFont(labelFont);
					for(int i = 0; i < unitList.length; i++) {
						if(unitList[i].equals(rs.getString("product_unit"))) {
							cbUnit.setSelectedIndex(i);
							break;
						}
					}
					
					panSecondColumn.add(lblQuantity);		
					panSecondColumn.add(tfQuantity);		
					panSecondColumn.add(lblNotifyQuantity);		
					panSecondColumn.add(tfNotifyQuantity);		
					panSecondColumn.add(lblSupplierName);		
					panSecondColumn.add(cbSupplierName);		
					panSecondColumn.add(lblSupplierPhoneNumber);		
					panSecondColumn.add(tfSupplierPhoneNumber);		
					panSecondColumn.add(lblSupplierLocation);		
					panSecondColumn.add(taSupplierLocation);		
					panSecondColumn.add(lblUnit);		
					panSecondColumn.add(cbUnit);	
					
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
					panContent.add(panFirstColumn);
					panContent.add(panSecondColumn);
					panContent.add(btnEditAndSave);
					
					panHeader = Header.setHeader(this, "Inventory");
					panFooter = Footer.setFooter(this);
					
					add(panHeader);
					add(panFooter);
					add(lblBackIcon);
					add(panContent);
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
		
		setVisible(true);
	}
	
	public String[] retrieveSupplierList() {
		Connection conn = null;
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name "
					   + "FROM supplier;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			List<String> categoryArrayList = new ArrayList<String>();
			
			if(rs != null) {
				while(rs.next()) {
					categoryArrayList.add(rs.getString("supplier_name"));
				}
			}
			
			String[] categoryList = new String[categoryArrayList.size()];
			categoryArrayList.toArray(categoryList);
			
			return categoryList;
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
		return null;
	}
	
	public void retrieveSupplierInfo() {
		Connection conn = null;
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_phone_number, supplier_location "
					   + "FROM supplier "
					   + "WHERE supplier_name = \"" + (String)cbSupplierName.getSelectedItem() + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
						
			if(rs != null) {
				while(rs.next()) {
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
	
	public void validateInput() {
		boolean incompleteInput = false;
		
		boolean validProductCost = true;
		boolean validProductPrice = true;
		boolean validProductQuantity = true;
		boolean validProductNotifyQuantity = true;
		
		boolean spacedName = Pattern.matches("\\s*", tfProductName.getText());
		boolean spaceBeforeName = Pattern.matches("\\s+.*", tfProductName.getText());
		
		boolean validCost1 = Pattern.matches("[0-9]*[.][0-9]{2}", tfCost.getText());
		boolean validCost2 = Pattern.matches("[0-9]*", tfCost.getText());
		boolean validCost3 = Pattern.matches("[0-9]*[.][0-9]{1}", tfCost.getText());
		boolean validCost4 = Pattern.matches("[0-9]*[.]", tfCost.getText());
		
		boolean validPrice1 = Pattern.matches("[0-9]*[.][0-9]{2}", tfPrice.getText());
		boolean validPrice2 = Pattern.matches("[0-9]*", tfPrice.getText());
		boolean validPrice3 = Pattern.matches("[0-9]*[.][0-9]{1}", tfPrice.getText());
		boolean validPrice4 = Pattern.matches("[0-9]*[.]", tfPrice.getText());
		
		if(spacedName || spaceBeforeName) {
			incompleteInput = true;
			tfProductName.setBorder(BorderFactory.createLineBorder(Color.RED));
		}else {
			tfProductName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(tfCost.getText().equals("")) {
			incompleteInput = true;
			tfCost.setBorder(BorderFactory.createLineBorder(Color.RED));
		}else if(!validCost1 && !validCost2 && !validCost3 && !validCost4) {
			validProductCost = false;
		}else {
			tfCost.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(tfPrice.getText().equals("")) {
			incompleteInput = true;
			tfPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
		}else if(!validPrice1 && !validPrice2 && !validPrice3 && !validPrice4) {
			validProductPrice = false;
		}else {
			tfPrice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(tfQuantity.getText().equals("")) {
			incompleteInput = true;
			tfQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
		}else if(!Pattern.matches("[0-9]*", tfQuantity.getText())) {
			validProductQuantity = false;
		}else {
			tfQuantity.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(tfNotifyQuantity.getText().equals("")) {
			incompleteInput = true;
			tfNotifyQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
		}else if(!Pattern.matches("[0-9]*", tfNotifyQuantity.getText())) {
			validProductNotifyQuantity = false;
		}else {
			tfNotifyQuantity.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		if(incompleteInput) {
			JOptionPane.showMessageDialog(this, "Please enter all product information", "Incomplete Input", JOptionPane.WARNING_MESSAGE);
			return;
		}else if(!validProductCost) {
			tfCost.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid product cost", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else if(!validProductPrice) {
			tfPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid product price", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else if(!validProductQuantity) {
			tfQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid product quantity", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else if(!validProductNotifyQuantity) {
			tfNotifyQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Invalid product notify quantity", "Invalid Input", JOptionPane.WARNING_MESSAGE);
		}else if(Double.parseDouble(tfCost.getText()) >= Double.parseDouble(tfPrice.getText())){
			tfCost.setBorder(BorderFactory.createLineBorder(Color.RED));
			tfPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Product cost could not greater than or equal to product price", "Cost >= Price", JOptionPane.WARNING_MESSAGE);
		}else if(Double.parseDouble(tfNotifyQuantity.getText()) == 0) {
			tfNotifyQuantity.setBorder(BorderFactory.createLineBorder(Color.RED));
			JOptionPane.showMessageDialog(this, "Product notify quantity should greater than 0", "Notify quantity = 0", JOptionPane.WARNING_MESSAGE);
		}else {
			saveData();		
		}
	}

	public void saveData() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "UPDATE product "
					   + "SET product_name = \'" + tfProductName.getText() + "\', "
					   + "product_cost = ?, "
					   + "product_price = ?, "
					   + "product_category = \'" + (String)cbCategory.getSelectedItem() + "\', "
					   + "product_quantity = ?, "
					   + "product_notification_quantity = ?, "
					   + "product_unit = \'" + (String)cbUnit.getSelectedItem() + "\', "
					   + "supplier_id = (SELECT supplier_id FROM supplier "
					   + "WHERE supplier_name = \"" + (String)cbSupplierName.getSelectedItem() + "\") "
					   + "WHERE product_barcode = \'" + tfBarcode.getText() + "\';";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, Double.parseDouble(tfCost.getText()));
			stmt.setDouble(2, Double.parseDouble(tfPrice.getText()));
			stmt.setInt(3, Integer.parseInt(tfQuantity.getText()));
			stmt.setInt(4, Integer.parseInt(tfNotifyQuantity.getText()));
			stmt.executeUpdate();
			
			int response = JOptionPane.showConfirmDialog(null, "Product updated successfully", "Updated", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, savedIcon);

			if(response == JOptionPane.YES_OPTION) {
				dispose();
				new InventoryFrame();
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
}