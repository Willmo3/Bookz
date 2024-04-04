/**
 * Button for editing the title of a book to the list.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookEditButton(controller: BookzController) : EditButton(controller, BUTTON_NAME) {
    override fun addListener() {
        button.addActionListener {
            if (!controller.isBookSelected) {
                WarningFrame("Alert!", "Select book first").setVisible(true)
            } else {
                window = EditWindow(BOOK_FIELDS, MENU_TITLE, this@BookEditButton)
                window!!.setVisible(true)
            }
        }
    }

    override fun done() {
        try {
            controller.editBook(window!!.returnFields)
        } catch (e: Exception) {
            System.err.println(e.stackTrace.contentToString())
            // This should never be reached.
        }
    }

    companion object {
        private const val BUTTON_NAME = "Edit"
        private const val MENU_TITLE = "Edit Book"
    }
}
