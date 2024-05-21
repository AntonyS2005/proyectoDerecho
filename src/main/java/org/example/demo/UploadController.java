package org.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadController {
  @FXML
  private TextField tituloField;
  @FXML
  private Label archivoLabel;

  private File archivoSeleccionado;

  private void showSuccesDialog(String mensaje) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirmacion.fxml"));
      Parent root = loader.load();
      ConfirmacionController controller = loader.getController();
      controller.setMensaje(mensaje);
      controller.setLabelMensaje();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOnCloseRequest(e -> e.consume());
      stage.setScene(new Scene(root));
      stage.showAndWait(); // Espera hasta que se cierre la ventana
    } catch (IOException e) {
    }
  }

  @FXML
  private void seleccionarArchivo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos Word", "*.docx", "*.doc"));
    archivoSeleccionado = fileChooser.showOpenDialog(null);
    if (archivoSeleccionado != null) {
      archivoLabel.setText("Archivo seleccionado: " + archivoSeleccionado.getName());
    }
  }
  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    String css = this.getClass().getResource("Aspecto.css").toExternalForm();
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }
  @FXML
  private void subirArchivo() {
    if (archivoSeleccionado != null && !tituloField.getText().isEmpty()) {
      try (Connection connection = new Conexion().establecerConexion()) {
        String sql = "INSERT INTO documentos (titulo, archivo) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setString(1, tituloField.getText());
          FileInputStream inputStream = new FileInputStream(archivoSeleccionado);
          statement.setBinaryStream(2, inputStream, archivoSeleccionado.length());
          statement.executeUpdate();
          archivoLabel.setText("Archivo subido correctamente.");
          showSuccesDialog("se subio el archivo correctamente");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        archivoLabel.setText("Error al subir el archivo.");
      } catch(Exception e){
        e.printStackTrace();
        archivoLabel.setText("Error inesperado.");
      }
    } else {
      archivoLabel.setText("Por favor selecciona un archivo y proporciona un título.");
    }
  }
}
