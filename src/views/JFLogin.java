package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JFLogin extends JFrame {
	//Global Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JComboBox<String> jcbUserName;
	public JTextField jtxtfUserName;
	public JPasswordField jpfUserPass;
	public JButton jbtnLogIn, jbtnExit;
	
	//Constructor
	public JFLogin() {
		//Initializations needed
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		//Construct the GUI
		this.setJFrame();
		this.setJPMain();
		this.setJPNorth();
		this.setJPImage();
		this.setJPComponents();
	}
	
	//Method that SETS the JFrame
	private void setJFrame() {
		Image iconJFrame = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/lock.png"));
		this.setIconImage(iconJFrame);
		this.setTitle("Inicio de Sesión");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(400, 250));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
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
		jpNorth.setLayout(new FlowLayout());
		JLabel jlbltWelcome = new JLabel();
		jlbltWelcome.setText("Inicio de Sesión");
		jlbltWelcome.setFont(new Font("Liberation Sans", Font.BOLD, 24));
		jpNorth.add(jlbltWelcome);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		jpMain.add(jpNorth, gbc);
	}
	
	//Method that SETS the JPanel Image
	private void setJPImage() {
		Image imageLogin = new ImageIcon(getClass().getResource("/img/images/userslogin.png")).getImage();
		JPanel jpImage = new JPanel() {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics graphics) {
				graphics.drawImage(imageLogin, 0, 0, getWidth(), getHeight(), this);
			}
		};
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jpMain.add(jpImage, gbc);
	}
	
	//Method that SETS the JPanel Components
	private void setJPComponents() {
		JPanel jpComponents = new JPanel();
		jpComponents.setLayout(gbl);
		
		JLabel jlbltUserName = new JLabel();
		jlbltUserName.setText("Usuario:");
		jlbltUserName.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(2, 0, 1, 2);
		jpComponents.add(jlbltUserName, gbc);
		
		jcbUserName = new JComboBox<String>();
		jcbUserName.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jcbUserName.setEditable(true);
		jcbUserName.requestFocus();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpComponents.add(jcbUserName, gbc);
		
		jtxtfUserName = (JTextField) jcbUserName.getEditor().getEditorComponent();
		
		JLabel jlbltUserPass = new JLabel();
		jlbltUserPass.setText("Contraseña:");
		jlbltUserPass.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpComponents.add(jlbltUserPass, gbc);
		
		jpfUserPass = new JPasswordField();
		jpfUserPass.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jpfUserPass.setColumns(15);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpComponents.add(jpfUserPass, gbc);
		
		jbtnLogIn = new JButton();
		jbtnLogIn.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/key.png")));
		jbtnLogIn.setText("Iniciar Sesión");
		jbtnLogIn.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpComponents.add(jbtnLogIn, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		jpComponents.add(jbtnExit, gbc);
		
		//END
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jpMain.add(jpComponents, gbc);
	}
	
	//Method that SETS the Corresponding MESSAGE
	public void setJOPMessage(String typeMessage, String message) {
		if(typeMessage.equals("dirs")) {
			JOptionPane.showMessageDialog(this, message, "Administrador del Sistema", JOptionPane.INFORMATION_MESSAGE);
		} else if(typeMessage.equals("incorrectUNorP")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		} else if(typeMessage.equals("changeADPW")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		} else if(typeMessage.equals("changePW")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		} else if(typeMessage.equals("newCashier")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		} else if(typeMessage.equals("unclosedTurn")) {
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		}
	}
}
