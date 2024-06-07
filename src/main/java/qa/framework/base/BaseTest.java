package qa.framework.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import qa.framework.browser.WebBrowserManager;
import qa.framework.extentreports.ExtentManager;
import qa.framework.extentreports.ExtentTestManager;
import qa.framework.fileoperation.FileUtils;
import qa.framework.report.Reporter;
import qa.framework.utils.Constants;

public class BaseTest {
	
	private String reportBackUpPath = "${BaseDirPath}/${suiteName} ${currentTime}";
    private static boolean isCurrentRunReportFolderReset = false;
	
	public void reportDirPath(String suiteName) throws IOException {
		
		Calendar cl = Calendar.getInstance();
		Date time = cl.getTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd hh mm ss aa z");
		formatter.setTimeZone(TimeZone.getTimeZone(Constants.REPORT_TIMEZONE));
		
		String format = formatter.format(time);
		
		reportBackUpPath = reportBackUpPath.replace("${BaseDirPath}", Constants.EXTENT_BASE_DIR_PATH).
				replace("${suitename}", suiteName).replace("${currentTime}", format);
		
		FileUtils.createDir(reportBackUpPath);
		
	}
	
	@BeforeSuite
	public void beforesuite(ITestContext context) throws Exception {
		
		String suitename = context.getCurrentXmlTest().getSuite().getName();
		
		/*will replace this is better code*/
		
		/*
		 * resetting current only once. in case we have multiple suite xml in the pom.xml
		 */
		
		if(!isCurrentRunReportFolderReset) {
			
			/*creating extent report folder (if not exists already) */
		
			
			FileUtils.createDir(Constants.EXTENT_REPORT_CRRRUN_DIR_PATH);
			
			/*cleaning extent report folder*/
			FileUtils.emptyDir(Constants.EXTENT_REPORT_CRRRUN_DIR_PATH );
			
			
			/*copying image-icon.png*/
			FileUtils.copy(Constants.IMG_ICON_PATH, Constants.EXTENT_REPORT_CRRRUN_DIR_PATH + "/image-icon.png");
			
			/*copying 'link-icon.png' to current report folder*/
			FileUtils.copy(Constants.LINK_ICON_PATH, Constants.EXTENT_REPORT_CRRRUN_DIR_PATH + "/image-icon.png");
			
			/*making is true*/
			isCurrentRunReportFolderReset = !isCurrentRunReportFolderReset;
			
		}
		
	}
	
	@AfterSuite
	public void afterSuite() throws IOException {
		
		/*Flushing extent report at the end of execution*/
		ExtentManager.extentReports.flush();
		
		/*copying report to backup folder */
		FileUtils.copyDirContent(Constants.EXTENT_REPORT_CRRRUN_DIR_PATH, reportBackUpPath);
		
		/*deleting old report, to over come low disk space issue */
		Reporter.deleteOldReports(Constants.HOW_OLD_REPORTS);
		
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext context, ITestResult result) throws Exception {
		
		String testname = context.getName();
		String description = result.getMethod().getDescription();
		
		String runStatus = context.getCurrentXmlTest().getParameter("runStatus");
		
		ExtentTestManager.startTest(testname, description);
		/* extent test will not get created if runstatus is no*/
		
		if(runStatus == null || runStatus.equalsIgnoreCase("yes")) {
		/* starting extent report test */
		// ExtentTestManager.startTest(testname,description);
			
			
		}
		
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		/*success = 1, fail = 2, and skipped = 3 */
		int status = result.getStatus();
		
		/*UI*/
		if(WebBrowserManager.getFlag()==true) {
			
			/*taking screenshot on failure*/
			if(status == 2) {
				
				Reporter image = new Reporter();
				image.captureWebScreen("Click on the image icon to view failure screenshot", "#FF0000");
			}
			
			/*Quitting all instances of webdriver */
			WebBrowserManager.quit();
			
			/* making uiflag as false*/
			WebBrowserManager.setFlag(false);
			
		}
		
		/*flushing report per method*/
		ExtentManager.extentReports.flush();
		
		
	}
	
	
	
	
}
