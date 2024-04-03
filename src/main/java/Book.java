/**
 * Book class. Encapsulates data regarding books, including date, author, etc.
 * This work complies with the JMU honor code.
 *
 * @author William Morris
 * @version 9/4/2022
 */
public class Book {
	private final String title;
	private final String author;
	private final String country;
	private final String imageLink;
	private final String language;
	private final String link;
	private final int pages;
	private final int year;

	/**
	 * @param title 	title of the book.
	 * @param author    book's author
	 * @param country   country of origin
	 * @param imageLink link to book's title image
	 * @param language  language book was written in
	 * @param link      link to book's page
	 * @param pages     number of pages in book
	 * @param year      year of origin; negative number indicates BCE.
	 */
	public Book(String title, String author, String country, String imageLink, String language, String link, int pages,
			int year) {
		this.title = title;
		this.author = author;
		this.country = country;
		this.imageLink = imageLink;
		this.language = language;
		this.link = link;
		this.pages = pages;
		this.year = year;
	}

	/**
	 * Author getter.
	 *
	 * @return author attribute
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Country getter.
	 *
	 * @return country attribute
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * ImageLink getter.
	 *
	 * @return imageLink attribute
	 */
	public String getImageLink() {
		return imageLink;
	}

	/**
	 * Language getter.
	 *
	 * @return language attribute
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Link getter.
	 *
	 * @return link attribute
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Pages getter.
	 *
	 * @return pages attribute
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * Year getter.
	 *
	 * @return year attribute
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Title getter
	 *
	 * @return title attribute
	 */
	public String getTitle() {
		return title;
	}
}
