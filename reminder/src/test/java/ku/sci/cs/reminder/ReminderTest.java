//Isares Srirattanapaisarn 5710404748

package ku.sci.cs.reminder;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class ReminderTest {
	
	Date date;
	Date time;
	Date change_date;
	Date change_time;
	String dateTime;
	String note;
	String repeat;
	String change_note;
	String change_repeat;
	Reminder reminder;
	Control control;
	ArrayList<Reminder> reminderArray;
	
	@Before
	public void setUp(){
		
		dateTime = "12/03/2560 08:00";
		note = "test";
		repeat = "None";

		date = new Date();
		time = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			change_date = sdf.parse(dateTime);
			change_time = sdf.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		change_note = "change";
		change_repeat = "Daily";
		
		reminder = new Reminder(dateTime,note,repeat);
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
	public void getRepeat(){
		assertEquals(repeat,reminder.getRepeat());
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
		control.addReminder(date,time,note,repeat);
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
        
		control.updateReminder(inDateTime, change_date, change_time, change_note,change_repeat);
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
