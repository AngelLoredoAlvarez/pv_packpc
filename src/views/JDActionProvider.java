package views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

public class JDActionProvider extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfName, jtxtfCompanyName, jtxtfIRS;
	public JTextField jtxtfStreet, jtxtfIntNumber, jtxtfExtNumber;
	public JTextField jtxtfColony, jtxtfCity, jtxtfPostCode;
	public JTextField jtxtfLandLine1, jtxtfLandLine2, jtxtfEmail;
	public JTextArea jtaComentary;
	public JButton jbtnSave, jbtnCancel;
	public String action = "";
	
	//Constructor
	public JDActionProvider(JDConsultProviders jdConsultProviders, String title, String icon) {
		super(jdConsultProviders);
		this.setModal(true);
		Image iconJD = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon));
		this.setIconImage(iconJD);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(590, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(jdConsultProviders);
		action = title;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPProviderInformation();
		this.setJPProviderContact();
		this.setJPComentary();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Company's Information
	private void setJPProviderInformation() {
		JPanel jpProviderInformation = new JPanel();
		jpProviderInformation.setLayout(gbl);
		jpProviderInformation.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Información del Proveedor",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		JPanel jpFirstData = new JPanel();
		jpFirstData = this.returnJPFirstData();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpProviderInformation.add(jpFirstData, gbc);
		
		JPanel jpSecondData = new JPanel();
		jpSecondData = this.returnJPSecondData();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpProviderInformation.add(jpSecondData, gbc);
		
		JPanel jpThirdData = new JPanel();
		jpThirdData = this.returnJPThirdData();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpProviderInformation.add(jpThirdData, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpMain.add(jpProviderInformation, gbc);
	}
	private JPanel returnJPFirstData() {
		JPanel returnJPProviderData = new JPanel();
		returnJPProviderData.setLayout(gbl);
		
		jtxtfName = new JTextField(16);
		jtxtfName.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Nombre", jtxtfName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfName);
		jtxtfName.requestFocus();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPProviderData.add(jtxtfName, gbc);
		
		jtxtfCompanyName = new JTextField(16);
		jtxtfCompanyName.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Razón Social", jtxtfCompanyName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCompanyName);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPProviderData.add(jtxtfCompanyName, gbc);
		
		jtxtfIRS = new JTextField(13);
		jtxtfIRS.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("R.F.C", jtxtfIRS);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIRS);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPProviderData.add(jtxtfIRS, gbc);
		
		return returnJPProviderData;
	}
	private JPanel returnJPSecondData() {
		JPanel returnJPSecondData = new JPanel();
		returnJPSecondData.setLayout(gbl);
		
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
		gbc.insets = new Insets(0, 0, 0, 8);
		returnJPSecondData.add(jtxtfStreet, gbc);
		
		jtxtfExtNumber = new JTextField(5);
		jtxtfExtNumber.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Ext", jtxtfExtNumber);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfExtNumber);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPSecondData.add(jtxtfExtNumber, gbc);
		
		jtxtfIntNumber = new JTextField(5);
		jtxtfIntNumber.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Int", jtxtfIntNumber);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIntNumber);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSecondData.add(jtxtfIntNumber, gbc);
		
		return returnJPSecondData;
	}
	private JPanel returnJPThirdData() {
		JPanel returnJPThirdData = new JPanel();
		returnJPThirdData.setLayout(gbl);
		
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
		returnJPThirdData.add(jtxtfColony, gbc);
		
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
		returnJPThirdData.add(jtxtfCity, gbc);
		
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
		returnJPThirdData.add(jtxtfPostCode, gbc);
		
		return returnJPThirdData;
	}
	
	//Method that SETS the JPanel Contact Provider Information
	private void setJPProviderContact() {
		JPanel jpProviderContact = new JPanel();
		jpProviderContact.setLayout(gbl);
		jpProviderContact.setBorder(
			BorderFactory.createTitledBorder(
				null, 
				"Información de Contacto",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		JPanel jpContactInfo = new JPanel();
		jpContactInfo = this.returnJPContactInfo();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpProviderContact.add(jpContactInfo, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpMain.add(jpProviderContact, gbc);
	}
	private JPanel returnJPContactInfo() {
		JPanel returnJPContactInfo = new JPanel();
		returnJPContactInfo.setLayout(gbl);
		
		jtxtfLandLine1 = new JTextField(10);
		jtxtfLandLine1.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Contacto 1", jtxtfLandLine1);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLandLine1);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfLandLine1, gbc);
		
		jtxtfLandLine2 = new JTextField(10);
		jtxtfLandLine2.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("No. Contacto 2", jtxtfLandLine2);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfLandLine2);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		returnJPContactInfo.add(jtxtfLandLine2, gbc);
		
		jtxtfEmail = new JTextField(25);
		jtxtfEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		PromptSupport.setPrompt("Correo Electrónico", jtxtfEmail);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfEmail);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPContactInfo.add(jtxtfEmail, gbc);
		
		return returnJPContactInfo;
	}
	
	//Method that SETS the JPanel Commentaries
	private void setJPComentary() {
		JPanel jpComentary = new JPanel();
		jpComentary.setLayout(gbl);
		jpComentary.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Comentarios",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 18)
			)
		);
		
		jtaComentary = new JTextArea();
		jtaComentary.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpComentary.add(jtaComentary, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
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
		jpActions.add(jbtnCancel, gbc);
		
		//This has to be at the END
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public String setJOPMessages(String typeMessage, String message) {
		int action = 0;
		String answer = "";
		
		if(typeMessage.equals("alreadyRegistered"))
			JOptionPane.showMessageDialog(this, message, "Administración de Proveedores", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("missing"))
			JOptionPane.showMessageDialog(this, message, "Administración de Proveedores", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("savedOK"))
			JOptionPane.showMessageDialog(this, message, "Administración de Proveedores", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("modifiedOK"))
			JOptionPane.showMessageDialog(this, message, "Administración de Proveedores", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("another")) {
			action = JOptionPane.showConfirmDialog(this, message, "Administración de Proveedores", JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
				answer = "YES";
			else
				answer = "NO";
		}
		
		return answer;
	}
}
