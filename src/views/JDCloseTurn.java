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
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JDCloseTurn extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private Font fJTextArea, fJLabels, fJButtons;
	public JLabel jlblExpectedCash, jlblDifference, jlbliStatusIcon, jlblStatus;
	public JSpinner.NumberEditor jspneTillCash;
	public JSpinner jspTillCash;
	public JButton jbtnCloseTurn, jbtnCancel;
	public String whoIsCalling = "", typeCourt = "";
	
	//Constructor
	public JDCloseTurn(JDialog jdDialog, String whoIsCalling, String typeCourt) {
		super(jdDialog);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/close_turn.png"));
		this.setIconImage(jdIcon);
		this.setTitle(typeCourt);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(500, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(jdDialog);
		this.whoIsCalling = whoIsCalling;
		this.typeCourt = typeCourt;
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fJTextArea = new Font("Liberation Sans", Font.PLAIN, 22);
		fJLabels = new Font("Liberation Sans", Font.BOLD, 22);
		fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		//GUI
		this.setJPMain();
		this.setJPNorth();
		this.setJPCenter();
		this.setJPSouth();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel North
	private void setJPNorth() {
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(gbl);
		
		JTextArea jtxtaDo = new JTextArea();
		jtxtaDo.setText("Por favor, cuenta el Dinero en Caja e Ingrésalo"
				      + "\n"
				      + "para proceder con el Cierre de Turno Actual.");
		jtxtaDo.setFont(fJTextArea);
		jtxtaDo.setEditable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpNorth.add(jtxtaDo, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpNorth, gbc);
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		JPanel jpExpectedCash = new JPanel();
		jpExpectedCash = this.returnJPExpectedCash();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpExpectedCash, gbc);
		
		JPanel jpTillCash = new JPanel();
		jpTillCash = this.returnJPTillCash();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpTillCash, gbc);
		
		JPanel jpDifference = new JPanel();
		jpDifference = this.returnJPDifference();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpDifference, gbc);
		
		JPanel jpStatus = new JPanel();
		jpStatus = this.returnJPStatus();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpCenter.add(jpStatus, gbc);
		
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
	private JPanel returnJPExpectedCash() {
		JPanel returnJPExpectedCash = new JPanel();
		returnJPExpectedCash.setLayout(gbl);
		
		JLabel jlbltExpectedCash = new JLabel();
		jlbltExpectedCash.setText("Efectivo esperado en Caja: ");
		jlbltExpectedCash.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPExpectedCash.add(jlbltExpectedCash, gbc);
		
		jlblExpectedCash = new JLabel();
		jlblExpectedCash.setFont(fJLabels);
		jlblExpectedCash.setForeground(Color.BLUE);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPExpectedCash.add(jlblExpectedCash, gbc);
		
		return returnJPExpectedCash;
	}
	private JPanel returnJPTillCash() {
		JPanel returnJPTillCash = new JPanel();
		returnJPTillCash.setLayout(gbl);
		
		JLabel jlbltTillCash = new JLabel();
		jlbltTillCash.setText("¿Efectivo en Caja?: $");
		jlbltTillCash.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillCash.add(jlbltTillCash, gbc);
		
		SpinnerModel spmTillCash = new SpinnerNumberModel(0.00, 0.00, 1000000.00, 1.00);
		jspTillCash = new JSpinner();
		jspTillCash.setModel(spmTillCash);
		jspneTillCash = new JSpinner.NumberEditor(jspTillCash, "#,###,##0.00");
		jspTillCash.setEditor(jspneTillCash);
		jspTillCash.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPTillCash.add(jspTillCash, gbc);
		
		return returnJPTillCash;
	}
	private JPanel returnJPDifference() {
		JPanel returnJPDifference = new JPanel();
		returnJPDifference.setLayout(gbl);
		
		JLabel jlbltDifference = new JLabel();
		jlbltDifference.setText("Diferencia: $");
		jlbltDifference.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDifference.add(jlbltDifference, gbc);
		
		jlblDifference = new JLabel();
		jlblDifference.setText("0.00");
		jlblDifference.setFont(fJLabels);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDifference.add(jlblDifference, gbc);
		
		return returnJPDifference;
	}
	private JPanel returnJPStatus() {
		JPanel returnJPStatus = new JPanel();
		returnJPStatus.setLayout(gbl); 
		
		jlbliStatusIcon = new JLabel();
		jlbliStatusIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/accept.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPStatus.add(jlbliStatusIcon, gbc);
		
		jlblStatus = new JLabel();
		jlblStatus.setText("¡Excelente!, Todo en orden");
		jlblStatus.setFont(fJLabels);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPStatus.add(jlblStatus, gbc);
		
		return returnJPStatus;
	}
	
	//Method that SETS the JPanel South
	private void setJPSouth() {
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		jbtnCloseTurn = new JButton();
		jbtnCloseTurn.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/close_turn.png")));
		jbtnCloseTurn.setText("Cerrar Turno");
		jbtnCloseTurn.setFont(fJButtons);
		jbtnCloseTurn.setFocusable(false);
		jpSouth.add(jbtnCloseTurn);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/medium/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
		jbtnCancel.setFocusable(false);
		jpSouth.add(jbtnCancel);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMain.add(jpSouth, gbc);
	}
}
