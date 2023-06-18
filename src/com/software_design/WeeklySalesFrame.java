package com.software_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class WeeklySalesFrame extends JFrame{
	
	JPanel panHeader, panFooter, panLower, panContent, panTitleRow, panData, panTotalRow;
	JLabel lblBackIcon, lblPageName, lblDate;
	JLabel lblTitleNo, lblTitleProduct, lblTitleQuantity, lblTitleCost, lblTitlePrice, lblTitleProfit;
	JLabel lblBlank, lblTotal, lblTotalCost, lblTotalPrice, lblTotalProfit;
	JScrollPane spData;

	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color BORDER_COLOR = new Color(168, 168, 168);
	final Color ROW_HOVER_COLOR = new Color(210, 210, 210);
	final Color TITLE_BORDER_COLOR = new Color(140, 140, 140);

	Font titleFont = new Font("Arial", Font.BOLD, 22);
	Font regularText = new Font("Arial", Font.PLAIN, 20);
	Font totalRowText = new Font("Arial", Font.BOLD, 25);

	double totalCost = 0;
	double totalPrice = 0;
	double totalProfit = 0;
	
	ImageIcon backIcon = IconClass.createIcon("Image\\return.png", 30, 30);
	
	final int MAX_NUM_OF_PRODUCT = 10;
	
	private String startDate, endDate;
	
	WeeklySalesFrame(String startDate, String endDate) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Weekly Sales");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setStartDate(startDate);
		setEndDate(endDate);
		
		spData = new JScrollPane(new JPanel());
		spData.setBounds(0, 0, 1340, 370);
		spData.setHorizontalScrollBar(null);
		spData.getVerticalScrollBar().setUnitIncrement(16);
		
		lblBackIcon = new JLabel(backIcon);
		lblBackIcon.setBounds(15, 50, 30, 30);
		lblBackIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackIcon.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				dispose();
				new WeeklyReportFrame();
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
		
		panLower = new JPanel();
		panLower.setBounds(15, 90, 1340, 530);
		panLower.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		panLower.setLayout(null);
		
		lblPageName = new JLabel("Weekly Sales");
		lblPageName.setBounds(0, 10, 200, 40);
		lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageName.setForeground(Color.BLACK);
		
		lblDate = new JLabel("Week: " + startDate + " -> " + endDate);
		lblDate.setBounds(250, 10, 300, 40);
		lblDate.setFont(regularText);
		lblDate.setForeground(Color.BLACK);
		
		panContent = new JPanel();
		panContent.setLayout(null);
		panContent.setBounds(0, 70, 1340, 460);
		panContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setTitleRow();

		panData = new JPanel();
		panData.setBounds(0, 40, 1340, 370);
		panData.setLayout(null);
		
		retrieveData(spData);
		
		panData.add(spData);
		
		setTotalRow();
		
		panContent.add(panTitleRow);
		panContent.add(panData);
		panContent.add(panTotalRow);
		
		panLower.add(lblPageName);
		panLower.add(lblDate);
		panLower.add(panContent);
		
		panHeader = Header.setHeader(this, "Report");
		panFooter = Footer.setFooter(this);
		
		add(panHeader);
		add(panFooter);
		add(lblBackIcon);
		add(panLower);
		
		setVisible(true);
	}
	
	public void setTitleRow() {
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBounds(0, 0, 1340, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		
		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 51, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleProduct = new JLabel("   Product Sold");
		lblTitleProduct.setBounds(51, 0, 570, 40);
		lblTitleProduct.setFont(titleFont);
		lblTitleProduct.setForeground(Color.BLACK);
		lblTitleProduct.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleQuantity = new JLabel("Quantity", SwingConstants.CENTER);
		lblTitleQuantity.setBounds(621, 0, 180, 40);
		lblTitleQuantity.setFont(titleFont);
		lblTitleQuantity.setForeground(Color.BLACK);
		
		lblTitleCost = new JLabel("Cost", SwingConstants.CENTER);
		lblTitleCost.setBounds(801, 0, 180, 40);
		lblTitleCost.setFont(titleFont);
		lblTitleCost.setForeground(Color.BLACK);
		lblTitleCost.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitlePrice = new JLabel("Price", SwingConstants.CENTER);
		lblTitlePrice.setBounds(981, 0, 180, 40);
		lblTitlePrice.setFont(titleFont);
		lblTitlePrice.setForeground(Color.BLACK);
		
		lblTitleProfit = new JLabel("Profit", SwingConstants.CENTER);
		lblTitleProfit.setBounds(1161, 0, 179, 40);
		lblTitleProfit.setFont(titleFont);
		lblTitleProfit.setForeground(Color.BLACK);
		lblTitleProfit.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, TITLE_BORDER_COLOR));
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleProduct);
		panTitleRow.add(lblTitleQuantity);
		panTitleRow.add(lblTitleCost);
		panTitleRow.add(lblTitlePrice);
		panTitleRow.add(lblTitleProfit);
	}

	public void retrieveData(JScrollPane spData) {
		JPanel panDataView = (JPanel)spData.getViewport().getView();
		panDataView.removeAll();
		
		panDataView.setPreferredSize(new Dimension(1340, 370));
		panDataView.setBackground(Color.WHITE);
		panDataView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT p.product_name, SUM(pc.purchased_product_quantity) AS 'TOTAL QUANTITY', SUM(p.product_cost * pc.purchased_product_quantity) AS 'TOTAL COST', SUM(p.product_price * pc.purchased_product_quantity) AS 'TOTAL PRICE' "
					   + "FROM receipt r "
					   + "INNER JOIN product_receipt pc ON r.receipt_id = pc.receipt_id "
					   + "INNER JOIN product p ON pc.product_barcode = p.product_barcode "
					   + "WHERE r.receipt_date BETWEEN '" + startDate + "' AND '" + endDate + "' "
					   + "GROUP BY p.product_name;";
						
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					final JPanel panDataRow;
					JLabel lblNo;
					final JLabel lblProduct;
					JLabel lblQuantity, lblCost, lblPrice, lblProfit;
					
					int quantity = rs.getInt("TOTAL QUANTITY");
					double cost = rs.getDouble("TOTAL COST");
					double price = rs.getDouble("TOTAL PRICE");
					double profit = price - cost;
					
					totalCost += cost;
					totalPrice += price;
					totalProfit += profit;
					
					String no = String.valueOf(++count);
					String productName = rs.getString("product_name");
					String strQuantity = String.valueOf(quantity);
					String strCost = "RM " + String.format("%.2f", cost);
					String strPrice = "RM " + String.format("%.2f", price);
					String strProfit = "RM " + String.format("%.2f", profit);
					
					panDataRow = new JPanel();
					panDataRow.setLayout(null);
					panDataRow.setPreferredSize(new Dimension(1340, 37));
					panDataRow.setBackground(null);
					panDataRow.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							
						}
						
						public void mouseExited(MouseEvent e) {
							panDataRow.setBackground(null);
						}
						
						public void mouseEntered(MouseEvent e) {
							panDataRow.setBackground(NO_BACKGROUND_COLOR);
						}
						
						public void mouseClicked(MouseEvent e) {
							
						}
					});
					
					lblNo = new JLabel(no, SwingConstants.CENTER);
					lblNo.setBounds(0, 0, 50, 37);
					lblNo.setFont(regularText);
					lblNo.setBackground(NO_BACKGROUND_COLOR);
					lblNo.setOpaque(true);
					lblNo.setForeground(Color.BLACK);
					
					lblProduct = new JLabel("  " + productName);
					lblProduct.setBounds(50, 0, 570, 37);
					lblProduct.setFont(regularText);
					lblProduct.setForeground(Color.BLACK);
					lblProduct.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
					
					lblQuantity = new JLabel(strQuantity, SwingConstants.CENTER);
					lblQuantity.setBounds(620, 0, 180, 37);
					lblQuantity.setFont(regularText);
					lblQuantity.setForeground(Color.BLACK);
					
					lblCost = new JLabel(strCost, SwingConstants.CENTER);
					lblCost.setBounds(800, 0, 180, 37);
					lblCost.setFont(regularText);
					lblCost.setForeground(Color.BLACK);
					lblCost.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
					
					lblPrice = new JLabel(strPrice, SwingConstants.CENTER);
					lblPrice.setBounds(980, 0, 180, 37);
					lblPrice.setFont(regularText);
					lblPrice.setForeground(Color.BLACK);
					
					lblProfit = new JLabel(strProfit, SwingConstants.CENTER);
					lblProfit.setBounds(1160, 0, 180, 37);
					lblProfit.setFont(regularText);
					lblProfit.setForeground(Color.BLACK);
					lblProfit.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER_COLOR));

					panDataRow.add(lblNo);
					panDataRow.add(lblProduct);
					panDataRow.add(lblQuantity);
					panDataRow.add(lblCost);
					panDataRow.add(lblPrice);
					panDataRow.add(lblProfit);
					
					panDataView.add(panDataRow);
					panDataView.setPreferredSize(new Dimension(1340, count * 37));
				}
				
				if(count < MAX_NUM_OF_PRODUCT) {
					for(int i = 0; i < MAX_NUM_OF_PRODUCT - count; i++) {
						final JPanel panDataRow;
						JLabel lblNo;
						final JLabel lblProduct;
						JLabel lblQuantity, lblCost, lblPrice, lblProfit;
						
						panDataRow = new JPanel();
						panDataRow.setLayout(null);
						panDataRow.setPreferredSize(new Dimension(1340, 37));
						panDataRow.setBackground(null);

						
						lblNo = new JLabel();
						lblNo.setBounds(0, 0, 50, 37);
						lblNo.setFont(regularText);
						lblNo.setBackground(NO_BACKGROUND_COLOR);
						lblNo.setOpaque(true);
						lblNo.setForeground(Color.BLACK);
						
						lblProduct = new JLabel();
						lblProduct.setBounds(50, 0, 570, 37);
						lblProduct.setFont(regularText);
						lblProduct.setForeground(Color.BLACK);
						lblProduct.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
						
						lblQuantity = new JLabel();
						lblQuantity.setBounds(620, 0, 180, 37);
						lblQuantity.setFont(regularText);
						lblQuantity.setForeground(Color.BLACK);
						
						lblCost = new JLabel();
						lblCost.setBounds(800, 0, 180, 37);
						lblCost.setFont(regularText);
						lblCost.setForeground(Color.BLACK);
						lblCost.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
						
						lblPrice = new JLabel();
						lblPrice.setBounds(980, 0, 180, 37);
						lblPrice.setFont(regularText);
						lblPrice.setForeground(Color.BLACK);
						
						lblProfit = new JLabel();
						lblProfit.setBounds(1160, 0, 180, 37);
						lblProfit.setFont(regularText);
						lblProfit.setForeground(Color.BLACK);
						lblProfit.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER_COLOR));

						panDataRow.add(lblNo);
						panDataRow.add(lblProduct);
						panDataRow.add(lblQuantity);
						panDataRow.add(lblCost);
						panDataRow.add(lblPrice);
						panDataRow.add(lblProfit);
						
						panDataView.add(panDataRow);
						panDataView.setPreferredSize(new Dimension(1340, count * 37));
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
	
	public void setTotalRow() {
		
		String strTotalCost = "RM " + String.format("%.2f", totalCost);
		String strTotalPrice = "RM " + String.format("%.2f", totalPrice);
		String strTotalProfit = "RM " + String.format("%.2f", totalProfit);
		
		panTotalRow = new JPanel();
		panTotalRow.setLayout(null);
		panTotalRow.setBounds(0, 410, 1340, 50);
		panTotalRow.setBackground(Color.WHITE);
		panTotalRow.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, BORDER_COLOR));
		
		lblBlank = new JLabel();
		lblBlank.setBounds(0, 0, 51, 50);
		lblBlank.setBackground(NO_BACKGROUND_COLOR);
		lblBlank.setOpaque(true);
		lblBlank.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, BORDER_COLOR));
		
		lblTotal = new JLabel("Total  ", SwingConstants.RIGHT);
		lblTotal.setBounds(51, 0, 751, 50);
		lblTotal.setFont(totalRowText);
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
		
		lblTotalCost = new JLabel(strTotalCost, SwingConstants.CENTER);
		lblTotalCost.setBounds(802, 0, 178, 50);
		lblTotalCost.setFont(totalRowText);
		lblTotalCost.setForeground(Color.BLACK);
		
		lblTotalPrice = new JLabel(strTotalPrice, SwingConstants.CENTER);
		lblTotalPrice.setBounds(980, 0, 182, 50);
		lblTotalPrice.setFont(totalRowText);
		lblTotalPrice.setForeground(Color.BLACK);
		lblTotalPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
		
		lblTotalProfit = new JLabel(strTotalProfit, SwingConstants.CENTER);
		lblTotalProfit.setBounds(1162, 0, 178, 50);
		lblTotalProfit.setFont(totalRowText);
		lblTotalProfit.setForeground(Color.BLACK);
		
		panTotalRow.add(lblBlank);
		panTotalRow.add(lblTotal);
		panTotalRow.add(lblTotalCost);
		panTotalRow.add(lblTotalPrice);
		panTotalRow.add(lblTotalProfit);
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
