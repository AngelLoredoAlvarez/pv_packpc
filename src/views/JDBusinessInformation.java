package views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.prompt.PromptSupport;

public class JDBusinessInformation extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JPanel jpSATInfo, jpCK;
	public JTextField jtxtfName, jtxtfCompanyName, jtxtfIRS, jtxtfStreet, jtxtfExtNumber, jtxtfIntNumber, jtxtfColony, jtxtfCity, jtxtfPostCode, jtxtfEmail;
	public JTextField jtxtfCertificateRoute, jtxtfPrivateKeyRoute;
	public JPasswordField jpfPrivateKeyPass;
	public JButton jbtnSearchCertificate, jbtnSearchPrivateKey, jbtnPrevious, jbtnAction, jbtnCancel;
	
	//Constructor
	public JDBusinessInformation(JFMain jfMain) {
		super(jfMain);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/invoice.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Configurar Facturación");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(540, 210);
		this.setResizable(false);
		this.setLocationRelativeTo(jfMain);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPCenter();
		this.setJPSouth();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		jpSATInfo = this.returnJPSATInfo();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpSATInfo, gbc);
		
		jpCK = this.returnJPCK();
		jpCK.setVisible(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpCK, gbc);
		
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
	private JPanel returnJPSATInfo() {
		JPanel returnJPSATInfo = new JPanel();
		returnJPSATInfo.setLayout(gbl);
		
		Font fJTextFields = new Font("Liberation Sans", Font.PLAIN, 14);
		
		//TOP==================================================================================================================
		JPanel jpSATInfoTop = new JPanel();
		jpSATInfoTop.setLayout(gbl);
		
		JLabel jlbltWho = new JLabel();
		jlbltWho.setText("¿Quién expide las Facturas?");
		jlbltWho.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoTop.add(jlbltWho, gbc);
		
		JPanel jpSpaceSATInfoTop = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoTop.add(jpSpaceSATInfoTop, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSATInfo.add(jpSATInfoTop, gbc);
		//TOP==================================================================================================================
		
		//Center===============================================================================================================
		JPanel jpSATInfoCenter = new JPanel();
		jpSATInfoCenter.setLayout(gbl);
		
		//JPInstructions=======================================================================================================
		JPanel jpInstructions = new JPanel();
		jpInstructions.setLayout(gbl);
		
		JLabel jlbltInstructions = new JLabel();
		jlbltInstructions.setText("Para comenzar, por favor indica tus datos de contribuyente ante el SAT:");
		jlbltInstructions.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpInstructions.add(jlbltInstructions, gbc);
		
		JPanel jpSpaceInstructions = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpInstructions.add(jpSpaceInstructions, gbc);
		
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
		jpSATInfoCenter.add(jpInstructions, gbc);
		//JPInstructions=======================================================================================================
		
		//JPBusinessInformation================================================================================================
		JPanel jpBusinessInformation = new JPanel();
		jpBusinessInformation.setLayout(gbl);
		
		jtxtfName = new JTextField(16);
		jtxtfName.setFont(fJTextFields);
		PromptSupport.setPrompt("Nombre", jtxtfName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfName);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpBusinessInformation.add(jtxtfName, gbc);
		
		jtxtfCompanyName = new JTextField(16);
		jtxtfCompanyName.setFont(fJTextFields);
		PromptSupport.setPrompt("Razón Social", jtxtfCompanyName);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCompanyName);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpBusinessInformation.add(jtxtfCompanyName, gbc);
		
		jtxtfIRS = new JTextField(13);
		jtxtfIRS.setFont(fJTextFields);
		PromptSupport.setPrompt("R.F.C.", jtxtfIRS);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIRS);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBusinessInformation.add(jtxtfIRS, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoCenter.add(jpBusinessInformation, gbc);
		//JPBusinessInformation================================================================================================
		
		//JPAddresBusiness1====================================================================================================
		JPanel jpAddressBusiness1 = new JPanel();
		jpAddressBusiness1.setLayout(gbl);
		
		jtxtfStreet = new JTextField(35);
		jtxtfStreet.setFont(fJTextFields);
		PromptSupport.setPrompt("Calle", jtxtfStreet);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfStreet);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpAddressBusiness1.add(jtxtfStreet, gbc);
		
		jtxtfExtNumber = new JTextField(5);
		jtxtfExtNumber.setFont(fJTextFields);
		PromptSupport.setPrompt("No. Ext", jtxtfExtNumber);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfExtNumber);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpAddressBusiness1.add(jtxtfExtNumber, gbc);
		
		jtxtfIntNumber = new JTextField(5);
		jtxtfIntNumber.setFont(fJTextFields);
		PromptSupport.setPrompt("No. Int", jtxtfIntNumber);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfIntNumber);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpAddressBusiness1.add(jtxtfIntNumber, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoCenter.add(jpAddressBusiness1, gbc);
		//JPAddresBusiness1====================================================================================================
		
		//JPAddresBusiness2====================================================================================================
		JPanel jpAddressBusiness2 = new JPanel();
		jpAddressBusiness2.setLayout(gbl);
		
		jtxtfColony = new JTextField(23);
		jtxtfColony.setFont(fJTextFields);
		PromptSupport.setPrompt("Colonia", jtxtfColony);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfColony);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpAddressBusiness2.add(jtxtfColony, gbc);
		
		jtxtfCity = new JTextField(17);
		jtxtfCity.setFont(fJTextFields);
		PromptSupport.setPrompt("Ciudad", jtxtfCity);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfCity);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpAddressBusiness2.add(jtxtfCity, gbc);
		
		jtxtfPostCode = new JTextField(5);
		jtxtfPostCode.setFont(fJTextFields);
		PromptSupport.setPrompt("C.P.", jtxtfPostCode);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfPostCode);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpAddressBusiness2.add(jtxtfPostCode, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoCenter.add(jpAddressBusiness2, gbc);
		//JPAddresBusiness2====================================================================================================
		
		//Email================================================================================================================
		JPanel jpEmail = new JPanel();
		jpEmail.setLayout(gbl);
		
		jtxtfEmail = new JTextField(47);
		jtxtfEmail.setFont(fJTextFields);
		PromptSupport.setPrompt("Correo Electrónico", jtxtfEmail);
		PromptSupport.setFontStyle(Font.BOLD, jtxtfEmail);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpEmail.add(jtxtfEmail, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSATInfoCenter.add(jpEmail, gbc);
		//Email================================================================================================================
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPSATInfo.add(jpSATInfoCenter, gbc);
		//Center==================================================================================================================
		
		return returnJPSATInfo;
	}
	private JPanel returnJPCK() {
		JPanel returnJPCK = new JPanel();
		returnJPCK.setLayout(gbl);
		
		Font fTitles = new Font("Liberation Sans", Font.BOLD, 14);
		Font fRoutes = new Font("Liberation Sans", Font.PLAIN, 14);
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		//TOP==================================================================================================================
		JPanel jpCKTop = new JPanel();
		jpCKTop.setLayout(gbl);
		
		JLabel jlbltWhereIs = new JLabel();
		jlbltWhereIs.setText("¿Dónde están tus archivos de Certificado de Sellos y Clave Privada?");
		jlbltWhereIs.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKTop.add(jlbltWhereIs, gbc);
		
		JPanel jpSpaceCKTop = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKTop.add(jpSpaceCKTop, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCK.add(jpCKTop, gbc);
		//TOP==================================================================================================================
		
		//Center===============================================================================================================
		JPanel jpCKCenter = new JPanel();
		jpCKCenter.setLayout(gbl);
		
		//JPInstructions=======================================================================================================
		JPanel jpInstructions = new JPanel();
		jpInstructions.setLayout(gbl);
		
		JLabel jlbltInstructions = new JLabel();
		jlbltInstructions.setText("<html>"
				                	+ "<p>"
				                		+ "Para poder realizar las Facturas Electrónicas, es necesario propoercionar los siguientes"
				                	+ "</p>"
				                	+ "<p>"
				                		+ "archivos que obtuviste al hacer los tramites ante el SAT:"
				                	+ "</p>"
				                + "</html>");
		jlbltInstructions.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpInstructions.add(jlbltInstructions, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKCenter.add(jpInstructions, gbc);
		//JPInstructions=======================================================================================================
		
		//JPCertificate========================================================================================================
		JPanel jpCertificate = new JPanel();
		jpCertificate.setLayout(gbl);
		
		jbtnSearchCertificate = new JButton();
		jbtnSearchCertificate.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/search.png")));
		jbtnSearchCertificate.setText("Localizar Certificado de Sellos");
		jbtnSearchCertificate.setFont(fJButtons);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 3);
		jpCertificate.add(jbtnSearchCertificate, gbc);
		
		JLabel jlbltRouteCertificate = new JLabel();
		jlbltRouteCertificate.setText("Ruta:");
		jlbltRouteCertificate.setFont(fTitles);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCertificate.add(jlbltRouteCertificate, gbc);
		
		jtxtfCertificateRoute = new JTextField();
		jtxtfCertificateRoute.setFont(fRoutes);
		jtxtfCertificateRoute.setEditable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpCertificate.add(jtxtfCertificateRoute, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKCenter.add(jpCertificate, gbc);
		//JPCertificate========================================================================================================
		
		//JPPrivateKey=========================================================================================================
		JPanel jpPrivateKey = new JPanel();
		jpPrivateKey.setLayout(gbl);
		
		jbtnSearchPrivateKey = new JButton();
		jbtnSearchPrivateKey.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/search.png")));
		jbtnSearchPrivateKey.setText("Localizar Clave Privada");
		jbtnSearchPrivateKey.setFont(fJButtons);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 3);
		jpPrivateKey.add(jbtnSearchPrivateKey, gbc);
		
		JLabel jlbltRoutePrivateKey = new JLabel();
		jlbltRoutePrivateKey.setText("Ruta:");
		jlbltRoutePrivateKey.setFont(fTitles);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpPrivateKey.add(jlbltRoutePrivateKey, gbc);
		
		jtxtfPrivateKeyRoute = new JTextField();
		jtxtfPrivateKeyRoute.setFont(fRoutes);
		jtxtfPrivateKeyRoute.setEditable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpPrivateKey.add(jtxtfPrivateKeyRoute, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKCenter.add(jpPrivateKey, gbc);
		//JPPrivateKey=========================================================================================================
		
		//JPPrivateKeyPass=====================================================================================================
		JPanel jpPrivateKeyPass = new JPanel();
		jpPrivateKeyPass.setLayout(gbl);
		
		JLabel jlbltInstructionsPrivateKeyPass = new JLabel();
		jlbltInstructionsPrivateKeyPass.setText("Ingresa la Contraseña de tu Clave Privada");
		jlbltInstructionsPrivateKeyPass.setFont(fTitles);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpPrivateKeyPass.add(jlbltInstructionsPrivateKeyPass, gbc);
		
		jpfPrivateKeyPass = new JPasswordField(30);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpPrivateKeyPass.add(jpfPrivateKeyPass, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCKCenter.add(jpPrivateKeyPass, gbc);
		//JPPrivateKeyPass=====================================================================================================
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPCK.add(jpCKCenter, gbc);
		//Center===============================================================================================================
		
		return returnJPCK;
	}
	
	//Method that SETS the JPanel South
	private void setJPSouth() {
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(gbl);
		
		JPanel jpSpaceActions = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSouth.add(jpSpaceActions, gbc);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnPrevious = new JButton();
		jbtnPrevious.setText("< Anterior");
		jbtnPrevious.setFont(fJButtons);
		jbtnPrevious.setEnabled(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 1);
		jpSouth.add(jbtnPrevious, gbc);
		
		jbtnAction = new JButton();
		jbtnAction.setText("Siguiente >");
		jbtnAction.setFont(fJButtons);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 1);
		jpSouth.add(jbtnAction, gbc);
		
		jbtnCancel = new JButton();
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpSouth.add(jbtnCancel, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 5, 5);
		jpMain.add(jpSouth, gbc);
	}
	
	//JFileChooser
	public String searchFiles(String title, String name, String extension) {
		String route = "";
		JFileChooser jfcFiles = new JFileChooser();
		jfcFiles.setDialogTitle(title);
		jfcFiles.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcFiles.setAcceptAllFileFilterUsed(false);
		jfcFiles.addChoosableFileFilter(new FileNameExtensionFilter(name, extension));
		
		int action = jfcFiles.showSaveDialog(this);
		if(action == JFileChooser.APPROVE_OPTION) {
			route = jfcFiles.getSelectedFile().getAbsolutePath();
		} else {
			route = "none";
		}
		
		return route;
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("missing"))
			JOptionPane.showMessageDialog(this, message, "Facturación", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("updateInformation") || typeMessage.equals("saveInformation"))
			JOptionPane.showMessageDialog(this, message, "Facturación", JOptionPane.INFORMATION_MESSAGE);
	}
}
