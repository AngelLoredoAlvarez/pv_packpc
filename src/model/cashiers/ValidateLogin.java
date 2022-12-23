package model.cashiers;

public class ValidateLogin {
	public boolean checkUserExistence(JBCashiersLogin jbCashierLogin, DAOCashiersLogin daoCashiersLogin) {
		boolean exist;
		exist = daoCashiersLogin.checkLogin(jbCashierLogin);
		
		return exist;
	}
}
