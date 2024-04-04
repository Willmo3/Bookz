import javax.swing.tree.DefaultMutableTreeNode

/**
 * BookNode. Like a tree node, but it also has a book.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 10/7/2022
 */
class BookNode(val book: Book) : DefaultMutableTreeNode(BookzList.formatEntry(book)) {

    companion object {
        private const val serialVersionUID = 1L
    }
}