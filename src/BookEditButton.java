import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button for editing the title of a book to the list.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 10/7/2022
 */
public class BookEditButton extends EditButton {
	private static final String BUTTON_NAME = "Edit";
	private static final String MENU_TITLE = "Edit Book";
	
	public BookEditButton(BookzController controller) {
		super(controller, BUTTON_NAME);
	}

	@Override
	void addListener() {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!controller.isBookSelected()) {
					new WarningFrame("Alert!", "Select book first").setVisible(true);
				} else {
					window = new EditWindow(BOOK_FIELDS, MENU_TITLE, BookEditButton.this);
					window.setVisible(true);
				}
			}
		});
		
	}

	@Override
	void done() {
		try {
			controller.editBook(window.getReturnFields());
		} catch (Exception E) {
			System.err.println(E.getStackTrace());
			// This should never be reached.
		}
	}
}
