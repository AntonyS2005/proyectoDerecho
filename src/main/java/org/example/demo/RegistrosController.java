package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegistrosController implements Initializable {
  @FXML private TextField TFuser;
  @FXML private TextField TFpassword;
  @FXML private TextField TFdpi;
  @FXML private TextField TFname;
  @FXML private ChoiceBox<String> CBestadoCivil;
  @FXML private TextField TFlastName;
  @FXML private TextField TFnumber;
  @FXML private DatePicker DPcalendar;
  private String[] estadoCivilArray= {"Casad@","Solter@"};
  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
  }

  public void registrarUsuarios() throws SQLException {
    try{
    Conexion conexion = new Conexion();
    String consulta = "INSERT INTO `usuarios`(`usuario`, `contrasena`) VALUES (?,?)";
    CallableStatement insert =conexion.establecerConexion().prepareCall(consulta);
    insert.setString(1,TFuser.getText());
    insert.setString(2,TFpassword.getText());
    insert.execute();
    JOptionPane.showMessageDialog(null,"Se guardaron los datos correctamente");
    TFpassword.setText("");
    TFuser.setText("");
  }catch (Exception e){
      JOptionPane.showMessageDialog(null,"error al registrar usuario");
    }}
  public void registrarCliente() throws SQLException {
    try{
      Conexion conexion = new Conexion();
      String consulta = "INSERT INTO `clientes`(`dpi`, `nombre`, `apellidos`, `estado_civil`, `numero_de_telefono`, `fecha_de_nacimiento`) " +
              "VALUES (?,?,?,?,?,?)";
      CallableStatement insert =conexion.establecerConexion().prepareCall(consulta);
      insert.setString(1,TFdpi.getText());
      insert.setString(2,TFname.getText());
      insert.setString(3,TFlastName.getText());
      insert.setString(4,CBestadoCivil.getValue().toString());
      insert.setString(5,TFnumber.getText());
      insert.setString(6,DPcalendar.getValue().toString());
      insert.execute();
      JOptionPane.showMessageDialog(null,"Se guardaron los datos correctamente");
      TFdpi.setText("");
      TFname.setText("");
      TFlastName.setText("");
      CBestadoCivil.setValue("");
      TFnumber.setText("");
    }catch (Exception e){
      JOptionPane.showMessageDialog(null,"error al registrar usuario por favor verificar que los campos esten correctos");
    }}


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if(CBestadoCivil !=null){
    CBestadoCivil.getItems().addAll(estadoCivilArray);
  }}
}
