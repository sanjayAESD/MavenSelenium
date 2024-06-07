package qa.framework.excel;

public enum ExcelOperation {
	
	LOAD("load");
	
	private String operation;
	
	ExcelOperation(String opertion){
		
		this.operation=operation;
	}

	public String getOperation() {
		return this.operation;
	}
	
	
}
