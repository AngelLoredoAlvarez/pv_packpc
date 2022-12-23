package views;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JDDetailsSB extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfSearch;
	public JButton jbtnExit;
	public DefaultTableModel dtmDetailsBuy;
	public JTable jtDetailsBuy;
	public TableRowSorter<TableModel> trsSorter;

	//Constructor
	public JDDetailsSB(Window owner, String title) {
		super(owner);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage("");
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
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
		//This has to be at the END
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel North
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JLabel jlblIcon = new JLabel();
		jlblIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/search.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jlblIcon, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField();
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfSearch.requestFocus();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpNorth.add(jtxtfSearch, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jbtnExit, gbc);
		
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
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmDetailsBuy = new DefaultTableModel();
		dtmDetailsBuy.addColumn("Descripción");
		dtmDetailsBuy.addColumn("Cantidad");
		dtmDetailsBuy.addColumn("Importe");
		
		jtDetailsBuy = new JTable() {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtDetailsBuy.setModel(dtmDetailsBuy);
		jtDetailsBuy.setRowHeight(20);
		jtDetailsBuy.setRowSelectionAllowed(false);
		jtDetailsBuy.setDefaultRenderer(Object.class, new RendererJTableClientsMovements());
		
		trsSorter = new TableRowSorter<TableModel>(jtDetailsBuy.getModel());
		jtDetailsBuy.setRowSorter(trsSorter);
		
		JTableHeader jthDetailsBuy = new JTableHeader();
		jthDetailsBuy = jtDetailsBuy.getTableHeader();
		jthDetailsBuy.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		TableColumn column = null;
		for(int a = 0; a < jtDetailsBuy.getColumnCount(); a++) {
			column = jtDetailsBuy.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(250);
			if(a == 1)
				column.setPreferredWidth(50);
			if(a == 2)
				column.setPreferredWidth(50);
		}
		
		JScrollPane jspJTDetailsBuy = new JScrollPane(jtDetailsBuy);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jspJTDetailsBuy, gbc);
		
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
	//Class that RENDERES the JTable
	class RendererJTableClientsMovements extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			cell.setHorizontalAlignment(SwingConstants.CENTER);
			
			cell.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
			
			return cell;
		}
	}
}
