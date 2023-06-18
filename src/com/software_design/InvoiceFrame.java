package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.*;

public class InvoiceFrame extends JFrame{
	
	JPanel panView, panTitle, panInvoice, panInfo, panTitleRow, panProductRow, panTotal;
	JLabel lblBackIcon, lblInvoice;
	JLabel lblSupplierTitle, lblInvoiceNoTitle, lblPlacedDateTitle, lblReceivedDateTitle, lblLocationTitle;
	JLabel lblTitleNo, lblTitleProduct, lblTitleQuantityPerUnit, lblTitleQuantity, lblTitleUnit, lblTitlePrice;
	JLabel lblSupplier, lblInvoiceNo, lblPlacedDate, lblReceivedDate;
	JLabel lblTitleTotal, lblTotal;
	JTextArea taLocation;
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);

	private final int MAX_WIDTH = 1366;
	private final int MAX_HEIGHT = 768;
	private final int INFO_HEIGHT = 440;
	private final int TITLE_HEIGHT = 70;
	
	Font regularText = new Font("Arial", Font.PLAIN, 18);
	Font titleRowText = new Font("Arial", Font.BOLD, 18);
	
	final int INVOICE_WIDTH = 800;
	final int MAX_NUM_OF_PRODUCT = 8;
	private int invoiceNo;
	
	int numberOfProduct = 0;
	double totalPrice = 0;
	
	InvoiceFrame(String strInvoiceNo) {
		
		this.setInvoiceNo(strInvoiceNo);
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT COUNT(*) "
					   + "FROM restock_product "
					   + "WHERE invoice_no = " + invoiceNo + ";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					numberOfProduct = rs.getInt("COUNT(*)");
				}
			}
			
			sql = "SELECT invoice_restock_fee "
				+ "FROM restock "
				+ "WHERE invoice_no = " + invoiceNo +";";
			
			rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					totalPrice = rs.getDouble("invoice_restock_fee");
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Invoice");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
				
		panView = new JPanel();
		panView.setLayout(null);
		panView.setBackground(Color.WHITE);
		
		if(numberOfProduct > MAX_NUM_OF_PRODUCT) {
			panView.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT + (numberOfProduct - MAX_NUM_OF_PRODUCT) * 40));
		}
		
		panInvoice = new JPanel();
		panInvoice.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panInvoice.setBounds((MAX_WIDTH - INVOICE_WIDTH) / 2, 40, INVOICE_WIDTH, INFO_HEIGHT + (numberOfProduct * 30));
		panInvoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panInvoice.setBackground(Color.WHITE);
		
		panTitle = new JPanel();
		panTitle.setLayout(null);
		panTitle.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, TITLE_HEIGHT));
		panTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panTitle.setBackground(null);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(0, 20, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				dispose();
				new RestockHistoryFrame();
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
		
		lblInvoice= new JLabel("INVOICE");
		lblInvoice.setFont(new Font("Arial", Font.BOLD, 30));
		lblInvoice.setBounds((INVOICE_WIDTH - 200) / 2, 25, 130, 25);
		lblInvoice.setForeground(Color.BLACK);
		
		panTitle.add(lblBackIcon);
		panTitle.add(lblInvoice);
		
		retrieveRestockInfo(strInvoiceNo);
		
		insertTitleRow();

		final JPanel panProduct = new JPanel();
		panProduct.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));
		panProduct.setBackground(null);
		panProduct.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, numberOfProduct * 30 + 10));	
		panProduct.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		
		retrieveProductInfo(panProduct);
		
		panTotal = new JPanel();
		panTotal.setLayout(null);
		panTotal.setBackground(null);
		panTotal.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, 50));
		
		lblTitleTotal = new JLabel("Total: ");
		lblTitleTotal.setBounds(530, 10, 80, 40);
		lblTitleTotal.setFont(new Font("Arial", Font.BOLD, 25));
		lblTitleTotal.setForeground(Color.BLACK);
		
		lblTotal = new JLabel("RM " + String.format("%.2f", totalPrice));
		lblTotal.setBounds(610, 10, 150, 40);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 25));
		lblTotal.setForeground(Color.BLACK);
		
		panTotal.add(lblTitleTotal);
		panTotal.add(lblTotal);
		
		panInvoice.add(panTitle);
		panInvoice.add(panInfo);
		panInvoice.add(panTitleRow);
		panInvoice.add(panProduct);
		panInvoice.add(panTotal);
		
		panView.add(panInvoice);
		
		JScrollPane spInvoice = new JScrollPane(panView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spInvoice.setBounds(0, 0, MAX_WIDTH, MAX_HEIGHT);
		spInvoice.getVerticalScrollBar().setUnitIncrement(16);
		
		add(spInvoice);
		
		setVisible(true);
	}

	public void retrieveRestockInfo(String strInvoiceNo) {
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT * FROM restock "
					   + "WHERE invoice_no = " + invoiceNo + ";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					String invoicePlacedDate = String.valueOf(rs.getDate("invoice_placed_date"));
					String invoiceReceivedDate = String.valueOf(rs.getDate("invoice_received_date"));
					String restockFee = "RM " + String.format("%.2f", rs.getDouble("invoice_restock_fee"));
					String supplierName = rs.getString("supplier_name");
					String supplierLocation = rs.getString("supplier_location");
					
					panInfo = new JPanel();
					panInfo.setLayout(null);
					panInfo.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, 230));
					panInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
					panInfo.setBackground(null);
					
					lblSupplierTitle = new JLabel("Supplier: ");
					lblSupplierTitle.setBounds(0, 10, 100, 20);
					lblSupplierTitle.setFont(titleRowText);
					lblSupplierTitle.setForeground(Color.BLACK);
					
					lblSupplier = new JLabel(supplierName);
					lblSupplier.setBounds(0, 30, 500, 20);
					lblSupplier.setFont(regularText);
					lblSupplier.setForeground(Color.BLACK);
					
					lblInvoiceNoTitle = new JLabel("InvoiceNo: ");
					lblInvoiceNoTitle.setBounds(0, 70, 150, 20);
					lblInvoiceNoTitle.setFont(titleRowText);
					lblInvoiceNoTitle.setForeground(Color.BLACK);
					
					lblInvoiceNo = new JLabel(strInvoiceNo);
					lblInvoiceNo.setBounds(0, 90, 150, 20);
					lblInvoiceNo.setFont(regularText);
					lblInvoiceNo.setForeground(Color.BLACK);
					
					lblPlacedDateTitle = new JLabel("Placed Date: ");
					lblPlacedDateTitle.setBounds(200, 70, 150, 20);
					lblPlacedDateTitle.setFont(titleRowText);
					lblPlacedDateTitle.setForeground(Color.BLACK);
					
					lblPlacedDate = new JLabel(invoicePlacedDate);
					lblPlacedDate.setBounds(200, 90, 150, 20);
					lblPlacedDate.setFont(regularText);
					lblPlacedDate.setForeground(Color.BLACK);
					
					lblReceivedDateTitle = new JLabel("Received Date: ");
					lblReceivedDateTitle.setBounds(400, 70, 150, 20);
					lblReceivedDateTitle.setFont(titleRowText);
					lblReceivedDateTitle.setForeground(Color.BLACK);
					
					lblReceivedDate = new JLabel(invoiceReceivedDate);
					lblReceivedDate.setBounds(400, 90, 150, 20);
					lblReceivedDate.setFont(regularText);
					lblReceivedDate.setForeground(Color.BLACK);
					
					lblLocationTitle = new JLabel("From: ");
					lblLocationTitle.setBounds(0, 130, 150, 20);
					lblLocationTitle.setFont(titleRowText);
					lblLocationTitle.setForeground(Color.BLACK);
					
					taLocation = new JTextArea(supplierLocation, 5, 10);
					taLocation.setBounds(0, 150,300, 70);
					taLocation.setFont(regularText);
					taLocation.setForeground(Color.BLACK);
					taLocation.setLineWrap(true);
					taLocation.setEditable(false);
					taLocation.setBorder(null);
					taLocation.setWrapStyleWord(true);
					
					panInfo.add(lblSupplierTitle);
					panInfo.add(lblSupplier);
					panInfo.add(lblInvoiceNoTitle);
					panInfo.add(lblInvoiceNo);
					panInfo.add(lblPlacedDateTitle);
					panInfo.add(lblPlacedDate);
					panInfo.add(lblReceivedDateTitle);
					panInfo.add(lblReceivedDate);
					panInfo.add(lblLocationTitle);
					panInfo.add(taLocation);
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
	
	public void insertTitleRow() {
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBackground(null);
		panTitleRow.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, 30));
		
		lblTitleNo = new JLabel("NO.", SwingConstants.CENTER);
		lblTitleNo.setFont(titleRowText);
		lblTitleNo.setForeground(Color.BLACK);
		lblTitleNo.setBounds(0, 10, 50, 18);
		
		lblTitleProduct = new JLabel("NAME");
		lblTitleProduct.setFont(titleRowText);
		lblTitleProduct.setForeground(Color.BLACK);
		lblTitleProduct.setBounds(50, 10, 270, 18);
		
		lblTitleQuantityPerUnit = new JLabel("QTR / UNIT", SwingConstants.CENTER);
		lblTitleQuantityPerUnit.setFont(titleRowText);
		lblTitleQuantityPerUnit.setForeground(Color.BLACK);
		lblTitleQuantityPerUnit.setBounds(320, 10, 100, 18);
		
		lblTitleUnit = new JLabel("UNIT", SwingConstants.CENTER);
		lblTitleUnit.setFont(titleRowText);
		lblTitleUnit.setForeground(Color.BLACK);
		lblTitleUnit.setBounds(420, 10, 100, 18);
		
		lblTitleQuantity = new JLabel("QUANTITY", SwingConstants.CENTER);
		lblTitleQuantity.setFont(titleRowText);
		lblTitleQuantity.setForeground(Color.BLACK);
		lblTitleQuantity.setBounds(520, 10, 100, 18);
		
		lblTitlePrice = new JLabel("PRICE", SwingConstants.CENTER);
		lblTitlePrice.setFont(titleRowText);
		lblTitlePrice.setForeground(Color.BLACK);
		lblTitlePrice.setBounds(620, 10, 100, 18);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleProduct);
		panTitleRow.add(lblTitleQuantityPerUnit);
		panTitleRow.add(lblTitleUnit);
		panTitleRow.add(lblTitleQuantity);
		panTitleRow.add(lblTitlePrice);
	}
	
	public void retrieveProductInfo(JPanel panProduct) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT * FROM restock_product "
					   + "WHERE invoice_no = " + invoiceNo + ";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					
					String productName = rs.getString("restock_product_name");
					String quantityPerUnit = String.valueOf(rs.getInt("restock_product_quantity_per_unit"));
					String quantity = String.valueOf(rs.getInt("restock_product_quantity"));
					String unit = rs.getString("restock_product_unit");
					String totalPrice = "RM " + String.format("%.2f", rs.getDouble("restock_product_total_price"));
					
					panProductRow = new JPanel();
					panProductRow.setLayout(null);
					panProductRow.setBackground(null);
					panProductRow.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, 25));
					
					final JLabel lblProductNo = new JLabel((++count) + ".", SwingConstants.CENTER);
					lblProductNo.setFont(regularText);
					lblProductNo.setForeground(Color.BLACK);
					lblProductNo.setBounds(0, 2, 50, 18);
					
					JTextArea taProductName = new JTextArea(productName);
					
					if(taProductName.getText().length() > 31) {
						taProductName.setBounds(50, 0, 270, 44);
						panProductRow.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, 50));
						numberOfProduct++;
						panProduct.setPreferredSize(new Dimension(INVOICE_WIDTH - 30, numberOfProduct * 25 + 10));	
						panInvoice.setBounds((MAX_WIDTH - INVOICE_WIDTH) / 2, 40, INVOICE_WIDTH, 440 + (numberOfProduct * 25));
						if(numberOfProduct > MAX_NUM_OF_PRODUCT) {
							panView.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT + (numberOfProduct - MAX_NUM_OF_PRODUCT) * 25));
						}
					}else {
						taProductName.setBounds(50, 0, 270, 22);
					}
					
					taProductName.setFont(regularText);
					taProductName.setForeground(Color.BLACK);
					taProductName.setWrapStyleWord(true);
					taProductName.setLineWrap(true);
					taProductName.setEditable(false);
					
					JLabel lblProductQuantityPerUnit = new JLabel(quantityPerUnit, SwingConstants.CENTER);
					lblProductQuantityPerUnit.setFont(regularText);
					lblProductQuantityPerUnit.setForeground(Color.BLACK);
					lblProductQuantityPerUnit.setBounds(320, 0, 100, 18);
					
					JLabel lblUnit = new JLabel(unit, SwingConstants.CENTER);
					lblUnit.setFont(regularText);
					lblUnit.setForeground(Color.BLACK);
					lblUnit.setBounds(420, 0, 100, 18);
					
					JLabel lblProductQuantity = new JLabel(quantity, SwingConstants.CENTER);
					lblProductQuantity.setFont(regularText);
					lblProductQuantity.setForeground(Color.BLACK);
					lblProductQuantity.setBounds(520, 0, 100, 18);
					
					JLabel lblProductPrice = new JLabel(totalPrice, SwingConstants.CENTER);
					lblProductPrice.setFont(regularText);
					lblProductPrice.setForeground(Color.BLACK);
					lblProductPrice.setBounds(620, 0, 100, 18);
					
					panProductRow.add(lblProductNo);
					panProductRow.add(taProductName);
					panProductRow.add(lblProductQuantityPerUnit);
					panProductRow.add(lblUnit);
					panProductRow.add(lblProductQuantity);
					panProductRow.add(lblProductPrice);
					
					panProduct.add(panProductRow);
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
	
	
	public void setInvoiceNo(String strInvoiceNo) {
		this.invoiceNo = Integer.parseInt(strInvoiceNo);
	}
 }
