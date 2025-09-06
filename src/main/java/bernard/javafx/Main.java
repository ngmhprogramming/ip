package bernard.javafx;

import java.io.IOException;

import bernard.core.Bernard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Bernard using FXML.
 */
public class Main extends Application {

    private Bernard bernard = new Bernard();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setBernard(bernard); // inject the Bernard instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
