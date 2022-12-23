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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDActionInventory extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public String name = "", unit_measurement = "";
	private Font fJLabelsT, fJLabels;
	public JLabel jlblDescription, jlblActualQuantity;
	private SpinnerModel spnmModel;
	public JSpinner jspQuantity;
	private JSpinner.NumberEditor jspneNumberEditor;
	public JButton jbtnAction, jbtnCancel;
	public String father = "";
	
	//Constructor
	public JDActionInventory(Window owner, String icon, String name, String father, String unit_measurement) {
		super(owner);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon));
		this.setIconImage(jdIcon);
		this.setTitle(name);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
		this.name = name;
		this.father = father;
		this.unit_measurement = unit_measurement;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fJLabelsT = new Font("Liberation Sans", Font.BOLD, 28);
		fJLabels = new Font("Liberation Sans", Font.PLAIN, 28);
		//GUI
		this.setJPMain();
		this.setJPDescription();
		this.setJPActualQuantity();
		this.setJPNewQuantity();
		this.setJPActions();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Description
	private void setJPDescription() {
		JPanel jpDescription = new JPanel();
		jpDescription.setLayout(gbl);
		
		JLabel jlbltDescription = new JLabel();
		jlbltDescription.setText("Descripción:");
		jlbltDescription.setFont(fJLabelsT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 5);
		jpDescription.add(jlbltDescription, gbc);
		
		jlblDescription = new JLabel();
		jlblDescription.setFont(fJLabels);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpDescription.add(jlblDescription, gbc);
		
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
		jpMain.add(jpDescription, gbc);
	}
	
	//Method that SETS the JPanel Actual Quantity
	private void setJPActualQuantity() {
		JPanel jpActualQuantity = new JPanel();
		jpActualQuantity.setLayout(gbl);
		
		JLabel jlbltActualQuantity = new JLabel();
		jlbltActualQuantity.setText("Cantidad Actual:");
		jlbltActualQuantity.setFont(fJLabelsT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 5);
		jpActualQuantity.add(jlbltActualQuantity, gbc);
		
		jlblActualQuantity = new JLabel();
		jlblActualQuantity.setFont(fJLabels);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpActualQuantity.add(jlblActualQuantity, gbc);
		
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
		jpMain.add(jpActualQuantity, gbc);
	}
	
	//Method that SETS the JPanel New Quantity
	private void setJPNewQuantity() {
		JPanel jpNewQuantity = new JPanel();
		jpNewQuantity.setLayout(gbl);
		
		JLabel jlbltQuantity = new JLabel();
		jlbltQuantity.setText("Cantidad:");
		jlbltQuantity.setFont(fJLabelsT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 5);
		jpNewQuantity.add(jlbltQuantity, gbc);
		
		if(unit_measurement.equals("Pza")) 
			spnmModel = new SpinnerNumberModel(1, 1, 100000, 1);
		else if(unit_measurement.equals("Granel")) 
			spnmModel = new SpinnerNumberModel(1.0, 1.0, 100000.00, 0.1);
		jspQuantity = new JSpinner();
		jspQuantity.setModel(spnmModel);
		if(unit_measurement.equals("Pza")) 
			jspneNumberEditor = new JSpinner.NumberEditor(jspQuantity, "0");
		else if(unit_measurement.equals("Granel")) 
			jspneNumberEditor = new JSpinner.NumberEditor(jspQuantity, "0.00");
		jspQuantity.setEditor(jspneNumberEditor);
		jspQuantity.setFont(fJLabels);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 200);
		jpNewQuantity.add(jspQuantity, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpNewQuantity, gbc);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 14);
		String nameJButton = "", iconJButton = "";
		jbtnAction = new JButton();
		if(name.equals("Agregar a Inventario")) {
			nameJButton = "Agregar a Inventario";
			iconJButton = "/img/jbuttons/medium/add.png";
		} else if(name.equals("Cantidad de Producto a Comprar")) {
			nameJButton = "Agregar a Lista";
			iconJButton = "/img/jbuttons/medium/add.png";
		} else {
			nameJButton = "Ajustar Inventario";
			iconJButton = "/img/jbuttons/medium/edit.png";
		}
		jbtnAction.setIcon(new ImageIcon(getClass().getResource(iconJButton)));
		jbtnAction.setText(nameJButton);
		jbtnAction.setFont(fJButtons);
		jpActions.add(jbtnAction);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
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
		if(typeMessage.equals("addToInventory"))
			JOptionPane.showMessageDialog(this, message, "Inventarios", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("adjustInventory"))
			JOptionPane.showMessageDialog(this, message, "Inventarios", JOptionPane.INFORMATION_MESSAGE);
	}
}
