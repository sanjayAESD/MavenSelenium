package qa.framework.browser;

import java.time.Duration;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import qa.framework.utils.Constants;



public class ChromeBrowser implements Browser {
	
	private WebDriver driver;
	
	
	public WebDriver getBrowser() {
		
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/driver/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICITLY_WAIT));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.IMPLICITLY_WAIT));
		
		return driver;
	}

}
