package org.example.demo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion
{
  Connection conectar = null;

  String user = "root";
  String password = "";
  String bd= "proyectoderecho";
  String ip = "localhost";
  String puerto = "3306";

  String cadena =  "jdbc:mysql://"+ip+":"+puerto+"/"+bd;

  public Connection establecerConexion() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conectar = DriverManager.getConnection(cadena,user,password);
    //JOptionPane.showMessageDialog(null,"conectada");
    }catch (Exception e){
      showErrorDialog("por favor verificar su conexion a internet");
    }
    return  conectar;
  }

  public void desconectarConexion(){
    conectar = null;
  }

  private void showErrorDialog(String mensaje) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("error.fxml"));
      Parent root = loader.load();
      ErrorControlQ controller = loader.getController();
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
}

