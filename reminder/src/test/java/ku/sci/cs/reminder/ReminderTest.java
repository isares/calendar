package ku.sci.cs.reminder;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ReminderTest {
	
	Date date;
	Date time;
	String note;
	Reminder reminder;
	Control control;
	ArrayList<Reminder> reminderArray;
	
	@Before
	public void setUp(){
		String dateTime = "12/03/2010 08:00";
		note = "test";
		reminder = new Reminder(dateTime,note);
		control = new Control();
		reminderArray = new ArrayList<Reminder>();
	}

	@Test
	public void getDateReminder() {
		assertEquals(date,reminder.getDateTime());
	}
	
	@Test
	public void getNoteReminder() {
		assertEquals(note,reminder.getNote());
	}

	@Test
	public void getReminderArray() {
		assertEquals(reminderArray,control.getReminder());
	}
	
	@Test
	public void addReminder() {
		int sizeBeforeAdd = control.getReminder().size();
		control.addReminder(date,time,note);
		int sizeAfterAdd = control.getReminder().size();
		assertTrue(sizeBeforeAdd < sizeAfterAdd);
	}
}
