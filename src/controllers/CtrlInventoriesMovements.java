package controllers;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.inventories.DAOInventoriesMovements;
import model.inventories.JBInventoriesMovements;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import views.JDInventoriesMovements;
import views.JDReports;
import views.JPVViewer;

public class CtrlInventoriesMovements {
	//Attributes
	private JDInventoriesMovements jdInventoriesMovements;
	private ArrayList<JBInventoriesMovements> alInventoriesMovements;
	private DAOInventoriesMovements daoInventoriesMovements;
	private DecimalFormat dfDecimals, dfIntegers;
	private Date date;
	private SimpleDateFormat sdfDate;
	private String selectedDate = "", filterMovement = "Todos", nameSheet = "", typeMessage = "", message = "", regex = "^$|^[\\p{L}0-9 '-]+$", userInput = "";
	private int type_movement = 0, columnToFilter = 1;
	private String[] dataForSave;
	private XSSFWorkbook workbook;
	private CellStyle csData;
	private Font fHeaders, fData;
	private CtrlReports ctrlReports;
	private JasperReportBuilder reportInventoriesMovements;
	private JPVViewer jpv;
	
	//Method that SETS the VIEW
	public void setJDInventoryMovements(JDInventoriesMovements jdInventoryMovements) {
		this.jdInventoriesMovements = jdInventoryMovements;
	}
	//Method that SETS the DAOInventoriesMovements
	public void setDAOInventoriesMovements(DAOInventoriesMovements daoInventoriesMovements) {
		this.daoInventoriesMovements = daoInventoriesMovements;
	}
	//Method that SETS the Communication JDInventoriesMovements - JDReports
	public void setCtrlReports(CtrlReports ctrlReports) {
		this.ctrlReports = ctrlReports;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		//Initializations
		date = new Date();
		sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
		dfDecimals = new DecimalFormat("#,###,##0.00");
		dfIntegers = new DecimalFormat("#,###,##0");
		selectedDate = sdfDate.format(date);
		this.setListeners();
		//This has to be at the END
		jdInventoriesMovements.setVisible(true);
	}
	
