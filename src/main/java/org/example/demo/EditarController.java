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
import javafx.scene.control.skin.TableViewSkinBase;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class EditarController implements Initializable {
  @FXML
  private TextField TFdpi;
  @FXML
  private Label Lcliente;
  @FXML
  private TextField TFtipo;
  @FXML
  private Label TFcosto;
  @FXML
  private TextField TFcostoNew;
  @FXML
  private TextField TFadelanto;
  @FXML
  private Label LsaldoPendiente;
  @FXML
  private ChoiceBox CBestadoProceso;
  @FXML
  private TextArea TAdetalles;
  @FXML
  private TableView<Casos> Tcasos;
  @FXML
  private TableColumn<Casos, String> Ccliente;
  @FXML
  private TableColumn<Casos, String> Ctipo;
  @FXML
  private TableColumn<Casos, String> Ccosto;
  @FXML
  private TableColumn<Casos, String> CsaldoPendiente;
  @FXML
  private TableColumn<Casos, String> Cestado;
  @FXML
  private TableColumn<Casos, String> Cdetalles;
  @FXML
  private TableColumn<Casos, String> CidCaso;
  @FXML
  private TableColumn<Casos, String> Cdpi;
  @FXML
  private TableView<usuarios> Tusers;
  @FXML
  private TableColumn<usuarios, String> Cuser;
  @FXML
  private TableColumn<usuarios, String> Cpas;
  @FXML
  private TableColumn<usuarios, String> CidUser;
  @FXML
  private TextField TFuser;
  @FXML
  private TextField TFpasword;
  @FXML
  private TextField TFname;
  @FXML
  private TextField TFlastName;
  @FXML
  private ChoiceBox CBestadoCivil;
  @FXML
  private DatePicker DPfecNac;
  @FXML
  private TextField TFnumber;
  @FXML
  private TableView<clientes> Tclientes;
  @FXML
  private TableColumn<clientes, String> ClastName;
  @FXML
  private TableColumn<clientes, String> CestadoCivil;
  @FXML
  private TableColumn<clientes, String> Cnumber;
  @FXML
  private TableColumn<clientes, String> Cdate;
  @FXML
  private TableColumn<clientes, String> Cname;
  @FXML
  private TableColumn<clientes, String> CidClienet;

  @FXML
  private TableColumn<Audiencias, String> Efecha;
  @FXML
  private TableColumn<Audiencias, String> Ehora;
  @FXML
  private TableColumn<Audiencias, String> Eubicacion;
  @FXML
  private TableColumn<Audiencias, String> Edetalles;
  @FXML
  private TableColumn<Audiencias, String> Eid;
  @FXML
  private TextField AAdpi;
  @FXML
  private Label EdAname;
  @FXML
  private TableView<Audiencias> TEAudiencias;
  @FXML
  private TextField EdUbi; // TextField para la ubicación de la audiencia

  @FXML
  private DatePicker DPfecha; // DatePicker para la fecha de la audiencia

  @FXML
  private TextField EdHora; // TextField para la hora de la audiencia

  @FXML
  private TextArea EdDetalle; // TextArea para los detalles de la audiencia
  private ObservableList<Casos> items = FXCollections.observableArrayList();
  private ObservableList<Audiencias> itemsAud = FXCollections.observableArrayList();
  private ObservableList<clientes> itemsClientes = FXCollections.observableArrayList();
  private String idCaso;
  private ObservableList<usuarios> itemsUser = FXCollections.observableArrayList();
  private String[] estadoProceso = { "pendiente", "finalizado" };
  int idUser;
  int idCliente;
  String idAu;

  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    String css = this.getClass().getResource("Aspecto.css").toExternalForm();
    scene.getStylesheets().add(css);
    stage.setTitle("menu");
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  public void busPorDPIcasos() {
    Lcliente.setText("");
    TFtipo.setText("");
    TFcosto.setText("");
    LsaldoPendiente.setText("");
    CBestadoProceso.setValue(null);
    TAdetalles.setText("");
    items.clear();
    if (Objects.equals(TFdpi.getText(), "")) {
      tablaCasos();
    } else {
      String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
          " clientes ON registro_de_casos.id_cliente = clientes.dpi WHERE registro_de_casos.id_cliente = '"
          + TFdpi.getText() + "';";
      sqlTablaCasos(sql);
    }
  }

  public void tablaCasos() {
    String sql = "SELECT registro_de_casos.*, clientes.nombre AS nombre_cliente FROM registro_de_casos INNER JOIN" +
        " clientes ON registro_de_casos.id_cliente = clientes.dpi;";
    sqlTablaCasos(sql);
  }

  public void getSelecDate() {
    Tcasos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Obtener el valor de la columna "Ccliente" de la fila seleccionada
        String cliente, tipo, costo, saldoPendiente, estado, detalle, dpi;
        cliente = Ccliente.getCellData(newSelection);
        tipo = Ctipo.getCellData(newSelection);
        costo = Ccosto.getCellData(newSelection);
        saldoPendiente = CsaldoPendiente.getCellData(newSelection);
        estado = Cestado.getCellData(newSelection);
        detalle = Cdetalles.getCellData(newSelection);
        idCaso = CidCaso.getCellData(newSelection);
        dpi = Cdpi.getCellData(newSelection);
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

  public boolean calcularSaldoPendiente() {
    try {
      // Obtener los valores de los campos de entrada
      String costoNuevoText = TFcostoNew.getText();
      String adelantoText = TFadelanto.getText();
      double saldoPendiente = Double.parseDouble(LsaldoPendiente.getText());

      // Verificar si ambos campos están vacíos
      if (costoNuevoText.isEmpty() && adelantoText.isEmpty()) {
        return true; // No se realizan cálculos
      }

      // Calcular el nuevo costo y el nuevo saldo pendiente si cambian ambos valores
      if (!costoNuevoText.isEmpty() && !adelantoText.isEmpty()) {
        double nuevoCosto = Double.parseDouble(costoNuevoText);
        double adelanto = Double.parseDouble(adelantoText);

        // Verificar si el nuevo costo es negativo
        if (nuevoCosto < 0) {
          JOptionPane.showMessageDialog(null, "El costo nuevo no puede ser negativo.");
          TFcostoNew.setText("");
          return false;
        }

        // Calcular el nuevo saldo pendiente
        double nuevoSaldoPendiente = saldoPendiente + (nuevoCosto - Double.parseDouble(TFcosto.getText()));

        // Verificar si el saldo pendiente es negativo
        if (nuevoSaldoPendiente < 0) {
          JOptionPane.showMessageDialog(null, "El saldo pendiente no puede ser negativo.");
          TFcostoNew.setText("");
          TFadelanto.setText("");
          return false;
        }

        // Actualizar los valores en los Labels
        LsaldoPendiente.setText(String.valueOf(nuevoSaldoPendiente));
        TFcosto.setText(costoNuevoText);
        TFcostoNew.setText("");

        // Verificar si el adelanto es mayor o igual al saldo pendiente
        if (adelanto < 0 || adelanto > nuevoSaldoPendiente) {
          JOptionPane.showMessageDialog(null, "El adelanto no puede ser negativo o mayor que el saldo pendiente.");
          TFadelanto.setText("");
          return false;
        }

        // Calcular el nuevo saldo pendiente con el adelanto ingresado
        double nuevoSaldoPendienteConAdelanto = nuevoSaldoPendiente - adelanto;

        // Verificar si el nuevo saldo pendiente con adelanto es negativo
        if (nuevoSaldoPendienteConAdelanto < 0) {
          JOptionPane.showMessageDialog(null, "El saldo pendiente con adelanto no puede ser negativo.");
          TFadelanto.setText("");
          return false;
        }

        // Actualizar el saldo pendiente en el Label
        LsaldoPendiente.setText(String.valueOf(nuevoSaldoPendienteConAdelanto));
        TFadelanto.setText("");

        return true; // Todo está correcto
      }

      // Calcular el costo nuevo y el nuevo saldo pendiente si cambia solo el costo
      if (!costoNuevoText.isEmpty()) {
        double nuevoCosto = Double.parseDouble(costoNuevoText);

        // Verificar si el nuevo costo es negativo
        if (nuevoCosto < 0) {
          JOptionPane.showMessageDialog(null, "El costo nuevo no puede ser negativo.");
          TFcostoNew.setText("");
          return false;
        }

        // Calcular el nuevo saldo pendiente
        double nuevoSaldoPendiente = saldoPendiente + (nuevoCosto - Double.parseDouble(TFcosto.getText()));

        // Verificar si el saldo pendiente es negativo
        if (nuevoSaldoPendiente < 0) {
          JOptionPane.showMessageDialog(null, "El saldo pendiente no puede ser negativo.");
          TFcostoNew.setText("");
          return false;
        }

        // Actualizar los valores en los Labels
        LsaldoPendiente.setText(String.valueOf(nuevoSaldoPendiente));
        TFcosto.setText(costoNuevoText);
        TFcostoNew.setText("");
      }

      // Calcular el saldo pendiente con el adelanto ingresado
      if (!adelantoText.isEmpty()) {
        double adelanto = Double.parseDouble(adelantoText);

        // Verificar si el adelanto es negativo
        if (adelanto < 0) {
          JOptionPane.showMessageDialog(null, "El adelanto no puede ser negativo.");
          TFadelanto.setText("");
          return false;
        }

        // Verificar si el adelanto es mayor o igual al saldo pendiente
        if (adelanto > saldoPendiente) {
          JOptionPane.showMessageDialog(null, "El adelanto no puede ser mayor que el saldo pendiente.");
          TFadelanto.setText("");
          return false;
        }

        // Calcular el nuevo saldo pendiente
        double nuevoSaldoPendiente = saldoPendiente - adelanto;

        // Verificar si el nuevo saldo pendiente es negativo
        if (nuevoSaldoPendiente < 0) {
          JOptionPane.showMessageDialog(null, "El saldo pendiente no puede ser negativo.");
          TFadelanto.setText("");
          return false;
        }

        // Actualizar el saldo pendiente en el Label
        LsaldoPendiente.setText(String.valueOf(nuevoSaldoPendiente));
        TFadelanto.setText("");
      }

      return true; // Todo está correcto
    } catch (NumberFormatException e) {
      // Si se ingresan caracteres no numéricos, mostrar mensaje de error y resetear
      // los campos
      JOptionPane.showMessageDialog(null, "Por favor, ingresa solo valores numéricos.");
      TFcostoNew.setText("");
      TFadelanto.setText("");
      return false;
    }
  }

  public void editarCaso() {
    if (calcularSaldoPendiente()) {
      try {
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
        JOptionPane.showMessageDialog(null, "Se editaron los datos correctamente");
        busPorDPIcasos();
        TFcosto.setText("");
        TAdetalles.setText("");
        LsaldoPendiente.setText("");
        CBestadoProceso.setValue(null);
        TFdpi.setText("");
        TFcostoNew.setText("");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "error al editar los casos por favor verificar que los campos esten correctos " + e);
      }
    } else
      return;
  }

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
      JOptionPane.showMessageDialog(null,
          "Error al eliminar el registro. Por favor, verifique que los campos estén correctos. " + e);
    }
  }

  public void sqlTablaCasos(String consulta) {
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(consulta);
      String cliente, tipo, costo, saldoPendiente, estado, detalles, dpi;
      while (rs.next()) {
        dpi = rs.getString(7).trim();
        idCaso = rs.getString(1);
        tipo = rs.getString(2).trim();
        costo = rs.getString(3).trim();
        saldoPendiente = rs.getString(5).trim();
        detalles = rs.getString(4).trim();
        estado = rs.getString(6).trim();
        cliente = rs.getString(8).trim();
        this.CidCaso.setCellValueFactory(new PropertyValueFactory<>("idCaso"));
        this.Cdpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        this.Ccliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        this.Ctipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.Ccosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        this.Cdetalles.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        this.CsaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("saldoPendiente"));
        this.Cestado.setCellValueFactory(new PropertyValueFactory<>("estadoDelProceso"));
        items.add(new Casos(dpi, idCaso, cliente, tipo, costo, saldoPendiente, estado, detalles));
        this.Tcasos.setItems(items);
      }
      st.close();
      rs.close();
    } catch (Exception e) {
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
      String usuario, contrasena, idUser;
      while (rs.next()) {
        idUser = String.valueOf(rs.getInt(1));
        usuario = rs.getString(2).trim();
        contrasena = rs.getString(3).trim();
        this.CidUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        this.Cuser.setCellValueFactory(new PropertyValueFactory<>("user"));
        this.Cpas.setCellValueFactory(new PropertyValueFactory<>("password"));
        itemsUser.add(new usuarios(usuario, contrasena, idUser));
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
      JOptionPane.showMessageDialog(null,
          "Error al editar el usuario. Por favor, verifique que los campos estén correctos. " + e);
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
      JOptionPane.showMessageDialog(null,
          "Error al eliminar el usuario. Por favor, verifique que los campos estén correctos. " + e);
    }
  }

  public void editarCliente() {
    try {
      Conexion conexion = new Conexion();
      String consulta = "UPDATE clientes SET nombre=?, apellidos=?, estado_civil=?, numero_de_telefono=?, fecha_de_nacimiento=? WHERE dpi=?";
      CallableStatement update = conexion.establecerConexion().prepareCall(consulta);
      update.setString(1, TFname.getText());
      update.setString(2, TFlastName.getText());
      update.setString(3, CBestadoCivil.getValue().toString());
      update.setString(4, TFnumber.getText());
      update.setString(5, DPfecNac.getValue().toString());
      update.setString(6, TFdpi.getText());
      update.execute();
      JOptionPane.showMessageDialog(null, "Se editó el cliente correctamente");
      tablaClientes();
      limpiarCamposCliente();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null,
          "Error al editar el cliente. Por favor, verifique que los campos estén correctos. " + e);
    }
  }

  public void eliminarCliente() {
    int comp = JOptionPane.showConfirmDialog(null,
        "Esto eliminara todos los registros relacionados al client desea continuar", "Advertencia",
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    System.out.println(comp);
    if (comp == 1) {
      return;
    }
    String dpi = TFdpi.getText().trim();
    if (!dpi.isEmpty()) {
      try {
        Conexion conexion = new Conexion();

        // Eliminar registros de casos relacionados con el cliente
        String deleteRegistrosCasos = "DELETE FROM registro_de_casos WHERE id_cliente = ?";
        CallableStatement deleteStmtRegistrosCasos = conexion.establecerConexion().prepareCall(deleteRegistrosCasos);
        deleteStmtRegistrosCasos.setString(1, dpi);
        deleteStmtRegistrosCasos.execute();

        // Eliminar audiencias relacionadas con el cliente
        String deleteAudiencias = "DELETE FROM audiencias WHERE dpi = ?";
        CallableStatement deleteStmtAudiencias = conexion.establecerConexion().prepareCall(deleteAudiencias);
        deleteStmtAudiencias.setString(1, dpi);
        deleteStmtAudiencias.execute();

        // Eliminar al cliente
        String deleteCliente = "DELETE FROM clientes WHERE dpi = ?";
        CallableStatement deleteStmtCliente = conexion.establecerConexion().prepareCall(deleteCliente);
        deleteStmtCliente.setString(1, dpi);
        deleteStmtCliente.execute();

        JOptionPane.showMessageDialog(null,
            "Se eliminaron todos los registros relacionados con el cliente correctamente");
        tablaClientes();
        limpiarCamposCliente();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al eliminar los registros relacionados con el cliente. Por favor, verifique que los campos estén correctos. "
                + e);
      }
    } else {
      JOptionPane.showMessageDialog(null, "Por favor, ingrese el DPI del cliente que desea eliminar.");
    }
  }

  public void getSelecDataClientes() {
    Tclientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Obtener el valor de las columnas de la fila seleccionada
        String dpi, nombre, apellidos, estadoCivil, numeroTelefono, fechaNacimiento;

        dpi = CidClienet.getCellData(newSelection);
        nombre = Cname.getCellData(newSelection);
        apellidos = ClastName.getCellData(newSelection);
        estadoCivil = CestadoCivil.getCellData(newSelection);
        numeroTelefono = Cnumber.getCellData(newSelection);
        fechaNacimiento = Cdate.getCellData(newSelection);

        // Mostrar los datos en los campos correspondientes
        TFdpi.setText(dpi);
        TFname.setText(nombre);
        TFlastName.setText(apellidos);
        CBestadoCivil.setValue(estadoCivil);
        TFnumber.setText(numeroTelefono);
        DPfecNac.setValue(LocalDate.parse(fechaNacimiento));
      }
    });
  }

  public void sqlTablaClientes(String consulta) {
    try {
      itemsClientes.clear();
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(consulta);
      String dpi, nombre, apellidos, estadoCivil, numeroTelefono, fechaNacimiento;
      while (rs.next()) {
        dpi = rs.getString(1).trim();
        nombre = rs.getString(2).trim();
        apellidos = rs.getString(3).trim();
        estadoCivil = rs.getString(4).trim();
        numeroTelefono = rs.getString(5).trim();
        fechaNacimiento = rs.getString(6).trim();
        this.CidClienet.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        this.Cname.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.ClastName.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.CestadoCivil.setCellValueFactory(new PropertyValueFactory<>("estadoCivil"));
        this.Cnumber.setCellValueFactory(new PropertyValueFactory<>("numero"));
        this.Cdate.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        itemsClientes.add(new clientes(dpi, fechaNacimiento, numeroTelefono, apellidos, estadoCivil, nombre));
        this.Tclientes.setItems(itemsClientes);
      }
      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al conectar: ");
    }
  }

  public void tablaClientes() {
    String sql = "SELECT * FROM clientes";
    sqlTablaClientes(sql);
  }

  public void limpiarCamposCliente() {
    TFdpi.setText("");
    TFname.setText("");
    TFlastName.setText("");
    CBestadoCivil.setValue(null);
    TFnumber.setText("");
    DPfecNac.setValue(null);
  }

  public void buscarPorDPI() {
    String dpi = TFdpi.getText().trim();
    itemsClientes.clear();
    if (!dpi.isEmpty()) {
      String sql = "SELECT * FROM clientes WHERE dpi=?";
      buscarCliente(sql, dpi);
    }
  }

  private void buscarCliente(String consulta, String parametro) {
    try {
      Conexion conexion = new Conexion();
      PreparedStatement statement = conexion.establecerConexion().prepareStatement(consulta);
      statement.setString(1, parametro);
      ResultSet rs = statement.executeQuery();
      itemsClientes.clear();

      while (rs.next()) {
        String dpi = rs.getString("dpi").trim();
        String nombre = rs.getString("nombre").trim();
        String apellidos = rs.getString("apellidos").trim();
        String estadoCivil = rs.getString("estado_civil").trim();
        String numeroTelefono = rs.getString("numero_de_telefono").trim();
        String fechaNacimiento = rs.getString("fecha_de_nacimiento").trim();

        itemsClientes.add(new clientes(dpi, fechaNacimiento, numeroTelefono, apellidos, estadoCivil, nombre));
      }
      Tclientes.setItems(itemsClientes);
      statement.close();
      rs.close();
    } catch (Exception e) {
    }
  }

  private String[] estadoCivilArray = { "Casad@", "Solter@" };

  public void editarAudiencia() {
    String sql = "SELECT audiencias.*, clientes.nombre AS nombre_cliente FROM audiencias " +
        "INNER JOIN clientes ON audiencias.dpi = clientes.dpi;";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String ubicacion, fecha, hora, detalles;
      while (rs.next()) {
        ubicacion = rs.getString(3).trim();
        fecha = rs.getString(4).trim();
        hora = rs.getString(5).trim();
        detalles = rs.getString(6).trim();
        this.Eubicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        this.Efecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.Ehora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        this.Edetalles.setCellValueFactory(new PropertyValueFactory<>("detalles"));
        Eid.setCellValueFactory(new PropertyValueFactory<>("id"));
        idAu = rs.getString("id_audiencia".trim());
        itemsAud.add(new Audiencias(idAu, ubicacion, fecha, hora, detalles));
        this.TEAudiencias.setItems(itemsAud);
      }
      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  public void editarAudienciabusPorDpi() {
    // Obtener el valor de DPI del TextField AAdpi
    String dpi = AAdpi.getText().trim();

    // Consulta SQL con filtro WHERE por el valor de DPI
    String sql = "SELECT audiencias.*, clientes.nombre AS nombre_cliente FROM audiencias " +
        "INNER JOIN clientes ON audiencias.dpi = clientes.dpi " +
        "WHERE audiencias.dpi = '" + dpi + "';";

    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);

      String ubicacion, fecha, hora, detalles;

      // Limpiar la tabla antes de agregar nuevos elementos
      TEAudiencias.getItems().clear();

      while (rs.next()) {
        ubicacion = rs.getString(3).trim();
        fecha = rs.getString(4).trim();
        hora = rs.getString(5).trim();
        detalles = rs.getString(6).trim();

        // Agregar elementos a la tabla
        idAu = rs.getString("id_audiencia").trim();
        itemsAud.add(new Audiencias(idAu, ubicacion, fecha, hora, detalles));
      }

      // Asignar valores a las celdas de la tabla
      this.Eubicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
      this.Efecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
      this.Ehora.setCellValueFactory(new PropertyValueFactory<>("hora"));
      this.Edetalles.setCellValueFactory(new PropertyValueFactory<>("detalles"));
      Eid.setCellValueFactory(new PropertyValueFactory<>("id"));

      // Actualizar la tabla con los nuevos elementos
      this.TEAudiencias.setItems(itemsAud);

      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  public void getSelectDataAu() {
    // Obtener la fila seleccionada en la tabla
    Audiencias audienciaSeleccionada = TEAudiencias.getSelectionModel().getSelectedItem();

    if (audienciaSeleccionada != null) {
      // Obtener los datos de la audiencia seleccionada
      String ubicacion = audienciaSeleccionada.getUbicacion();
      String fecha = audienciaSeleccionada.getFecha();
      String hora = audienciaSeleccionada.getHora();
      String detalles = audienciaSeleccionada.getDetalles();
      String dpi = consultarDPIAudiencia(audienciaSeleccionada.getId());
      idAu = audienciaSeleccionada.getId();
      // Establecer los datos en los campos correspondientes
      EdUbi.setText(ubicacion);
      DPfecha.setValue(LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      EdHora.setText(hora);
      EdDetalle.setText(detalles);

      // Consultar el nombre del cliente asociado al DPI
      String nombreCliente = consultarNombreCliente(dpi);

      // Establecer el nombre del cliente en el Label EdAname
      EdAname.setText(nombreCliente);

      // Establecer el DPI en el TextField AAdpi
      AAdpi.setText(dpi);
      TEAudiencias.getSelectionModel().clearSelection();

    } else {
      // Si no hay fila seleccionada, limpiar los campos
      EdUbi.clear();
      DPfecha.setValue(null);
      EdHora.clear();
      EdDetalle.clear();
      EdAname.setText("");
      AAdpi.clear();
    }
  }

  public void eliminarAudiencia() {
    if (idAu != null && !idAu.isEmpty()) {
      try {
        Conexion conexion = new Conexion();
        String sql = "DELETE FROM audiencias WHERE id_audiencia = ?";
        PreparedStatement pstmt = conexion.establecerConexion().prepareStatement(sql);
        pstmt.setString(1, idAu);
        int filasEliminadas = pstmt.executeUpdate();
        if (filasEliminadas > 0) {
          // Eliminación exitosa
          JOptionPane.showMessageDialog(null, "La audiencia ha sido eliminada exitosamente.");
          // Actualizar la tabla después de la eliminación
          TEAudiencias.getItems().clear();
          editarAudiencia();
          // Limpiar los campos después de eliminar la audiencia
          limpiarCampos();
        } else {
          // No se encontró ninguna fila para eliminar
          JOptionPane.showMessageDialog(null, "No se encontró ninguna audiencia para eliminar.");
        }
        pstmt.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar la audiencia: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "No hay ninguna audiencia seleccionada para eliminar.");
    }
  }

  private void limpiarCampos() {
    EdUbi.clear();
    DPfecha.setValue(null);
    EdHora.clear();
    EdDetalle.clear();
    EdAname.setText("");
    AAdpi.clear();
    idAu = null;
  }

  private String consultarDPIAudiencia(String idAudiencia) {
    String dpi = "";
    try {
      Conexion conexion = new Conexion();
      String sql = "SELECT dpi FROM audiencias WHERE id_audiencia = '" + idAudiencia + "'";
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        dpi = rs.getString("dpi");
      }
      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    return dpi;
  }

  // Método para consultar el nombre del cliente asociado al DPI
  private String consultarNombreCliente(String dpi) {
    String nombreCliente = "";
    try {
      Conexion conexion = new Conexion();
      String sql = "SELECT `nombre` FROM `clientes` WHERE `dpi` = '" + dpi + "'";
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        nombreCliente = rs.getString("nombre");
      }
      st.close();
      rs.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
    return nombreCliente;
  }

  public void editarDatosAudiencia() {
    if (idAu != null && !idAu.isEmpty()) {
      try {
        Conexion conexion = new Conexion();
        String sql = "UPDATE audiencias SET ubicacion_de_la_audiencia = ?, fecha_de_audiencia = ?, hora_de_la_audiencia = ?, detalles = ? WHERE id_audiencia = ?";
        PreparedStatement pstmt = conexion.establecerConexion().prepareStatement(sql);
        pstmt.setString(1, EdUbi.getText());
        pstmt.setString(2, DPfecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        pstmt.setString(3, EdHora.getText());
        pstmt.setString(4, EdDetalle.getText());
        pstmt.setString(5, idAu);
        int filasActualizadas = pstmt.executeUpdate();
        if (filasActualizadas > 0) {
          // Actualización exitosa
          JOptionPane.showMessageDialog(null, "Los datos de la audiencia han sido actualizados exitosamente.");
          // Limpiar los campos después de la edición
          limpiarCampos();
          // Actualizar la tabla después de la edición
          TEAudiencias.getItems().clear();
          editarAudiencia();
        } else {
          // No se encontró ninguna fila para actualizar
          JOptionPane.showMessageDialog(null, "No se encontró ninguna audiencia para actualizar.");
        }
        pstmt.close();
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar los datos de la audiencia: " + e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "No hay ninguna audiencia seleccionada para editar.");
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (Tcasos != null) {
      Tcasos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      tablaCasos();
      CBestadoProceso.getItems().addAll(estadoProceso);
      CidCaso.setVisible(false);
      Cdpi.setVisible(false);
      Tcasos.setFixedCellSize(30);
    }
  if (Tusers != null) {
    Tusers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      tablaUsuarios();
      CidUser.setVisible(false);
    Tusers.setFixedCellSize(30);
  }
  if (Tclientes != null) {
    Tclientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      tablaClientes();
      CidClienet.setVisible(false);
      CBestadoCivil.getItems().addAll(estadoCivilArray);
    Tclientes.setFixedCellSize(30);
  }
  if (TEAudiencias != null) {
    Eid.setVisible(false);
    TEAudiencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      editarAudiencia();
    // Establecer la altura de celda fija y deshabilitar el ajuste automático del tamaño de celda
    TEAudiencias.setFixedCellSize(30); // Establece la altura de la celda en 30 píxeles (valor arbitrario)
  }
  }


}
