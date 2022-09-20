package UserAPIServices.UserAPIServices;
import genericLib.Utilities;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.Reporter;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;

import genericLib.BaseClass;
public class UserAPIMethods 
{
	public String Register(String email, String password){
		String url = BaseClass.baseURL.concat("/user/register");
		String payload = Utilities.createCredentialsPayload(email, password);
		return Utilities.postRequest(url, payload);
	}

	public String Login(String email, String password){
		String url = BaseClass.baseURL.concat("/user/login");
		String payload = Utilities.createCredentialsPayload(email, password);
		return Utilities.login(url, payload);
	}
	
	public String createUserData(HashMap<String, String> payloadMap){
		String url = BaseClass.baseURL.concat("/user/create/");
		String payload = Utilities.createPayload(payloadMap);
		return Utilities.postRequest(url, payload);
	}
	
	public String getUserData(){
		String url = BaseClass.baseURL.concat("/user/data");
		return Utilities.getRequest(url);
	}
	
	public String updateUserData(HashMap<String, String> payloadMap){
		String url = BaseClass.baseURL.concat("/user/update");
		String payload = Utilities.createPayload(payloadMap);
		return Utilities.putRequest(url, payload);
	}
	
	public String deleteUserData(){
		String url = BaseClass.baseURL.concat("/user/delete");
		return Utilities.deleteRequest(url);
	}
	
	public String Exit(){
		String url = BaseClass.baseURL.concat("/user/exit");
		return Utilities.deleteRequest(url);
	}
	
	public void validateResponse(String response, String statusCode, String responseMessage){
		JsonPath path = new JsonPath(response);
		String apiStatusCode = path.getString("statusCode");
		String apiResponseBody = path.get("responseBody").toString().replaceAll("\"", "");
		Assert.assertEquals(apiStatusCode, statusCode, "StatusCode not Matching");
		Assert.assertEquals(apiResponseBody, responseMessage, "Response message not Matching");
		Reporter.log("Status code:"+apiStatusCode+" Response:"+apiResponseBody, true);
	}
}
