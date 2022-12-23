package views;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JROdtSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;

public class JPVViewer extends JasperViewer {
	private static final long serialVersionUID = 1L;

	public JPVViewer(JasperPrint jp) {
		super(jp);
		
		this.setExtendedState(MAXIMIZED_BOTH);
		
		Locale locale = viewer.getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", locale);
		JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resourceBundle);
	    JRSingleSheetXlsSaveContributor xls = new JRSingleSheetXlsSaveContributor(locale, resourceBundle);
	    JRDocxSaveContributor docx = new JRDocxSaveContributor(locale, resourceBundle);
	    JROdtSaveContributor odt = new JROdtSaveContributor(locale, resourceBundle);
	    viewer.setSaveContributors(new JRSaveContributor[]{pdf, xls, docx, odt});
	}
}
