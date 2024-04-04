import com.formdev.flatlaf.FlatDarculaLaf;

class BookzMain {
   public static void main(String[] args) {
        FlatDarculaLaf.setup();
        BookzController controller = args.length == 0 ? new BookzController() : new BookzController(args[0]);
        controller.readyUI();
    }
}
