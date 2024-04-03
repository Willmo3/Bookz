import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Class representing a single encapsulation of a BookzUI. This code complies
 * with the JMU honor code.
 * 
 * @author William Morris
 * @version 4/3/2024
 *
 */
public class BookzUI {
	
	private JLabel image;
	private final JFrame window;

	private static final String VERSION_TITLE = "BookzV3";

	/**
	 * Prepares core JPanel, corresponding JTree, editing buttons, and image handling.
	 * 
	 * @param tree BookzTree to take tree from.
	 * @param controller Controller to pass on to sub elements of UI.
	 * @throws IOException If issues with preparing JTree (should never happen).
	 */
	public BookzUI(BookzTree tree, BookzController controller) throws IOException {
		window = new JFrame(VERSION_TITLE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel core = new JPanel();
		core.setBorder(new EmptyBorder(15, 15, 15, 15));
		core.setLayout(new BorderLayout());

		// Add left scrollbar to JTree.
		prepareJTree(core, tree);
		prepareImage(core);
		
		JPanel bottomButtons = new JPanel();
		EditButton editButton = new BookEditButton(controller);
		EditButton newBookButton = new AddButton(controller);
		
		bottomButtons.add(newBookButton.button, BorderLayout.EAST);
		bottomButtons.add(editButton.button, BorderLayout.WEST);
		
		core.add(bottomButtons, BorderLayout.SOUTH);
		
		window.add(core);
		window.pack();
	}
	
	/**
	 * Gets the JTree ready for usage.
	 * 
	 * @param core core JPanel to add the tree to.
	 * @param tree JTree, as passed as a parameter to UI.
	 */
	private void prepareJTree(JPanel core, BookzTree tree) {
		JScrollPane scrollTree = new JScrollPane(tree.getTree());
		scrollTree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollTree.setPreferredSize(new Dimension(200, 300));
		core.add(scrollTree, BorderLayout.WEST);
	}
	
	/**
	 * Gets the image ready for usage.
	 * 
	 * @param core Core JFrame to add image to.
	 */
	private void prepareImage(JPanel core) {
		image = new JLabel();
		image.setVerticalAlignment(SwingConstants.TOP);
		image.setVerticalTextPosition(SwingConstants.BOTTOM);
		image.setHorizontalTextPosition(SwingConstants.CENTER);
		core.add(image, BorderLayout.CENTER);
	}
	
	/**
	 * Makes the window visible.
	 */
	public void visible() {
		window.setVisible(true);
	}

	/**
	 * Sets the image in this book to the specified filename.
	 * 
	 * @param filename Where to find the image.
	 */
	void setImage(String filename) {
		try {
			Image testImage = ImageIO.read(new File(filename)).getScaledInstance(300, 250, Image.SCALE_DEFAULT);
			image.setIcon(new ImageIcon(testImage));
		} catch (IOException E) {
			System.out.println("ImageLink " + filename + " not found.");
			image.setIcon(null);
		}
		
		// Technically, window.pack() does not need to be called here, 
		// since setTitle is called after this and it packs.
		// But in case the calling order should be changed in the future,
		// I have elected to pack in both methods.
		window.pack();
	}

	/**
	 * Sets the title of this book to the specified title string.
	 * 
	 * @param newTitle String representation of the new title.
	 */
	void setTitle(String newTitle) {
		image.setText(newTitle);
		window.pack();
	}
}
