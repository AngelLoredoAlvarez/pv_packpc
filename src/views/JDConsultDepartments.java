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

public class JDConsultDepartments extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfSearch;
	public JButton jbtnAdd, jbtnModify, jbtnDelete, jbtnExit;
	public DefaultTableModel dtmDepartments;
	public JTable jtDepartments;
	public TableRowSorter<TableModel> trsSorter;
	
	//Constructor
	public JDConsultDepartments(JDActionProductService jdActionProductService) {
		super(jdActionProductService);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage("/img/jframes/departments.png");
		this.setIconImage(jdIcon);
		this.setTitle("Departamentos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(385, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(jdActionProductService);
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
	
	//Method that SETS the JPanel NORTH
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JPanel jpSearch = this.returnJPSearch();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jpSearch, gbc);
		
		JPanel jpActions = this.returnJPActions();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
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
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpNorth, gbc);
	}
	
	//Method that RETURNS the JPanel Search
	private JPanel returnJPSearch() {
		JPanel returnJPSearch = new JPanel();
		returnJPSearch.setLayout(gbl);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPSearch.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField();
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfSearch.requestFocus();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPSearch.add(jtxtfSearch, gbc);
		
		return returnJPSearch;
	}
	
	//Method that RETURNS the JPanel Actions
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
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jpSpaceA, gbc);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnAdd = new JButton();
		jbtnAdd.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add.png")));
		jbtnAdd.setText("Agregar");
		jbtnAdd.setFont(fJButtons);
		jbtnAdd.setVisible(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jbtnAdd, gbc);
		
		jbtnModify = new JButton();
		jbtnModify.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/edit.png")));
		jbtnModify.setText("Modificar");
		jbtnModify.setFont(fJButtons);
		jbtnModify.setVisible(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jbtnModify, gbc);
		
		jbtnDelete = new JButton();
		jbtnDelete.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnDelete.setText("Eliminar");
		jbtnDelete.setFont(fJButtons);
		jbtnDelete.setVisible(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jbtnDelete, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(fJButtons);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jbtnExit, gbc);
		
		return returnJPActions;
	}
	
	//Method that SETS then JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmDepartments = new DefaultTableModel();
		dtmDepartments.addColumn("No.");
		dtmDepartments.addColumn("Departamento");
		
		jtDepartments = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtDepartments.setModel(dtmDepartments);
		jtDepartments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtDepartments.setRowHeight(20);
		jtDepartments.setDefaultRenderer(Object.class, new RendererJTableDepartments());
		
		trsSorter = new TableRowSorter<TableModel>(jtDepartments.getModel());
		jtDepartments.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthDepartments = new JTableHeader();
		jthDepartments = jtDepartments.getTableHeader();
		jthDepartments.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns WIDTH
		TableColumn column = null;
		for(int a = 0; a < jtDepartments.getColumnCount(); a++) {
			column = jtDepartments.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			else if(a == 1)
				column.setPreferredWidth(200);
		}
		
		JScrollPane jspJTDepartments = new JScrollPane(jtDepartments);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpCenter.add(jspJTDepartments, gbc);
		
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
	class RendererJTableDepartments extends DefaultTableCellRenderer {
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
	
	//Method that SETS the Messages for the Cashier
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
