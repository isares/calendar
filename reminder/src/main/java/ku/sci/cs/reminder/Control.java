package ku.sci.cs.reminder;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public Boolean addReminder(java.util.Date inDate,java.util.Date inTime,String inNote){
		SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dformat.format(inDate);
        
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String time = tformat.format(inTime);
        
        String dateTime = date + " " + time;
        
		try {
			String query = "Insert into reminder (datetime,note) Values (\"" + dateTime + "\",\"" + inNote + "\")";
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
	
	public Boolean updateReminder(String inDateTime_first,java.util.Date inDate,java.util.Date inTime,String inNote){
		SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dformat.format(inDate);
        
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String time = tformat.format(inTime);
        
        String dateTime = date + " " + time;
		
		try {
			String query = "Update reminder Set datetime = \"" + dateTime + "\"" + ", note = \"" + inNote + "\""
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
				reminderArray.add(new Reminder(dateTime,note));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reminderArray;
	}
	
}
