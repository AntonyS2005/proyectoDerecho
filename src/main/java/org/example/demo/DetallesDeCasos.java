// Controlador DetallesDeCasos.java
package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetallesDeCasos implements Initializable {
  @FXML
  private Label dpiLabel;
  @FXML
  private Label nombreLabel;
  @FXML
  private Label fechaLabel;
  @FXML
  private Label ubicacionLabel;
  @FXML
  private Label horaLabel;
  @FXML
  private TextArea detallesText;

  private String idAudiencia;

  public void setIdAudiencia(String idAudiencia) {
    this.idAudiencia = idAudiencia;
    obtenerDetalles();
  }
  public void openMainMenu(ActionEvent event) throws IOException {
    // Obtiene el Stage desde el evento actual
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // Cierra el Stage
    stage.close();
  }
  private void obtenerDetalles() {
    Conexion conexion = new Conexion();
    try (Connection conectar = conexion.establecerConexion();
         PreparedStatement statement = conectar.prepareStatement(
                 "SELECT a.dpi, a.ubicacion_de_la_audiencia, " +
                         "a.fecha_de_audiencia, a.hora_de_la_audiencia, a.detalles, c.nombre " +
                         "FROM audiencias a " +
                         "INNER JOIN clientes c ON a.dpi = c.dpi " +
                         "WHERE a.id_audiencia = ?")) {

      statement.setString(1, idAudiencia);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int dpi = resultSet.getInt("dpi");
          String ubicacion = resultSet.getString("ubicacion_de_la_audiencia");
          String fecha = resultSet.getString("fecha_de_audiencia");
          String hora = resultSet.getString("hora_de_la_audiencia");
          String detalles = resultSet.getString("detalles");
          String nombreCliente = resultSet.getString("nombre");

          dpiLabel.setText(String.valueOf(dpi));
          ubicacionLabel.setText(ubicacion);
          fechaLabel.setText(fecha);
          horaLabel.setText(hora);
          detallesText.setText(detalles);
          nombreLabel.setText(nombreCliente);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    detallesText.setEditable(false);
  }
}
