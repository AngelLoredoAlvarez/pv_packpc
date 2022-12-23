package views;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jasperreports.view.JasperViewer;

public class JDReports extends JDialog {
	//Attributes
	private static final long serialVersionUID = 1L;
	public String reportName = "";
	public JMenuItem jmiExit;
	
	//Constructor
	public JDReports(JDialog jdParent, String title, JasperViewer jpv) {
		super(jdParent);
		this.setModal(true);
		Image jdIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/jframes/report.png"));
		this.setIconImage(jdIcon);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(jpv.getContentPane());
		this.setSize(jpv.getSize());
		this.setResizable(false);
		this.setLocationRelativeTo(jdParent);
	}
	
	//Method that Retrieves the DATA for Saving
	public String[] retriveDataForSaveReport() {
		String[] dataForSave = null;
		dataForSave = new String[2];
		
		JFileChooser jfcPath = new JFileChooser();
		jfcPath.setDialogTitle("Guardar como...");
		jfcPath.setSelectedFile(new File(reportName));
		jfcPath.setAcceptAllFileFilterUsed(false);
		jfcPath.addChoosableFileFilter(new FileNameExtensionFilter("PDF", "pdf"));
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
}
