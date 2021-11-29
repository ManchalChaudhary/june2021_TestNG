package conceptTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTestNG {
	WebDriver driver;
	// String browser = "Firefox";
	String browser;
	String url;
	// here we dealing with login data

	String USER_NAME = "demo@techfios.com";
	String PASSWORD = "abc123";

	// TestData
	String FULL_NAME = "TestNG";
	String COMPANY_NAME = "Techfios";
	String EMAIL = "abc3221@techfios.com";
	String PHONE_NUMBER = "2237438328";
	String COUNTRY = "Angola";

	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//*[@id=\"password\"]");
	By signInbuttonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");
	By customerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By addCustomerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//*[@id=\"account\"]");
	By companyNameField = By.xpath("//*[@id=\"cid\"]");
	By emailField = By.xpath("//*[@id=\"email\"]");
	By phoneField = By.xpath("//*[@id=\"phone\"]");
	By countryField = By.xpath("//*[@id=\"country\"]");
	By saveField = By.xpath("//*[@id=\"submit\"]");

	@BeforeClass
	public void readConfig() {
		// 5 Ways to Read a File in Java – BufferedReader, FileInputStream, Files,
		// Scanner, RandomAccessFile

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\cofigure.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used:" + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers2\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers2\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("url");
	}

	@Test(priority=1)
	public void loginTest() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passwordField).sendKeys(PASSWORD);
		driver.findElement(signInbuttonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wrong Page!!");

		driver.findElement(customerButtonField).click();
		driver.findElement(addCustomerButtonField).click();
		
		
		generateRandomNo(999);
		
		
		//driver.findElement(fullNameField).sendKeys(FULL_NAME);
		driver.findElement(fullNameField).sendKeys(FULL_NAME + generateRandomNo(999));
//		Select sel = new Select(driver.findElement(companyNameField));
//		sel.selectByVisibleText(COMPANY_NAME);
		
		//selectFromDropdown(driver.findElement(companyNameField), COMPANY_NAME);
		selectFromDropdown(companyNameField, COMPANY_NAME);
		
		driver.findElement(companyNameField).sendKeys(COMPANY_NAME);
		//String generateRandomNo(999);
		//driver.findElement(emailField).sendKeys(EMAIL);
		driver.findElement(emailField).sendKeys(generateRandomNo(999) + EMAIL);
		//driver.findElement(phoneField).sendKeys(PHONE_NUMBER);
		driver.findElement(phoneField).sendKeys(PHONE_NUMBER + generateRandomNo(999));
		//driver.findElement(countryField).sendKeys(COUNTRY);
		//Select sel2 = new Select(driver.findElement(countryField));
		// sel2.deselectByVisibleText(COUNTRY);
		//selectFromDropdown(driver.findElement(countryField), COUNTRY);
		selectFromDropdown(countryField, COUNTRY);
		driver.findElement(saveField).click();
	}



	public int generateRandomNo(int boundaryNumber) {
		Random rnd = new Random();
		int genratedNo = rnd.nextInt(boundaryNumber);
		return genratedNo;
	}

	public void selectFromDropdown(By element, String visibleText) {
		//Select sel = new Select(element);
		Select sel = new Select(driver.findElement(element));
		
		sel.selectByVisibleText(visibleText);
		
	}

	@Test(priority=2)
	public void addCustomerTest() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passwordField).sendKeys(PASSWORD);
		driver.findElement(signInbuttonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wrong Page!!");
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
