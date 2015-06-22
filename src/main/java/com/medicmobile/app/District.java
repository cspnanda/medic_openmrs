package main.java.com.medicmobile.app;

import java.util.logging.Logger;

public class District {
    private static final Logger log = Logger.getLogger(District.class.getName());
	private String districtName = null;
	private String oPENMRS_HOST = null;
	private int oPENMRS_PORT = 8080;
	private String oPENMRS_USERNAME = null;
	private String oPENMRS_PASSWORD = null;
	public District(String districtName, String oPENMRS_HOST, int oPENMRS_PORT,
			String oPENMRS_USERNAME, String oPENMRS_PASSWORD) {
		// TODO Auto-generated constructor stub
		this.districtName = districtName;
		this.oPENMRS_HOST = oPENMRS_HOST;
		this.oPENMRS_PORT = oPENMRS_PORT;
		this.oPENMRS_USERNAME = oPENMRS_USERNAME;
		this.oPENMRS_PASSWORD = oPENMRS_PASSWORD;
	}

	public void createIfNeeded() {
		// TODO Auto-generated method stub
		boolean exist = RestUtils.checkIfExist(districtName,oPENMRS_HOST,oPENMRS_PORT,oPENMRS_USERNAME,oPENMRS_PASSWORD);
		if(exist)
		{
			log.info("District Hospital " + districtName + " exist. Skipping Creation");						
		}
		else
		{
			RestUtils.createOrg(districtName, null, oPENMRS_HOST, oPENMRS_PORT, oPENMRS_USERNAME, oPENMRS_PASSWORD);
		}

	}

}
