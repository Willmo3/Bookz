import java.awt.Font
import javax.swing.*

/**
 * Modular window for editing books.
 * Should be encapsulated in editing button.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2022
 */
class EditWindow(fields: Array<String>, title: String, private val actionButton: EditButton) {
    private var containerFrame: JFrame = JFrame()
    private var editPanel: JPanel = JPanel()
    private val text: MutableList<JTextField>
    val returnFields: MutableList<String>

    /**
     * Prepares an edit window.
     * Uses the specified title and adds textfields for all desired fields in fields array.
     *
     */
    init {
        editPanel.layout = BoxLayout(editPanel, BoxLayout.Y_AXIS)
        text = ArrayList()
        this.returnFields = ArrayList()

        val titleLabel = JLabel(title)
        titleLabel.font = Font("Label.font", Font.BOLD, 20)
        titleLabel.alignmentX = JLabel.CENTER_ALIGNMENT
        editPanel.add(titleLabel)

        addFields(fields)

        val doneButton = finishButton()
        doneButton.alignmentX = JLabel.CENTER_ALIGNMENT
        editPanel.add(doneButton)

        containerFrame.add(editPanel)
        containerFrame.pack()
    }

    fun setVisible(isVisible: Boolean) {
        containerFrame.setLocationRelativeTo(null)
        containerFrame.isVisible = isVisible
    }

    /**
     * Adds this list of fields to the window,
     * As well as textfields to get user input.
     *
     * @param fields Array of fields to add.
     */
    private fun addFields(fields: Array<String>) {
        for (field in fields) {
            val toAdd = JLabel(field)
            toAdd.alignmentX = JLabel.CENTER_ALIGNMENT
            editPanel.add(toAdd)

            val input = JTextField()
            editPanel.add(input)
            text.add(input)
        }
    }

    /**
     * Gets the "Done" button ready.
     * Specifically, adds an actionListener which takes text from all current JTextFields
     * And adds it to returnFields.
     *
     * @return The button.
     */
    private fun finishButton(): JButton {
        val doneButton = JButton("Done")
        doneButton.addActionListener {
            for (textField in text) {
                returnFields.add(textField.text)
            }
            actionButton.done()
            containerFrame.dispose()
        }

        return doneButton
    }
}
