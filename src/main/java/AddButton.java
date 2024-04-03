import java.util.List;

/**
 * Button responsible for adding new books.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 4/3/2024
 *
 */
public class AddButton extends EditButton {

	private static final String BUTTON_NAME = "Add";
	
	public AddButton(BookzController controller) {
		super(controller, BUTTON_NAME);
	}

	@Override
	void addListener() {
		button.addActionListener(arg0 -> {
			window = new EditWindow(BOOK_FIELDS, "Add Book", AddButton.this);
			window.setVisible(true);
		});
	}

	/**
	 * Called when user entry is complete.
	 */
	@Override
	void done() {
		List<String> fields = window.getReturnFields();
		controller.addBook(fields);
	}

}
