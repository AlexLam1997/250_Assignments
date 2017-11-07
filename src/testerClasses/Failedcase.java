package testerClasses;

public class Failedcase {
	String expression;
	int expectedvalue;
	int calculatedvalue;
	int expressionID;
	String note="Actual error";
	
	public Failedcase(String expression, int expectedvalue, int calculatedvalue, int expressionID) {
		this.calculatedvalue=calculatedvalue;
		this.expression=expression;
		this.expectedvalue=expectedvalue;
		this.expressionID=expressionID;
	}
	
	public Failedcase(String expression, int expectedvalue, int calculatedvalue, int expressionID,String note) {
		this.calculatedvalue=calculatedvalue;
		this.expression=expression;
		this.expectedvalue=expectedvalue;
		this.expressionID=expressionID;
		this.note=note;
	}
	
	public String getExp(){
		return expression;
	}
	
	public int getEValue(){
		return expectedvalue;
	}
	
	public int getCValue(){
		return calculatedvalue;
	}
	
	public int getID(){
		return expressionID;
	}
	public String getNote(){
		return note;
	}
}
