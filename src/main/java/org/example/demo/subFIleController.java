package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class subFIleController {
    @FXML private TextField filePathField;

    public void chooseFileButtonPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar documento");

        // Obtiene el Stage actual a través del TextField
        Stage stage = (Stage) filePathField.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }
    public  void uploadFileButtonPressed() {
        String filePath = filePathField.getText();
        if (!filePath.isEmpty()) {
            this.uploadDocument(filePath);
        }
    }

    private void uploadDocument(String filePath) {
        try {
            Conexion conexion = new Conexion();
            String sql = "INSERT INTO documentos (nombre, contenido) VALUES (?, ?)";
            PreparedStatement pstmt = conexion.establecerConexion().prepareStatement(sql);
            File file = new File(filePath);
            pstmt.setString(1, file.getName());
            InputStream inputStream = new FileInputStream(file);
            pstmt.setBinaryStream(2, inputStream);
            pstmt.executeUpdate();
            inputStream.close();
            pstmt.close();
            pstmt.close();
            System.out.println("Documento subido exitosamente a la base de datos.");
        } catch (Exception var10) {
            Exception ex = var10;
            ex.printStackTrace();
        }

    }
}
