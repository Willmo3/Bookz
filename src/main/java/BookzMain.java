import java.io.IOException;

public class BookzMain {

	public static void main(String[] args) throws IOException {
		BookzController controller;
		controller = args.length == 0 ? new BookzController() : new BookzController(args[0]);
		controller.readyUI();
	}
}
