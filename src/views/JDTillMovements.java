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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDTillMovements extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JComboBox<String> jcbTypeMovement;
	public JSpinner jspBalance;
	public JTextArea jtaComentary;
	public JButton jbtnAccept, jbtnCancel;
	
	//Constructor
	public JDTillMovements(JFMain jfSales) {
		super(jfSales);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/till_movements.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Moviemientos de Caja");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSales);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPTypeMovement();
		this.setJPBalances();
		this.setJPComentary();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel TypeMovement
	private void setJPTypeMovement() {
		JPanel jpTypeMovement = new JPanel();
		jpTypeMovement.setLayout(gbl);
		
		JLabel jlbltTypeMovement = new JLabel();
		jlbltTypeMovement.setText("Tipo de Movimiento:");
		jlbltTypeMovement.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpTypeMovement.add(jlbltTypeMovement, gbc);
		
		String[] movements = {"Entrada", "Salida"};
		jcbTypeMovement = new JComboBox<String>(movements);
		jcbTypeMovement.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		jcbTypeMovement.setSelectedIndex(0);
		jcbTypeMovement.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpTypeMovement.add(jcbTypeMovement, gbc);
		
		JLabel jlbltExitofCash = new JLabel();
		jlbltExitofCash.setText("de Efectivo");
		jlbltExitofCash.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpTypeMovement.add(jlbltExitofCash, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 0, 10);
		jpMain.add(jpTypeMovement, gbc);
	}
	
	//Method that SETS the JPanel Balances
	private void setJPBalances() {
		JPanel jpBalances = new JPanel();
		jpBalances.setLayout(gbl);
		
		JLabel jlbliMoney = new JLabel();
		jlbliMoney.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/dollar_coin.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBalances.add(jlbliMoney, gbc);
		
		JLabel jlbltAmount = new JLabel();
		jlbltAmount.setText("Monto: $");
		jlbltAmount.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBalances.add(jlbltAmount, gbc);
		
		SpinnerModel spmBalance = new SpinnerNumberModel(0.00, 0.00, 100000.00, 0.10);
		jspBalance = new JSpinner();
		jspBalance.setModel(spmBalance);
		JSpinner.NumberEditor jspneBalance = new JSpinner.NumberEditor(jspBalance, "###,##0.00");
		jspBalance.setEditor(jspneBalance);
		jspBalance.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpBalances.add(jspBalance, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 0, 10);
		jpMain.add(jpBalances, gbc);
	}
	
	//Method that SETS the JPanel Comentary
	private void setJPComentary() {
		JPanel jpComentary = new JPanel();
		jpComentary.setLayout(gbl);
		
		JLabel jlbltComentary = new JLabel();
		jlbltComentary.setText("Comentarios y/o Razón Social:");
		jlbltComentary.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpComentary.add(jlbltComentary, gbc);
		
		jtaComentary = new JTextArea();
		jtaComentary.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		JScrollPane jspJTAComentary = new JScrollPane(jtaComentary); 
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpComentary.add(jspJTAComentary, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 0, 10);
		jpMain.add(jpComentary, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnAccept = new JButton();
		jbtnAccept.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnAccept.setText("Aceptar");
		jbtnAccept.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnAccept.setEnabled(false);
		jpActions.add(jbtnAccept);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpActions.add(jbtnCancel);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpActions, gbc);
	}
	
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("movementOK"))
			JOptionPane.showMessageDialog(this, message, "Movimiento en Caja", JOptionPane.INFORMATION_MESSAGE);
	}
}
