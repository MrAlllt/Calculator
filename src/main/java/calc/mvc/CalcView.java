package calc.mvc;

import calc.mvc.ex.PropertiesReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class CalcView extends Application {
    private PropertiesReader properties;
    private Stage currentStage;

    public void showGUI() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CalcSchema.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            String css;
            Image img;

            properties = new PropertiesReader("config.properties");
            if (properties.isError()) {
                css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
                img = new Image(Objects.requireNonNull(getClass().getResource("icon.png")).toString());
            } else {
                css = Objects.requireNonNull(this.getClass().getResource(properties.getProperty("CSS_FILE"))).toExternalForm();
                img = new Image(Objects.requireNonNull(getClass().getResource(properties.getProperty("ICON"))).toString());

                double windowX = Double.parseDouble(properties.getProperty("WINDOW_X"));
                double windowY = Double.parseDouble(properties.getProperty("WINDOW_Y"));

                stage.setX(windowX);
                stage.setY(windowY);
                stage.setResizable(false);

                currentStage = stage;
            }

            scene.getStylesheets().add(css);
            stage.getIcons().add(img);

            stage.setTitle("Calculator");
            stage.show();
        } catch (Exception e) {
            showError("FXML File Error", "Error occurred while launching app!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void stop() {
        if (!properties.isError()) {
            String windowY = String.valueOf(currentStage.getY());
            String windowX = String.valueOf(currentStage.getX());

            properties.setProperty("WINDOW_Y", windowY);
            properties.setProperty("WINDOW_X", windowX);

            properties.saveProperties();
        }
    }

    public void setLabelText(Label label, String text) {
        label.setText(text);
    }
    public void setLabelText(Label label, char text) {
        label.setText(String.valueOf(text));
    }

    public void matchSize(Label label) {
        String labelContent = label.getText();

        if (labelContent.length() < 15)
            changeLabelFontSize(label, 40);
        else if (labelContent.length() < 20)
            changeLabelFontSize(label, 30);
        else
            changeLabelFontSize(label, 20);

    }

    public void changeLabelFontSize(Label label, double size) {
        label.setFont(new Font(size));
    }

    public String getLabelText(Label label) {
        return label.getText();
    }

    public void showError(String header, String content) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText(header);
        error.setContentText(content);
        error.showAndWait();
    }
}