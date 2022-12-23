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
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDPayment extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JSpinner jspQuantity;
	public JButton jbtnPay, jbtnCancel;
	
	//Constructor
	public JDPayment(JDStatementOfAccount jdStatementOfAccount) {
		super(jdStatementOfAccount);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/pay.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Abono a Crédito");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(370, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(jdStatementOfAccount);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPNorth();
		this.setJPSouth();
	}
	
	//Method that SETS the JPMain
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JLabel jlbltQuantityToPay = new JLabel();
		jlbltQuantityToPay.setText("Cantidad a Abonar:");
		jlbltQuantityToPay.setFont(new Font("Liberation Sans", Font.BOLD, 28));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jlbltQuantityToPay, gbc);
		
		SpinnerModel spmQuantity = new SpinnerNumberModel(0.00, 0.00, 100000.00, 0.10);
		jspQuantity = new JSpinner();
		jspQuantity.setModel(spmQuantity);
		JSpinner.NumberEditor jsnePay = new JSpinner.NumberEditor(jspQuantity, "###,##0.00");
		jspQuantity.setEditor(jsnePay);
		jspQuantity.setFont(new Font("Liberation Sans", Font.BOLD, 36));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jspQuantity, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpNorth, gbc);
	}
	
	private void setJPSouth() {
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnPay = new JButton();
		jbtnPay.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnPay.setText("Abonar");
		jbtnPay.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpSouth.add(jbtnPay);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jpSouth.add(jbtnCancel);
		
		//This has to at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpSouth, gbc);
	}
}
