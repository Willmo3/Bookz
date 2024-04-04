import javax.swing.JButton

/**
 * Button for adding a book to the list.
 *
 * @author William Morris
 * @version 4/3/2024
 */
abstract class EditButton(protected var controller: BookzController, buttonName: String) {
    var button: JButton = JButton(buttonName)
    protected var window: EditWindow? = null

    init {
        addListener()
    }

    /**
     * Adds click listener to the button.
     */
    abstract fun addListener()

    /**
     * Performs operation when done button pressed.
     */
    abstract fun done()

    companion object {
        @JvmStatic
        protected val BOOK_FIELDS: Array<String> =
            arrayOf("Title", "Author", "Country", "Image link", "Language", "Link", "Pages", "Year")
    }
}
