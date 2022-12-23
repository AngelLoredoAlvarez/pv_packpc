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
import javax.swing.JRadioButton;
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

public class JDConsultClients extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfSearch;
	public JRadioButton jrbName, jrbFirstName, jrbLastName;
	public JButton jbtnAdd, jbtnModify, jbtnDelete, jbtnCreditStatus, jbtnExit;
	public DefaultTableModel dtmClients;
	public JTable jtClients;
	public TableRowSorter<TableModel> trsSorter;
	public String father = "";
	
	//Constructor
	public JDConsultClients(Window owner, String father) {
		super(owner);
		this.setModal(true);
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/search_clients.png"));
		this.setIconImage(icon);
		this.setTitle("Consulta de Clientes");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1200, 600);
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
	//Methods that SETS the JPanel North
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JPanel jpSearchField = new JPanel();
		jpSearchField = this.returnJPSearchField();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jpSearchField, gbc);
		
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
		
		//END
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
	private JPanel returnJPSearchField() {
		JPanel returnJPSearchField = new JPanel();
		returnJPSearchField.setLayout(gbl);
		
		JLabel jlblIconSearch = new JLabel();
		jlblIconSearch.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/search.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSearchField.add(jlblIconSearch, gbc);
		
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
		returnJPSearchField.add(jlbltSearch, gbc);
		
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
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPSearchField.add(jtxtfSearch, gbc);
		
		return returnJPSearchField;
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
		
		jbtnCreditStatus = new JButton();
		jbtnCreditStatus.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/about_credit.png")));
		jbtnCreditStatus.setText("Estado Crediticio");
		jbtnCreditStatus.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnCreditStatus.setVisible(false);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 1);
		returnJPActions.add(jbtnCreditStatus, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 5;
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
		
		dtmClients = new DefaultTableModel();
		dtmClients.addColumn("No.");
		dtmClients.addColumn("R.F.C.");
		dtmClients.addColumn("Cliente");
		dtmClients.addColumn("Dirección");
		dtmClients.addColumn("Tel. Fijo");
		dtmClients.addColumn("Tel. Celular");
		dtmClients.addColumn("E-Mail");
		
		jtClients = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtClients.setModel(dtmClients);
		jtClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtClients.setRowHeight(20);
		jtClients.setDefaultRenderer(Object.class, new RendererJTableClients());
		
		trsSorter = new TableRowSorter<TableModel>(jtClients.getModel());
		jtClients.setRowSorter(trsSorter);
		
		//Columns FONT
		JTableHeader jthClients = new JTableHeader();
		jthClients = jtClients.getTableHeader();
		jthClients.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		//Columns WIDTH
		TableColumn column = null;
		for(int a = 0; a < jtClients.getColumnCount(); a++) {
			column = jtClients.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(20);
			else if(a == 1)
				column.setPreferredWidth(110);
			else if(a == 2)
				column.setPreferredWidth(225);
			else if(a == 3)
				column.setPreferredWidth(325);
			else if(a == 4)
				column.setPreferredWidth(100);
			else if(a == 5)
				column.setPreferredWidth(100);
			else if(a == 6)
				column.setPreferredWidth(200);
		}
		
		JScrollPane jspJTClients = new JScrollPane(jtClients);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jspJTClients, gbc);
		
		//END
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
	
	public String setJOPMessages(String typeMessage, String message) {
		String answer = "";
		
		if(typeMessage.equals("delete")) {
			int action = JOptionPane.showConfirmDialog(this, message, "¿Eliminar?", JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
				answer = "YES";
		} else if(typeMessage.equals("noSales")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		}
		
	    return answer;
	}
}
