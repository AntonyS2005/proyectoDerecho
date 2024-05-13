package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.*;
import java.sql.*;
import java.util.Optional;

public class GestionDocumentosController {

  @FXML
  private TableView<Document> documentosTableView;
  @FXML
  private Button descargarButton;
  @FXML
  private Button editarButton;
  @FXML
  private Button eliminarButton;
  @FXML
  private  TableColumn<Document, String> tituloColumn;
  private final Timeline searchTimeline = new Timeline();
  @FXML
  private void initialize() {
    // Configurar la columna de Título
    tituloColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
    tituloColumn.setResizable(true); // Hacer la columna redimensionable

    // Ocultar las columnas de ID y Archivo
    TableColumn<Document, Integer> idColumn = new TableColumn<>("ID");
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    idColumn.setResizable(true); // Hacer la columna redimensionable
    idColumn.setVisible(false);

    TableColumn<Document, String> archivoColumn = new TableColumn<>("Archivo");
    archivoColumn.setCellValueFactory(cellData -> cellData.getValue().archivoProperty());
    archivoColumn.setResizable(true); // Hacer la columna redimensionable
    archivoColumn.setVisible(false);

    documentosTableView.getColumns().addAll(tituloColumn);
    documentosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Hacer que las columnas se ajusten al ancho de la tabla

    // Llenar la tabla con los documentos de la base de datos
    cargarDocumentosDesdeBD();
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
  private void descargarArchivo() {
    Document selectedDocument = documentosTableView.getSelectionModel().getSelectedItem();
    if (selectedDocument != null) {
      try {
        Connection connection = new Conexion().establecerConexion();
        PreparedStatement statement = connection.prepareStatement("SELECT archivo FROM documentos WHERE id = ?");
        statement.setInt(1, selectedDocument.getId());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          Blob blob = resultSet.getBlob("archivo");
          InputStream inputStream = blob.getBinaryStream();
          FileChooser fileChooser = new FileChooser();
          fileChooser.setInitialFileName(selectedDocument.getTitulo() + ".docx");
          File file = fileChooser.showSaveDialog(null);
          if (file != null) {
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
              outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            // Abrir el archivo después de la descarga
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
              Desktop.getDesktop().open(file);
            } else {
              // Manejo si el sistema no soporta la apertura de archivos
              System.out.println("No se pudo abrir el archivo automáticamente.");
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        // Manejo de errores
      }
    }
  }

  @FXML
  private void editarDocumento() throws SQLException {
    Document selectedDocument = documentosTableView.getSelectionModel().getSelectedItem();
    if (selectedDocument != null) {
      // Preguntar al usuario si desea editar el documento
      Alert editAlert = new Alert(Alert.AlertType.CONFIRMATION);
      editAlert.setTitle("Confirmación");
      editAlert.setHeaderText("¿Desea editar el documento?");
      Optional<ButtonType> editResult = editAlert.showAndWait();
      if (editResult.isPresent() && editResult.get() == ButtonType.OK) {
        // Preguntar al usuario si desea cambiar el nombre del archivo
        Alert renameAlert = new Alert(Alert.AlertType.CONFIRMATION);
        renameAlert.setTitle("Confirmación");
        renameAlert.setHeaderText("¿Desea cambiar el nombre del archivo?");
        Optional<ButtonType> renameResult = renameAlert.showAndWait();
        if (renameResult.isPresent() && renameResult.get() == ButtonType.OK) {
          // Pedir al usuario que ingrese el nuevo nombre del archivo
          TextInputDialog dialog = new TextInputDialog(selectedDocument.getTitulo());
          dialog.setTitle("Nuevo Nombre de Archivo");
          dialog.setHeaderText("Ingrese el nuevo nombre del archivo:");
          Optional<String> nuevoNombre = dialog.showAndWait();
          if (nuevoNombre.isPresent() && !nuevoNombre.get().isEmpty()) {
            // Actualizar el nombre del archivo en la base de datos
            Connection connection = new Conexion().establecerConexion();
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE documentos SET titulo = ? WHERE id = ?");
            updateStatement.setString(1, nuevoNombre.get());
            updateStatement.setInt(2, selectedDocument.getId());
            updateStatement.executeUpdate();
          }
        }
        // Preguntar al usuario si desea cambiar el documento
        Alert changeFileAlert = new Alert(Alert.AlertType.CONFIRMATION);
        changeFileAlert.setTitle("Confirmación");
        changeFileAlert.setHeaderText("¿Desea cambiar el documento?");
        Optional<ButtonType> changeFileResult = changeFileAlert.showAndWait();
        if (changeFileResult.isPresent() && changeFileResult.get() == ButtonType.OK) {
          // Seleccionar un nuevo archivo
          FileChooser fileChooser = new FileChooser();
          fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos Word", "*.docx"));
          File nuevoArchivo = fileChooser.showOpenDialog(null);
          if (nuevoArchivo != null) {
            // Resto del código para editar el documento...
          }
        }
        // Actualizar la tabla
        cargarDocumentosDesdeBD();
      }
    }
  }

  @FXML
  private void eliminarDocumento() {
    Document selectedDocument = documentosTableView.getSelectionModel().getSelectedItem();
    if (selectedDocument != null) {
      try {
        Connection connection = new Conexion().establecerConexion();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM documentos WHERE id = ?");
        statement.setInt(1, selectedDocument.getId());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
          documentosTableView.getItems().remove(selectedDocument);
        }
      } catch (Exception e) {
        e.printStackTrace();
        // Manejo de errores
      }
    }
  }

  private void cargarDocumentosDesdeBD() {
    try {
      Connection connection = new Conexion().establecerConexion();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT id, titulo FROM documentos");
      List<Document> documentos = new ArrayList<>();
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String titulo = resultSet.getString("titulo");
        documentos.add(new Document(id, titulo, ""));
      }
      documentosTableView.getItems().setAll(documentos);
    } catch (Exception e) {
      e.printStackTrace();
      // Manejo de errores
    }
  }
}

