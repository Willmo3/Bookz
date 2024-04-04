/**
 * Book class. Encapsulates data regarding books, including date, author, etc.
 * This work complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class Book
/**
 * @param title    title of the book.
 * @param author    book's author
 * @param country   country of origin
 * @param imageLink link to book's title image
 * @param language  language book was written in
 * @param link      link to book's page
 * @param pages     number of pages in book
 * @param year      year of origin; negative number indicates BCE.
 */(
	/**
     * Title getter
     *
     * @return title attribute
     */
	val title: String,
	/**
     * Author getter.
     *
     * @return author attribute
     */
	val author: String,
	/**
     * Country getter.
     *
     * @return country attribute
     */
	val country: String,
	/**
     * ImageLink getter.
     *
     * @return imageLink attribute
     */
	val imageLink: String,
	/**
     * Language getter.
     *
     * @return language attribute
     */
	val language: String,
	/**
     * Link getter.
     *
     * @return link attribute
     */
	val link: String,
	/**
     * Pages getter.
     *
     * @return pages attribute
     */
	val pages: Int,
	/**
     * Year getter.
     *
     * @return year attribute
     */
	val year: Int
)
