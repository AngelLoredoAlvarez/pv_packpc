package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class JFMain extends JFrame {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	public JTextField jtxtfBarCode, jtxtfActiveUser, jtxtfFolioNumber, jtxtfDate, jtxtfTime;
	public JButton jbtnAddtoList, jbtnAddTicket, jbtnCancelTicket, jbtnRemoveProduct, jbtnCharge;
	public JMenu jmSystem, jmTill, jmDataBaseAction, jmTCourtCase, jmConsults;
	public JMenuItem jmiSBackupDB, jmiSRestoreDB, jmiSInvoice, jmiSLogOut, jmiSCloseSystem;
	public JMenuItem jmiTOpenTill, jmiTMovements, jmiTBuys, jmiTSales, jmiTCashierCourt, jmiTDayCourt;
	public JMenuItem jmiCClients, jmiCProviders, jmiCCashiers, jmiCInventories;
	public JMenuItem jmiRSells, jmiRBuys, jmiRInventory;
	public JLabel jlbllTotal, jlbllPay, jlbllChange, jlblTotal;
	public JTabbedPane jtpTickets;
	private DefaultTableModel dtmAddedProductService;
	private JTable jtAddedProductService;
	private JScrollPane jspJTProductServicetoSell;
	public HashMap<Integer, JTable> hmJTTickets;
	public JSpinner jspQuantity, jspPrice;
	public String actionDB = "";
	
	//Constructor
	public JFMain() {
		//Initializations NEEDED
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		hmJTTickets = new HashMap<Integer, JTable>();
		//GUI
		this.setJFMain();
		this.setJMenuBar();
		this.setJPMain();
		this.setJPNorth();
		this.setJPCenter();
		this.setJPSouth();
	}
	
	//Method that SETS the JFrame Main
	private void setJFMain() {
		Image jfIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/pv.png"));
		this.setIconImage(jfIcon);
		this.setTitle("Punto de Venta");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1280, 600));
	}
	
	//Method that SETS the JMenuBar
	private void setJMenuBar() {
		Font jmf = new Font("Liberation Sans", Font.BOLD, 14);
		Font jmif = new Font("Liberation Sans", Font.PLAIN, 14);
		JMenuBar jmbBar = new JMenuBar();
		
		//System===============================================================================================================
		jmSystem = new JMenu();
		jmSystem.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/system.png")));
		jmSystem.setText("Sistema");
		jmSystem.setFont(jmf);
		jmbBar.add(jmSystem);
		
		jmDataBaseAction = new JMenu();
		jmDataBaseAction.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/system.png")));
		jmDataBaseAction.setText("Base de Datos");
		jmDataBaseAction.setFont(jmf);
		jmDataBaseAction.setVisible(false);
		jmSystem.add(jmDataBaseAction);
		
		jmiSBackupDB = new JMenuItem();
		jmiSBackupDB.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/backupdb.png")));
		jmiSBackupDB.setText("Respaldar Base de Datos");
		jmiSBackupDB.setFont(jmif);
		jmiSBackupDB.setVisible(false);
		jmDataBaseAction.add(jmiSBackupDB);
		
		jmiSRestoreDB = new JMenuItem();
		jmiSRestoreDB.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/restoredb.png")));
		jmiSRestoreDB.setText("Restaurar Base de Datos");
		jmiSRestoreDB.setFont(jmif);
		jmiSRestoreDB.setVisible(false);
		jmDataBaseAction.add(jmiSRestoreDB);
		
		jmiSInvoice = new JMenuItem();
		jmiSInvoice.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/invoice.png")));
		jmiSInvoice.setText("Facturación");
		jmiSInvoice.setFont(jmif);
		jmiSInvoice.setVisible(false);
		jmSystem.add(jmiSInvoice);
		
		jmiSLogOut = new JMenuItem();
		jmiSLogOut.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/log_out.png")));
		jmiSLogOut.setText("Cerrar Sesión");
		jmiSLogOut.setFont(jmif);
		jmiSLogOut.setVisible(false);
		jmSystem.add(jmiSLogOut);
		
		jmiSCloseSystem = new JMenuItem();
		jmiSCloseSystem.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/exit.png")));
		jmiSCloseSystem.setText("Salir del Sistema");
		jmiSCloseSystem.setFont(jmif);
		jmSystem.add(jmiSCloseSystem);
		//System===============================================================================================================
		
		//Till=================================================================================================================
		jmTill = new JMenu();
		jmTill.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/till.png")));
		jmTill.setText("Caja");
		jmTill.setFont(jmf);
		jmbBar.add(jmTill);
		
		jmiTOpenTill = new JMenuItem();
		jmiTOpenTill.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/open_till.png")));
		jmiTOpenTill.setText("Abrir Caja");
		jmiTOpenTill.setFont(jmif);
		jmTill.add(jmiTOpenTill);
		
		jmiTMovements = new JMenuItem();
		jmiTMovements.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/till_movements.png")));
		jmiTMovements.setText("Movimientos");
		jmiTMovements.setFont(jmif);
		jmiTMovements.setVisible(false);
		jmTill.add(jmiTMovements);
		
		jmiTBuys = new JMenuItem();
		jmiTBuys.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/buys.png")));
		jmiTBuys.setText("Compras");
		jmiTBuys.setFont(jmif);
		jmiTBuys.setVisible(false);
		jmTill.add(jmiTBuys);
		
		jmiTSales = new JMenuItem();
		jmiTSales.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/sales.png")));
		jmiTSales.setText("Ventas");
		jmiTSales.setFont(jmif);
		jmTill.add(jmiTSales);
		
		jmTCourtCase = new JMenu();
		jmTCourtCase.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/court_case.png")));
		jmTCourtCase.setText("Corte de Caja");
		jmTCourtCase.setFont(jmf);
		jmTCourtCase.setVisible(false);
		jmTill.add(jmTCourtCase);
		
		jmiTCashierCourt = new JMenuItem();
		jmiTCashierCourt.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/court_case.png")));
		jmiTCashierCourt.setText("Corte de Cajero");
		jmiTCashierCourt.setFont(jmif);
		jmTCourtCase.add(jmiTCashierCourt);
		
		jmiTDayCourt = new JMenuItem();
		jmiTDayCourt.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/court_case.png")));
		jmiTDayCourt.setText("Corte del Día");
		jmiTDayCourt.setFont(jmif);
		jmiTDayCourt.setVisible(false);
		jmTCourtCase.add(jmiTDayCourt);
		//Till=================================================================================================================
		
		//Consults=============================================================================================================
		jmConsults = new JMenu();
		jmConsults.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/search.png")));
		jmConsults.setText("Consultas");
		jmConsults.setFont(jmf);
		jmConsults.setEnabled(false);
		jmbBar.add(jmConsults);
		
		jmiCClients = new JMenuItem();
		jmiCClients.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/clients.png")));
		jmiCClients.setText("Clientes");
		jmiCClients.setFont(jmif);
		jmConsults.add(jmiCClients);
		
		jmiCProviders = new JMenuItem();
		jmiCProviders.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/providers.png")));
		jmiCProviders.setText("Proveedores");
		jmiCProviders.setFont(jmif);
		jmConsults.add(jmiCProviders);
		
		jmiCCashiers = new JMenuItem();
		jmiCCashiers.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/cashiers.png")));
		jmiCCashiers.setText("Cajeros");
		jmiCCashiers.setFont(jmif);
		jmConsults.add(jmiCCashiers);
		
		jmiCInventories = new JMenuItem();
		jmiCInventories.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/inventories.png")));
		jmiCInventories.setText("Inventarios y Servicios");
		jmiCInventories.setFont(jmif);
		jmConsults.add(jmiCInventories);
		//Consults=============================================================================================================
		
//		//Help==============================================================================================================
//		JMenu jmHelp = new JMenu();
//		jmHelp.setIcon(new ImageIcon(getClass().getResource("/img/jmenubar/help.png")));
//		jmHelp.setText("Ayuda");
//		jmHelp.setFont(jmf);
//		jmbBar.add(jmHelp);
//		//Help==============================================================================================================
		
		//END
		this.setJMenuBar(jmbBar);
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
		
		//Add the JPanel Actions to the JPanel North
		JPanel jpActions = new JPanel();
		jpActions = this.setJPActions();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jpActions, gbc);

		//Add the JPanel Info to the JPanel North
		JPanel jpInfo = new JPanel();
		jpInfo = this.setJPInfo();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpNorth.add(jpInfo, gbc);
		
		//Add to the JPanel MAIN (THIS must be at the END of All)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpNorth, gbc);
	}
	
	//Method that RETURNS the JPanel Actions
	private JPanel setJPActions() {
		//Creates the JPanel North
		JPanel returnJPActions = new JPanel();
		returnJPActions.setLayout(gbl);
		
		//First JPanel===============================================================================
		JPanel jpBarCodeandServices = new JPanel();
		jpBarCodeandServices.setLayout(gbl);
		
		JLabel jlblImageBarCode = new JLabel();
		jlblImageBarCode.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/large/barcode.png")));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpBarCodeandServices.add(jlblImageBarCode, gbc);
				
		JLabel jlbltBarCode = new JLabel();
		jlbltBarCode.setText("Código de Barras:");
		jlbltBarCode.setFont(new Font("Liberation Sans", Font.BOLD, 32));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpBarCodeandServices.add(jlbltBarCode, gbc);
				
		jtxtfBarCode = new JTextField(10);
		jtxtfBarCode.setFont(new Font("Liberation Sans", Font.BOLD, 32));
		jtxtfBarCode.setEnabled(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpBarCodeandServices.add(jtxtfBarCode, gbc);
		
		jbtnAddtoList = new JButton();
		jbtnAddtoList.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/add.png")));
		jbtnAddtoList.setText("Agregar");
		jbtnAddtoList.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jbtnAddtoList.setEnabled(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpBarCodeandServices.add(jbtnAddtoList, gbc);
		//First JPanel===============================================================================
		
		//Second JPanel==============================================================================
		JPanel jpOptions = new JPanel();
		jpOptions.setLayout(gbl);
		
		jbtnAddTicket = new JButton();
		jbtnAddTicket.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/add.png")));
		jbtnAddTicket.setText("Nuevo Ticket");
		jbtnAddTicket.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jbtnAddTicket.setEnabled(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpOptions.add(jbtnAddTicket, gbc);
		
		jbtnCancelTicket = new JButton();
		jbtnCancelTicket.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/block.png")));
		jbtnCancelTicket.setText("Cancelar Ticket");
		jbtnCancelTicket.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jbtnCancelTicket.setEnabled(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpOptions.add(jbtnCancelTicket, gbc);
		
		jbtnRemoveProduct = new JButton();
		jbtnRemoveProduct.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/delete.png")));
		jbtnRemoveProduct.setText("Quitar Artículo");
		jbtnRemoveProduct.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jbtnRemoveProduct.setEnabled(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpOptions.add(jbtnRemoveProduct, gbc);
		
		jbtnCharge = new JButton();
		jbtnCharge.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/large/charge.png")));
		jbtnCharge.setText("Cobrar");
		jbtnCharge.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		jbtnCharge.setEnabled(false);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpOptions.add(jbtnCharge, gbc);
		//Second JPanel==============================================================================
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jpBarCodeandServices, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPActions.add(jpOptions, gbc);
		
		return returnJPActions;
	}
	
	//Method that RETURNS the JPanel Info
	private JPanel setJPInfo() {
		//JPanel with ALL relative to the NEEDED INFO
		JPanel returnJPInfo = new JPanel();
		returnJPInfo.setLayout(gbl);
		
		JLabel jlbltActiveUser = new JLabel();
		jlbltActiveUser.setText("Usuario Activo:");
		jlbltActiveUser.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInfo.add(jlbltActiveUser, gbc);
				
		jtxtfActiveUser = new JTextField();
		jtxtfActiveUser.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfActiveUser.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInfo.add(jtxtfActiveUser, gbc);
				
		JLabel jlbltFolioNumber = new JLabel();
		jlbltFolioNumber.setText("No. de Folio:");
		jlbltFolioNumber.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInfo.add(jlbltFolioNumber, gbc);
				
		jtxtfFolioNumber = new JTextField();
		jtxtfFolioNumber.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfFolioNumber.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPInfo.add(jtxtfFolioNumber, gbc);
		
		JLabel jlbltDate = new JLabel();
		jlbltDate.setText("Fecha:");
		jlbltDate.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor =  GridBagConstraints.CENTER;
		returnJPInfo.add(jlbltDate, gbc);
		
		jtxtfDate = new JTextField();
		jtxtfDate.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfDate.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor =  GridBagConstraints.CENTER;
		returnJPInfo.add(jtxtfDate, gbc);
		
		JLabel jlbltTime = new JLabel();
		jlbltTime.setText("Hora:");
		jlbltTime.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor =  GridBagConstraints.CENTER;
		returnJPInfo.add(jlbltTime, gbc);
		
		jtxtfTime = new JTextField();
		jtxtfTime.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
		jtxtfTime.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor =  GridBagConstraints.CENTER;
		returnJPInfo.add(jtxtfTime, gbc);
		
		return returnJPInfo;
	}
	
	//Method that SETS the JPanel Center
	private void setJPCenter() {
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(gbl);
		
		jtpTickets = new JTabbedPane();
		jtpTickets.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		jtpTickets.addTab("Ticket 1", this.setJTTicket());
		jtpTickets.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpCenter.add(jtpTickets, gbc);
		
		//END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpCenter, gbc);
	}
	
	//Method that RETURNS the TICKET
	public JScrollPane setJTTicket() {
		dtmAddedProductService = new DefaultTableModel();
		dtmAddedProductService.addColumn("Código de Barras");
		dtmAddedProductService.addColumn("Descripión del Producto");
		dtmAddedProductService.addColumn("Unidad");
		dtmAddedProductService.addColumn("Cantidad");
		dtmAddedProductService.addColumn("Importe");
		
		jtAddedProductService = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				if(column == 3)
					return true;
				else
					return false;
			}
		};
		jtAddedProductService.setModel(dtmAddedProductService);
		jtAddedProductService.setFont(new Font("Liberation Sans", Font.PLAIN, 20));
		jtAddedProductService.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtAddedProductService.setRowHeight(20);
		jtAddedProductService.setDefaultRenderer(Object.class, new RendererJTableTicket());
		
		//COLUNMS FONT
		JTableHeader jtHeaders = new JTableHeader();
		jtHeaders = jtAddedProductService.getTableHeader();
		jtHeaders.setFont(new Font("Liberation Sans", Font.BOLD, 18));
		
		//COLUNMS WIDTH
		TableColumn colunm = null;
		for(int a = 0; a < jtAddedProductService.getColumnCount(); a++) {
			colunm = jtAddedProductService.getColumnModel().getColumn(a);
			if(a == 0) {
				colunm.setPreferredWidth(150);
			} else if(a == 1) {
				colunm.setPreferredWidth(650);
			} else if(a == 4) {
				colunm.setPreferredWidth(100);
			}
		}
		//Adds the JTable to the HashMap tickets
		hmJTTickets.put(jtpTickets.getTabCount() + 1, jtAddedProductService);
		
		//Adds the JTable to the JScrollPane
		jspJTProductServicetoSell = new JScrollPane(jtAddedProductService);
		
		return jspJTProductServicetoSell;
	}
	
	//Class that RENDERES the JTable
	class RendererJTableTicket extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.PLAIN, 24));
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(isSelected) {
					cell.setForeground(Color.WHITE);
				} else {
					if(column == 5)
						cell.setForeground(Color.GREEN);
					else
						cell.setForeground(Color.BLACK);
				}
			}
			
			return cell;
		}
	}
	
	//Method that SETS the JPanel South
	private void setJPSouth() {
		JPanel jpSouth = new JPanel();
		jpSouth.setLayout(gbl);

		JPanel jpLastTotal = new JPanel();
		jpLastTotal = this.setJPLastSell();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpSouth.add(jpLastTotal, gbc);
		
		JPanel jpTotal = new JPanel();
		jpTotal = this.setJPTotal();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpSouth.add(jpTotal, gbc);
		
		//Add to the JPanel Main
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		jpMain.add(jpSouth, gbc);
	}
	
	//Method that RETURNS the JPanel Last Sell
	private JPanel setJPLastSell() {
		JPanel returnJPLastSell = new JPanel();
		returnJPLastSell.setLayout(gbl);
		returnJPLastSell.setBorder(
				BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.BLACK),
					"Última Venta:",
					TitledBorder.ABOVE_BOTTOM,
					TitledBorder.TOP,
					new Font("Liberation Sans", Font.BOLD, 24)
				)
		);
		
		Font fTitles = new Font("Liberation Sans", Font.BOLD, 24);
		Font fData = new Font("Liberation Sans", Font.PLAIN, 24);
		
		//TOTAL==============================================================================================
		JLabel jlbltlTotal = new JLabel();
		jlbltlTotal.setText("Total: ");
		jlbltlTotal.setFont(fTitles);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbltlTotal, gbc);
		
		JLabel jlblcslt = new JLabel();
		jlblcslt.setText("$");
		jlblcslt.setFont(fData);
		jlblcslt.setForeground(Color.BLUE);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlblcslt, gbc);
		
		jlbllTotal = new JLabel();
		jlbllTotal.setText("0.00");
		jlbllTotal.setFont(fData);
		jlbllTotal.setForeground(Color.BLUE);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbllTotal, gbc);
		//TOTAL==============================================================================================
		
		//PAY WITH==============================================================================================
		JLabel jlbltlPay = new JLabel();
		jlbltlPay.setText("Pagó con: ");
		jlbltlPay.setFont(fTitles);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbltlPay, gbc);
		
		JLabel jlblcslp = new JLabel();
		jlblcslp.setText("$");
		jlblcslp.setFont(fData);
		jlblcslp.setForeground(Color.BLUE);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlblcslp, gbc);

		jlbllPay = new JLabel();
		jlbllPay.setText("0.00");
		jlbllPay.setFont(fData);
		jlbllPay.setForeground(Color.BLUE);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbllPay, gbc);
		//PAY WITH==============================================================================================
		
		//CHANGE==============================================================================================
		JLabel jlbltlChange = new JLabel();
		jlbltlChange.setText("Cambió: ");
		jlbltlChange.setFont(fTitles);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbltlChange, gbc);
		
		JLabel jlblcslc = new JLabel();
		jlblcslc.setText("$");
		jlblcslc.setFont(fData);
		jlblcslc.setForeground(Color.BLUE);
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlblcslc, gbc);
		
		jlbllChange = new JLabel();
		jlbllChange.setText("0.00");
		jlbllChange.setFont(fData);
		jlbllChange.setForeground(Color.BLUE);
		gbc.gridx = 5;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		returnJPLastSell.add(jlbllChange, gbc);
		//CHANGE==============================================================================================
		
		return returnJPLastSell;
	}
	
	//Method that RETURNS the JPanel TOTAL
	private JPanel setJPTotal() {
		JPanel returnJPTotal = new JPanel();
		returnJPTotal.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel jlbltTotal = new JLabel();
		jlbltTotal.setText("Total: $ ");
		jlbltTotal.setFont(new Font("Liberation Sans", Font.BOLD, 54));
		returnJPTotal.add(jlbltTotal);
		
		jlblTotal = new JLabel();
		jlblTotal.setText("0.00");
		jlblTotal.setFont(new Font("Liberation Sans", Font.BOLD, 54));
		jlblTotal.setForeground(Color.BLUE);
		returnJPTotal.add(jlblTotal);
		
		JLabel jlbltMXN = new JLabel();
		jlbltMXN.setText(" MXN");
		jlbltMXN.setFont(new Font("Liberation Sans", Font.BOLD, 54));
		returnJPTotal.add(jlbltMXN);
		
		return returnJPTotal;
	}
	
	//Method that SHOWS UP the JFileChooser to Backup the DB
	public String[] jfcActionDB() {
		String[] dataForAction = null;
		dataForAction = new String[2];
		
		JFileChooser jfcPath = new JFileChooser();
		if(actionDB.equals("backup"))
			jfcPath.setDialogTitle("Respaldar Base de Datos...");
		else if(actionDB.equals("restore"))
			jfcPath.setDialogTitle("Restaurar Base de Datos...");
		jfcPath.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcPath.setAcceptAllFileFilterUsed(false);
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Archivo SQL", "sql"));
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Archivo TXT", "txt"));
		int action = jfcPath.showSaveDialog(this);
		
		if(action == JFileChooser.APPROVE_OPTION) {
			dataForAction[0] = jfcPath.getSelectedFile().getAbsolutePath();
			if(jfcPath.getFileFilter() instanceof FileNameExtensionFilter) {
				String[] extensions = ((FileNameExtensionFilter)jfcPath.getFileFilter()).getExtensions();
				for (String ext : extensions) {
		          dataForAction[1] = ext;
		        }
			}
		} else {
			dataForAction = null;
		}
		
		return dataForAction;
	}
	
	//Method that SETS the MESSAGES for the User
	public String setJOPMessages(String typeMessage, String message) {
		String response = "";
		if(typeMessage.equals("noTillOpened"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("notExist")) 
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("removePieceProduct")) 
			response = String.valueOf(JOptionPane.showInputDialog(this, message, "Punto de Venta", JOptionPane.QUESTION_MESSAGE, null, null, null));
		else if(typeMessage.equals("fileExist"))
			response = String.valueOf(JOptionPane.showInputDialog(this, message, "Punto de Venta", JOptionPane.QUESTION_MESSAGE, null, null, null));
		else if(typeMessage.equals("backupOK"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("restoreOK"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("reachMinStock"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("lessMinStock"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("outOfStock"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.ERROR_MESSAGE);
		else if(typeMessage.equals("limitReach"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.WARNING_MESSAGE);
		else if(typeMessage.equals("noBuys"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("noSales"))
			JOptionPane.showMessageDialog(this, message, "Punto de Venta", JOptionPane.INFORMATION_MESSAGE);
		
		return response;
	}
}
