package qa.framework.browser;

import org.openqa.selenium.WebDriver;

public interface Browser {
	
	public WebDriver getBrowser() throws InterruptedException;

}
