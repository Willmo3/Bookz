import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Modular window for editing books.
 * Should be encapsulated in editing button.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 10/7/2022
 *
 */
public class EditWindow {
	JFrame containerFrame;
	JPanel editPanel;
	private List<JTextField> text;
	private List<String> returnFields;
	private EditButton actionButton;
	
	/**
	 * Prepares an edit window.
	 * Uses the specified title and adds textfields for all desired fields in fields array.
	 * 
	 * @param fields Fields to prompt user for.
	 * @param title Title of the window.
	 */
	public EditWindow(String[] fields, String title, EditButton actionButton) {
		this.actionButton = actionButton;
		
		containerFrame = new JFrame();
		editPanel = new JPanel();
		
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
		text = new ArrayList<JTextField>(); 
		this.returnFields = new ArrayList<String>();
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Label.font", Font.BOLD, 20));
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		editPanel.add(titleLabel);
		
		addFields(fields);
		
		JButton doneButton = finishButton();
		doneButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		editPanel.add(doneButton);
		
		containerFrame.add(editPanel);
		containerFrame.pack();
	}
	
	public void setVisible(boolean isVisible) {
		containerFrame.setLocationRelativeTo(null);
		containerFrame.setVisible(true);
	}
	
	/**
	 * Adds this list of fields to the window,
	 * As well as textfields to get user input.
	 * 
	 * @param fields Array of fields to add.
	 */
	private void addFields(String[] fields) {
		for (String field : fields) {
			JLabel toAdd = new JLabel(field);
			toAdd.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			editPanel.add(toAdd);
			
			JTextField input = new JTextField();
			editPanel.add(input);
			text.add(input);
		}
	}
	
	/**
	 * Gets the "Done" button ready.
	 * Specifically, adds an actionListener which takes text from all current JTextFields
	 * And adds it to returnFields.
	 * 
	 * @return The button.
	 */
	private JButton finishButton() {
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (JTextField textField : text) {
					returnFields.add(textField.getText());
				}
				
				actionButton.done();
				containerFrame.dispose();
			}
		});
		
		return doneButton;
	}
	
	List<String> getReturnFields() {
		return returnFields;
	}
	
	JFrame getFrame() {
		return containerFrame;
	}
}
