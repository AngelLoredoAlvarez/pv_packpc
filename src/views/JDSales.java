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
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class JDSales extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JDatePickerImpl jdpInitDate, jdpEndDate;
	public JRadioButton jrbClient, jrbStatus;
	public JTextField jtxtfSearch;
	public JButton jbtnInvoiceSale, jbtnCancelSale, jbtnExit;
	public DefaultTableModel dtmSales, dtmDetailsSale;
	public JTable jtSales, jtDetailsSale;
	public TableRowSorter<TableModel> trsSorter;
	public JLabel jlblTotalSale;
	
	//Constructor
	public JDSales(JFMain jfMain) {
		super(jfMain);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/sales.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Ventas a Clientes");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1200, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(jfMain);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPNorth();
		this.setJPJTSales();
		this.setJPJTDetailsSale();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel North
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JPanel jpFilterByDate = this.returnJPFilterByDate();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		jpNorth.add(jpFilterByDate, gbc);
		
		JPanel jpFilterByCC = this.returnJPFilterByCC();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		jpNorth.add(jpFilterByCC, gbc);
		
		JPanel jpActions = this.returnJPActions();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
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
	private JPanel returnJPFilterByDate() {
		JPanel returnJPFilterByDate = new JPanel();
		returnJPFilterByDate.setLayout(gbl);
		returnJPFilterByDate.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Filtrar por Fecha:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		Font fJLabels = new Font("Liberation Sans", Font.BOLD, 14);
		
		JLabel jlbltInitDate = new JLabel();
		jlbltInitDate.setText("Fecha Inicio:");
		jlbltInitDate.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDate.add(jlbltInitDate, gbc);
		
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
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDate.add(jdpInitDate, gbc);
		
		JLabel jlbltEndDate = new JLabel();
		jlbltEndDate.setText("Fecha Fin:");
		jlbltEndDate.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDate.add(jlbltEndDate, gbc);
		
		UtilDateModel udmEndDate = new UtilDateModel();
		Properties pEndDate = new Properties();
		pInitDate.put("text.today", "Today");
		pInitDate.put("text.month", "Month");
		pInitDate.put("text.year", "Year");
		JDatePanelImpl dpEndDate = new JDatePanelImpl(udmEndDate, pEndDate);
		jdpEndDate = new JDatePickerImpl(dpEndDate, new DateLabelFormatter());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDate.add(jdpEndDate, gbc);
		
		return returnJPFilterByDate;
	}
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
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
	        return "";
	    }
	}
	private JPanel returnJPFilterByCC() {
		JPanel returnJPFilterByCC = new JPanel();
		returnJPFilterByCC.setLayout(gbl);
		returnJPFilterByCC.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Filtrar por Cliente:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		JLabel jlbliSearch = new JLabel();
		jlbliSearch.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/search.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByCC.add(jlbliSearch, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByCC.add(jlbltSearch, gbc);
			
		jtxtfSearch = new JTextField(40);
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		jtxtfSearch.requestFocus();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByCC.add(jtxtfSearch, gbc);
		
		return returnJPFilterByCC;
	}
	private JPanel returnJPActions() {
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnInvoiceSale = new JButton();
		jbtnInvoiceSale.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/invoice_sale.png")));
		jbtnInvoiceSale.setText("Facturar Venta");
		jbtnInvoiceSale.setFont(fJButtons);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnInvoiceSale, gbc);
		
		jbtnCancelSale = new JButton();
		jbtnCancelSale.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnCancelSale.setText("Cancelar Venta");
		jbtnCancelSale.setFont(fJButtons);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnCancelSale, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(fJButtons);
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
	
	//Method that SETS the JPanel JTSales
	private void setJPJTSales() {
		JPanel jpJTSales = new JPanel();
		jpJTSales.setLayout(gbl);
		
		dtmSales = new DefaultTableModel();
		dtmSales.addColumn("No.");
		dtmSales.addColumn("Fecha");
		dtmSales.addColumn("Hora");
		dtmSales.addColumn("Cliente");
		dtmSales.addColumn("Estado");
			
		jtSales = new JTable() {
			//Attributes
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtSales.setModel(dtmSales);
		jtSales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtSales.setRowHeight(20);
		jtSales.setDefaultRenderer(Object.class, new RendererJTableBuys());
		
		trsSorter = new TableRowSorter<TableModel>(jtSales.getModel());
		jtSales.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthSales = new JTableHeader();
		jthSales = jtSales.getTableHeader();
		jthSales.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtSales.getColumnCount(); a++) {
			column = jtSales.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(20);
			else if(a == 1)
				column.setPreferredWidth(250);
			else if(a == 2)
				column.setPreferredWidth(75);
			else if(a == 3)
				column.setPreferredWidth(205);
			else if(a == 5)
				column.setPreferredWidth(65);
		}
		
		JScrollPane jspJTSales = new JScrollPane(jtSales);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpJTSales.add(jspJTSales, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(3, 0, 0, 0);
		jpMain.add(jpJTSales, gbc);
	}
	class RendererJTableBuys extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
			cell.setHorizontalAlignment(SwingConstants.CENTER);
			cell.setForeground(Color.BLACK);
			
			if(isSelected)
				cell.setForeground(Color.WHITE);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.BOLD, 14));
				if(value.equals("Pagada"))
					cell.setForeground(Color.BLUE);
				else if(value.equals("Pendiente"))
					cell.setForeground(Color.ORANGE);
				else
					cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
			}
			
			return cell;
		}
	}
	
	//Method that SETS the JPanel JTDetailsSale
	private void setJPJTDetailsSale() {
		JPanel jpJTDetailsSale = new JPanel();
		jpJTDetailsSale.setLayout(gbl);
		
		dtmDetailsSale = new DefaultTableModel();
		dtmDetailsSale.addColumn("Cantidad");
		dtmDetailsSale.addColumn("Descripción");
		dtmDetailsSale.addColumn("Importe");
		
		jtDetailsSale = new JTable() {
			//Attributes
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtDetailsSale.setModel(dtmDetailsSale);
		jtDetailsSale.setRowSelectionAllowed(false);
		jtDetailsSale.setRowHeight(20);
		jtDetailsSale.setDefaultRenderer(Object.class, new RendererJTableDetails());
		
		//Columns FONT
		JTableHeader jthDetailsSale = new JTableHeader();
		jthDetailsSale = jtDetailsSale.getTableHeader();
		jthDetailsSale.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		TableColumn column = null;
		for(int a = 0; a < jtDetailsSale.getColumnCount(); a++) {
			column = jtDetailsSale.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(5);
			else if(a == 1)
				column.setPreferredWidth(175);
			else if(a == 2)
				column.setPreferredWidth(30);
		}
		
		JScrollPane jspJTDetailsSale = new JScrollPane(jtDetailsSale);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpJTDetailsSale.add(jspJTDetailsSale, gbc);
		
		JPanel jpTotalSale = this.returnJPTotalSale();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpJTDetailsSale.add(jpTotalSale, gbc);
		
		//This has to be at the END
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(3, 0, 0, 0);
		jpMain.add(jpJTDetailsSale, gbc);
	}
	class RendererJTableDetails extends DefaultTableCellRenderer {
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
	private JPanel returnJPTotalSale() {
		JPanel returnJPTotalSale = new JPanel();
		returnJPTotalSale.setLayout(gbl);
		
		JLabel jlbltTotal = new JLabel();
		jlbltTotal.setText("Total: ");
		jlbltTotal.setFont(new Font("Liberation Sans", Font.BOLD, 42));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalSale.add(jlbltTotal, gbc);
		
		jlblTotalSale = new JLabel();
		jlblTotalSale.setText("0.00");
		jlblTotalSale.setFont(new Font("Liberation Sans", Font.BOLD, 42));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalSale.add(jlblTotalSale, gbc);
		
		return returnJPTotalSale;
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("initLarger"))
			JOptionPane.showMessageDialog(this, message, "Compras", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("endLower"))
			JOptionPane.showMessageDialog(this, message, "Compras", JOptionPane.INFORMATION_MESSAGE);
	}
}