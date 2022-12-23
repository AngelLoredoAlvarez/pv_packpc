package views;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

public class JDActionCashier extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private Font fJTextFields = new Font("Liberation Sans", Font.PLAIN, 14);
	public JTextField jtxtfNames, jtxtfFirstName, jtxtfLastName, jtxtfLandLine, jtxtfCelPhone, jtxtfEmail, jtxtfUserName;
	public JTabbedPane jtpPrivileges;
	public ArrayList<JCheckBox> alPrivileges;
	public JCheckBox jchbOpenTill, jchbCashInFlow, jchbCashOutFlow, jchbCourtDay, jchbSeeDayProfit;
	public JCheckBox jchbAMProduct, jchbDelProduct, jchbDoBuys, jchbAdjustInventory;
	public JCheckBox jchbAMDepartments, jchbDelDepartments;
	public JCheckBox jchbAMProvider, jchbDelProvider;
	public JCheckBox jchbAMClient, jchbDelClient, jchbManageClientCredit;
	public JButton jbtnChangePass, jbtnSave, jbtnCancel;
	public String action = "";
	
	//Constructor
	public JDActionCashier(JDConsultCashiers jdConsultCashiers, String sjdIcon, String title) {
		super(jdConsultCashiers);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(sjdIcon));
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(590, 370);
		this.setResizable(false);
		this.setLocationRelativeTo(jdConsultCashiers);
		action = title;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		alPrivileges = new ArrayList<JCheckBox>();
		//GUI
		this.setJPMain();
		this.setJPCashierInfo();
		this.setJPLoginInfo();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Cashier Info
	private void setJPCashierInfo() {
		JPanel jpCashierInfo = new JPanel();
		jpCashierInfo.setLayout(gbl);
		
		JPanel jpPersonalInfo = new JPanel();
		jpPersonalInfo = this.returnJPPersonalInfo();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpCashierInfo.add(jpPersonalInfo, gbc);
		
		JPanel jpContactInfo = new JPanel();
		jpContactInfo = this.returnJPContactInfo();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCashierInfo.add(jpContactInfo, gbc);
		
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
		jpMain.add(jpCashierInfo, gbc);
	}
	private JPanel returnJPPersonalInfo() {
		JPanel returnJPPersonalInfo = new JPanel();
		returnJPPersonalInfo.setLayout(gbl);
		returnJPPersonalInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información Personal:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		jtxtfNames = new JTextField(21);
		jtxtfNames.setFont(fJTextFields);
		jtxtfNames.requestFocus();
		PromptSupport.setPrompt("Nombres", jtxtfNames);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfNames);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPPersonalInfo.add(jtxtfNames, gbc);
		
		jtxtfFirstName = new JTextField(12);
		jtxtfFirstName.setFont(fJTextFields);
		PromptSupport.setPrompt("Apellido Paterno", jtxtfFirstName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfFirstName);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPPersonalInfo.add(jtxtfFirstName, gbc);
		
		jtxtfLastName = new JTextField(12);
		jtxtfLastName.setFont(fJTextFields);
		PromptSupport.setPrompt("Apellido Materno", jtxtfLastName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLastName);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPPersonalInfo.add(jtxtfLastName, gbc);
		
		return returnJPPersonalInfo;
	}
	private JPanel returnJPContactInfo() {
		JPanel returnJPContactInfo = new JPanel();
		returnJPContactInfo.setLayout(gbl);
		returnJPContactInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información de Contacto:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		jtxtfLandLine = new JTextField(10);
		jtxtfLandLine.setFont(fJTextFields);
		jtxtfLandLine.requestFocus();
		PromptSupport.setPrompt("Tel. Fijo", jtxtfLandLine);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLandLine);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfLandLine, gbc);
		
		jtxtfCelPhone = new JTextField(10);
		jtxtfCelPhone.setFont(fJTextFields);
		PromptSupport.setPrompt("Tel. Celular", jtxtfCelPhone);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCelPhone);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfCelPhone, gbc);
		
		jtxtfEmail = new JTextField(25);
		jtxtfEmail.setFont(fJTextFields);
		PromptSupport.setPrompt("Correo Electrónico", jtxtfEmail);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfEmail);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPContactInfo.add(jtxtfEmail, gbc);
		
		return returnJPContactInfo;
	}
	
	//Method that SETS the JPanel Login Info
	private void setJPLoginInfo() {
		JPanel jpLoginInfo = new JPanel();
		jpLoginInfo.setLayout(gbl);
		jpLoginInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información para Sesión de Cajero:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		//Login DATA
		JPanel jpLoginData = new JPanel();
		jpLoginData = this.returnJPLoginData();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpLoginInfo.add(jpLoginData, gbc);
		
		//Privileges
		JPanel jpPrivileges = new JPanel();
		jpPrivileges = this.returnJPPrivileges();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpLoginInfo.add(jpPrivileges, gbc);
		
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
		jpMain.add(jpLoginInfo, gbc);
	}
	//Login DATA
	private JPanel returnJPLoginData() {
		JPanel returnJPLoginData = new JPanel();
		returnJPLoginData.setLayout(gbl);
		
		jtxtfUserName = new JTextField(15);
		jtxtfUserName.setFont(fJTextFields);
		PromptSupport.setPrompt("Nombre de Usuario", jtxtfUserName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfUserName);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPLoginData.add(jtxtfUserName, gbc);
		
		jbtnChangePass = new JButton();
		jbtnChangePass.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/key.png")));
		jbtnChangePass.setText("Solicitar Cambio de Contraseña");
		jbtnChangePass.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnChangePass.setVisible(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPLoginData.add(jbtnChangePass, gbc);
		
		JPanel jpSpaceUserName = new JPanel();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPLoginData.add(jpSpaceUserName, gbc);
		
		return returnJPLoginData;
	}
	private JPanel returnJPPrivileges() {
		JPanel returnJPPriviliges = new JPanel();
		returnJPPriviliges.setLayout(gbl);
		returnJPPriviliges.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Privilegios",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 14)
			)
	    );
		
		jtpPrivileges = new JTabbedPane();
		jtpPrivileges.addTab("Caja", new ImageIcon(getClass().getResource("/img/jtabbedpanes/till.png")), this.returnJPTillPriviliges(), "Privilegios de Caja");
		jtpPrivileges.addTab("Inventarios", new ImageIcon(getClass().getResource("/img/jtabbedpanes/products.png")), this.returnJPInventoriesPriviliges(), "Privilegios para los Productos");
		jtpPrivileges.addTab("Proveedores", new ImageIcon(getClass().getResource("/img/jtabbedpanes/providers.png")), this.returnJPProvidersPriviliges(), "Privilegios para los Proveedores");
		jtpPrivileges.addTab("Clientes", new ImageIcon(getClass().getResource("/img/jtabbedpanes/clients.png")), this.returnJPClientsPriviliges(), "Privilegios para los Clientes");
		jtpPrivileges.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		jtpPrivileges.setSelectedIndex(0);
		jtpPrivileges.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPPriviliges.add(jtpPrivileges, gbc);
		
		return returnJPPriviliges;
	}
	//Method that RETURNS the JPanel Till Privileges
	private JPanel returnJPTillPriviliges() {
		JPanel returnJPTillPriviliges = new JPanel();
		returnJPTillPriviliges.setLayout(gbl);
		
		JPanel jpContainerTillPriviliges = new JPanel();
		jpContainerTillPriviliges.setLayout(gbl);
		
		jchbOpenTill = new JCheckBox();
		jchbOpenTill.setText("Apertura de Caja");
		jchbOpenTill.setFont(fJTextFields);
		jchbOpenTill.setFocusable(false);
		jchbOpenTill.setName("OpenTill");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jchbOpenTill, gbc);
		alPrivileges.add(jchbOpenTill);
		
		jchbCashInFlow = new JCheckBox();
		jchbCashInFlow.setText("Registrar Entradas de Efectivo");
		jchbCashInFlow.setFont(fJTextFields);
		jchbCashInFlow.setFocusable(false);
		jchbCashInFlow.setName("CashInFlow");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jchbCashInFlow, gbc);
		alPrivileges.add(jchbCashInFlow);
		
		jchbCashOutFlow = new JCheckBox();
		jchbCashOutFlow.setText("Registrar Salidas de Efectivo");
		jchbCashOutFlow.setFont(fJTextFields);
		jchbCashOutFlow.setFocusable(false);
		jchbCashOutFlow.setName("CashOutFlow");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jchbCashOutFlow, gbc);
		alPrivileges.add(jchbCashOutFlow);
		
		jchbCourtDay = new JCheckBox();
		jchbCourtDay.setText("Realizar Corte del Día");
		jchbCourtDay.setFont(fJTextFields);
		jchbCourtDay.setFocusable(false);
		jchbCourtDay.setName("CourtDay");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jchbCourtDay, gbc);
		alPrivileges.add(jchbCourtDay);
		
		jchbSeeDayProfit = new JCheckBox();
		jchbSeeDayProfit.setText("Ver la Ganancia del Día");
		jchbSeeDayProfit.setFont(fJTextFields);
		jchbSeeDayProfit.setFocusable(false);
		jchbSeeDayProfit.setName("SeeDayProfit");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jchbSeeDayProfit, gbc);
		alPrivileges.add(jchbSeeDayProfit);
		
		JPanel jpSpaceTillP = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerTillPriviliges.add(jpSpaceTillP, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillPriviliges.add(jpContainerTillPriviliges, gbc);
				
		JPanel jpSRJPTP = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillPriviliges.add(jpSRJPTP, gbc);
		
		return returnJPTillPriviliges;
	}
	//Method that RETURNS the JPanel Products Privileges
	private JPanel returnJPInventoriesPriviliges() {
		JPanel returnJPInventoriesPriviliges = new JPanel();
		returnJPInventoriesPriviliges.setLayout(gbl);
		
		JPanel jpContainerInventoriesP = new JPanel();
		jpContainerInventoriesP.setLayout(gbl);
		
		jchbAMProduct = new JCheckBox();
		jchbAMProduct.setText("Agregar y/o Modificar Productos");
		jchbAMProduct.setFont(fJTextFields);
		jchbAMProduct.setFocusable(false);
		jchbAMProduct.setName("AMProduct");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerInventoriesP.add(jchbAMProduct, gbc);
		alPrivileges.add(jchbAMProduct);
		
		jchbDelProduct = new JCheckBox();
		jchbDelProduct.setText("Eliminar Productos");
		jchbDelProduct.setFont(fJTextFields);
		jchbDelProduct.setFocusable(false);
		jchbDelProduct.setName("DelProduct");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerInventoriesP.add(jchbDelProduct, gbc);
		alPrivileges.add(jchbDelProduct);
		
		jchbDoBuys = new JCheckBox();
		jchbDoBuys.setText("Realizar Compras");
		jchbDoBuys.setFont(fJTextFields);
		jchbDoBuys.setFocusable(false);
		jchbDoBuys.setName("DoBuys");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerInventoriesP.add(jchbDoBuys, gbc);
		alPrivileges.add(jchbDoBuys);
		
		jchbAdjustInventory = new JCheckBox();
		jchbAdjustInventory.setText("Ajustar el Inventario");
		jchbAdjustInventory.setFont(fJTextFields);
		jchbAdjustInventory.setFocusable(false);
		jchbAdjustInventory.setName("AdjustInventory");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerInventoriesP.add(jchbAdjustInventory, gbc);
		alPrivileges.add(jchbAdjustInventory);
		
		JPanel jpSpacePP = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerInventoriesP.add(jpSpacePP, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPInventoriesPriviliges.add(jpContainerInventoriesP, gbc);
		
		JPanel jpSRJPProductsP = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPInventoriesPriviliges.add(jpSRJPProductsP, gbc);
		
		return returnJPInventoriesPriviliges;
	}
	//Method that RETURNS the JPanel Providers Privileges
	private JPanel returnJPProvidersPriviliges() {
		JPanel returnJPProvidersPriviliges = new JPanel();
		returnJPProvidersPriviliges.setLayout(gbl);
		
		JPanel jpContainerProvidersP = new JPanel();
		jpContainerProvidersP.setLayout(gbl);
		
		jchbAMProvider = new JCheckBox();
		jchbAMProvider.setText("Agregar y/o Modificar Proveedores");
		jchbAMProvider.setFont(fJTextFields);
		jchbAMProvider.setFocusable(false);
		jchbAMProvider.setName("AMProvider");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerProvidersP.add(jchbAMProvider, gbc);
		alPrivileges.add(jchbAMProvider);
		
		jchbDelProvider = new JCheckBox();
		jchbDelProvider.setText("Eliminar Proveedores");
		jchbDelProvider.setFont(fJTextFields);
		jchbDelProvider.setFocusable(false);
		jchbDelProvider.setName("DelProvider");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerProvidersP.add(jchbDelProvider, gbc);
		alPrivileges.add(jchbDelProvider);
		
		JPanel jpSpaceProvidersP = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerProvidersP.add(jpSpaceProvidersP, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProvidersPriviliges.add(jpContainerProvidersP, gbc);
				
		JPanel jpSRJPClientsP = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProvidersPriviliges.add(jpSRJPClientsP, gbc);
		
		return returnJPProvidersPriviliges;
	}
	//Method that RETURNS the JPanel Clients Privileges
	private JPanel returnJPClientsPriviliges() {
		JPanel returnJPClientsPriviliges = new JPanel();
		returnJPClientsPriviliges.setLayout(gbl);
		
		JPanel jpContainerClientsP = new JPanel();
		jpContainerClientsP.setLayout(gbl);
		
		jchbAMClient = new JCheckBox();
		jchbAMClient.setText("Agregar y/o Modificar Clientes");
		jchbAMClient.setFont(fJTextFields);
		jchbAMClient.setFocusable(false);
		jchbAMClient.setName("AMClient");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClientsP.add(jchbAMClient, gbc);
		alPrivileges.add(jchbAMClient);
		
		jchbDelClient = new JCheckBox();
		jchbDelClient.setText("Eliminar Clientes");
		jchbDelClient.setFont(fJTextFields);
		jchbDelClient.setFocusable(false);
		jchbDelClient.setName("DelClient");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClientsP.add(jchbDelClient, gbc);
		alPrivileges.add(jchbDelClient);
		
		jchbManageClientCredit = new JCheckBox();
		jchbManageClientCredit.setText("Administrar crédito de Clientes");
		jchbManageClientCredit.setFont(fJTextFields);
		jchbManageClientCredit.setFocusable(false);
		jchbManageClientCredit.setName("ManageClientCredit");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClientsP.add(jchbManageClientCredit, gbc);
		alPrivileges.add(jchbManageClientCredit);
		
		JPanel jpSpaceClientsP = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContainerClientsP.add(jpSpaceClientsP, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPClientsPriviliges.add(jpContainerClientsP, gbc);
		
		JPanel jpSRJPClientsP = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPClientsPriviliges.add(jpSRJPClientsP, gbc);
		
		return returnJPClientsPriviliges;
	}
	
	//Method that set the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnSave = new JButton();
		jbtnSave.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnSave.setText("Guardar");
		jbtnSave.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnSave.setFocusable(false);
		jpActions.add(jbtnSave);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnCancel.setFocusable(false);
		jpActions.add(jbtnCancel);
		
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
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("saveCashier"))
			JOptionPane.showMessageDialog(this, message, "Administrador de Cajeros", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("updateCashier"))
			JOptionPane.showMessageDialog(this, message, "Administrador de Cajeros", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("resetPass"))
			JOptionPane.showMessageDialog(this, message, "Administrador de Cajeros", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("missing"))
			JOptionPane.showMessageDialog(this, message, "Administrador de Cajeros", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("alreadyRegistered"))
			JOptionPane.showMessageDialog(this, message, "Administrador de Cajeros", JOptionPane.WARNING_MESSAGE);
	}
}
