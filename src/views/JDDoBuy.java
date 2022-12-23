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
import javax.swing.JTextField;
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

public class JDDoBuy extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JDatePickerImpl jdpDate;
	public JLabel jlblFolium, jlblNoProvider, jlblProvider, jlblTotalToPay;
	public JButton jbtnSearchProvider, jbtnSearchProduct, jbtnRemove, jbtnRegisterBuy, jbtnExit;
	public JTextField jtxtfBarCode;
	public DefaultTableModel dtmProducts;
	public JTable jtProducts;
	
	//Constructor
	public JDDoBuy(JDialog jdBuys) {
		super(jdBuys);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/buys.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Compra de Productos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(900, 550);
		this.setResizable(false);
		this.setLocationRelativeTo(jdBuys);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPBuyInfo();
		this.setJPProducts();
		this.setJPTotalToPay();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Buy Info
	private void setJPBuyInfo() {
		JPanel jpBuyInfo = new JPanel();
		jpBuyInfo.setLayout(gbl);
		jpBuyInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información de la Compra:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		Font fJLabels = new Font("Liberation Sans", Font.BOLD, 16);
		Font fInfo = new Font("Liberation Sans", Font.PLAIN, 16);
		
		JLabel jlbltDate = new JLabel();
		jlbltDate.setText("Fecha:");
		jlbltDate.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBuyInfo.add(jlbltDate, gbc);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		jdpDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		jpBuyInfo.add(jdpDate, gbc);
		
		JLabel jlbltFolium = new JLabel();
		jlbltFolium.setText("No. de Folio:");
		jlbltFolium.setFont(fJLabels);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBuyInfo.add(jlbltFolium, gbc);
		
		jlblFolium = new JLabel();
		jlblFolium.setText("1");
		jlblFolium.setFont(fInfo);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		jpBuyInfo.add(jlblFolium, gbc);
		
		JLabel jlbltProvider = new JLabel();
		jlbltProvider.setText("Proveedor:");
		jlbltProvider.setFont(fJLabels);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBuyInfo.add(jlbltProvider, gbc);
		
		jlblNoProvider = new JLabel();
		jlblNoProvider.setText("");
		jlblNoProvider.setVisible(false);
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBuyInfo.add(jlblNoProvider, gbc);
		
		jlblProvider = new JLabel();
		jlblProvider.setText("===============");
		jlblProvider.setFont(fInfo);
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		jpBuyInfo.add(jlblProvider, gbc);
		
		jbtnSearchProvider = new JButton();
		jbtnSearchProvider.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/search.png")));
		jbtnSearchProvider.setText("Buscar Proveedor");
		jbtnSearchProvider.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 1);
		jpBuyInfo.add(jbtnSearchProvider, gbc);
		
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
		jpMain.add(jpBuyInfo, gbc);
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
	
	//Method that SETS the JPanel Products
	private void setJPProducts() {
		JPanel jpProducts = new JPanel();
		jpProducts.setLayout(gbl);
		jpProducts.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Productos en la Compra:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		JPanel jpProduct = new JPanel();
		jpProduct = this.returnJPProduct();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpProducts.add(jpProduct, gbc);
		
		JPanel jpListProducts = new JPanel();
		jpListProducts = this.returnJPListProducts();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 0, 0);
		jpProducts.add(jpListProducts, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 0, 0);
		jpMain.add(jpProducts, gbc);
	}
	private JPanel returnJPProduct() {
		JPanel returnJPProduct = new JPanel();
		returnJPProduct.setLayout(gbl);
		
		JLabel jlbltBarCode = new JLabel();
		jlbltBarCode.setText("Código de Barras:");
		jlbltBarCode.setFont(new Font("Liberation Sans", Font.BOLD, 24));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProduct.add(jlbltBarCode, gbc);
		
		jtxtfBarCode = new JTextField(15);
		jtxtfBarCode.setFont(new Font("Liberation Sans", Font.BOLD, 24));
		jtxtfBarCode.setEnabled(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		returnJPProduct.add(jtxtfBarCode, gbc);
		
		jbtnSearchProduct = new JButton();
		jbtnSearchProduct.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/search.png")));
		jbtnSearchProduct.setText("Buscar Producto");
		jbtnSearchProduct.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnSearchProduct.setEnabled(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		returnJPProduct.add(jbtnSearchProduct, gbc);
		
		jbtnRemove = new JButton();
		jbtnRemove.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnRemove.setText("Quitar Articulo");
		jbtnRemove.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnRemove.setEnabled(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 8);
		returnJPProduct.add(jbtnRemove, gbc);
		
		return returnJPProduct;
	}
	private JPanel returnJPListProducts() {
		JPanel returnJPListProducts = new JPanel();
		returnJPListProducts.setLayout(gbl);
		
		dtmProducts = new DefaultTableModel();
		dtmProducts.addColumn("No.");
		dtmProducts.addColumn("Código de Barras");
		dtmProducts.addColumn("Descripción");
		dtmProducts.addColumn("Cantidad");
		dtmProducts.addColumn("Importe");
		
		jtProducts = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				if(column == 3)
					return true;
				else
					return false;
			}
		};
		jtProducts.setModel(dtmProducts);
		jtProducts.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jtProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtProducts.setRowHeight(20);
		jtProducts.setDefaultRenderer(Object.class, new RendererJTableProducts());
		
		//Columns FONT
		JTableHeader jthProducts = new JTableHeader();
		jthProducts = jtProducts.getTableHeader();
		jthProducts.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtProducts.getColumnCount(); a++) {
			column = jtProducts.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			else if(a == 1)
				column.setPreferredWidth(110);
			else if(a == 2)
				column.setPreferredWidth(250);
			else if(a == 3)
				column.setPreferredWidth(80);
			else if(a == 4)
				column.setPreferredWidth(80);
		}
		
		JScrollPane jspJTableProducts = new JScrollPane(jtProducts);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPListProducts.add(jspJTableProducts, gbc);
		
		return returnJPListProducts;
	}
	class RendererJTableProducts extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			cell.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
			cell.setHorizontalAlignment(SwingConstants.CENTER);
			cell.setForeground(Color.BLACK);
			
			if(isSelected)
				cell.setForeground(Color.WHITE);
			else
				cell.setForeground(Color.BLACK);
			

			return cell;
		}
	}
	
	//Method that SETS the JPanel Total To Pay
	private void setJPTotalToPay() {
		JPanel jpTotalToPay = new JPanel();
		jpTotalToPay.setLayout(gbl);
		
		JPanel jpSpace = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTotalToPay.add(jpSpace, gbc);
		
		JLabel jlbltTotalToPay = new JLabel();
		jlbltTotalToPay.setText("Total a Pagar: $");
		jlbltTotalToPay.setFont(new Font("Liberation Sans", Font.BOLD, 36));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTotalToPay.add(jlbltTotalToPay, gbc);
		
		jlblTotalToPay = new JLabel();
		jlblTotalToPay.setText("0.00");
		jlblTotalToPay.setFont(new Font("Liberation Sans", Font.BOLD, 36));
		jlblTotalToPay.setForeground(Color.RED);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTotalToPay.add(jlblTotalToPay, gbc);
		
		JLabel jlbltCoin = new JLabel();
		jlbltCoin.setText(" MXN");
		jlbltCoin.setFont(new Font("Liberation Sans", Font.BOLD, 36));
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTotalToPay.add(jlbltCoin, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpTotalToPay, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnRegisterBuy = new JButton();
		jbtnRegisterBuy.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnRegisterBuy.setText("Registrar Compra");
		jbtnRegisterBuy.setFont(fJButtons);
		jbtnRegisterBuy.setEnabled(false);
		jpActions.add(jbtnRegisterBuy);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnExit.setText("Cancelar");
		jbtnExit.setFont(fJButtons);
		jpActions.add(jbtnExit);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages from the User
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("buyRegistered"))
			JOptionPane.showMessageDialog(this, message, "Compras a Proveedores", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("noMatch"))
			JOptionPane.showMessageDialog(this, message, "Compras a Proveedores", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("notExist"))
			JOptionPane.showMessageDialog(this, message, "Compras a Proveedores", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("biggerTotal"))
			JOptionPane.showMessageDialog(this, message, "Compras a Proveedores", JOptionPane.WARNING_MESSAGE);
	}
}

