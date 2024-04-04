import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

/**
 * Reads a book list from an InputStream and returns a list of books.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookListReader {
    /**
     * Reads a book list from an InputStream and returns a list of books.
     *
     * @param input InputStream containing json file list of books.
     * @return java list of books
     */
    fun readBookList(input: InputStream?): MutableList<Book> {
        val mapper = ObjectMapper()
        val tree = mapper.readTree(input)
        val books: MutableList<Book> = ArrayList()

        for (node in tree) {
            books.add(
                Book(
                    node["title"].asText(), node["author"].asText(), node["country"].asText(),
                    node["imageLink"].asText(), node["language"].asText(), node["link"].asText(),
                    node["pages"].asInt(), node["year"].asInt()
                )
            )
        }

        return books
    }
}
