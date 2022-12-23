package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class JDTillCourt extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private Font fJLabelT, fJLabelD, fJLabelIR, fJTableHeaders;
	public JLabel jlbltCashier, jlblCashier, jlbltTurns, jlblTotalSales, jlblEarnings;
	public JDatePickerImpl jdpDate;
	public JComboBox<String> jcbTurns;
	public JButton jbtnCloseAction, jbtnExit;
	public DefaultTableModel dtmTillMoney, dtmSalesByDepartment, dtmCashInflows, dtmCashOutflows;
	public String action = "";
	
	//Constructor
	public JDTillCourt(JFMain jfSales, String title) {
		super(jfSales);
		this.setModal(true);
		this.action = title;
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/court.png"));
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fJLabelT = new Font("Liberation Sans", Font.BOLD, 16);
		fJLabelD = new Font("Liberation Sans", Font.PLAIN, 16);
		fJLabelIR = new Font("Liberation Sans", Font.BOLD, 20);
		fJTableHeaders = new Font("Liberation Sans", Font.BOLD, 16);
		//GUI	
		this.setJPMain();
		this.setJPNorth();
		this.setJPCenter();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel NORTH
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		//JPanel Cashier Info
		JPanel jpCashierInfo = new JPanel();
		jpCashierInfo = this.returnJPCashierInfo();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpCashierInfo, gbc);
		
		//JPanel Turn Info
		JPanel jpTurnInfo = new JPanel();
		jpTurnInfo = this.returnJPTurnInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpTurnInfo, gbc);
		
		//JPanel Actions
		JPanel jpActions = new JPanel();
		jpActions = this.returnJPActions();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpActions, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpNorth, gbc);
	}
	private JPanel returnJPCashierInfo() {
		JPanel returnJPCashierInfo = new JPanel();
		returnJPCashierInfo.setLayout(gbl);
		
		jlbltCashier = new JLabel();
		jlbltCashier.setText("Corte de: ");
		jlbltCashier.setFont(fJLabelT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCashierInfo.add(jlbltCashier, gbc);
		
		jlblCashier = new JLabel();
		jlblCashier.setFont(fJLabelD);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 10);
		returnJPCashierInfo.add(jlblCashier, gbc);
		
		JLabel jlbltFrom = new JLabel();
		jlbltFrom.setText("Del Día: ");
		jlbltFrom.setFont(fJLabelT);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCashierInfo.add(jlbltFrom, gbc);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		jdpDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCashierInfo.add(jdpDate, gbc);
		
		return returnJPCashierInfo;
	}
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		Date actualDate = new Date();
		SimpleDateFormat sdfTurnDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
	    
		@Override
	    public Object stringToValue(String text) throws ParseException {
	    	return sdfTurnDate.parseObject(text);
	    }
	    
	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	        	Calendar selectedDate = (Calendar) value;
	        	return sdfTurnDate.format(selectedDate.getTime());
	        }
	        return sdfTurnDate.format(actualDate);
	    }
	}
	private JPanel returnJPTurnInfo() {
		JPanel returnJPTurnInfo = new JPanel();
		returnJPTurnInfo.setLayout(gbl);
		
		jlbltTurns = new JLabel();
		jlbltTurns.setText("Turno(s:)");
		jlbltTurns.setFont(fJLabelT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTurnInfo.add(jlbltTurns, gbc);
		
		jcbTurns = new JComboBox<String>();
		jcbTurns.setFont(fJLabelD);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTurnInfo.add(jcbTurns, gbc);
		
		return returnJPTurnInfo;
	}
	private JPanel returnJPActions() {
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnCloseAction = new JButton();
		jbtnCloseAction.setFont(fJButtons);
		jbtnCloseAction.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnCloseAction, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(fJButtons);
		jbtnExit.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnExit, gbc);
		
		return returnJPActions;
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		//JPanel 00
		JPanel jp00 = new JPanel();
		jp00 = this.returnJP00();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 5, 0);
		jpCenter.add(jp00, gbc);
		
		//JPanel 10
		JPanel jp10 = new JPanel();
		jp10 = this.returnJP10();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 5, 0);
		jpCenter.add(jp10, gbc);
		
		//JPanel 01
		JPanel jp01 = new JPanel();
		jp01 = this.returnJP01();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jp01, gbc);
		
		//JPanel 11
		JPanel jp11 = new JPanel();
		jp11 = this.returnJP11();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jp11, gbc);
		
		//JPanel 02
		JPanel jp02 = new JPanel();
		jp02 = this.returnJP02();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jp02, gbc);
		
		//JPanel jp12
		JPanel jp12 = new JPanel();
		jp12 = this.returnJP12();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jp12, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCenter, gbc);
	}
	//JP00============================================================================================================
	private JPanel returnJP00() {
		JPanel returnJP00 = new JPanel();
		returnJP00.setLayout(gbl);
		
		JLabel jlbliTotalSales = new JLabel();
		jlbliTotalSales.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/total_sales.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP00.add(jlbliTotalSales, gbc);
		
		JLabel jlbltTotalSales = new JLabel();
		jlbltTotalSales.setText("Ventas Totales");
		jlbltTotalSales.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP00.add(jlbltTotalSales, gbc);
		
		JLabel jlbltCoinTotalSales = new JLabel();
		jlbltCoinTotalSales.setText("$");
		jlbltCoinTotalSales.setFont(fJLabelIR);
		jlbltCoinTotalSales.setForeground(Color.BLUE);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP00.add(jlbltCoinTotalSales, gbc);
		
		jlblTotalSales = new JLabel();
		jlblTotalSales.setText("0.00");
		jlblTotalSales.setFont(fJLabelIR);
		jlblTotalSales.setForeground(Color.BLUE);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP00.add(jlblTotalSales, gbc);
		
		return returnJP00;
	}
	//JP00============================================================================================================
	
	//JP10============================================================================================================
	private JPanel returnJP10() {
		JPanel returnJP10 = new JPanel();
		returnJP10.setLayout(gbl);
		
		JLabel jlbliEarnings = new JLabel();
		jlbliEarnings.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/earnings.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jlbliEarnings, gbc);
		
		JLabel jlbltEarnings = new JLabel();
		jlbltEarnings.setText("Ganancias");
		jlbltEarnings.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jlbltEarnings, gbc);
		
		JLabel jlbltCoinEarnings = new JLabel();
		jlbltCoinEarnings.setText("$");
		jlbltCoinEarnings.setFont(fJLabelIR);
		jlbltCoinEarnings.setForeground(Color.BLUE);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jlbltCoinEarnings, gbc);
		
		jlblEarnings = new JLabel();
		jlblEarnings.setText("0.00");
		jlblEarnings.setFont(fJLabelIR);
		jlblEarnings.setForeground(Color.BLUE);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jlblEarnings, gbc);
		
		return returnJP10;
	}
	//JP10============================================================================================================
	
	//JP01============================================================================================================
	private JPanel returnJP01() {
		JPanel returnJP01 = new JPanel();
		returnJP01.setLayout(gbl);
		
		//JPanel Till Money
		JPanel jpTillMoney = new JPanel();
		jpTillMoney = this.returnJPTillMoney();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP01.add(jpTillMoney, gbc);
		
		 return returnJP01;
	}
	private JPanel returnJPTillMoney() {
		JPanel returnJPTillMoney = new JPanel();
		returnJPTillMoney.setLayout(gbl);
		
		//JPanel Till Money Icon
		JPanel jpIconTillMoney = new JPanel();
		jpIconTillMoney = this.returnJPIconTillMoney();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillMoney.add(jpIconTillMoney, gbc);
		
		//JPanel Till Money Info
		JPanel jpTillMoneyInfo = new JPanel();
		jpTillMoneyInfo = this.returnJPTillMoneyInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillMoney.add(jpTillMoneyInfo, gbc);
		
		return returnJPTillMoney;
	}
	private JPanel returnJPIconTillMoney() {
		JPanel returnJPIconTillMoney = new JPanel();
		returnJPIconTillMoney.setLayout(gbl);
		
		JLabel jlbliTillMoney = new JLabel();
		jlbliTillMoney.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/till.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconTillMoney.add(jlbliTillMoney, gbc);
		
		JLabel jlbltTillMoney = new JLabel();
		jlbltTillMoney.setText("Dinero en Caja        ");
		jlbltTillMoney.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconTillMoney.add(jlbltTillMoney, gbc);
		
		return returnJPIconTillMoney;
	}
	private JPanel returnJPTillMoneyInfo() {
		JPanel returnJPTillMoneyInfo = new JPanel();
		returnJPTillMoneyInfo.setLayout(gbl);
		
		dtmTillMoney = new DefaultTableModel();
		dtmTillMoney.addColumn("Tipo");
		dtmTillMoney.addColumn("$ Cantidad");
		
		JTable jtTillMoney = new JTable() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtTillMoney.setModel(dtmTillMoney);
		jtTillMoney.setRowHeight(22);
		jtTillMoney.setShowGrid(false);
		jtTillMoney.setRowSelectionAllowed(false);
		jtTillMoney.setDefaultRenderer(Object.class, new RendererJTableTillMoney());
		
		JTableHeader jthTotalSales = new JTableHeader();
		jthTotalSales = jtTillMoney.getTableHeader();
		jthTotalSales.setFont(fJTableHeaders);
		
		TableColumn column = null;
		for(int a = 0; a < jtTillMoney.getColumnCount(); a++) {
			column = jtTillMoney.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(170);
			if(a == 1) 
				column.setPreferredWidth(50);
		}
		
		JScrollPane jspJTTotalSales = new JScrollPane(jtTillMoney);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillMoneyInfo.add(jspJTTotalSales, gbc);
		
		return returnJPTillMoneyInfo;
	}
	class RendererJTableTillMoney extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.BOLD, 16));
				
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				String recivedValue = (String) value;
				if(recivedValue.charAt(0) == '+')
					cell.setForeground(Color.GREEN);
				else if(recivedValue.charAt(0) == '-') 
					cell.setForeground(Color.RED);
				else 
					cell.setForeground(Color.BLACK);
				
				if(column == 1 && row == 0)
					cell.setForeground(Color.CYAN);
				if(column == 1 && row == 7) 
					cell.setForeground(Color.BLUE);
			}
			
			return cell;
		}
	}
	//JP01============================================================================================================
	
	//JP11============================================================================================================
	private JPanel returnJP11() {
		JPanel returnJP10 = new JPanel();
		returnJP10.setLayout(gbl);
		
		//JPanel Icon Sales
		JPanel jpIconSales = new JPanel();
		jpIconSales = this.returnJPIconSales();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jpIconSales, gbc);
		
		//JPanel Sales Info
		JPanel jpSalesInfo = new JPanel();
		jpSalesInfo = this.returnJPSalesInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP10.add(jpSalesInfo, gbc);
		
		return returnJP10;
	}
	private JPanel returnJPIconSales() {
		JPanel returnJPIconSales = new JPanel();
		returnJPIconSales.setLayout(gbl);
		
		JLabel jlbliSales = new JLabel();
		jlbliSales.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/departments.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconSales.add(jlbliSales, gbc);
		
		JLabel jlbltSales = new JLabel();
		jlbltSales.setText("Ventas por Departamento");
		jlbltSales.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconSales.add(jlbltSales, gbc);
		
		return returnJPIconSales;
	}
	private JPanel returnJPSalesInfo() {
		JPanel returnJPSalesInfo = new JPanel();
		returnJPSalesInfo.setLayout(gbl);
		
		dtmSalesByDepartment = new DefaultTableModel();
		dtmSalesByDepartment.addColumn("Departamento");
		dtmSalesByDepartment.addColumn("$ Cantidad");
		
		JTable jtSalesByDepartment = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtSalesByDepartment.setModel(dtmSalesByDepartment);
		jtSalesByDepartment.setRowHeight(22);
		jtSalesByDepartment.setShowGrid(false);
		jtSalesByDepartment.setRowSelectionAllowed(false);
		jtSalesByDepartment.setDefaultRenderer(Object.class, new RendererJTableEarningsByDepartment());
		
		JTableHeader jthSales = new JTableHeader();
		jthSales = jtSalesByDepartment.getTableHeader();
		jthSales.setFont(fJTableHeaders);
		
		TableColumn column = null;
		for(int a = 0; a < jtSalesByDepartment.getColumnCount(); a++) {
			column = jtSalesByDepartment.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(170);
			if(a == 1)
				column.setPreferredWidth(50);
		}
		
		JScrollPane jspJTSales = new JScrollPane(jtSalesByDepartment);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSalesInfo.add(jspJTSales, gbc);
		
		return returnJPSalesInfo;
	}
	class RendererJTableEarningsByDepartment extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.BOLD, 16));
				
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(column == 1)
					cell.setForeground(Color.GREEN);
				else
					cell.setForeground(Color.BLACK);
			}
			return cell;
		}
	}
	//JP11============================================================================================================
	
	//JP02============================================================================================================
	private JPanel returnJP02() {
		JPanel returnJP01 = new JPanel();
		returnJP01.setLayout(gbl);
		
		//JPanel Icon Cash Inflows
		JPanel jpIconCashInflows = new JPanel();
		jpIconCashInflows = this.returnJPIconCashInflows();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP01.add(jpIconCashInflows, gbc);
		
		//JPanel Cash Inflows Info
		JPanel jpCashInflowsInfo = new JPanel();
		jpCashInflowsInfo = this.returnJPCashInflowsInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP01.add(jpCashInflowsInfo, gbc);
		
		return returnJP01;
	}
	private JPanel returnJPIconCashInflows() {
		JPanel returnJPIconCashInflows = new JPanel();
		returnJPIconCashInflows.setLayout(gbl);
		
		JLabel jlbliCashInflows = new JLabel();
		jlbliCashInflows.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/cash_inflows.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconCashInflows.add(jlbliCashInflows, gbc);
		
		JLabel jlbltCashInflows = new JLabel();
		jlbltCashInflows.setText("Entradas de Efectivo");
		jlbltCashInflows.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconCashInflows.add(jlbltCashInflows, gbc);
		
		return returnJPIconCashInflows;
	}
	private JPanel returnJPCashInflowsInfo() {
		JPanel returnJPCashInflowsInfo = new JPanel();
		returnJPCashInflowsInfo.setLayout(gbl);
		
		dtmCashInflows = new DefaultTableModel();
		dtmCashInflows.addColumn("Hora");
		dtmCashInflows.addColumn("Comentario/Razón Social");
		dtmCashInflows.addColumn("$ Cantidad");
		
		JTable jtCashInflows = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtCashInflows.setModel(dtmCashInflows);
		jtCashInflows.setRowSelectionAllowed(false);
		jtCashInflows.setDefaultRenderer(Object.class, new RendererJTableCI());
		
		JTableHeader jthCashInflows = new JTableHeader();
		jthCashInflows = jtCashInflows.getTableHeader();
		jthCashInflows.setFont(fJTableHeaders);
		
		TableColumn column = null;
		for(int a = 0; a < jtCashInflows.getColumnCount(); a++) {
			column = jtCashInflows.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(35);
			if(a == 1)
				column.setPreferredWidth(155);
			if(a == 2)
				column.setPreferredWidth(35);
		}
		
		JScrollPane jspJTCashInflows = new JScrollPane(jtCashInflows);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCashInflowsInfo.add(jspJTCashInflows, gbc);
		
		return returnJPCashInflowsInfo;
	}
	class RendererJTableCI extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
				
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(column == 2)
					cell.setForeground(Color.GREEN);
				else
					cell.setForeground(Color.BLACK);
			}
			return cell;
		}
	}
	//JP02============================================================================================================
	
	//JP12============================================================================================================
	private JPanel returnJP12() {
		JPanel returnJP11 = new JPanel();
		returnJP11.setLayout(gbl);
		
		//JPanel Icon Cash Outflows
		JPanel jpIconCashOutflows = new JPanel();
		jpIconCashOutflows = this.returnJPIconCashOutflows();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP11.add(jpIconCashOutflows, gbc);
		
		//JPanel Cash Outflows Info
		JPanel jpCashOutflowsInfo = new JPanel();
		jpCashOutflowsInfo = this.returnJPCashOutflowsInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJP11.add(jpCashOutflowsInfo, gbc);
		
		return returnJP11;
	}
	private JPanel returnJPIconCashOutflows() {
		JPanel returnJPIconCashOutflows = new JPanel();
		returnJPIconCashOutflows.setLayout(gbl);
		
		JLabel jlbliCashOutflows = new JLabel();
		jlbliCashOutflows.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/cash_outflows.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconCashOutflows.add(jlbliCashOutflows, gbc);
		
		JLabel jlbltCashOutflows = new JLabel();
		jlbltCashOutflows.setText("Salidas de Efectivo");
		jlbltCashOutflows.setFont(fJLabelIR);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIconCashOutflows.add(jlbltCashOutflows, gbc);
		
		return returnJPIconCashOutflows;
	}
	private JPanel returnJPCashOutflowsInfo() {
		JPanel returnJPCashOutflowsInfo = new JPanel();
		returnJPCashOutflowsInfo.setLayout(gbl);
		
		dtmCashOutflows = new DefaultTableModel();
		dtmCashOutflows.addColumn("Hora");
		dtmCashOutflows.addColumn("Comentario/Razón Social");
		dtmCashOutflows.addColumn("$ Cantidad");
		
		JTable jtCashOutflows = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtCashOutflows.setModel(dtmCashOutflows);
		jtCashOutflows.setRowSelectionAllowed(false);
		jtCashOutflows.setDefaultRenderer(Object.class, new RendererJTableCO());
		
		JTableHeader jthCashOutflows = new JTableHeader();
		jthCashOutflows = jtCashOutflows.getTableHeader();
		jthCashOutflows.setFont(fJTableHeaders);
		
		TableColumn column = null;
		for(int a = 0; a < jtCashOutflows.getColumnCount(); a++) {
			column = jtCashOutflows.getColumnModel().getColumn(a);
			
			if(a == 0)
				column.setPreferredWidth(35);
			if(a == 1)
				column.setPreferredWidth(155);
			if(a == 2)
				column.setPreferredWidth(35);
		}
		
		JScrollPane jspJTCashOutflows = new JScrollPane(jtCashOutflows);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCashOutflowsInfo.add(jspJTCashOutflows, gbc);
		
		return returnJPCashOutflowsInfo;
	}
	class RendererJTableCO extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
				
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(column == 2)
					cell.setForeground(Color.RED);
				else
					cell.setForeground(Color.BLACK);
			}
			return cell;
		}
	}
	//JP12============================================================================================================
}
