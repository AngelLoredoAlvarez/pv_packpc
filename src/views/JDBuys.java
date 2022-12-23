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
import javax.swing.ButtonGroup;
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

public class JDBuys extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JDatePickerImpl jdpInitDate, jdpEndDate;
	public JRadioButton jrbProvider, jrbCashier;
	public JTextField jtxtfSearch;
	public JButton jbtnNewBuy, jbtnDetailsBuy, jbtnExit;
	public DefaultTableModel dtmBuys;
	public JTable jtBuys;
	public TableRowSorter<TableModel> trsSorter;
	
	//Constructor
	public JDBuys(JFMain jfSales) {
		super(jfSales);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/buys.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Compras a Proveedores");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1024, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
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
	
	//Method that SETS the JPanel North
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JPanel jpFilterByDate = new JPanel();
		jpFilterByDate = this.returnJPFilterByDate();
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
		
		JPanel jpFilterByPC = new JPanel();
		jpFilterByPC = this.returnJPFilterByPC();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		jpNorth.add(jpFilterByPC, gbc);
		
		JPanel jpActions = new JPanel();
		jpActions = this.returnJPActions();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpActions, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
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
					"Filtrar por Fechas:",
					TitledBorder.ABOVE_BOTTOM,
					TitledBorder.TOP,
					new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		JLabel jlbltInitDate = new JLabel();
		jlbltInitDate.setText("Fecha Inicio:");
		jlbltInitDate.setFont(new Font("Liberation Sans", Font.BOLD, 14));
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
		jlbltEndDate.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(2, 0, 0, 0);
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
		gbc.insets = new Insets(2, 0, 0, 0);
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
	private JPanel returnJPFilterByPC() {
		JPanel returnJPFilterByPC = new JPanel();
		returnJPFilterByPC.setLayout(gbl);
		returnJPFilterByPC.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Filtrar por:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		jrbProvider = new JRadioButton();
		jrbProvider.setText("Proveedor");
		jrbProvider.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jrbProvider.setSelected(true);
		jrbProvider.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByPC.add(jrbProvider, gbc);
		
		jrbCashier = new JRadioButton();
		jrbCashier.setText("Cajero");
		jrbCashier.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jrbCashier.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByPC.add(jrbCashier, gbc);
		
		ButtonGroup bgJRB = new ButtonGroup();
		bgJRB.add(jrbProvider);
		bgJRB.add(jrbCashier);
		
		JLabel jlblIcon = new JLabel();
		jlblIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/search.png")));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByPC.add(jlblIcon, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByPC.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField(27);
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfSearch.requestFocus();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByPC.add(jtxtfSearch, gbc);
		
		return returnJPFilterByPC;
	}
	private JPanel returnJPActions() {
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnNewBuy = new JButton();
		jbtnNewBuy.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add_buy.png")));
		jbtnNewBuy.setText("Nueva Compra");
		jbtnNewBuy.setFont(fJButtons);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnNewBuy, gbc);
		
		jbtnDetailsBuy = new JButton();
		jbtnDetailsBuy.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/detailsBS.png")));
		jbtnDetailsBuy.setText("Ver Detalles");
		jbtnDetailsBuy.setFont(fJButtons);
		jbtnDetailsBuy.setEnabled(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnDetailsBuy, gbc);
		
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
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmBuys = new DefaultTableModel();
		dtmBuys.addColumn("No.");
		dtmBuys.addColumn("Fecha");
		dtmBuys.addColumn("Total");
		dtmBuys.addColumn("Proveedor");
		dtmBuys.addColumn("Cajero");
		
		jtBuys = new JTable() {
			//Attributes
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtBuys.setModel(dtmBuys);
		jtBuys.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtBuys.setRowHeight(20);
		jtBuys.setDefaultRenderer(Object.class, new RendererJTableBuys());
		
		trsSorter = new TableRowSorter<TableModel>(jtBuys.getModel());
		jtBuys.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthBuys = new JTableHeader();
		jthBuys = jtBuys.getTableHeader();
		jthBuys.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtBuys.getColumnCount(); a++) {
			column = jtBuys.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			else if(a == 1)
				column.setPreferredWidth(250);
			else if(a == 2)
				column.setPreferredWidth(30);
			else if(a == 3)
				column.setPreferredWidth(150);
			else if(a == 4)
				column.setPreferredWidth(150);
		}
		
		JScrollPane jspJTBuys = new JScrollPane(jtBuys);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jspJTBuys, gbc);
		
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
		jpMain.add(jpCenter, gbc);
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
			else
				cell.setForeground(Color.BLACK);
			
			return cell;
		}
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("initLarger"))
			JOptionPane.showMessageDialog(this, message, "Compras", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("endLower"))
			JOptionPane.showMessageDialog(this, message, "Compras", JOptionPane.INFORMATION_MESSAGE);
	}
}
