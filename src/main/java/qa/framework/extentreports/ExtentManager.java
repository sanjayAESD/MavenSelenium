package qa.framework.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import qa.framework.utils.Constants;

public class ExtentManager {
	
	public static final ExtentReports extentReports = new ExtentReports();
	
	public synchronized static ExtentReports createExtentReports() {
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(Constants.EXTENT_REPORT_CRRRUN_DIR_PATH);
		reporter.config().setReportName(Constants.REPORT_NAME);
		
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("OS:", System.getProperty("os.name"));
		extentReports.setSystemInfo("Version:", System.getProperty("os.version"));
		extentReports.setSystemInfo("Architecture:", System.getProperty("os.arch"));
		
		
		return extentReports;
		
		
	}

}
