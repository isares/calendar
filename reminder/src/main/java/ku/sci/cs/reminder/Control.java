package ku.sci.cs.reminder;

import java.util.ArrayList;

public class Control {

	private ArrayList<Reminder> reminderArray;
	private View view;
	
	public Control(){
		reminderArray = new ArrayList<Reminder>();
	}
	
	public void Run(){
		view = new View();
		view.frame.setVisible(true);
	}
	
	public void addReminder(java.util.Date inDate,String inNote){
		reminderArray.add(new Reminder(inDate,inNote));
	}
	
	public ArrayList<Reminder> getReminder(){
		return reminderArray;
	}
	
}
