package org.example.demo;
  
  import javafx.application.Application;
  import javafx.fxml.FXMLLoader;
  import javafx.scene.Scene;
  import javafx.scene.image.Image;
  import javafx.scene.text.Font;
  import javafx.stage.Stage;

  import java.io.IOException;

  public class HelloApplication extends Application {

    public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      String css = this.getClass().getResource("Aspecto.css").toExternalForm();
      scene.getStylesheets().add(css);

      // Cargar el icono desde la ruta especificada
      Image icon = new Image(getClass().getResourceAsStream("/img/icono.png"));

      // Configurar el icono del escenario (Stage)
      stage.getIcons().add(icon);

      stage.setTitle("Iniciar sesión");
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      stage.centerOnScreen();
    }

  public static void main(String[] args) {
    launch();
  }
}