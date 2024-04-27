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
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class  EditarController implements Initializable {
  @FXML private TextField TFdpi;
  @FXML private Label Lcliente;
  @FXML private TextField TFtipo;
  @FXML private Label TFcosto;
  @FXML private TextField TFcostoNew;
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
  @FXML private  TableColumn<Casos,String> CidCaso;
  @FXML private TableColumn<Casos,String> Cdpi;
  @FXML private TableView<usuarios> Tusers;
  @FXML private TableColumn<usuarios,String> Cuser;
  @FXML private  TableColumn<usuarios,String> Cpas;
  @FXML private TableColumn<usuarios,String> CidUser;
  @FXML private TextField TFuser;
  @FXML private TextField TFpasword;
  private  String idCaso;
  private ObservableList<Casos> items= FXCollections.observableArrayList();
  private ObservableList<usuarios> itemsUser= FXCollections.observableArrayList();
  private String[] estadoProceso= {"pendiente","finalizado"};
  int idUser;



  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
  }

  public boolean calcularSaldoPendiente() {
    String adelantoStr = TFadelanto.getText();
    String newCostStr = TFcostoNew.getText();

    // Obtener los valores actuales
    double costoActual = Double.parseDouble(TFcosto.getText());
    double saldoPendienteActual = Double.parseDouble(LsaldoPendiente.getText());

    // Variables para el nuevo costo y saldo pendiente
    double newCost = costoActual;
    double saldoPendienteNuevo = saldoPendienteActual;

    // Verificar si se ha ingresado un nuevo costo
    if (!newCostStr.isEmpty()) {
      newCost = Double.parseDouble(newCostStr); // Nuevo costo ingresado por el usuario

      // Verificar si el nuevo costo es válido (no negativo)
      if (newCost < 0) {
        JOptionPane.showMessageDialog(null, "Error: El nuevo costo no puede ser negativo.");
        TFcostoNew.setText("");
        return false;
      }

      // Calcular el adelanto dado actual
      double adelantoDadoActual = costoActual - saldoPendienteActual;

      // Verificar si se ha ingresado un adelanto
      if (!adelantoStr.isEmpty()) {
        double adelanto = Double.parseDouble(adelantoStr); // Adelanto ingresado por el usuario

        // Verificar si el adelanto es válido (no negativo)
        if (adelanto < 0) {
          JOptionPane.showMessageDialog(null, "Error: El adelanto no puede ser negativo.");
          TFadelanto.setText("");
          return false;
        }

        // Verificar si el adelanto es mayor que el saldo pendiente actual
        if (adelanto > saldoPendienteActual) {
          JOptionPane.showMessageDialog(null, "Error: El adelanto es mayor que el saldo pendiente.");
          TFadelanto.setText("");
          return false;
        }

        // Calcular el saldo pendiente nuevo teniendo en cuenta el nuevo costo y el adelanto
        saldoPendienteNuevo = Math.max(0, newCost - adelanto);
      } else { // Si no se ha ingresado un adelanto
        // Calcular el saldo pendiente nuevo teniendo en cuenta el nuevo costo y el adelanto dado actual
        saldoPendienteNuevo = Math.max(0, newCost - adelantoDadoActual);
      }
    } else if (!adelantoStr.isEmpty()) { // Si no se ha ingresado un nuevo costo pero sí un adelanto
      double adelanto = Double.parseDouble(adelantoStr); // Adelanto ingresado por el usuario

      // Verificar si el adelanto es válido (no negativo)
      if (adelanto < 0) {
        JOptionPane.showMessageDialog(null, "Error: El adelanto no puede ser negativo.");
        TFadelanto.setText("");
        return false;
      }

      // Verificar si el adelanto es mayor que el saldo pendiente actual
      if (adelanto > saldoPendienteActual) {
        JOptionPane.showMessageDialog(null, "Error: El adelanto es mayor que el saldo pendiente.");
        TFadelanto.setText("");
        return false;
      }

      // Calcular el saldo pendiente nuevo teniendo en cuenta solo el adelanto
      saldoPendienteNuevo = Math.max(0, costoActual - adelanto);
    }

    // Actualizar los campos solo si hay cambios
    if (newCost != costoActual || saldoPendienteNuevo != saldoPendienteActual) {
      TFcosto.setText(String.valueOf(newCost));
      LsaldoPendiente.setText(String.valueOf(saldoPendienteNuevo));
      TFadelanto.setText("");
      return true;
    }

    // Devolver verdadero incluso si no hay cambios
    return true;
  }





  public  void busPorDPIcasos(){
    Lcliente.setText("");
    TFtipo.setText("");
    TFcosto.setText("");
    LsaldoPendiente.setText("");
    CBestadoProceso.setValue(null);
    TAdetalles.setText("");
    items.clear();
    if(Objects.equals(TFdpi.getText(), "")){
      tablaCasos();
    }else {
      String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
              " clientes ON registro_de_casos.id_cliente = clientes.dpi WHERE registro_de_casos.id_cliente = '"+TFdpi.getText()+"';";
     sqlTablaCasos(sql);
    }}

  public void tablaCasos(){
    String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
            " clientes ON registro_de_casos.id_cliente = clientes.dpi;";
    sqlTablaCasos(sql);
  }

  public void getSelecDate(){
    Tcasos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Obtener el valor de la columna "Ccliente" de la fila seleccionada
        String cliente,tipo,costo,saldoPendiente,estado,detalle,dpi;
        cliente = Ccliente.getCellData(newSelection);
        tipo = Ctipo.getCellData(newSelection);
        costo = Ccosto.getCellData(newSelection);
        saldoPendiente = CsaldoPendiente.getCellData(newSelection);
        estado = Cestado.getCellData(newSelection);
        detalle = Cdetalles.getCellData(newSelection);
        idCaso = CidCaso.getCellData(newSelection);
        dpi= Cdpi.getCellData(newSelection);
        // Mostrar el dato en el TextField
        Lcliente.setText(cliente);
        TFtipo.setText(tipo);
        TFcosto.setText(costo);
        LsaldoPendiente.setText(saldoPendiente);
        CBestadoProceso.setValue(estado);
        TAdetalles.setText(detalle);
        TFdpi.setText(dpi);
      }
    });
  }

  public void editarCaso(){
    if(calcularSaldoPendiente()){
    try{
      Conexion conexion = new Conexion();
      String consulta = "UPDATE `registro_de_casos` SET `tipo`=?, `costo`=?, `detalles`=?, `saldo_pendiente`=?, `estado_del_proceso`=? WHERE `id_caso`=?";
      CallableStatement update = conexion.establecerConexion().prepareCall(consulta);
      update.setString(1, TFtipo.getText());
      update.setString(2, TFcosto.getText());
      update.setString(3, TAdetalles.getText());
      update.setString(4, LsaldoPendiente.getText());
      update.setString(5, CBestadoProceso.getValue().toString());
      update.setString(6, idCaso);
      update.execute();
      JOptionPane.showMessageDialog(null,"Se editaron los datos correctamente");
      busPorDPIcasos();
      TFcosto.setText("");
      TAdetalles.setText("");
      LsaldoPendiente.setText("");
      CBestadoProceso.setValue(null);
      TFdpi.setText("");
      TFcostoNew.setText("");
    }catch (Exception e){
      JOptionPane.showMessageDialog(null,"error al editar los casos por favor verificar que los campos esten correctos "+e);
    }
  }else return;}
  public void eliminarCaso() {
    try {
      Conexion conexion = new Conexion();
      String consulta = "DELETE FROM `registro_de_casos` WHERE `id_caso`=?";
      CallableStatement delete = conexion.establecerConexion().prepareCall(consulta);
      delete.setString(1, idCaso); // idCaso es la variable que contiene el ID del caso a eliminar
      delete.execute();
      JOptionPane.showMessageDialog(null, "Se eliminó el registro correctamente");
      // Limpiar los campos si es necesario
      busPorDPIcasos();
      TFtipo.setText("");
      TFcosto.setText("");
      TAdetalles.setText("");
      LsaldoPendiente.setText("");
      CBestadoProceso.setValue(null);
      TFdpi.setText("");
      TFadelanto.setText("");
      TFcostoNew.setText("");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al eliminar el registro. Por favor, verifique que los campos estén correctos. " + e);
    }
  }

  public void sqlTablaCasos(String consulta){
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(consulta);
      String cliente,tipo,costo,saldoPendiente,estado,detalles,dpi;
      while(rs.next()) {
        dpi=rs.getString(7).trim();
        idCaso= rs.getString(1);
        tipo=rs.getString(2).trim();
        costo=rs.getString(3).trim();
        saldoPendiente=rs.getString(5).trim();
        detalles=rs.getString(4).trim();
        estado=rs.getString(6).trim();
        cliente=rs.getString(8).trim();
        this.CidCaso.setCellValueFactory(new PropertyValueFactory<>("idCaso"));
        this.Cdpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        this.Ccliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.Ctipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.Ccosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        this.Cdetalles.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        this.CsaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("saldoPendiente"));
        this.Cestado.setCellValueFactory(new PropertyValueFactory<>("estadoDelProceso"));
        items.add(new Casos(dpi,idCaso,cliente,tipo,costo,saldoPendiente,estado,detalles));
        this.Tcasos.setItems(items);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null, "error al conectar");
    }
  }
  // user
  public void tablaUsuarios() {
    String sql = "SELECT * FROM usuarios";
    sqlTablaUsuarios(sql);
  }

  public void sqlTablaUsuarios(String consulta) {
    try {
      itemsUser.clear();
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(consulta);
      String usuario, contrasena,idUser;
      while (rs.next()) {
        idUser = String.valueOf(rs.getInt(1));
        usuario = rs.getString(2).trim();
        contrasena = rs.getString(3).trim();
        this.CidUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        this.Cuser.setCellValueFactory(new PropertyValueFactory<>("user"));
        this.Cpas.setCellValueFactory(new PropertyValueFactory<>("password"));
        itemsUser.add(new usuarios( usuario, contrasena,idUser));
        this.Tusers.setItems(itemsUser);
      }
      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al conectar: " + e);
    }
  }

  public void getSelecDataUsuarios() {
    Tusers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Obtener el valor de las columnas de la fila seleccionada
        String usuario, contrasena;

        idUser = Integer.parseInt(CidUser.getCellData(newSelection));
        usuario = Cuser.getCellData(newSelection);
        contrasena = Cpas.getCellData(newSelection);

        // Mostrar los datos en los campos correspondientes
        TFuser.setText(usuario);
        TFpasword.setText(contrasena);
      }
    });
  }

  public void editarUsuario() {
    try {
      Conexion conexion = new Conexion();
      String consulta = "UPDATE usuarios SET usuario=?, contrasena=? WHERE id_user=?";
      CallableStatement update = conexion.establecerConexion().prepareCall(consulta);
      update.setString(1, TFuser.getText());
      update.setString(2, TFpasword.getText());
      update.setInt(3, Integer.parseInt(CidUser.getCellData(Tusers.getSelectionModel().getSelectedItem())));
      update.execute();
      JOptionPane.showMessageDialog(null, "Se editó el usuario correctamente");
      tablaUsuarios();
      TFuser.setText("");
      TFpasword.setText("");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al editar el usuario. Por favor, verifique que los campos estén correctos. " + e);
    }
  }

  public void eliminarUsuario() {
    try {
      Conexion conexion = new Conexion();
      String consulta = "DELETE FROM usuarios WHERE id_user=?";
      CallableStatement delete = conexion.establecerConexion().prepareCall(consulta);
      delete.setInt(1, Integer.parseInt(CidUser.getCellData(Tusers.getSelectionModel().getSelectedItem())));
      delete.execute();
      JOptionPane.showMessageDialog(null, "Se eliminó el usuario correctamente");
      tablaUsuarios();
      TFuser.setText("");
      TFpasword.setText("");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al eliminar el usuario. Por favor, verifique que los campos estén correctos. " + e);
    }
  }






  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if(Tcasos != null){
      tablaCasos();
      CBestadoProceso.getItems().addAll(estadoProceso);
      CidCaso.setVisible(false);
      Cdpi.setVisible(false);
    }
    if(Tusers != null){
      tablaUsuarios();
      CidUser.setVisible(false);
    }
  }
}