	//Method that SETS the Listener
	private void setListeners() {
		//JDialog
		jdInventoriesMovements.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				getInventoriesMovementsByDate();
			}
		});
		
		//JDatePicker
		jdInventoriesMovements.jdpDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Date dateCashier = (Date) jdInventoriesMovements.jdpDate.getModel().getValue();
				selectedDate = sdfDate.format(dateCashier);
				
				if(filterMovement.equals("Todos"))
					getInventoriesMovementsByDate();
				else
					filterInventoriesMovementsByDateAndMovement();
				
				jdInventoriesMovements.jtxtfSearch.requestFocus();
			}
		});
		
		//JComboBox
		jdInventoriesMovements.jcbMovements.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				@SuppressWarnings("unchecked")
				JComboBox<String> jcmbMovements = (JComboBox<String>)ae.getSource();
				int movement = jcmbMovements.getSelectedIndex();
				switch(movement) {
					case 0:
						filterMovement = "Todos";
						getInventoriesMovementsByDate();
						break;
					case 1:
						filterMovement = "Entrada";
						filterInventoriesMovementsByDateAndMovement();
						break;
					case 2:
						filterMovement = "Salida";
						filterInventoriesMovementsByDateAndMovement();
						break;
					case 3:
						filterMovement = "Ajuste";
						filterInventoriesMovementsByDateAndMovement();
						break;
					case 4:
						filterMovement = "Devolución";
						filterInventoriesMovementsByDateAndMovement();
						break;
				}
			}
		});
		
		//JRadioButtons
		jdInventoriesMovements.jrbProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				regex = "^$|^[\\p{L}0-9 '-]+$";
				jdInventoriesMovements.jtxtfSearch.setText("");
				columnToFilter = 1;
			}
		});
		jdInventoriesMovements.jrbCashier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				regex = "^$|^[\\p{L} '-]+$";
				jdInventoriesMovements.jtxtfSearch.setText("");
				columnToFilter = 5;
			}
		});
		
		//JTextField
		((AbstractDocument)jdInventoriesMovements.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			    if(jdInventoriesMovements.jtInventoriesMovements.getRowCount() == 0)
	    			disableJButtons();
	    		else
					enableJButtonsandSelectsFirstRow();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
					filterJTable();
					if(jdInventoriesMovements.jtInventoriesMovements.getRowCount() == 0)
			    		disableJButtons();
			    	else
						enableJButtonsandSelectsFirstRow();
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regex, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    	filterJTable();
			    	if(jdInventoriesMovements.jtInventoriesMovements.getRowCount() == 0)
			    		disableJButtons();
			    	else
						enableJButtonsandSelectsFirstRow();
			    }
			}
		});
		
		//JButtons
		jdInventoriesMovements.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filterMovement = "Todos";
				jdInventoriesMovements.dispose();
			}
		});
		jdInventoriesMovements.jbtnGenerateSpreadsheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdInventoriesMovements.jbtnGenerateSpreadsheet.setEnabled(false);
				setNameDateSheet();
				jdInventoriesMovements.nameSpreadSheet = nameSheet;
				dataForSave = jdInventoriesMovements.retrivedDataForSaveSpreadSheet();
				
				if(dataForSave != null) {
					if(checkFile()) {
						typeMessage = "fileExist";
						message = "Ya existe un archivo llamado '" + nameSheet + "." + dataForSave[1] + "', ¿Desea reemplazarlo?";
						int response = jdInventoriesMovements.setJOPMessages(typeMessage, message);
						if(response == JOptionPane.YES_OPTION) {
							generateSpreadSheet();
						}
					} else {
						generateSpreadSheet();
					}
				}
			}
		});
		jdInventoriesMovements.jbtnGenerateReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdInventoriesMovements.jbtnGenerateReport.setEnabled(false);
				setNameDateSheet();
				generateReport();
			}
		});
		jdInventoriesMovements.jbtnPrintMovements.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				setNameDateSheet();
				MessageFormat mfHeader = new MessageFormat(nameSheet);
				MessageFormat mfFooter = new MessageFormat("Página {0}");
				JTable.PrintMode jtpm = JTable.PrintMode.FIT_WIDTH;
				try {
					boolean printingComplete = jdInventoriesMovements.jtInventoriesMovements.print(
												jtpm, mfHeader, mfFooter, true, null, true, null);
					if(printingComplete) {
						typeMessage = "printingOK";
						message = "Impresión Terminada Correctamente";
						jdInventoriesMovements.setJOPMessages(typeMessage, message);
					} else {
						typeMessage = "printingCancel";
						message = "Impresión Cancelada";
						jdInventoriesMovements.setJOPMessages(typeMessage, message);
					}
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		});
		
		//JTable
		jdInventoriesMovements.jtInventoriesMovements.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(lse.getValueIsAdjusting()) {
					int selectedRow = jdInventoriesMovements.jtInventoriesMovements.getSelectedRow();
					if(selectedRow >= 0) {
						jdInventoriesMovements.jtxtfSearch.requestFocus();
					}
				}
			}
		});
	}
	
	//Method that GETS the Inventories Movements by DATE
	private void getInventoriesMovementsByDate() {
		SwingWorker<Void, Void> getInventoriesMovementsByDate = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				removePreviousRows();
				alInventoriesMovements = daoInventoriesMovements.getInventoriesMovementsByDate(selectedDate);
				return null;
			}

			@Override
			protected void done() {
				addDataFromDB();
			}
		};
		//Executes the WORKER
		getInventoriesMovementsByDate.execute();
	}
	
	//Method that FILTERS the Inventories Movements by DATE and TYPE MOVEMENT
	private void filterInventoriesMovementsByDateAndMovement() {
		SwingWorker<Void, Void> filterInventoriesMovementsByDateAndMovement = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				removePreviousRows();
				JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
				if(jdInventoriesMovements.jdpDate.getModel().getValue() == null) {
					jbInventoryMovement.setDate(selectedDate);
				} else {
					Date selectedDate = (Date) jdInventoriesMovements.jdpDate.getModel().getValue();
					jbInventoryMovement.setDate(sdfDate.format(selectedDate));
				}
				jbInventoryMovement.setMovement(filterMovement);
				alInventoriesMovements = daoInventoriesMovements.filterInventoriesMovements(jbInventoryMovement);
				return null;
			}

			@Override
			protected void done() {
				addDataFromDB();
			}
		};
		//Executes the WORKER
		filterInventoriesMovementsByDateAndMovement.execute();
	}
	
	//Method that REMOVES the Previous ROWS from the JTable
	private void removePreviousRows() {
		for(int rr = jdInventoriesMovements.dtmInventoriesMovements.getRowCount() - 1; rr >= 0; rr--) {
			jdInventoriesMovements.dtmInventoriesMovements.removeRow(rr);
		}
	}
	
	//Method that ADDS Rows of DATA from the DB
	private void addDataFromDB() {
		if(!alInventoriesMovements.isEmpty()) {
			for(int aim = 0; aim < alInventoriesMovements.size(); aim++) {
				JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
				jbInventoryMovement = alInventoriesMovements.get(aim);
				
				double fExisted = jbInventoryMovement.getExisted();
				double fQuantity = jbInventoryMovement.getQuantity();
				
				String existed = "", quantity = "";
				
				if(jbInventoryMovement.getUnitMeasurement().equals("Pza")) {
					existed = dfIntegers.format(fExisted);
					quantity = dfIntegers.format(fQuantity);
				} else if(jbInventoryMovement.getUnitMeasurement().equals("Granel")) {
					existed = dfDecimals.format(fExisted);
					quantity = dfDecimals.format(fQuantity);
				}
				
				jdInventoriesMovements.dtmInventoriesMovements.addRow(new Object[] {
					jbInventoryMovement.getTime(),
					jbInventoryMovement.getDescription(), 
					existed,
					jbInventoryMovement.getMovement(),
					quantity,
					jbInventoryMovement.getCashier()
				});
			}
			enableJButtonsandSelectsFirstRow();
		} else {
			disableJButtons();
		}
	}
	
	//Method that Enable the JButtons and Selects the First Row from the JTable
	private void enableJButtonsandSelectsFirstRow() {
		jdInventoriesMovements.jbtnGenerateSpreadsheet.setEnabled(true);
		jdInventoriesMovements.jbtnGenerateReport.setEnabled(true);
		jdInventoriesMovements.jbtnPrintMovements.setEnabled(true);
		jdInventoriesMovements.jtInventoriesMovements.setRowSelectionInterval(0, 0);
	}
	
	//Method that Disable the JButtons
	private void disableJButtons() {
		jdInventoriesMovements.jbtnGenerateSpreadsheet.setEnabled(false);
		jdInventoriesMovements.jbtnGenerateReport.setEnabled(false);
		jdInventoriesMovements.jbtnPrintMovements.setEnabled(false);
	}
	
	//Method that SETS the Name and the Date for the Sheet
	private void setNameDateSheet() {
		String dateSpreadSheet = "";
		SimpleDateFormat sdfSmallDate = new SimpleDateFormat("dd-MM-yyyy");
		type_movement = jdInventoriesMovements.jcbMovements.getSelectedIndex();
		
		if(jdInventoriesMovements.jdpDate.getModel().getValue() == null) {
			dateSpreadSheet = sdfSmallDate.format(date);
		} else {
			Date selectedDate = (Date) jdInventoriesMovements.jdpDate.getModel().getValue();
			dateSpreadSheet = sdfSmallDate.format(selectedDate);
		}
		
		switch(type_movement) {
			case 0:
				nameSheet = "Todos los Movimientos de Inventarios del Día " + dateSpreadSheet;
				break;
			case 1:
				nameSheet = "Entradas a Inventarios del Día " + dateSpreadSheet;
				break;
			case 2:
				nameSheet = "Salidas de Inventarios del Día " + dateSpreadSheet;
				break;
			case 3:
				nameSheet = "Ajustes de Inventarios del Día " + dateSpreadSheet;
				break;
			case 4:
				nameSheet = "Devoluciones a Inventarios del Día " + dateSpreadSheet;
				break;
		}
	}
	
	//Method that CHECKS if the File was already done
	private boolean checkFile() {
		boolean fileExist = false;
		File file = new File(dataForSave[0] + "." + dataForSave[1]);
		if(file.exists() && !file.isDirectory()) {
			fileExist = true;
		}
		return fileExist;
	}
	
	//Generate the SPREADSHEET
	private void generateSpreadSheet() {
		SwingWorker<Void, Void> generateSpreadSheet = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Create the Excel Book
				workbook = new XSSFWorkbook();
				
				//Font for the Headers Cells
				fHeaders = workbook.createFont();
				fHeaders.setFontHeightInPoints((short)14);
				fHeaders.setFontName("Liberation Sans");
				fHeaders.setBold(true);
				fHeaders.setColor(IndexedColors.WHITE.getIndex());
				
				//Font for the DATA Cells
				fData = workbook.createFont();
				fData.setFontHeightInPoints((short)12);
				fData.setFontName("Liberation Sans");
				
				//Create the Global Cell Style
				csData = workbook.createCellStyle();
				csData.setFont(fData);
				csData.setAlignment(CellStyle.ALIGN_CENTER);
				csData.setBorderLeft(CellStyle.BORDER_THIN);
				csData.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csData.setBorderBottom(CellStyle.BORDER_THIN);
				csData.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csData.setBorderRight(CellStyle.BORDER_THIN);
				csData.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				switch(type_movement) {
					case 0:
						fillSheetEntries();
						fillSheetOuts();
						fillSheetAdjustments();
						fillSheetReturns();
						break;
					case 1:
						fillSheetEntries();
						break;
					case 2:
						fillSheetOuts();
						break;
					case 3:
						fillSheetAdjustments();
						break;
					case 4:
						fillSheetReturns();
						break;
				}
				
				//Writes the File
				FileOutputStream fileOut = new FileOutputStream(dataForSave[0] + "." + dataForSave[1]);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				
				return null;
			}

			@Override
			protected void done() {
				//Open the Workbook
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(new File(dataForSave[0] + "." + dataForSave[1]));
				} catch (IOException e) {
					e.printStackTrace();
				}
				jdInventoriesMovements.jbtnGenerateSpreadsheet.setEnabled(true);
			}
		};
		
		//Executes the WORKER
		generateSpreadSheet.execute();
	}
	private void fillSheetEntries() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetEntries = workbook.createSheet("Entradas");
	
		Row rowHeadersEntries = sheetEntries.createRow(0);
		rowHeadersEntries.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);

		//Time Header
		Cell timeHeaderEntries = rowHeadersEntries.createCell(0);
		timeHeaderEntries.setCellValue("Hora");
		timeHeaderEntries.setCellStyle(csHeaders);
		//Description Header
		Cell descriptionHeaderEntries = rowHeadersEntries.createCell(1);
		descriptionHeaderEntries.setCellValue("Descripción del Producto");
		descriptionHeaderEntries.setCellStyle(csHeaders);
		//Existed Header
		Cell existedHeaderEntries = rowHeadersEntries.createCell(2);
		existedHeaderEntries.setCellValue("Había");
		existedHeaderEntries.setCellStyle(csHeaders);
		//Movement Header
		Cell movementHeaderEntries = rowHeadersEntries.createCell(3);
		movementHeaderEntries.setCellValue("Movimiento");
		movementHeaderEntries.setCellStyle(csHeaders);
		//Quantity Header
		Cell quantityHeaderEntries = rowHeadersEntries.createCell(4);
		quantityHeaderEntries.setCellValue("Cantidad");
		quantityHeaderEntries.setCellStyle(csHeaders);
		//Cashier Header
		Cell cashierHeaderEntries = rowHeadersEntries.createCell(5);
		cashierHeaderEntries.setCellValue("Cajero");
		cashierHeaderEntries.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdie = 0; gdie < alInventoriesMovements.size(); gdie++) {
			JBInventoriesMovements jbEntryMovement = new JBInventoriesMovements();
			jbEntryMovement = alInventoriesMovements.get(gdie);
			
			if(jbEntryMovement.getMovement().equals("Entrada")) {
				row = gdie - lessToGetRow;
				
				Row rowEntry = sheetEntries.createRow(row + 1);
				
				Cell cTime = rowEntry.createCell(0);
				cTime.setCellValue(jbEntryMovement.getTime());
				cTime.setCellStyle(csData);
				
				Cell cDescription = rowEntry.createCell(1);
				cDescription.setCellValue(jbEntryMovement.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cExisted = rowEntry.createCell(2);
				cExisted.setCellValue(jbEntryMovement.getExisted());
				cExisted.setCellStyle(csData);
				
				Cell cMovement = rowEntry.createCell(3);
				cMovement.setCellValue(jbEntryMovement.getMovement());
				cMovement.setCellStyle(csData);
				
				Cell cQuantity = rowEntry.createCell(4);
				cQuantity.setCellValue(jbEntryMovement.getQuantity());
				cQuantity.setCellStyle(csData);
				
				Cell cCashier = rowEntry.createCell(5);
				cCashier.setCellValue(jbEntryMovement.getCashier());
				cCashier.setCellStyle(csData);
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersEntries.getLastCellNum(); column++) {
			sheetEntries.autoSizeColumn(column);
		}
	}
	private void fillSheetOuts() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetOuts = workbook.createSheet("Salidas");
		
		Row rowHeadersOuts = sheetOuts.createRow(0);
		rowHeadersOuts.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);

		//Time Header
		Cell timeHeaderOuts = rowHeadersOuts.createCell(0);
		timeHeaderOuts.setCellValue("Hora");
		timeHeaderOuts.setCellStyle(csHeaders);
		//Description Header
		Cell descriptionHeaderOuts = rowHeadersOuts.createCell(1);
		descriptionHeaderOuts.setCellValue("Descripción del Producto");
		descriptionHeaderOuts.setCellStyle(csHeaders);
		//Existed Header
		Cell existedHeaderOuts = rowHeadersOuts.createCell(2);
		existedHeaderOuts.setCellValue("Había");
		existedHeaderOuts.setCellStyle(csHeaders);
		//Movement Header
		Cell movementHeaderOuts = rowHeadersOuts.createCell(3);
		movementHeaderOuts.setCellValue("Movimiento");
		movementHeaderOuts.setCellStyle(csHeaders);
		//Quantity Header
		Cell quantityHeaderOuts = rowHeadersOuts.createCell(4);
		quantityHeaderOuts.setCellValue("Cantidad");
		quantityHeaderOuts.setCellStyle(csHeaders);
		//Cashier Header
		Cell cashierHeaderOuts = rowHeadersOuts.createCell(5);
		cashierHeaderOuts.setCellValue("Cajero");
		cashierHeaderOuts.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdio = 0; gdio < alInventoriesMovements.size(); gdio++) {
			JBInventoriesMovements jbOutMovement = new JBInventoriesMovements();
			jbOutMovement = alInventoriesMovements.get(gdio);
			if(jbOutMovement.getMovement().equals("Salida")) {
				row = gdio - lessToGetRow;
				
				Row rowOut = sheetOuts.createRow(row + 1);
				
				Cell cTime = rowOut.createCell(0);
				cTime.setCellValue(jbOutMovement.getTime());
				cTime.setCellStyle(csData);
				
				Cell cDescription = rowOut.createCell(1);
				cDescription.setCellValue(jbOutMovement.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cExisted = rowOut.createCell(2);
				cExisted.setCellValue(jbOutMovement.getExisted());
				cExisted.setCellStyle(csData);
				
				Cell cMovement = rowOut.createCell(3);
				cMovement.setCellValue(jbOutMovement.getMovement());
				cMovement.setCellStyle(csData);
				
				Cell cQuantity = rowOut.createCell(4);
				cQuantity.setCellValue(jbOutMovement.getQuantity());
				cQuantity.setCellStyle(csData);
				
				Cell cCashier = rowOut.createCell(5);
				cCashier.setCellValue(jbOutMovement.getCashier());
				cCashier.setCellStyle(csData);
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersOuts.getLastCellNum(); column++) {
			sheetOuts.autoSizeColumn(column);
		}
	}
	private void fillSheetAdjustments() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetAdjustments = workbook.createSheet("Ajustes");
	
		Row rowHeadersAdjustments = sheetAdjustments.createRow(0);
		rowHeadersAdjustments.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);

		//Time Header
		Cell timeHeaderAdjustments = rowHeadersAdjustments.createCell(0);
		timeHeaderAdjustments.setCellValue("Hora");
		timeHeaderAdjustments.setCellStyle(csHeaders);
		//Description Header
		Cell descriptionHeaderAdjustments = rowHeadersAdjustments.createCell(1);
		descriptionHeaderAdjustments.setCellValue("Descripción del Producto");
		descriptionHeaderAdjustments.setCellStyle(csHeaders);
		//Existed Header
		Cell existedHeaderAdjustments = rowHeadersAdjustments.createCell(2);
		existedHeaderAdjustments.setCellValue("Había");
		existedHeaderAdjustments.setCellStyle(csHeaders);
		//Movement Header
		Cell movementHeaderAdjustments = rowHeadersAdjustments.createCell(3);
		movementHeaderAdjustments.setCellValue("Movimiento");
		movementHeaderAdjustments.setCellStyle(csHeaders);
		//Quantity Header
		Cell quantityHeaderAdjustments = rowHeadersAdjustments.createCell(4);
		quantityHeaderAdjustments.setCellValue("Cantidad");
		quantityHeaderAdjustments.setCellStyle(csHeaders);
		//Cashier Header
		Cell cashierHeaderAdjustments = rowHeadersAdjustments.createCell(5);
		cashierHeaderAdjustments.setCellValue("Cajero");
		cashierHeaderAdjustments.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdia = 0; gdia < alInventoriesMovements.size(); gdia++) {
			JBInventoriesMovements jbEntryMovement = new JBInventoriesMovements();
			jbEntryMovement = alInventoriesMovements.get(gdia);
			if(jbEntryMovement.getMovement().equals("Ajuste")) {
				row = gdia - lessToGetRow;
				
				Row rowEntry = sheetAdjustments.createRow(row + 1);
				
				Cell cTime = rowEntry.createCell(0);
				cTime.setCellValue(jbEntryMovement.getTime());
				cTime.setCellStyle(csData);
				
				Cell cDescription = rowEntry.createCell(1);
				cDescription.setCellValue(jbEntryMovement.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cExisted = rowEntry.createCell(2);
				cExisted.setCellValue(jbEntryMovement.getExisted());
				cExisted.setCellStyle(csData);
				
				Cell cMovement = rowEntry.createCell(3);
				cMovement.setCellValue(jbEntryMovement.getMovement());
				cMovement.setCellStyle(csData);
				
				Cell cQuantity = rowEntry.createCell(4);
				cQuantity.setCellValue(jbEntryMovement.getQuantity());
				cQuantity.setCellStyle(csData);
				
				Cell cCashier = rowEntry.createCell(5);
				cCashier.setCellValue(jbEntryMovement.getCashier());
				cCashier.setCellStyle(csData);
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersAdjustments.getLastCellNum(); column++) {
			sheetAdjustments.autoSizeColumn(column);
		}
	}
	private void fillSheetReturns() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetReturns = workbook.createSheet("Devoluciones");
		
		Row rowHeadersReturns = sheetReturns.createRow(0);
		rowHeadersReturns.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.RED.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		//Time Header
		Cell timeHeaderReturns = rowHeadersReturns.createCell(0);
		timeHeaderReturns.setCellValue("Hora");
		timeHeaderReturns.setCellStyle(csHeaders);
		//Description Header
		Cell descriptionHeaderReturns = rowHeadersReturns.createCell(1);
		descriptionHeaderReturns.setCellValue("Descripción del Producto");
		descriptionHeaderReturns.setCellStyle(csHeaders);
		//Existed Header
		Cell existedHeaderReturns = rowHeadersReturns.createCell(2);
		existedHeaderReturns.setCellValue("Había");
		existedHeaderReturns.setCellStyle(csHeaders);
		//Movement Header
		Cell movementHeaderReturns = rowHeadersReturns.createCell(3);
		movementHeaderReturns.setCellValue("Movimiento");
		movementHeaderReturns.setCellStyle(csHeaders);
		//Quantity Header
		Cell quantityHeaderReturns = rowHeadersReturns.createCell(4);
		quantityHeaderReturns.setCellValue("Cantidad");
		quantityHeaderReturns.setCellStyle(csHeaders);
		//Cashier Header
		Cell cashierHeaderReturns = rowHeadersReturns.createCell(5);
		cashierHeaderReturns.setCellValue("Cajero");
		cashierHeaderReturns.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdir = 0; gdir < alInventoriesMovements.size(); gdir++) {
			JBInventoriesMovements jbReturnMovement = new JBInventoriesMovements();
			jbReturnMovement = alInventoriesMovements.get(gdir);
			if(jbReturnMovement.getMovement().equals("Devolución")) {
				row = gdir - lessToGetRow;
				
				Row rowReturn = sheetReturns.createRow(row + 1);
				
				Cell cTime = rowReturn.createCell(0);
				cTime.setCellValue(jbReturnMovement.getTime());
				cTime.setCellStyle(csData);
				
				Cell cDescription = rowReturn.createCell(1);
				cDescription.setCellValue(jbReturnMovement.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cExisted = rowReturn.createCell(2);
				cExisted.setCellValue(jbReturnMovement.getExisted());
				cExisted.setCellStyle(csData);
				
				Cell cMovement = rowReturn.createCell(3);
				cMovement.setCellValue(jbReturnMovement.getMovement());
				cMovement.setCellStyle(csData);
				
				Cell cQuantity = rowReturn.createCell(4);
				cQuantity.setCellValue(jbReturnMovement.getQuantity());
				cQuantity.setCellStyle(csData);
				
				Cell cCashier = rowReturn.createCell(5);
				cCashier.setCellValue(jbReturnMovement.getCashier());
				cCashier.setCellStyle(csData);
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersReturns.getLastCellNum(); column++) {
			sheetReturns.autoSizeColumn(column);
		}
	}
	
	//Generate the REPORT
	private void generateReport() {
		SwingWorker<Void, Void> generateReport = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Define that will be a Report, and sets the Page Orientation
				reportInventoriesMovements = DynamicReports.report();
				reportInventoriesMovements.setPageFormat(PageType.LETTER, PageOrientation.LANDSCAPE);
				
				//Fonts
				StyleBuilder centered = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER);
				StyleBuilder fontTitle = stl.style(centered).setFont(DynamicReports.stl.font("Liberation Sans", true, false, 18));
				StyleBuilder formatTable = stl.style(centered).setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
				StyleBuilder fontHeaders = stl.style(formatTable).setFont(DynamicReports.stl.font("Liberation Sans", true, false, 14));
				StyleBuilder fontRows = stl.style(centered).setFont(DynamicReports.stl.font("Liberation Sans", false, false, 12));
				
				//Report Title
				TextFieldBuilder<String> reportTitle = cmp.text(nameSheet);
				reportTitle.setStyle(fontTitle).setHorizontalAlignment(HorizontalAlignment.CENTER);
				reportInventoriesMovements.title(reportTitle);
				
				//Columns and Rows
				TextColumnBuilder<Integer> rowNumber = col.reportRowNumberColumn("No.").setFixedColumns(2).setHorizontalAlignment(HorizontalAlignment.CENTER);
				TextColumnBuilder<String> time = Columns.column("Hora", "time", type.stringType());
				TextColumnBuilder<String> description = Columns.column("Descripción del Producto", "description", type.stringType());
				TextColumnBuilder<Double> existed = Columns.column("Había", "existed", type.doubleType());
				TextColumnBuilder<String> movement = Columns.column("Movimiento", "movement", type.stringType());
				TextColumnBuilder<String> quantity = Columns.column("Cantidad", "quantity", type.stringType());
				TextColumnBuilder<String> cashier = Columns.column("Cajero", "cashier", type.stringType());
				reportInventoriesMovements.addColumn(rowNumber.setFixedWidth(40));
				reportInventoriesMovements.addColumn(time.setFixedWidth(70));
				reportInventoriesMovements.addColumn(description.setFixedWidth(210));
				reportInventoriesMovements.addColumn(existed.setFixedWidth(65));
				reportInventoriesMovements.addColumn(movement.setFixedWidth(95));
				reportInventoriesMovements.addColumn(quantity.setFixedWidth(75));
				reportInventoriesMovements.addColumn(cashier.setFixedWidth(215));
				
				//Format the Report
				reportInventoriesMovements.setColumnTitleStyle(fontHeaders);
				reportInventoriesMovements.setColumnStyle(fontRows);
				reportInventoriesMovements.highlightDetailEvenRows();
				
				//DataSource
				reportInventoriesMovements.setDataSource(new JRBeanCollectionDataSource(alInventoriesMovements));
				
				//Show the Number of the Page
				reportInventoriesMovements.pageFooter(cmp.pageXofY());
				
				return null;
			}

			@Override
			protected void done() {
				try {
					JasperPrint jp = reportInventoriesMovements.toJasperPrint();
					jpv = new JPVViewer(jp);
					JDReports jdReports = new JDReports(jdInventoriesMovements, nameSheet, jpv);
					ctrlReports.setJDReports(jdReports);
					ctrlReports.setJasperViewer(jpv);
					ctrlReports.prepareView();
				} catch (DRException e) {
					e.printStackTrace();
				}
				jdInventoriesMovements.jbtnGenerateReport.setEnabled(true);
			}
		};
		
		//Execute the WORKER
		generateReport.execute();
	}
	
	//Filter the JTable
	private void filterJTable() {
		userInput = jdInventoriesMovements.jtxtfSearch.getText();
		RowFilter<TableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter("(?i)" + userInput, columnToFilter);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        jdInventoriesMovements.trsSorter.setRowFilter(rf);
	}
}
