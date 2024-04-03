import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for BookzV3. Connects UI to underlying model.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 10/7/2022
 *
 */
public class BookzController {

	private BookzUI ui;
	private BookzTree tree;
	private BookzFile file;
	private BookzList list;
	private boolean bookSelected;
	
	BookzController() throws FileNotFoundException, IOException {
		this(BookzFile.DEFAULT_FILENAME);
	}

	BookzController(String filename) throws FileNotFoundException, IOException {
		file = new BookzFile(filename);
		list = new BookzList(file.getFile());
		tree = new BookzTree(list, this);
		ui = new BookzUI(tree, this);
		bookSelected = false;
	}

	void readyUI() {
		ui.visible();
	}

	/**
	 * Called when a book is selected. The point of this is to prevent methods which
	 * rely on a book being selected from being called when no book has yet been
	 * chosen.
	 * (E.g. the edit book method)>
	 * 
	 * @param selection New value of bookSelection.
	 */
	void setBookSelected(boolean selection) {
		bookSelected = selection;
	}

	boolean isBookSelected() {
		return bookSelected;
	}

	/**
	 * Updates an image in the UI. If this is ever being called, then a book has
	 * been selected.
	 * 
	 * @param imageLink New string for the image location.
	 * @param book Book to update.
	 * @throws IOException If image invalid (all cases where this happen should be checked).
	 */
	void updateImage(String imageLink, Book book) throws IOException {
		ui.setImage(imageLink);
		ui.setTitle(BookzList.formatEntry(book));
	}
	
	/**
	 * Adds a book to the list from a list of attributes about the new book.
	 * 
	 * @param bookAttributes List of attributes.
	 */
	void addBook(List<String> bookAttributes) {
		addBook(BookzList.parseBook(bookAttributes));
	}
	
	/**
	 * Adds a book to the list.
	 * 
	 * @param book Book to add.
	 */
	void addBook(Book book) {
		list.addBook(book);
		file.writeList(list.getBooks());
		tree.addBook(book);
	}

	/**
	 * Updates the title of the last book the tree selected.
	 * 
	 * @param newTitle What to set the title to.
	 * @throws IOException 
	 */
	void editBook(List<String> bookAttributes) throws IOException {
		Book lastBook = tree.getLastSelected().getBook();
		Book newBook = BookzList.parseToEdit(lastBook, bookAttributes);

		list.updateBook(lastBook, newBook);
		tree.editBook(newBook);
		ui.setTitle(BookzList.formatEntry(newBook));
		ui.setImage(newBook.getImageLink());
		
		file.writeList(list.getBooks());
	}
}
