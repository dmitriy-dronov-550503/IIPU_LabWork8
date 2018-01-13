package events;

import logic.TimeManager;
import logs.FileLogWriter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {

    private FileLogWriter writer = new FileLogWriter("MouseEvents.txt");

    public void nativeMouseClicked(NativeMouseEvent e) {
        writer.write(TimeManager.getTime()+"\tBtn: "+e.getButton()+" X: "+e.getX()+" Y: "+e.getY());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        writer.write(TimeManager.getTime()+"\tBtn: "+e.getButton()+" X: "+e.getX()+" Y: "+e.getY());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }

    public static void main(String[] args) {
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
    }
}