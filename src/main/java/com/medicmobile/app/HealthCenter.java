package main.java.com.medicmobile.app;

import java.util.logging.Logger;

public class HealthCenter {
    private static final Logger log = Logger.getLogger(HealthCenter.class.getName());
	private String healthCenterName = null;
	private String parentName = null;
	private String oPENMRS_HOST = null;
	private int oPENMRS_PORT = 8080;
	private String oPENMRS_USERNAME = null;
	private String oPENMRS_PASSWORD = null;
	public HealthCenter(String healthCenterName, String parentName, String oPENMRS_HOST, int oPENMRS_PORT,
			String oPENMRS_USERNAME, String oPENMRS_PASSWORD) {
		// TODO Auto-generated constructor stub
		this.healthCenterName = healthCenterName;
		this.parentName = parentName;
		this.oPENMRS_HOST = oPENMRS_HOST;
		this.oPENMRS_PORT = oPENMRS_PORT;
		this.oPENMRS_USERNAME = oPENMRS_USERNAME;
		this.oPENMRS_PASSWORD = oPENMRS_PASSWORD;
	}

	public void createIfNeeded() {
		// TODO Auto-generated method stub
		boolean exist = RestUtils.checkIfExist(healthCenterName,oPENMRS_HOST,oPENMRS_PORT,oPENMRS_USERNAME,oPENMRS_PASSWORD);
		if(exist)
		{
			log.info("Health Center " + healthCenterName + " exist. Skipping Creation");						
		}
		else
		{
			String parentuuid = RestUtils.getParentUUID(parentName, oPENMRS_HOST, oPENMRS_PORT, oPENMRS_USERNAME,oPENMRS_PASSWORD);
			if(parentuuid!=null)
				RestUtils.createOrg(healthCenterName, parentuuid, oPENMRS_HOST, oPENMRS_PORT, oPENMRS_USERNAME, oPENMRS_PASSWORD);
		}

	}

}
