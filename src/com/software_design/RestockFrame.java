package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.sql.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.toedter.calendar.JDateChooser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.awt.*;

public class RestockFrame extends JFrame{
	JLabel lblBackIcon, lblRestockInfo, lblRestockProduct, lblSupplier, lblPhoneNumber, lblLocation, lblInvoiceNo,
		   lblRestockFee, lblOrderPlaced, lblOrderReceived, lblHistoryIcon, lblRestockHistory, lblTotal, lblSearchIcon;
	JLabel lblTitleNo, lblTitleName, lblTitleUnit, lblTitleQuantityPerUnit, lblTitleQuantity, lblTitleSinglePrice, lblTitleTotalPrice;
	JPanel panContent, panHeader, panFooter, panRestockInfo, panRestockProduct, panTitleRow, panProductContainer, 
		   panSearchBar, panSearchResult;
	JButton btnClearAll, btnConfirmAndSave, btnViewSupplier;
	JTextField tfPhoneNumber, tfInvoiceNo, tfRestockFee, tfTotal, tfSearchBar;
	JTextArea taLocation;
	JComboBox<String> cbSupplierName;
	JDateChooser orderPlacedDateChooser, orderReceivedDateChooser;
	JScrollPane spProduct, spSearch;
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon dustbinIcon = IconClass.createIcon("Image\\dustbin.png", 20, 20);
	ImageIcon decreaseIcon = IconClass.createIcon("Image\\minus.png", 20, 20);
	ImageIcon increaseIcon = IconClass.createIcon("Image\\plus2.png", 20, 20);
	ImageIcon historyIcon = IconClass.createIcon("Image\\history.png", 30, 30);
	ImageIcon searchIcon = IconClass.createIcon("Image\\loupe.png", 20, 20);
	ImageIcon savedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	
	final Color CONFIRM_AND_SAVE_BUTTON_COLOR = new Color(246, 84, 70);
	final Color VIEW_SUPPLIER_BUTTON_COLOR = new Color(30, 158, 58);
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color PRODUCT_NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color PRODUCT_BORDER_COLOR = new Color(168, 168, 168);
	final Color TITLE_BORDER_COLOR = new Color(140, 140, 140);
	
	Font regularText = new Font("Arial", Font.PLAIN, 18);
	Font titleFont = new Font("Arial", Font.BOLD, 18);
	
	int numberOfProductAdded = 0;
	double sum = 0;
	
	private List<RestockProductInfo> restockProductInfoList = new ArrayList<RestockProductInfo>(); 
	private List<String> barcodeList = new ArrayList<String>();
	
	// for adding new restock product
	
	JPanel panProductName, panQuantityPerUnit, panQuantity;
	JLabel lblProductNo, lblDustbinIcon, lblDecreaseIcon1, lblIncreaseIcon1, lblDecreaseIcon2, lblIncreaseIcon2;
	
	public RestockFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Restock");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(15, 50, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {

			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new InventoryFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		panContent = new JPanel();
		panContent.setBounds(15, 90, 1340, 540);
		panContent.setLayout(null);
		
		panRestockInfo = new JPanel();
		panRestockInfo.setBounds(0, 0, 469, 490);
		panRestockInfo.setLayout(null);
		panRestockInfo.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		
		lblRestockInfo = new JLabel("Restock Information");
		lblRestockInfo.setBounds(0, 25, 300, 30);
		lblRestockInfo.setFont(new Font("Arial", Font.BOLD, 30));
		lblRestockInfo.setForeground(Color.BLACK);
		
