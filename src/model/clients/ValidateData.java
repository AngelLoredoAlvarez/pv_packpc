package model.clients;

public class ValidateData {
	public String checkData(JBClients jbClient) {
		String field = "";
		
		if(jbClient.getNames().isEmpty()) {
			field = "names";
		} else if(jbClient.getFirstName().isEmpty()) {
			field = "firstname";
		} else if(jbClient.getLastName().isEmpty()) {
			field = "lastname";
		} else if(jbClient.getStreet().isEmpty()) {
			field = "street";
		} else if(jbClient.getExtNumber().isEmpty()) {
			field = "extnumber";
		} else if(jbClient.getColony().isEmpty()) {
			field = "colony";
		} else if(jbClient.getCity().isEmpty()) {
			field = "city";
		} else if(jbClient.getLandLine().isEmpty() && jbClient.getCelPhone().isEmpty()) {
			field = "contact";
		} else if(jbClient.getIRS().isEmpty()) {
			field = "irs";
		} else if(jbClient.getPostCode().isEmpty()) {
			field = "postcode";
		}
		
		return field;
	}
}
