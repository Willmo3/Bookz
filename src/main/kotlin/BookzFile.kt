import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Handles Book Json files.
 * This code complies with the JMU honor code.
 *
 * @author William Morris
 * @version 4/3/2024
 */
class BookzFile {
    var file: File
        private set

    /**
     * Ensures that the specified filename works properly.
     * If so, sets this file to that filename.
     * If not, uses the default filename.
     *
     * @param filename Name of file to attempt to load.
     * @throws FileNotFoundException If default file cannot be found
     * and specified file is invalid.
     */
    internal constructor(filename: String) {
        file = File(filename)

        val bookName = file.name

        if (invalidFile(bookName, ".json")) {
            System.err.println("Invalid file: $filename. Defaulting to $DEFAULT_FILENAME")
            file = File(DEFAULT_FILENAME)

            if (!file.exists()) {
                throw FileNotFoundException("Default file $DEFAULT_FILENAME not found!")
            }
        }
    }

    internal constructor() {
        file = File(DEFAULT_FILENAME)
    }

    /**
     * Writes the entire list of books to the file.
     *
     * @param books List of books to write.
     */
    fun writeList(books: List<Book?>?) {
        val mapper = ObjectMapper()
        val writer = mapper.writer(DefaultPrettyPrinter())

        try {
            writer.writeValue(this.file, books)
        } catch (e: IOException) {
            System.err.println("WARNING! Could not write file. File remains at pre-write state.")
            System.err.println(e.toString())
        }
    }

    companion object {
        const val DEFAULT_FILENAME: String = "books.json"

        /**
         * Checks whether a file exists and ends with the specified extension.
         *
         * @param filename Base name of file to check
         * @param extension File extension to check
         * @return Whether the file is invalid
         */
		fun invalidFile(filename: String, extension: String): Boolean {
            val validExtension = (filename.length >= extension.length
                    && filename.endsWith(extension))

            val toCheck = File(filename)
            return !toCheck.exists() || !validExtension
        }
    }
}
