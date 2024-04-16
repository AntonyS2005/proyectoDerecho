package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.stage.*;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class MenuController {
  @FXML
  private TextField TFuser;
  @FXML
  private PasswordField TFpasword;


  public void iniciarSecion(ActionEvent event) {
    String user = TFuser.getText();
    String password = TFpasword.getText();
    String sql = "SELECT * FROM `usuarios`";
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
          openFXML("menuPrincipal","menu",event);
        } else {
          JOptionPane.showMessageDialog(null, "la contraseña es incorrecta");
        }
      } else {
        JOptionPane.showMessageDialog(null, "El usuario es incorrecto");
        TFuser.setText("");
        TFpasword.setText("");
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "error al conectar ");
    }

  }

  public void openEdUs(ActionEvent event) throws IOException {
    openFXML("editarUsuarios","Editar usuarios",event);
  }

  public void openEdCl(ActionEvent event) throws IOException {
    openFXML("editarClientes","Editar clientes",event);
  }

  public void openEdPr(ActionEvent event) throws IOException {
    openFXML("editarCasos","Editar casos",event);
  }

  public void openReUs(ActionEvent event) throws IOException {
    openFXML("registroUsuario","Registro de usuarios",event);
  }

  public void openReCl(ActionEvent event) throws IOException {
    openFXML("registroClientes","Registro de clientes",event);
  }

  public void openRePr(ActionEvent event) throws IOException {
    openFXML("registroCasos","Registro de casos",event);
  }

  public void openMainMenu(ActionEvent event) throws IOException {
    openFXML("menuPrincipal","Menu principal",event);
  }

  public void openFXML(String fxml,String title,ActionEvent event) throws IOException{
    fxml=fxml+".fxml";
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
  }

}
