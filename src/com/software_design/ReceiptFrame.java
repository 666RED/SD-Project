package com.software_design;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;

public class ReceiptFrame extends JFrame{
	
	JPanel panView, panReceipt, panTitle, panTime, panTitleRow, panProductRow, panTotal, panButton;
	JLabel lblBackIcon, lblReceipt, lblShopName, lblDate, lblTime, lblReceiptNo, lblTitleNo, lblTitleName,
		   lblTitleQuantity, lblTitlePrice, lblProductNo, lblProductQuantity, lblProductPrice,
		   lblTotal, lblPayment, lblChange;
	JTextField tfTotal, tfPayment, tfChange;
	JTextArea taProductName;
	JButton btnPrintAndSave, btnSave;
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon savedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	ImageIcon printIcon = IconClass.createIcon("Image\\printing.png", 30, 30);
	private final int MAX_WIDTH = 1366;
	private final int MAX_HEIGHT = 768;
	Font regularText = new Font("Arial", Font.PLAIN, 18);
	Font titleRowText = new Font("Arial", Font.BOLD, 18);
	final int RECEIPT_WIDTH = 600;
	final int MAX_NUM_OF_PRODUCT = 8;
	final Color BUTTON_COLOR = new Color(93, 88, 88);
	int numberOfProduct = 0;
	final Date date = Date.valueOf(LocalDate.now());
	final Time time = Time.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	
	public ReceiptFrame(final List<ProductInfo> productInfoList, final String[] calcList) {
		numberOfProduct = productInfoList.size();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Receipt");
		setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		panView = new JPanel();
		panView.setLayout(null);
		panView.setBackground(Color.WHITE);
		
		if(numberOfProduct > MAX_NUM_OF_PRODUCT) {
			panView.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT + (numberOfProduct - MAX_NUM_OF_PRODUCT) * 25));
		}

		panReceipt = new JPanel();
		panReceipt.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panReceipt.setBounds((MAX_WIDTH - RECEIPT_WIDTH) / 2, 40, RECEIPT_WIDTH, 440 + (numberOfProduct * 25));
		panReceipt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panReceipt.setBackground(Color.WHITE);
		
		panTitle = new JPanel();
		panTitle.setLayout(null);
		panTitle.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 70));
		panTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panTitle.setBackground(null);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(0, 20, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				dispose();
				new PurchaseFrame();
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
		
		lblReceipt= new JLabel("RECEIPT");
		lblReceipt.setFont(new Font("Arial", Font.BOLD, 30));
		lblReceipt.setBounds(220, 25, 130, 25);
		lblReceipt.setForeground(Color.BLACK);
		
		panTitle.add(lblBackIcon);
		panTitle.add(lblReceipt);
		
		panTime = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panTime.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 110));
		panTime.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panTime.setBackground(null);
		
		lblShopName = new JLabel("PASAR MINI KMS", SwingConstants.CENTER);
		lblShopName.setFont(new Font("Arial", Font.BOLD, 20));
		lblShopName.setPreferredSize(new Dimension(RECEIPT_WIDTH, 40));
		lblShopName.setForeground(Color.BLACK);
		
		lblDate = new JLabel("Date: " + date, SwingConstants.CENTER);
		lblDate.setPreferredSize(new Dimension(RECEIPT_WIDTH, 20));
		lblDate.setFont(regularText);
		lblDate.setForeground(Color.BLACK);
		
		lblTime = new JLabel("Time: " + time, SwingConstants.CENTER);
		lblTime.setPreferredSize(new Dimension(RECEIPT_WIDTH, 20));
		lblTime.setFont(regularText);
		lblTime.setForeground(Color.BLACK);
		
		int receiptNo = getReceiptNo();
		lblReceiptNo = new JLabel("Receipt No: " + String.format("%06d", receiptNo + 1), SwingConstants.CENTER);
		lblReceiptNo.setPreferredSize(new Dimension(RECEIPT_WIDTH, 20));
		lblReceiptNo.setFont(regularText);
		lblReceiptNo.setForeground(Color.BLACK);
		
		panTime.add(lblShopName);
		panTime.add(lblDate);
		panTime.add(lblTime);
		panTime.add(lblReceiptNo);
		
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBackground(null);
		panTitleRow.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 30));
		
		lblTitleNo = new JLabel("NO.", SwingConstants.CENTER);
		lblTitleNo.setFont(titleRowText);
		lblTitleNo.setForeground(Color.BLACK);
		lblTitleNo.setBounds(0, 10, 50, 18);
		
		lblTitleName = new JLabel("NAME");
		lblTitleName.setFont(titleRowText);
		lblTitleName.setForeground(Color.BLACK);
		lblTitleName.setBounds(50, 10, 270, 18);
		
		lblTitleQuantity = new JLabel("QUANTITY", SwingConstants.CENTER);
		lblTitleQuantity.setFont(titleRowText);
		lblTitleQuantity.setForeground(Color.BLACK);
		lblTitleQuantity.setBounds(320, 10, 100, 18);
		
		lblTitlePrice = new JLabel("PRICE", SwingConstants.CENTER);
		lblTitlePrice.setFont(titleRowText);
		lblTitlePrice.setForeground(Color.BLACK);
		lblTitlePrice.setBounds(420, 10, 150, 18);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleName);
		panTitleRow.add(lblTitleQuantity);
		panTitleRow.add(lblTitlePrice);
		
		final JPanel panProduct = new JPanel();
		panProduct.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		panProduct.setBackground(null);
		panProduct.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, numberOfProduct * 25 + 10));	
		panProduct.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		
		retrieveProductData(productInfoList, panProduct);
		
		panTotal = new JPanel();
		panTotal.setLayout(null);
		panTotal.setBackground(null);
		panTotal.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 150));
		
		lblTotal = new JLabel("Total:");
		lblTotal.setBounds(350, 25, 70, 25);
		lblTotal.setFont(new Font("Arial", Font.BOLD, 25));
		lblTotal.setForeground(Color.BLACK);
		
		tfTotal = new JTextField(calcList[0]);
		tfTotal.setBounds(440, 25, 130, 25);
		tfTotal.setFont(new Font("Arial", Font.BOLD, 25));
		tfTotal.setForeground(Color.BLACK);
		tfTotal.setEditable(false);
		tfTotal.setBackground(null);
		tfTotal.setBorder(null);
		
		lblPayment = new JLabel("Payment:");
		lblPayment.setBounds(340, 70, 90, 20);
		lblPayment.setFont(regularText);
		lblPayment.setForeground(Color.BLACK);
		
		tfPayment = new JTextField(calcList[1]);
		tfPayment.setBounds(460, 70, 110, 20);
		tfPayment.setFont(regularText);
		tfPayment.setForeground(Color.BLACK);
		tfPayment.setEditable(false);
		tfPayment.setBackground(null);
		tfPayment.setBorder(null);
		
		lblChange = new JLabel("Change:");
		lblChange.setBounds(345, 110, 85, 20);
		lblChange.setFont(regularText);
		lblChange.setForeground(Color.BLACK);
		
		tfChange = new JTextField(calcList[2]);
		tfChange.setBounds(460, 110, 110, 20);
		tfChange.setFont(regularText);
		tfChange.setForeground(Color.BLACK);
		tfChange.setEditable(false);
		tfChange.setBackground(null);
		tfChange.setBorder(null);
		
		panTotal.add(lblTotal);
		panTotal.add(tfTotal);
		panTotal.add(lblPayment);
		panTotal.add(tfPayment);
		panTotal.add(lblChange);
		panTotal.add(tfChange);
		
		panButton = new JPanel();
		panButton.setLayout(null);
		panButton.setBackground(null);
		panButton.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 60));
		
		btnPrintAndSave = new JButton("Print and Save");
		btnPrintAndSave.setBounds(20, 10, 180, 40);
		btnPrintAndSave.setBackground(BUTTON_COLOR);
		btnPrintAndSave.setForeground(Color.WHITE);
		btnPrintAndSave.setFont(regularText);
		btnPrintAndSave.setFocusable(false);
		btnPrintAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel lblSaveAndPrint = new JLabel("Saving and Printing...");
				lblSaveAndPrint.setFont(new Font("Arial", Font.PLAIN, 25));
				lblSaveAndPrint.setForeground(Color.BLACK);
				int result = JOptionPane.showConfirmDialog(null, lblSaveAndPrint, "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, printIcon);
				saveData(calcList, productInfoList);
				
				if(result == JOptionPane.YES_OPTION || result == JOptionPane.CLOSED_OPTION) {
					dispose();
					new PurchaseFrame();
				}
			}
		});
		
		btnSave = new JButton("Save");
		btnSave.setBounds(370, 10, 180, 40);
		btnSave.setBackground(BUTTON_COLOR);
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(regularText);
		btnSave.setFocusable(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveData(calcList, productInfoList);
				int result = JOptionPane.showConfirmDialog(null, "Data saved successfully", "Saved", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, savedIcon);
				if(result == JOptionPane.YES_OPTION) {
					dispose();
					new PurchaseFrame();
				}
			}
		});
		
		panButton.add(btnPrintAndSave);
		panButton.add(btnSave);
		
		panReceipt.add(panTitle);
		panReceipt.add(panTime);
		panReceipt.add(panTitleRow);
		panReceipt.add(panProduct);
		panReceipt.add(panTotal);
		panReceipt.add(panButton);
		
		panView.add(panReceipt);
		
		JScrollPane spReceipt = new JScrollPane(panView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spReceipt.setBounds(0, 0, MAX_WIDTH, MAX_HEIGHT);
		spReceipt.getVerticalScrollBar().setUnitIncrement(16);
		
		add(spReceipt);
		
		setVisible(true);
	}
	
	public int getReceiptNo() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT COUNT(*) FROM receipt;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					return rs.getInt("COUNT(*)");
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
		return 0;
	}

	public void retrieveProductData(List<ProductInfo> productInfoList, JPanel panProduct) {
		for(int i = 0; i < productInfoList.size(); i++) {
			panProductRow = new JPanel();
			panProductRow.setLayout(null);
			panProductRow.setBackground(null);
			panProductRow.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 25));
			
			lblProductNo = new JLabel((i + 1) + ".", SwingConstants.CENTER);
			lblProductNo.setFont(regularText);
			lblProductNo.setForeground(Color.BLACK);
			lblProductNo.setBounds(0, 2, 50, 18);
			
			taProductName = new JTextArea(productInfoList.get(i).getProductName());

			if(taProductName.getText().length() > 31) {
				taProductName.setBounds(50, 0, 270, 44);
				panProductRow.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, 50));
				numberOfProduct++;
				panProduct.setPreferredSize(new Dimension(RECEIPT_WIDTH - 30, numberOfProduct * 25 + 10));	
				panReceipt.setBounds((MAX_WIDTH - RECEIPT_WIDTH) / 2, 40, RECEIPT_WIDTH, 440 + (numberOfProduct * 25));
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
			
			lblProductQuantity = new JLabel(String.valueOf(productInfoList.get(i).getProductQuantity()), SwingConstants.CENTER);
			lblProductQuantity.setFont(regularText);
			lblProductQuantity.setForeground(Color.BLACK);
			lblProductQuantity.setBounds(320, 0, 100, 18);
			
			lblProductPrice = new JLabel("RM " + String.format("%.2f", productInfoList.get(i).getProductTotal()), SwingConstants.CENTER);
			lblProductPrice.setFont(regularText);
			lblProductPrice.setForeground(Color.BLACK);
			lblProductPrice.setBounds(420, 0, 150, 18);
			
			panProductRow.add(lblProductNo);
			panProductRow.add(taProductName);
			panProductRow.add(lblProductQuantity);
			panProductRow.add(lblProductPrice);
			
			panProduct.add(panProductRow);
		}
	}
	
	public void saveData(String[] calcList, List<ProductInfo> productInfoList) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "INSERT INTO receipt(receipt_date, receipt_time, receipt_total, receipt_payment, receipt_change) "
					   + "VALUES(\"" + date + "\", \"" + time + "\", ?, ?, ?);";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, Double.parseDouble(calcList[0].substring(3)));
			ps.setDouble(2, Double.parseDouble(calcList[1].substring(3)));
			ps.setDouble(3, Double.parseDouble(calcList[2].substring(3)));
			ps.executeUpdate();
			
			for(int i = 0; i < productInfoList.size(); i++) {
				String productBarcode = "";
				int receiptID = 0;
				
				String findBarcode = "SELECT product_barcode "
						           + "FROM product "
						           + "WHERE product_name = \"" + productInfoList.get(i).getProductName() + "\";";
				
				ps = conn.prepareStatement(findBarcode);
				ResultSet rs = ps.executeQuery();
				
				if(rs != null) {
					while(rs.next()) {
						productBarcode = rs.getString("product_barcode");
					}
				}
				
				String findReceiptID = "SELECT receipt_id FROM receipt "
						              + "ORDER BY receipt_id DESC "
						              + "LIMIT 1";
				
				ps = conn.prepareStatement(findReceiptID);
				rs = ps.executeQuery();
				
				if(rs != null) {
					while(rs.next()) {
						receiptID = rs.getInt("receipt_id");
					}
				}
				
				sql = "INSERT INTO product_receipt "
					+ "VALUES(?, ?, ?);";
				
				int productQuantity = productInfoList.get(i).getProductQuantity();
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, productBarcode);
				ps.setInt(2, receiptID);
				ps.setInt(3, productQuantity);
				ps.executeUpdate();
				
				decreaseInventory(productBarcode, productQuantity);
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
	
	public void decreaseInventory(String productBarcode, int productQuantity) {
		Connection conn = null;
		int newProductQuantity = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_quantity FROM product "
					   + "WHERE product_barcode = \"" + productBarcode + "\";";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next()) {
					newProductQuantity = rs.getInt("product_quantity") - productQuantity;
				}
			}
			
			sql = "UPDATE product "
				+ "SET product_quantity = " + newProductQuantity + " "
				+ "WHERE product_barcode = " + productBarcode;
			
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
