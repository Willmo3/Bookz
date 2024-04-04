import java.io.IOException
import javax.swing.JTree
import javax.swing.event.TreeSelectionEvent
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultTreeSelectionModel

/**
 * Represents a JTree for some books.
 * This code complies with the JMU honor code.
 *
 * @author Will Morris
 * @version 4/3/2024
 */
class BookzTree(list: BookzList, private val controller: BookzController) {
    private val booksPerAuthor = list.booksPerAuthor
    private val authorNodes: MutableList<DefaultMutableTreeNode> = ArrayList()

    private val root: DefaultMutableTreeNode = DefaultMutableTreeNode()
    val tree: JTree
    private val model: DefaultTreeModel

    var lastSelected: BookNode? = null
        private set

    init {
        addNodes(root)
        tree = JTree(root)
        tree.isRootVisible = false
        model = tree.model as DefaultTreeModel

        // Add listeners to tree.
        tree.selectionModel.addTreeSelectionListener { e: TreeSelectionEvent ->
            val node = e.path.lastPathComponent
            if (node is BookNode) {
                lastSelected = node
                controller.isBookSelected = true
                try {
                    controller.updateImage(lastSelected!!.book.imageLink, lastSelected!!.book)
                } catch (e: IOException) {
                    System.err.println(e.toString())
                }
            }
        }
    }

    private fun buildTree() {
        // Initialized before building
        addNodes(root)
        tree.isRootVisible = false

        // Add listeners to tree.
        tree.selectionModel.addTreeSelectionListener { e: TreeSelectionEvent ->
            val node = e.path.lastPathComponent
            if (node is BookNode) {
                lastSelected = node
                controller.isBookSelected = true
                try {
                    controller.updateImage(lastSelected!!.book.imageLink, lastSelected!!.book)
                } catch (e: IOException) {
                    System.err.println(e.toString())
                }
            }
        }
    }

    /**
     * Adds nodes to root node for tree.
     *
     * @param root Root to add new nodes to.
     */
    private fun addNodes(root: DefaultMutableTreeNode) {
        var authNode: DefaultMutableTreeNode

        for (author in booksPerAuthor.keys) {
            authNode = DefaultMutableTreeNode(author)
            root.add(authNode)
            authorNodes.add(authNode)

            for (b in booksPerAuthor[author]!!) {
                authNode.add(BookNode(b))
            }
        }
    }

    /**
     * Adds a new book to the tree.
     * Places it under an existing author.
     *
     * @param newBook Book to add.
     */
    fun addBook(newBook: Book) {
        addBook(BookNode(newBook))
    }

    /**
     * Adds a new book node to the tree.
     * Places it under an existing author.
     *
     * @param newBook BookNode to add.
     */
    private fun addBook(newBook: BookNode) {
        val bookAuthor = newBook.book.author
        var author = findAuthor(bookAuthor)

        if (author == null) {
            author = DefaultMutableTreeNode(bookAuthor)
            model.insertNodeInto(author, model.root as DefaultMutableTreeNode, 0)
        }


        // Note that new nodes are inserted at the top of the author 
        // (and new authors at the top of the list)
        // Intentionally, so that the user may immediately see their changes.
        model.insertNodeInto(newBook, author, 0)
        model.nodeChanged(author)
    }

    /**
     * Finds the node containing the specified author name.
     *
     * @param author Author to search for.
     * @return Node containing the author if found, null if not.
     */
    private fun findAuthor(author: String): DefaultMutableTreeNode? {
        for (candidate in authorNodes) {
            if (candidate.userObject == author) {
                return candidate
            }
        }

        return null
    }

    /**
     * Edits the last BookNode selected.
     * Specifically, removes the last bookNode and adds a new book node with the edited properties.
     * This makes the user manually select the node each time,
     * Reducing the amount of accidental edits.
     *
     * @param newBook new book for the last booknode
     */
    fun editBook(newBook: Book) {
        val bookNode = BookNode(newBook)
        val parent = lastSelected!!.parent as DefaultMutableTreeNode
        model.removeNodeFromParent(lastSelected)
        addBook(bookNode)


        // If the editing of the book resulted in the parent losing its last child,
        // Removes the author from the list.
        if (parent.childCount == 0) {
            model.removeNodeFromParent(parent)
        }

        lastSelected = bookNode
    }
}
