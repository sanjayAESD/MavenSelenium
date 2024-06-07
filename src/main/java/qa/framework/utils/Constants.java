package qa.framework.utils;

import qa.framework.fileoperation.PropertyFileUtil;

public class Constants {
	
	
	public static final int IMPLICITLY_WAIT = 20;
	public static final String IMG_ICON_PATH = "./src/main/resources/core/image/image-icon.png";
	public static final String LINK_ICON_PATH = "./src/main/resources/core/image/image-icon.png";
	public static final String EXTENT_BASE_DIR_PATH = "./extent-reports";
	public static final String EXTENT_REPORT_CRRRUN_DIR_PATH = EXTENT_BASE_DIR_PATH + "/" + "current-run";
	public static final String EXT_REPORT_PATH = EXTENT_REPORT_CRRRUN_DIR_PATH + "/" + "Index.html";

	public static final int FLUENT_POOLING_TIME = 2;
	public static final int FLUENT_WAIT = 30;
	public static final int WAIT_TIME = 20;
	
	public static final String UI_OBJREPO = "./src/test/resources/repository/ui-object-repository-json";
	public static final String ERR_MSG = "Error: Element with key %s not available in object repository";
	public static final String ERR_Locator_MSG = "Error:Locator in key %s not found !!";
	public static final String ERR_NO_BY_JS_MSG = "JS locator does have by: Key: %s";
	
	public static final PropertyFileUtil config = new PropertyFileUtil("./config.properties");
	public static final int HOW_OLD_REPORTS = Integer.parseInt(System.getProperty("config.report.howold", Constants.config.getProperty("config.report.howold")));
	
	/*Name on Extent Report*/
	public static final String REPORT_NAME = System.getProperty("config.report.name", Constants.config.getProperty("config.report.name"));
	public static final String REPORT_TIMEZONE = System.getProperty("config.report.timezone", Constants.config.getProperty("config.report.timezone")).toUpperCase();
	

}
