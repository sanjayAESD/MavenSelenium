package qa.framework.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.WebDriverWait;

import qa.framework.utils.Constants;

public class WebBrowserManager {
	
	private static final String ERR_BROWSER = "Error : Browser provided i.e. %s is not being coded for !!";
	private static ThreadLocal<String> tLBrowser = new ThreadLocal<String>();
	private static ThreadLocal<Boolean> uiflag = new ThreadLocal<Boolean>();
	private static ThreadLocal<WebDriver> tLWebDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<WebDriverWait> tlWebDriverWait = new ThreadLocal<WebDriverWait>();
	
	private static boolean grid = false;
	private static String gridHostname = "undefine";
	private static String gridURL = "http://${hostname}.ad.allstate.com:4444/wd/hub";
	private static boolean incognito = false;
	
	public static void configBrowse(String browser) throws Exception{
		
		
		tLBrowser.set(browser);
		
		
		switch(browser.toLowerCase()) {
		
		case "chrome":{
			
			tLWebDriver.set(ThreadGuard.protect(new ChromeBrowser().getBrowser()));
			setFlag(true);
			break;
			
		}
		
		
		
		}
		
		
	}

	public static void setFlag(boolean uiflag) {
		
		WebBrowserManager.uiflag.set(uiflag);
	}
	
	public static boolean getFlag() {
		
		Boolean flagValue = uiflag.get();
		
		if(flagValue == null) {
			return false;
		}
		return flagValue.booleanValue();
		
		
	}
	
	public static synchronized WebDriver getBrowser() {
		
		return tLWebDriver.get();
		
	}
	
	public static synchronized String getBrowserName() {
		
		
		return tLBrowser.get();
		
	}
	
	public static void quit() {
		
		tLWebDriver.get().quit();
		
	}
	
	public static boolean getGridConfig() {
		return grid;
		
	}
	
	public static String gridURL() {
		return gridURL;
		
	}
	
	public static boolean getIncognitoModeConfig() {
		return incognito;
			
	}
	

}
