package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class RestockHistoryFrame extends JFrame{
	
	JLabel lblBackIcon, lblRestockHistory;
	JLabel lblTitleNo, lblTitleInvoiceNo, lblTitleSupplierName, lblTitleOrderPlaced, lblTitleOrderReceived, lblTitleRestockFee;
	JPanel panHeader, panFooter, panContent, panTitleRow, panInvoiceRow;
	JComboBox<String> cbSupplierName;
	JScrollPane spSupplier;
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color PRODUCT_NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color PRODUCT_BORDER_COLOR = new Color(168, 168, 168);
	final Color TITLE_BORDER_COLOR = new Color(140, 140, 140);
	final Color PRODUCT_ROW_HOVER_COLOR = new Color(210, 210, 210);
	
	Font regularText = new Font("Arial", Font.PLAIN, 18);
	Font titleFont = new Font("Arial", Font.BOLD, 20);
	
	int numberOfInvoice = 0;
	final int WIDTH = 1340;
	final int HEIGHT = 540;
	final int MAX_NUM_OF_ROW = 10;
	
	public RestockHistoryFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Restock History");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(15, 50, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {

			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new RestockFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		panContent = new JPanel();
		panContent.setBounds(15, 90, WIDTH, HEIGHT);
		panContent.setLayout(null);
		panContent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		
		lblRestockHistory = new JLabel("Restock History");
		lblRestockHistory.setBounds(0, 25, 250, 30);
		lblRestockHistory.setFont(new Font("Arial", Font.BOLD, 30));
		lblRestockHistory.setForeground(Color.BLACK);
		
		String[] supplierList = retrieveSupplierList();
		if(supplierList != null) {
			cbSupplierName = new JComboBox<String>(supplierList);
			cbSupplierName.setBounds(260, 25, 200, 30);
			cbSupplierName.setBackground(Color.WHITE);
			cbSupplierName.setForeground(Color.BLACK);
			cbSupplierName.setFocusable(false);
			cbSupplierName.setFont(regularText);
			cbSupplierName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((JPanel)spSupplier.getViewport().getView()).removeAll();
					retrieveSupplierData(spSupplier, (String)cbSupplierName.getSelectedItem());
					revalidate();
					repaint();
				}
			});
			ConfigureComboBox.addHorizontalScrollBar(cbSupplierName);
		}
		
		panTitleRow = new JPanel();
		panTitleRow.setBounds(0, 70, WIDTH, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		panTitleRow.setLayout(null);
		
		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 40, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleInvoiceNo = new JLabel("Invoice No.", SwingConstants.CENTER);
		lblTitleInvoiceNo.setBounds(40, 0, 202, 40);
		lblTitleInvoiceNo.setFont(titleFont);
		lblTitleInvoiceNo.setForeground(Color.BLACK);
		lblTitleInvoiceNo.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleSupplierName = new JLabel("  Supplier Name");
		lblTitleSupplierName.setBounds(242, 0, 558, 40);
		lblTitleSupplierName.setFont(titleFont);
		lblTitleSupplierName.setForeground(Color.BLACK);
		
		lblTitleOrderPlaced = new JLabel("Order Placed", SwingConstants.CENTER);
		lblTitleOrderPlaced.setBounds(800, 0, 182, 40);
		lblTitleOrderPlaced.setFont(titleFont);
		lblTitleOrderPlaced.setForeground(Color.BLACK);
		lblTitleOrderPlaced.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleOrderReceived = new JLabel("Order Received", SwingConstants.CENTER);
		lblTitleOrderReceived.setBounds(978, 0, 180, 40);
		lblTitleOrderReceived.setFont(titleFont);
		lblTitleOrderReceived.setForeground(Color.BLACK);
		
		lblTitleRestockFee = new JLabel("Restock Fee", SwingConstants.CENTER);
		lblTitleRestockFee.setBounds(1160, 0, 180, 40);
		lblTitleRestockFee.setFont(titleFont);
		lblTitleRestockFee.setForeground(Color.BLACK);
		lblTitleRestockFee.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, TITLE_BORDER_COLOR));
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleInvoiceNo);
		panTitleRow.add(lblTitleSupplierName);
		panTitleRow.add(lblTitleOrderPlaced);
		panTitleRow.add(lblTitleOrderReceived);
		panTitleRow.add(lblTitleRestockFee);
		
		panInvoiceRow = new JPanel();
		panInvoiceRow.setBounds(0, 110, WIDTH, HEIGHT - 110);
		panInvoiceRow.setLayout(null);
		
		spSupplier = new JScrollPane(new JPanel());
		spSupplier.setBounds(0, 0, WIDTH, HEIGHT - 110);
		spSupplier.setHorizontalScrollBar(null);
		spSupplier.getVerticalScrollBar().setUnitIncrement(16);
		
		retrieveSupplierData(spSupplier, (String)cbSupplierName.getSelectedItem());
		
		panInvoiceRow.add(spSupplier);
		
		panContent.add(lblRestockHistory);
		panContent.add(cbSupplierName);
		panContent.add(panTitleRow);
		panContent.add(panInvoiceRow);
			
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
			categoryArrayList.add("All");
			
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

	public void retrieveSupplierData(JScrollPane spSupplier, String category) {
		JPanel panSupplierView = (JPanel)spSupplier.getViewport().getView();
		String orderPlaced, orderReceived;
		String restockFee;
		String supplierName;
		
		panSupplierView.setPreferredSize(new Dimension(WIDTH, HEIGHT - 110));
		panSupplierView.setBackground(Color.WHITE);
		panSupplierView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql;
			
			if(category.equals("All")) {
				sql = "SELECT * FROM restock;";
			}else {
				sql = "SELECT * FROM restock "
				    + "WHERE supplier_name = \"" + category + "\";";
			}

			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					final JPanel panSupplierRow;
					final JLabel lblSupplierNo;
					final JLabel lblInvoiceNo;
					final JLabel lblSupplierName;
					final JLabel lblOrderPlaced;
					final JLabel lblOrderReceived;
					final JLabel lblRestockFee;
					
					final String invoiceNo = String.valueOf(rs.getInt("invoice_no"));
					orderPlaced = String.valueOf(rs.getDate("invoice_placed_date"));
					orderReceived = String.valueOf(rs.getDate("invoice_received_date"));
					restockFee = "RM " + String.format("%.2f", rs.getDouble("invoice_restock_fee"));
					supplierName = rs.getString("supplier_name");
					
					panSupplierRow = new JPanel();
					panSupplierRow.setLayout(null);
					panSupplierRow.setPreferredSize(new Dimension(WIDTH, 43));
					panSupplierRow.setBackground(null);
					panSupplierRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					panSupplierRow.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							dispose();
							new InvoiceFrame(invoiceNo);
						}
						
						public void mouseExited(MouseEvent e) {
							panSupplierRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panSupplierRow.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});

					lblSupplierNo = new JLabel(++count + ".", SwingConstants.CENTER);
					lblSupplierNo.setBounds(0, 0, 40, 43);
					lblSupplierNo.setFont(regularText);
					lblSupplierNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
					lblSupplierNo.setForeground(Color.BLACK);
					lblSupplierNo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, TITLE_BORDER_COLOR));
					
					lblInvoiceNo = new JLabel(invoiceNo, SwingConstants.CENTER);
					lblInvoiceNo.setBounds(40, 0, 200, 43);
					lblInvoiceNo.setFont(regularText);
					lblInvoiceNo.setForeground(Color.BLACK);
					
					lblSupplierName = new JLabel("  " + supplierName);
					lblSupplierName.setBounds(240, 0, 560, 43);
					lblSupplierName.setFont(regularText);
					lblSupplierName.setForeground(Color.BLACK);
					lblSupplierName.setBackground(null);
					lblSupplierName.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
					lblSupplierName.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							dispose();
							new InvoiceFrame(invoiceNo);
						}
						
						public void mouseExited(MouseEvent e) {
							panSupplierRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panSupplierRow.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					lblOrderPlaced = new JLabel(orderPlaced, SwingConstants.CENTER);
					lblOrderPlaced.setBounds(800, 0, 180, 43);
					lblOrderPlaced.setFont(regularText);
					lblOrderPlaced.setForeground(Color.BLACK);
					
					lblOrderReceived = new JLabel(orderReceived, SwingConstants.CENTER);
					lblOrderReceived.setBounds(980, 0, 180, 43);
					lblOrderReceived.setFont(regularText);
					lblOrderReceived.setForeground(Color.BLACK);
					lblOrderReceived.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
					
					lblRestockFee = new JLabel(restockFee, SwingConstants.CENTER);
					lblRestockFee.setBounds(1160, 0, 180, 43);
					lblRestockFee.setFont(regularText);
					lblRestockFee.setForeground(Color.BLACK);
					
					panSupplierRow.add(lblSupplierNo);
					panSupplierRow.add(lblInvoiceNo);
					panSupplierRow.add(lblSupplierName);
					panSupplierRow.add(lblOrderPlaced);
					panSupplierRow.add(lblOrderReceived);
					panSupplierRow.add(lblRestockFee);
					
					panSupplierView.add(panSupplierRow);
					panSupplierView.setPreferredSize(new Dimension(WIDTH, count * 43));
					
					lblSupplierName.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							
						}
						
						public void mouseExited(MouseEvent e) {
							lblInvoiceNo.setBackground(null);
							lblSupplierName.setBackground(null);
							lblOrderPlaced.setBackground(null);
							lblOrderReceived.setBackground(null);
							lblRestockFee.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							lblInvoiceNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblSupplierName.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblOrderPlaced.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblOrderReceived.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblRestockFee.setBackground(PRODUCT_NO_BACKGROUND_COLOR);							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panSupplierRow.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							
						}
						
						public void mouseExited(MouseEvent e) {
							lblInvoiceNo.setBackground(null);
							lblSupplierName.setBackground(null);
							lblOrderPlaced.setBackground(null);
							lblOrderReceived.setBackground(null);
							lblRestockFee.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							lblInvoiceNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblSupplierName.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblOrderPlaced.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblOrderReceived.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
							lblRestockFee.setBackground(PRODUCT_NO_BACKGROUND_COLOR);						
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
				}
				
				if(count < MAX_NUM_OF_ROW) {
					for(int i = 0; i < MAX_NUM_OF_ROW - count; i++) {
						final JPanel panSupplierRow;
						final JLabel lblSupplierNo;
						final JLabel lblInvoiceNo;
						final JLabel lblSupplierName;
						final JLabel lblOrderPlaced;
						final JLabel lblOrderReceived;
						final JLabel lblRestockFee;
						
						panSupplierRow = new JPanel();
						panSupplierRow.setLayout(null);
						panSupplierRow.setPreferredSize(new Dimension(WIDTH, 43));
						panSupplierRow.setBackground(null);

						lblSupplierNo = new JLabel();
						lblSupplierNo.setBounds(0, 0, 40, 43);
						lblSupplierNo.setFont(regularText);
						lblSupplierNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
						lblSupplierNo.setForeground(Color.BLACK);
						lblSupplierNo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, TITLE_BORDER_COLOR));
						
						lblInvoiceNo = new JLabel();
						lblInvoiceNo.setBounds(40, 0, 200, 43);
						lblInvoiceNo.setFont(regularText);
						lblInvoiceNo.setForeground(Color.BLACK);
						
						lblSupplierName = new JLabel();
						lblSupplierName.setBounds(240, 0, 560, 43);
						lblSupplierName.setFont(regularText);
						lblSupplierName.setForeground(Color.BLACK);
						lblSupplierName.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
						
						lblOrderPlaced = new JLabel();
						lblOrderPlaced.setBounds(800, 0, 180, 43);
						lblOrderPlaced.setFont(regularText);
						lblOrderPlaced.setForeground(Color.BLACK);
						
						lblOrderReceived = new JLabel();
						lblOrderReceived.setBounds(980, 0, 180, 43);
						lblOrderReceived.setFont(regularText);
						lblOrderReceived.setForeground(Color.BLACK);
						lblOrderReceived.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
						
						lblRestockFee = new JLabel();
						lblRestockFee.setBounds(1160, 0, 180, 43);
						lblRestockFee.setFont(regularText);
						lblRestockFee.setForeground(Color.BLACK);
						
						panSupplierRow.add(lblSupplierNo);
						panSupplierRow.add(lblInvoiceNo);
						panSupplierRow.add(lblSupplierName);
						panSupplierRow.add(lblOrderPlaced);
						panSupplierRow.add(lblOrderReceived);
						panSupplierRow.add(lblRestockFee);
						
						panSupplierView.add(panSupplierRow);
						panSupplierView.setPreferredSize(new Dimension(WIDTH, count * 43));
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
	}
}
