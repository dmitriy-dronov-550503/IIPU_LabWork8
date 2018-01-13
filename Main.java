import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) {
        GlobalKeyListener hookKeyboard = new GlobalKeyListener();
        GlobalMouseListener hookMouse = new GlobalMouseListener();
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        // Construct the example object.
        GlobalMouseListener example = new GlobalMouseListener();

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }

}
