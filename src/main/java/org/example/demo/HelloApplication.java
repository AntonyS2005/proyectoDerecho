package org.example.demo;
  
  import javafx.application.Application;
  import javafx.fxml.FXMLLoader;
  import javafx.scene.Scene;
  import javafx.scene.text.Font;
  import javafx.stage.Stage;

  import java.io.IOException;

  public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      //String css = this.getClass().getResource("menu.css").toExternalForm();
      //scene.getStylesheets().add(css);
    stage.setTitle("Iniciar secion");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}