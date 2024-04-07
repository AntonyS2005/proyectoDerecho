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
import java.sql.*;
import java.util.Objects;


public class HelloController {
  @FXML
  private TextField TFuser;
  @FXML
  private PasswordField TFpasword;

  public void  iniciarSecion(ActionEvent event){
    String user=TFuser.getText();
    String password = TFpasword.getText();
    String sql = "SELECT * FROM `usuarios` WHERE usuario='"+user+"'";
    try {
      Conexion conexion = new Conexion();
      Statement st =conexion.establecerConexion().createStatement();

      ResultSet rs = st.executeQuery(sql);
      if(rs.next()){
      String userBD =  rs.getString(2);

      String passwordBD = rs.getString(3);

      if((Objects.equals(userBD, TFuser.getText())) & (Objects.equals(TFpasword.getText(), passwordBD))){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pendiente de dos numeros");
        stage.setScene(scene);
        stage.show();

      }}else {
        JOptionPane.showMessageDialog(null,"El usuario y/o contraseña son incorrectos");
        TFuser.setText("");
        TFpasword.setText("");
      }

    }catch (Exception e){
      JOptionPane.showMessageDialog(null,"error al conectar "+e);
    }

  }}


