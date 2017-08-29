package ku.sci.cs.reminder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXDatePicker;

public class View {
	
	JFrame frame;
	private JTable showReminder;
	private JXDatePicker datePicker;
	private JTextArea textNote;
	private Control control;
	
	public View() {
		control = new Control();
		frame = new JFrame();
		frame.setBounds(100, 100, 501, 396);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reminder");
		home();
	}
	
	private void home() {
		
		//table
		String[] column = {"Date","Note"};
		DefaultTableModel tableModel = new DefaultTableModel(column, 0);
		showReminder = new JTable(tableModel);
		TableColumnModel tcm = showReminder.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		showReminder.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tcm.getColumn(1).setPreferredWidth(300);	//Note
		JScrollPane scrollPane = new JScrollPane(showReminder);
		
		ArrayList<Reminder> reminder = control.getReminder();
		for (Reminder data : reminder){
			Date date = data.getDate();
			SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy");
	        String strDate = dformat.format(date);
			String[] row = {strDate,data.getNote()};
			tableModel.addRow(row);
		}
		
		//button add
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				add();
				frame.getContentPane().validate();
			}
		});
		
		//set view
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(32, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(193)
					.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
					.addGap(198))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(21))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void add() {
		
		datePicker = new JXDatePicker();
		datePicker.setDate(Calendar.getInstance().getTime());
        datePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        
        textNote = new JTextArea();
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				java.util.Date date = datePicker.getDate();
		        String note = textNote.getText();
		        control.addReminder(date, note);
		        
		        //back to home
		        frame.getContentPane().removeAll();
				home();
				frame.getContentPane().validate();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				home();
				frame.getContentPane().validate();
			}
		});
		
		JLabel lblDate = new JLabel("Date");
		
		JLabel lblNote = new JLabel("Note");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDate))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNote)
						.addComponent(textNote, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(83)
					.addComponent(btnCancel)
					.addPreferredGap(ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
					.addComponent(btnSubmit)
					.addGap(85))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNote))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textNote, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCancel)
								.addComponent(btnSubmit))
							.addGap(21))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(284, Short.MAX_VALUE))))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
