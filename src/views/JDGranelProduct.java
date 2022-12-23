package views;

import java.awt.Color;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDGranelProduct extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JLabel jlblProduct, jlblUnitaryPrice;
	public JTextField jtxtfQuantityProduct, jtxtfActualPrice;
	public JButton jbtnAccept, jbtnCancel;
	public JSpinner jspQuantity, jspPrice;
	public JSpinner.NumberEditor jspneQuantity, jspnePrice;
	public String action;
	private double limit = 0.0;
	
	//Constructor
	public JDGranelProduct(JFMain jfSells, String icon, String title, double limit) {
		super(jfSells);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon));
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(550, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSells);
		action = title;
		this.limit = limit;
		//Initializations NEEDED
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPComponents();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Components
	private void setJPComponents() {
		JPanel jpComponents = new JPanel();
		jpComponents.setLayout(gbl);
		
		JPanel jpNorth = new JPanel();
		jpNorth = this.returnJPNorth();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		jpComponents.add(jpNorth, gbc);
		
		JPanel jpCenter = new JPanel();
		jpCenter = this.returnJPCenter();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jpComponents.add(jpCenter, gbc);
		
		JPanel jpSouth = new JPanel();
		jpSouth = this.returnJPSouth();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		jpComponents.add(jpSouth, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jpMain.add(jpComponents, gbc);
	}
	
	//Method that RETURNS the JPanel North
	private JPanel returnJPNorth() {
		JPanel returnJPNorth = new JPanel();
		returnJPNorth.setLayout(gbl);
		
		jlblProduct = new JLabel();
		jlblProduct.setFont(new Font("Liberation Sans", Font.BOLD, 32));
		jlblProduct.setForeground(Color.BLUE);
		jlblProduct.setText("Producto");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		returnJPNorth.add(jlblProduct, gbc);
		
		return returnJPNorth;
	}
	
	//Method that RETURNS the JPanel Center
	private JPanel returnJPCenter() {
		JPanel returnJPCenter = new JPanel();
		returnJPCenter.setLayout(gbl);
		
		JPanel jp0x0= new JPanel();
		jp0x0 = this.returnJP0x0();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 0);
		returnJPCenter.add(jp0x0, gbc);
		
		JPanel jp0x1 = new JPanel();
		jp0x1 = this.returnJP0x1();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 5);
		returnJPCenter.add(jp0x1, gbc);
		return returnJPCenter;
	}
	
	//Method that RETURNS the JPanel 0x0
	private JPanel returnJP0x0() {
		JPanel returnJP0x0 = new JPanel();
		returnJP0x0.setLayout(gbl);
		
		JLabel jlbltQuantityProduct = new JLabel();
		jlbltQuantityProduct.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		jlbltQuantityProduct.setForeground(Color.red);
		jlbltQuantityProduct.setText("Cantidad de Producto:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		returnJP0x0.add(jlbltQuantityProduct, gbc);
		
		SpinnerModel snmQuantity = new SpinnerNumberModel(0.0, 0.0, limit, 0.1);
		jspQuantity = new JSpinner();
		jspQuantity.setModel(snmQuantity);
		jspneQuantity = new JSpinner.NumberEditor(jspQuantity, "0.0");
		jspQuantity.setEditor(jspneQuantity);
		jspQuantity.setFont(new Font("Liberation Sans", Font.PLAIN, 36));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		returnJP0x0.add(jspQuantity, gbc);
		
		return returnJP0x0;
	}
	
	//Method that RETURNS the JPanel 0x1
	private JPanel returnJP0x1() {
		JPanel returnJP0x1 = new JPanel();
		returnJP0x1.setLayout(gbl);
		
		JLabel jlbltActualPrice = new JLabel();
		jlbltActualPrice.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		jlbltActualPrice.setForeground(Color.red);
		jlbltActualPrice.setText("Importe Actual:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		returnJP0x1.add(jlbltActualPrice, gbc);
		
		SpinnerModel spmPrice = new SpinnerNumberModel(0.00, 0.00, 1000.00, 0.10);
		jspPrice = new JSpinner();
		jspPrice.setModel(spmPrice);
		jspnePrice = new JSpinner.NumberEditor(jspPrice, "0.00");
		jspPrice.setEditor(jspnePrice);
		jspPrice.setFont(new Font("Liberation Sans", Font.PLAIN, 36));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		returnJP0x1.add(jspPrice, gbc);
		
		return returnJP0x1;
	}
	
	//Method that RETURNS the JPanel South
	private JPanel returnJPSouth() {
		JPanel returnJPSouth = new JPanel();
		returnJPSouth.setLayout(gbl);
		
		JLabel jlbltUnitaryPrice = new JLabel();
		jlbltUnitaryPrice.setFont(new Font("Liberation Sans", Font.BOLD, 32));
		jlbltUnitaryPrice.setForeground(Color.BLUE);
		jlbltUnitaryPrice.setText("Precio Unitario = $");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		returnJPSouth.add(jlbltUnitaryPrice, gbc);
		
		jlblUnitaryPrice = new JLabel();
		jlblUnitaryPrice.setFont(new Font("Liberation Sans", Font.BOLD, 32));
		jlblUnitaryPrice.setForeground(Color.BLUE);
		jlblUnitaryPrice.setText("0.00");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		returnJPSouth.add(jlblUnitaryPrice, gbc);
		return returnJPSouth;
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 14);
		
		jbtnAccept = new JButton();
		jbtnAccept.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/accept.png")));
		jbtnAccept.setText("Aceptar");
		jbtnAccept.setFont(fJButtons);
		jpActions.add(jbtnAccept);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
		jpActions.add(jbtnCancel);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		jpMain.add(jpActions, gbc);
	}
	
	public void setJOPMessages(String typeMessage, String message) {
		if(typeMessage.equals("limitReach"))
			JOptionPane.showMessageDialog(this, message, "Inventarios", JOptionPane.WARNING_MESSAGE);
	}
}
