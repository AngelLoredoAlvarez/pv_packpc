package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import net.sf.jasperreports.view.JasperViewer;
import views.JDReports;

public class CtrlReports {
	//Attributes
	private JDReports jdReports;
	private JasperViewer jpv;
	
	//Method that SETS the VIEW
	public void setJDReports(JDReports jdReports) {
		this.jdReports = jdReports;
	}
	//Method that SETS the JasperViewer
	public void setJasperViewer(JasperViewer jpv) {
		this.jpv = jpv;
	}
	
	//Method that PREPRES the VIEW
	public void prepareView() {
		this.setListener();
		//This has to be at the END
		jdReports.setVisible(true);
	}
	
	//Method that SETS the LISTENERS
	private void setListener() {
		//JDialog
		jdReports.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				jpv.dispose();
			}
		});
	}
}
