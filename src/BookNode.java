import javax.swing.tree.DefaultMutableTreeNode;

/**
 * BookNode. Like a tree node, but it also has a book.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 10/7/2022
 *
 */
public class BookNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	private Book book;
	
	public BookNode(Book book) {
		super(BookzList.formatEntry(book));
		this.book = book;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}

}
