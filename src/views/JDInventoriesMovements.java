package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class JDInventoriesMovements extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	private JPanel jpMain;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private Font fJLabels;
	public JDatePickerImpl jdpDate;
	public JRadioButton jrbProduct, jrbCashier;
	public JTextField jtxtfSearch;
	public JComboBox<String> jcbMovements;
	public JButton jbtnExit, jbtnGenerateSpreadsheet, jbtnGenerateReport, jbtnPrintMovements;
	public DefaultTableModel dtmInventoriesMovements;
	public JTable jtInventoriesMovements;
	public TableRowSorter<TableModel> trsSorter;
	public String nameSpreadSheet = "", filtering = "";
	public ProgressMonitor pmAction;
	
	//Constructor
	public JDInventoriesMovements(JDConsultInventoriesServices jdInventories) {
		super(jdInventories);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/inventory_movements.png"));
		this.setIconImage(jdIcon);
		this.setTitle("Movimientos en Inventarios");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1024, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(jdInventories);
		//Initializations
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fJLabels = new Font("Liberation Sans", Font.BOLD, 14);
		//GUI
		this.setJPMain();
		this.setJPActions();
		this.setJPActionsUser();
		this.setJPMovements();
	}
	
	//Method that SETS the JPanel Main
	private void setJPMain() {
		jpMain = new JPanel();
		jpMain.setLayout(gbl);
		this.add(jpMain);
	}
	
	//Method that SETS the JPanel Actions
	private void setJPActions() {
		JPanel jpActions = new JPanel();
		jpActions.setLayout(gbl);
		
		JPanel jpDate = new JPanel();
		jpDate = this.returnJPDate();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 5, 0, 3);
		jpActions.add(jpDate, gbc);
		
		JPanel jpMovements = new JPanel();
		jpMovements = this.returnJPMovements();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpActions.add(jpMovements, gbc);
		
		JPanel jpFilter = new JPanel();
		jpFilter = this.returnJPFilter();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 5);
		jpActions.add(jpFilter, gbc);
		
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
		jpMain.add(jpActions, gbc);
	}
	private JPanel returnJPDate() {
		JPanel returnJPDate = new JPanel();
		returnJPDate.setLayout(gbl);
		
		JLabel jlbltFromDay = new JLabel();
		jlbltFromDay.setText("Del día:");
		jlbltFromDay.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDate.add(jlbltFromDay, gbc);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePanel.setFocusable(false);
		jdpDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		jdpDate.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPDate.add(jdpDate, gbc);
		
		return returnJPDate;
	}
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		Date actualDate = new Date();
		SimpleDateFormat sdfTurnDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
	    
		@Override
	    public Object stringToValue(String text) throws ParseException {
	    	return sdfTurnDate.parseObject(text);
	    }
	    
	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	        	Calendar selectedDate = (Calendar) value;
	        	return sdfTurnDate.format(selectedDate.getTime());
	        }
	        return sdfTurnDate.format(actualDate);
	    }
	}
	private JPanel returnJPMovements() {
		JPanel returnJPMovements = new JPanel();
		returnJPMovements.setLayout(gbl);
		
		JLabel jlbltMovements = new JLabel();
		jlbltMovements.setText("Movimientos:");
		jlbltMovements.setFont(fJLabels);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPMovements.add(jlbltMovements, gbc);
		
		String[] movements = {"- Todos -", "Entradas", "Salidas", "Ajustes", "Devoluciones"};
		jcbMovements = new JComboBox<String>(movements);
		jcbMovements.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		jcbMovements.setSelectedIndex(0);
		jcbMovements.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPMovements.add(jcbMovements, gbc);
		
		return returnJPMovements;
	}
	private JPanel returnJPFilter() {
		JPanel returnJPFilter = new JPanel();
		returnJPFilter.setLayout(gbl);
		
		JPanel jpFilterBy = new JPanel();
		jpFilterBy = this.returnJPFilterBy();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jpFilterBy, gbc);
		
		JLabel jlbliSearch = new JLabel();
		jlbliSearch.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/medium/search.png")));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jlbliSearch, gbc);
		
		JLabel jlbltSearch = new JLabel();
		jlbltSearch.setText("Búsqueda:");
		jlbltSearch.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jlbltSearch, gbc);
		
		jtxtfSearch = new JTextField(18);
		jtxtfSearch.setFont(new Font("Liberation Sans", Font.PLAIN, 16));
		jtxtfSearch.requestFocus();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		returnJPFilter.add(jtxtfSearch, gbc);
		
		jbtnExit = new JButton();
		jbtnExit.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/exit.png")));
		jbtnExit.setText("Salir");
		jbtnExit.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		jbtnExit.setFocusable(false);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilter.add(jbtnExit, gbc);
		
		return returnJPFilter;
	}
	private JPanel returnJPFilterBy() {
		JPanel returnJPFilterBy = new JPanel();
		returnJPFilterBy.setLayout(gbl);
		returnJPFilterBy.setBorder(
			BorderFactory.createTitledBorder(
				null,
				"Búsqueda por:",
				TitledBorder.ABOVE_BOTTOM,
				TitledBorder.TOP,
				new Font("Liberation Sans", Font.BOLD, 14)
			)
		);
		
		Font fJRadioButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jrbProduct = new JRadioButton();
		jrbProduct.setText("Descripción de Producto");
		jrbProduct.setFont(fJRadioButtons);
		jrbProduct.setSelected(true);
		jrbProduct.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterBy.add(jrbProduct, gbc);
		
		jrbCashier = new JRadioButton();
		jrbCashier.setText("Nombre de Cajero");
		jrbCashier.setFont(fJRadioButtons);
		jrbCashier.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		returnJPFilterBy.add(jrbCashier, gbc);
		
		ButtonGroup bgSearchBy = new ButtonGroup();
		bgSearchBy.add(jrbProduct);
		bgSearchBy.add(jrbCashier);
		
		return returnJPFilterBy;
	}
	
	//Method that SETS the JPanel Actions User
	private void setJPActionsUser() {
		JPanel jpActionsUser = new JPanel();
		jpActionsUser.setLayout(gbl);
		
		Font fJButtons = new Font("Liberation Sans", Font.PLAIN, 12);
		
		jbtnGenerateSpreadsheet = new JButton();
		jbtnGenerateSpreadsheet.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/spreadsheet.png")));
		jbtnGenerateSpreadsheet.setText("Generar Hoja de Cálculo");
		jbtnGenerateSpreadsheet.setFont(fJButtons);
		jbtnGenerateSpreadsheet.setFocusable(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 3, 0, 3);
		jpActionsUser.add(jbtnGenerateSpreadsheet, gbc);
		
		jbtnGenerateReport = new JButton();
		jbtnGenerateReport.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/report.png")));
		jbtnGenerateReport.setText("Generar Reporte");
		jbtnGenerateReport.setFont(fJButtons);
		jbtnGenerateReport.setFocusable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpActionsUser.add(jbtnGenerateReport, gbc);
		
		jbtnPrintMovements = new JButton();
		jbtnPrintMovements.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/printer.png")));
		jbtnPrintMovements.setText("Imprimir Movimientos");
		jbtnPrintMovements.setFont(fJButtons);
		jbtnPrintMovements.setFocusable(false);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 3);
		jpActionsUser.add(jbtnPrintMovements, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpMain.add(jpActionsUser, gbc);
	}
	public String[] retrivedDataForSaveSpreadSheet() {
		String[] dataForSave = null;
		dataForSave = new String[2];
		JFileChooser jfcPath = new JFileChooser();
		jfcPath.setDialogTitle("Guardar como...");
		jfcPath.setSelectedFile(new File(nameSpreadSheet));
		jfcPath.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcPath.setAcceptAllFileFilterUsed(false);
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Libro de Excel", "xlsx"));
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003", "xls"));
		int action = jfcPath.showSaveDialog(this);
		if(action == JFileChooser.APPROVE_OPTION) {
			dataForSave[0] = jfcPath.getSelectedFile().getAbsolutePath();
			if(jfcPath.getFileFilter() instanceof FileNameExtensionFilter) {
				String[] extensions = ((FileNameExtensionFilter)jfcPath.getFileFilter()).getExtensions();
				for (String ext : extensions) {
		          dataForSave[1] = ext;
		        }
			}
		} else {
			dataForSave = null;
		}
		
		return dataForSave;
	}	
	
	//Method that SETS the JPanel Movements
	private void setJPMovements() {
		JPanel jpMovements = new JPanel();
		jpMovements.setLayout(gbl);
		
		dtmInventoriesMovements = new DefaultTableModel();
		dtmInventoriesMovements.addColumn("Hora");
		dtmInventoriesMovements.addColumn("Descrición del Producto");
		dtmInventoriesMovements.addColumn("Había");
		dtmInventoriesMovements.addColumn("Movimiento");
		dtmInventoriesMovements.addColumn("Cantidad");
		dtmInventoriesMovements.addColumn("Cajero");
		
		jtInventoriesMovements = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtInventoriesMovements.setModel(dtmInventoriesMovements);
		jtInventoriesMovements.setRowHeight(20);
		jtInventoriesMovements.setDefaultRenderer(Object.class, new RendererJTableInventoriesMovements());
		jtInventoriesMovements.setFocusable(false);
		jtInventoriesMovements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		trsSorter = new TableRowSorter<TableModel>(jtInventoriesMovements.getModel());
		jtInventoriesMovements.setRowSorter(trsSorter);
		
		JTableHeader jthInventoriesMovements = new JTableHeader();
		jthInventoriesMovements = jtInventoriesMovements.getTableHeader();
		jthInventoriesMovements.setFont(new Font("Liberation Sans", Font.BOLD, 14));
		
		TableColumn column = null;
		for(int a = 0; a < jtInventoriesMovements.getColumnCount(); a++) {
			column = jtInventoriesMovements.getColumnModel().getColumn(a);
			if(a == 0)
				column.setPreferredWidth(10);
			if(a == 1)
				column.setPreferredWidth(180);
			if(a == 2)
				column.setPreferredWidth(10);
			if(a == 3)
				column.setPreferredWidth(20);
			if(a == 4)
				column.setPreferredWidth(20);
			if(a == 5)
				column.setPreferredWidth(200);
		}
		
		JScrollPane jspJTableInventoriesMovements = new JScrollPane(jtInventoriesMovements);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpMovements.add(jspJTableInventoriesMovements, gbc);
		
		//This has to be at the END
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpMain.add(jpMovements, gbc);
	}
	private class RendererJTableInventoriesMovements extends DefaultTableCellRenderer {
		//Attributes
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(value instanceof String) {
				cell.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				
				if(column == 3) {
					cell.setFont(new Font("Liberation Sans", Font.BOLD, 14));
					if(value.equals("Entrada"))
						cell.setForeground(Color.GREEN);
					else if(value.equals("Salida"))
						cell.setForeground(Color.BLUE);
					else if(value.equals("Ajuste"))
						cell.setForeground(Color.ORANGE);
					else if(value.equals("Devolución"))
						cell.setForeground(Color.RED);
				} else {
					cell.setForeground(Color.BLACK);
				}
				
				if(isSelected) {
					cell.setForeground(Color.WHITE);
				} else {
					if(column == 3) {
						cell.setFont(new Font("Liberation Sans", Font.BOLD, 14));
						if(value.equals("Entrada"))
							cell.setForeground(Color.GREEN);
						else if(value.equals("Salida"))
							cell.setForeground(Color.BLUE);
						else if(value.equals("Ajuste"))
							cell.setForeground(Color.ORANGE);
						else if(value.equals("Devolución"))
							cell.setForeground(Color.RED);
					} else {
						cell.setForeground(Color.BLACK);
					}
				}
			}

			return cell;
		}
	}
	
	//Method that SETS the JOPMessages
	public int setJOPMessages(String typeMessage, String message) {
		int response = 0;
		
		if(typeMessage.equals("writeFileOK"))
			JOptionPane.showMessageDialog(this, message, "Movimientos en Inventarios", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("printingOK"))
			JOptionPane.showMessageDialog(this, message, "Estado de Impresión", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("printingCancel"))
			JOptionPane.showMessageDialog(this, message, "Estado de Impresión", JOptionPane.INFORMATION_MESSAGE);
		else if(typeMessage.equals("fileExist"))
			response = JOptionPane.showConfirmDialog(this, message, "¿Reemplazar Archivo?", JOptionPane.YES_NO_OPTION);
			
		return response;
	}
}
