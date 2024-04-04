import BookzFile.Companion.invalidFile
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

/**
 * Processes a list of books in various ways.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookzList internal constructor(val books: MutableList<Book>) {
	val booksPerAuthor: Map<String, ArrayList<Book>>

    /**
     * File processing constructor. Takes a filename and gets the books list from that.
     *
     * @param booksJsonFile Name of file to process.
     */
    internal constructor(booksJsonFile: File) : this(BookListReader().readBookList(FileInputStream(booksJsonFile)))

    /**
     * Existing list constructor. Takes a list of books and gets around to getting the author.
     *
     */
    init {
        this.booksPerAuthor = processBooksByAuthor(books)
    }


    /**
     * Maps each author to the books they've made.
     *
     * @param books List of all books being considered.
     * @return A map of all books per author.
     */
    private fun processBooksByAuthor(books: List<Book>): Map<String, ArrayList<Book>> {
        val booksPerAuthor: MutableMap<String, ArrayList<Book>> = TreeMap()

        var author: String
        for (book in books) {
            author = book.author

            booksPerAuthor.putIfAbsent(author, ArrayList())
            booksPerAuthor[author]!!.add(book)
        }

        return booksPerAuthor
    }

    /*
	 * Updates one book node to contain a new book.
	 * 
	 * @param book Book to update
	 * @param newTitle Thing to set the title of the book to.
	 */
    fun updateBook(book: Book, newBook: Book) {
        var isFound = false

        var i = 0
        while (i < books.size && !isFound) {
            val current = books[i]
            if (current == book) {
                books.remove(current)
                books.add(newBook)
                isFound = true
            }
            i++
        }
    }

    /**
     * Adds a book to the list of books.
     *
     * @param book Book to add.
     */
    fun addBook(book: Book) {
        books.add(book)
    }

    companion object {
        private const val DEFAULT_INT = 0
        private const val DEFAULT_STRING = "Unknown"
        private const val EMPTY_ERROR = "Value for field %s not specified. Defaulting to %s.\n"

        private const val TITLE_INDEX = 0
        private const val AUTHOR_INDEX = 1
        private const val COUNTRY_INDEX = 2
        private const val IMGLINK_INDEX = 3
        private const val LANG_INDEX = 4
        private const val LINK_INDEX = 5
        private const val PAGE_INDEX = 6
        private const val YEAR_INDEX = 7

        /**
         * Formats a book entry's title in the UI. Format: Title (year)
         *
         * @param book Book to format
         * @return String representation of the formatted title.
         */
        fun formatEntry(book: Book): String {
            val formattedBook = StringBuilder()
            formattedBook.append(book.title)
            formattedBook.append(" by ")
            formattedBook.append(book.author)
            formattedBook.append(" (")
            if (book.year < 0) {
                formattedBook.append(abs(book.year.toDouble()))
                formattedBook.append(" BCE")
            } else {
                formattedBook.append(book.year)
            }
            formattedBook.append(")")

            return formattedBook.toString()
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
        fun parseToEdit(original: Book, bookAttributes: List<String>): Book {
            val title = parseField(bookAttributes[TITLE_INDEX], "title", original.title)
            val author = parseField(bookAttributes[AUTHOR_INDEX], "author", original.author)
            val country = parseField(bookAttributes[COUNTRY_INDEX], "country", original.country)
            val imageLink = parseImageLink(bookAttributes[IMGLINK_INDEX], original.imageLink)
            val language = parseField(bookAttributes[LANG_INDEX], "language", original.language)
            val link = parseField(bookAttributes[LINK_INDEX], "link", original.link)
            val pages = parseInt(bookAttributes[PAGE_INDEX], "pages", original.pages)
            val year = parseInt(bookAttributes[YEAR_INDEX], "year", original.year)

            return Book(title, author, country, imageLink, language, link, pages, year)
        }

        /**
         * Special case for parsing an image link when editing a file.
         * This allows the imageLink to be kept at original value when editing a book.
         *
         * @param imageLink Link to the new book's image.
         * @param defaultStr default imageLink.
         * @return The default imageLink if provided is invalid, provided if not.
         */
        private fun parseImageLink(imageLink: String, defaultStr: String): String {
            var retVal = imageLink

            if (invalidFile(imageLink, ".jpg")) {
                println("Invalid image (does it exist?): $imageLink defaulting to: $defaultStr")
                retVal = defaultStr
            }

            return retVal
        }

        /**
         * Parses a book from a list of attributes.
         * Unlike parseToEdit, this method sets all defaults to global defaults.
         *
         * @param bookAttributes List of attributes to make a book from.
         * @return The parsed book.
         */
        fun parseBook(bookAttributes: List<String>): Book {
            val title = parseField(bookAttributes[TITLE_INDEX], "title", DEFAULT_STRING)
            val author = parseField(bookAttributes[AUTHOR_INDEX], "author", DEFAULT_STRING)
            val country = parseField(bookAttributes[COUNTRY_INDEX], "country", DEFAULT_STRING)
            val imageLink = parseField(bookAttributes[IMGLINK_INDEX], "image link", DEFAULT_STRING)
            val language = parseField(bookAttributes[LANG_INDEX], "language", DEFAULT_STRING)
            val link = parseField(bookAttributes[LINK_INDEX], "link", DEFAULT_STRING)
            val pages = parseInt(bookAttributes[PAGE_INDEX], "pages", DEFAULT_INT)
            val year = parseInt(bookAttributes[YEAR_INDEX], "year", DEFAULT_INT)

            return Book(title, author, country, imageLink, language, link, pages, year)
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
        private fun parseField(toParse: String, description: String, defaultStr: String): String {
            var retVal = toParse

            if (toParse.isEmpty()) {
                System.out.printf(EMPTY_ERROR, description, defaultStr)
                retVal = defaultStr
            }

            return retVal
        }

        /**
         * Gets an int from a string -- or sets to default, if it isn't an int.
         *
         * @param toParse String to get int from.
         * @param description Description of the field (in case there are problems).
         * @return the int.
         */
        private fun parseInt(toParse: String, description: String, defaultInt: Int): Int {
            var retVal: Int
            try {
                retVal = toParse.toInt()
            } catch (e: Exception) {
                if (toParse.isEmpty()) {
                    System.out.printf(EMPTY_ERROR, description, defaultInt)
                } else {
                    println("Value $toParse for field $description not an integer. Defaulting to $defaultInt.")
                }
                retVal = defaultInt
            }

            return retVal
        }
    }
}
