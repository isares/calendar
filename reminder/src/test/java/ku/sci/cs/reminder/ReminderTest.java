package ku.sci.cs.reminder;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class ReminderTest {
	
	java.util.Date date;
	java.util.Date time;
	java.util.Date change_date;
	java.util.Date change_time;
	String change_note;
	String dateTime;
	String note;
	Reminder reminder;
	Control control;
	ArrayList<Reminder> reminderArray;
	
	@Before
	public void setUp(){
		
		dateTime = "12/03/2560 08:00";
		note = "test";

		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		time = calendar.getTime();

		//for change dateTime
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		int new_date = Integer.parseInt(dateTime.substring(0,2));
		int new_month = Integer.parseInt(dateTime.substring(3,5));
		int new_year = Integer.parseInt(dateTime.substring(6,10));
		int new_hour = Integer.parseInt(dateTime.substring(11,13));
		int new_min = Integer.parseInt(dateTime.substring(14,16));
		calendar = new GregorianCalendar(new_year,new_month - 1,new_date,new_hour,new_min);
		change_date = calendar.getTime();
		change_time = calendar.getTime();
		change_note = "change";
		
		reminder = new Reminder(dateTime,note);
		control = new Control();
		control.connectSQLite();
		reminderArray = new ArrayList<Reminder>();

	}

	@Test
	public void getDateReminder() {
		assertEquals(dateTime,reminder.getDateTime());
	}
	
	@Test
	public void getNoteReminder() {
		assertEquals(note,reminder.getNote());
	}

	@Test
	public void connectSQLite() {
		assertEquals(true,control.connectSQLite());
	}
	
	@Test
	public void getReminderArray() {
		assertTrue(control.getReminder() != null);
	}
	
	@Test
	public void addReminder() {
		int sizeBeforeAdd = control.getReminder().size();
		control.addReminder(date,time,note);
		int sizeAfterAdd = control.getReminder().size();
		assertTrue(sizeBeforeAdd < sizeAfterAdd);
	}
	
	@Test
	public void updateReminder() {
		SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        String fdate = dformat.format(date);
        
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String ftime = tformat.format(time);
        
        String inDateTime = fdate + " " + ftime;
        
		control.updateReminder(inDateTime, change_date, change_time, change_note);
		reminderArray = control.getReminder();
		for (Reminder rem : reminderArray){
			if (inDateTime.equals(rem.getDateTime())){
				System.out.println("Nothing change.");
			} else if (dateTime.equals(rem.getDateTime())){
				assertEquals(change_note,rem.getNote());
			}
		}
	}
	
	@Test
	public void deleteReminder() {
		dateTime = "12/03/3103 08:00";
		ArrayList<String> chose = new ArrayList<String>();
		chose.add(dateTime);
		int sizeBeforeAdd = control.getReminder().size();
		control.deleteReminder(chose);
		int sizeAfterAdd = control.getReminder().size();
		assertTrue(sizeBeforeAdd > sizeAfterAdd);
	}
	
	@Test
	public void closeConnectSQLite() {
		assertEquals(true,control.closeConnectSQLite());
	}
}
