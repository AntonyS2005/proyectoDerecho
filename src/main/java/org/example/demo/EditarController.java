package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class  EditarController implements Initializable {
  @FXML private TextField TFdpi;
  @FXML private Label Lcliente;
  @FXML private TextField TFtipo;
  @FXML private TextField TFcosto;
  @FXML private TextField TFadelanto;
  @FXML private Label LsaldoPendiente;
  @FXML private ChoiceBox CBestadoProceso;
  @FXML private TextArea TAdetalles;
  @FXML private TableView<Casos> Tcasos;
  @FXML private TableColumn<Casos,String> Ccliente;
  @FXML private TableColumn<Casos,String> Ctipo;
  @FXML private TableColumn<Casos,String> Ccosto;
  @FXML private TableColumn<Casos,String> CsaldoPendiente;
  @FXML private TableColumn<Casos,String> Cestado;
  @FXML private TableColumn<Casos,String> Cdetalles;
  private ObservableList<Casos> items= FXCollections.observableArrayList();
  private String[] estadoProceso= {"pendiente","finalizado"};

  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
  }

  public  void busPorDPIcasos(){
    Lcliente.setText("");
    TFtipo.setText("");
    TFcosto.setText("");
    LsaldoPendiente.setText("");
    CBestadoProceso.setValue("");
    TAdetalles.setText("");
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
      JOptionPane.showMessageDialog(null, "error al conectar");
    }
  }

  public void getSelecDate(){
    Tcasos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Obtener el valor de la columna "Ccliente" de la fila seleccionada
        String cliente,tipo,costo,saldoPendiente,estado,detalle;
                cliente = Ccliente.getCellData(newSelection).toString();
                tipo = Ctipo.getCellData(newSelection).toString();
                costo = Ccosto.getCellData(newSelection).toString();
                saldoPendiente = CsaldoPendiente.getCellData(newSelection).toString();
                estado = Cestado.getCellData(newSelection).toString();
                detalle = Cdetalles.getCellData(newSelection).toString();

        // Mostrar el dato en el TextField
        Lcliente.setText(cliente);
        TFtipo.setText(tipo);
        TFcosto.setText(costo);
        LsaldoPendiente.setText(saldoPendiente);
        CBestadoProceso.setValue(estado);
        TAdetalles.setText(detalle);
      }
    });
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if(Tcasos != null){
      tablaCasos();
      CBestadoProceso.getItems().addAll(estadoProceso);
    }
  }
}
