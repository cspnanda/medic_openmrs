package main.java.com.medicmobile.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestUtils implements Constants {
	static String getParentUUID(String orgName,String OPENMRS_HOST,int OPENMRS_PORT,String OPENMRS_USERNAME,String OPENMRS_PASSWORD)
	{
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      String urlString = "http://" + OPENMRS_HOST + ":" + OPENMRS_PORT + Constants.OPENMRS_API_ENDPOINT + "location";
	      URL url;
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         String userpass = OPENMRS_USERNAME + ":" + OPENMRS_PASSWORD;
	         String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
	         conn.setRequestProperty ("Authorization", basicAuth);

	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			obj = parser.parse(result);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray array = (JSONArray) jsonObject.get("results");
			for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
			{
				JSONObject parentJSON = i.next();
				String orgNameHere = (String) parentJSON.get("display");
				if(orgNameHere!=null)
				{
					if(orgNameHere.equalsIgnoreCase(orgName))
					{
						return (String) parentJSON.get("uuid");
					}
				}				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return null;	      
	}
	static boolean checkIfExist(String orgName,String OPENMRS_HOST,int OPENMRS_PORT,String OPENMRS_USERNAME,String OPENMRS_PASSWORD)
	{
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      String urlString = "http://" + OPENMRS_HOST + ":" + OPENMRS_PORT + Constants.OPENMRS_API_ENDPOINT+ "location";
	      URL url;
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         String userpass = OPENMRS_USERNAME + ":" + OPENMRS_PASSWORD;
	         String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
	         conn.setRequestProperty ("Authorization", basicAuth);

	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			obj = parser.parse(result);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray array = (JSONArray) jsonObject.get("results");
			for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
			{
				String orgNameHere = (String) i.next().get("display");
				if(orgNameHere!=null)
				{
					if(orgNameHere.equalsIgnoreCase(orgName))
						return true;
				}				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return false;	      
	}
	
	
	@SuppressWarnings("unchecked")
	static void createOrg(String orgName,String parentUUID,String OPENMRS_HOST,int OPENMRS_PORT,String OPENMRS_USERNAME,String OPENMRS_PASSWORD)
	{
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      String urlString = "http://" + OPENMRS_HOST + ":" + OPENMRS_PORT + Constants.OPENMRS_API_ENDPOINT+ "location";
	      URL url;
	      
	      // System.out.println( new JSONObject(postData));
	      JSONObject postData = new JSONObject();
	      postData.put("name", orgName);
	      postData.put("description", orgName);
	      if(parentUUID != null)
		      postData.put("parentLocation", parentUUID);	      
		try {
			
			
			Credentials defaultcreds = new UsernamePasswordCredentials(OPENMRS_USERNAME,OPENMRS_PASSWORD);

			
		    HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
		    
		    
		    
	         String userpass = OPENMRS_USERNAME + ":" + OPENMRS_PASSWORD;
	         String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
	         // conn.setRequestProperty ("Authorization", basicAuth);
	         // HttpResponse response;
	         HttpPost post = new HttpPost(urlString);
	         post.setHeader("Authorization", basicAuth);

	         
	         StringEntity entity = new StringEntity(postData.toString(), HTTP.UTF_8);
	         entity.setContentType("application/json");
	         post.setEntity(entity);
	         HttpResponse response = httpClient.execute(post);
	         int statusCode = response.getStatusLine().getStatusCode();	            
	         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	}
	@SuppressWarnings("unused")
	static void deleteAllLocations(String OPENMRS_HOST,int OPENMRS_PORT,String OPENMRS_USERNAME,String OPENMRS_PASSWORD)
	{
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      String urlString = "http://" + OPENMRS_HOST + ":" + OPENMRS_PORT + Constants.OPENMRS_API_ENDPOINT+ "location";
	      URL url;
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         String userpass = OPENMRS_USERNAME + ":" + OPENMRS_PASSWORD;
	         String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
	         conn.setRequestProperty ("Authorization", basicAuth);

	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			obj = parser.parse(result);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray array = (JSONArray) jsonObject.get("results");
			for(Iterator<JSONObject> i = array.iterator();i.hasNext();)
			{
				String uuid = (String) i.next().get("uuid");
				System.out.println("curl -X DELETE "+urlString+"/"+uuid+"?purge=yes");
								
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@SuppressWarnings("unchecked")
	static void createPhoneNumberAttribute(String OPENMRS_HOST,int OPENMRS_PORT,String OPENMRS_USERNAME,String OPENMRS_PASSWORD)
	{
	      String urlString = "http://" + OPENMRS_HOST + ":" + OPENMRS_PORT + Constants.OPENMRS_API_ENDPOINT + "personattributetype";
	      JSONObject postData = new JSONObject();
	      postData.put("name", "Phone");
	      postData.put("description", "Phone Number");
	      postData.put("format","java.lang.String");
		try {
			
			HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
		    String userpass = OPENMRS_USERNAME + ":" + OPENMRS_PASSWORD;
	        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
	        HttpPost post = new HttpPost(urlString);
	        post.setHeader("Authorization", basicAuth);
	        StringEntity entity = new StringEntity(postData.toString(), HTTP.UTF_8);
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	        HttpResponse response = httpClient.execute(post);
	        int statusCode = response.getStatusLine().getStatusCode();	            
	         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	}
}
