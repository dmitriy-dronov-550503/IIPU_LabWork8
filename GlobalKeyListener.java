import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private FileWriter writer = new FileWriter("KeyboardEvents.txt");

    private QueueBuffer<Character> buf = new QueueBuffer<>(5);

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        save(e, "Pressed");
        buf.add(NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0));
        if (buf.toString().equals("BREAK")) { //if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        save(e, "Realeased");
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
        save(e, "Typed");
    }

    private void save(NativeKeyEvent e, String message){
        writer.write(TimeManager.getTime()+"\t"+message+": "+e.getKeyCode() + NativeKeyEvent.getKeyText(e.getKeyCode()));
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

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}
