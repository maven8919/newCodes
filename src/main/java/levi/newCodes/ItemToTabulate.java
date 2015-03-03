package levi.newCodes;

public class ItemToTabulate {
	private String cbCode;
	private int    levelInExcel;
	private String engTrans;
	private String codesToTab;
	
	public ItemToTabulate(String cbCode, int levelInExcel, String engTrans) {
		super();
		this.cbCode = cbCode;
		this.levelInExcel = levelInExcel;
		this.engTrans = engTrans;
	}
	
	public String getCbCode() {
		return cbCode;
	}
	
	public void setCbCode(String cbCode) {
		this.cbCode = cbCode;
	}
	
	public int getLevelInExcel() {
		return levelInExcel;
	}
	
	public void setLevelInExcel(int levelInExcel) {
		this.levelInExcel = levelInExcel;
	}
	
	public String getEngTrans() {
		return engTrans;
	}
	
	public void setEngTrans(String engTrans) {
		this.engTrans = engTrans;
	}
	
	public String getCodesToTab() {
		return codesToTab;
	}

	public void setCodesToTab(String codesToTab) {
		this.codesToTab = codesToTab;
	}
}
