import java.awt.Component
import javax.swing.BoxLayout
import javax.swing.JDialog
import javax.swing.JLabel

/**
 * Class representing a warning message, to be displayed when input invalid.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class WarningFrame internal constructor(title: String?, message: String?) {
    private val warning = JDialog()

    init {
        warning.layout = BoxLayout(warning.contentPane, BoxLayout.Y_AXIS)

        val titleLabel = JLabel(title)
        titleLabel.alignmentX = Component.CENTER_ALIGNMENT
        warning.add(titleLabel)

        val messageLabel = JLabel(message)
        messageLabel.alignmentX = Component.CENTER_ALIGNMENT
        warning.add(messageLabel)

        warning.pack()
        warning.setLocationRelativeTo(null)
    }

    fun setVisible(isVisible: Boolean) {
        warning.isVisible = isVisible
    }
}
