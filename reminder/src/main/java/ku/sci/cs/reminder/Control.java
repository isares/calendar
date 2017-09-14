package ku.sci.cs.reminder;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Control {

	private View view;
	private String dbURL;
	private Connection connect;
	private Statement statement;
	
	public void Run(){
		view = new View();
		view.frame.setVisible(true);
	}
	
	public Boolean connectSQLite(){
		try {
			// setup
			Class.forName("org.sqlite.JDBC");
			dbURL = "jdbc:sqlite:reminder.db";
			connect = DriverManager.getConnection(dbURL);
			if (connect != null) {
				System.out.println("Connected to the database....");
				statement = connect.createStatement();
			}
			return true;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public Boolean closeConnectSQLite(){
		try {
			connect.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean addReminder(java.util.Date inDate,java.util.Date inTime,String inNote,String inRepeat){
		SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dformat.format(inDate);
        
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String time = tformat.format(inTime);
        
        String dateTime = date + " " + time;
        
		try {
			String query = "Insert into reminder (datetime,note,repeat) Values (\"" + dateTime + "\",\"" + inNote + "\",\"" + inRepeat + "\")";
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteReminder(ArrayList<String> inChose){
		try {
			for (String dateTime : inChose){
				String query = "Delete From reminder Where datetime = \"" + dateTime + "\"";
				statement.execute(query);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Boolean updateReminder(String inDateTime_first,Date inDate,Date inTime,String inNote,String inRepeat){
		SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dformat.format(inDate);
        
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String time = tformat.format(inTime);
        
        String dateTime = date + " " + time;

		try {
			String query = "Update reminder Set datetime = \"" + dateTime + "\"" 
					+ ", note = \"" + inNote + "\""
					+ ", repeat = \"" + inRepeat + "\""
					+ "Where datetime = \"" + inDateTime_first + "\"";
			statement.execute(query);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Reminder> getReminder(){
		ArrayList<Reminder> reminderArray = new ArrayList<Reminder>();
		try {
			String query = "Select * from reminder";
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String dateTime = resultSet.getString(1);
				String note = resultSet.getString(2);
				String repeat = resultSet.getString(3);
				reminderArray.add(new Reminder(dateTime,note,repeat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reminderArray;
	}
	
	public void repeatedCheck(){
		try {
			String query = "Select * from reminder";
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			Date curDatetime = new Date();
			while (resultSet.next()) {
				String dateTime = resultSet.getString(1);
				String note = resultSet.getString(2);
				String repeat = resultSet.getString(3);
				
				if (!repeat.equals("None")){
					Date Rdatetime = null;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					try {
						Rdatetime = sdf.parse(dateTime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (repeat.equals("Daily")){
						if (curDatetime.getHours() > Rdatetime.getHours()){
							Rdatetime.setDate(Rdatetime.getDate() + 1);
						} else{
							while (curDatetime.getDate() > Rdatetime.getDate() || curDatetime.getMonth() > Rdatetime.getMonth() || curDatetime.getYear() > Rdatetime.getYear()){
								Rdatetime.setDate(Rdatetime.getDate() + 1);
							}
						}
					} else if (repeat.equals("Weekly")){
						if (curDatetime.getHours() > Rdatetime.getHours()){
							Rdatetime.setDate(Rdatetime.getDate() + 7);
						} else{
							while (curDatetime.getDate() > Rdatetime.getDate() || curDatetime.getMonth() > Rdatetime.getMonth() || curDatetime.getYear() > Rdatetime.getYear()){
								Rdatetime.setDate(Rdatetime.getDate() + 7);
							}
						}
					} else if (repeat.equals("Monthly")){
						if (curDatetime.getHours() > Rdatetime.getHours() || (curDatetime.getDate() > Rdatetime.getDate() && curDatetime.getMonth() == Rdatetime.getMonth())){
							Rdatetime.setMonth(Rdatetime.getMonth() + 1);
						} else{
							while (curDatetime.getMonth() > Rdatetime.getMonth() || curDatetime.getYear() > Rdatetime.getYear()){
								Rdatetime.setMonth(Rdatetime.getMonth() + 1);
							}
						}
					}
					updateReminder(dateTime,Rdatetime,Rdatetime,note,repeat);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
