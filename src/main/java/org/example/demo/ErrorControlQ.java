package org.example.demo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorControlQ {
  @FXML
  private Label LabelMensaje;
  private  String mensaje;
 public void setMensaje(String mensaje){
   this.mensaje = mensaje;
 }
 public void setLabelMensaje(){
   LabelMensaje.setText(mensaje);
 }
  public void btnAceptar(Event event){
    // Obtiene el Stage desde el evento actual
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // Cierra el Stage
    stage.close();
  }
}
