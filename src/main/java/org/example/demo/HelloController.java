package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.stage.*;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class HelloController implements Initializable{
  @FXML
  private TableColumn<?, ?> columDetalle;

  @FXML
  private TableColumn<?, ?> columName;

  @FXML
  private TableColumn<?, ?> columTipoCaso;

  @FXML
  private TableView<?> tablaNoti;

  @FXML
  private TextField TFuser;
  @FXML
  private PasswordField TFpasword;

  public void iniciarSecion(ActionEvent event) {
    String user = TFuser.getText();
    String password = TFpasword.getText();
    String sql = "SELECT * FROM `registro_de_casos` WHERE estado='pendiente'";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();

      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        String userBD = rs.getString(2);

        String passwordBD = rs.getString(3);

        if ((Objects.equals(userBD, TFuser.getText())) & (Objects.equals(TFpasword.getText(), passwordBD))) {
          conexion.desconectarConexion();
          st.close();
          FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          Scene scene = new Scene(fxmlLoader.load());
          stage.setTitle("Pendiente de dos numeros");
          stage.setScene(scene);
          stage.show();

        } else {
          JOptionPane.showMessageDialog(null, "la contraseña es incorrecta");
        }
      } else {
        JOptionPane.showMessageDialog(null, "El usuario es incorrecto");
        TFuser.setText("");
        TFpasword.setText("");
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "error al conectar " + e);
    }

  }

  public void openEdUs(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editarUsuarios.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Editar Usuarios");
    stage.setScene(scene);
    stage.show();
  }

  public void openEdCl(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editarClientes.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Editar Clientes");
    stage.setScene(scene);
    stage.show();
  }

  public void openEdPr(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editarCasos.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Editar Casos");
    stage.setScene(scene);
    stage.show();
  }

  public void openReUs(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registroUsuario.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Registrar de usuarios");
    stage.setScene(scene);
    stage.show();
  }

  public void openReCl(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registroClientes.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Registro de clientes");
    stage.setScene(scene);
    stage.show();
  }

  public void openRePr(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registroCasos.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Registro de casos");
    stage.setScene(scene);
    stage.show();
  }

  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    @FXML
    void openEdPr(ActionEvent event) {

    }

    @FXML
    void openEdUs(ActionEvent event) {

    }

    @FXML
    void openReCl(ActionEvent event) {

    }

    @FXML
    void openRePr(ActionEvent event) {

    }

    @FXML
    void openReUs(ActionEvent event) {

    }


  }
}
