package ku.sci.cs.reminder;

public class Reminder {

	private java.util.Date date;
	private String note;
	
	public Reminder(java.util.Date inDate,String inNote){
		date = inDate; 
		note = inNote;
	}
	
	public java.util.Date getDate(){
		return date;
	}
	
	public String getNote(){
		return note;
	}
}
