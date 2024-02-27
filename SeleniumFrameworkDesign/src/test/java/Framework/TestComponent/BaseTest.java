package Framework.TestComponent;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Framework.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	String customChromePath= "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
	
	public WebDriver initializeDriver() throws IOException {
		
		Properties prop= new Properties();
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Framework\\Resources\\GlobalData.properties");
		prop.load(fis);
		String browserNmae = System.getProperty("browser")!=null ? System.getProperty("browser") :System.getProperty("browser");
		//= prop.getProperty("browser");
		
		if(browserNmae.contains("chrome")) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setBinary(customChromePath);
		options.addArguments("--remote-allow-origins=*");
		if(browserNmae.contains("headless")) {
			options.addArguments("headless");
		}	
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		}else if(browserNmae.equalsIgnoreCase("firefox")) {
			//Firefox
		}else if(browserNmae.equalsIgnoreCase("edge")) {
			//edge
		}
        driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
		
		@Test
		public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
			//read Json to String
			
			String jsonContent=  FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
			
			//Convert String to HashMap
			ObjectMapper mapper= new ObjectMapper();
			List<HashMap<String,String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
			});
			return data;			
		}
		
		@Test
		public String getScreeshot(String testCaseName) throws IOException {
			TakesScreenshot ts=	(TakesScreenshot)driver;
			File source= ts.getScreenshotAs(OutputType.FILE);
			File destination= new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
			FileUtils.copyFile(source, destination);
			return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
		}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver= initializeDriver();
        landingPage= new LandingPage(driver);		
		landingPage.goTo();
		return landingPage;		
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
	

}
