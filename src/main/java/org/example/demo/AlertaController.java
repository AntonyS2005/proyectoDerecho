package org.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
public class AlertaController  {
  @FXML
  private Label mensaje;
  private Boolean aceptar;
  private String infomacion;
  public void setMensaje(String mensaje){
    infomacion=mensaje;
  }
  public void btnAceptar(ActionEvent event){
    aceptar=true;
    // Obtiene el Stage desde el evento actual
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // Cierra el Stage
    stage.close();
  }
  public void btnCancelar(ActionEvent event){
    aceptar=false;
    // Obtiene el Stage desde el evento actual
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // Cierra el Stage
    stage.close();
  }
  public boolean getAceptar(){
    return aceptar;
  }
  public void setInLabels(){
    mensaje.setText(infomacion);
  }

}
