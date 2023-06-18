package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ViewSupplierFrame extends JFrame{

	JPanel panHeader, panFooter, panContent, panSearchBar, panAddNewSupplier, panSupplier, panSearchResult, panTitleRow, panOverallSupplier;
	JLabel lblBackIcon, lblAddNewSupplier, lblPageName, lblAddIcon, lblSearchIcon;
	JTextField tfSearchBar;
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	ImageIcon searchIcon = IconClass.createIcon("Image\\loupe.png", 20, 20);
	ImageIcon addIcon = IconClass.createIcon("Image\\plus2.png", 22, 22);
	ImageIcon dustbinIcon = IconClass.createIcon("Image\\dustbin.png", 20, 20);
	ImageIcon deletedIcon = IconClass.createIcon("Image\\verified.png", 30, 30);
	JScrollPane spSupplier, spSearch;
	JLabel lblTitleNo, lblTitleName, lblTitlePhoneNumber;

	final Color ADD_NEW_SUPPLIER_COLOR = new Color(179, 179, 179);
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color SUPPLIER_NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color SUPPLIER_BORDER_COLOR = new Color(168, 168, 168);
	final Color SUPPLIER_ROW_HOVER_COLOR = new Color(210, 210, 210);
	Font titleFont = new Font("Arial", Font.BOLD, 20);
	
	final int MAX_NUM_OF_ROW = 10;
	
	public ViewSupplierFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("View Supplier");
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
		panContent.setBounds(15, 90, 1340, 530);
		panContent.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		panContent.setLayout(null);
		
		panAddNewSupplier = new JPanel();
		panAddNewSupplier.setBounds(0, 10, 190, 35);
		panAddNewSupplier.setBackground(ADD_NEW_SUPPLIER_COLOR);
		panAddNewSupplier.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panAddNewSupplier.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				dispose();
				new AddNewSupplierFrame();
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
		
		lblAddNewSupplier = new JLabel("Add New Supplier");
		lblAddNewSupplier.setBounds(30, 5, 50, 18);
		lblAddNewSupplier.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAddNewSupplier.setForeground(Color.BLACK);
		
		panAddNewSupplier.add(lblAddIcon);
		panAddNewSupplier.add(lblAddNewSupplier);
		
		panSearchBar = new JPanel();
		panSearchBar.setLayout(null);
		panSearchBar.setBounds(220, 10, 350, 35);
		panSearchBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panSearchBar.setBackground(Color.WHITE);
		
		lblSearchIcon = new JLabel(searchIcon);
		lblSearchIcon.setBounds(5, 8, 20, 20);
		
		spSearch = new JScrollPane(new JPanel());
		spSearch.setBounds(0, 0, 370, 200);
		spSearch.getVerticalScrollBar().setUnitIncrement(16);
		
		panSearchResult = new JPanel();
		panSearchResult.setBounds(210, 55, 370, 200);
		panSearchResult.setLayout(null);
		panSearchResult.setVisible(false);
		
		tfSearchBar = new JTextField("");
		tfSearchBar.setBounds(30, 8, 310, 20);
		tfSearchBar.setFont(new Font("Arial", Font.PLAIN, 20));
		tfSearchBar.setBorder(null);
		tfSearchBar.setForeground(Color.BLACK);
		tfSearchBar.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchSupplier(tfSearchBar.getText(), spSearch);
				revalidate();
				repaint();
			}
			
			public void insertUpdate(DocumentEvent e) {
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchSupplier(tfSearchBar.getText(), spSearch);
				revalidate();
				repaint();	
			}
			
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
		
		tfSearchBar.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				if(tfSearchBar.getText().equals("")) {
					tfSearchBar.setText("Supplier Name");
					tfSearchBar.setForeground(Color.GRAY);
				}
				panSearchResult.setVisible(false);
			}
			
			public void focusGained(FocusEvent e) {
				if(tfSearchBar.getText().equals("Supplier Name")) {
					tfSearchBar.setText("");
					tfSearchBar.setForeground(Color.BLACK);
				}
				JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
				panSearchView.removeAll();
				searchSupplier(tfSearchBar.getText(), spSearch);
			}
		});
		
		panSearchResult.add(spSearch);
		
		panSearchBar.add(lblSearchIcon);
		panSearchBar.add(tfSearchBar);
		
		lblPageName = new JLabel("View Supplier");
		lblPageName.setBounds(0, 60, 300, 30);
		lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageName.setForeground(Color.BLACK);
		
		panOverallSupplier = new JPanel();
		panOverallSupplier.setLayout(null);
		panOverallSupplier.setBounds(0, 110, 1340, 420);
		panOverallSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBounds(0, 0, 1340, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);

		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 50, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleName = new JLabel("Supplier Name");
		lblTitleName.setBounds(60, 0, 1000, 40);
		lblTitleName.setFont(titleFont);
		lblTitleName.setForeground(Color.BLACK);
		
		lblTitlePhoneNumber = new JLabel("Phone Number", SwingConstants.CENTER);
		lblTitlePhoneNumber.setBounds(1060, 0, 280, 40);
		lblTitlePhoneNumber.setFont(titleFont);
		lblTitlePhoneNumber.setForeground(Color.BLACK);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleName);
		panTitleRow.add(lblTitlePhoneNumber);
		
		panSupplier = new JPanel();
		panSupplier.setBounds(0, 40, 1340, 380);
		panSupplier.setLayout(null);

		spSupplier = new JScrollPane(new JPanel());
		spSupplier.setBounds(0, 0, 1340, 380);
		spSupplier.setHorizontalScrollBar(null);
		spSupplier.getVerticalScrollBar().setUnitIncrement(16);
		
		retrieveSupplierData(spSupplier);
		
		panSupplier.add(spSupplier);
		
		panOverallSupplier.add(panTitleRow);
		panOverallSupplier.add(panSupplier);
		
		panContent.add(panAddNewSupplier);
		panContent.add(panSearchBar);
		panContent.add(lblPageName);
		panContent.add(panSearchResult);
		panContent.add(panOverallSupplier);
		
		panHeader = Header.setHeader(this, "Inventory");
		panFooter = Footer.setFooter(this);
		
		add(panHeader);
		add(panFooter);
		add(lblBackIcon);
		add(panContent);
		
		setVisible(true);
	}
	
	public void searchSupplier(String supplierName, JScrollPane spSearch) {
		Connection conn = null;
		JPanel panSearchView = (JPanel)spSearch.getViewport().getView();
		panSearchView.setBackground(Color.WHITE);
		panSearchView.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 0));
		final Color SEARCH_RESULT_BACKGROUND_COLOR = new Color(205, 205, 205);
		int count = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT supplier_name, supplier_id FROM supplier "
					   + "WHERE supplier_name LIKE '" + supplierName + "%' AND supplier_id != 1;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				while(rs.next() && !Pattern.matches("\\s*", supplierName)) {
					final int supplierID = rs.getInt("supplier_id");

					final JTextField tfSearchResult = new JTextField(rs.getString("supplier_name"));
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
							new ManageSupplierFrame(supplierID);
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

	public void retrieveSupplierData(JScrollPane spSupplier) {
		JPanel panSupplierView = (JPanel)spSupplier.getViewport().getView();
		String supplierName, phoneNumber;
		int supplierNo;
		
		panSupplierView.setPreferredSize(new Dimension(1340, 430));
		panSupplierView.setBackground(Color.WHITE);
		panSupplierView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		Connection conn = null;
		try {
			Font supplierFont = new Font("Arial", Font.PLAIN, 20);
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT * FROM supplier "
					   + "WHERE supplier_id != 1 "
					   + "ORDER BY supplier_name;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					final int supplierID = rs.getInt("supplier_id");
					JLabel lblSupplierNo;
					final JLabel lblSupplierName;
					JLabel lblDustbinIcon, lblSupplierPhoneNumber;
					final JPanel panSupplierRow;
					JPanel panSupplierName;

					panSupplierRow = new JPanel();
					panSupplierRow.setLayout(null);
					panSupplierRow.setPreferredSize(new Dimension(1340, 38));
					panSupplierRow.setBackground(null);
					panSupplierRow.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							
						}
						
						public void mouseExited(MouseEvent e) {
							panSupplierRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panSupplierRow.setBackground(SUPPLIER_NO_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					supplierNo = ++count;
					supplierName = rs.getString("supplier_name");
					phoneNumber = rs.getString("supplier_phone_number");
					
					lblSupplierNo = new JLabel(supplierNo + ".", SwingConstants.CENTER);
					lblSupplierNo.setBounds(0, 0, 50, 38);
					lblSupplierNo.setFont(supplierFont);
					lblSupplierNo.setBackground(SUPPLIER_NO_BACKGROUND_COLOR);
					lblSupplierNo.setOpaque(true);
					lblSupplierNo.setForeground(Color.BLACK);
					
					panSupplierName = new JPanel();
					panSupplierName.setBounds(50, 0, 1010, 38);
					panSupplierName.setLayout(null);
					panSupplierName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					panSupplierName.setBackground(null);
					panSupplierName.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, SUPPLIER_BORDER_COLOR));
					
					lblSupplierName = new JLabel(supplierName);
					lblSupplierName.setBounds(10, 0, 970, 38);
					lblSupplierName.setFont(supplierFont);
					lblSupplierName.setForeground(Color.BLACK);
					
					lblDustbinIcon = new JLabel(dustbinIcon);
					lblDustbinIcon.setBounds(980, 0, 30, 38);
					lblDustbinIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDustbinIcon.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete this supplier?\n(" + lblSupplierName.getText() + ")", "DELETE SUPPLIER", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							if(answer == JOptionPane.YES_OPTION) {
								JOptionPane.showMessageDialog(null, "Suuplier Deleted Succussfully", "Deleted", JOptionPane.DEFAULT_OPTION, deletedIcon);
								deleteSupplier(supplierID);
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
					
					panSupplierName.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {

						}
						
						public void mousePressed(MouseEvent e) {
							dispose();
							new ManageSupplierFrame(supplierID);
						}
						
						public void mouseExited(MouseEvent e) {
							panSupplierRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panSupplierRow.setBackground(SUPPLIER_ROW_HOVER_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					panSupplierName.add(lblSupplierName);
					panSupplierName.add(lblDustbinIcon);
					
					lblSupplierPhoneNumber = new JLabel(phoneNumber, SwingConstants.CENTER);
					lblSupplierPhoneNumber.setBounds(1060, 0, 280, 38);
					lblSupplierPhoneNumber.setFont(supplierFont);
					lblSupplierPhoneNumber.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, SUPPLIER_BORDER_COLOR));
					lblSupplierPhoneNumber.setForeground(Color.BLACK);
					
					panSupplierRow.add(lblSupplierNo);
					panSupplierRow.add(panSupplierName);	
					panSupplierRow.add(lblSupplierPhoneNumber);
										
					panSupplierView.add(panSupplierRow);
					panSupplierView.setPreferredSize(new Dimension(1340, count * 38));
				}
				
				if(count < MAX_NUM_OF_ROW) {
					for(int i = 0; i < MAX_NUM_OF_ROW - count; i++) {
						JLabel lblSupplierNo;
						final JLabel lblSupplierName;
						JLabel lblDustbinIcon, lblSupplierPhoneNumber;
						final JPanel panSupplierRow;
						JPanel panSupplierName;

						panSupplierRow = new JPanel();
						panSupplierRow.setLayout(null);
						panSupplierRow.setPreferredSize(new Dimension(1340, 38));
						panSupplierRow.setBackground(null);
						
						lblSupplierNo = new JLabel();
						lblSupplierNo.setBounds(0, 0, 50, 38);
						lblSupplierNo.setFont(supplierFont);
						lblSupplierNo.setBackground(SUPPLIER_NO_BACKGROUND_COLOR);
						lblSupplierNo.setOpaque(true);
						lblSupplierNo.setForeground(Color.BLACK);
						
						panSupplierName = new JPanel();
						panSupplierName.setBounds(50, 0, 1010, 38);
						panSupplierName.setLayout(null);
						panSupplierName.setBackground(null);
						panSupplierName.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, SUPPLIER_BORDER_COLOR));
						
						lblSupplierName = new JLabel();
						lblSupplierName.setBounds(10, 0, 970, 38);
						lblSupplierName.setFont(supplierFont);
						lblSupplierName.setForeground(Color.BLACK);
						
						panSupplierName.addMouseListener(new MouseListener() {
							
							public void mouseReleased(MouseEvent e) {

							}
							
							public void mousePressed(MouseEvent e) {
								
							}
							
							public void mouseExited(MouseEvent e) {
								
							}
							
							public void mouseEntered(MouseEvent e) {
								panSupplierRow.setBackground(null);
							}
							
							public void mouseClicked(MouseEvent e) {
								
							}
						});
						
						panSupplierName.add(lblSupplierName);
						
						lblSupplierPhoneNumber = new JLabel();
						lblSupplierPhoneNumber.setBounds(1060, 0, 280, 38);
						lblSupplierPhoneNumber.setFont(supplierFont);
						lblSupplierPhoneNumber.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, SUPPLIER_BORDER_COLOR));
						lblSupplierPhoneNumber.setForeground(Color.BLACK);
						
						panSupplierRow.add(lblSupplierNo);
						panSupplierRow.add(panSupplierName);	
						panSupplierRow.add(lblSupplierPhoneNumber);
											
						panSupplierView.add(panSupplierRow);
						panSupplierView.setPreferredSize(new Dimension(1340, count * 38));
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
	
	public void deleteSupplier(int supplierID) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql1 = "UPDATE product "
					+ "SET supplier_id = 1 "
					+ "WHERE supplier_id = " + supplierID + ";";
			
			String sql2 = "DELETE FROM supplier "
					   + "WHERE supplier_id = " + supplierID + ";";

			// need to add one more for the restock_product_supplier table
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);

			JPanel panSupplierView = (JPanel)spSupplier.getViewport().getView();
			panSupplierView.removeAll();
			retrieveSupplierData(spSupplier);
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
}
