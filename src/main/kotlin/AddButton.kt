
/**
 * Button responsible for adding new books.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class AddButton(controller: BookzController) : EditButton(controller, BUTTON_NAME) {
    override fun addListener() {
        button.addActionListener {
            window = EditWindow(BOOK_FIELDS, "Add Book", this@AddButton)
            window?.setVisible(true)
        }
    }

    /**
     * Called when user entry is complete.
     */
    override fun done() {
        val fields = window!!.returnFields
        controller.addBook(fields)
    }

    companion object {
        private const val BUTTON_NAME = "Add"
    }
}
