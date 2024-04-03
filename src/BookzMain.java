import java.io.FileNotFoundException;
import java.io.IOException;

public class BookzMain {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		BookzController controller;
		
		if (args.length == 0) {
			controller = new BookzController();
		} else {
			controller = new BookzController(args[0]);
		}
		
		controller.readyUI();
	}
}
