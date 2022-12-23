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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JDAskSUDO extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfUserName;
	public JPasswordField jpfPassword;
	public JButton jbtnAccept, jbtnCancel;
	
	//Constructor
	public JDAskSUDO(JFMain jfSales) {
		super(jfSales);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/ask_sudo.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Esta accion requiere Permisos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(280, 130);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations NEEDED
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPLogin();
		this.setJPActions();
	}
	
	//Method that SETS the JPMain
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Login
	private void setJPLogin() {
		JPanel jpLogin = new JPanel();
		jpLogin.setLayout(gbl);
		
		JPanel jpUserName = new JPanel();
		jpUserName = this.returnJPUserName();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpLogin.add(jpUserName, gbc);
		
		JPanel jpPass = new JPanel();
		jpPass = this.returnJPPass();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpLogin.add(jpPass, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpLogin, gbc);
	}
	
	//Method that SETS the JPanel USERNAME
	private JPanel returnJPUserName() {
		JPanel returnJPUserName = new JPanel();
		returnJPUserName.setLayout(gbl);
		
		JLabel jlbltUserName = new JLabel();
		jlbltUserName.setText("Usuario:");
		jlbltUserName.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPUserName.add(jlbltUserName, gbc);
		
		jtxtfUserName = new JTextField(10);
		jtxtfUserName.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfUserName.requestFocus();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPUserName.add(jtxtfUserName, gbc);
		
		return returnJPUserName;
	}
	
	//Method that SETS the JPanel Pass
	private JPanel returnJPPass() {
		JPanel returnJPPass = new JPanel();
		returnJPPass.setLayout(gbl);
		
		JLabel jlbltPass = new JLabel();
		jlbltPass.setText("Contraseña:");
		jlbltPass.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPPass.add(jlbltPass, gbc);
		
		jpfPassword = new JPasswordField(10);
		jpfPassword.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPPass.add(jpfPassword, gbc);
		
		return returnJPPass;
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(gbl);
		
		jbtnAccept = new JButton();
		jbtnAccept.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnAccept.setText("Acceptar");
		jbtnAccept.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpActions.add(jbtnAccept, gbc);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpActions.add(jbtnCancel, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 5, 0);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("notExist"))
			JOptionPane.showMessageDialog(this, message, "Solicitud de Super Usuario", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("noPrivilege"))
			JOptionPane.showMessageDialog(this, message, "Solicitud de Super Usuario", JOptionPane.ERROR_MESSAGE);
	}
}
