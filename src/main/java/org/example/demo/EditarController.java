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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class  EditarController implements Initializable {
  @FXML private TextField TFdpi;
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
  private TableColumn<Audiencias, String> Efecha;
  @FXML
  private TableColumn<Audiencias, String> Ehora;
  @FXML
  private TableColumn<Audiencias, String> Eubicacion;
  @FXML
  private TableColumn<Audiencias, String> Edetalles;
  @FXML
  private TableColumn<Casos,String> Cdetalles;
  @FXML
  private TextField AAdpi;
  @FXML
  private Label EdAname;
  @FXML
  private TableView<Audiencias> TEAudiencias;
  private ObservableList<Casos> items= FXCollections.observableArrayList();
  private ObservableList<Audiencias> itemsAud= FXCollections.observableArrayList();

  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
  }

  public  void busPorDPIcasos(){
    items.clear();
    String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
            " clientes ON registro_de_casos.id_cliente = clientes.dpi WHERE registro_de_casos.id_cliente = '"+TFdpi.getText()+"';";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String cliente,tipo,costo,saldoPendiente,estado,detalles;
      while(rs.next()) {
        tipo=rs.getString(2).trim();
        costo=rs.getString(3).trim();
        saldoPendiente=rs.getString(5).trim();
        detalles=rs.getString(4).trim();
        estado=rs.getString(6).trim();
        cliente=rs.getString(8).trim();
        this.Ccliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.Ctipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.Ccosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        this.Cdetalles.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        this.CsaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("saldoPendiente"));
        this.Cestado.setCellValueFactory(new PropertyValueFactory<>("estadoDelProceso"));
        items.add(new Casos(cliente,tipo,costo,saldoPendiente,estado,detalles));
        this.Tcasos.setItems(items);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null,e);
    }
  }

  public void tablaCasos(){
    String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
            " clientes ON registro_de_casos.id_cliente = clientes.dpi;";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String cliente,tipo,costo,saldoPendiente,estado,detalles;
      while(rs.next()) {
        tipo=rs.getString(2).trim();
        costo=rs.getString(3).trim();
        saldoPendiente=rs.getString(5).trim();
        detalles=rs.getString(4).trim();
        estado=rs.getString(6).trim();
        cliente=rs.getString(8).trim();
        this.Ccliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.Ctipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.Ccosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        this.Cdetalles.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        this.CsaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("saldoPendiente"));
        this.Cestado.setCellValueFactory(new PropertyValueFactory<>("estadoDelProceso"));
        items.add(new Casos(cliente,tipo,costo,saldoPendiente,estado,detalles));
        this.Tcasos.setItems(items);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }
  public void editarAudiencia(){
    String sql = "SELECT audiencias.*, clientes.nombre AS nombre_cliente FROM audiencias " +
            "INNER JOIN clientes ON audiencias.dpi = clientes.dpi;";
    try{
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String ubicacion,fecha,hora,detalles;
      while(rs.next()){
        ubicacion=rs.getString(3).trim();
        fecha=rs.getString(4).trim();
        hora=rs.getString(5).trim();
        detalles=rs.getString(6).trim();
        this.Eubicacion.setCellValueFactory(new PropertyValueFactory<> ("ubicacion"));
        this.Efecha.setCellValueFactory(new PropertyValueFactory<> ("fecha"));
        this.Ehora.setCellValueFactory(new PropertyValueFactory<> ("hora"));
        this.Edetalles.setCellValueFactory(new PropertyValueFactory<> ("detalles"));
        itemsAud.add(new Audiencias(ubicacion,fecha,hora,detalles));
        this.TEAudiencias.setItems(itemsAud);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }
  public void BusAudDPI(){
    String dpi;
    dpi=AAdpi.getText();
    String sql = "SELECT * FROM `clientes` WHERE dpi='"+dpi+"'";
    try{
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        String nombre;
        nombre=rs.getString(2)+" "+rs.getString(3);
        EdAname.setText(nombre);
      }
      conexion.desconectarConexion();
      st.close();
      rs.close();

    }catch (Exception e){}

  }



  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if(Tcasos != null){tablaCasos();}
    if(TEAudiencias!=null){
      editarAudiencia();}
    }
  }

