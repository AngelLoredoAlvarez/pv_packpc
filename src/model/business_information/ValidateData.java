package model.business_information;

public class ValidateData {
	public String checkData(JBBusinessInformation jbBusinessInformation) {
		String field = "";
		
		if(jbBusinessInformation.getNameBusiness().isEmpty())
			field = "name_company";
		else if(jbBusinessInformation.getCompanyName().isEmpty())
			field = "company_name";
		else if(jbBusinessInformation.getIRS().isEmpty())
			field = "irs";
		else if(jbBusinessInformation.getStreet().isEmpty())
			field = "street";
		else if(jbBusinessInformation.getExtNumber().isEmpty())
			field = "ext_number";
		else if(jbBusinessInformation.getColony().isEmpty())
			field = "colony";
		else if(jbBusinessInformation.getCity().isEmpty())
			field = "city";
		else if(jbBusinessInformation.getPostCode().isEmpty())
			field = "post_code";
		else if(jbBusinessInformation.getEmail().isEmpty())
			field = "email";
		else if(jbBusinessInformation.getCertificateRoute().isEmpty())
			field = "certificate_route";
		else if(jbBusinessInformation.getPrivateKeyRoute().isEmpty())
			field = "privatekey_route";
		else if(jbBusinessInformation.getPrivateKeyPass().isEmpty())
			field = "privatekey_pass";
		
		return field;
	}
}
