package model.cashiers;

public class ValidateData {
	public String checkData(JBCashiers jbCashier) {
		String field = "";
		
		if(jbCashier.getNames().isEmpty())
			field = "names";
		else if(jbCashier.getFirstName().isEmpty())
			field = "firstname";
		else if(jbCashier.getLastName().isEmpty())
			field = "lastname";
		else if(jbCashier.getLandLine().isEmpty() && jbCashier.getCelPhone().isEmpty())
			field = "contact";
		else if(jbCashier.getUsername().isEmpty())
			field = "username";
		
		return field;
	}
}
