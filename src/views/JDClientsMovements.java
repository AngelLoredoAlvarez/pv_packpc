package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JDClientsMovements extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JComboBox<String> jcbWhatToShow, jcbQuantities;
	public Date date = null;
	public JButton jbtnPrint, jbtnExit;
	public DefaultTableModel dtmClientsMovements;
	public TableRowSorter<TableModel> trsSorter;
	public JTable jtClientsMovements;
	
	//Constructor
	public JDClientsMovements(JDStatementOfAccount jdStatementOfAccount, Date date) {
		super(jdStatementOfAccount);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/payment_history.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Movimientos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(550, 383);
		this.setResizable(false);
		this.setLocationRelativeTo(jdStatementOfAccount);
		this.date = date;
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
		
		Font fJLabels = new Font("Liberation Sans", Font.BOLD, 14);
		Font fFields = new Font("Liberation Sans", Font.PLAIN, 14);
		
		JLabel jlbltWhatToShow = new JLabel();
		jlbltWhatToShow.setText("Movimiento:");
		jlbltWhatToShow.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jlbltWhatToShow, gbc);
		
		String[] items = {"Todos", "Abonos", "Compras"};
		jcbWhatToShow = new JComboBox<String>(items);
		jcbWhatToShow.setFont(fFields);
		jcbWhatToShow.setSelectedIndex(0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jcbWhatToShow, gbc);
		
		JLabel jlbltQuantity = new JLabel();
		jlbltQuantity.setText("Ver:");
		jlbltQuantity.setFont(fJLabels);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jlbltQuantity, gbc);
		
		String[] quantities = {"Todo", "15", "30"};
		jcbQuantities = new JComboBox<String>(quantities);
		jcbQuantities.setFont(fFields);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jcbQuantities, gbc);
		
		JPanel jpSpace = new JPanel();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jpSpace, gbc);
		
//		jbtnPrint = new JButton();
//		jbtnPrint.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/printer.png")));
//		jbtnPrint.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
//		jbtnPrint.setText("Imprimir");
//		gbc.gridx = 5;
//		gbc.gridy = 0;
//		gbc.gridwidth = 1;
//		gbc.gridheight = 1;
//		gbc.weightx = 0.0;
//		gbc.weighty = 0.0;
//		gbc.fill = GridBagConstraints.NONE;
//		gbc.insets = new Insets(0, 0, 0, 1);
//		gbc.anchor = GridBagConstraints.CENTER;
//		jpNorth.add(jbtnPrint, gbc);
//		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jbtnExit, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpNorth, gbc);
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmClientsMovements = new DefaultTableModel();
		dtmClientsMovements.addColumn("Fecha");
		dtmClientsMovements.addColumn("Hora");
		dtmClientsMovements.addColumn("Movimiento");
		dtmClientsMovements.addColumn("$Cantidad");
		
		jtClientsMovements = new JTable() {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtClientsMovements.setModel(dtmClientsMovements);
		jtClientsMovements.setRowHeight(20);
		jtClientsMovements.setRowSelectionAllowed(false);
		jtClientsMovements.setDefaultRenderer(Object.class, new RendererJTableClientsMovements());
		
		trsSorter = new TableRowSorter<TableModel>(jtClientsMovements.getModel());
		jtClientsMovements.setRowSorter(trsSorter);
		
		JTableHeader jthPaymentHistory = new JTableHeader();
		jthPaymentHistory = jtClientsMovements.getTableHeader();
		jthPaymentHistory.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		TableColumn column = null;
		for(int a = 0; a < jtClientsMovements.getColumnCount(); a++) {
			column = jtClientsMovements.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(150);
			if(a == 1)
				column.setPreferredWidth(25);
			if(a == 2)
				column.setPreferredWidth(50);
			if(a == 3)
				column.setPreferredWidth(25);
		}
		
		JScrollPane jspJTPaymentHistory = new JScrollPane(jtClientsMovements);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpCenter.add(jspJTPaymentHistory, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpCenter, gbc);
	}
	
	//Class that RENDERES the JTable
	class RendererJTableClientsMovements extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
				
				String recivedValue = (String) value;
				if(recivedValue.charAt(0) == '+') {
					cell.setForeground(Color.GREEN);
				} else if(recivedValue.charAt(0) == '-') {
					cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
					cell.setForeground(Color.RED);
				} else {
					cell.setFont(new Font("Liberation Sans", Font.PLAIN, 12));	
					cell.setForeground(Color.BLACK);
				}
					
			}
			
			return cell;
		}
	}
}
