package Keymethods;
import java.io.BufferedInputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipOutputStream;

import org.bouncycastle.asn1.x509.sigi.PersonalData;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.internal.mapping.JaxbMapper;
import io.restassured.response.Response;  
@Test
public class ZippingMultipleFiles{
 public void ohm() throws JsonMappingException, JsonProcessingException  {
  String baseUrl = "https://vakilsearch.com/sitemap.xml"; // Replace with your API URL

  // Send a GET request
  Response response = RestAssured.get(baseUrl);

  // Get the response body as a String
  String responseBody = response.getBody().asString();

  // Print the response body
  System.out.println(responseBody);
  
 
 
 
  }
}