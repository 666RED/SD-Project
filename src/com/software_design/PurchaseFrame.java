package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class PurchaseFrame extends JFrame{
	
	// original component
	JPanel panHeader, panFooter, panContent, panTitleRow, panProduct, panCalc, panSearchBar, panSearchResult;
	JLabel lblPageTitle, lblSearchIcon, lblBarcodeScannerIcon, lblTitleNo, lblTitleName, lblTitlePrice,
		   lblTitleQuantity, lblTitleTotal, lblCalcTotal, lblCalcPayment, lblCalcChange;
	JComboBox cbProductList;
	JTextField tfSearchBar, tfCalcTotal, tfCalcPayment, tfCalcChange;
	JButton btnPurchase;
	ImageIcon searchIcon = IconClass.createIcon("Image\\loupe.png", 20, 20);
	ImageIcon barcodeScannerIcon = IconClass.createIcon("Image\\barcode-scan.png", 35, 35);
	ImageIcon decreaseIcon = IconClass.createIcon("Image\\minus.png", 20, 20);
	ImageIcon increaseIcon = IconClass.createIcon("Image\\plus2.png", 20, 20);
	ImageIcon dustbinIcon = IconClass.createIcon("Image\\dustbin.png", 20, 20);
	ImageIcon connectedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	JScrollPane spProduct, spSearch;
	
	// for adding product
	Font productFont = new Font("Arial", Font.PLAIN, 20);
	JPanel panProductRow;
	JPanel panName, panQuantity;
	JLabel lblProductNo, lblDustbinIcon;
	JLabel lblDecreaseIcon, lblIncreaseIcon;
	
	final Color PURCHASE_BUTTON_COLOR = new Color(246, 84, 70);
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(168, 168, 168);
	final Color PRODUCT_BACKGROUND_COLOR = new Color(205, 205, 205);
	Font titleFont = new Font("Arial", Font.BOLD, 20);
	Font calcLabelFont = new Font("Arial", Font.BOLD, 25);
	Font calcTextFieldFont = new Font("Arial", Font.PLAIN, 25);
	boolean validCalc = false;
	
	private int numberOfProductAdded = 0;
	private List<String> barcodeList = new ArrayList<String>();
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>(); 
	private double sum = 0;
	private String[] calcList = new String[3];
	
	PurchaseFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Purchase");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		panContent = new JPanel();
		panContent.setLayout(null);
		panContent.setBounds(0, 40, 1400, 595);
		
		lblPageTitle = new JLabel("Purchase");
		lblPageTitle.setBounds(10, 30, 150, 30);
		lblPageTitle.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageTitle.setForeground(Color.BLACK);
		
		panSearchResult = new JPanel();
		panSearchResult.setBounds(170, 70, 370, 200);
		panSearchResult.setLayout(null);
		panSearchResult.setVisible(false);
		
		spSearch = new JScrollPane(new JPanel());
		spSearch.setBounds(0, 0, 370, 200);
		spSearch.getVerticalScrollBar().setUnitIncrement(16);
		
		panSearchBar = new JPanel();
		panSearchBar.setLayout(null);
		panSearchBar.setBounds(180, 25, 350, 35);
		panSearchBar.setBorder(BorderFactory.createLineBorder(TITLE_ROW_BACKGROUND_COLOR));
		panSearchBar.setBackground(Color.WHITE);
		
		lblSearchIcon = new JLabel(searchIcon);
		lblSearchIcon.setBounds(5, 8, 20, 20);
		
		String[] productList = {"All", "Beverage", "Food", "Daily Supplies"};
		cbProductList = new JComboBox<String>(productList);
		cbProductList.setBounds(550, 25, 200, 35);
		cbProductList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cbProductList.setBackground(Color.WHITE);
		cbProductList.setFont(new Font("Arial", Font.PLAIN, 20));
		cbProductList.setForeground(Color.BLACK);
		ConfigureComboBox.addHorizontalScrollBar(cbProductList);
		cbProductList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				tfSearchBar.setText("Product Name / Barcode");
				tfSearchBar.setForeground(Color.GRAY);
			}
		});
		
		tfSearchBar = new JTextField("Product Name / Barcode");
		tfSearchBar.setBounds(35, 5, 310, 25);
		tfSearchBar.setFont(new Font("Arial", Font.PLAIN, 20));
		tfSearchBar.setBorder(null);
		tfSearchBar.setForeground(Color.GRAY);
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
		
		panSearchBar.add(lblSearchIcon);
		panSearchBar.add(tfSearchBar);
		
		panSearchResult.add(spSearch);
		
		lblBarcodeScannerIcon = new JLabel(barcodeScannerIcon);
		lblBarcodeScannerIcon.setBounds(770, 25, 35, 35);
		lblBarcodeScannerIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBarcodeScannerIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Connect to Barcode Scanner Successfully", "Connected", JOptionPane.INFORMATION_MESSAGE, connectedIcon);
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBounds(10, 80, 1340, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		
		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 50, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleName = new JLabel("Name");
		lblTitleName.setBounds(60, 0, 680, 40);
		lblTitleName.setFont(titleFont);
		lblTitleName.setForeground(Color.BLACK);
		
		lblTitlePrice = new JLabel("Price", SwingConstants.CENTER);
		lblTitlePrice.setBounds(740, 0, 200, 40);
		lblTitlePrice.setFont(titleFont);
		lblTitlePrice.setForeground(Color.BLACK);
		
		lblTitleQuantity = new JLabel("Quantity", SwingConstants.CENTER);
		lblTitleQuantity.setBounds(940, 0, 200, 40);
		lblTitleQuantity.setFont(titleFont);
		lblTitleQuantity.setForeground(Color.BLACK);
		
		lblTitleTotal = new JLabel("Total", SwingConstants.CENTER);
		lblTitleTotal.setBounds(1140, 0, 200, 40);
		lblTitleTotal.setFont(titleFont);
		lblTitleTotal.setForeground(Color.BLACK);

		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleName);
		panTitleRow.add(lblTitlePrice);
		panTitleRow.add(lblTitleQuantity);
		panTitleRow.add(lblTitleTotal);
		
		panProduct = new JPanel();
		panProduct.setBounds(10, 120, 1340, 300);
		panProduct.setLayout(null);
		
		spProduct = new JScrollPane(new JPanel());
		spProduct.setBounds(0, 0, 1340, 300);
		spProduct.setHorizontalScrollBar(null);
		spProduct.getVerticalScrollBar().setUnitIncrement(16);
		
		spProduct.getViewport().getView().setBackground(Color.WHITE);
		
		panProduct.add(spProduct);
		
		panCalc = new JPanel();
		panCalc.setLayout(null);
		panCalc.setBounds(1020, 420, 330, 120);
		panCalc.setBackground(Color.WHITE);
		panCalc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblCalcTotal = new JLabel("Total:");
		lblCalcTotal.setFont(calcLabelFont);
		lblCalcTotal.setBounds(52, 0, 78, 30);
		lblCalcTotal.setForeground(Color.BLACK);
		
		tfCalcTotal = new JTextField();
		tfCalcTotal.setFont(calcTextFieldFont);
		tfCalcTotal.setBounds(130, 0, 200, 30);
		tfCalcTotal.setForeground(Color.BLACK);
		tfCalcTotal.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		tfCalcTotal.setHorizontalAlignment(JTextField.CENTER);
		tfCalcTotal.setEditable(false);
		tfCalcTotal.setBackground(null);

		lblCalcPayment = new JLabel("Payment:");
		lblCalcPayment.setFont(calcLabelFont);
		lblCalcPayment.setBounds(10, 40, 120, 30);
		lblCalcPayment.setForeground(Color.BLACK);
		
		tfCalcPayment = new JTextField();
		tfCalcPayment.setFont(calcTextFieldFont);
		tfCalcPayment.setBounds(130, 40, 200, 30);
		tfCalcPayment.setForeground(Color.BLACK);
		tfCalcPayment.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		tfCalcPayment.setHorizontalAlignment(JTextField.CENTER);
		tfCalcPayment.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(tfCalcPayment.getText().equals("")) {
					tfCalcPayment.setText("RM");
					tfCalcPayment.setForeground(Color.GRAY);
				}
			}
			
			public void focusGained(FocusEvent e) {
				if(tfCalcPayment.getText().equals("RM")) {
					tfCalcPayment.setText("");
					tfCalcPayment.setForeground(Color.BLACK);
				}
			}
		});
		
		
		tfCalcPayment.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {

			}
			
			public void keyReleased(KeyEvent e) {
				
			}
			
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(tfCalcTotal.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please insert the product", "Invalid Total", JOptionPane.WARNING_MESSAGE);
						tfCalcPayment.setText("");
						validCalc = false;
						return;
					}
					boolean validPayment = validatePayment(tfCalcPayment.getText());
					boolean enoughPayment = false;
					if(validPayment) {
						enoughPayment = Double.parseDouble(tfCalcPayment.getText()) >= Double.parseDouble(tfCalcTotal.getText().substring(3));
						validCalc = false;
					}
					
					if(validPayment && enoughPayment) {
						tfCalcPayment.setText("RM " + String.format("%.2f", Double.parseDouble(tfCalcPayment.getText())));
						tfCalcPayment.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
						calculateChange();
						validCalc = true;
					}else if(!validPayment){
						tfCalcPayment.setBorder(BorderFactory.createLineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Invalid payment", "Invalid Input", JOptionPane.WARNING_MESSAGE);
						tfCalcPayment.setText("");
						validCalc = false;
					}else if(!enoughPayment) {
						tfCalcPayment.setBorder(BorderFactory.createLineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Payment not enough", "Not enough", JOptionPane.WARNING_MESSAGE);
						validCalc = false;
					}
				}
			}
		});
		
		lblCalcChange = new JLabel("Change:");
		lblCalcChange.setFont(calcLabelFont);
		lblCalcChange.setBounds(20, 80, 110, 30);
		lblCalcChange.setForeground(Color.BLACK);
		
		tfCalcChange = new JTextField();
		tfCalcChange.setFont(new Font("Arial", Font.BOLD, 25));
		tfCalcChange.setBounds(130, 80, 200, 30);
		tfCalcChange.setForeground(Color.BLACK);
		tfCalcChange.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		tfCalcChange.setHorizontalAlignment(JTextField.CENTER);
		tfCalcChange.setEditable(false);
		tfCalcChange.setBackground(null);
		
		panCalc.add(lblCalcTotal);
		panCalc.add(tfCalcTotal);
		panCalc.add(lblCalcPayment);
		panCalc.add(tfCalcPayment);
		panCalc.add(lblCalcChange);
		panCalc.add(tfCalcChange);
		
		btnPurchase = new JButton("Purchase");
		btnPurchase.setBounds(1020, 550, 330, 40);
		btnPurchase.setBorder(null);
		btnPurchase.setBackground(PURCHASE_BUTTON_COLOR);
		btnPurchase.setForeground(Color.WHITE);
		btnPurchase.setFont(new Font("Arial", Font.BOLD, 30));
		btnPurchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPurchase.setFocusable(false);
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validCalc) {
					calcList[0] = tfCalcTotal.getText();
					calcList[1] = tfCalcPayment.getText();
					calcList[2] = tfCalcChange.getText();
					dispose();
					new ReceiptFrame(productInfoList, calcList);
					return;
				}
				if(tfCalcTotal.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please insert the product", "Invalid Total", JOptionPane.WARNING_MESSAGE);
				}else if(tfCalcPayment.getText().equals("")) {
					tfCalcPayment.setBorder(BorderFactory.createLineBorder(Color.RED));
					JOptionPane.showMessageDialog(null, "Please enter the payment", "ERROR", JOptionPane.WARNING_MESSAGE);
				}else {
					boolean validPayment = validatePayment(tfCalcPayment.getText());
					if(validPayment) {
						boolean enoughPayment = false;
						enoughPayment = Double.parseDouble(tfCalcPayment.getText()) >= Double.parseDouble(tfCalcTotal.getText().substring(3));
						if(!enoughPayment) {
							tfCalcPayment.setBorder(BorderFactory.createLineBorder(Color.RED));
							JOptionPane.showMessageDialog(null, "Payment not enough", "Not enough", JOptionPane.WARNING_MESSAGE);
							return;
						}
						tfCalcPayment.setText("RM " + String.format("%.2f", Double.parseDouble(tfCalcPayment.getText())));
						calculateChange();
						calcList[0] = tfCalcTotal.getText();
						calcList[1] = tfCalcPayment.getText();
						calcList[2] = tfCalcChange.getText();
						dispose();
						new ReceiptFrame(productInfoList, calcList);
					}else {
						tfCalcPayment.setBorder(BorderFactory.createLineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Invalid Payment", "Invalid Input", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		panHeader = Header.setHeader(this, "Purchase");
		panFooter = Footer.setFooter(this);
		
		panContent.add(lblPageTitle);
		panContent.add(panSearchBar);
		panContent.add(panSearchResult);
		panContent.add(cbProductList);
		panContent.add(lblBarcodeScannerIcon);
		panContent.add(panTitleRow);
		panContent.add(panProduct);
		panContent.add(panCalc);
		panContent.add(btnPurchase);
		
		add(panHeader);
		add(panContent);
		add(panFooter);

		setVisible(true);
	}
	
	public void addProduct(JScrollPane spProduct, String productBarcode) {
		
		final JPanel panProductView = (JPanel)spProduct.getViewport().getView();
		
		int productViewHeight;
		
		if(numberOfProductAdded == 7) {
			productViewHeight = 320;
		}else if(numberOfProductAdded > 6) {
			productViewHeight = (numberOfProductAdded + 1) * 40;
		}else {
			productViewHeight = 0;
		}
		
		panProductView.setPreferredSize(new Dimension(1340, productViewHeight));
		panProductView.setBackground(Color.WHITE);
		panProductView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT product_name, product_price FROM product "
					   + "WHERE product_barcode = \"" + productBarcode + "\";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			
			if(rs != null) {
				while(rs.next()) {
					panProductRow = new JPanel();
					panProductRow.setLayout(null);
					panProductRow.setPreferredSize(new Dimension(1340, 40));
					panProductRow.setBackground(Color.WHITE);
					
					lblProductNo = new JLabel(++numberOfProductAdded + ".", SwingConstants.CENTER);
					lblProductNo.setBounds(0, 0, 50, 40);
					lblProductNo.setFont(productFont);
					lblProductNo.setBackground(PRODUCT_BACKGROUND_COLOR);
					lblProductNo.setOpaque(true);
					lblProductNo.setForeground(Color.BLACK);
					lblProductNo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
					
					panName = new JPanel();
					panName.setBounds(50, 0, 690, 40);
					panName.setLayout(null);
					panName.setBackground(null);
					panName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
					
					final JLabel lblProductName = new JLabel(rs.getString("product_name"));
					lblProductName.setBounds(10, 0, 650, 40);
					lblProductName.setFont(productFont);
					lblProductName.setForeground(Color.BLACK);
					
					final JLabel lblProductQuantity = new JLabel("1", SwingConstants.CENTER);
					lblProductQuantity.setBounds(60, 0, 80, 40);
					lblProductQuantity.setFont(productFont);
					lblProductQuantity.setForeground(Color.BLACK);
					
					lblDustbinIcon = new JLabel(dustbinIcon);
					lblDustbinIcon.setBounds(650, 10, 30, 20);
					lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDustbinIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
						
						}
						
						public void mousePressed(MouseEvent e) {
							int result = JOptionPane.showConfirmDialog(null, "Do you want to remove this product?\n" + lblProductName.getText(), "REMOVE PRODUCT", JOptionPane.DEFAULT_OPTION);
							if(result == JOptionPane.YES_OPTION) {
								updateProductInfo(lblProductName.getText(), "!");
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
					
					panName.add(lblProductName);
					panName.add(lblDustbinIcon);
					
					final double productPrice = rs.getDouble("product_price");
					
					final JLabel lblProductPrice = new JLabel(String.format("RM " + "%.2f", productPrice), SwingConstants.CENTER);
					lblProductPrice.setBounds(740, 0, 200, 40);
					lblProductPrice.setFont(productFont);
					lblProductPrice.setBackground(PRODUCT_BACKGROUND_COLOR);
					lblProductPrice.setOpaque(true);
					lblProductPrice.setForeground(Color.BLACK);
					lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
										
					double productTotal = rs.getDouble("product_price");
					
					final JLabel lblProductTotal = new JLabel("RM " + String.format("%.2f", rs.getDouble("product_price")), SwingConstants.CENTER);
					lblProductTotal.setBounds(1140, 0, 200, 40);
					lblProductTotal.setFont(productFont);
					lblProductTotal.setBackground(PRODUCT_BACKGROUND_COLOR);
					lblProductTotal.setOpaque(true);
					lblProductTotal.setForeground(Color.BLACK);
					lblProductTotal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));

					panQuantity = new JPanel();
					panQuantity.setBounds(940, 0, 200, 40);
					panQuantity.setLayout(null);
					panQuantity.setBackground(null);
					panQuantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
					
					lblDecreaseIcon = new JLabel(decreaseIcon);
					lblDecreaseIcon.setBounds(40, 10, 20, 20);
					lblDecreaseIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDecreaseIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							if(lblProductQuantity.getText().equals("1")) {
								JOptionPane.showMessageDialog(null, "Product quantity cannot less than 1", "", JOptionPane.WARNING_MESSAGE);
							}else {
								int productQuantity = Integer.parseInt(lblProductQuantity.getText());
								lblProductQuantity.setText(String.valueOf(productQuantity - 1));
								calculateTotalPrice(lblProductTotal, productPrice, lblProductQuantity, "-");
								updateProductInfo(lblProductName.getText(), "-");
							}
						}
						
						public void mouseExited(MouseEvent e) {
							
						}
						
						public void mouseEntered(MouseEvent e) {
							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					lblIncreaseIcon = new JLabel(increaseIcon);
					lblIncreaseIcon.setBounds(140, 10, 20, 20);
					lblIncreaseIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblIncreaseIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							int productQuantity = Integer.parseInt(lblProductQuantity.getText());
							lblProductQuantity.setText(String.valueOf(productQuantity + 1));
							calculateTotalPrice(lblProductTotal, productPrice, lblProductQuantity, "+");
							updateProductInfo(lblProductName.getText(), "+");
						}
						
						public void mouseExited(MouseEvent e) {
							
						}
						
						public void mouseEntered(MouseEvent e) {
							
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panQuantity.add(lblDecreaseIcon);
					panQuantity.add(lblProductQuantity);
					panQuantity.add(lblIncreaseIcon);
					
					panProductRow.add(lblProductNo);
					panProductRow.add(panName);	
					panProductRow.add(lblProductPrice);
					panProductRow.add(panQuantity);
					panProductRow.add(lblProductTotal);
					
					panProductView.add(panProductRow);
					revalidate();
					repaint();					
					
					calculateSum(productPrice, productTotal, 1);
					productInfoList.add(new ProductInfo(lblProductName.getText(), Integer.parseInt(lblProductQuantity.getText()), productTotal, productPrice));					
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
					tfSearchResult.setBackground(null);
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
							addProduct(spProduct, productBarcode);
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
							addProduct(spProduct, productBarcode);
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
	
	public void calculateTotalPrice(JLabel lblProductTotal, double productPrice, JLabel lblProductQuantity, String operator) {
		int productQuantity = Integer.parseInt(lblProductQuantity.getText());
		double total = productQuantity * productPrice;
		lblProductTotal.setText("RM " + String.format("%.2f", total));
		if(operator.equals("+")) {
			increaseSum(productPrice, total, productQuantity);
		}else {
			decreaseSum(productPrice);
		}
	}
	
	public void increaseSum(double productPrice, double productTotalPrice, int productQuantity) {
		sum = sum - (productPrice * (productQuantity - 1)) + productTotalPrice;
		tfCalcTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void decreaseSum(double productPrice) {
		sum = sum - productPrice;
		tfCalcTotal.setText("RM " + String.format("%.2f", sum));
	}
	
	public void calculateSum(double productPrice, double productTotalPrice, int productQuantity) {
		sum = sum - (productPrice * (productQuantity - 1)) + productTotalPrice;
		tfCalcTotal.setText("RM " + String.format("%.2f", sum));
	}

	public boolean validatePayment(String payment) {
		
		boolean validPayment1 = Pattern.matches("[0-9]*[.][0-9]{2}", payment);
		boolean validPayment2 = Pattern.matches("[0-9]*", payment);
		boolean validPayment3 = Pattern.matches("[0-9]*[.][0-9]{1}", payment);
		boolean validPayment4 = Pattern.matches("[0-9]*[.]", payment);
		
		if(!validPayment1 && !validPayment2 && !validPayment3 && !validPayment4) {
			return false;
		}else {
			return true;
		}
	}
	
	public void calculateChange() {
		double total = Double.parseDouble(tfCalcTotal.getText().substring(3));
		double payment = Double.parseDouble(tfCalcPayment.getText().substring(3));
		double change = payment - total;
		tfCalcChange.setText("RM " + String.format("%.2f", change));
	}
	
	public void updateProductInfo(String productName, String operator) {
		for(int i = 0; i < productInfoList.size(); i++) {
			if(productName.equals(productInfoList.get(i).getProductName())) {
				if(operator.equals("+")) {
					productInfoList.get(i).increaseProductQuantity();
				}else if(operator.equals("-")){
					productInfoList.get(i).decreaseProductQuantity();
				}else if(operator.equals("!")) {
					productInfoList.remove(i);
					barcodeList.remove(i);
				}
				break;
			}
		}
	}
	
	public void updateProductScrollPane(final JPanel panProductView) {
		numberOfProductAdded = 0;
		panProductView.removeAll();
		for(int i = 0; i < productInfoList.size(); i++) {
			panProductRow = new JPanel();
			panProductRow.setLayout(null);
			panProductRow.setPreferredSize(new Dimension(1340, 40));
			panProductRow.setBackground(Color.WHITE);
			
			lblProductNo = new JLabel(++numberOfProductAdded + ".", SwingConstants.CENTER);
			lblProductNo.setBounds(0, 0, 50, 40);
			lblProductNo.setFont(productFont);
			lblProductNo.setBackground(PRODUCT_BACKGROUND_COLOR);
			lblProductNo.setOpaque(true);
			lblProductNo.setForeground(Color.BLACK);
			lblProductNo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
			
			panName = new JPanel();
			panName.setBounds(50, 0, 690, 40);
			panName.setLayout(null);
			panName.setBackground(null);
			panName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
			
			final JLabel lblProductName = new JLabel(productInfoList.get(i).getProductName());
			lblProductName.setBounds(10, 0, 650, 40);
			lblProductName.setFont(productFont);
			lblProductName.setForeground(Color.BLACK);
			
			final JLabel lblProductQuantity = new JLabel(String.valueOf(productInfoList.get(i).getProductQuantity()), SwingConstants.CENTER);
			lblProductQuantity.setBounds(60, 0, 80, 40);
			lblProductQuantity.setFont(productFont);
			lblProductQuantity.setForeground(Color.BLACK);
			
			lblDustbinIcon = new JLabel(dustbinIcon);
			lblDustbinIcon.setBounds(650, 10, 30, 20);
			lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDustbinIcon.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
				
				}
				
				public void mousePressed(MouseEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to remove this product?\n" + lblProductName.getText(), "REMOVE PRODUCT", JOptionPane.DEFAULT_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						updateProductInfo(lblProductName.getText(), "!");
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
			
			panName.add(lblProductName);
			panName.add(lblDustbinIcon);
			
			final double productPrice = productInfoList.get(i).getProductPrice();
			final double productTotal = productInfoList.get(i).getProductTotal();
			
			final JLabel lblProductPrice = new JLabel(String.format("RM " + "%.2f", productPrice), SwingConstants.CENTER);
			lblProductPrice.setBounds(740, 0, 200, 40);
			lblProductPrice.setFont(productFont);
			lblProductPrice.setBackground(PRODUCT_BACKGROUND_COLOR);
			lblProductPrice.setOpaque(true);
			lblProductPrice.setForeground(Color.BLACK);
			lblProductPrice.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
								
			final JLabel lblProductTotal = new JLabel("RM " + String.format("%.2f", productTotal), SwingConstants.CENTER);
			lblProductTotal.setBounds(1140, 0, 200, 40);
			lblProductTotal.setFont(productFont);
			lblProductTotal.setBackground(PRODUCT_BACKGROUND_COLOR);
			lblProductTotal.setOpaque(true);
			lblProductTotal.setForeground(Color.BLACK);
			lblProductTotal.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));

			panQuantity = new JPanel();
			panQuantity.setBounds(940, 0, 200, 40);
			panQuantity.setLayout(null);
			panQuantity.setBackground(null);
			panQuantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TITLE_ROW_BACKGROUND_COLOR));
			
			lblDecreaseIcon = new JLabel(decreaseIcon);
			lblDecreaseIcon.setBounds(40, 10, 20, 20);
			lblDecreaseIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblDecreaseIcon.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					if(lblProductQuantity.getText().equals("1")) {
						JOptionPane.showMessageDialog(null, "Product quantity cannot less than 1", "", JOptionPane.WARNING_MESSAGE);
					}else {
						int productQuantity = Integer.parseInt(lblProductQuantity.getText());
						lblProductQuantity.setText(String.valueOf(productQuantity - 1));
						calculateTotalPrice(lblProductTotal, productPrice, lblProductQuantity, "-");
						updateProductInfo(lblProductName.getText(), "-");
					}
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			lblIncreaseIcon = new JLabel(increaseIcon);
			lblIncreaseIcon.setBounds(140, 10, 20, 20);
			lblIncreaseIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblIncreaseIcon.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					int productQuantity = Integer.parseInt(lblProductQuantity.getText());
					lblProductQuantity.setText(String.valueOf(productQuantity + 1));
					calculateTotalPrice(lblProductTotal, productPrice, lblProductQuantity, "+");
					updateProductInfo(lblProductName.getText(), "+");
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			panQuantity.add(lblDecreaseIcon);
			panQuantity.add(lblProductQuantity);
			panQuantity.add(lblIncreaseIcon);
			
			panProductRow.add(lblProductNo);
			panProductRow.add(panName);	
			panProductRow.add(lblProductPrice);
			panProductRow.add(panQuantity);
			panProductRow.add(lblProductTotal);
			
			panProductView.add(panProductRow);
			revalidate();
			repaint();					
			
			updateSum(productTotal);
		}
	}
	
	public void updateSum(double productTotal) {
		sum += productTotal;
		tfCalcTotal.setText("RM " + String.format("%.2f", sum));
	}
}