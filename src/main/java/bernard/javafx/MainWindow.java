package bernard.javafx;

import bernard.core.Bernard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bernard bernard;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Walter.png"));
    private Image bernardImage = new Image(this.getClass().getResourceAsStream("/images/Bernard.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Bernard instance */
    public void setBernard(Bernard b) {
        bernard = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bernard.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBernardDialog(response, bernardImage)
        );
        userInput.clear();

        // Close the window if the response is the goodbye message
        if (response.equals("Goodbye! See you again!")) {
            javafx.stage.Stage stage = (javafx.stage.Stage) userInput.getScene().getWindow();
            stage.close();
        }
    }
}
