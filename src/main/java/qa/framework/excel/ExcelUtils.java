package qa.framework.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private String filepath;
	private XSSFWorkbook workbook;
	
	private static final String ERR_COLUMN_MSG = "Error: Column Name %s is not available in excel sheet!!";
	private static final String ERR_UNKNOWN_DATATYPE = " Error: Unknown data type for value %s";
	
	/**
	 * @author skutl
	 * @param operation
	 * @param filepath
	 * @throws IOException
	 */
	
	public ExcelUtils(ExcelOperation operation, String filepath) throws IOException{
		
		this.filepath = filepath;
		if(operation.getOperation().equalsIgnoreCase("load")) {
			loadExcel();
		}
	}
	
	private void loadExcel() throws IOException{
		
		FileInputStream inpstrm = null;
		try {
			
			ZipSecureFile.setMinInflateRatio(0);
			inpstrm = new FileInputStream(this.filepath);
			this.workbook = new XSSFWorkbook(inpstrm);	
		}catch(Exception e) {
			e.printStackTrace();	
		}finally {
			inpstrm.close();
		}
		
	}
	
	public XSSFSheet createSheet(String sheetname) {
		
		XSSFSheet sheet = null;
		try {
			sheet = this.workbook.createSheet(sheetname);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sheet;
		
	}
	
	public void removeSheet(String sheetname) {
		
		int sheetIndex = workbook.getSheetIndex(sheetname);
		workbook.removeSheetAt(sheetIndex);
	}
	
	public XSSFSheet getSheet(String sheetname) {
		
		XSSFSheet sheet = null;
		try {
			
			sheet = this.workbook.getSheet(sheetname);
		} catch(Exception e) {
			
		}
		
		return sheet;
		
	}
	
	public boolean isSheetPresent(String sheetname) {
		
		boolean isSheetPresent = false;
		
		Iterator<Sheet> sheetItr = workbook.sheetIterator();
		while(sheetItr.hasNext()) {
			
			if(sheetname.trim().equals(sheetItr.next().getSheetName().trim())) {
				isSheetPresent = true;
				break;
				
			}
		}
		
		return isSheetPresent;
		
	}
	
	

}
