package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Controller controller = new Controller();
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        mainStage = primaryStage;
        mainStage.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);

        controller.init();
        controller.initHook();
        Parent root = controller.getRoot();

        mainStage.setTitle("Hooker");
        mainStage.setScene(new Scene(root, 400, 200));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void run() {
        Platform.runLater(() -> {
            mainStage.sizeToScene();
            mainStage.show();
        });
    }

    @Override
    public void stop(){

    }
}
