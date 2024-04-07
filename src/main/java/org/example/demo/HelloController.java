package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }
  @FXML
  private TextField TFuser;
  @FXML
  private TextField TFpasword;

  @FXML
  protected void  BiniciarSecion(){

  }
}