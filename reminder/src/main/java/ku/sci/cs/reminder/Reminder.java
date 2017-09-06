package ku.sci.cs.reminder;

public class Reminder {

	private String dateTime;
	private String note;
	
	public Reminder(String inDateTime,String inNote){
		dateTime = inDateTime; 
		note = inNote;
	}
	
	public String getDateTime(){
		return dateTime;
	}
	
	public String getNote(){
		return note;
	}
}
