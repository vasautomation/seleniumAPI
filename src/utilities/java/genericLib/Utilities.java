package genericLib;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class Utilities {
public static String authorizationToken;

	public static String getRequest(String url){
		String bodyResponse="";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGetRequest = new HttpGet(url);
		httpGetRequest.addHeader("Authorization" , authorizationToken);
		try {
			HttpResponse response = client.execute(httpGetRequest);
			bodyResponse = getAPIResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bodyResponse;
	}
	
	public static String postRequest(String url,String payload){
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPostRequest = new HttpPost(url);
		httpPostRequest.addHeader("content-type", "application/json");
		httpPostRequest.addHeader("Authorization", authorizationToken);
		StringEntity params;
		try {
			params = new StringEntity(payload);
			httpPostRequest.setEntity(params);
			HttpResponse response = client.execute(httpPostRequest);
			return getAPIResponse(response);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static String putRequest(String url, String payload){
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut httpPutRequest = new HttpPut(url);
		httpPutRequest.addHeader("content-type", "application/json");
		httpPutRequest.addHeader("Authorization", authorizationToken);
		StringEntity params;
		try {
			params = new StringEntity(payload);
			httpPutRequest.setEntity(params);
			HttpResponse response = client.execute(httpPutRequest);
			return getAPIResponse(response);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String deleteRequest(String url){
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete httpDeleteRequest = new HttpDelete(url);
		httpDeleteRequest.addHeader("content-type", "application/json");
		httpDeleteRequest.addHeader("Authorization", authorizationToken);
		try {
			
			HttpResponse response = client.execute(httpDeleteRequest);
			return getAPIResponse(response);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String login(String url,String payload){
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPostRequest = new HttpPost(url);
		httpPostRequest.addHeader("content-type", "application/json");
		StringEntity params;
		try {
			params = new StringEntity(payload);
			httpPostRequest.setEntity(params);
			HttpResponse response = client.execute(httpPostRequest);
			Header headers [] = response.getAllHeaders();
			for(Header header:headers){
				if(header.getName().equalsIgnoreCase("Authorization")){
					authorizationToken = header.getValue();
					break;
				}
			}
			return getAPIResponse(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String getAPIResponse(HttpResponse response){
		JSONObject json = new JSONObject();
		String bodyResponse = "";
		try {
			InputStream istream = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(istream));
			String output;
			// Simply iterate through XML response and show on console.
			while ((output = br.readLine()) != null) {
				bodyResponse= bodyResponse+output;
			}
			br.close();
			istream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 json.put("statusCode", response.getStatusLine().getStatusCode());
		 json.put("responseBody", bodyResponse);
		 return json.toString();
	}
	

	public static String createPayload(HashMap<String, String> payload){
		JSONObject json = new JSONObject();
		Iterator<String> it = payload.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			json.put(key, payload.get(key));
		}
		return json.toString();
	}
	
	public static String createCredentialsPayload(String email, String password){
		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);
		return json.toString();
	}

}
