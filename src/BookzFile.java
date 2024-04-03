import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Handles Book Json files.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 4/3/2024
 *
 */
public class BookzFile {
	private File books;
	public static final String DEFAULT_FILENAME = "books.json";
	
	/**
	 * Ensures that the specified filename works properly.
	 * If so, sets this file to that filename.
	 * If not, uses the default filename.
	 * 
	 * @param filename Name of file to attempt to load.
	 * @throws FileNotFoundException If default file cannot be found 
	 * and specified file is invalid.
	 */
	BookzFile(String filename) throws FileNotFoundException {
		books = new File(filename);
		
		String bookName = books.getName();
		
		if (!isValidFile(bookName, ".json")) {
			System.err.println("Invalid file: " + filename + ". Defaulting to " + DEFAULT_FILENAME);
			books = new File(DEFAULT_FILENAME);
			
			if (!books.exists()) {
				throw new FileNotFoundException("Default file " + DEFAULT_FILENAME + " not found!");
			}
		}
	}
	
	BookzFile() {
		books = new File(DEFAULT_FILENAME);
	}
	
	/**
	 * Checks whether a file exists and ends with the specified extension.
	 * 
	 * @param filename Base name of file to check
	 * @param extension File extension to check
	 * @return Whether the extension exists in a valid state
	 */
	static boolean isValidFile(String filename, String extension) {
		boolean validExtension = filename.length() >= extension.length() 
				&& filename.endsWith(extension);
		
		File toCheck = new File(filename);
		return toCheck.exists() && validExtension;
	}
	
	/**
	 * Writes the entire list of books to the file.
	 * 
	 * @param books List of books to write.
	 */
	void writeList(List<Book> books) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
		try {
			writer.writeValue(this.books, books);
		} catch (IOException e) {
			System.err.println("WARNING! Could not write file. File remains at pre-write state.");
			System.err.println(e.toString());
		}
	}
	
	File getFile() {
		return books;
	}
}
