import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Represents a JTree for some books.
 * This code complies with the JMU honor code.
 * 
 * @author Will Morris
 * @version 4/3/2024
 *
 */
public class BookzTree {

	private final Map<String, ArrayList<Book>> booksPerAuthor;
	private final List<DefaultMutableTreeNode> authorNodes;
	private final BookzController controller;
	private JTree tree;
	private final DefaultTreeModel model;
	private BookNode lastSelected;

	/**
	 * Tree constructor.
	 * 
	 * @param list           List of all books available
	 * @param controller	 Controller for this UI tree
	 */
	public BookzTree(BookzList list, BookzController controller) {
		this.controller = controller;
		this.booksPerAuthor = list.getBooksPerAuthor();
		this.authorNodes = new ArrayList<>();
		buildTree();
		model = (DefaultTreeModel) tree.getModel();
	}

	private void buildTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		addNodes(root);

		tree = new JTree(root);
		tree.setRootVisible(false);
		
		// Add listeners to tree.
		tree.getSelectionModel().addTreeSelectionListener(e -> {
			Object node = e.getPath().getLastPathComponent();

			if (node instanceof BookNode) {
				lastSelected = (BookNode) node;
				controller.setBookSelected(true);
				try {
					controller.updateImage(lastSelected.getBook().getImageLink(), lastSelected.getBook());
				} catch (IOException E) {
					System.err.println(E.toString());
				}
			}
		});
	}
	
	/**
	 * Adds nodes to root node for tree.
	 * 
	 * @param root Root to add new nodes to.
	 */
	private void addNodes(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode authNode;

		for (String author : booksPerAuthor.keySet()) {
			authNode = new DefaultMutableTreeNode(author);
			root.add(authNode);
			authorNodes.add(authNode);

			for (Book b : booksPerAuthor.get(author)) {
				authNode.add(new BookNode(b));
			}
		}
	}
	
	/**
	 * Adds a new book to the tree.
	 * Places it under an existing author.
	 * 
	 * @param newBook Book to add.
	 */
	void addBook(Book newBook) {
		addBook(new BookNode(newBook));
	}
	
	/**
	 * Adds a new book node to the tree.
	 * Places it under an existing author. 
	 * 
	 * @param newBook BookNode to add.
	 */
	private void addBook(BookNode newBook) {
		String bookAuthor = newBook.getBook().getAuthor();
		DefaultMutableTreeNode author = findAuthor(bookAuthor);
		
		if (author == null) {
			author = new DefaultMutableTreeNode(bookAuthor);
			model.insertNodeInto(author, (DefaultMutableTreeNode) model.getRoot(), 0);
		}
		
		// Note that new nodes are inserted at the top of the author 
		// (and new authors at the top of the list)
		// Intentionally, so that the user may immediately see their changes.
		model.insertNodeInto(newBook, author, 0);
		model.nodeChanged(author);
	}
	
	/**
	 * Finds the node containing the specified author name.
	 * 
	 * @param author Author to search for.
	 * @return Node containing the author if found, null if not.
	 */
	private DefaultMutableTreeNode findAuthor(String author) {
		for (DefaultMutableTreeNode candidate : authorNodes) {
			if (candidate.getUserObject().equals(author)) {
				return candidate;
			}
		}
		
		return null;
	}
	/**
	 * Edits the last BookNode selected.
	 * Specifically, removes the last bookNode and adds a new book node with the edited properties.
	 * This makes the user manually select the node each time,
	 * Reducing the amount of accidental edits.
	 * 
	 * @param newBook new book for the last booknode
	 */
	void editBook(Book newBook) {
		BookNode bookNode = new BookNode(newBook);
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) lastSelected.getParent();
		model.removeNodeFromParent(lastSelected);
		addBook(bookNode);
		
		// If the editing of the book resulted in the parent losing its last child,
		// Removes the author from the list.
		if (parent.getChildCount() == 0) {
			model.removeNodeFromParent(parent);
		}
		
		lastSelected = bookNode;
	}
	
	/**
	 * Gets the last TreeNode that someone clicked on in this tree.
	 * 
	 * @return the last selected node.
	 */
	BookNode getLastSelected() {
		return lastSelected;
	}

	/**
	 * Gets the JTree for the books associated with this BookzTree instance.
	 * 
	 * @return The JTree
	 */
	JTree getTree() {
		return tree;
	}
}
