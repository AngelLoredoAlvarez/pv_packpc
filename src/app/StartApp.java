package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.business_information.DAOBusinessInformation;
import model.buys.DAOBuys;
import model.buys.DAODetailsBuys;
import model.cashiers.DAOCashiers;
import model.cashiers.DAOCashiersLogin;
import model.cashiers.DAOCashiersPrivileges;
import model.cashiers.DAOCashiersTurns;
import model.clients.DAOClients;
import model.clients.DAOClientsMovements;
import model.clients.DAOCredits;
import model.inventories.DAODepartments;
import model.inventories.DAOInventoriesMovements;
import model.inventories.DAOInventoriesProductService;
import model.inventories.DAOProductProviders;
import model.invoices.DAOInvoices;
import model.invoices.DAOInvoicesDetails;
import model.providers.DAOProviders;
import model.sales.DAODetailsSales;
import model.sales.DAOSales;
import model.till.DAOTill;
import model.till.DAOTillDetails;
import model.till.DAOTillMovements;
import controllers.CtrlActionCashier;
import controllers.CtrlActionClient;
import controllers.CtrlActionDepartment;
import controllers.CtrlActionProductService;
import controllers.CtrlActionProvider;
import controllers.CtrlAskSUDO;
import controllers.CtrlBusinessInformation;
import controllers.CtrlBuys;
import controllers.CtrlDoBuy;
import controllers.CtrlActionInventory;
import controllers.CtrlCloseSystem;
import controllers.CtrlCloseTurn;
import controllers.CtrlConsultInventoriesServices;
import controllers.CtrlInventoriesMovements;
import controllers.CtrlLogin;
import controllers.CtrlTillCourt;
import controllers.CtrlCharge;
import controllers.CtrlConsultCashiers;
import controllers.CtrlConsultClients;
import controllers.CtrlConsultDepartments;
import controllers.CtrlConsultProviders;
import controllers.CtrlDetailsSB;
import controllers.CtrlGranelProduct;
import controllers.CtrlOpenTill;
import controllers.CtrlPayment;
import controllers.CtrlClientsMovements;
import controllers.CtrlReports;
import controllers.CtrlSales;
import controllers.CtrlMain;
import controllers.CtrlStatementOfAccountO;
import controllers.CtrlTillMovements;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import views.JFLogin;

public class StartApp {	
	//Main Method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//JUnique
				String appID = "pv_papeleria.JFLogin";
				boolean alreadyRunning = false;
				try {
					JUnique.acquireLock(appID);
					alreadyRunning = false;
				} catch (AlreadyLockedException e1) {
					e1.printStackTrace();
					alreadyRunning = true;
				}
				
