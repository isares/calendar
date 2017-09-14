package ku.sci.cs.reminder;

public class Reminder {

	private String dateTime;
	private String note;
	private String repeat;
	
	public Reminder(String inDateTime,String inNote,String inRepeat){
		dateTime = inDateTime; 
		note = inNote;
		repeat = inRepeat;
	}
	
	public String getDateTime(){
		return dateTime;
	}
	
	public String getNote(){
		return note;
	}
	
	public String getRepeat(){
		return repeat;
	}
}
