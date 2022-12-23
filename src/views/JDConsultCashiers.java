package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JDConsultCashiers extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfSearch;
	public JButton jbtnAdd, jbtnModify, jbtnDelete, jbtnExit;
	public DefaultTableModel dtmCashiers;
	public JTable jtCashiers;
	public TableRowSorter<TableModel> trsSorter;
	
	//Constructor
	public JDConsultCashiers(JFMain jfSales) {
		super(jfSales);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/search_cashiers.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Consulta de Cajeros");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1152, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPOptions();
		this.setJPCashiers();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPOptions() {
		JPanel jpOptions = new JPanel();
		jpOptions.setLayout(gbl);
		
		JPanel jpSearch = new JPanel();
		jpSearch = this.returnJPSearch();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpOptions.add(jpSearch, gbc);
		
		JPanel jpActions = new JPanel();
		jpActions = this.returnJPActions();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpOptions.add(jpActions, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpOptions, gbc);
	}
	private JPanel returnJPSearch() {
		JPanel returnJPSearch = new JPanel();
		returnJPSearch.setLayout(gbl);
		
		JLabel jlbliSearch = new JLabel();
		jlbliSearch.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/search.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSearch.add(jlbliSearch, gbc);
		
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
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSearch.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField(32);
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfSearch.requestFocus();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSearch.add(jtxtfSearch, gbc);
		
		return returnJPSearch;
	}
	private JPanel returnJPActions() {
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		JPanel jpSpaceA = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jpSpaceA, gbc);
		
		jbtnAdd = new JButton();
		jbtnAdd.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add.png")));
		jbtnAdd.setText("Agregar");
		jbtnAdd.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnAdd.setVisible(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnAdd, gbc);
		
		jbtnModify = new JButton();
		jbtnModify.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/edit.png")));
		jbtnModify.setText("Modificar");
		jbtnModify.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnModify.setEnabled(false);
		jbtnModify.setVisible(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnModify, gbc);
		
		jbtnDelete = new JButton();
		jbtnDelete.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnDelete.setText("Eliminar");
		jbtnDelete.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnDelete.setEnabled(false);
		jbtnDelete.setVisible(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnDelete, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActions.add(jbtnExit, gbc);
		
		return returnJPActions;
	}
	
	//Method that SETS the JPanel Cashiers
	private void setJPCashiers() {
		JPanel jpCashiers = new JPanel();
		jpCashiers.setLayout(gbl);
		
		dtmCashiers = new DefaultTableModel();
		dtmCashiers.addColumn("No.");
		dtmCashiers.addColumn("Nombre");
		dtmCashiers.addColumn("Tel. Fijo");
		dtmCashiers.addColumn("Tel. Celular");
		dtmCashiers.addColumn("E-Mail");
		
		jtCashiers = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtCashiers.setModel(dtmCashiers);
		jtCashiers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtCashiers.setRowHeight(20);
		jtCashiers.setDefaultRenderer(Object.class, new RendererJTableCashiers());
		
		trsSorter = new TableRowSorter<TableModel>(jtCashiers.getModel());
		jtCashiers.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthCashiers = new JTableHeader();
		jthCashiers = jtCashiers.getTableHeader();
		jthCashiers.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtCashiers.getColumnCount(); a++) {
			column = jtCashiers.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			else if(a == 1)
				column.setPreferredWidth(250);
			else if(a == 2)
				column.setPreferredWidth(100);
			else if(a == 3)
				column.setPreferredWidth(100);
			else if(a == 4)
				column.setPreferredWidth(300);
		}
		
		JScrollPane jscJTCashiers = new JScrollPane(jtCashiers);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCashiers.add(jscJTCashiers, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCashiers, gbc);
	}
	class RendererJTableCashiers extends DefaultTableCellRenderer {
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
	public String setJOPMessages(String typeMessage, String message) {
		String answer = "";
		
		if(typeMessage.equals("delete")) {
			int action = JOptionPane.showConfirmDialog(this, message, "¿Eliminar?", JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
				answer = "YES";
		}
		
		return answer;
	}
}