				if(!alreadyRunning) {
					//Look and Feel
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					StartApp initAll = new StartApp();
					initAll.initApp();
				}
			}
		});
	}
	//Creates the Instantiations, and run the APP
	private void initApp() {
		//Initializations
		
		//DAO's
		DAOInventoriesProductService daoInventoriesProductService = new DAOInventoriesProductService();
		DAOClients daoClients = new DAOClients();
		DAOProviders daoProviders = new DAOProviders();
		DAOCredits daoCredits = new DAOCredits();
		DAOSales daoSales = new DAOSales();
		DAODetailsSales daoDetailsSales = new DAODetailsSales();
		DAOTill daoTill = new DAOTill();
		DAOTillDetails daoTillDetails = new DAOTillDetails();
		DAOTillMovements daoTillMovements = new DAOTillMovements();
		DAOCashiers daoCashiers = new DAOCashiers();
		DAOCashiersLogin daoCashiersLogin = new DAOCashiersLogin();
		DAOCashiersTurns daoCashiersTurns = new DAOCashiersTurns();
		DAOCashiersPrivileges daoCashiersPrivileges = new DAOCashiersPrivileges();
		DAOClientsMovements daoClientsMovements = new DAOClientsMovements();
		DAOInventoriesMovements daoInventoriesMovements = new DAOInventoriesMovements();
		DAOBuys daoBuys = new DAOBuys();
		DAODetailsBuys daoDetailsBuys = new DAODetailsBuys();
		DAOProductProviders daoProductProviders = new DAOProductProviders();
		DAODepartments daoDepartments = new DAODepartments();
		DAOBusinessInformation daoBusinessInformation = new DAOBusinessInformation();
		DAOInvoices daoInvoices = new DAOInvoices();
		DAOInvoicesDetails daoInvoicesDetails = new DAOInvoicesDetails();
		
		//Controllers
		CtrlLogin ctrlLogin = new CtrlLogin();
		CtrlMain ctrlMain = new CtrlMain();
		CtrlActionProductService ctrlActionProductService = new CtrlActionProductService();
		CtrlGranelProduct ctrlGranelProduct = new CtrlGranelProduct();
		CtrlConsultClients ctrlConsultClients = new CtrlConsultClients();
		CtrlActionClient ctrlActionClient = new CtrlActionClient();
		CtrlCharge ctrlCharge = new CtrlCharge();
		CtrlConsultProviders ctrlConsultProviders = new CtrlConsultProviders();
		CtrlActionProvider ctrlActionProvider = new CtrlActionProvider();
		CtrlStatementOfAccountO ctrlStatementOfAccount = new CtrlStatementOfAccountO();
		CtrlClientsMovements ctrlPaymentHistory = new CtrlClientsMovements();
		CtrlPayment ctrlPayment = new CtrlPayment();
		CtrlOpenTill ctrlOpenTill = new CtrlOpenTill();
		CtrlTillMovements ctrlTillMovements = new CtrlTillMovements();
		CtrlConsultCashiers ctrlConsultCashiers = new CtrlConsultCashiers();
		CtrlActionCashier ctrlActionCashier = new CtrlActionCashier();
		CtrlTillCourt ctrlTillCourt = new CtrlTillCourt();
		CtrlCloseSystem ctrlCloseSystem = new CtrlCloseSystem();
		CtrlCloseTurn ctrlCloseTurn = new CtrlCloseTurn();
		CtrlConsultInventoriesServices ctrlConsultInventoriesServices = new CtrlConsultInventoriesServices();
		CtrlInventoriesMovements ctrlInventoriesMovements = new CtrlInventoriesMovements();
		CtrlReports ctrlReports = new CtrlReports(); 
		CtrlActionInventory ctrlActionInventory = new CtrlActionInventory();
		CtrlBuys ctrlBuys = new CtrlBuys();
		CtrlDoBuy ctrlDoBuy = new CtrlDoBuy();
		CtrlAskSUDO ctrlAskSudo = new CtrlAskSUDO();
		CtrlConsultDepartments ctrlConsultDepartments = new CtrlConsultDepartments();
		CtrlActionDepartment ctrlActionDepartment = new CtrlActionDepartment();
		CtrlDetailsSB ctrlDetailsSB = new CtrlDetailsSB();
		CtrlSales ctrlSales = new CtrlSales();
		CtrlBusinessInformation ctrlBusinessInformation = new CtrlBusinessInformation();
		
		//Establish the RELATIONSHIPS between Controllers
		ctrlLogin.setCtrlSales(ctrlMain);
		ctrlCloseTurn.setCtrlLogin(ctrlLogin);
		ctrlMain.setCtrlOpenTill(ctrlOpenTill);
		ctrlOpenTill.setCtrlSales(ctrlMain);
		ctrlMain.setCtrlCloseSystem(ctrlCloseSystem);
		ctrlCloseSystem.setCtrlSales(ctrlMain);
		ctrlMain.setCtrlBuys(ctrlBuys);
		ctrlMain.setCtrlSales(ctrlSales);
		ctrlMain.setCtrlTillMovements(ctrlTillMovements);
		ctrlMain.setCtrlTillCourt(ctrlTillCourt);
		ctrlMain.setCtrlConsultClients(ctrlConsultClients);
		ctrlMain.setCtrlConsultProviders(ctrlConsultProviders);
		ctrlMain.setCtrlConsultCashiers(ctrlConsultCashiers);
		ctrlMain.setCtrlConsultInventories(ctrlConsultInventoriesServices);
		ctrlMain.setCtrlGranelProduct(ctrlGranelProduct);
		ctrlMain.setCtrlBusinessInformation(ctrlBusinessInformation);
		ctrlGranelProduct.setCtrlSales(ctrlMain);
		ctrlMain.setCtrlCharge(ctrlCharge);
		ctrlCharge.setCtrlSales(ctrlMain);
		ctrlCharge.setCtrlActionClient(ctrlActionClient);
		ctrlCharge.setCtrlConsultClients(ctrlConsultClients);
		ctrlCloseTurn.setCtrlSales(ctrlMain);
		ctrlMain.setCtrlAskSUDO(ctrlAskSudo);
		ctrlAskSudo.setCtrlOpenTill(ctrlOpenTill);
		ctrlActionProductService.setCtrlInventories(ctrlConsultInventoriesServices);
		ctrlActionProductService.setCtrlConsultDepartments(ctrlConsultDepartments);
		ctrlConsultClients.setCtrlActionClient(ctrlActionClient);
		ctrlActionClient.setCtrlConsultClients(ctrlConsultClients);
		ctrlConsultClients.setCtrlStatementOfAccount(ctrlStatementOfAccount);
		ctrlActionClient.setCtrlCharge(ctrlCharge);
		ctrlConsultProviders.setCtrlActionProvider(ctrlActionProvider);
		ctrlActionProvider.setCtrlConsultProviders(ctrlConsultProviders);
		ctrlStatementOfAccount.setCtrlPay(ctrlPayment);
		ctrlPayment.setCtrlStatementOfAccount(ctrlStatementOfAccount);
		ctrlStatementOfAccount.setCtrlPaymentHistory(ctrlPaymentHistory);
		ctrlConsultCashiers.setCtrlActionCashier(ctrlActionCashier);
		ctrlActionCashier.setCtrlConsultCashiers(ctrlConsultCashiers);
		ctrlCloseSystem.setCtrlCloseTurn(ctrlCloseTurn);
		ctrlTillCourt.setCtrlCloseTurn(ctrlCloseTurn);
		ctrlTillCourt.setCtrlCloseSystem(ctrlCloseSystem);
		ctrlConsultInventoriesServices.setCtrlInventoryMovements(ctrlInventoriesMovements);
		ctrlInventoriesMovements.setCtrlReports(ctrlReports);
		ctrlConsultInventoriesServices.setCtrlActionInventory(ctrlActionInventory);
		ctrlActionInventory.setCtrlInventories(ctrlConsultInventoriesServices);
		ctrlConsultInventoriesServices.setCtrlReports(ctrlReports);
		ctrlConsultInventoriesServices.setDAOProviders(daoProviders);
		ctrlBuys.setCtrlDoBuys(ctrlDoBuy);
		ctrlBuys.setCtrlDetailsSB(ctrlDetailsSB);
		ctrlDoBuy.setCtrlBuys(ctrlBuys);
		ctrlDoBuy.setCtrlConsultProviders(ctrlConsultProviders);
		ctrlConsultProviders.setCtrlBuys(ctrlDoBuy);
		ctrlDoBuy.setCtrlConsultInventories(ctrlConsultInventoriesServices);
		ctrlConsultInventoriesServices.setCtrlBuys(ctrlDoBuy);
		ctrlDoBuy.setCtrlActionInventory(ctrlActionInventory);
		ctrlActionInventory.setCtrlBuys(ctrlDoBuy);
		ctrlActionProductService.setCtrlConsultProviders(ctrlConsultProviders);
		ctrlConsultProviders.setCtrlActionProductService(ctrlActionProductService);
		ctrlConsultDepartments.setCtrlActionDepartment(ctrlActionDepartment);
		ctrlConsultDepartments.setCtrlActionProductService(ctrlActionProductService);
		ctrlActionDepartment.setCtrlConsultDepartments(ctrlConsultDepartments);
		ctrlSales.setCtrlConsultClients(ctrlConsultClients);
		
		//Establish the Relationship with their respective DAOs
		ctrlLogin.setDAOCashiersTurns(daoCashiersTurns);
		ctrlLogin.setDAOCashiersLogin(daoCashiersLogin);
		ctrlLogin.setDAOCashiersPriviliges(daoCashiersPrivileges);
		ctrlLogin.setDAOTill(daoTill);
		ctrlLogin.setDAOSales(daoSales);
		ctrlMain.setDAOInventoriesProductService(daoInventoriesProductService);
		ctrlMain.setDAOBuys(daoBuys);
		ctrlMain.setDAOSales(daoSales);
		ctrlActionProductService.setDAOProductService(daoInventoriesProductService);
		ctrlActionProductService.setDAOInventoriesMovements(daoInventoriesMovements);
		ctrlConsultClients.setDAOClients(daoClients);
		ctrlConsultClients.setDAOCredits(daoCredits);
		ctrlConsultClients.setDAOSells(daoSales);
		ctrlConsultClients.setDAOBusinessInformation(daoBusinessInformation);
		ctrlActionClient.setDAOClients(daoClients);
		ctrlActionClient.setDAOCredits(daoCredits);
		ctrlConsultClients.setDAOInvoices(daoInvoices);
		ctrlConsultClients.setDAOInvoicesDetails(daoInvoicesDetails);
		ctrlConsultProviders.setDAOProviders(daoProviders);
		ctrlActionProvider.setDAOProviders(daoProviders);
		ctrlCharge.setDAOClients(daoCredits);
		ctrlCharge.setDAOSells(daoSales);
		ctrlCharge.setDAOProducts(daoInventoriesProductService);
		ctrlCharge.setDAODetailsSell(daoDetailsSales);
		ctrlCharge.setDAOInventoriesMovements(daoInventoriesMovements);
		ctrlCharge.setDAOClientsMovements(daoClientsMovements);
		ctrlCharge.setDAOTill(daoTill);
		ctrlStatementOfAccount.setDAOSells(daoSales);
		ctrlStatementOfAccount.setDAODetailsSell(daoDetailsSales);
		ctrlStatementOfAccount.setDAOPayment(daoClientsMovements);
		ctrlStatementOfAccount.setDAOCredits(daoCredits);
		ctrlPayment.setDAOCredits(daoCredits);
		ctrlPayment.setDAOPayment(daoClientsMovements);
		ctrlPayment.setDAOTill(daoTill);
		ctrlOpenTill.setDAOTill(daoTill);
		ctrlOpenTill.setDAOTillDetails(daoTillDetails);
		ctrlOpenTill.setDAOCashiersTurns(daoCashiersTurns);
		ctrlTillMovements.setDAOTill(daoTill);
		ctrlTillMovements.setDAOTillMovements(daoTillMovements);
		ctrlConsultCashiers.setDAOCashiers(daoCashiers);
		ctrlConsultCashiers.setDAOCashiersLogin(daoCashiersLogin);
		ctrlConsultCashiers.setDAOCashiersPriviliges(daoCashiersPrivileges);
		ctrlActionCashier.setDAOCashiers(daoCashiers);
		ctrlActionCashier.setDAOCashiersLogin(daoCashiersLogin);
		ctrlActionCashier.setDAOCashiersPriviliges(daoCashiersPrivileges);
		ctrlTillCourt.setDAOCashiers(daoCashiers);
		ctrlTillCourt.setDAOCashiersTurns(daoCashiersTurns);
		ctrlTillCourt.setDAOTill(daoTill);
		ctrlTillCourt.setDAOSells(daoSales);
		ctrlTillCourt.setDAOBuys(daoBuys);
		ctrlTillCourt.setDAOPayment(daoClientsMovements);
		ctrlTillCourt.setDAOTillMovements(daoTillMovements);
		ctrlTillCourt.setDAODetailsSell(daoDetailsSales);
		ctrlTillCourt.setDAOProducts(daoInventoriesProductService);
		ctrlCloseTurn.setDAOCashiersTurns(daoCashiersTurns);
		ctrlCloseTurn.setDAOTill(daoTill);
		ctrlCloseTurn.setDAOTillDetails(daoTillDetails);
		ctrlCloseSystem.setDAOTill(daoTill);
		ctrlCloseSystem.setDAOCashiersTurns(daoCashiersTurns);
		ctrlConsultInventoriesServices.setDAOProducts(daoInventoriesProductService);
		ctrlConsultInventoriesServices.setCtrlActionProductService(ctrlActionProductService);
		ctrlConsultInventoriesServices.setDAODepartments(daoDepartments);
		ctrlInventoriesMovements.setDAOInventoriesMovements(daoInventoriesMovements);
		ctrlActionInventory.setDAOInventoriesProductService(daoInventoriesProductService);
		ctrlActionInventory.setDAOInventoriesMovements(daoInventoriesMovements);
		ctrlBuys.setDAOBuys(daoBuys);
		ctrlBuys.setDAODetailsBuys(daoDetailsBuys);
		ctrlDoBuy.setDAOInventoriesProducts(daoInventoriesProductService);
		ctrlDoBuy.setDAOBuys(daoBuys);
		ctrlDoBuy.setDAODetailsBuys(daoDetailsBuys);
		ctrlDoBuy.setDAOTill(daoTill);
		ctrlDoBuy.setDAOCashiers(daoCashiers);
		ctrlDoBuy.setDAOProductProviders(daoProductProviders);
		ctrlActionProductService.setDAOProductProvider(daoProductProviders);
		ctrlDoBuy.setDAOTill(daoTill);
		ctrlAskSudo.setDAOCashiersLogin(daoCashiersLogin);
		ctrlAskSudo.setDAOCashierPrivileges(daoCashiersPrivileges);
		ctrlConsultDepartments.setDAODepartments(daoDepartments);
		ctrlActionDepartment.setDAODepartments(daoDepartments);
		ctrlSales.setDAOSales(daoSales);
		ctrlSales.setDAODetailsSales(daoDetailsSales);
		ctrlBusinessInformation.setDAOBusinessInformation(daoBusinessInformation);
		
		//StartsAPP
		JFLogin jfLogin = new JFLogin();
		ctrlLogin.setJFLogin(jfLogin);
		ctrlLogin.prepareView();
	}
}