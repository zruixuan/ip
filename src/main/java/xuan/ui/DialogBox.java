package xuan.ui;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Represents a dialog box containing a message and an image.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box with the specified text and image.
     *
     * @param text the message to display
     * @param image the image to display
     */
    private DialogBox(String text, ImageView image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(image.getImage());

        setOnMouseEntered(event -> moveDialogBox(-3));
        setOnMouseExited(event -> moveDialogBox(0));
    }

    /**
     * Creates a dialog box for a user message.
     *
     * @param text the user's message
     * @param image the user's image
     * @return the user dialog box
     */
    public static DialogBox getUserDialog(String text, ImageView image) {
        return new DialogBox(text, image);
    }

    /**
     * Creates a dialog box for a Xuan message.
     *
     * @param text Xuan's response
     * @param image Xuan's image
     * @return the Xuan dialog box
     */
    public static DialogBox getXuanDialog(String text, ImageView image) {
        DialogBox dialogBox = new DialogBox(text, image);

        dialogBox.getChildren().clear();
        dialogBox.getChildren().addAll(
                dialogBox.displayPicture,
                dialogBox.dialog
        );

        dialogBox.setAlignment(Pos.TOP_LEFT);
        dialogBox.dialog.setStyle(
                "-fx-background-color: #e8e8e8;"
                        + "-fx-background-radius: 10;"
                        + "-fx-padding: 10;"
        );

        return dialogBox;
    }

    /**
     * Moves the dialog box vertically with a short animation.
     *
     * @param y the target vertical translation
     */
    private void moveDialogBox(double y) {
        TranslateTransition transition =
                new TranslateTransition(Duration.millis(120), this);
        transition.setToY(y);
        transition.play();
    }
}
