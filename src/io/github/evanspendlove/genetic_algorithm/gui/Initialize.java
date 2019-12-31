package io.github.evanspendlove.genetic_algorithm.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
public class Initialize extends Application {

    StringProperty title = new SimpleStringProperty();
    // launch the application
    public void start(Stage stage)
    {
        TextField titleTextField;
        titleTextField = new TextField();
        titleTextField.setText("Stage Coach");
        titleTextField.setPrefColumnCount(15);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().add(new Label("title:"));
        hBox.getChildren().add(titleTextField);

        Scene scene  = new Scene(hBox,270,270);
        title.bind(titleTextField.textProperty());

        stage.setScene(scene);
        stage.titleProperty().bind(title);


        stage.show();
    }

    public static void main(String args[])
    {
        // launch the application
        launch(args);
    }
}
