package baseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.github.javafaker.Faker;

public class BaseClass {
	public static WebDriver driver;
	Faker fk = new Faker();
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "browser", "os" })
	public void setup(String browser, String os) throws IOException {
		try {
			// Loading the config.properties file
			FileInputStream fis = new FileInputStream("./src//test//resources//config.properties");
//		FileReader fr = new FileReader("./src//test//resources//config.properties");
			p = new Properties();
			p.load(fis);

			logger = LogManager.getLogger(this.getClass());

			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				System.out.println("Invalid browser name..");
				throw new RuntimeException("Invalid browser name: " + browser);
//				throw new SkipException("Skipping tests: Invalid browser name - " + browser);				

			}
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(p.getProperty("appUrl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		} catch (IOException e) {

			logger.error("Failed to load config.properties file", e);
			throw new RuntimeException("Setup failed due to missing or invalid properties file", e);

			// for skipping the test
//			  throw new SkipException("Skipping tests: Config file not found or invalid");

		} catch (Exception e) {
			logger.error("Setup failed due to unexpected error", e);
			throw new RuntimeException("Setup failed due to unexpected error", e);
			// for skipping the test
//			 throw new SkipException("Skipping tests due to unexpected setup error: " + e.getMessage());
		}
	}

	public String firstName() {
		return fk.name().firstName();
	}

	public String lastName() {
		return fk.name().lastName();
	}

	public String randomEmail() {

		 return fk.internet().emailAddress();
	}

	public String captureScreenshot(String tname) {
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		TakesScreenshot takeScreenshot = ((TakesScreenshot) driver);
		File srcFIle = takeScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		File targetFile = new File(targetFilePath);
		try {
			FileUtils.copyFile(srcFIle, targetFile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return targetFilePath;
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void teardown() {
//		driver.quit();
	}

}
