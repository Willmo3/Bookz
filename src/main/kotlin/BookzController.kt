import java.io.IOException

/**
 * Controller class for BookzV3. Connects UI to underlying model.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookzController internal constructor(filename: String = BookzFile.DEFAULT_FILENAME) {
    private val ui: BookzUI
    private val tree: BookzTree
    private val file = BookzFile(filename)
    private val list = BookzList(file.file)

    /**
     * Called when a book is selected. The point of this is to prevent methods which
     * rely on a book being selected from being called when no book has yet been
     * chosen.
     * (E.g. the edit book method)>
     *
     */
    var isBookSelected: Boolean

    init {
        tree = BookzTree(list, this)
        ui = BookzUI(tree, this)
        isBookSelected = false
    }

    fun readyUI() {
        ui.visible()
    }

    /**
     * Updates an image in the UI. If this is ever being called, then a book has
     * been selected.
     *
     * @param imageLink New string for the image location.
     * @param book Book to update.
     * @throws IOException If image invalid (all cases where this happen should be checked).
     */
    @Throws(IOException::class)
    fun updateImage(imageLink: String, book: Book) {
        ui.setImage(imageLink)
        ui.setTitle(BookzList.formatEntry(book))
    }

    /**
     * Adds a book to the list from a list of attributes about the new book.
     *
     * @param bookAttributes List of attributes.
     */
    fun addBook(bookAttributes: List<String>) {
        addBook(BookzList.parseBook(bookAttributes))
    }

    /**
     * Adds a book to the list.
     *
     * @param book Book to add.
     */
    private fun addBook(book: Book) {
        list.addBook(book)
        file.writeList(list.books)
        tree.addBook(book)
    }

    /**
     * Edit the last book modified
     *
     * @param bookAttributes Attributes of book to modify
     */
    fun editBook(bookAttributes: List<String>) {
        val lastBook = tree.lastSelected!!.book
        val newBook = BookzList.parseToEdit(lastBook, bookAttributes)

        list.updateBook(lastBook, newBook)
        tree.editBook(newBook)
        ui.setTitle(BookzList.formatEntry(newBook))
        ui.setImage(newBook.imageLink)

        file.writeList(list.books)
    }
}
