package org.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
    stage.setScene(scene);
    stage.show();
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
