package utilities;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

public class Search {
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	public static List<JSONObject> googleSearch(String qry) throws Exception {
	    String key="AIzaSyDdzBawwW5PoJMa7QrxeV0CHdOWypsyRro";
	    
	    URL url = new URL(
	            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    BufferedReader br = new BufferedReader(new InputStreamReader(
	            (conn.getInputStream())));
	    
        JSONParser j = new JSONParser();
        String jsonText = readAll(br);
	      JSONObject json = new JSONObject(jsonText);
    
        JSONArray list =   (JSONArray) json.get("items");
        List <JSONObject> links =new ArrayList<JSONObject>();
        
        list.forEach(o ->{
        	JSONObject crt = (JSONObject) o;
			JSONObject obj = new JSONObject();

			obj.put("title",crt.get("title"));
			obj.put("link", crt.get("link"));
			obj.put("description", crt.get("snippet"));
		
			links.add(obj);
		
        });
    //	System.out.println(links.toString());
    	
	    conn.disconnect(); 
	    return links;

	}

}
