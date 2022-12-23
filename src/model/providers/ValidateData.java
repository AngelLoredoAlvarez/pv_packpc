package model.providers;

public class ValidateData {
	public String checkData(JBProviders jbProvider) {
		String field = "";
		
		if(jbProvider.getName().isEmpty())
			field = "names";
		else if(jbProvider.getCompanyName().isEmpty()) 
			field = "companyname";
		else if(jbProvider.getIRS().isEmpty()) 
			field = "irs";
		else if(jbProvider.getStreet().isEmpty()) 
			field = "street";
		else if(jbProvider.getExtNumber().isEmpty()) 
			field = "extnumber";
		else if(jbProvider.getColony().isEmpty()) 
			field = "colony";
		else if(jbProvider.getCity().isEmpty()) 
			field = "city";
		else if(jbProvider.getPostCode().isEmpty()) 
			field = "postcode";
		else if(jbProvider.getLandLine1().isEmpty() && jbProvider.getLandLine2().isEmpty()) 
			field = "contact";
		
		return field;
	}
}
