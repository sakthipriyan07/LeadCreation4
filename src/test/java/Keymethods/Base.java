package Keymethods;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.gson.JsonObject;

import LeadCreation.Leadcreation;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Base {

	ExtentTest test;
	ScreenShot screenshot = new ScreenShot();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMddyyHHMMSSSS");
	String Date11 = dateFormat1.format(new Date());
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMddyyyyM");
	String Date12 = dateFormat2.format(new Date());
	

	public void Base1(String Username, ExtentReports extentreport,String Date1)
			throws InterruptedException, AWTException, IOException {

		try {
			test = extentreport.createTest(Username);
			URL url = new URL(Username);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			String body = connection.getResponseMessage();

			int status = 200;
			System.out.println(code + body + Username);

			if (status == code) {
				test.log(Status.PASS, Username + "---" + code + "  " + body);
			} else {
				test.log(Status.FAIL, Username + "---" + code + "  " + body);
				
				sendStatusToGoogleChat(Username + "---" + code + "  " + body);
				
			}
		} catch (Exception Warning) {
			System.out.println(Warning);
			test.log(Status.FAIL, Username + "--No response");
		}
		
	}
	private void sendStatusToGoogleChat(String message) {
		String k = "https://chat.googleapis.com/v1/spaces/AAAAgosrJz0/messages";
		RestAssured.baseURI = k;
		RequestSpecification httpRequest = RestAssured.given();
		JsonObject requestParams = new JsonObject();
		requestParams.addProperty("text", message);
		httpRequest.queryParam("key", "AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI");
		httpRequest.queryParam("token", "XPVjYvyGFQPwgsEP3bB3zaxfhfvIvSSHjVeX43-I8hk");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toString());
		Response response = httpRequest.request(Method.POST);
		System.out.println(response.asPrettyString());
}}
//TakesScreenshot screenshot1 = ((TakesScreenshot) driver);
//File srcFile1 = screenshot1.getScreenshotAs(OutputType.FILE);
//FileUtils.copyFile(srcFile1,
//		new File("\\\\14.140.167.188\\Vakilsearch\\AutomatonLeadCreation5\\"+Date1+"\\"+ Screenshot+".png"));
//test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(
//		"\\\\14.140.167.188\\Vakilsearch\\AutomatonLeadCreation5\\"+Date1+"\\"+ Screenshot+".png",Username).build());
//	}}
