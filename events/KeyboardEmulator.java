package events;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyboardEmulator {

    public static void put(){
        try {
            Robot robot = new Robot();

            // Simulate a mouse click
            //robot.mousePress(InputEvent.BUTTON1_MASK);
            //robot.mouseRelease(InputEvent.BUTTON1_MASK);

            // Simulate a key press
            //robot.keyPress(KeyEvent.VK_BACK_SPACE);
            //robot.keyRelease(KeyEvent.VK_BACK_SPACE);

            robot.keyPress(KeyEvent.VK_4);
            robot.keyRelease(KeyEvent.VK_4);
            robot.keyPress(KeyEvent.VK_2);
            robot.keyRelease(KeyEvent.VK_2);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
