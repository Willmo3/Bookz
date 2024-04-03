import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Reads a book list from an InputStream and returns a list of books. This code
 * complies with the JMU honor code.
 *
 * @author William Morris
 * @version 9/6/2022
 */
public class BookListReader {
	/**
	 * Reads a book list from an InputStream and returns a list of books.
	 *
	 * @param input InputStream containing json file list of books.
	 * @return java list of books
	 * @throws IOException if InputStream contains malformed data.
	 */
	public static List<Book> readBookList(InputStream input) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode tree = mapper.readTree(input);
		List<Book> books = new ArrayList<Book>();

		for (JsonNode node : tree) {
			books.add(new Book(node.get("title").asText(), node.get("author").asText(), node.get("country").asText(),
					node.get("imageLink").asText(), node.get("language").asText(), node.get("link").asText(),
					node.get("pages").asInt(), node.get("year").asInt()));
		}

		return books;
	}
}
