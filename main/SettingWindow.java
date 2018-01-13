package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SettingWindow extends Application {
    private static Controller controller = new Controller();
    private Stage mainStage;
    private static boolean isFirst = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller.init();
        Parent root = controller.getRoot();
        mainStage = primaryStage;

        Platform.setImplicitExit(false);

        primaryStage.setTitle("Hooker");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    public void run() {
        if(isFirst){
            launch();
            isFirst=false;
        }
        else{
            Platform.runLater(() -> {
                mainStage.show();
            });
        }

    }


}
