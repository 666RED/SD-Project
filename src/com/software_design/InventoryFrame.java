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
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InventoryFrame extends JFrame{
	
	JPanel panHeader, panFooter, panAddNewProduct, panSearchBar, panContent, panTitleRow, panProduct, panSearchResult;
	JTextField tfSearchBar;
	JLabel lblAddIcon, lblAddNewProduct, lblSearchIcon, lblPageName, lblTitleNo, lblTitleName, lblTitleBarcode, 
		   lblTitleCost, lblTitlePrice, lblTitleQuantity, lblDustbinIcon;
	JComboBox cbProductList;
	JButton btnRestock;
	ImageIcon searchIcon = IconClass.createIcon("Image\\loupe.png", 20, 20);
	ImageIcon addIcon = IconClass.createIcon("Image\\plus2.png", 22, 22);
	ImageIcon dustbinIcon = IconClass.createIcon("Image\\dustbin.png", 20, 20);
	ImageIcon deletedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	ImageIcon lowIcon = IconClass.createIcon("Image\\low.png", 70, 70);
	JScrollPane spProduct, spSearch;
	final Color ADD_NEW_PRODUCT_COLOR = new Color(179, 179, 179);
	final Color RESTOCK_BUTTON_COLOR = new Color(250, 128, 34);
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color PRODUCT_NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color PRODUCT_BORDER_COLOR = new Color(168, 168, 168);
	final Color PRODUCT_ROW_HOVER_COLOR = new Color(210, 210, 210);
	Font titleFont = new Font("Arial", Font.BOLD, 20);
	
	final int MAX_NUM_OF_ROW = 10;
	
	InventoryFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Inventory");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		panAddNewProduct = new JPanel();
		panAddNewProduct.setBounds(10, 60, 190, 35);
		panAddNewProduct.setBackground(ADD_NEW_PRODUCT_COLOR);
		panAddNewProduct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panAddNewProduct.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				dispose();
				new AddNewProductFrame();
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
		
		lblAddIcon = new JLabel(addIcon);
		lblAddIcon.setBounds(0, 5, 12, 12);
		
		lblAddNewProduct = new JLabel("Add New Product");
		lblAddNewProduct.setBounds(30, 5, 50, 18);
		lblAddNewProduct.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAddNewProduct.setForeground(Color.BLACK);
		
		panAddNewProduct.add(lblAddIcon);
		panAddNewProduct.add(lblAddNewProduct);
		
		panSearchBar = new JPanel();
		panSearchBar.setLayout(null);
		panSearchBar.setBounds(220, 60, 350, 35);
		panSearchBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panSearchBar.setBackground(Color.WHITE);
		
		lblSearchIcon = new JLabel(searchIcon);
		lblSearchIcon.setBounds(5, 8, 20, 20);
		
		spSearch = new JScrollPane(new JPanel());
		spSearch.setBounds(0, 0, 370, 200);
		spSearch.getVerticalScrollBar().setUnitIncrement(16);
		
		panSearchResult = new JPanel();
		panSearchResult.setBounds(210, 105, 370, 200);
		panSearchResult.setLayout(null);
		panSearchResult.setVisible(false);
		
		tfSearchBar = new JTextField("");
		tfSearchBar.setBounds(35, 5, 310, 25);
		tfSearchBar.setFont(new Font("Arial", Font.PLAIN, 20));
		tfSearchBar.setBorder(null);
		tfSearchBar.setForeground(Color.BLACK);
		tfSearchBar.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchProduct(tfSearchBar.getText(), spSearch, (String)cbProductList.getSelectedItem());
				revalidate();
				repaint();
			}
			
			public void insertUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchProduct(tfSearchBar.getText(), spSearch, (String)cbProductList.getSelectedItem());
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
				searchProduct(tfSearchBar.getText(), spSearch, (String)cbProductList.getSelectedItem());
			}
		});
		
		panSearchResult.add(spSearch);
		
		panSearchBar.add(lblSearchIcon);
		panSearchBar.add(tfSearchBar);
		
		String[] productList = {"All", "Beverage", "Food", "Daily Supplies"};
		cbProductList = new JComboBox(productList);
		cbProductList.setBounds(600, 60, 200, 35);
		cbProductList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cbProductList.setBackground(Color.WHITE);
		cbProductList.setFont(new Font("Arial", Font.PLAIN, 20));
		cbProductList.setForeground(Color.BLACK);
		ConfigureComboBox.addHorizontalScrollBar(cbProductList);
		cbProductList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfSearchBar.setText("Product Name / Barcode");
				tfSearchBar.setForeground(Color.GRAY);
				if(((String)cbProductList.getSelectedItem()).equals("All")) {
					((JPanel)spProduct.getViewport().getView()).removeAll();
					retrieveProductData(spProduct, "All");
					revalidate();
					repaint();
				}else {
					((JPanel)spProduct.getViewport().getView()).removeAll();
					retrieveProductData(spProduct, (String)cbProductList.getSelectedItem());
					revalidate();
					repaint();
				}
			}
		});

		lblPageName = new JLabel("Inventory");
		lblPageName.setBounds(10, 120, 150, 30);
		lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageName.setForeground(Color.BLACK);
		
		btnRestock = new JButton("RESTOCK");
		btnRestock.setBounds(170, 120, 120, 30);
		btnRestock.setFont(new Font("Arial", Font.BOLD, 15));
		btnRestock.setForeground(Color.WHITE);
		btnRestock.setBackground(RESTOCK_BUTTON_COLOR);
		btnRestock.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRestock.setFocusable(false);
		btnRestock.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				dispose();
				new RestockFrame();
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
		panContent.setLayout(null);
		panContent.setBounds(10, 160, 1340, 470);
		panContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBounds(0, 0, 1340, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);

		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 50, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleName = new JLabel("Name");
		lblTitleName.setBounds(60, 0, 600, 40);
		lblTitleName.setFont(titleFont);
		lblTitleName.setForeground(Color.BLACK);
		
		lblTitleBarcode = new JLabel("Barcode", SwingConstants.CENTER);
		lblTitleBarcode.setBounds(650, 0, 180, 40);
		lblTitleBarcode.setFont(titleFont);
		lblTitleBarcode.setForeground(Color.BLACK);
		
		lblTitleCost = new JLabel("Cost(Each)", SwingConstants.CENTER);
		lblTitleCost.setBounds(830, 0, 150, 40);
		lblTitleCost.setFont(titleFont);
		lblTitleCost.setForeground(Color.BLACK);
		
		lblTitlePrice = new JLabel("Price(Each)", SwingConstants.CENTER);
		lblTitlePrice.setBounds(980, 0, 150, 40);
		lblTitlePrice.setFont(titleFont);
		lblTitlePrice.setForeground(Color.BLACK);
		
		lblTitleQuantity = new JLabel("Quantity");
		lblTitleQuantity.setBounds(1150, 0, 210, 40);
		lblTitleQuantity.setFont(titleFont);
		lblTitleQuantity.setForeground(Color.BLACK);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleName);
		panTitleRow.add(lblTitleBarcode);
		panTitleRow.add(lblTitleCost);
		panTitleRow.add(lblTitlePrice);
		panTitleRow.add(lblTitleQuantity);
		
		panProduct = new JPanel();
		panProduct.setBounds(0, 40, 1340, 430);
		panProduct.setLayout(null);

		spProduct = new JScrollPane(new JPanel());
		spProduct.setBounds(0, 0, 1340, 430);
		spProduct.setHorizontalScrollBar(null);
		spProduct.getVerticalScrollBar().setUnitIncrement(16);
		
		retrieveProductData(spProduct, "All");
		
		panProduct.add(spProduct);
		
		panContent.add(panTitleRow);
		panContent.add(panProduct);
		
		panHeader = Header.setHeader(this, "Inventory");
		panFooter = Footer.setFooter(this);
		
		add(panHeader);
		add(panFooter);
		add(panSearchResult);
		add(panAddNewProduct);
		add(panSearchBar);
		add(cbProductList);
		add(lblPageName);
		add(btnRestock);
		add(panContent);
		
		setVisible(true);
	}
	
	public void retrieveProductData(JScrollPane spProduct, String productCategory) {
		JPanel panProductView = (JPanel)spProduct.getViewport().getView();
		String productName;
		Double productCost, productPrice;
		int productNo, productQuantity;
		
		panProductView.setPreferredSize(new Dimension(1340, 430));
		panProductView.setBackground(Color.WHITE);
		panProductView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		Connection conn = null;
		try {
			Font productFont = new Font("Arial", Font.PLAIN, 20);
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql;
			
			if(productCategory.equals("All")) {
				sql = "SELECT * FROM product "
					+ "ORDER BY (product_quantity - product_notification_quantity);";
			}else {
				sql = "SELECT * FROM product "
					+ "WHERE product_category = \"" + (String)cbProductList.getSelectedItem() + "\" "
					+ "ORDER BY (product_quantity - product_notification_quantity);";
			}
			

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					final JPanel panProductRow;
					JPanel panName;
					JLabel lblProductNo;
					final JLabel lblProductName;
					final JLabel lblProductBarcode;
					final JLabel lblLowIcon;
					JLabel lblProductCost, lblProductPrice, lblProductQuantity, lblDustbinIcon;
					
					panProductRow = new JPanel();
					panProductRow.setLayout(null);
					panProductRow.setPreferredSize(new Dimension(1340, 43));
					panProductRow.setBackground(null);
					panProductRow.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							
						}
						
						public void mouseExited(MouseEvent e) {
							panProductRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panProductRow.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					productNo = ++count;
					productName = rs.getString("product_name");
					final String productBarcode = rs.getString("product_barcode");
					productCost = rs.getDouble("product_cost");
					productPrice = rs.getDouble("product_price");
					productQuantity = rs.getInt("product_quantity");
					
					lblProductNo = new JLabel(productNo + ".", SwingConstants.CENTER);
					lblProductNo.setBounds(0, 0, 50, 43);
					lblProductNo.setFont(productFont);
					lblProductNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
					lblProductNo.setOpaque(true);
					lblProductNo.setForeground(Color.BLACK);
					
					panName = new JPanel();
					panName.setBounds(50, 0, 600, 43);
					panName.setLayout(null);
					panName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					panName.setBackground(null);
					panName.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, PRODUCT_BORDER_COLOR));
					
					lblProductName = new JLabel(productName);
					lblProductName.setBounds(10, 0, 560, 43);
					lblProductName.setFont(productFont);
					lblProductName.setForeground(Color.BLACK);
					
					lblProductBarcode = new JLabel(productBarcode, SwingConstants.CENTER);
					lblProductBarcode.setBounds(650, 0, 180, 43);
					lblProductBarcode.setFont(productFont);
					lblProductBarcode.setForeground(Color.BLACK);
					
					lblDustbinIcon = new JLabel(dustbinIcon);
					lblDustbinIcon.setBounds(570, 10, 30, 20);
					lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDustbinIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete this product? \n(" + lblProductName.getText() + ")", "DELETE PRODUCT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							if(answer == JOptionPane.YES_OPTION) {
								JOptionPane.showMessageDialog(null, "Product Deleted Succussfully", "Deleted", JOptionPane.DEFAULT_OPTION, deletedIcon);
								deleteProduct(lblProductBarcode.getText());
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
					
					panName.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {

						}
						
						public void mousePressed(MouseEvent e) {
							dispose();
							new ManageProductFrame(productBarcode);
						}
						
						public void mouseExited(MouseEvent e) {
							panProductRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panProductRow.setBackground(PRODUCT_ROW_HOVER_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panName.add(lblProductName);
					panName.add(lblDustbinIcon);
					
					lblProductCost = new JLabel("RM " + String.format("%.2f", productCost), SwingConstants.CENTER);
					lblProductCost.setBounds(830, 0, 150, 43);
					lblProductCost.setFont(productFont);
					lblProductCost.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, PRODUCT_BORDER_COLOR));
					lblProductCost.setForeground(Color.BLACK);
					
					lblProductPrice = new JLabel("RM " + String.format("%.2f", productPrice), SwingConstants.CENTER);
					lblProductPrice.setBounds(980, 0, 150, 43);
					lblProductPrice.setFont(productFont);
					lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, PRODUCT_BORDER_COLOR));
					lblProductPrice.setForeground(Color.BLACK);
					
					lblProductQuantity = new JLabel(String.valueOf(productQuantity));
					lblProductQuantity.setBounds(1180, 0, 50, 43);
					lblProductQuantity.setFont(productFont);
					lblProductQuantity.setForeground(Color.BLACK);
					
					lblLowIcon = new JLabel(lowIcon);
					lblLowIcon.setBounds(1250, 0, 50, 35);
					if(rs.getInt("product_quantity") <= rs.getInt("product_notification_quantity")) {
						lblLowIcon.setVisible(true);
					}else {
						lblLowIcon.setVisible(false);
					}
					
					panProductRow.add(lblProductNo);
					panProductRow.add(panName);	
					panProductRow.add(lblProductBarcode);
					panProductRow.add(lblProductCost);
					panProductRow.add(lblProductPrice);
					panProductRow.add(lblProductQuantity);
					panProductRow.add(lblLowIcon);
										
					panProductView.add(panProductRow);
					panProductView.setPreferredSize(new Dimension(1340, count * 43));
				}
				
				if(count < MAX_NUM_OF_ROW) {
					for(int i = 0; i < MAX_NUM_OF_ROW - count; i++) {
						final JPanel panProductRow;
						JPanel panName;
						JLabel lblProductNo;
						final JLabel lblProductName;
						final JLabel lblProductBarcode;
						final JLabel lblLowIcon;
						JLabel lblProductCost, lblProductPrice, lblProductQuantity, lblDustbinIcon;
						
						panProductRow = new JPanel();
						panProductRow.setLayout(null);
						panProductRow.setPreferredSize(new Dimension(1340, 43));
						panProductRow.setBackground(null);
						
						lblProductNo = new JLabel();
						lblProductNo.setBounds(0, 0, 50, 43);
						lblProductNo.setFont(productFont);
						lblProductNo.setBackground(PRODUCT_NO_BACKGROUND_COLOR);
						lblProductNo.setOpaque(true);
						lblProductNo.setForeground(Color.BLACK);
						
						panName = new JPanel();
						panName.setBounds(50, 0, 600, 43);
						panName.setLayout(null);
						panName.setBackground(null);
						panName.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, PRODUCT_BORDER_COLOR));
						
						lblProductName = new JLabel();
						lblProductName.setBounds(10, 0, 560, 43);
						lblProductName.setFont(productFont);
						lblProductName.setForeground(Color.BLACK);
						
						lblProductBarcode = new JLabel();
						lblProductBarcode.setBounds(650, 0, 180, 43);
						lblProductBarcode.setFont(productFont);
						lblProductBarcode.setForeground(Color.BLACK);
						
						panName.addMouseListener(new MouseListener() {
							
							public void mouseReleased(MouseEvent e) {

							}
							
							public void mousePressed(MouseEvent e) {
								
							}
							
							public void mouseExited(MouseEvent e) {
								
							}
							
							public void mouseEntered(MouseEvent e) {
								panProductRow.setBackground(null);
							}
							
							public void mouseClicked(MouseEvent e) {
								
							}
						});
						
						panName.add(lblProductName);
						
						lblProductCost = new JLabel();
						lblProductCost.setBounds(830, 0, 150, 43);
						lblProductCost.setFont(productFont);
						lblProductCost.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, PRODUCT_BORDER_COLOR));
						lblProductCost.setForeground(Color.BLACK);
						
						lblProductPrice = new JLabel();
						lblProductPrice.setBounds(980, 0, 150, 43);
						lblProductPrice.setFont(productFont);
						lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, PRODUCT_BORDER_COLOR));
						lblProductPrice.setForeground(Color.BLACK);
						
						lblProductQuantity = new JLabel();
						lblProductQuantity.setBounds(1180, 0, 50, 43);
						lblProductQuantity.setFont(productFont);
						lblProductQuantity.setForeground(Color.BLACK);
						
						panProductRow.add(lblProductNo);
						panProductRow.add(panName);	
						panProductRow.add(lblProductBarcode);
						panProductRow.add(lblProductCost);
						panProductRow.add(lblProductPrice);
						panProductRow.add(lblProductQuantity);
											
						panProductView.add(panProductRow);
						panProductView.setPreferredSize(new Dimension(1340, count * 43));
					}
				}
			}			
		}catch(Exception e1) {
			e1.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void deleteProduct(String productBarcode) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql1 = "DELETE FROM product_receipt "
				    + "WHERE product_barcode = \"" + productBarcode + "\";";
			
			String sql2 = "DELETE FROM product "
					   + "WHERE product_barcode = \"" + productBarcode + "\";";

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);

			cbProductList.setSelectedItem("All");
			JPanel panProductView = (JPanel)spProduct.getViewport().getView();
			panProductView.removeAll();
			retrieveProductData(spProduct, "All");
			revalidate();
			repaint();
			
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
	
	public void searchProduct(String productName, JScrollPane spSearch, String category) {
		Connection conn = null;
		JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
		panSearchView.setBackground(Color.WHITE);
		panSearchView.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
		final Color SEARCH_RESULT_BACKGROUND_COLOR = new Color(205, 205, 205);
		int count = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql;
			
			if(category.equals("All")) {
				sql = "SELECT product_name, product_barcode FROM product "
					+ "WHERE product_name LIKE '" + productName + "%' "
					+ "ORDER BY product_name;";
			}else {
				sql = "SELECT product_name, product_barcode FROM product "
					+ "WHERE product_name LIKE '" + productName + "%' AND "
					+ "product_category = \"" + category + "\" "
					+ "ORDER BY product_name;";
			}

			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next() && !Pattern.matches("\\s*", productName)) {
					final String productBarcode = rs.getString("product_barcode");

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
							dispose();
							new ManageProductFrame(productBarcode);
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
			
			if(category.equals("All")) {
				sql = "SELECT product_name, product_barcode FROM product "
					+ "WHERE product_barcode LIKE '" + productName + "%' "
					+ "ORDER BY product_name;";
			}else {
				sql = "SELECT product_name, product_barcode FROM product "
					+ "WHERE product_barcode LIKE '" + productName + "%' AND "
					+ "product_category = \"" + category + "\" "
					+ "ORDER BY product_name;";
			}
			
			rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next() && !Pattern.matches("\\s*", productName)) {
					final String productBarcode = rs.getString("product_barcode");
					
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
							dispose();
							new ManageProductFrame(productBarcode);
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

}