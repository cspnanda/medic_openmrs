package main.java.com.medicmobile.app;

import java.io.FileReader;
import java.util.Iterator;
import java.util.logging.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class PushData implements Constants {
    private static final Logger log = Logger.getLogger(PushData.class.getName());
	public static String MEDICMOBILE_JSON_DUMP;
	public static String OPENMRS_HOST;
	public static int OPENMRS_PORT;
	public static String OPENMRS_USERNAME;
	public static String OPENMRS_PASSWORD;
	public static void main(String[] args) {
		MEDICMOBILE_JSON_DUMP = args[0];
		OPENMRS_HOST = args[1];
		OPENMRS_PORT = new Integer(args[2]).intValue();
		OPENMRS_USERNAME = args[3];
		OPENMRS_PASSWORD = args[4];
		readJSONFile(MEDICMOBILE_JSON_DUMP);
	}
	private static void readJSONFile(String fileName) {
		// TODO Auto-generated method stub
		System.out.println("Parsing " + fileName);
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(fileName));
			JSONObject jsonObject = (JSONObject) obj;
//			create_District(jsonObject);
//			create_HealthCenter(jsonObject);
//			create_VillageClinic(jsonObject);
//			RestUtils.createPhoneNumberAttribute(OPENMRS_HOST, OPENMRS_PORT, OPENMRS_USERNAME, OPENMRS_PASSWORD);
//			RestUtils.deleteAllLocations(OPENMRS_HOST, OPENMRS_PORT, OPENMRS_USERNAME, OPENMRS_PASSWORD);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	private static void create_District(JSONObject jsonObject) {
		JSONArray array = (JSONArray) jsonObject.get("rows");		
		for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
		{
			JSONObject doc = (JSONObject) i.next().get("doc");
			String type = (String) doc.get("type");
			if(type!=null)
			if(type.startsWith("district_hospital"))
			{
				String districtName = (String) doc.get("name");
				District district = new District(districtName,OPENMRS_HOST,OPENMRS_PORT,OPENMRS_USERNAME,OPENMRS_PASSWORD);
				district.createIfNeeded();
			}				
		}
	}
	private static void create_HealthCenter(JSONObject jsonObject) {
		JSONArray array = (JSONArray) jsonObject.get("rows");	
		for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
		{
			JSONObject doc = (JSONObject) i.next().get("doc");
			String type = (String) doc.get("type");
			if(type!=null)
			if(type.startsWith("health_center"))
			{
				String health_center = (String) doc.get("name");
				JSONObject health_center_parent = (JSONObject) doc.get("parent");
				String health_center_parent_name = (String) health_center_parent.get("name");
				HealthCenter healthCenter = new HealthCenter(health_center, health_center_parent_name, OPENMRS_HOST, OPENMRS_PORT, OPENMRS_USERNAME, OPENMRS_PASSWORD);
				healthCenter.createIfNeeded();
			}				
		}
	}
	private static void create_VillageClinic(JSONObject jsonObject) {
		JSONArray array = (JSONArray) jsonObject.get("rows");	
		for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
		{
			JSONObject doc = (JSONObject) i.next().get("doc");
			String type = (String) doc.get("type");
			if(type!=null)
			if(type.startsWith("clinic"))
			{
				String clinic_name = (String) doc.get("name");
				JSONObject clinic_parent = (JSONObject) doc.get("parent");
				String clinic_parent_name = (String) clinic_parent.get("name");
				VillageClinic villageClinic = new VillageClinic(clinic_name, clinic_parent_name, OPENMRS_HOST, OPENMRS_PORT, OPENMRS_USERNAME, OPENMRS_PASSWORD);
				villageClinic.createIfNeeded();
			}				
		}
	}
}
