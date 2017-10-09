//Isares Srirattanapaisarn 5710404748

package ku.sci.cs.reminder;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXDatePicker;

public class View {
	
	JFrame frame;
	private JTable showReminder;
	private JXDatePicker datePicker;
	private JTextArea textNote;
	private JComboBox repeatCB;
	private Control control;
	
	public View() {
		control = new Control();
		control.connectSQLite();
		control.repeatedCheck();
		frame = new JFrame();
		frame.setBounds(100, 100, 501, 396);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        control.closeConnectSQLite();
		        System.out.println("Connection close");
		    }
		});
		frame.setTitle("Reminder");
		home();
	}
	
	private void home() {
		
		//table
		String[] column = {"Date and Time","Note","Repeat"};
		final DefaultTableModel tableModel = new DefaultTableModel(column, 0);
		showReminder = new JTable(tableModel);
		TableColumnModel tcm = showReminder.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		showReminder.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		showReminder.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tcm.getColumn(0).setPreferredWidth(120);	//datetime
		tcm.getColumn(1).setPreferredWidth(250);	//note
		tcm.getColumn(2).setPreferredWidth(70);		//repeat
		JScrollPane scrollPane = new JScrollPane(showReminder);
		
		ArrayList<Reminder> reminder = control.getReminder();
		for (Reminder data : reminder){
			String[] row = {data.getDateTime(),data.getNote(),data.getRepeat()};
			tableModel.addRow(row);
		}
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				delete_edit("delete");
				frame.getContentPane().validate();
			}
		});
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				delete_edit("edit");
				frame.getContentPane().validate();
			}
		});
		
		//button add
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				add_edit("add",null,"","None");
				frame.getContentPane().validate();
			}
		});
		
		JLabel lblInfo = new JLabel("All Reminder");
		
		ImageIcon searchIcon = new ImageIcon("icon/search-icon.png", "search icon");
		Image img = searchIcon.getImage(); 
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics(); 
		g.drawImage(img, 0, 0, 20, 20, null); 
		searchIcon = new ImageIcon(bi);
		JLabel lblSearch = new JLabel(searchIcon);
		
		final JXDatePicker searchDatePicker = new JXDatePicker();
		searchDatePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		searchDatePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
					tableModel.removeRow(i);
			    }
				
				Date date = searchDatePicker.getDate();
				ArrayList<Reminder> search = control.searchReminder(date);
				for (Reminder reminder : search){
					String[] row = {reminder.getDateTime(),reminder.getNote(),reminder.getRepeat()};
					tableModel.addRow(row);
				}
			}
		});
		
		//set view
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblInfo)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblSearch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(46)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInfo)
						.addComponent(searchDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSearch))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDelete)
						.addComponent(btnEdit))
					.addGap(21))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void delete_edit(final String option) {
		
		//table
		showReminder = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel() {
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		showReminder.setModel(tableModel);
		
		tableModel.addColumn("Select");
		tableModel.addColumn("Date and Time");
		tableModel.addColumn("Note");
		tableModel.addColumn("Repeat");

		TableColumnModel tcm = showReminder.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		showReminder.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		showReminder.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tcm.getColumn(1).setPreferredWidth(150);	//datetime
		tcm.getColumn(2).setPreferredWidth(205);	//note
		tcm.getColumn(3).setPreferredWidth(80);		//repeat
		JScrollPane scrollPane = new JScrollPane(showReminder);
		
		//add table data
		ArrayList<Reminder> reminder = control.getReminder();
		for (Reminder data : reminder){
			Object[] add = {false,data.getDateTime(),data.getNote(),data.getRepeat()};
			tableModel.addRow(add);
		}
		
		if (option == "edit"){
			showReminder.addMouseListener(new MouseAdapter() {
			
				public void mouseClicked(MouseEvent e) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					if (column == 0){
					    for (int i = 0; i < showReminder.getRowCount(); i++){
					    	if (i != row){
					    		showReminder.setValueAt(false, i, 0);
					    	}
					    }
					}
				}
			});
		}
		
		JButton btnSubmit = new JButton();
		if (option == "delete"){
			btnSubmit.setText("Delete");
		} else if (option == "edit"){
			btnSubmit.setText("Edit");
		}
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (option == "delete"){
					ArrayList<String> chose = new ArrayList<String>();
					for (int i = 0; i < showReminder.getRowCount(); i++) {
						Boolean checked = Boolean.valueOf(showReminder.getValueAt(i, 0).toString());
						if (checked == true) {
							String dateTime = showReminder.getValueAt(i, 1).toString();
							chose.add(dateTime);
						}
					}
				
				
					Object[] options = {"Yes","No"};
					int dialogResult  = JOptionPane.showOptionDialog(frame,
									"Are you sure want to delete?",
									"Delete Data",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE,
									null,     //do not use a custom Icon
									options,  //the titles of buttons
									options[0]); //default button title
					if(dialogResult == JOptionPane.YES_OPTION){
						control.deleteReminder(chose);
						frame.getContentPane().removeAll();
						home();
						frame.getContentPane().validate();
					}
				} else if (option == "edit"){
					for (int i = 0; i < showReminder.getRowCount(); i++) {
						Boolean checked = Boolean.valueOf(showReminder.getValueAt(i, 0).toString());
						if (checked == true) {
							String dateTime = showReminder.getValueAt(i,1).toString();
							String note = showReminder.getValueAt(i,2).toString();
							String repeat = showReminder.getValueAt(i,3).toString();
							
							frame.getContentPane().removeAll();
							add_edit("edit",dateTime,note,repeat);
							frame.getContentPane().validate();
						}
					}
				}
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
		
		JLabel lblInfo = new JLabel(" ");
		
		if (option == "delete"){
			lblInfo.setText("Choose note to delete");
		} else if (option == "edit"){
			lblInfo.setText("Choose note to edit (Can edit only 1 note per time)");
		}
		
		//set view
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInfo)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblInfo)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnSubmit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnCancel))
						.addGap(21))
			);
		frame.getContentPane().setLayout(groupLayout);
	}

	private void add_edit(final String option,final String dateTime_org,String note_org,String repeat_org) {
		
		datePicker = new JXDatePicker();
		
		Date date = new Date();
		if (option == "add"){
			datePicker.setDate(date);
		} else if (option == "edit"){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				date = sdf.parse(dateTime_org);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
			datePicker.setDate(date);
		}
        datePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        
        textNote = new JTextArea(note_org);
        
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		final JSpinner spinner = new javax.swing.JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
		spinner.setEditor(de);
		
		JLabel lblRepeat = new JLabel("Repeat");
		
		String[] repeatList = {"None","Daily","Weekly","Monthly"};
		repeatCB = new JComboBox(repeatList);
		for (int i = 0 ; i < repeatList.length ; i++){
			if (repeatList[i].equals(repeat_org)){
				repeatCB.setSelectedIndex(i);
			}
		}
		
		JButton btnSubmit = new JButton();
		if (option == "add"){
			btnSubmit.setText("Add");
		} else if (option == "edit"){
			btnSubmit.setText("Edit");
		}
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Date date = datePicker.getDate();
		        String note = textNote.getText();
		        Date time = (Date) spinner.getValue();
		        String repeat = (String) repeatCB.getSelectedItem();
		        if (option == "add"){
		        	Boolean check = control.addReminder(date,time,note,repeat);
		        	if (check == false){
			        	JOptionPane t = new JOptionPane();
			        	t.showMessageDialog(frame,
			        		    "This date and time is already have in reminder.",
			        		    "Add Data error",
			        		    t.ERROR_MESSAGE);
			        } else{
			        	//back to home
			        	frame.getContentPane().removeAll();
			        	home();
			        	frame.getContentPane().validate();
			        }
		        } else if (option == "edit"){
		        	Boolean check = control.updateReminder(dateTime_org,date,time,note,repeat);
		        	if (check == false){
		        		JOptionPane t = new JOptionPane();
			        	t.showMessageDialog(frame,
			        		    "This date and time is already have in reminder.",
			        		    "Edit Data error",
			        		    t.ERROR_MESSAGE);
		        	} else{
			        	//back to home
			        	frame.getContentPane().removeAll();
			        	home();
			        	frame.getContentPane().validate();
			        }
		        }
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
		
		JLabel lblTime = new JLabel("Time");
		
		JLabel lblNote = new JLabel("Note");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(datePicker, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblDate)
						.addComponent(lblTime)
						.addComponent(lblRepeat)
						.addComponent(repeatCB, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(spinner))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNote)
						.addComponent(textNote, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNote))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textNote, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblRepeat)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(repeatCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(100)))
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnSubmit))
					.addGap(21))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
