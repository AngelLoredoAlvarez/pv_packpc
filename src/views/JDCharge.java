package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JDCharge extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JLabel jlblTotal, jlblChange;
	public JTabbedPane jtpTypePayment;
	public JButton jbtnChargeSale, jbtnCancel, jbtnAddClient;
	public JCheckBox jchbInvoiceSale;
	public JSpinner jspPayWith;
	public JTextField jtxtfSearchClient;
	public DefaultTableModel dtmClients;
	public JTable jtClients;
	public TableRowSorter<TableModel> rsFilterClient;
	
	//Constructor
	public JDCharge(JFMain jfSales) {
		super(jfSales);
		this.setModal(true);
		this.setTitle("Tipo de Pago");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPCTotal();
		this.setJPCTypePayment();
		this.setJPCActions();
	}
	
	//Method that SETS the JPMain
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Total
	private void setJPCTotal() {
		JPanel jpCTotal = new JPanel();
		jpCTotal.setLayout(gbl);
		
		JLabel jlbltTotal = new JLabel();
		jlbltTotal.setText("Total a Pagar:");
		jlbltTotal.setFont(new Font("Liberation Sans", Font.BOLD, 44));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCTotal.add(jlbltTotal, gbc);
		
		JPanel jpTotal = new JPanel();
		jpTotal = this.returnJPTotal();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCTotal.add(jpTotal, gbc);
		
		//Adds to the JPanel Main (this has to be at the END)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCTotal, gbc);
	}
	private JPanel returnJPTotal() {
		JPanel returnJPTotal = new JPanel();
		returnJPTotal.setLayout(gbl);
		
		JLabel jlbltCoin = new JLabel();
		jlbltCoin.setText("$ ");
		jlbltCoin.setFont(new Font("Liberation Sans", Font.BOLD, 54));
		jlbltCoin.setForeground(Color.BLUE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotal.add(jlbltCoin, gbc);
		
		jlblTotal = new JLabel();
		jlblTotal.setText("0.00");
		jlblTotal.setFont(new Font("Liberation Sans", Font.BOLD, 54));
		jlblTotal.setForeground(Color.BLUE);
		gbc.gridx = 1;
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
	
	//Method that SETS the JPanel Type of Payment
	private void setJPCTypePayment() {
		JPanel jpCTypePayment = new JPanel();
		jpCTypePayment.setLayout(gbl);
		
		jtpTypePayment = new JTabbedPane();
		jtpTypePayment.addTab("Efectivo", new ImageIcon(getClass().getResource("/img/jtabbedpanes/cash.png")), this.returnJPCash(), "Pagó en Efectivo");
		jtpTypePayment.addTab("Crédito", new ImageIcon(getClass().getResource("/img/jtabbedpanes/credit.png")), this.returnJPCredit(), "Créditos a Clientes");
		jtpTypePayment.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		jtpTypePayment.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCTypePayment.add(jtpTypePayment, gbc);
		
		//Adds to the JPanel Main
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCTypePayment, gbc);
	}
	private JPanel returnJPCash() {
		JPanel returnJPCash = new JPanel();
		returnJPCash.setLayout(gbl);
		
		JLabel jlbltPayWith = new JLabel();
		jlbltPayWith.setText("Pagó con:");
		jlbltPayWith.setFont(new Font("Liberation Sans", Font.BOLD, 40));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 20, 0);
		returnJPCash.add(jlbltPayWith, gbc);
		
		SpinnerModel spmPayWith = new SpinnerNumberModel(0.00, 0.00, 10000.00, 0.10);
		jspPayWith = new JSpinner();
		jspPayWith.setModel(spmPayWith);
		JSpinner.NumberEditor jspnePayWith = new JSpinner.NumberEditor(jspPayWith, "#,###,##0.00");
		jspPayWith.setEditor(jspnePayWith);
		jspPayWith.setFont(new Font("Liberation Sans", Font.PLAIN, 36));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 20, 0);
		returnJPCash.add(jspPayWith, gbc);
		
		JLabel jlbltChange = new JLabel();
		jlbltChange.setText("Su Cambió: $");
		jlbltChange.setFont(new Font("Liberation Sans", Font.BOLD, 40));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCash.add(jlbltChange, gbc);
		
		jlblChange = new JLabel();
		jlblChange.setText("0.00");
		jlblChange.setFont(new Font("Liberation Sans", Font.BOLD, 40));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCash.add(jlblChange, gbc);
		
		return returnJPCash;
	}
	private JPanel returnJPCredit() {
		JPanel returnJPCredit = new JPanel();
		returnJPCredit.setLayout(gbl);
		
		JLabel jlbltSearchClient = new JLabel();
		jlbltSearchClient.setText("Búsqueda:");
		jlbltSearchClient.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPCredit.add(jlbltSearchClient, gbc);
		
		jtxtfSearchClient = new JTextField();
		jtxtfSearchClient.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfSearchClient.requestFocus();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPCredit.add(jtxtfSearchClient, gbc);
		
		JPanel jpActionsClients = new JPanel();
		jpActionsClients = this.returnJPActionsClients();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCredit.add(jpActionsClients, gbc);
		
		JScrollPane jspJTClients = new JScrollPane();
		jspJTClients = this.returnJTClients();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCredit.add(jspJTClients, gbc);
		
		return returnJPCredit;
	}
	private JPanel returnJPActionsClients() {
		JPanel returnJPActionsClients = new JPanel();
		returnJPActionsClients.setLayout(gbl);
		
		jbtnAddClient = new JButton();
		jbtnAddClient.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add_client.png")));
		jbtnAddClient.setText("Agregar Cliente");
		jbtnAddClient.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActionsClients.add(jbtnAddClient, gbc);
		
		return returnJPActionsClients;
	}
	private JScrollPane returnJTClients() {
		dtmClients = new DefaultTableModel();
		dtmClients.addColumn("No.");
		dtmClients.addColumn("Cliente");
		dtmClients.addColumn("Límite de Crédito");
		dtmClients.addColumn("Crédito Actual");
		
		jtClients = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtClients.setModel(dtmClients);
		jtClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtClients.setDefaultRenderer(Object.class, new RendererJTableClients());
		
		//To Add the Filtering
		rsFilterClient = new TableRowSorter<>(jtClients.getModel());
		jtClients.setRowSorter(rsFilterClient);
		
		//Columns FONT
		JTableHeader jthClients = new JTableHeader();
		jthClients = jtClients.getTableHeader();
		jthClients.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtClients.getColumnCount(); a++) {
			column = jtClients.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			else if(a == 1)
				column.setPreferredWidth(240);
			else if(a == 2)
				column.setPreferredWidth(110);
			else if(a == 3)
				column.setPreferredWidth(100);
		}
		
		JScrollPane returnJSPJTClients = new JScrollPane(jtClients);
		
		return returnJSPJTClients;
	}
	class RendererJTableClients extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
			cell.setHorizontalAlignment(SwingConstants.CENTER);
			cell.setForeground(Color.BLACK);
			
			if(isSelected)
				cell.setForeground(Color.WHITE);
			else 
				cell.setForeground(Color.BLACK);
			
			return cell;
		}
	}
	
	//Method that SETS the JPanel Actions
	private void setJPCActions() {
		JPanel jpCActions = new JPanel();
		jpCActions.setLayout(gbl);
		
		jbtnChargeSale = new JButton();
		jbtnChargeSale.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/charge_sale.png")));
		jbtnChargeSale.setText("Cobrar Venta");
		jbtnChargeSale.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCActions.add(jbtnChargeSale, gbc);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar Cobro");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpCActions.add(jbtnCancel, gbc);
		
		jchbInvoiceSale = new JCheckBox();
		jchbInvoiceSale.setText("Facturar Venta");
		jchbInvoiceSale.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCActions.add(jchbInvoiceSale, gbc);
		
		//Adds to the JPanel Main
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("cashSell"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("creditSell"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
	}
}
