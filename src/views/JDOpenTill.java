package views;

import java.awt.FlowLayout;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDOpenTill extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfUser, jtxtfDate, jtxtfTime;
	public JSpinner jspInitialBalance;
	public JButton jbtnOpenTill, jbtnCancel;
	
	//Constructor
	public JDOpenTill(Window parent) {
		super(parent);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/open_till.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Apertura de Caja");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(420, 220);
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPTitle();
		this.setJPOperator();
		this.setJPDate();
		this.setJPTime();
		this.setJPInitialBalance();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Title
	private void setJPTitle() {
		JPanel jpTitle = new JPanel();
		jpTitle.setLayout(gbl);
		
		JLabel jlbltTitle = new JLabel();
		jlbltTitle.setText("Detalles de Apertura");
		jlbltTitle.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTitle.add(jlbltTitle, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpTitle, gbc);
	}
	
	//Method that SETS the JPanel Operator
	private void setJPOperator() {
		JPanel jpOperator = new JPanel();
		jpOperator.setLayout(gbl);
		
		JLabel jlbltOperator = new JLabel();
		jlbltOperator.setText("Operador que Abre Caja:");
		jlbltOperator.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 0);
		jpOperator.add(jlbltOperator, gbc);
		
		jtxtfUser = new JTextField(10);
		jtxtfUser.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfUser.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpOperator.add(jtxtfUser, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpOperator, gbc);
	}
	
	//Method that SETS the JPanel Date
	private void setJPDate() {
		JPanel jpDate = new JPanel();
		jpDate.setLayout(gbl);
		
		JLabel jlbltDate = new JLabel();
		jlbltDate.setText("Fecha de Apertura:");
		jlbltDate.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 0);
		jpDate.add(jlbltDate, gbc);
		
		jtxtfDate = new JTextField();
		jtxtfDate.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfDate.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpDate.add(jtxtfDate, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpDate, gbc);
	}
	
	//Method that SETS the JPanel Time
	private void setJPTime() {
		JPanel jpTime = new JPanel();
		jpTime.setLayout(gbl);
		
		JLabel jlbltTime = new JLabel();
		jlbltTime.setText("Hora de Apertura:");
		jlbltTime.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpTime.add(jlbltTime, gbc);
		
		jtxtfTime = new JTextField(10);
		jtxtfTime.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jtxtfTime.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTime.add(jtxtfTime, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpTime, gbc);
	}
	
	//Method that SETS the JPanel Initial Balance
	private void setJPInitialBalance() {
		JPanel jpInitialBalance = new JPanel();
		jpInitialBalance.setLayout(gbl);
		
		JLabel jlbltInitialBalance = new JLabel();
		jlbltInitialBalance.setText("Saldo Inicial: $");
		jlbltInitialBalance.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpInitialBalance.add(jlbltInitialBalance, gbc);
		
		SpinnerModel spmInitialBalance = new SpinnerNumberModel(0.00, 0.00, 100000.00, 0.10);
		jspInitialBalance = new JSpinner();
		jspInitialBalance.setModel(spmInitialBalance);
		JSpinner.NumberEditor jspneInitialBalance = new JSpinner.NumberEditor(jspInitialBalance, "###,##0.00");
		jspInitialBalance.setEditor(jspneInitialBalance);
		jspInitialBalance.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpInitialBalance.add(jspInitialBalance, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpInitialBalance, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnOpenTill = new JButton();
		jbtnOpenTill.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnOpenTill.setText("Abrir Caja");
		jbtnOpenTill.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpActions.add(jbtnOpenTill);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpActions.add(jbtnCancel);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	
	//Method that SETS the Messages for the USER
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("tillOpened"))
			JOptionPane.showMessageDialog(this, message, "Apertura de Caja", JOptionPane.INFORMATION_MESSAGE);
	}
}
