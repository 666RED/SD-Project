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
import java.sql.*;

import javax.swing.*;
import com.toedter.calendar.*;

public class DailyReportFrame extends JFrame{
	
	JPanel panHeader, panFooter, panTerm, panContent, panTitleRow, panData, panTotal;
	JLabel lblPageName, lblMonth, lblYear, lblGraphIcon, lblDailyNav, lblWeeklyNav, lblMonthlyNav;
	JLabel lblTitleNo, lblTitleDate, lblTitleCost, lblTitlePrice, lblTitleProfit;
	JLabel lblBlank, lblTotal, lblTotalCost, lblTotalPrice, lblTotalProfit;
	JButton btnSearch;
	JYearChooser yearChooser;
	JMonthChooser monthChooser;
	
	ImageIcon graphIcon = IconClass.createIcon("Image\\graph.png", 20, 20);
	JScrollPane spData;
	
	final Color TITLE_ROW_BACKGROUND_COLOR = new Color(179, 179, 179);
	final Color NO_BACKGROUND_COLOR = new Color(205, 205, 205);
	final Color BORDER_COLOR = new Color(168, 168, 168);
	final Color PAN_TERM_COLOR = new Color(168, 168, 168);
	final Color ROW_HOVER_COLOR = new Color(210, 210, 210);
	final Color NAV_HOVER_COLOR = new Color(190, 190, 190);
	final Color TITLE_BORDER_COLOR = new Color(140, 140, 140);

	Font titleFont = new Font("Arial", Font.BOLD, 22);
	Font regularText = new Font("Arial", Font.PLAIN, 20);
	Font selectedText = new Font("Arial", Font.BOLD, 20);
	Font totalRowText = new Font("Arial", Font.BOLD, 25);
	
	double totalCost = 0;
	double totalPrice = 0;
	double totalProfit = 0;
	
	final int MAX_NUM_OF_ROW = 10;
	
	DailyReportFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setTitle("Daily Report");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		spData = new JScrollPane(new JPanel());
		spData.setBounds(0, 0, 1340, 430);
		spData.setHorizontalScrollBar(null);
		spData.getVerticalScrollBar().setUnitIncrement(16);

		lblPageName = new JLabel("Report");
		lblPageName.setBounds(10, 50, 150, 30);
		lblPageName.setFont(new Font("Arial", Font.BOLD, 30));
		lblPageName.setForeground(Color.BLACK);
		
		lblYear = new JLabel("Year: ");
		lblYear.setBounds(180, 50, 60, 30);
		lblYear.setFont(regularText);
		lblYear.setForeground(Color.BLACK);
		
		yearChooser = new JYearChooser();
		yearChooser.setBounds(240, 50, 80, 30);
		yearChooser.setFont(regularText);
		yearChooser.setForeground(Color.BLACK);
		
		lblMonth = new JLabel("Month: ");
		lblMonth.setBounds(340, 50, 70, 30);
		lblMonth.setFont(regularText);
		lblMonth.setForeground(Color.BLACK);
		
