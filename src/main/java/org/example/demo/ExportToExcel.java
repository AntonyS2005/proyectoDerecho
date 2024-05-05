package org.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class ExportToExcel {

  private static final String FILE_NAME = "datos_guardados.xlsx";

  public void exportToExcel() {
    try {
      Conexion conexion = new Conexion(); // Crear una instancia de la clase Conexion
      Connection conn = conexion.establecerConexion(); // Obtener la conexión usando el método establecerConexion()

      Workbook workbook = new XSSFWorkbook();
      exportarTabla(conn, workbook, "usuarios");
      exportarTabla(conn, workbook, "registro_de_casos");
      exportarTabla(conn, workbook, "clientes");
      exportarTabla(conn, workbook, "audiencias");

      FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
      workbook.write(fileOut);
      fileOut.close();
      workbook.close();

      conn.close();

      System.out.println("Datos exportados a " + FILE_NAME + " correctamente.");
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  private void exportarTabla(Connection conn, Workbook workbook, String tabla) throws SQLException {
    Sheet sheet = workbook.createSheet(tabla);

    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tabla);
    ResultSet rs = stmt.executeQuery();

    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();

    Row headerRow = sheet.createRow(0);
    for (int i = 1; i <= columnCount; i++) {
      Cell cell = headerRow.createCell(i - 1);
      cell.setCellValue(metaData.getColumnName(i));
    }

    int rowNum = 1;
    while (rs.next()) {
      Row row = sheet.createRow(rowNum++);
      for (int i = 1; i <= columnCount; i++) {
        Cell cell = row.createCell(i - 1);
        cell.setCellValue(rs.getString(i));
      }
    }

    rs.close();
    stmt.close();
  }
}
