package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.*;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
public class MenuController implements Initializable{
  @FXML
  private TextField TFuser;
  @FXML
  private PasswordField TFpasword;
  @FXML
  private TableView<Audiencias> Taudiencias;
  @FXML
  private TableColumn<Audiencias, String> columUbi;
  @FXML
  private TableColumn<Audiencias, String> columHora;
  @FXML
  private TableColumn<Audiencias, String> columFecha;
  @FXML
  private TableColumn<Audiencias, String> columDetalles;

  private ObservableList<Audiencias> items= FXCollections.observableArrayList();

  public void mostrarAudPendientes() {

  }


  public void iniciarSecion(ActionEvent event) {
    String user = TFuser.getText();
    String password = TFpasword.getText();
    String sql = "SELECT * FROM `usuarios` WHERE usuario='"+user+"'";
    try {
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      if (rs.next()) {
        String userBD = rs.getString(2);

        String passwordBD = rs.getString(3);

        if ((Objects.equals(userBD, TFuser.getText())) & (Objects.equals(TFpasword.getText(), passwordBD))) {
          conexion.desconectarConexion();
          st.close();
          openFXML("menuPrincipal","menu",event,"Aspecto");
        } else {
          JOptionPane.showMessageDialog(null, "la contraseña es incorrecta");
        }
      } else {
        JOptionPane.showMessageDialog(null, "El usuario es incorrecto");
        TFuser.setText("");
        TFpasword.setText("");
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "error al conectar ");
    }

  }

  public void openEdUs(ActionEvent event) throws IOException {
    //el ultimo dato es el nombre del css te los deje en null por que como no se si vas a usar
    //el mismo de menu.css o otro pero de hay solo escribre el nombre del archivo sin agregar .css
    //ya lo programe yo para que te lo agregue solo
    openFXML("editarUsuarios","Editar usuarios",event,"Aspecto");
  }

  public void openEdCl(ActionEvent event) throws IOException {
    openFXML("editarClientes","Editar clientes",event,"Aspecto");
  }

  public void openEdPr(ActionEvent event) throws IOException {
    openFXML("editarCasos","Editar casos",event,"Aspecto");
  }

  public void openReUs(ActionEvent event) throws IOException {
    openFXML("registroUsuario","Registro de usuarios",event,"Aspecto");
  }

  public void openReCl(ActionEvent event) throws IOException {
    openFXML("registroClientes","Registro de clientes",event,"Aspecto");
  }

  public void openRePr(ActionEvent event) throws IOException {
    openFXML("registroCasos","Registro de casos",event,"Aspecto");
  }

  public void openMainMenu(ActionEvent event) throws IOException {
    openFXML("menuPrincipal","Menu principal",event,"Aspecto");
  }
  public void openSubFil(ActionEvent event) throws IOException{
    openFXML("subArchivo","Plantillas",event,"Aspecto");
  }
  public void openAu(ActionEvent event) throws IOException {
    openFXML("agregarAudiencia", "agregar audiencia",event,"Aspecto");
  }
  public void openEdAud(ActionEvent event) throws IOException {
    openFXML("editarAudiencia", "editar audiencia", event,"Aspecto");
  }
  public void exportToExcel(){
    ExportToExcel exportToExcel= new ExportToExcel();
    exportToExcel.exportToExcel();
  }
  public  void openSubFile(ActionEvent event) throws IOException {
    openFXML("upload","subir plantillas",event,"Aspecto");
  }
  public  void openPlan(ActionEvent event) throws IOException {
    openFXML("GestionDocumentos","Plantillas",event,"Aspecto");
  }

  public void openCreditos(ActionEvent event) throws IOException {
    openFXML("pantallaCompleta","Creditos",event,"creditos");
  }

  public void openFXML(String fxml, String title, ActionEvent event, String nombreDelCSS) throws IOException {
    fxml = fxml + ".fxml";
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle(title);
    if (nombreDelCSS != null) {
      nombreDelCSS += ".css";
      String css = this.getClass().getResource(nombreDelCSS).toExternalForm();
      scene.getStylesheets().add(css);
    }
    stage.setScene(scene);
    stage.setResizable(false); // No permitir redimensionar la ventana
    stage.show();
  }


  public void tablaNoti(){
    items.clear();
    String sql = "SELECT ubicacion_de_la_audiencia, fecha_de_audiencia, hora_de_la_audiencia, detalles FROM audiencias" +
            " WHERE fecha_de_audiencia >= CURDATE();";
    try{
      Conexion conexion = new Conexion();
      Statement st = conexion.establecerConexion().createStatement();
      ResultSet rs = st.executeQuery(sql);
      String ubicacion, fecha,hora,detalles;
      while(rs.next()){
        ubicacion=rs.getString(1).trim();
        fecha=rs.getString(2).trim();
        hora=rs.getString(3).trim();
        detalles=rs.getString(4).trim();
        this.columUbi.setCellValueFactory(new PropertyValueFactory<> ("ubicacion"));
        this.columFecha.setCellValueFactory(new PropertyValueFactory<> ("fecha"));
        this.columHora.setCellValueFactory(new PropertyValueFactory<> ("hora"));
        this.columDetalles.setCellValueFactory(new PropertyValueFactory<> ("detalles"));
        items.add(new Audiencias("",ubicacion, fecha, hora, detalles));
        this.Taudiencias.setItems(items);
      }
      st.close();
      rs.close();}catch(Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    if(Taudiencias!= null){
      tablaNoti();

    }
  }
}