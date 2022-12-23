package views;

import java.awt.Color;
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

public class JDConsultProviders extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfSearch;
	public JButton jbtnAdd, jbtnModify, jbtnDelete, jbtnExit;
	public DefaultTableModel dtmProviders;
	public TableRowSorter<TableModel> trsSorter;
	public JTable jtProviders;
	public String father = "";
	
	//Constructor
	public JDConsultProviders(Window owner, String father) {
		super(owner);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/search.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Consulta de Proveedores");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1152, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
		this.father = father;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//Constructs the GUI
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
		
		JPanel jpSearch = new JPanel();
		jpSearch = this.returnJPSearch();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpSearch, gbc);
		
		JPanel jpActions = new JPanel();
		jpActions = this.returnJPActions();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
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
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpNorth, gbc);
	}
	private JPanel returnJPSearch() {
		JPanel returnJPSearch = new JPanel();
		returnJPSearch.setLayout(gbl);
		
		JLabel jlblIcon = new JLabel();
		jlblIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/search.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSearch.add(jlblIcon, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 18));
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
		jbtnAdd.setFocusable(false);
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
		jbtnModify.setFocusable(false);
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
		jbtnDelete.setFocusable(false);
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
		jbtnExit.setFocusable(false);
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
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmProviders = new DefaultTableModel();
		dtmProviders.addColumn("No.");
		dtmProviders.addColumn("Nombre");
		dtmProviders.addColumn("Dirección");
		dtmProviders.addColumn("No. Contacto 1");
		dtmProviders.addColumn("No. Contacto 2");
		dtmProviders.addColumn("E-Mail");
		
		jtProviders = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtProviders.setModel(dtmProviders);
		jtProviders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtProviders.setRowHeight(20);
		jtProviders.setDefaultRenderer(Object.class, new RendererJTableProviders());
		
		trsSorter = new TableRowSorter<TableModel>(jtProviders.getModel());
		jtProviders.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthProviders = new JTableHeader();
		jthProviders = jtProviders.getTableHeader();
		jthProviders.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns Width
		TableColumn column = null;
		for(int a = 0; a < jtProviders.getColumnCount(); a++) {
			column = jtProviders.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(20);
			else if(a == 1)
				column.setPreferredWidth(230);
			else if(a == 2)
				column.setPreferredWidth(350);
			else if(a == 3)
				column.setPreferredWidth(110);
			else if(a == 4)
				column.setPreferredWidth(110);
			else if(a == 5)
				column.setPreferredWidth(230);
		}
		
		JScrollPane jspJTProviders = new JScrollPane(jtProviders);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jspJTProviders, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpCenter, gbc);
	}
	class RendererJTableProviders extends DefaultTableCellRenderer {
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
	public String setJOPMessages(String type, String message) {
		String answer = "";
		if(type.equals("delete")) {
			int selection = JOptionPane.showConfirmDialog(this, message, "Proveedores", JOptionPane.YES_NO_OPTION);
			if(selection == JOptionPane.YES_OPTION)
				answer = "YES";
			else
				answer = "NO";
		} 
		
		return answer;
	}
}
