import javax.swing.JButton;

/**
 * Button for adding a book to the list.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 4/3/2024
 */
public abstract class EditButton {
	protected JButton button;
	protected EditWindow window;
	protected BookzController controller;
	
	protected static final String[] BOOK_FIELDS = 
		{"Title", "Author", "Country", "Image link", "Language", "Link", "Pages", "Year"};
	
	public EditButton(BookzController controller, String buttonName) {
		this.controller = controller;
		this.button = new JButton(buttonName);
		addListener();
	}
	
	/**
	 * Adds click listener to the button.
	 */
	abstract void addListener();
	
	/**
	 * Performs operation when done button pressed.
	 */
	abstract void done();
}
