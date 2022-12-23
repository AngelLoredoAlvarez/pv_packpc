package views;

import java.awt.FlowLayout;
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
import javax.swing.JTextField;

public class JDActionDepartment extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfName;
	public JButton jbtnAccept, jbtnCancel;
	public String action = "";
	
	//Constructor
	public JDActionDepartment(JDConsultDepartments jdConsultDepartments, String title, String icon) {
		super(jdConsultDepartments);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(icon);
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(350, 100);
		this.setResizable(false);
		this.setLocationRelativeTo(jdConsultDepartments);
		this.action = title;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPName();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Name
	private void setJPName() {
		JPanel jpName = new JPanel();
		jpName.setLayout(gbl);
		
		JLabel jlbltName = new JLabel();
		jlbltName.setText("Nombre:");
		jlbltName.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpName.add(jlbltName, gbc);
		
		jtxtfName = new JTextField();
		jtxtfName.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfName.requestFocus();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		jpName.add(jtxtfName, gbc);
		
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
		jpMain.add(jpName, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnAccept = new JButton();
		jbtnAccept.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnAccept.setText("Guardar");
		jbtnAccept.setFont(fJButtons);
		jpActions.add(jbtnAccept);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
		jpActions.add(jbtnCancel);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the Cashier
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("SMCorrectly"))
			JOptionPane.showMessageDialog(this, message, "Departamentos", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("alreadyExist"))
			JOptionPane.showMessageDialog(this, message, "Departamentos", JOptionPane.WARNING_MESSAGE);
	}
}
