package LeadCreation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Keymethods.Base;
import Keymethods.Driver;

import Keymethods.SendMailSSLWithAttachment;
import Keymethods.slack;
import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Leadcreation {

	ExtentReports extentreport;
	ExtentSparkReporter htmlReporter;
	ExtentTest test;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
	String Date1 = dateFormat.format(new Date());
	String[][] data = null;

	@DataProvider(name = "itemsdata")
	public String[][] loginDataProvider() throws BiffException, IOException {
		data = getExcelData();
		return data;
	}

	public String[][] getExcelData() throws BiffException, IOException {
		FileInputStream excel = new FileInputStream(
				"\\\\14.140.167.188\\Vakilsearch\\VakilsearchSmokeTesting\\Excel\\Items jxl boss.xls");
		Workbook workbook = Workbook.getWorkbook(excel);
		Sheet sheet = workbook.getSheet("Sheet7");
		int rowCount = sheet.getRows();
		int columnCount = sheet.getColumns();

		String testData[][] = new String[rowCount][columnCount];

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				testData[i][j] = sheet.getCell(j, i).getContents();

			}
		}
		System.out.println(testData.toString());
		return testData;

	}

	public WebDriver driver;

	@BeforeSuite
	public void Login() throws InterruptedException, AWTException {

		extentreport = new ExtentReports();
		htmlReporter = new ExtentSparkReporter(
				"\\\\14.140.167.188\\Vakilsearch\\AutomatonLeadCreation4\\" + Date1 + "\\extentreport.html");
		// htmlReporter = new
		// ExtentSparkReporter("C:\\Users\\admit\\git\\Automation-Critical-Flow\\GRCCriticalflow\\Screenshots\\"+Date1+"\\extentreport.html");

		extentreport.attachReporter(htmlReporter);

	}

	@BeforeTest
	public void Max() throws InterruptedException, AWTException {
		Driver d = new Driver(driver);
		WebDriverManager.chromedriver().setup();
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		ChromeOptions option = new ChromeOptions();
//		option.addArguments("incognito");
//
//		option.addArguments("start-maximized");
//		capabilities.setCapability(ChromeOptions.CAPABILITY, option);
//		option.addArguments("--headless");

		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@BeforeClass
	public void url() throws InterruptedException, AWTException {

//		PageFactory.initElements(driver, HomescreenPageobject.class);
//		PageFactory.initElements(driver, HelpdeskPageobject.class);
//		Thread.sleep(3000);

	}

	@Test(dataProvider = "itemsdata")
	public void Customercreation100(String Username, String Screenshot) throws Exception {

//		Base base = new Base();
//		base.Base1(Username, extentreport,Date1);

	}

	@AfterClass
	public void close() {
		// driver.close();
		System.out.println("The close_up process is completed");

	}

	@AfterTest
	public void Report() {
		extentreport.flush();

		System.out.println("Test completed");

	}

	@AfterSuite
	public void Mail() throws EmailException {
		try {
			Robot robot = new Robot();
			String screenshotLocation = "\\\\14.140.167.188\\Vakilsearch\\AutomatonLeadCreation4\\" + Date1
					+ "\\ExtentreportScreenshot.png";
			String extentreportLocation = "\\\\14.140.167.188\\Vakilsearch\\AutomatonLeadCreation4\\" + Date1
					+ "\\extentreport.html";
			String messageInputdata = "URL response Automation Test Report";
			
			
			
			SendMailSSLWithAttachment Mail = new SendMailSSLWithAttachment();
			Mail.main(extentreportLocation, messageInputdata,Date1);
		    slack slackmsg = new slack();
			slackmsg.slackMessageTest(driver, screenshotLocation, extentreportLocation, messageInputdata);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			// Handle exceptions appropriately, log them
			e.printStackTrace();
		} finally {
			// Ensure that driver is quit even if there is an exception
			if (driver != null) {
				driver.quit();
			}

		}

	}
}