		btnViewSupplier = new JButton("View Supplier");
		btnViewSupplier.setForeground(Color.WHITE);
		btnViewSupplier.setFont(new Font("Arial", Font.BOLD, 20));
		btnViewSupplier.setFocusable(false);
		btnViewSupplier.setBackground(VIEW_SUPPLIER_BUTTON_COLOR);
		btnViewSupplier.setBounds(300, 20, 169, 40);	
		btnViewSupplier.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnViewSupplier.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ViewSupplierFrame();
			}
		});

		lblSupplier = new JLabel("Supplier            :");
		lblSupplier.setBounds(0, 85, 140, 20);
		lblSupplier.setFont(regularText);
		lblSupplier.setForeground(Color.BLACK);
		
		String[] supplierList = retrieveSupplierList();
		if(supplierList != null) {
			cbSupplierName = new JComboBox<String>(supplierList);
			cbSupplierName.setBounds(140, 80, 329, 30);
			cbSupplierName.setBackground(Color.WHITE);
			cbSupplierName.setForeground(Color.BLACK);
			cbSupplierName.setFocusable(false);
			cbSupplierName.setFont(regularText);
			ConfigureComboBox.addHorizontalScrollBar(cbSupplierName);
		}
		
		lblPhoneNumber = new JLabel("Phone Number :");
		lblPhoneNumber.setBounds(0, 135, 140, 20);
		lblPhoneNumber.setFont(regularText);
		lblPhoneNumber.setForeground(Color.BLACK);
		
		tfPhoneNumber = new JTextField();
		tfPhoneNumber.setBounds(140, 130, 329, 30);
		tfPhoneNumber.setEditable(false);
		tfPhoneNumber.setForeground(Color.BLACK);
		tfPhoneNumber.setBackground(Color.WHITE);
		tfPhoneNumber.setFont(regularText);
		tfPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		lblLocation = new JLabel("Location           :");
		lblLocation.setBounds(0, 205, 140, 20);
		lblLocation.setFont(regularText);
		lblLocation.setForeground(Color.BLACK);
		
		taLocation = new JTextArea();
		taLocation.setBounds(140, 180, 329, 70);
		taLocation.setEditable(false);
		taLocation.setForeground(Color.BLACK);
		taLocation.setBackground(Color.WHITE);
		taLocation.setLineWrap(true);
		taLocation.setWrapStyleWord(true);
		taLocation.setFont(regularText);
		taLocation.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		retrieveSupplierInfo();
		
		cbSupplierName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retrieveSupplierInfo();
			}
		});
		
		lblInvoiceNo = new JLabel("Invoice No        :");
		lblInvoiceNo.setBounds(0, 275, 140, 20);
		lblInvoiceNo.setFont(regularText);
		lblInvoiceNo.setForeground(Color.BLACK);
		
		tfInvoiceNo = new JTextField();
		tfInvoiceNo.setBounds(140, 270, 329, 30);
		tfInvoiceNo.setFont(regularText);
		tfInvoiceNo.setForeground(Color.BLACK);
		tfInvoiceNo.setBackground(Color.WHITE);
		tfInvoiceNo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		lblRestockFee = new JLabel("Restock Fee     :");
		lblRestockFee.setBounds(0, 325, 140, 20);
		lblRestockFee.setFont(regularText);
		lblRestockFee.setForeground(Color.BLACK);
		
		tfRestockFee = new JTextField();
		tfRestockFee.setBounds(140, 320, 329, 30);
		tfRestockFee.setFont(regularText);
		tfRestockFee.setForeground(Color.BLACK);
		tfRestockFee.setBackground(Color.WHITE);
		tfRestockFee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		lblOrderPlaced = new JLabel("Order Placed    :");
		lblOrderPlaced.setBounds(0, 375, 140, 20);
		lblOrderPlaced.setFont(regularText);
		lblOrderPlaced.setForeground(Color.BLACK);
		
		orderPlacedDateChooser = new JDateChooser();
		orderPlacedDateChooser.setBounds(140, 370, 329, 30);
		orderPlacedDateChooser.setDateFormatString("yyyy-MM-dd");
		orderPlacedDateChooser.setFont(regularText);
		
		lblOrderReceived = new JLabel("Order Received:");
		lblOrderReceived.setBounds(0, 425, 140, 20);
		lblOrderReceived.setFont(regularText);
		lblOrderReceived.setForeground(Color.BLACK);
		
		orderReceivedDateChooser = new JDateChooser();
		orderReceivedDateChooser.setBounds(140, 420, 329, 30);
		orderReceivedDateChooser.setDateFormatString("yyyy-MM-dd");
		orderReceivedDateChooser.setFont(regularText);
		
		panRestockInfo.add(lblRestockInfo);
		panRestockInfo.add(btnViewSupplier);
		panRestockInfo.add(lblSupplier);
		panRestockInfo.add(cbSupplierName);
		panRestockInfo.add(lblPhoneNumber);
		panRestockInfo.add(tfPhoneNumber);
		panRestockInfo.add(lblLocation);
		panRestockInfo.add(taLocation);
		panRestockInfo.add(lblInvoiceNo);
		panRestockInfo.add(tfInvoiceNo);
		panRestockInfo.add(lblRestockFee);
		panRestockInfo.add(tfRestockFee);
		panRestockInfo.add(lblOrderPlaced);
		panRestockInfo.add(orderPlacedDateChooser);
		panRestockInfo.add(lblOrderReceived);
		panRestockInfo.add(orderReceivedDateChooser);
		
		panRestockProduct = new JPanel();
		panRestockProduct.setBounds(469, 0, 871, 490);
		panRestockProduct.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		panRestockProduct.setLayout(null);
		
		lblRestockProduct = new JLabel("Restock Product");
		lblRestockProduct.setBounds(50, 25, 300, 30);
		lblRestockProduct.setFont(new Font("Arial", Font.BOLD, 30));
		lblRestockProduct.setForeground(Color.BLACK);
		
		lblHistoryIcon = new JLabel(historyIcon);
		lblHistoryIcon.setBounds(650, 25, 30, 30);
		
		lblRestockHistory = new JLabel("<HTML><U>Restock History</U></HTML>");
		lblRestockHistory.setForeground(Color.BLUE);
		lblRestockHistory.setFont(new Font("Arial", Font.PLAIN, 20));
		lblRestockHistory.setBounds(685, 25, 150, 30);
		lblRestockHistory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRestockHistory.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new RestockHistoryFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		panSearchResult = new JPanel();
		panSearchResult.setBounds(45, 100, 370, 200);
		panSearchResult.setLayout(null);
		panSearchResult.setVisible(false);
		
		spSearch = new JScrollPane(new JPanel());
		spSearch.setBounds(0, 0, 370, 200);
		spSearch.getVerticalScrollBar().setUnitIncrement(16);
		
		panSearchBar = new JPanel();
		panSearchBar.setLayout(null);
		panSearchBar.setBounds(50, 60, 350, 35);
		panSearchBar.setBorder(BorderFactory.createLineBorder(TITLE_ROW_BACKGROUND_COLOR));
		panSearchBar.setBackground(Color.WHITE);
		
		lblSearchIcon = new JLabel(searchIcon);
		lblSearchIcon.setBounds(5, 8, 20, 20);
		
		tfSearchBar = new JTextField("Product Name / Barcode");
		tfSearchBar.setBounds(35, 5, 310, 25);
		tfSearchBar.setFont(new Font("Arial", Font.PLAIN, 20));
		tfSearchBar.setBorder(null);
		tfSearchBar.setForeground(Color.GRAY);
		tfSearchBar.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchProduct(tfSearchBar.getText(), spSearch);
				revalidate();
				repaint();
			}
			
			public void insertUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchProduct(tfSearchBar.getText(), spSearch);
				revalidate();
				repaint();			
			}
			
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
		
		tfSearchBar.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(tfSearchBar.getText().equals("")) {
					tfSearchBar.setText("Product Name / Barcode");
					tfSearchBar.setForeground(Color.GRAY);
				}
				panSearchResult.setVisible(false);
			}
			
			public void focusGained(FocusEvent e) {
				if(tfSearchBar.getText().equals("Product Name / Barcode")) {
					tfSearchBar.setText("");
					tfSearchBar.setForeground(Color.BLACK);
				}
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchProduct(tfSearchBar.getText(), spSearch);
			}
		});
		
		panSearchBar.add(lblSearchIcon);
		panSearchBar.add(tfSearchBar);
		
		panSearchResult.add(spSearch);
		
		panTitleRow = new JPanel();
		panTitleRow.setBounds(50, 100, 821, 30);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		panTitleRow.setLayout(null);
		
		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 40, 30);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleName = new JLabel("Name");
		lblTitleName.setBounds(40, 0, 285, 30);
		lblTitleName.setFont(titleFont);
		lblTitleName.setForeground(Color.BLACK);
		lblTitleName.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleUnit = new JLabel("Unit", SwingConstants.CENTER);
		lblTitleUnit.setBounds(325, 0, 85, 30);
		lblTitleUnit.setFont(titleFont);
		lblTitleUnit.setForeground(Color.BLACK);
		
		lblTitleQuantityPerUnit = new JLabel("Qty / Unit", SwingConstants.CENTER);
		lblTitleQuantityPerUnit.setBounds(409, 0, 101, 30);
		lblTitleQuantityPerUnit.setFont(titleFont);
		lblTitleQuantityPerUnit.setForeground(Color.BLACK);
		lblTitleQuantityPerUnit.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleQuantity = new JLabel("Qty", SwingConstants.CENTER);
		lblTitleQuantity.setBounds(510, 0, 100, 30);
		lblTitleQuantity.setFont(titleFont);
		lblTitleQuantity.setForeground(Color.BLACK);
		
		lblTitleSinglePrice = new JLabel("Price", SwingConstants.CENTER);
		lblTitleSinglePrice.setBounds(610, 0, 105, 30);
		lblTitleSinglePrice.setFont(titleFont);
		lblTitleSinglePrice.setForeground(Color.BLACK);
		lblTitleSinglePrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleTotalPrice = new JLabel("Total", SwingConstants.CENTER);
		lblTitleTotalPrice.setBounds(715, 0, 106, 30);
		lblTitleTotalPrice.setFont(titleFont);
		lblTitleTotalPrice.setForeground(Color.BLACK);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleName);
		panTitleRow.add(lblTitleUnit);
		panTitleRow.add(lblTitleQuantityPerUnit);
		panTitleRow.add(lblTitleQuantity);
		panTitleRow.add(lblTitleSinglePrice);
		panTitleRow.add(lblTitleTotalPrice);
		
		panProductContainer = new JPanel();
		panProductContainer.setBounds(50, 130, 821, 290);
		panProductContainer.setLayout(null);
		
		spProduct = new JScrollPane(new JPanel());
		spProduct.setBounds(0, 0, 821, 290);
		spProduct.setHorizontalScrollBar(null);
		spProduct.getVerticalScrollBar().setUnitIncrement(16);
		
		panProductContainer.add(spProduct);
		
		lblTotal = new JLabel("Total :");
		lblTotal.setBounds(670, 430, 100, 40);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 25));
		lblTotal.setForeground(Color.BLACK);
		
		tfTotal = new JTextField();
		tfTotal.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotal.setFont(new Font("Arial", Font.PLAIN, 20));
		tfTotal.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		tfTotal.setForeground(Color.BLACK);
		tfTotal.setBounds(745, 430, 126, 40);
		tfTotal.setEditable(false);
		tfTotal.setBorder(null);
		
		panRestockProduct.add(lblRestockProduct);
		panRestockProduct.add(lblHistoryIcon);
		panRestockProduct.add(lblRestockHistory);
		panRestockProduct.add(panSearchBar);
		panRestockProduct.add(panSearchResult);
		panRestockProduct.add(panTitleRow);
		panRestockProduct.add(panProductContainer);
		panRestockProduct.add(lblTotal);
		panRestockProduct.add(tfTotal);
	
		btnClearAll = new JButton("Clear All");
		btnClearAll.setForeground(Color.WHITE);
		btnClearAll.setFont(new Font("Arial", Font.BOLD, 25));
		btnClearAll.setFocusable(false);
		btnClearAll.setBackground(Color.RED);
		btnClearAll.setBounds(0, 490, 200, 50);
		btnClearAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnConfirmAndSave = new JButton("Confirm and Save");
		btnConfirmAndSave.setForeground(Color.WHITE);
		btnConfirmAndSave.setFont(new Font("Arial", Font.BOLD, 25));
		btnConfirmAndSave.setFocusable(false);
		btnConfirmAndSave.setBackground(CONFIRM_AND_SAVE_BUTTON_COLOR);
		btnConfirmAndSave.setBounds(1090, 490, 250, 50);
		btnConfirmAndSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfirmAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateRestockInfo();
			}
		});
		
		panContent.add(panRestockInfo);
		panContent.add(panRestockProduct);
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
	
	public String[] retrieveSupplierList() {
		Connection conn = null;
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name "
					   + "FROM supplier "
					   + "WHERE supplier_id != 1";
			
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
					tfPhoneNumber.setText(rs.getString("supplier_phone_number"));
					taLocation.setText(rs.getString("supplier_location"));
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
	
	public void validateRestockInfo() {
		
		boolean validInvoiceNo = Pattern.matches("[0-9]+", tfInvoiceNo.getText());
		boolean validRestockFee1 = Pattern.matches("[0-9]*[.]{1}[0-9]{2}", tfRestockFee.getText());
		boolean validRestockFee2 = Pattern.matches("[0-9]*[.]{1}[0-9]{1}", tfRestockFee.getText());
		boolean validRestockFee3 = Pattern.matches("[0-9]*", tfRestockFee.getText());
		
		if(tfInvoiceNo.getText().equals("") || tfRestockFee.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter all restock information", "INCOMPLETE RESTOCK INFORMATION", JOptionPane.WARNING_MESSAGE);
		}else if(!validInvoiceNo) {
			JOptionPane.showMessageDialog(null, "Invalid invoice No", "INVALID RESTOCK INFORMATION", JOptionPane.WARNING_MESSAGE);
		}else if(!validRestockFee1 && !validRestockFee2 && !validRestockFee3) {
			JOptionPane.showMessageDialog(null, "Invalid restock fee", "INVALID RESTOCK INFORMATION", JOptionPane.WARNING_MESSAGE);
		}else if(!String.format("%.2f", Double.parseDouble(tfRestockFee.getText())).equals(tfTotal.getText().substring(3))) {
			JOptionPane.showMessageDialog(null, "Restock fee and Total are not the same", "NOT EQUAL", JOptionPane.WARNING_MESSAGE);
		}else {
			Connection conn = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
				
				String sql = "SELECT invoice_no "
						   + "FROM restock;";
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs != null) {
					while(rs.next()) {
						if(rs.getInt("invoice_no") == Integer.parseInt(tfInvoiceNo.getText())) {
							JOptionPane.showMessageDialog(null, "This invoice No is duplicated", "DUPLICATE INVOICE NO", JOptionPane.WARNING_MESSAGE);
							return;
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
			saveData();
		}
	}
	
	public void saveData() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String formattedDate = dateFormat.format(orderPlacedDateChooser.getDate());
			Date orderPlacedDate = java.sql.Date.valueOf(formattedDate);
			
			formattedDate = dateFormat.format(orderReceivedDateChooser.getDate());
			Date orderReceivedDate = java.sql.Date.valueOf(formattedDate);
			
			String sql = "INSERT INTO restock "
					   + "VALUES(\"" + tfInvoiceNo.getText() + "\", \"" + orderPlacedDate + "\", \"" + orderReceivedDate + "\", "
					   + "?, \"" + (String)cbSupplierName.getSelectedItem() + "\", \"" + taLocation.getText() + "\");";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, Double.parseDouble(tfTotal.getText().substring(3)));
			pstmt.executeUpdate();
			
			int invoiceNo = Integer.parseInt(tfInvoiceNo.getText());
			
			for(int i = 0; i < restockProductInfoList.size(); i++) {
				String productBarcode = restockProductInfoList.get(i).getProductBarcode();
				String productName = restockProductInfoList.get(i).getProductName();
				String productUnit = restockProductInfoList.get(i).getUnit();
				int quantityPerUnit = restockProductInfoList.get(i).getQuantityPerUnit();
				int productQuantity = restockProductInfoList.get(i).getQuantity();
				double productPrice = restockProductInfoList.get(i).getPrice();
				double productTotal = restockProductInfoList.get(i).getTotal();
				
				sql = "INSERT INTO restock_product "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, productBarcode);
				pstmt.setInt(2, invoiceNo);
				pstmt.setString(3, productName);
				pstmt.setString(4, productUnit);
				pstmt.setInt(5, quantityPerUnit);
				pstmt.setInt(6, productQuantity);
				pstmt.setDouble(7, productPrice);
				pstmt.setDouble(8, productTotal);
				pstmt.executeUpdate();
			}
			int response = JOptionPane.showConfirmDialog(null, "Date saved successfully", "SAVED", JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.PLAIN_MESSAGE, savedIcon);

			if(response == JOptionPane.YES_OPTION) {
				manipulateInventory();
				dispose();
				new RestockHistoryFrame();
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
	
	public void addNewProductRow(JScrollPane spProduct, String productBarcode) {
		final JPanel panProductView = (JPanel)spProduct.getViewport().getView();
		int productViewHeight = 280;
		
		if(numberOfProductAdded == 10) {
			productViewHeight = 318;
		}else if(numberOfProductAdded > 10) {
			productViewHeight = 318 + (numberOfProductAdded - 10) * 29;
		}
		
		panProductView.setPreferredSize(new Dimension(821, productViewHeight));
		panProductView.setBackground(Color.WHITE);
		panProductView.setLayout(new FlowLayout(FlowLayout.LEADING, -1, -1));
				
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_name, product_cost "
					   + "FROM product "
					   + "WHERE product_barcode = \"" + productBarcode + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					
					final JPanel panProductRow = new JPanel();
					panProductRow.setPreferredSize(new Dimension(821, 30));
					panProductRow.setLayout(null);
					panProductRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRODUCT_BORDER_COLOR));
					
					final String productName = rs.getString("product_name");
					final double productPrice = rs.getDouble("product_cost");
					
					final JLabel lblProductPrice = new JLabel("RM " + String.format("%.2f", productPrice));
					lblProductPrice.setHorizontalAlignment(SwingConstants.CENTER);
					lblProductPrice.setBounds(610, 0, 105, 30);
					lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, PRODUCT_BORDER_COLOR));
					lblProductPrice.setBackground(Color.WHITE);
					lblProductPrice.setForeground(Color.BLACK);
					lblProductPrice.setFont(regularText);
					lblProductPrice.setOpaque(true);
				
					final JLabel lblProductTotal = new JLabel("RM " + String.format("%.2f", productPrice));
					lblProductTotal.setHorizontalAlignment(SwingConstants.CENTER);
					lblProductTotal.setBounds(715, 0, 106, 30);
					lblProductTotal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, PRODUCT_BORDER_COLOR));
					lblProductTotal.setBackground(Color.WHITE);
					lblProductTotal.setForeground(Color.BLACK);
					lblProductTotal.setFont(regularText);
					lblProductTotal.setOpaque(true);
					
					final JLabel lblQuantityPerUnit = new JLabel("1");
					lblQuantityPerUnit.setBounds(45, 5, 20, 20);
					lblQuantityPerUnit.setFont(regularText);
					lblQuantityPerUnit.setForeground(Color.BLACK);
					
					final JLabel lblQuantity = new JLabel("1");
					lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
					lblQuantity.setBounds(35, 5, 30, 20);
					lblQuantity.setFont(regularText);
					lblQuantity.setForeground(Color.BLACK);
					
					final JLabel lblProductName = new JLabel(productName);
					lblProductName.setBounds(0, 0, 255, 30);
					lblProductName.setForeground(Color.BLACK);
					lblProductName.setFont(regularText);
					
					String[] unitList = {"Carton", "Box", "Crate", "Bundle"};
					
					final JComboBox<String> cbUnitList = new JComboBox<String>(unitList);
					cbUnitList.setBounds(325, 0, 85, 30);
					cbUnitList.setBackground(Color.WHITE);
					cbUnitList.setFont(regularText);
					cbUnitList.setFocusable(false);
					cbUnitList.setForeground(Color.BLACK);
					cbUnitList.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							for(int i = 0; i < restockProductInfoList.size(); i++) {
								if(restockProductInfoList.get(i).getProductName().equals(lblProductName.getText())) {
									restockProductInfoList.get(i).setUnit((String)cbUnitList.getSelectedItem());
								}
							}
						}
					});
					ConfigureComboBox.addHorizontalScrollBar(cbUnitList);
					
					lblProductNo = new JLabel(++numberOfProductAdded + ".", SwingConstants.CENTER);
					lblProductNo.setBounds(0, 0, 40, 30);
					lblProductNo.setFont(regularText);
					lblProductNo.setForeground(Color.BLACK);
					lblProductNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
					lblProductNo.setOpaque(true);
					
					panProductName = new JPanel();
					panProductName.setBounds(40, 0, 285, 30);
					panProductName.setLayout(null);
					panProductName.setBackground(Color.WHITE);
					panProductName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, PRODUCT_BORDER_COLOR));
					
					lblDustbinIcon = new JLabel(dustbinIcon);
					lblDustbinIcon.setBounds(250, 0, 30, 30);
					lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDustbinIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							int result = JOptionPane.showConfirmDialog(null, "Do you want to remove this product?\n" + lblProductName.getText(), "REMOVE PRODUCT", JOptionPane.DEFAULT_OPTION);
							if(result == JOptionPane.YES_OPTION) {
								updateRestockProductInfo(lblProductName.getText(), "!");
								sum = 0;
								updateProductScrollPane(panProductView);
							}
						}
						
						public void mouseExited(MouseEvent e) {
							
						}
						
						public void mouseEntered(MouseEvent e) {
							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panProductName.add(lblProductName);
					panProductName.add(lblDustbinIcon);
					
					panQuantityPerUnit = new JPanel();
					panQuantityPerUnit.setBounds(410, 0, 100, 30);
					panQuantityPerUnit.setLayout(null);
					panQuantityPerUnit.setBackground(Color.WHITE);
					panQuantityPerUnit.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, PRODUCT_BORDER_COLOR));
					
					lblDecreaseIcon1 = new JLabel(decreaseIcon);
					lblDecreaseIcon1.setBounds(10, 5, 20, 20);
					lblDecreaseIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDecreaseIcon1.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							if(Integer.parseInt(lblQuantityPerUnit.getText()) <= 1) {
								JOptionPane.showMessageDialog(null, "Quantity per unit must be a positive value", "INVALID QUANTITY PER UNIT", JOptionPane.WARNING_MESSAGE);
								return;
							}
							lblQuantityPerUnit.setText(String.valueOf(Integer.parseInt(lblQuantityPerUnit.getText()) - 1));
							updateRestockProductInfo(lblProductName.getText(), "--");
							
						}
						
						public void mouseExited(MouseEvent e) {
							
						}
						
						public void mouseEntered(MouseEvent e) {
							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					lblIncreaseIcon1 = new JLabel(increaseIcon);
					lblIncreaseIcon1.setBounds(70, 5, 20, 20);
					lblIncreaseIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblIncreaseIcon1.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							lblQuantityPerUnit.setText(String.valueOf(Integer.parseInt(lblQuantityPerUnit.getText()) + 1));
							updateRestockProductInfo(lblProductName.getText(), "++");
						}
						
						public void mouseExited(MouseEvent e) {
							
						}
						
						public void mouseEntered(MouseEvent e) {
							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panQuantityPerUnit.add(lblDecreaseIcon1);
					panQuantityPerUnit.add(lblQuantityPerUnit);
					panQuantityPerUnit.add(lblIncreaseIcon1);
					
					panQuantity = new JPanel();
					panQuantity.setBounds(510, 0, 100, 30);
					panQuantity.setLayout(null);
					panQuantity.setBackground(Color.WHITE);
					panQuantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRODUCT_BORDER_COLOR));
							
					lblDecreaseIcon2 = new JLabel(decreaseIcon);
					lblDecreaseIcon2.setBounds(10, 5, 20, 20);
					lblDecreaseIcon2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDecreaseIcon2.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							if(Integer.parseInt(lblQuantity.getText()) <= 1) {
								JOptionPane.showMessageDialog(null, "Product quantity must be a positive value", "INVALID PRODUCT QUANTITY", JOptionPane.WARNING_MESSAGE);
								return;
							}
							lblQuantity.setText(String.valueOf(Integer.parseInt(lblQuantity.getText()) - 1));
							calculateTotalPrice(lblProductTotal, lblProductPrice, lblQuantity, "-");
							updateRestockProductInfo(lblProductName.getText(), "-");
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
					
					lblIncreaseIcon2 = new JLabel(increaseIcon);
					lblIncreaseIcon2.setBounds(70, 5, 20, 20);
					lblIncreaseIcon2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblIncreaseIcon2.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							lblQuantity.setText(String.valueOf(Integer.parseInt(lblQuantity.getText()) + 1));
							retrieveProductTotal(lblProductName.getText(), lblProductTotal, Integer.parseInt(lblQuantity.getText()), "+");
							calculateTotalPrice(lblProductTotal, lblProductPrice, lblQuantity, "+");
							updateRestockProductInfo(lblProductName.getText(), "+");
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
					
					panQuantity.add(lblDecreaseIcon2);
					panQuantity.add(lblQuantity);
					panQuantity.add(lblIncreaseIcon2);
					
					calculateSum(Double.parseDouble(lblProductPrice.getText().substring(3)), Double.parseDouble(lblProductTotal.getText().substring(3)), 
							     Integer.parseInt(lblQuantity.getText()));
					
					panProductRow.add(lblProductNo);
					panProductRow.add(panProductName);
					panProductRow.add(cbUnitList);
					panProductRow.add(panQuantityPerUnit);
					panProductRow.add(panQuantity);
					panProductRow.add(lblProductPrice);
					panProductRow.add(lblProductTotal);
					
					panProductView.add(panProductRow);
					
					restockProductInfoList.add(new RestockProductInfo(productName, (String)cbUnitList.getSelectedItem(), Integer.parseInt(lblQuantityPerUnit.getText()), Integer.parseInt(lblQuantity.getText()), Double.parseDouble(lblProductPrice.getText().substring(3)), Double.parseDouble(lblProductTotal.getText().substring(3)), productBarcode));
					
					revalidate();
					repaint();
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
	
	public String[] retrieveProductList() {
		Connection conn = null;
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_name "
					   + "FROM product "
					   + "WHERE supplier_id = ("
					                         + "SELECT supplier_id "
					                         + "FROM supplier "
					                         + "WHERE supplier_name = \"" + (String)cbSupplierName.getSelectedItem() + "\") "
                 	   + "ORDER BY product_name;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			List<String> productArrayList1 = new ArrayList<String>();
			
			if(rs != null) {
				while(rs.next()) {
					productArrayList1.add(rs.getString("product_name"));
				}
			}

			String[] productList = new String[productArrayList1.size()];
			productArrayList1.toArray(productList);
			
			return productList;
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

	public void retrieveProductPrice(String productName, JLabel lblProductPrice) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_cost "
					   + "FROM product "
					   + "WHERE product_name = \"" + productName + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					lblProductPrice.setText("RM " + String.format("%.2f", rs.getDouble("product_cost")));
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
	
	public void retrieveProductTotal(String productName, JLabel lblProductTotal, int productQuantity, String operator) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_cost "
					   + "FROM product "
					   + "WHERE product_name = \"" + productName + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					double productTotal = productQuantity * rs.getDouble("product_cost");
					lblProductTotal.setText("RM " + String.format("%.2f", productTotal));
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
	
	public void calculateTotalPrice(JLabel lblProductTotal, JLabel lblProductPrice, JLabel lblProductQuantity, String operator) {
		int productQuantity = Integer.parseInt(lblProductQuantity.getText());
		double price = Double.parseDouble(lblProductPrice.getText().substring(3));
		double total = productQuantity * price;
		lblProductTotal.setText("RM " + String.format("%.2f", total));
		lblProductTotal.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, TITLE_BORDER_COLOR));
		
		if(operator.equals("+")) {
			increaseSum(price, total, productQuantity);
		}else {
			decreaseSum(price);
		}
	}
	
	public void increaseSum(double productPrice, double productTotalPrice, int productQuantity) {
		sum = sum - (productPrice * (productQuantity - 1)) + productTotalPrice;
		tfTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void decreaseSum(double productPrice) {
		sum = sum - productPrice;
		tfTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void calculateSum(double productPrice, double productTotalPrice, int productQuantity) {
		sum = sum - (productPrice * (productQuantity - 1)) + productTotalPrice;
		tfTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void updateRestockProductInfo(String productName, String operator) {
		for(int i = 0; i < restockProductInfoList.size(); i++) {
			if(productName.equals(restockProductInfoList.get(i).getProductName())) {
				if(operator.equals("+")) {
					restockProductInfoList.get(i).increaseProductQuantity();
				}else if(operator.equals("-")){
					restockProductInfoList.get(i).decreaseProductQuantity();
				}else if(operator.equals("++")) {
					restockProductInfoList.get(i).increaseQuantityPerUnit();
				}else if(operator.equals("--")){
					restockProductInfoList.get(i).decreaseQuantityPerUnit();
				}else if(operator.equals("!")) {
					restockProductInfoList.remove(i);
					barcodeList.remove(i);
				}
				break;
			}
		}
	}
	
	public void updateProductScrollPane(final JPanel panProductView) {
		numberOfProductAdded = 0;
		panProductView.removeAll();
		
		for(int i = 0; i < restockProductInfoList.size(); i++) {
			double productPrice = restockProductInfoList.get(i).getPrice();
			double productTotal = restockProductInfoList.get(i).getTotal();
			int productQuantity = restockProductInfoList.get(i).getQuantity();
			String unit = restockProductInfoList.get(i).getUnit();
			int quantityPerUnit = restockProductInfoList.get(i).getQuantityPerUnit();
			final String productName = restockProductInfoList.get(i).getProductName();
			
			final JLabel lblProductPrice = new JLabel("RM " + String.format("%.2f", productPrice));
			lblProductPrice.setHorizontalAlignment(SwingConstants.CENTER);
			lblProductPrice.setBounds(610, 0, 105, 30);
			lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, PRODUCT_BORDER_COLOR));
			lblProductPrice.setBackground(Color.WHITE);
			lblProductPrice.setForeground(Color.BLACK);
			lblProductPrice.setFont(regularText);
			lblProductPrice.setOpaque(true);
		
			final JLabel lblProductTotal = new JLabel("RM " + String.format("%.2f", productTotal));
			lblProductTotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblProductTotal.setBounds(715, 0, 106, 30);
			lblProductTotal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, PRODUCT_BORDER_COLOR));
			lblProductTotal.setBackground(Color.WHITE);
			lblProductTotal.setForeground(Color.BLACK);
			lblProductTotal.setFont(regularText);
			lblProductTotal.setOpaque(true);
			
			final JLabel lblQuantityPerUnit = new JLabel(String.valueOf(quantityPerUnit));
			lblQuantityPerUnit.setBounds(45, 5, 20, 20);
			lblQuantityPerUnit.setFont(regularText);
			lblQuantityPerUnit.setForeground(Color.BLACK);
			
			final JLabel lblQuantity = new JLabel(String.valueOf(productQuantity));
			lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
			lblQuantity.setBounds(35, 5, 30, 20);
			lblQuantity.setFont(regularText);
			lblQuantity.setForeground(Color.BLACK);
			
			final JLabel lblProductName = new JLabel(productName);
			lblProductName.setBounds(0, 0, 255, 30);
			lblProductName.setForeground(Color.BLACK);
			lblProductName.setFont(regularText);
			
			String[] unitList = {"Carton", "Box", "Crate", "Bundle"};
			
			final JComboBox<String> cbUnitList = new JComboBox<String>(unitList);
			cbUnitList.setBounds(325, 0, 85, 30);
			cbUnitList.setBackground(Color.WHITE);
			cbUnitList.setFont(regularText);
			cbUnitList.setFocusable(false);
			cbUnitList.setForeground(Color.BLACK);
			for(int j = 0; j < unitList.length; j++) {
				if(unitList[j].equals(unit)) {
					cbUnitList.setSelectedIndex(j);
				}
			}
			cbUnitList.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < restockProductInfoList.size(); i++) {
						if(restockProductInfoList.get(i).getProductName().equals(lblProductName.getText())) {
							restockProductInfoList.get(i).setUnit((String)cbUnitList.getSelectedItem());
						}
					}
				}
			});
			
			ConfigureComboBox.addHorizontalScrollBar(cbUnitList);
			
			lblProductNo = new JLabel(++numberOfProductAdded + ".", SwingConstants.CENTER);
			lblProductNo.setBounds(0, 0, 40, 30);
			lblProductNo.setFont(regularText);
			lblProductNo.setForeground(Color.BLACK);
			lblProductNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
			lblProductNo.setOpaque(true);
			
			panProductName = new JPanel();
			panProductName.setBounds(40, 0, 285, 30);
			panProductName.setLayout(null);
			panProductName.setBackground(Color.WHITE);
			panProductName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, PRODUCT_BORDER_COLOR));
			
			lblDustbinIcon = new JLabel(dustbinIcon);
			lblDustbinIcon.setBounds(250, 0, 30, 30);
			lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDustbinIcon.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to remove this product?\n" + lblProductName.getText(), "REMOVE PRODUCT", JOptionPane.DEFAULT_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						updateRestockProductInfo(lblProductName.getText(), "!");
						sum = 0;
						updateProductScrollPane(panProductView);
					}
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			panProductName.add(lblProductName);
			panProductName.add(lblDustbinIcon);
			
			panQuantityPerUnit = new JPanel();
			panQuantityPerUnit.setBounds(410, 0, 100, 30);
			panQuantityPerUnit.setLayout(null);
			panQuantityPerUnit.setBackground(Color.WHITE);
			panQuantityPerUnit.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, PRODUCT_BORDER_COLOR));
			
			lblDecreaseIcon1 = new JLabel(decreaseIcon);
			lblDecreaseIcon1.setBounds(10, 5, 20, 20);
			lblDecreaseIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDecreaseIcon1.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					if(Integer.parseInt(lblQuantityPerUnit.getText()) <= 1) {
						JOptionPane.showMessageDialog(null, "Quantity per unit must be a positive value", "INVALID QUANTITY PER UNIT", JOptionPane.WARNING_MESSAGE);
						return;
					}
					lblQuantityPerUnit.setText(String.valueOf(Integer.parseInt(lblQuantityPerUnit.getText()) - 1));
					updateRestockProductInfo(lblProductName.getText(), "--");
					
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			lblIncreaseIcon1 = new JLabel(increaseIcon);
			lblIncreaseIcon1.setBounds(70, 5, 20, 20);
			lblIncreaseIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblIncreaseIcon1.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					lblQuantityPerUnit.setText(String.valueOf(Integer.parseInt(lblQuantityPerUnit.getText()) + 1));
					updateRestockProductInfo(lblProductName.getText(), "++");
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			panQuantityPerUnit.add(lblDecreaseIcon1);
			panQuantityPerUnit.add(lblQuantityPerUnit);
			panQuantityPerUnit.add(lblIncreaseIcon1);
			
			panQuantity = new JPanel();
			panQuantity.setBounds(510, 0, 100, 30);
			panQuantity.setLayout(null);
			panQuantity.setBackground(Color.WHITE);
			panQuantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRODUCT_BORDER_COLOR));
					
			lblDecreaseIcon2 = new JLabel(decreaseIcon);
			lblDecreaseIcon2.setBounds(10, 5, 20, 20);
			lblDecreaseIcon2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDecreaseIcon2.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					if(Integer.parseInt(lblQuantity.getText()) <= 1) {
						JOptionPane.showMessageDialog(null, "Product quantity must be a positive value", "INVALID PRODUCT QUANTITY", JOptionPane.WARNING_MESSAGE);
						return;
					}
					lblQuantity.setText(String.valueOf(Integer.parseInt(lblQuantity.getText()) - 1));
					calculateTotalPrice(lblProductTotal, lblProductPrice, lblQuantity, "-");
					updateRestockProductInfo(lblProductName.getText(), "-");
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
			
			lblIncreaseIcon2 = new JLabel(increaseIcon);
			lblIncreaseIcon2.setBounds(70, 5, 20, 20);
			lblIncreaseIcon2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblIncreaseIcon2.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					lblQuantity.setText(String.valueOf(Integer.parseInt(lblQuantity.getText()) + 1));
					retrieveProductTotal(lblProductName.getText(), lblProductTotal, Integer.parseInt(lblQuantity.getText()), "+");
					calculateTotalPrice(lblProductTotal, lblProductPrice, lblQuantity, "+");
					updateRestockProductInfo(lblProductName.getText(), "+");
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
			
			panQuantity.add(lblDecreaseIcon2);
			panQuantity.add(lblQuantity);
			panQuantity.add(lblIncreaseIcon2);
			
			reCalculateSum(productTotal);
			
			final JPanel panProductRow = new JPanel();
			panProductRow.setPreferredSize(new Dimension(821, 30));
			panProductRow.setLayout(null);
			panProductRow.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PRODUCT_BORDER_COLOR));
			
			panProductRow.add(lblProductNo);
			panProductRow.add(panProductName);
			panProductRow.add(cbUnitList);
			panProductRow.add(panQuantityPerUnit);
			panProductRow.add(panQuantity);
			panProductRow.add(lblProductPrice);
			panProductRow.add(lblProductTotal);
			
			panProductView.add(panProductRow);
		}
		
		revalidate();
		repaint();
	}
	
	public void reCalculateSum(double productTotal) {
		sum += productTotal;
		tfTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void searchProduct(String productName, JScrollPane spSearch) {
		Connection conn = null;
		JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
		panSearchView.setBackground(Color.WHITE);
		panSearchView.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
		final Color SEARCH_RESULT_BACKGROUND_COLOR = new Color(205, 205, 205);
		int count = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			
			String sql = "SELECT product_name, product_barcode FROM product "
					   + "WHERE product_name LIKE '" + productName + "%' "
					   + "AND supplier_id = ("
					                       + "SELECT supplier_id "
					                       + "FROM supplier "
					                       + "WHERE supplier_name = \"" + (String)cbSupplierName.getSelectedItem() + "\") "
				       + "ORDER BY product_name;";

			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next() && !Pattern.matches("\\s*", productName)) {
					boolean repeatProduct = false;
					final String productBarcode = rs.getString("product_barcode");

					for(int i = 0; i < barcodeList.size(); i++) {
						if(barcodeList.get(i).equals(productBarcode)) {
							repeatProduct = true;
							break;
						}
					}
					if(repeatProduct) {
						continue;
					}
					final JTextField tfSearchResult = new JTextField(rs.getString("product_name"));
					tfSearchResult.setFont(new Font("Arial", Font.PLAIN, 20));
					tfSearchResult.setPreferredSize(new Dimension(500, 25));
					tfSearchResult.setEditable(false);
					tfSearchResult.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					tfSearchResult.setBorder(null);
					tfSearchResult.setForeground(Color.BLACK);
					
					tfSearchResult.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							panSearchResult.setVisible(false);
							tfSearchBar.setText("");
							addNewProductRow(spProduct, productBarcode);
							barcodeList.add(productBarcode);

						}
						
						public void mouseExited(MouseEvent e) {
							tfSearchResult.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							tfSearchResult.setBackground(SEARCH_RESULT_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panSearchView.add(tfSearchResult);
					count++;
				}
			}
			
			sql = "SELECT product_name, product_barcode FROM product "
				+ "WHERE product_barcode LIKE '" + productName + "%' "
				+ "AND supplier_id = ("
                                    + "SELECT supplier_id "
                                    + "FROM supplier "
                                    + "WHERE supplier_name = \"" + (String)cbSupplierName.getSelectedItem() + "\") "
				+ "ORDER BY product_name";
			
			rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next() && !Pattern.matches("\\s*", productName)) {
					
					boolean repeatProduct = false;
					final String productBarcode = rs.getString("product_barcode");
					for(int i = 0; i < barcodeList.size(); i++) {
						if(barcodeList.get(i).equals(productBarcode)) {
							repeatProduct = true;
							break;
						}
					}
					
					if(repeatProduct) {
						continue;
					}
					
					final JTextField tfSearchResult = new JTextField(rs.getString("product_name"));
					tfSearchResult.setFont(new Font("Arial", Font.PLAIN, 20));
					tfSearchResult.setPreferredSize(new Dimension(500, 25));
					tfSearchResult.setEditable(false);
					tfSearchResult.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					tfSearchResult.setBorder(null);
					tfSearchResult.setForeground(Color.BLACK);
					
					tfSearchResult.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							panSearchResult.setVisible(false);
							tfSearchBar.setText("");
							addNewProductRow(spProduct, productBarcode);
							barcodeList.add(productBarcode);
						}
						
						public void mouseExited(MouseEvent e) {
							tfSearchResult.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							tfSearchResult.setBackground(SEARCH_RESULT_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panSearchView.add(tfSearchResult);
					count++;
				}
			}
			
			if(count > 0) {
				panSearchResult.setVisible(true);
				panSearchView.setPreferredSize(new Dimension(500, count * 25));
			}else {
				panSearchResult.setVisible(false);
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

	public void manipulateInventory() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_barcode, restock_product_quantity_per_unit, restock_product_quantity "
					   + "FROM restock_product "
					   + "WHERE invoice_no = " + Integer.parseInt(tfInvoiceNo.getText()) + ";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					String productBarcode = rs.getString("product_barcode");
					int quantityAdded = rs.getInt("restock_product_quantity_per_unit") * rs.getInt("restock_product_quantity");
					int totalQuantity = 0;
					
					sql = "SELECT product_quantity "
					    + "FROM product "
					    + "WHERE product_barcode = " + productBarcode + ";";
					
					Statement stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(sql);
					
					if(rs2 != null) {
						while(rs2.next()) {
							totalQuantity = quantityAdded + rs2.getInt("product_quantity");
						}
					}
					
					sql = "UPDATE product "
						+ "SET product_quantity = " + totalQuantity + " "
						+ "WHERE product_barcode = " + productBarcode + ";";
					
					stmt2.executeUpdate(sql);
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
}