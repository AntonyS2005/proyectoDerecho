package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class RegistrosController implements Initializable {
  @FXML
  private TextField TFuser;
  @FXML
  private TextField TFpassword;
  @FXML
  private TextField TFdpi;
  @FXML
  private TextField TFname;
  @FXML
  private ChoiceBox<String> CBestadoCivil;
  @FXML
  private TextField TFlastName;
  @FXML
  private TextField TFnumber;
  @FXML
  private DatePicker DPcalendar;
  @FXML
  private Label LfechaNacimiento;
  @FXML
  private Label Lname;
  @FXML
  private Label LestadoCivil;
  @FXML
  private TextField TFtipo;
  @FXML
  private TextField TFcosto;
  @FXML
  private TextField TFadelanto;
  @FXML
  private Label LsaldoPendiente;
  @FXML
  private ChoiceBox<String> CBestadoProceso;
  @FXML
  private TextArea TAdetalles;
  @FXML
  private TextField AAUbi;
  @FXML
  private TextArea AAdetalles;
  @FXML
  private TextField AAdpi;
  @FXML
  private TextField AAhora;
  @FXML
  private DatePicker DPAudiencia;
  @FXML
  private Label Aname;


  private String[] estadoCivilArray = {"Casad@", "Solter@"};
  private String[] estadoProceso = {"pendiente", "finalizado"};
  private boolean showConfirmationDialog(String mensaje) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Alerta.fxml"));
      Parent root = loader.load();
      AlertaController controller = loader.getController();
      controller.setMensaje(mensaje);
      controller.setInLabels();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOnCloseRequest(e -> e.consume());
      stage.setScene(new Scene(root));
      stage.showAndWait(); // Espera hasta que se cierre la ventana

      return controller.getAceptar();
    } catch (IOException e) {
      return false;
    }
  }
  private void showSuccesDialog(String mensaje) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirmacion.fxml"));
      Parent root = loader.load();
      ConfirmacionController controller = loader.getController();
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
  public void openMainMenu(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("menu");
    String css = this.getClass().getResource("Aspecto.css").toExternalForm();
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }

  public void registrarUsuarios() throws SQLException {
    try {
      Conexion conexion = new Conexion();
      String consulta = "INSERT INTO `usuarios`(`usuario`, `contrasena`) VALUES (?,?)";
      CallableStatement insert = conexion.establecerConexion().prepareCall(consulta);
      insert.setString(1, TFuser.getText());
      insert.setString(2, TFpassword.getText());
      insert.execute();
      showSuccesDialog("Se guardaron los datos correctamente");
      TFpassword.setText("");
      TFuser.setText("");
    } catch (Exception e) {
      showErrorDialog("error al registrar usuario");
    }
  }

  public void registrarCliente() throws SQLException {
    try {
      Conexion conexion = new Conexion();
      String consulta = "INSERT INTO `clientes`(`dpi`, `nombre`, `apellidos`, `estado_civil`, `numero_de_telefono`, `fecha_de_nacimiento`) " +
              "VALUES (?,?,?,?,?,?)";
      CallableStatement insert = conexion.establecerConexion().prepareCall(consulta);
      insert.setString(1, TFdpi.getText());
      insert.setString(2, TFname.getText());
      insert.setString(3, TFlastName.getText());
      insert.setString(4, CBestadoCivil.getValue().toString());
      insert.setString(5, TFnumber.getText());
      insert.setString(6, DPcalendar.getValue().toString());
      insert.execute();
      showSuccesDialog("Se guardaron los datos correctamente");
      TFdpi.setText("");
      TFname.setText("");
      TFlastName.setText("");
      CBestadoCivil.setValue("");
      TFnumber.setText("");
      DPcalendar.setValue(null);
    } catch (Exception e) {
      showErrorDialog("error al registrar usuario");
    }
  }

  //esta funcion busca el nombre de la persona y los datos
  public void regCaBusDpi() {

    Lname.setText("");
    LestadoCivil.setText("");
    LfechaNacimiento.setText("");
    String dpi;
    dpi = TFdpi.getText();
    String sql = "SELECT * FROM `clientes` WHERE dpi='" + dpi + "'";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        String fechaNacimiento, nombre, estadoCivil;
        nombre = rs.getString(2) + " " + rs.getString(3);
        estadoCivil = rs.getString(4);
        fechaNacimiento = rs.getString(6);
        Lname.setText(nombre);
        LestadoCivil.setText(estadoCivil);
        LfechaNacimiento.setText(fechaNacimiento);
      }
      conexion.desconectarConexion();
      st.close();
      rs.close();
    } catch (Exception e) {
    }
  }

  public void calcularSaldoPendiente() {
    try {
      Double costo = Double.parseDouble(TFcosto.getText());
      Double saldoPendiente;
      LsaldoPendiente.setText(String.valueOf(costo));
      if (TFadelanto.getText() != "") {
        saldoPendiente = costo - Double.parseDouble(TFadelanto.getText());
        if (saldoPendiente >= 0) {
          LsaldoPendiente.setText(String.valueOf(saldoPendiente));
        } else {
          showErrorDialog("el adelato es mayor al costo");
          TFadelanto.setText("");
        }
      }
    } catch (Exception e) {
    }
  }

  public void guardarCaso() {
    try {
      Conexion conexion = new Conexion();
      String consulta = "INSERT INTO `registro_de_casos`( `tipo`, `costo`, `detalles`, `saldo_pendiente`, `estado_del_proceso`, `id_cliente`) " +
              "VALUES (?,?,?,?,?,?)";
      CallableStatement insert = conexion.establecerConexion().prepareCall(consulta);
      insert.setString(1, TFtipo.getText());
      insert.setString(2, TFcosto.getText());
      insert.setString(3, TAdetalles.getText());
      insert.setString(4, LsaldoPendiente.getText());
      insert.setString(5, CBestadoProceso.getValue().toString());
      insert.setString(6, TFdpi.getText());
      insert.execute();
      showSuccesDialog("Se guardaron los datos correctamente");
      TFtipo.setText("");
      TFcosto.setText("");
      TAdetalles.setText("");
      LsaldoPendiente.setText("");
      CBestadoProceso.setValue(null);
      TFdpi.setText("");
      TFadelanto.setText("");
    } catch (Exception e) {
      showErrorDialog("error al registrar usuario");
    }
  }

  public void guardarAudiencia() {
    Conexion conexion = null;
    try {
      conexion = new Conexion();
      String consulta = "INSERT INTO `audiencias`(`dpi`, `ubicacion_de_la_audiencia`, `fecha_de_audiencia`, " +
              "`hora_de_la_audiencia`, `detalles`) VALUES (?,?,?,?,?)";
      CallableStatement insert = conexion.establecerConexion().prepareCall(consulta);
      insert.setString(1, AAdpi.getText());
      insert.setString(2, AAUbi.getText());
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String fechaFormateada = DPAudiencia.getValue().format(dateFormatter);
      insert.setString(3, fechaFormateada);
      insert.setString(4, AAhora.getText());
      insert.setString(5, AAdetalles.getText());

      // Ejecuta la consulta para guardar los datos en la base de datos
      insert.executeUpdate();

      showSuccesDialog("Se guardaron los datos correctamente");

      // Limpia los campos después de guardar los datos
      AAdpi.setText("");
      AAUbi.setText("");
      AAhora.setText("");
      AAdetalles.setText("");
      DPAudiencia.setValue(null);
    } catch (Exception e) {
      showErrorDialog("Error al agregar audiencia.");
    } finally {
      // Cierra la conexión a la base de datos en cualquier caso
      if (conexion != null) {
        conexion.desconectarConexion();
      }
    }
  }

  public void regBusAudDPI() {
    Aname.setText("");
    String dpi;
    dpi = AAdpi.getText();
    String sql = "SELECT * FROM `clientes` WHERE dpi='" + dpi + "'";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        String nombre;
        nombre = rs.getString(2) + " " + rs.getString(3);
        Aname.setText(nombre);
      }
      conexion.desconectarConexion();
      st.close();
      rs.close();

    } catch (Exception e) {
    }

  }

  public void numero(){
    try{
    int num =Integer.parseInt(TFnumber.getText());
    if(num>99999999){
      showErrorDialog("el numero no puede acceder los 8 digitos");
      TFnumber.setText("");
      }
    }catch (NumberFormatException e){
      if(TFnumber.getText() ==""){
        return;
      }
      showErrorDialog("solo se pueden ingresar numeros");
      TFnumber.setText("");

    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (CBestadoCivil != null) {
      CBestadoCivil.getItems().addAll(estadoCivilArray);

    }
    if (CBestadoProceso != null) {
      CBestadoProceso.getItems().addAll(estadoProceso);
    }
  }
}
