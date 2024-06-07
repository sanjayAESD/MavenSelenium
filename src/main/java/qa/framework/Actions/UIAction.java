package qa.framework.Actions;

import org.openqa.selenium.WebElement;

public class UIAction {
	
	public static void pause(long time) {
		try {
			Thread.sleep(time);
		} catch(InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void openUrl(String url) {
		
		try {
			
			//WebBrowserManager.getBrowser().get(url);
			
		}catch (Exception e) {
			
			
		}
	}
	
	public static void click(WebElement element) {
		
		try {
			
		element.click();
		
		} catch(Exception e) {
			
			
		}
		
	}

}
