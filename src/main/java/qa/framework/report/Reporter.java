package qa.framework.report;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebElement;

import qa.framework.browser.WebBrowserManager;
import qa.framework.extentreports.ExtentTestManager;
import qa.framework.fileoperation.FileUtils;
import qa.framework.utils.Constants;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Reporter {
	
	private static final String dirPath = Constants.EXTENT_REPORT_CRRRUN_DIR_PATH;
	private static final String initImgFilename = "image";
	private static final String initFileName = "file";
	private static int imgCounter = 0;
	private static int fileCounter = 0;
	private static final String DEFAULT_MSG_COLOR = "#000000";
	private static final String DEFAULT_MSG = "Click on the image icon to view screenshot";
	
	/*
	 * this HTML will be embedded into the extent report to attached screenshot
	 */
	
	private static String embeddHtml = "<div class=\"embedded-Image\" style=\"margin-top:10px\">\r\n" + "    \r\n"
	+ "   <label style=\"color: ${txgmsgcolor}\"> ${txgsg} >></label>\r\n"
	+ "   <a href=\"%s\" target=\"blank\" style=\"float:right;\">\r\n" +" 		<img src=\"${coreimagename}\" style =\"width:50px;\"/>\r\n"
	+ "   </a>\r\n" + " \r\n" + "<div>";	
	
	
	private static synchronized int incrementCounter() {
		
		imgCounter += 1;
		return imgCounter;
	}
			
	private static synchronized int incrementFileCounter() {
		
		fileCounter +=1;
		return fileCounter;
		
	}
	
	/**
	 * Takes webelement screenshot on webpage
	 * 
	 * @author sanjay
	 * @param element
	 * 
	 */
	
	public void captureWebElement(WebElement element) {
		
		try {
			String filename = initImgFilename + "_" + incrementCounter() + ".png";
			String filePath = dirPath + "/" + filename;
			
			ru.yandex.qatools.ashot.Screenshot screenshot = new AShot().takeScreenshot(WebBrowserManager.getBrowser(), element);
			ImageIO.write(screenshot.getImage(), "PNG", new File(filePath));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Takes screenshot of viewpoint of webpage.
	 * 
	 * @author sanjay
	 * 
	 */

	public void captureWebScreen(String msg, String rgbColour) {
		
		try {
			String filename = initImgFilename + "_" + incrementCounter() + ".png";
			String filePath = dirPath + "/" + filename;
			
		
			String tempEmbeddedHtml = embeddHtml; 
			
			if(msg ==null) {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("$(txtmsg)", DEFAULT_MSG);
			} else {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("$(txgmsg)", msg);
			}
			
			if(rgbColour == null) {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("$(txtmsgcolor)", DEFAULT_MSG_COLOR);	
			} else {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("$(txgmsgcolor)", rgbColour);
			}
			
			/* attaching cilck able image icon */
			tempEmbeddedHtml = tempEmbeddedHtml.replace("$(coreimagename)", "image-icon.png");
			
			ru.yandex.qatools.ashot.Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.simple())
					.takeScreenshot(WebBrowserManager.getBrowser());
			ImageIO.write(screenshot.getImage(), "PNG", new File(filePath));
			
			ExtentTestManager.getTest().info(String.format(tempEmbeddedHtml, filename));
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void embedFile(String fileContent, String fileType, String msg, String rgbColour) {
		
		try {
			
			/* forming unique file name - so that data should not overlap */
			String filename = initFileName + "_" + incrementFileCounter();
			String filePath;
			String tempEmbeddedHtml = embeddHtml;
			
			/*file type*/
			switch(fileType.toLowerCase()) {
			
			case "xml" :
				filename = filename + ".xml";
				break;
			case "json" :
				filename = filename + ".json";
				break;
			case "txt" :
				filename = filename + ".txt";
				break;
			default :
				filename = filename + ".txt";
			}
			
			/*file directory path*/
			filePath = dirPath + "/" + filename;
			
			/*creating the file */
			FileUtils.createFile(filePath);
			
			/* writing data to file */
			FileUtils.write(filePath, fileContent);
			
			if(msg == null) {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("${txtmsg}", DEFAULT_MSG);
			} else {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("${txtmsg}", msg);
			}
			
			if(rgbColour == null) {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("${txtmsgcolor}", DEFAULT_MSG_COLOR);
			} else {
				tempEmbeddedHtml = tempEmbeddedHtml.replace("${txtmsgcolor}", rgbColour);
			}
			
			/* attaching click able image icon */
			tempEmbeddedHtml = tempEmbeddedHtml.replace("${coreimagename}", "link-icon.png");
			
			/*embedding the html to extent report*/
			ExtentTestManager.getTest().info(String.format(tempEmbeddedHtml, filename));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Delete old reports from extent-report directory
	 * 
	 * @author sanjay
	 * @param howOldReportInDays
	 * 
	 */
	
		public static void deleteOldReports(int howOldReportInDays) {
			
			/*Path on Extent Report directory */
			String extentReportsDirPath = Constants.EXTENT_BASE_DIR_PATH;
			
			File extendReportsDir = new File(extentReportsDirPath);
			
			/*
			 * Created a file filter so that current-run is not evaludated for deletion
			 * 
			 * 
			 */
			
			FileFilter currentRunFilefilter = new FileFilter() {
				
				@Override
				public boolean accept(File file) {
					
					if(file.getName().matches("current-run")) {
						return false;
					}
					return true;
				}
			};
			
			/* List of reports directory under in extent-reports directory */
			File[] reportDirs = extendReportsDir.listFiles(currentRunFilefilter);
			
			for(File reportDir : reportDirs) {
			
			try {
				
				/* Getting last modification/creation date of the report dir */
				FileTime fileTime = Files.getLastModifiedTime(reportDir.toPath());
				
				long fileCreationTImeInMillis = fileTime.toMillis();
				
				Calendar cal = Calendar.getInstance();
				long currentTimeInMillis = cal.getTimeInMillis();
				
				/*Converting the difference in days */
				long numofDaysBackFileCreated = TimeUnit.DAYS.convert(currentTimeInMillis - fileCreationTImeInMillis, TimeUnit.MILLISECONDS);
				
				/*
				 * comparing if file is created more than days mention in argument
				 * 
				 */
				if(numofDaysBackFileCreated > howOldReportInDays) {
					FileUtils.deleteDir(reportDir.getAbsolutePath());
				}
				
			}catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
	
	}
}
