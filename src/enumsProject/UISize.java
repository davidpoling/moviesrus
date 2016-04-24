package enumsProject;

public enum UISize {
	FIELDHEIGHT(22),
	FIELDWIDTH_SM(100),
	FIELDWIDTH_MD(150),
	FIELDWIDTH_LG(200),
	FIELDWIDTH_XL(250);
	
	private int size;
	
	UISize(int size){
		this.size = size;
	}
	
	public int getSize(){
		return size;
	}
}
