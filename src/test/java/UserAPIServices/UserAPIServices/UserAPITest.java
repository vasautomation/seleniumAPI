package UserAPIServices.UserAPIServices;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.api.client.json.Json;

import io.restassured.path.json.JsonPath;

public class UserAPITest{
	UserAPIMethods lib;
	String email = "sanatkumar@gmail.com";
	String password = "password1234";
	String invalidEmail = "sanatkumar";
	String invalidPassword = "pass";
	
	@BeforeClass
	public void init(){
		lib = new UserAPIMethods();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method m) throws InterruptedException{
		Reporter.log("============================================",true);
		Reporter.log("TC:"+m.getName(),true);
		Reporter.log("--------------------------------------------",true);
		Thread.sleep(2000);
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterMethod(){
		Reporter.log("============================================",true);
		Reporter.log("\n\n",true);
	}
	
	@Test(priority = 1)
	public void ValidateLoginBeforeRegistration(){
		lib.validateResponse(lib.Login(email, password),"401","user not found");
	}
	
	@Test(priority = 2)
	public void validateRegistrationWithInvalidData(){
		lib.validateResponse(lib.Register("", invalidPassword),"400","email is not allowed to be empty");
		lib.validateResponse(lib.Register(email, ""),"400","password is not allowed to be empty");
		lib.validateResponse(lib.Register(invalidEmail, invalidPassword),"400","email must be a valid email");
		lib.validateResponse(lib.Register(email, invalidPassword),"400","password length must be at least 6 characters long");
	}
	
	@Test(priority = 3)
	public void ValidateRegistration(){
		lib.validateResponse(lib.Register(email, password),"200","User created successfully");
		lib.getUserData();
	}
	
	@Test(priority = 4)
	public void ValidateRegistrationWithSameUserData(){
		lib.validateResponse(lib.Register(email, password),"400","User already exists");
	}
	
	@Test(priority = 5)
	public void ValidateAccessingDataWithoutLogin(){
		lib.validateResponse(lib.getUserData(),"401","Access Denied");
	}
	
	@Test(priority = 6)
	public void validateLoginWithInvalidCredentials(){
		lib.validateResponse(lib.Login("", invalidPassword),"400","please enter email id");
		lib.validateResponse(lib.Login(email, ""),"400","please enter password");
		lib.validateResponse(lib.Login(invalidEmail, invalidPassword),"401","user not found");
		lib.validateResponse(lib.Login(email, invalidPassword),"401","password not matching");
	}
	
	@Test(priority = 7)
	public void validateLogin(){
		lib.validateResponse(lib.Login(email, password),"200","Login success");
	}
	
	@Test(priority = 8)
	public void validateDataWithoutCreation(){
		lib.validateResponse(lib.getUserData(),"200","[{id:c2FuYXRrdW1hckBnbWFpbC5jb21wYXNzd29yZDEyMzQ=}]");
	}
	
	@Test(priority = 9)
	public void createUserData(){
		HashMap<String, String> payloadMap = new HashMap<String, String>();
		payloadMap.put("age", "30");
		payloadMap.put("name", "Sanat");
		lib.validateResponse(lib.createUserData(payloadMap),"201","Data created successfully");
	}
	
	@Test(priority = 10)
	public void validateCreatedUserData(){
		String expectedResponseody = "[{age:30,id:c2FuYXRrdW1hckBnbWFpbC5jb21wYXNzd29yZDEyMzQ=,name:Sanat}]"; 
		lib.validateResponse(lib.getUserData(),"200",expectedResponseody);
	}	
	
	@Test(priority = 11)
	public void updateUserData(){
		HashMap<String, String> payloadMap = new HashMap<String, String>();
		payloadMap.put("age", "20");
		payloadMap.put("name", "Sanat_Kumar");
		lib.validateResponse(lib.updateUserData(payloadMap),"201","Data updated successfully");
	}
	
	@Test(priority = 12)
	public void validateUpdatedUserData(){
		String expectedResponseody = "[{age:20,id:c2FuYXRrdW1hckBnbWFpbC5jb21wYXNzd29yZDEyMzQ=,name:Sanat_Kumar}]"; 
		lib.validateResponse(lib.getUserData(),"200",expectedResponseody);
	}
	
	@Test(priority = 13)
	public void deleteUserData(){
		lib.validateResponse(lib.deleteUserData(),"201","Data deleted successfully");
	}
	
	@Test(priority = 14)
	public void validateUserRemove(){
		lib.validateResponse(lib.Exit(),"201","User removed successfully");
	}
	
	@Test(priority = 15)
	public void ValidateUserRemovalForNoUser(){
		lib.validateResponse(lib.Exit(),"404","User not found");
	}
	
}