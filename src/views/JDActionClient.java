package views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

public class JDActionClient extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfNames, jtxtfFirstName, jtxtfLastName;
	public JTextField jtxtfStreet, jtxtfIntNum, jtxtfExtNum, jtxtfColony, jtxtfCity, jtxtfPostCode, jtxtfLandLine, jtxtfCelPhone, jtxtfEmail, jtxtfIRS;
	public JSpinner jspCreditLimit;
	public JTextArea jtaComentary;
	public JButton jbtnSave, jbtnCancel;
	public String action = "";
	public String parent = "";
	
	//Constructor
	public JDActionClient(Window window, String title, String icon, String parent) {
		super(window);
		this.setModal(true);
		Image iconJD = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon));
		this.setIconImage(iconJD);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(590, 345);
		this.setResizable(false);
		this.setLocationRelativeTo(window);
		this.action = title;
		this.parent = parent;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPPersonalInfo();
		this.setJPContactInfo();
		this.setJPCIRS();
		this.setJPComentary();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel with the Personal Info of the Client
	private void setJPPersonalInfo() {
		JPanel jpPersonalInfo = new JPanel();
		jpPersonalInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información Personal:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		jpPersonalInfo.setLayout(gbl);
		
		//JPanel Name=========================================================================================================
		JPanel jpName = new JPanel();
		jpName = this.returnJPName();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpPersonalInfo.add(jpName, gbc);
		//JPanel Name=========================================================================================================
		
		//JPanel Address======================================================================================================
		JPanel jpAddress = new JPanel();
		jpAddress = this.returnJPAddress();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpPersonalInfo.add(jpAddress, gbc);
		//JPanel Address======================================================================================================
		
		//JPanel Address2======================================================================================================
		JPanel jpAddress2 = new JPanel();
		jpAddress2 = this.returnJPAddress2();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpPersonalInfo.add(jpAddress2, gbc);
		//JPanel Address2======================================================================================================
		
		//Add to the JPanel Main
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpMain.add(jpPersonalInfo, gbc);
	}
	private JPanel returnJPName() {
		JPanel returnJPName = new JPanel();
		returnJPName.setLayout(gbl);
		
		jtxtfNames = new JTextField(21);
		jtxtfNames.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
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
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPName.add(jtxtfNames, gbc);
		
		jtxtfFirstName = new JTextField(12);
		jtxtfFirstName.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Apellido Paterno", jtxtfFirstName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfFirstName);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPName.add(jtxtfFirstName, gbc);
		
		jtxtfLastName = new JTextField(12);
		jtxtfLastName.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Apellido Materno", jtxtfLastName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLastName);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPName.add(jtxtfLastName, gbc);
		return returnJPName;
	}
	private JPanel returnJPAddress() {
		JPanel returnJPAddress = new JPanel();
		returnJPAddress.setLayout(gbl);
		
		jtxtfStreet = new JTextField(35);
		jtxtfStreet.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Calle", jtxtfStreet);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfStreet);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPAddress.add(jtxtfStreet, gbc);
		
		jtxtfExtNum = new JTextField(5);
		jtxtfExtNum.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Ext", jtxtfExtNum);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfExtNum);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPAddress.add(jtxtfExtNum, gbc);
		
		jtxtfIntNum = new JTextField(5);
		jtxtfIntNum.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Int", jtxtfIntNum);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIntNum);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPAddress.add(jtxtfIntNum, gbc);
		
		return returnJPAddress;
	}
	private JPanel returnJPAddress2() {
		JPanel returnJPAddress2 = new JPanel();
		returnJPAddress2.setLayout(gbl);
		
		jtxtfColony = new JTextField(23);
		jtxtfColony.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Colonia", jtxtfColony);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfColony);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPAddress2.add(jtxtfColony, gbc);
		
		jtxtfCity = new JTextField(17);
		jtxtfCity.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Ciudad", jtxtfCity);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCity);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPAddress2.add(jtxtfCity, gbc);
		
		jtxtfPostCode = new JTextField(5);
		jtxtfPostCode.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("C.P.", jtxtfPostCode);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfPostCode);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPAddress2.add(jtxtfPostCode, gbc);
		
		return returnJPAddress2;
	}
	
	//Method that SETS the JPanel with the Contact Informations of the Client
	private void setJPContactInfo() {
		JPanel jpContactInfo = new JPanel();
		jpContactInfo.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información de Contacto",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		jpContactInfo.setLayout(gbl);
		
		JPanel jpContactInfoData = new JPanel();
		jpContactInfoData = this.returnJPContactInfo();
		gbc.gridx = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpContactInfo.add(jpContactInfoData, gbc);
		
		//Add to the JPanel Main
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpMain.add(jpContactInfo, gbc);
	}
	private JPanel returnJPContactInfo() {
		JPanel returnJPContactInfo = new JPanel();
		returnJPContactInfo.setLayout(gbl);
		
		jtxtfLandLine = new JTextField(10);
		jtxtfLandLine.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Tel. Fijo", jtxtfLandLine);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLandLine);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfLandLine, gbc);
		
		jtxtfCelPhone = new JTextField(10);
		jtxtfCelPhone.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Tel. Celular", jtxtfCelPhone);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCelPhone);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfCelPhone, gbc);
		
		jtxtfEmail = new JTextField(25);
		jtxtfEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Correo Electrónico", jtxtfEmail);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfEmail);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPContactInfo.add(jtxtfEmail, gbc);
		
		return returnJPContactInfo;
	}
	
	//Method that SETS the JPanel Credit IRS
	private void setJPCIRS() {
		JPanel jpCIRS = new JPanel();
		jpCIRS.setLayout(gbl);
		
		JPanel jpIRS = this.returnJPIRS();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCIRS.add(jpIRS, gbc);
		
		JPanel jpCreditLimit = this.returnJPCreditLimit();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCIRS.add(jpCreditLimit, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		jpMain.add(jpCIRS, gbc);
	}
	private JPanel returnJPIRS() {
		JPanel returnJPIRS = new JPanel();
		returnJPIRS.setLayout(gbl);
		returnJPIRS.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Facturación",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		jtxtfIRS = new JTextField(13);
		jtxtfIRS.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("R.F.C.", jtxtfIRS);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIRS);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPIRS.add(jtxtfIRS, gbc);
		
		return returnJPIRS;
	}
	private JPanel returnJPCreditLimit() {
		JPanel returnJPCreditLimit = new JPanel();
		returnJPCreditLimit.setLayout(gbl);
		returnJPCreditLimit.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Límite de Cŕedito",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		
		JLabel jlbltCreditLimit = new JLabel();
		jlbltCreditLimit.setText("$");
		jlbltCreditLimit.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCreditLimit.add(jlbltCreditLimit, gbc);
		
		SpinnerModel snmCreditLimit = new SpinnerNumberModel(0, 0, 100000, 0.10);
		jspCreditLimit = new JSpinner();
		jspCreditLimit.setModel(snmCreditLimit);
		JSpinner.NumberEditor jspneCreditLimit = new JSpinner.NumberEditor(jspCreditLimit, "###,##0.00");
		jspCreditLimit.setEditor(jspneCreditLimit);
		jspCreditLimit.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jspCreditLimit.setValue(new Double(1000));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCreditLimit.add(jspCreditLimit, gbc);
		
		return returnJPCreditLimit;
	}
	
	//Method that SETS the JPanel Commentary
	private void setJPComentary() {
		JPanel jpComentary = new JPanel();
		jpComentary.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Comentarios",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 16)
			)
		);
		jpComentary.setLayout(gbl);
		
		jtaComentary = new JTextArea();
		jtaComentary.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		JScrollPane jspComentary = new JScrollPane(jtaComentary);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpComentary.add(jspComentary, gbc);
		
		//Add to the JPanel Main
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpMain.add(jpComentary, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(gbl);
		
		jbtnSave = new JButton();
		jbtnSave.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnSave.setText("Guardar");
		jbtnSave.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpActions.add(jbtnSave, gbc);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpActions.add(jbtnCancel, gbc);
		
		//Add to the JPanel Main
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public String setJOPMessages(String typeMessage, String message) {
		int action = 0;
		String answer = "";
		
	    if(typeMessage.equals("alreadyRegistered"))
	    	JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("savedOK"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("modifiedOK"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("another")) {
			action = JOptionPane.showConfirmDialog(this, message, "Control de Clientes", JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
				answer = "YES";
			else
				answer = "NO";
		} else if(typeMessage.equals("missing")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		}
	    
	    return answer;
	}
}
