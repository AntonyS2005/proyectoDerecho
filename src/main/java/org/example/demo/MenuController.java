package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.*;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable{
  @FXML
  private TextField TFuser;
  @FXML
  private PasswordField TFpasword;



  public void iniciarSecion(ActionEvent event) {
    String user = TFuser.getText();
    String password = TFpasword.getText();
    String sql = "SELECT * FROM `usuarios` WHERE usuario='"+user+"'";
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
          openFXML("menuPrincipal","menu",event,null);
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
    //el ultimo dato es el nombre del css te los deje en null por que como no se si vas a usar
    //el mismo de menu.css o otro pero de hay solo escribre el nombre del archivo sin agregar .css
    //ya lo programe yo para que te lo agregue solo
    openFXML("editarUsuarios","Editar usuarios",event,null);
  }

  public void openEdCl(ActionEvent event) throws IOException {
    openFXML("editarClientes","Editar clientes",event,null);
  }

  public void openEdPr(ActionEvent event) throws IOException {
    openFXML("editarCasos","Editar casos",event,null);
  }

  public void openReUs(ActionEvent event) throws IOException {
    openFXML("registroUsuario","Registro de usuarios",event,"ejemploNombreS");
  }

  public void openReCl(ActionEvent event) throws IOException {
    openFXML("registroClientes","Registro de clientes",event,null);
  }

  public void openRePr(ActionEvent event) throws IOException {
    openFXML("registroCasos","Registro de casos",event,null);
  }

  public void openMainMenu(ActionEvent event) throws IOException {
    openFXML("menuPrincipal","Menu principal",event,null);
  }
  public void openSubFil(ActionEvent event) throws IOException{
    openFXML("subArchivo","Plantillas",event,null);
  }
  public void openAu(ActionEvent event) throws IOException {
    openFXML("agregarAudiencia", "agregar audiencia",event,null);
  }
  public void openEdAud(ActionEvent event) throws IOException {
    openFXML("editarAudiencia", "editar audiencia", event,null);
  }
  public void exportToExcel(){
    ExportToExcel exportToExcel= new ExportToExcel();
    exportToExcel.exportToExcel();
  }

  public void openFXML(String fxml,String title,ActionEvent event,String nombreDelCSS) throws IOException{
    fxml=fxml+".fxml";
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle(title);
    if(nombreDelCSS != null){
      nombreDelCSS+=".css";
      String css = this.getClass().getResource(nombreDelCSS).toExternalForm();
      scene.getStylesheets().add(css);}
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
}