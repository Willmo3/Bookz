import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.EmptyBorder

/**
 * Class representing a single encapsulation of a BookzUI. This code complies
 * with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookzUI(tree: BookzTree, controller: BookzController) {
    private var image: JLabel? = null
    private val window: JFrame = JFrame("BOOKZV3")

    /**
     * Prepares core JPanel, corresponding JTree, editing buttons, and image handling.
     */
    init {
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val core = JPanel()
        core.border = EmptyBorder(15, 15, 15, 15)
        core.layout = BorderLayout()

        // Add left scrollbar to JTree.
        prepareJTree(core, tree)
        prepareImage(core)

        val bottomButtons = JPanel()
        val editButton: EditButton = BookEditButton(controller)
        val newBookButton: EditButton = AddButton(controller)

        bottomButtons.add(newBookButton.button, BorderLayout.EAST)
        bottomButtons.add(editButton.button, BorderLayout.WEST)

        core.add(bottomButtons, BorderLayout.SOUTH)

        window.add(core)
        window.pack()
    }

    /**
     * Gets the JTree ready for usage.
     *
     * @param core core JPanel to add the tree to.
     * @param tree JTree, as passed as a parameter to UI.
     */
    private fun prepareJTree(core: JPanel, tree: BookzTree) {
        val scrollTree = JScrollPane(tree.tree)
        // Return scrollbar to left
        scrollTree.preferredSize = Dimension(200, 300)
        core.add(scrollTree, BorderLayout.WEST)
    }

    /**
     * Gets the image ready for usage.
     *
     * @param core Core JFrame to add image to.
     */
    private fun prepareImage(core: JPanel) {
        image = JLabel()
        image!!.verticalAlignment = SwingConstants.TOP
        image!!.verticalTextPosition = SwingConstants.BOTTOM
        image!!.horizontalTextPosition = SwingConstants.CENTER
        image?.let { core.add(it, BorderLayout.CENTER) }
    }

    /**
     * Makes the window visible.
     */
    fun visible() {
        window.isVisible = true
    }

    /**
     * Sets the image in this book to the specified filename.
     *
     * @param filename Where to find the image.
     */
    fun setImage(filename: String) {
        try {
            val testImage = ImageIO.read(File(filename)).getScaledInstance(300, 250, Image.SCALE_DEFAULT)
            image!!.icon = ImageIcon(testImage)
        } catch (_: IOException) {
            println("ImageLink $filename not found.")
            image!!.icon = null
        }


        // Technically, window.pack() does not need to be called here, 
        // since setTitle is called after this and it packs.
        // But in case the calling order should be changed in the future,
        // I have elected to pack in both methods.
        window.pack()
    }

    /**
     * Sets the title of this book to the specified title string.
     *
     * @param newTitle String representation of the new title.
     */
    fun setTitle(newTitle: String?) {
        image!!.text = newTitle
        window.pack()
    }
}
