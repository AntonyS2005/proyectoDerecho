package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CasosController implements Initializable {
  @FXML
  private TableView<Casos> Tcasos;
  @FXML
  private TableColumn<Casos,String> Ccliente;
  @FXML
  private TableColumn<Casos,String> Ctipo;
  @FXML
  private TableColumn<Casos,String> Ccosto;
  @FXML
  private TableColumn<Casos,String> CsaldoPendiente;
  @FXML
  private TableColumn<Casos,String> Cestado;
  @FXML
  private TableColumn<Casos,String> Cdetalles;
  private ObservableList<Casos> items= FXCollections.observableArrayList();

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
    String sql = "SELECT * FROM `registro_de_casos`";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String cliente,tipo,costo,saldoPendiente,estado,detalles;
      while(rs.next()) {
        tipo=rs.getString(1).trim();
        costo=rs.getString(2).trim();
        saldoPendiente=rs.getString(4).trim();
        detalles=rs.getString(3).trim();
        estado=rs.getString(5).trim();
        this.Ccliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.Ctipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.Ccosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        this.Cdetalles.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        this.CsaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("saldoPendiente"));
        this.Cestado.setCellValueFactory(new PropertyValueFactory<>("estadoDelProceso"));
        items.add(new Casos(tipo,"antony",costo,saldoPendiente,estado,detalles));
        this.Tcasos.setItems(items);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null, "error al conectar");
    }
  }
}
