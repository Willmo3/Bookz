import com.formdev.flatlaf.FlatDarculaLaf;

/**
 * Java entry point for Bookz.
 * Makes it easy to build as Jar.
 *
 * Author: Willmo3
 * Version: 4/3/2024
 */
class BookzMain {
   public static void main(String[] args) {
        FlatDarculaLaf.setup();
        BookzController controller = args.length == 0 ? new BookzController() : new BookzController(args[0]);
        controller.readyUI();
    }
}
