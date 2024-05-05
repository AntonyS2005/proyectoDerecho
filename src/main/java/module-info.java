module org.example.demo {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires java.sql;
  requires org.apache.poi.poi;
  requires org.apache.poi.ooxml;


  opens org.example.demo to javafx.fxml;
  exports org.example.demo;
}