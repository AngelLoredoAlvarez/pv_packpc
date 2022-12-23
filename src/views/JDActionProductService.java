package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

public class JDActionProductService extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfBarCode, jtxtfDescription, jtxtfJSPBuyPrice, jtxtfJSPSellPrice, jtxtfJSPStock, jtxtfJSPMinStock;
	public JComboBox<String> jcbProvidersList;
	public JLabel jlblDepartment, jlblIDDepartment;
	public JSpinner jspIVA, jspBuyPrice, jspSalePrice, jspStock, jspMinStock;
	public JRadioButton jrbPice, jrbGranel, jrbService;
	public JButton jbtnAddProvider, jbtnRemoveProvider, jbtnSelectDepartment, jbtnSave, jbtnCancel;
	public String action = "", parent = "";
	
	//Constructor
	public JDActionProductService(JDialog jdParent, String icon, String title, String parent) {
		super(jdParent);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon));
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(630, 400));
		this.setResizable(false);
		this.setLocationRelativeTo(jdParent);
		this.action = title;
		this.parent = parent;
		//Initializations needed
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJP0x0();
		this.setJP0x1();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel 0x0
	private void setJP0x0() {
		JPanel jp0x0 = new JPanel();
		jp0x0.setLayout(gbl);
		jp0x0.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Descripción del Producto",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		JPanel jpBarCode = new JPanel();
		jpBarCode = this.returnJPBarCode();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpBarCode, gbc);
		
		JPanel jpDescription = new JPanel();
		jpDescription = this.returnJPDescription();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpDescription, gbc);
		
		JPanel jpUnitMeasurement = new JPanel();
		jpUnitMeasurement = this.returnJPUM();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpUnitMeasurement, gbc);
		
		JPanel jpProvider = new JPanel();
		jpProvider = this.returnJPProvider();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpProvider, gbc);
		
		JPanel jpDepartment = new JPanel();
		jpDepartment = this.returnJPDepartment();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpDepartment, gbc);
		
		JPanel jpIVA = new JPanel();
		jpIVA = this.returnJPIVA();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x0.add(jpIVA, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jp0x0, gbc);
	}
	private JPanel returnJPBarCode() {
		JPanel returnJPBarCode = new JPanel();
		returnJPBarCode.setLayout(gbl);
		
		JLabel jlbltBarCode = new JLabel();
		jlbltBarCode.setText("Código de Barras:");
		jlbltBarCode.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPBarCode.add(jlbltBarCode, gbc);
		
		jtxtfBarCode = new JTextField(13);
		jtxtfBarCode.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPBarCode.add(jtxtfBarCode, gbc);
		
		JPanel jpSpaceBC = new JPanel();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPBarCode.add(jpSpaceBC, gbc);
		
		return returnJPBarCode;
	}
	private JPanel returnJPDescription() {
		JPanel returnJPDescription = new JPanel();
		returnJPDescription.setLayout(gbl);
		
		JLabel jlbltDescription = new JLabel();
		jlbltDescription.setText("Descripción:");
		jlbltDescription.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 45, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDescription.add(jlbltDescription, gbc);
		
		jtxtfDescription = new JTextField(25);
		jtxtfDescription.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDescription.add(jtxtfDescription, gbc);
		
		JPanel jpSpaceD = new JPanel();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDescription.add(jpSpaceD, gbc);
		
		 return returnJPDescription;
	}
	private JPanel returnJPUM() {
		JPanel jpUM = new JPanel();
		jpUM.setLayout(gbl);
		
		JLabel jlbltUnitMeasurement = new JLabel();
		jlbltUnitMeasurement.setText("Se vende:");
		jlbltUnitMeasurement.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 63, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		jpUM.add(jlbltUnitMeasurement, gbc);
		
		jrbPice = new JRadioButton();
		jrbPice.setText("Por Unidad/Pieza");
		jrbPice.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jrbPice.setSelected(true);
		jrbPice.setFocusable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		jpUM.add(jrbPice, gbc);
		
		jrbGranel = new JRadioButton();
		jrbGranel.setText("A Granel (usa decimales)");
		jrbGranel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jrbGranel.setFocusable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		jpUM.add(jrbGranel, gbc);
		
		jrbService = new JRadioButton();
		jrbService.setText("Servicio");
		jrbService.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jrbService.setFocusable(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpUM.add(jrbService, gbc);
		
		ButtonGroup bgUM = new ButtonGroup();
		bgUM.add(jrbPice);
		bgUM.add(jrbGranel);
		bgUM.add(jrbService);
		
		JPanel jpSpaceUM = new JPanel();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpUM.add(jpSpaceUM, gbc);
		
		return jpUM;
	}
	private JPanel returnJPProvider() {
		JPanel returnJPProvider = new JPanel();
		returnJPProvider.setLayout(gbl);
		
		JLabel jlbltProvider = new JLabel();
		jlbltProvider.setText("Proveedor(es):");
		jlbltProvider.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 22, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPProvider.add(jlbltProvider, gbc);
		
		jcbProvidersList = new JComboBox<String>();
		jcbProvidersList.addItem("Proveedores Varios");
		jcbProvidersList.setSelectedIndex(0);
		jcbProvidersList.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPProvider.add(jcbProvidersList, gbc);
		
		jbtnAddProvider = new JButton();
		jbtnAddProvider.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/add.png")));
		jbtnAddProvider.setText("Agregar Proveedor");
		jbtnAddProvider.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPProvider.add(jbtnAddProvider, gbc);
		
		jbtnRemoveProvider = new JButton();
		jbtnRemoveProvider.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/delete.png")));
		jbtnRemoveProvider.setText("Quitar Proveedor");
		jbtnRemoveProvider.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnRemoveProvider.setEnabled(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPProvider.add(jbtnRemoveProvider, gbc);
		
		JPanel jpSpaceP = new JPanel();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPProvider.add(jpSpaceP, gbc);
		
		return returnJPProvider;
	}
	private JPanel returnJPDepartment() {
		JPanel returnJPDepartment = new JPanel();
		returnJPDepartment.setLayout(gbl);
		
		JLabel jlbltDepartment = new JLabel();
		jlbltDepartment.setText("Departamento:");
		jlbltDepartment.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 22, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDepartment.add(jlbltDepartment, gbc);
		
		jlblIDDepartment = new JLabel();
		jlblIDDepartment.setText("1");
		jlblIDDepartment.setVisible(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDepartment.add(jlblIDDepartment, gbc);
		
		jlblDepartment = new JLabel();
		jlblDepartment.setText("Sin Departamento");
		jlblDepartment.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDepartment.add(jlblDepartment, gbc);
		
		jbtnSelectDepartment = new JButton();
		jbtnSelectDepartment.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/search.png")));
		jbtnSelectDepartment.setText("Seleccionar Departamento");
		jbtnSelectDepartment.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDepartment.add(jbtnSelectDepartment, gbc);
		
		JPanel jpSpaceD = new JPanel();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPDepartment.add(jpSpaceD, gbc);
		
		return returnJPDepartment;
	}
	private JPanel returnJPIVA() {
		JPanel returnJPIVA = new JPanel();
		returnJPIVA.setLayout(gbl);
		
		JLabel jlbltIVA = new JLabel();
		jlbltIVA.setText("I.V.A.:");
		jlbltIVA.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 100, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPIVA.add(jlbltIVA, gbc);
		
		SpinnerModel smIVA = new SpinnerNumberModel(16, 0, 16, 1);
		jspIVA = new JSpinner();
		jspIVA.setModel(smIVA);
		JSpinner.NumberEditor jspneIVA = new JSpinner.NumberEditor(jspIVA, "0");
		jspIVA.setEditor(jspneIVA);
		jspIVA.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPIVA.add(jspIVA, gbc);
		
		JLabel jlbltPercentage = new JLabel();
		jlbltPercentage.setText("%");
		jlbltPercentage.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPIVA.add(jlbltPercentage, gbc);
		
		JPanel jpSpaceIVA = new JPanel();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPIVA.add(jpSpaceIVA, gbc);
		
		return returnJPIVA;
	}
	
	//Method that SETS the JPanel 0x1
	private void setJP0x1() {
		JPanel jp0x1 = new JPanel();
		jp0x1.setLayout(gbl);
		
		JPanel jpPrices = new JPanel();
		jpPrices = this.returnJPPrices();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x1.add(jpPrices, gbc);
		
		JPanel jpInventory = new JPanel();
		jpInventory = this.returnJPInventory();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jp0x1.add(jpInventory, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jp0x1, gbc);
	}
	//Method that RETURNS the JPanel Prices
	private JPanel returnJPPrices() {
		JPanel returnJPPrices = new JPanel();
		returnJPPrices.setLayout(gbl);
		returnJPPrices.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Precios",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		JLabel jlbltBuyPrice = new JLabel();
		jlbltBuyPrice.setText("Precio Compra: $");
		jlbltBuyPrice.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPPrices.add(jlbltBuyPrice, gbc);
		
		SpinnerModel snmBuyPrice = new SpinnerNumberModel(0.00, 0.00, 10000, 0.01);
		jspBuyPrice = new JSpinner();
		jspBuyPrice.setModel(snmBuyPrice);
		JSpinner.NumberEditor jspneBuyPrice = new JSpinner.NumberEditor(jspBuyPrice, "###,##0.00");
		jspBuyPrice.setEditor(jspneBuyPrice);
		jspBuyPrice.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPPrices.add(jspBuyPrice, gbc);
		
		JSpinner.DefaultEditor editorJSPBuyPrice = (JSpinner.DefaultEditor)jspBuyPrice.getEditor();
		jtxtfJSPBuyPrice = editorJSPBuyPrice.getTextField();
		
		JLabel jlbltSellPrice = new JLabel();
		jlbltSellPrice.setText("Precio Venta: $");
		jlbltSellPrice.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPPrices.add(jlbltSellPrice, gbc);
		
		SpinnerModel snmSellPrice = new SpinnerNumberModel(0.00, 0.00, 10000, 0.10);
		jspSalePrice = new JSpinner();
		jspSalePrice.setModel(snmSellPrice);
		JSpinner.NumberEditor jspneSellPrice = new JSpinner.NumberEditor(jspSalePrice, "###,##0.00");
		jspSalePrice.setEditor(jspneSellPrice);
		jspSalePrice.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPPrices.add(jspSalePrice, gbc);
		
		JSpinner.DefaultEditor editorJSPSellPrice = (JSpinner.DefaultEditor)jspSalePrice.getEditor();
		jtxtfJSPSellPrice = editorJSPSellPrice.getTextField();
		
		return returnJPPrices;
	}
	//Method that RETURNS the JPanel Inventories
	private JPanel returnJPInventory() {
		JPanel returnJPInventory = new JPanel();
		returnJPInventory.setLayout(gbl);
		returnJPInventory.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Inventario",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("LiberationSerif", Font.BOLD, 18)
			)
		);
		
		JLabel jlbltStock = new JLabel();
		jlbltStock.setText("Stock:");
		jlbltStock.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInventory.add(jlbltStock, gbc);
		
		SpinnerModel snmStock = new SpinnerNumberModel(0.00, 0.00, 10000.00, 1.00);
		jspStock = new JSpinner();
		jspStock.setModel(snmStock);
		JSpinner.NumberEditor jspneStock = new JSpinner.NumberEditor(jspStock, "###,##0.00");
		jspStock.setEditor(jspneStock);
		jspStock.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInventory.add(jspStock, gbc);
		
		JSpinner.DefaultEditor editorJSPStock = (JSpinner.DefaultEditor)jspStock.getEditor();
		jtxtfJSPStock = editorJSPStock.getTextField();
		
		JLabel jlbltMinStock = new JLabel();
		jlbltMinStock.setText("Stock Mínimo:");
		jlbltMinStock.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInventory.add(jlbltMinStock, gbc);
		
		SpinnerModel snmMinStock = new SpinnerNumberModel(0.00, 0.00, 10000.00, 1.00);
		jspMinStock = new JSpinner();
		jspMinStock.setModel(snmMinStock);
		JSpinner.NumberEditor jspneMinStock = new JSpinner.NumberEditor(jspMinStock, "###,##0.00");
		jspMinStock.setEditor(jspneMinStock);
		jspMinStock.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInventory.add(jspMinStock, gbc);
		
		JSpinner.DefaultEditor editorJSPMinStock = (JSpinner.DefaultEditor)jspMinStock.getEditor();
		jtxtfJSPMinStock = editorJSPMinStock.getTextField();
		
		//END
		return returnJPInventory;
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnSave = new JButton();
		jbtnSave.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnSave.setText("Guardar");
		jbtnSave.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpActions.add(jbtnSave);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpActions.add(jbtnCancel);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public String setJOPMessages(String typeMessage, String message) {
		int action;
		String answer = "";
		
		if(typeMessage.equals("barcode")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("description")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("buyprice")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("sellprice")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("stock")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("minstock")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("exist")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("barcodeExist")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("descriptionExist")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("biggerPrice")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("biggerMinStock")) 
			JOptionPane.showMessageDialog(this, message, "Control de Productos", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("imageSelected")) 
			JOptionPane.showMessageDialog(this, message, "Selector de Imagen", JOptionPane.ERROR_MESSAGE);
		else if(typeMessage.equals("actionCorrectly")) 
			JOptionPane.showMessageDialog(this, message, "Operacion Exitosa", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("another")) {
			action = JOptionPane.showConfirmDialog(this, message, "Control de Inventarios", JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
				answer = "YES";
			else
				answer = "NO";
		}
		
		return answer;
	}
}
