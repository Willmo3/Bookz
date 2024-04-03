import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Processes a list of books in various ways.
 * This code complies with the JMU honor code.
 * 
 * @author William Morris
 * @version 10/7/2022
 *
 */
public class BookzList {
	private final Map<String, ArrayList<Book>> booksPerAuthor;
	private final List<Book> books;
	private static final int DEFAULT_INT = 0;
	private static final String DEFAULT_STRING = "Unknown";
	private static final String EMPTY_ERROR = "Value for field %s not specified. Defaulting to %s.\n";
	
	private static final int TITLE_INDEX = 0;
	private static final int AUTHOR_INDEX = 1;
	private static final int COUNTRY_INDEX = 2;
	private static final int IMGLINK_INDEX = 3;
	private static final int LANG_INDEX = 4;
	private static final int LINK_INDEX = 5;
	private static final int PAGE_INDEX = 6;
	private static final int YEAR_INDEX = 7;
	
	/**
	 * File processing constructor. Takes a filename and gets the books list from that.
	 * 
	 * @param booksJsonFile Name of file to process.
	 * @throws IOException If provided file cannot be read properly.
	 */
	BookzList(File booksJsonFile) throws IOException {
		this (BookListReader.readBookList(new FileInputStream(booksJsonFile)));
	}
	
	/**
	 * Existing list constructor. Takes a list of books and gets around to getting the author.
	 * 
	 * @param books Book list to get.
	 */
	BookzList(List<Book> books) {
		this.books = books;
		this.booksPerAuthor = processBooksByAuthor(books);
	}
	
	
	/**
	 * Maps each author to the books they've made.
	 * 
	 * @param books List of all books being considered.
	 * @return A map of all books per author.
	 */
	private Map<String, ArrayList<Book>> processBooksByAuthor(List<Book> books) {
		Map<String, ArrayList<Book>> booksPerAuthor = new TreeMap<>();

		String author;
		for (Book book : books) {
			author = book.getAuthor();

			booksPerAuthor.putIfAbsent(author, new ArrayList<>());
			booksPerAuthor.get(author).add(book);
		}

		return booksPerAuthor;
	}

	/**
	 * Formats a book entry's title in the UI. Format: Title (year)
	 * 
	 * @param book Book to format
	 * @return String representation of the formatted title.
	 */
	public static String formatEntry(Book book) {
		StringBuilder formattedBook = new StringBuilder();
		formattedBook.append(book.getTitle());
		formattedBook.append(" by ");
		formattedBook.append(book.getAuthor());
		formattedBook.append(" (");
		if (book.getYear() < 0) {
			formattedBook.append(Math.abs(book.getYear()));
			formattedBook.append(" BCE");
		} else {
			formattedBook.append(book.getYear());
		}
		formattedBook.append(")");
		
		return formattedBook.toString();
	}
	
	/*
	 * Updates one book node to contain a new book.
	 * 
	 * @param book Book to update
	 * @param newTitle Thing to set the title of the book to.
	 */
	void updateBook(Book book, Book newBook) {
		boolean isFound = false;
		
		for (int i = 0; i < books.size() && !isFound; i++) {
			Book current = books.get(i);
			if (current == book) {
				books.remove(current);
				books.add(newBook);
				isFound = true;
			}
		}
	}
	
	/**
	 * Adds a book to the list of books.
	 * 
	 * @param book Book to add.
	 */
	void addBook(Book book) {
		books.add(book);
	}
	
