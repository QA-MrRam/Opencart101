package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	public static WebDriver initDriver(String browser) {

		boolean headless = false;
		switch (browser) {
		case "chrome":
			ChromeOptions chromeOption = new ChromeOptions();
			if (headless) {
				chromeOption.addArguments("--headless=new");
			}
			WebDriver chromedriver = new ChromeDriver(chromeOption);
			tldriver.set(chromedriver);
			break;

		case "firefox":
			FirefoxOptions firefoxOption = new FirefoxOptions();

			if (headless) {
				firefoxOption.addArguments("--headless=new");
			}
			WebDriver firefoxdriver = new FirefoxDriver(firefoxOption);
			tldriver.set(firefoxdriver);
			break;

		case "edge":
			EdgeOptions edgeOption = new EdgeOptions();

			if (headless) {
				edgeOption.addArguments("--headless=new");
			}
			WebDriver edgedriver = new EdgeDriver(edgeOption);
			tldriver.set(edgedriver);
			break;
		default:
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}

		return getDriver();
	}

	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public static void quitDriver() {
		if (tldriver.get() != null) {
			tldriver.get().quit();
			tldriver.remove();

		}
	}
}
