package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.buys.JBDetailsBuys;
import model.sales.JBDetailsSales;
import views.JDDetailsSB;

public class CtrlDetailsSB {
	//Attributes
	private JDDetailsSB jdDetailsSB;
	
	//Method that SETS the VIEW
	public void setJDDetailsSB(JDDetailsSB jdDetailsSB) {
		this.jdDetailsSB = jdDetailsSB;
	}
	
	//Method that SETS the DATA
	public <E> void setData(ArrayList<E> alDetails, String parent) {
		DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
		if(parent.equals("Buys")) {
			for(int ad = 0; ad < alDetails.size(); ad++) {
				JBDetailsBuys jbDetailBuy = (JBDetailsBuys) alDetails.get(ad);
				jdDetailsSB.dtmDetailsBuy.addRow(new Object[] {
					jbDetailBuy.getDescription(), 
					jbDetailBuy.getQuantity(),
					"$" + dfDecimals.format(jbDetailBuy.getImporte())
				});
			}
		} else if(parent.equals("Sales")) {
			for(int ad = 0; ad < alDetails.size(); ad++) {
				JBDetailsSales jbDetailSale = (JBDetailsSales) alDetails.get(ad);
				jdDetailsSB.dtmDetailsBuy.addRow(new Object[] {
					jbDetailSale.getDescription(), 
					jbDetailSale.getQuantity(),
					"$" + dfDecimals.format(jbDetailSale.getImportPrice())
				});
			}
		}
	}
	
	//Method that PREPARES the View
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdDetailsSB.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JTextField
		((AbstractDocument)jdDetailsSB.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string, attr);
				filterJTable();
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				fb.replace(offset, length, text, attrs);
				filterJTable();
			}
		});
		
		//JButtons
		jdDetailsSB.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdDetailsSB.dispose();
			}
		});
	}
	
	//Method that Filters the JTable
	private void filterJTable() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String userInput = jdDetailsSB.jtxtfSearch.getText();
				RowFilter<TableModel, Object> rf = null;
				try {
				    rf = RowFilter.regexFilter("(?i)" + userInput, 0);
				} catch (java.util.regex.PatternSyntaxException e) {
				    return;
				}
				jdDetailsSB.trsSorter.setRowFilter(rf);
			}
		});
	}
}
