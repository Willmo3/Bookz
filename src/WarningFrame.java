import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Class representing a warning message, to be displayed when input invalid.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 10/6/2022
 *
 */
public class WarningFrame {
	private JDialog warning;
	
	WarningFrame(String title, String message) {
		warning = new JDialog();
		warning.setLayout(new BoxLayout(warning.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		warning.add(titleLabel);
		
		JLabel messageLabel = new JLabel(message);
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		warning.add(messageLabel);
		
		warning.pack();
		warning.setLocationRelativeTo(null);
	}
	
	void setVisible(boolean isVisible) {
		warning.setVisible(isVisible);
	}
}
