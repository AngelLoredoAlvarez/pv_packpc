package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class JDStatementOfAccount extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JLabel jlblNo, jlblClient, jlblCurrentBalance, jlbltCoinCreditLimit, jlblCreditLimit, jlblTotal;
	public JDatePickerImpl jdpInitDate, jdpEndDate;
	public JButton jbtnExit, jbtnPaymentHistory, jbtnPay, jbtnLiquidate, jbtnPrintDetails;
	public DefaultTableModel dtmDates, dtmProducts;
	public JTable jtDates, jtProducts;
	
	//Constructor
	public JDStatementOfAccount(JDConsultClients jdConsultClients) {
		super(jdConsultClients);
		this.setModal(true);
		Image iconJD = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/statement_of_account.png"));
		this.setIconImage(iconJD);
		this.setTitle("Estado de Cuenta");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(jdConsultClients);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPCreditInfo();
		this.setJPCreditDetails();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel CreditInfo
	private void setJPCreditInfo() {
		JPanel jpCredit = new JPanel();
		jpCredit.setLayout(gbl);
		jpCredit.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información del Crédito",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("LiberationSerif", Font.BOLD, 18)
			)
		);
		
		JPanel jpClientInfo = new JPanel();
		jpClientInfo = this.returnJPClientInfo();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpCredit.add(jpClientInfo, gbc);
		
		JPanel jpCreditInfo = new JPanel();
		jpCreditInfo = this.returnJPCreditInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCredit.add(jpCreditInfo, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCredit, gbc);
	}
	private JPanel returnJPClientInfo() {
		JPanel returnJPClientInfo = new JPanel();
		returnJPClientInfo.setLayout(gbl);
		
		JPanel jpContainerClient = new JPanel();
		jpContainerClient.setLayout(gbl);
		
		JLabel jlbltNo = new JLabel();
		jlbltNo.setText("No.:");
		jlbltNo.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClient.add(jlbltNo, gbc);
		
		jlblNo = new JLabel();
		jlblNo.setText("0");
		jlblNo.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 20);
		jpContainerClient.add(jlblNo, gbc);
		
		JLabel jlbltClient = new JLabel();
		jlbltClient.setText("Cliente: ");
		jlbltClient.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClient.add(jlbltClient, gbc);
		
		jlblClient = new JLabel();
		jlblClient.setText("Client");
		jlblClient.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClient.add(jlblClient, gbc);
		
		JPanel jpSpaceClient = new JPanel();
		jpSpaceClient.setLayout(new FlowLayout(FlowLayout.RIGHT));
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClient.add(jpSpaceClient, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnExit.setFocusable(false);
		jpSpaceClient.add(jbtnExit);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPClientInfo.add(jpContainerClient, gbc);
		
		return returnJPClientInfo;
	}
	private JPanel returnJPCreditInfo() {
		JPanel returnJPCreditInfo = new JPanel();
		returnJPCreditInfo.setLayout(gbl);
		
		JPanel jpContainerCredit = new JPanel();
		jpContainerCredit.setLayout(gbl);
		
		JLabel jlbltCurrentBalance = new JLabel();
		jlbltCurrentBalance.setText("Saldo Actual: ");
		jlbltCurrentBalance.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jlbltCurrentBalance, gbc);
		
		JLabel jlbltCoinCurrentBalance = new JLabel();
		jlbltCoinCurrentBalance.setText("$");
		jlbltCoinCurrentBalance.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jlbltCoinCurrentBalance, gbc);
		
		jlblCurrentBalance = new JLabel();
		jlblCurrentBalance.setText("0.00");
		jlblCurrentBalance.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 20);
		jpContainerCredit.add(jlblCurrentBalance, gbc);
		
		JLabel jlbltCreditLimit = new JLabel();
		jlbltCreditLimit.setText("Límite de Crédito: ");
		jlbltCreditLimit.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jlbltCreditLimit, gbc);
		
		jlbltCoinCreditLimit = new JLabel();
		jlbltCoinCreditLimit.setText("$");
		jlbltCoinCreditLimit.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jlbltCoinCreditLimit, gbc);
		
		jlblCreditLimit = new JLabel();
		jlblCreditLimit.setText("0.00");
		jlblCreditLimit.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jlblCreditLimit, gbc);
		
		JPanel jpSpaceCredit = new JPanel();
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerCredit.add(jpSpaceCredit, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCreditInfo.add(jpContainerCredit, gbc);
		
		return returnJPCreditInfo;
	}
	
	//Method that SETS the JPanel Credit Details
	private void setJPCreditDetails() {
		JPanel jpCreditDetails = new JPanel();
		jpCreditDetails.setLayout(gbl);
		jpCreditDetails.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Detalles del Crédito",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		JPanel jpActions = new JPanel();
		jpActions = this.returnJPActions();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpCreditDetails.add(jpActions, gbc);
		
		JPanel jpFilterDates = new JPanel();
		jpFilterDates = this.returnJPFilterDates();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpCreditDetails.add(jpFilterDates, gbc);
		
		JPanel jpDates = new JPanel();
		jpDates = this.returnJPDates();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCreditDetails.add(jpDates, gbc);
		
		JPanel jpProducts = new JPanel();
		jpProducts = this.returnJPProducts();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCreditDetails.add(jpProducts, gbc);
		
		JPanel jpTotal = new JPanel();
		jpTotal = this.returnJPTotal();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCreditDetails.add(jpTotal, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCreditDetails, gbc);
	}
	private JPanel returnJPActions() {
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		jbtnPaymentHistory = new JButton();
		jbtnPaymentHistory.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/payment_history.png")));
		jbtnPaymentHistory.setText("Revisar Historial");
		jbtnPaymentHistory.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnPaymentHistory.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnPaymentHistory, gbc);
		
		jbtnPay = new JButton();
		jbtnPay.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/pay.png")));
		jbtnPay.setText("Abonar al Crédito");
		jbtnPay.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnPay.setFocusable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnPay, gbc);
		
		jbtnLiquidate = new JButton();
		jbtnLiquidate.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/liquidate.png")));
		jbtnLiquidate.setText("Liquidar Adeudo");
		jbtnLiquidate.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnLiquidate.setFocusable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnLiquidate, gbc);
		
		return returnJPActions;
	}
	private JPanel returnJPFilterDates() {
		JPanel returnJPFilterDates = new JPanel();
		returnJPFilterDates.setLayout(gbl);
		
		JLabel jlbltInitDate = new JLabel();
		jlbltInitDate.setText("Fecha de Inicio:");
		jlbltInitDate.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 0);
		returnJPFilterDates.add(jlbltInitDate, gbc);
		
		UtilDateModel udmInitDate = new UtilDateModel();
		Properties pInitDate = new Properties();
		pInitDate.put("text.today", "Today");
		pInitDate.put("text.month", "Month");
		pInitDate.put("text.year", "Year");
		JDatePanelImpl dpInitDate = new JDatePanelImpl(udmInitDate, pInitDate);
		jdpInitDate = new JDatePickerImpl(dpInitDate, new DateLabelFormatter());
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 10);
		returnJPFilterDates.add(jdpInitDate, gbc);
		
		JLabel jlbltEndDate = new JLabel();
		jlbltEndDate.setText("Fecha de Fin:");
		jlbltEndDate.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterDates.add(jlbltEndDate, gbc);
		
		UtilDateModel udmEndDate = new UtilDateModel();
		Properties pEndDate = new Properties();
		pInitDate.put("text.today", "Today");
		pInitDate.put("text.month", "Month");
		pInitDate.put("text.year", "Year");
		JDatePanelImpl dpEndDate = new JDatePanelImpl(udmEndDate, pEndDate);
		jdpEndDate = new JDatePickerImpl(dpEndDate, new DateLabelFormatter());
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPFilterDates.add(jdpEndDate, gbc);
		
		return returnJPFilterDates;
	}
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private Date actualDate = new Date();
		private SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
		
		@Override
	    public Object stringToValue(String text) throws ParseException {
			return sdfDate.parseObject(text);
	    }
	    
	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	        	Calendar selectedDate = (Calendar) value;
	        	return sdfDate.format(selectedDate.getTime());
	        }
	        return sdfDate.format(actualDate);
	    }
	}
	private JPanel returnJPDates() {
		JPanel returnJPDates = new JPanel();
		returnJPDates.setLayout(gbl);
		
		JLabel jlbltSells = new JLabel();
		jlbltSells.setText("Ventas Realizadas al Cliente");
		jlbltSells.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDates.add(jlbltSells, gbc);
		
		dtmDates = new DefaultTableModel();
		dtmDates.addColumn("No.");
		dtmDates.addColumn("Fecha");
		
		jtDates = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtDates.setModel(dtmDates);
		jtDates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtDates.setRowHeight(20);
		jtDates.setDefaultRenderer(Object.class, new RendererJTableDates());
		
		//Columns FONT
		JTableHeader jthDates = new JTableHeader();
		jthDates = jtDates.getTableHeader();
		jthDates.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		//Columns WIDTH
		TableColumn column = null;
		for(int a = 0; a < jtDates.getColumnCount(); a++) {
			column = jtDates.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			if(a == 1)
				column.setPreferredWidth(160);
		}
		
		JScrollPane jspJTDates = new JScrollPane(jtDates);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDates.add(jspJTDates, gbc);
		
		return returnJPDates;
	}
	class RendererJTableDates extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			cell.setFont(new Font("Liberation Sans", Font.PLAIN, 10));
			cell.setHorizontalAlignment(SwingConstants.CENTER);
			cell.setForeground(Color.BLACK);
			
			if(isSelected)
				cell.setForeground(Color.WHITE);
			else 
				cell.setForeground(Color.BLACK);
			
			return cell;
		}
	}
	private JPanel returnJPProducts() {
		JPanel returnJPProducts = new JPanel();
		returnJPProducts.setLayout(gbl);
		
		JLabel jlbltProductsOnSell = new JLabel();
		jlbltProductsOnSell.setText("Productos en la Venta");
		jlbltProductsOnSell.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProducts.add(jlbltProductsOnSell, gbc);
		
		dtmProducts = new DefaultTableModel();
		dtmProducts.addColumn("Descripción del Producto");
		dtmProducts.addColumn("Precio Venta");
		dtmProducts.addColumn("Cantidad");
		dtmProducts.addColumn("Importe");
		
		jtProducts = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtProducts.setModel(dtmProducts);
		jtProducts.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jtProducts.setRowHeight(20);
		jtProducts.setRowSelectionAllowed(false);
		
		//Columns FONT
		JTableHeader jthProducts = new JTableHeader();
		jthProducts = jtProducts.getTableHeader();
		jthProducts.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		//Columns WIDTH
		TableColumn column = null;
		for(int a = 0; a < jtProducts.getColumnCount(); a++) {
			column = jtProducts.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(200);
			else if(a == 1)
				column.setPreferredWidth(100);
			else if(a == 2)
				column.setPreferredWidth(100);
			else if(a == 3)
				column.setPreferredWidth(100);
		}
		
		JScrollPane jspJTProducts = new JScrollPane(jtProducts);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProducts.add(jspJTProducts, gbc);
		
		return returnJPProducts;
	}
	
	private JPanel returnJPTotal() {
		JPanel returnJPTotal = new JPanel();
		returnJPTotal.setLayout(gbl);
		
		JPanel jpSpaceTotal = new JPanel();
		jpSpaceTotal.setLayout(new FlowLayout(FlowLayout.RIGHT));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotal.add(jpSpaceTotal, gbc);
		
//		jbtnPrintDetails = new JButton();
//		jbtnPrintDetails.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/printer.png")));
//		jbtnPrintDetails.setText("Imprimir Detalles");
//		jbtnPrintDetails.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
//		jbtnPrintDetails.setFocusable(false);
//		jpSpaceTotal.add(jbtnPrintDetails);
		
		JLabel jlbltTotal = new JLabel();
		jlbltTotal.setText("Total: $ ");
		jlbltTotal.setFont(new Font("Liberation Sans", Font.BOLD, 28));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotal.add(jlbltTotal, gbc);
		
		jlblTotal = new JLabel();
		jlblTotal.setText("0.00");
		jlblTotal.setFont(new Font("Liberation Sans", Font.BOLD, 28));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotal.add(jlblTotal, gbc);
		
		return returnJPTotal;
	}
	
	public String setJOPMessages(String typeMessage, String message) {
		String action = "";
		if(typeMessage.equals("liquidate")) {
			int answer = JOptionPane.showConfirmDialog(this, message, "¿Liquidar Adeudo?", JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION)
				action = "YES";
			else
				action = "NO";
		} else if(typeMessage.equals("notOwn")) 
			JOptionPane.showMessageDialog(this, message, "Estado de Cuenta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("initLarger"))
			JOptionPane.showMessageDialog(this, message, "Estado de Cuenta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("endLower"))
			JOptionPane.showMessageDialog(this, message, "Estado de Cuenta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("balanceUpdated"))
			JOptionPane.showMessageDialog(this, message, "Estado de Cuenta", JOptionPane.INFORMATION_MESSAGE);
		
		return action;
	}
}