	/**
	 * Parses a book from a set of bookAttributes, using values from another book as default.
	 * Functionally, this allows a user to edit specific attributes of a book.
	 * NOTE: this method assumes that the UI will prompt for all 7 values.
	 * 
	 * @param original Original book being edited; default values come from here.
	 * @param bookAttributes List of new attributes for the book.
	 * @return the new book.
	 */
	static Book parseToEdit(Book original, List<String> bookAttributes) {
		String title = parseField(bookAttributes.get(TITLE_INDEX), "title", original.getTitle());
		String author = parseField(bookAttributes.get(AUTHOR_INDEX), "author", original.getAuthor());
		String country = parseField(bookAttributes.get(COUNTRY_INDEX), "country", original.getCountry());
		String imageLink = parseImageLink(bookAttributes.get(IMGLINK_INDEX), original.getImageLink());
		String language = parseField(bookAttributes.get(LANG_INDEX), "language", original.getLanguage());
		String link = parseField(bookAttributes.get(LINK_INDEX), "link", original.getLink());
		int pages = parseInt(bookAttributes.get(PAGE_INDEX), "pages", original.getPages());
		int year = parseInt(bookAttributes.get(YEAR_INDEX), "year", original.getYear());
		
		return new Book(title, author, country, imageLink, language, link, pages, year);
	}
	
	/**
	 * Special case for parsing an image link when editing a file.
	 * This allows the imageLink to be kept at original value when editing a book.
	 * 
	 * @param imageLink Link to the new book's image.
	 * @param defaultStr default imageLink.
	 * @return The default imageLink if provided is invalid, provided if not.
	 */
	static String parseImageLink(String imageLink, String defaultStr) {
		String retVal = imageLink;
		
		if (BookzFile.invalidFile(imageLink, ".jpg")) {
			System.out.println("Invalid image (does it exist?): " + imageLink + " defaulting to: " + defaultStr);
			retVal = defaultStr;
		}
		
		return retVal;
	}

	/**
	 * Parses a book from a list of attributes.
	 * Unlike parseToEdit, this method sets all defaults to global defaults.
	 * 
	 * @param bookAttributes List of attributes to make a book from.
	 * @return The parsed book.
	 */
	static Book parseBook(List<String> bookAttributes) {
		String title = parseField(bookAttributes.get(TITLE_INDEX), "title", DEFAULT_STRING);
		String author = parseField(bookAttributes.get(AUTHOR_INDEX), "author", DEFAULT_STRING);
		String country = parseField(bookAttributes.get(COUNTRY_INDEX), "country", DEFAULT_STRING);
		String imageLink = parseField(bookAttributes.get(IMGLINK_INDEX), "image link", DEFAULT_STRING);
		String language = parseField(bookAttributes.get(LANG_INDEX), "language", DEFAULT_STRING);
		String link = parseField(bookAttributes.get(LINK_INDEX), "link", DEFAULT_STRING);
		int pages = parseInt(bookAttributes.get(PAGE_INDEX), "pages", DEFAULT_INT);
		int year = parseInt(bookAttributes.get(YEAR_INDEX), "year", DEFAULT_INT);
		
		return new Book(title, author, country, imageLink, language, link, pages, year);
	}
	
	/**
	 * Formats a field for a book.
	 * Returns the field in original form, unless its empty -- then it
	 * returns DEFAULT_STRING.
	 * 
	 * @param toParse String to parse.
	 * @param description Description of the field (in case there are problems).
	 * @return the final string.
	 */
	private static String parseField(String toParse, String description, String defaultStr) {
		String retVal = toParse;
		
		if (toParse.isEmpty()) {
			System.out.printf(EMPTY_ERROR, description, defaultStr);
			retVal = defaultStr;
		}
		
		return retVal;
	}
	
	/**
	 * Gets an int from a string -- or sets to default, if it isn't an int.
	 * 
	 * @param toParse String to get int from.
	 * @param description Description of the field (in case there are problems).
	 * @return the int.
	 */
	private static int parseInt(String toParse, String description, int defaultInt) {
		int retVal;
		try {
			retVal = Integer.parseInt(toParse);
		} catch (Exception e) {
			if (toParse.isEmpty()) {
				System.out.printf(EMPTY_ERROR, description, defaultInt);
			} else {
				System.out.println("Value " + toParse + " for field " + description + " not an integer. Defaulting to " + defaultInt + ".");
			}
			retVal = defaultInt;
		}
		
		return retVal;
	}
	
	
	Map<String, ArrayList<Book>> getBooksPerAuthor() {
		return booksPerAuthor;
	}


	List<Book> getBooks() {
		return books;
	}
}
