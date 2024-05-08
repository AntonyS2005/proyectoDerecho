package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    String css = this.getClass().getResource("Aspectomenu.css").toExternalForm();
    scene.getStylesheets().add(css);
    stage.setTitle("Iniciar sesión");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
