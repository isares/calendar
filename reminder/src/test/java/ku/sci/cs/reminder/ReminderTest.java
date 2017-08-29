package ku.sci.cs.reminder;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ReminderTest {
	
	Date date;
	String note;
	Reminder reminder;
	Control control;
	ArrayList<Reminder> reminderArray;
	
	@Before
	public void setUp(){
		date = new Date(105,10,5);
		note = "test";
		reminder = new Reminder(date,note);
		control = new Control();
		reminderArray = new ArrayList<Reminder>();
	}

	@Test
	public void getDateReminder() {
		assertEquals(date,reminder.getDate());
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
		control.addReminder(date,note);
		int sizeAfterAdd = control.getReminder().size();
		assertTrue(sizeBeforeAdd < sizeAfterAdd);
	}
}
