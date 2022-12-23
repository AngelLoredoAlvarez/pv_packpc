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
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JDCloseSystem extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JButton jbtnCloseTurn, jbtnCloseAndLetOpen, jbtnCancel;
	
	//Constructor
	public JDCloseSystem(JFMain jfSells) {
		super(jfSells);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/close_system.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Salir del Punto de Venta");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(350, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(jfSells);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//GUI
		this.setJPMain();
		this.setJPNorth();
		this.setJPCenter();
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
		
		JLabel jlbltWWYD = new JLabel();
		jlbltWWYD.setText("Antes de Cerrar el Sistema, elige una Opción:");
		jlbltWWYD.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpNorth.add(jlbltWWYD, gbc);
		
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
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnCloseTurn = new JButton();
		jbtnCloseTurn.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/close_turn.png")));
		jbtnCloseTurn.setText("Cerrar el Turno y Salir");
		jbtnCloseTurn.setFont(fJButtons);
		jbtnCloseTurn.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 0, 10);
		jpCenter.add(jbtnCloseTurn, gbc);
		
		jbtnCloseAndLetOpen = new JButton();
		jbtnCloseAndLetOpen.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/close_and_let_open.png")));
		jbtnCloseAndLetOpen.setText("Dejar turno Abierto y Salir");
		jbtnCloseAndLetOpen.setFont(fJButtons);
		jbtnCloseAndLetOpen.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 0, 10);
		jpCenter.add(jbtnCloseAndLetOpen, gbc);
		
		JTextArea jtxtaInfo = new JTextArea();
		jtxtaInfo.setText("Sí dejas abierto tú Turno, solo tú o un Administrador" + "\n" + "podrá abrir nuevamente el Sistema");
		jtxtaInfo.setFont(fJButtons);
		jtxtaInfo.setEditable(false);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpCenter.add(jtxtaInfo, gbc);
		
		jbtnCancel = new JButton();
		jbtnCancel.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/delete.png")));
		jbtnCancel.setText("Cancelar");
		jbtnCancel.setFont(fJButtons);
		jbtnCancel.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);
		jpCenter.add(jbtnCancel, gbc);
		
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
}
