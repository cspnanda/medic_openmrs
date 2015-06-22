package main.java.com.medicmobile.app;

import java.util.logging.Logger;

public class VillageClinic {
    private static final Logger log = Logger.getLogger(VillageClinic.class.getName());
	private String villageClinicName = null;
	private String parentName = null;
	private String oPENMRS_HOST = null;
	private int oPENMRS_PORT = 8080;
	private String oPENMRS_USERNAME = null;
	private String oPENMRS_PASSWORD = null;
	public VillageClinic(String villageClinicName, String parentName, String oPENMRS_HOST, int oPENMRS_PORT,
			String oPENMRS_USERNAME, String oPENMRS_PASSWORD) {
		// TODO Auto-generated constructor stub
		this.villageClinicName = villageClinicName;
		this.parentName = parentName;
		this.oPENMRS_HOST = oPENMRS_HOST;
		this.oPENMRS_PORT = oPENMRS_PORT;
		this.oPENMRS_USERNAME = oPENMRS_USERNAME;
		this.oPENMRS_PASSWORD = oPENMRS_PASSWORD;
	}

	public void createIfNeeded() {
		// TODO Auto-generated method stub
		boolean exist = RestUtils.checkIfExist(villageClinicName,oPENMRS_HOST,oPENMRS_PORT,oPENMRS_USERNAME,oPENMRS_PASSWORD);
		if(exist)
		{
			log.info("Health Center " + villageClinicName + " exist. Skipping Creation");						
		}
		else
		{
			String parentuuid = RestUtils.getParentUUID(parentName, oPENMRS_HOST, oPENMRS_PORT, oPENMRS_USERNAME,oPENMRS_PASSWORD);
			if(parentuuid!=null)
				RestUtils.createOrg(villageClinicName, parentuuid, oPENMRS_HOST, oPENMRS_PORT, oPENMRS_USERNAME, oPENMRS_PASSWORD);
		}

	}

}