		monthChooser = new JMonthChooser();
		monthChooser.setBounds(410, 50, 120, 30);
		monthChooser.setFont(regularText);
		monthChooser.setForeground(Color.BLACK);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(540, 50, 100, 30);
		btnSearch.setFont(regularText);
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setFocusable(false);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalCost = 0;
				totalPrice = 0;
				totalProfit = 0;
				retrieveData(spData);
				updateTotalRow();
				revalidate();
				repaint();
			}
		});
		
		panTerm = new JPanel();
		panTerm.setBackground(PAN_TERM_COLOR);
		panTerm.setBounds(885, 50, 330, 32);
		panTerm.setLayout(null);
		
		lblDailyNav = new JLabel("Daily", SwingConstants.CENTER);
		lblDailyNav.setBounds(0, 0, 110, 32);
		lblDailyNav.setFont(selectedText);
		lblDailyNav.setForeground(Color.BLACK);
		
		lblWeeklyNav = new JLabel("Weekly", SwingConstants.CENTER);
		lblWeeklyNav.setBounds(110, 0, 110, 32);
		lblWeeklyNav.setFont(regularText);
		lblWeeklyNav.setForeground(Color.BLACK);
		lblWeeklyNav.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblWeeklyNav.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new WeeklyReportFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				lblWeeklyNav.setOpaque(false);
				lblWeeklyNav.setBackground(null);
			}
			
			public void mouseEntered(MouseEvent e) {
				lblWeeklyNav.setOpaque(true);
				lblWeeklyNav.setBackground(NAV_HOVER_COLOR);
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		lblMonthlyNav = new JLabel("Monthly", SwingConstants.CENTER);
		lblMonthlyNav.setBounds(220, 0, 110, 32);
		lblMonthlyNav.setFont(regularText);
		lblMonthlyNav.setForeground(Color.BLACK);
		lblMonthlyNav.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMonthlyNav.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				dispose();
				new MonthlyReportFrame();
			}
			
			public void mouseExited(MouseEvent e) {
				lblMonthlyNav.setOpaque(false);
				lblMonthlyNav.setBackground(null);
			}
			
			public void mouseEntered(MouseEvent e) {
				lblMonthlyNav.setOpaque(true);
				lblMonthlyNav.setBackground(NAV_HOVER_COLOR);
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		panTerm.add(lblDailyNav);
		panTerm.add(lblWeeklyNav);
		panTerm.add(lblMonthlyNav);
		
		panContent = new JPanel();
		panContent.setLayout(null);
		panContent.setBounds(10, 100, 1340, 520);
		
		setTitleRow();
		
		panData = new JPanel();
		panData.setBounds(0, 40, 1340, 430);
		panData.setLayout(null);
		
		retrieveData(spData);
		
		panData.add(spData);
		
		setTotalRow();
		
		panContent.add(panTitleRow);
		panContent.add(panData);
		panContent.add(panTotal);
		
		panHeader = Header.setHeader(this, "Report");
		panFooter = Footer.setFooter(this);
		
		add(panHeader);
		add(panFooter);
		add(lblPageName);
		add(lblYear);
		add(yearChooser);
		add(lblMonth);
		add(monthChooser);
		add(btnSearch);
		add(panTerm);
		add(panContent);
		
		setVisible(true);

	}
	
	public void setTitleRow() {
		panTitleRow = new JPanel();
		panTitleRow.setLayout(null);
		panTitleRow.setBounds(0, 0, 1340, 40);
		panTitleRow.setBackground(TITLE_ROW_BACKGROUND_COLOR);
		
		lblTitleNo = new JLabel("No.", SwingConstants.CENTER);
		lblTitleNo.setBounds(0, 0, 101, 40);
		lblTitleNo.setFont(titleFont);
		lblTitleNo.setForeground(Color.BLACK);
		
		lblTitleDate = new JLabel("   Date");
		lblTitleDate.setBounds(101, 0, 610, 40);
		lblTitleDate.setFont(titleFont);
		lblTitleDate.setForeground(Color.BLACK);
		lblTitleDate.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleCost = new JLabel("Cost", SwingConstants.CENTER);
		lblTitleCost.setBounds(711, 0, 210, 40);
		lblTitleCost.setFont(titleFont);
		lblTitleCost.setForeground(Color.BLACK);
		
		lblTitlePrice = new JLabel("Price", SwingConstants.CENTER);
		lblTitlePrice.setBounds(921, 0, 210, 40);
		lblTitlePrice.setFont(titleFont);
		lblTitlePrice.setForeground(Color.BLACK);
		lblTitlePrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, TITLE_BORDER_COLOR));
		
		lblTitleProfit = new JLabel("Profit", SwingConstants.CENTER);
		lblTitleProfit.setBounds(1131, 0, 209, 40);
		lblTitleProfit.setFont(titleFont);
		lblTitleProfit.setForeground(Color.BLACK);
		
		panTitleRow.add(lblTitleNo);
		panTitleRow.add(lblTitleDate);
		panTitleRow.add(lblTitleCost);
		panTitleRow.add(lblTitlePrice);
		panTitleRow.add(lblTitleProfit);
	}
	
	public void retrieveData(JScrollPane spData) {
		JPanel panDataView = (JPanel)spData.getViewport().getView();
		panDataView.removeAll();
		
		panDataView.setPreferredSize(new Dimension(1340, 430));
		panDataView.setBackground(Color.WHITE);
		panDataView.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		String month = "";
		
		if(monthChooser.getMonth() + 1 < 10) {
			month = "0" + (monthChooser.getMonth() + 1);
		}
		
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/kms_sales_system_db", "root", "");
			
			String sql = "SELECT r.receipt_date AS 'DATE', SUM(r.receipt_total) AS 'TOTAL PRICE', SUM(r.total_cost) AS 'TOTAL COST' "
					   + "FROM ("
					         + "SELECT r.receipt_id, r.receipt_date, r.receipt_total, SUM(pc.purchased_product_quantity * p.product_cost) AS total_cost "
					         + "FROM receipt r "
					         + "INNER JOIN product_receipt pc ON r.receipt_id = pc.receipt_id "
					         + "INNER JOIN product p ON pc.product_barcode = p.product_barcode "
					         + "WHERE r.receipt_date LIKE '" + yearChooser.getYear() + "-" + month + "-__' " 
					         + "GROUP BY r.receipt_id"
			           + ") r "
			           + "GROUP BY r.receipt_date "
			           + "ORDER BY r.receipt_date;";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs != null) {
				int count = 0;
				while(rs.next()) {
					final JPanel panDataRow;
					JLabel lblNo;
					final JLabel lblDate;
					JLabel lblCost, lblPrice, lblProfit;
					
					Date date = rs.getDate("DATE");
					double cost = rs.getDouble("TOTAL COST");
					double price = rs.getDouble("TOTAL PRICE");
					double profit = price - cost;
					
					totalCost += cost;
					totalPrice += price;
					totalProfit += profit;
					
					String no = String.valueOf(++count);
					final String strDate = String.valueOf(date);
					String strCost = "RM " + String.format("%.2f", cost);
					String strPrice = "RM " + String.format("%.2f", price);
					String strProfit = "RM " + String.format("%.2f", profit);
					
					panDataRow = new JPanel();
					panDataRow.setLayout(null);
					panDataRow.setPreferredSize(new Dimension(1340, 43));
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
					lblNo.setBounds(0, 0, 100, 43);
					lblNo.setFont(regularText);
					lblNo.setBackground(NO_BACKGROUND_COLOR);
					lblNo.setOpaque(true);
					lblNo.setForeground(Color.BLACK);
					
					lblDate = new JLabel("  " + strDate);
					lblDate.setBounds(100, 0, 610, 43);
					lblDate.setFont(regularText);
					lblDate.setForeground(Color.BLACK);
					lblDate.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
					lblDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lblDate.addMouseListener(new MouseListener() {
						
						public void mouseReleased(MouseEvent e) {
							
						}
						
						public void mousePressed(MouseEvent e) {
							dispose();
							new DailySalesFrame(strDate);
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
					
					lblCost = new JLabel(strCost, SwingConstants.CENTER);
					lblCost.setBounds(710, 0, 210, 43);
					lblCost.setFont(regularText);
					lblCost.setForeground(Color.BLACK);
					
					lblPrice = new JLabel(strPrice, SwingConstants.CENTER);
					lblPrice.setBounds(920, 0, 210, 43);
					lblPrice.setFont(regularText);
					lblPrice.setForeground(Color.BLACK);
					lblPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
					
					lblProfit = new JLabel(strProfit, SwingConstants.CENTER);
					lblProfit.setBounds(1130, 0, 210, 43);
					lblProfit.setFont(regularText);
					lblProfit.setForeground(Color.BLACK);
					
					panDataRow.add(lblNo);
					panDataRow.add(lblDate);
					panDataRow.add(lblCost);
					panDataRow.add(lblPrice);
					panDataRow.add(lblProfit);
					
					panDataView.add(panDataRow);
					panDataView.setPreferredSize(new Dimension(1340, count * 43));
				}
				
				if(count < MAX_NUM_OF_ROW) {
					for(int i = 0; i < MAX_NUM_OF_ROW - count; i++) {
						final JPanel panDataRow;
						JLabel lblNo;
						final JLabel lblDate;
						JLabel lblCost, lblPrice, lblProfit;
						
						panDataRow = new JPanel();
						panDataRow.setLayout(null);
						panDataRow.setPreferredSize(new Dimension(1340, 43));
						panDataRow.setBackground(null);
						
						lblNo = new JLabel();
						lblNo.setBounds(0, 0, 100, 43);
						lblNo.setFont(regularText);
						lblNo.setBackground(NO_BACKGROUND_COLOR);
						lblNo.setOpaque(true);
						lblNo.setForeground(Color.BLACK);
						
						lblDate = new JLabel();
						lblDate.setBounds(100, 0, 610, 43);
						lblDate.setFont(regularText);
						lblDate.setForeground(Color.BLACK);
						lblDate.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
						lblDate.addMouseListener(new MouseListener() {
							
							public void mouseReleased(MouseEvent e) {
								
							}
							
							public void mousePressed(MouseEvent e) {
							}
							
							public void mouseExited(MouseEvent e) {
								
							}
							
							public void mouseEntered(MouseEvent e) {
								panDataRow.setBackground(null);
							}
							
							public void mouseClicked(MouseEvent e) {
								
							}
						});
						
						lblCost = new JLabel();
						lblCost.setBounds(710, 0, 210, 43);
						lblCost.setFont(regularText);
						lblCost.setForeground(Color.BLACK);
						
						lblPrice = new JLabel();
						lblPrice.setBounds(920, 0, 210, 43);
						lblPrice.setFont(regularText);
						lblPrice.setForeground(Color.BLACK);
						lblPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
						
						lblProfit = new JLabel();
						lblProfit.setBounds(1130, 0, 210, 43);
						lblProfit.setFont(regularText);
						lblProfit.setForeground(Color.BLACK);
						
						panDataRow.add(lblNo);
						panDataRow.add(lblDate);
						panDataRow.add(lblCost);
						panDataRow.add(lblPrice);
						panDataRow.add(lblProfit);
						
						panDataView.add(panDataRow);
						panDataView.setPreferredSize(new Dimension(1340, count * 43));
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
		
		panTotal = new JPanel();
		panTotal.setLayout(null);
		panTotal.setBounds(0, 470, 1340, 50);
		panTotal.setBackground(Color.WHITE);
		panTotal.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, BORDER_COLOR));
		
		lblBlank = new JLabel();
		lblBlank.setBounds(0, 0, 101, 50);
		lblBlank.setBackground(NO_BACKGROUND_COLOR);
		lblBlank.setOpaque(true);
		lblBlank.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, BORDER_COLOR));
		
		lblTotal = new JLabel("Total  ", SwingConstants.RIGHT);
		lblTotal.setBounds(101, 0, 610, 50);
		lblTotal.setFont(totalRowText);
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
		
		lblTotalCost = new JLabel(strTotalCost, SwingConstants.CENTER);
		lblTotalCost.setBounds(711, 0, 210, 50);
		lblTotalCost.setFont(totalRowText);
		lblTotalCost.setForeground(Color.BLACK);
		
		lblTotalPrice = new JLabel(strTotalPrice, SwingConstants.CENTER);
		lblTotalPrice.setBounds(921, 0, 210, 50);
		lblTotalPrice.setFont(totalRowText);
		lblTotalPrice.setForeground(Color.BLACK);
		lblTotalPrice.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, BORDER_COLOR));
		
		lblTotalProfit = new JLabel(strTotalProfit, SwingConstants.CENTER);
		lblTotalProfit.setBounds(1131, 0, 209, 50);
		lblTotalProfit.setFont(totalRowText);
		lblTotalProfit.setForeground(Color.BLACK);
		
		panTotal.add(lblBlank);
		panTotal.add(lblTotal);
		panTotal.add(lblTotalCost);
		panTotal.add(lblTotalPrice);
		panTotal.add(lblTotalProfit);
	}
	
	public void updateTotalRow() {
		
		String strTotalCost = "RM " + String.format("%.2f", totalCost);
		String strTotalPrice = "RM " + String.format("%.2f", totalPrice);
		String strTotalProfit = "RM " + String.format("%.2f", totalProfit);
		
		lblTotalCost.setText(strTotalCost);
		lblTotalPrice.setText(strTotalPrice);
		lblTotalProfit.setText(strTotalProfit);
	}
}
