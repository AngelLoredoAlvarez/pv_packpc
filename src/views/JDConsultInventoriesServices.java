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
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JDConsultInventoriesServices extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private Font fjlblText;
	private Font fjlblData;
	public JLabel jlblInventoryCost, jlblTotalPieceProducts, jlblTotalGranelProducts;
	public JComboBox<String> jcbWhatToShow, jcbDepartments;
	public JRadioButton jrbBarCode, jrbDescription;
	public JTextField jtxtfSearch;
	public JButton jbtnExit, jbtnNewProductService, jbtnModifyProductService, jbtnDelete, jbtnAdjustInventory;
	public JButton jbtnGenerateSpreadsheet, jbtnGenerateReport, jbtnPrintInventories, jbtnInventoryMovements;
	private Font fJButtons;
	public DefaultTableModel dtmInventoriesServices;
	public TableRowSorter<TableModel> trsSorter;
	public JTable jtInventories;
	private double minStock = 0.0, actualStock = 0.0;
	public String nameSheet = "";
	public ProgressMonitor pmAction;
	public String father = "";
	
	//Constructor
	public JDConsultInventoriesServices(Window owner, String father) {
		super(owner);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/inventories.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Inventarios y Servicios");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1152, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
		this.father = father;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fjlblText = new Font("Liberation Sans", Font.BOLD, 18);
		fjlblData = new Font("Liberation Sans", Font.PLAIN, 34);
		fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		//GUI
		this.setJPMain();
		this.setJPCostQuantities();
		this.setJPActions();
		this.setJPCenter();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel North
	private void setJPCostQuantities() {
		JPanel jpCostQuantities = new JPanel();
		jpCostQuantities.setLayout(gbl);
		
		JPanel jpInventoryCost = new JPanel();
		jpInventoryCost = this.returnJPInventoryCost();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCostQuantities.add(jpInventoryCost, gbc);
		
		JPanel jpTotalPieceProducts = new JPanel();
		jpTotalPieceProducts = this.returnJPTotalPieceProducts();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCostQuantities.add(jpTotalPieceProducts, gbc);
		
		JPanel jpTotalGranelProducts = new JPanel();
		jpTotalGranelProducts = this.returnJPTotalGranelProducts();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCostQuantities.add(jpTotalGranelProducts, gbc);
		
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
		jpMain.add(jpCostQuantities, gbc);
	}
	private JPanel returnJPInventoryCost() {
		JPanel returnJPInventoryCost = new JPanel();
		returnJPInventoryCost.setLayout(gbl);
		
		JLabel jlbltInventoryCost = new JLabel();
		jlbltInventoryCost.setText("Costo del Inventario:");
		jlbltInventoryCost.setFont(fjlblText);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPInventoryCost.add(jlbltInventoryCost, gbc);
		
		jlblInventoryCost = new JLabel();
		jlblInventoryCost.setFont(fjlblData);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPInventoryCost.add(jlblInventoryCost, gbc);
		
		return returnJPInventoryCost;
	}
	private JPanel returnJPTotalPieceProducts() {
		JPanel returnJPTotalPieceProducts = new JPanel();
		returnJPTotalPieceProducts.setLayout(gbl);
		
		JLabel jlbltTotalPieceProducts = new JLabel();
		jlbltTotalPieceProducts.setText("Total de Productos por Pieza:");
		jlbltTotalPieceProducts.setFont(fjlblText);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalPieceProducts.add(jlbltTotalPieceProducts, gbc);
		
		jlblTotalPieceProducts = new JLabel();
		jlblTotalPieceProducts.setFont(fjlblData);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalPieceProducts.add(jlblTotalPieceProducts, gbc);
		
		return returnJPTotalPieceProducts;
	}
	private JPanel returnJPTotalGranelProducts() {
		JPanel returnJPTotalGranelProducts = new JPanel();
		returnJPTotalGranelProducts.setLayout(gbl);
		
		JLabel jlbltTotalGranelProducts = new JLabel();
		jlbltTotalGranelProducts.setText("Total de Productos en Granel:");
		jlbltTotalGranelProducts.setFont(fjlblText);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalGranelProducts.add(jlbltTotalGranelProducts, gbc);
		
		jlblTotalGranelProducts = new JLabel();
		jlblTotalGranelProducts.setFont(fjlblData);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTotalGranelProducts.add(jlblTotalGranelProducts, gbc);
		
		return returnJPTotalGranelProducts;
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(gbl);
		
		JPanel jpWhatToShow = new JPanel();
		jpWhatToShow = this.returnJPWhatToShow();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpActions.add(jpWhatToShow, gbc);
		
		JPanel jpFilterByDepartment = new JPanel();
		jpFilterByDepartment = this.returnJPFilterByDepartment();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpActions.add(jpFilterByDepartment, gbc);
		
		JPanel jpFilter = new JPanel();
		jpFilter = this.returnJPFilter();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpActions.add(jpFilter, gbc);
		
		JPanel jpActionsInventories = new JPanel();
		jpActionsInventories = this.returnJPActionsInventories();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpActions.add(jpActionsInventories, gbc);
		
		JPanel jpReportsInventories = new JPanel();
		jpReportsInventories = this.returnJPReportsInventories();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpActions.add(jpReportsInventories, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	private JPanel returnJPWhatToShow() {
		JPanel returnJPWhatToShow = new JPanel();
		returnJPWhatToShow.setLayout(gbl);
		
		JLabel jlbltWhatToShow = new JLabel();
		jlbltWhatToShow.setText("Mostrar:");
		jlbltWhatToShow.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 3, 0, 0);
		returnJPWhatToShow.add(jlbltWhatToShow, gbc);
		
		String[] whatToShow = {"Todo", 
				               "Productos en Inventario",
				               "Productos por Agotarse",
				               "Todos los Servicios"};
		jcbWhatToShow = new JComboBox<String>(whatToShow);
		jcbWhatToShow.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jcbWhatToShow.setSelectedIndex(0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPWhatToShow.add(jcbWhatToShow, gbc);
		
		return returnJPWhatToShow;
	}
	private JPanel returnJPFilterByDepartment() {
		JPanel returnJPFilterByDepartment = new JPanel();
		returnJPFilterByDepartment.setLayout(gbl);
		
		JLabel jlbltDepartment = new JLabel();
		jlbltDepartment.setText("Departamento:");
		jlbltDepartment.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDepartment.add(jlbltDepartment, gbc);
		
		jcbDepartments = new JComboBox<String>();
		jcbDepartments.addItem("Todos");
		jcbDepartments.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jcbDepartments.setSelectedIndex(0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterByDepartment.add(jcbDepartments, gbc);
		
		return returnJPFilterByDepartment;
	}
	private JPanel returnJPFilter() {
		JPanel returnJPFilter = new JPanel();
		returnJPFilter.setLayout(gbl);
		
		JPanel jpFilterBy = new JPanel();
		jpFilterBy = this.returnJPFilterBy();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jpFilterBy, gbc);
		
		JLabel jlbliSearch = new JLabel();
		jlbliSearch.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/search.png")));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jlbliSearch, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField();
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		jtxtfSearch.requestFocus();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		returnJPFilter.add(jtxtfSearch, gbc);
		
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
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		returnJPFilter.add(jbtnExit, gbc);
		
		return returnJPFilter;
	}
	private JPanel returnJPFilterBy() {
		JPanel returnJPFilterBy = new JPanel();
		returnJPFilterBy.setLayout(gbl);
		returnJPFilterBy.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Búsqueda por:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 14)
			)
		);
		
		Font fJRadioButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jrbBarCode = new JRadioButton();
		jrbBarCode.setText("Código de Barras");
		jrbBarCode.setFont(fJRadioButtons);
		jrbBarCode.setFocusable(false);
		jrbBarCode.setSelected(true);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterBy.add(jrbBarCode, gbc);
		
		jrbDescription = new JRadioButton();
		jrbDescription.setText("Descripción");
		jrbDescription.setFont(fJRadioButtons);
		jrbDescription.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterBy.add(jrbDescription, gbc);
		
		ButtonGroup bgFilter = new ButtonGroup();
		bgFilter.add(jrbBarCode);
		bgFilter.add(jrbDescription);
		
		return returnJPFilterBy;
	}
	private JPanel returnJPActionsInventories() {
		JPanel returnJPActionsInventories = new JPanel();
		returnJPActionsInventories.setLayout(gbl);
		
		JPanel jpSpaceLeft = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActionsInventories.add(jpSpaceLeft, gbc);
		
		jbtnNewProductService = new JButton();
		jbtnNewProductService.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add.png")));
		jbtnNewProductService.setText("Nuevo");
		jbtnNewProductService.setFont(fJButtons);
		jbtnNewProductService.setFocusable(false);
		jbtnNewProductService.setVisible(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPActionsInventories.add(jbtnNewProductService, gbc);
		
		jbtnModifyProductService = new JButton();
		jbtnModifyProductService.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/edit.png")));
		jbtnModifyProductService.setText("Modificar");
		jbtnModifyProductService.setFont(fJButtons);
		jbtnModifyProductService.setFocusable(false);
		jbtnModifyProductService.setEnabled(false);
		jbtnModifyProductService.setVisible(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPActionsInventories.add(jbtnModifyProductService, gbc);
		
		jbtnDelete = new JButton();
		jbtnDelete.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnDelete.setText("Eliminar");
		jbtnDelete.setFont(fJButtons);
		jbtnDelete.setFocusable(false);
		jbtnDelete.setEnabled(false);
		jbtnDelete.setVisible(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPActionsInventories.add(jbtnDelete, gbc);
		
		jbtnAdjustInventory = new JButton();
		jbtnAdjustInventory.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/adjust_inventory.png")));
		jbtnAdjustInventory.setText("Ajustar Inventario");
		jbtnAdjustInventory.setFont(fJButtons);
		jbtnAdjustInventory.setFocusable(false);
		jbtnAdjustInventory.setVisible(false);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActionsInventories.add(jbtnAdjustInventory, gbc);
		
		JPanel jpSpaceRight = new JPanel();
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPActionsInventories.add(jpSpaceRight, gbc);
		
		return returnJPActionsInventories;
	}
	private JPanel returnJPReportsInventories() {
		JPanel returnJPReportsInventories = new JPanel();
		returnJPReportsInventories.setLayout(gbl);
		
		JPanel jpSpaceLeft = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPReportsInventories.add(jpSpaceLeft, gbc);
		
		jbtnGenerateSpreadsheet = new JButton();
		jbtnGenerateSpreadsheet.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/spreadsheet.png")));
		jbtnGenerateSpreadsheet.setText("Generar Hoja de Cálculo");
		jbtnGenerateSpreadsheet.setFont(fJButtons);
		jbtnGenerateSpreadsheet.setFocusable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPReportsInventories.add(jbtnGenerateSpreadsheet, gbc);
	
		jbtnGenerateReport = new JButton();
		jbtnGenerateReport.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/report.png")));
		jbtnGenerateReport.setText("Generar Reporte");
		jbtnGenerateReport.setFont(fJButtons);
		jbtnGenerateReport.setFocusable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPReportsInventories.add(jbtnGenerateReport, gbc);
		
		jbtnPrintInventories = new JButton();
		jbtnPrintInventories.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/printer.png")));
		jbtnPrintInventories.setText("Imprimir Inventarios");
		jbtnPrintInventories.setFont(fJButtons);
		jbtnPrintInventories.setFocusable(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 2);
		returnJPReportsInventories.add(jbtnPrintInventories, gbc);
		
		jbtnInventoryMovements = new JButton();
		jbtnInventoryMovements.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/inventory_movements.png")));
		jbtnInventoryMovements.setText("Movimientos en Inventarios");
		jbtnInventoryMovements.setFont(fJButtons);
		jbtnInventoryMovements.setFocusable(false);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPReportsInventories.add(jbtnInventoryMovements, gbc);
		
		JPanel jpSpaceRight = new JPanel();
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPReportsInventories.add(jpSpaceRight, gbc);
		
		return returnJPReportsInventories;
	}
	public String[] retriveDataForSaveSpreadSheet() {
		String[] dataForSave = null;
		dataForSave = new String[2];
		JFileChooser jfcPath = new JFileChooser();
		jfcPath.setDialogTitle("Guardar como...");
		jfcPath.setSelectedFile(new File(nameSheet));
		jfcPath.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcPath.setAcceptAllFileFilterUsed(false);
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Libro de Excel", "xlsx"));
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003", "xls"));
		int action = jfcPath.showSaveDialog(this);
		
		if(action == JFileChooser.APPROVE_OPTION) {
			dataForSave[0] = jfcPath.getSelectedFile().getAbsolutePath();
			if(jfcPath.getFileFilter() instanceof FileNameExtensionFilter) {
				String[] extensions = ((FileNameExtensionFilter)jfcPath.getFileFilter()).getExtensions();
				for (String ext : extensions) {
		          dataForSave[1] = ext;
		        }
			}
		} else {
			dataForSave = null;
		}
		
		return dataForSave;
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		dtmInventoriesServices = new DefaultTableModel();
		dtmInventoriesServices.addColumn("Código de Barras");
		dtmInventoriesServices.addColumn("Descrición");
		dtmInventoriesServices.addColumn("Und. de Medida");
		dtmInventoriesServices.addColumn("Departamento");
		dtmInventoriesServices.addColumn("Precio Compra");
		dtmInventoriesServices.addColumn("Precio Venta");
		dtmInventoriesServices.addColumn("Stock Mínimo");
		dtmInventoriesServices.addColumn("Stock Actual");
		
		jtInventories = new JTable() {
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtInventories.setModel(dtmInventoriesServices);
		jtInventories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtInventories.setRowHeight(20);
		jtInventories.setDefaultRenderer(Object.class, new RendererJTableInventories());
		
		trsSorter = new TableRowSorter<TableModel>(jtInventories.getModel());
		jtInventories.setRowSorter(trsSorter);
		
		JTableHeader jthInventories = new JTableHeader();
		jthInventories = jtInventories.getTableHeader();
		jthInventories.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		
		TableColumn column = null;
		for(int a = 0; a < jtInventories.getColumnCount(); a++) {
			column = jtInventories.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(55);
			if(a == 1)
				column.setPreferredWidth(180);
			if(a == 2)
				column.setPreferredWidth(45);
			if(a == 3)
				column.setPreferredWidth(30);
			if(a == 4)
				column.setPreferredWidth(35);
			if(a == 5)
				column.setPreferredWidth(20);
			if(a == 6)
				column.setPreferredWidth(25);
			if(a == 7)
				column.setPreferredWidth(25);
		}
		
		JScrollPane jspJTInventories = new JScrollPane(jtInventories);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jspJTInventories, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpMain.add(jpCenter, gbc);
	}
	
	//Class that RENDERES the JTable
	class RendererJTableInventories extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(column == 5)
					cell.setForeground(Color.GREEN);
				else
					cell.setForeground(Color.BLACK);
				
				if(column == 6) {
					if(!value.equals(""))
						minStock = Double.parseDouble(value.toString());
					
				}
				else if(column == 7) {
					if(!value.equals("")) {
						actualStock = Double.parseDouble(value.toString().replace(",", ""));
						if(minStock > actualStock) {
							cell.setForeground(Color.RED);
							cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
						} else if(minStock == actualStock) {
							cell.setForeground(Color.ORANGE);
							cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
						}
					}
				}	
				
				if(isSelected) {
					cell.setForeground(Color.WHITE);
				} else {
					if(column == 5)
						cell.setForeground(Color.GREEN);
					else
						cell.setForeground(Color.BLACK);
					
					if(column == 6) {
						if(!value.equals(""))
							minStock = Double.parseDouble(value.toString());
					}
					else if(column == 7) {
						if(!value.equals("")) {
							actualStock = Double.parseDouble(value.toString().replace(",", ""));
							if(minStock > actualStock) {
								cell.setForeground(Color.RED);
								cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
							} else if(minStock == actualStock) {
								cell.setForeground(Color.ORANGE);
								cell.setFont(new Font("Liberation Sans", Font.BOLD, 12));
							}
						}
					}	
				}
			}
			
			return cell;
		}
	}
	
	//Method that SETS the Messages for the User
	public int setJOPMessages(String typeMessage, String message) {
		int response = 0;
		
		if(typeMessage.equals("delete"))
			response = JOptionPane.showConfirmDialog(this, message, "¿Eliminar Producto?", JOptionPane.YES_NO_OPTION);
		else if(typeMessage.equals("fileExist"))
			response = JOptionPane.showConfirmDialog(this, message, "¿Reemplazar Archivo?", JOptionPane.YES_NO_OPTION);
		else if(typeMessage.equals("printingOK"))
			JOptionPane.showMessageDialog(this, message, "Estado de Impresión", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("printingCancel"))
			JOptionPane.showMessageDialog(this, message, "Impresión Cancelada", JOptionPane.INFORMATION_MESSAGE);
		
		return response;
	}
}
