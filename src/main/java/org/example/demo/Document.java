package org.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Document {
  private final SimpleIntegerProperty id;
  private final SimpleStringProperty titulo;
  private final SimpleStringProperty archivo;

  public Document(int id, String titulo, String archivo) {
    this.id = new SimpleIntegerProperty(id);
    this.titulo = new SimpleStringProperty(titulo);
    this.archivo = new SimpleStringProperty(archivo);
  }

  public int getId() {
    return id.get();
  }

  public SimpleIntegerProperty idProperty() {
    return id;
  }

  public String getTitulo() {
    return titulo.get();
  }

  public SimpleStringProperty tituloProperty() {
    return titulo;
  }

  public String getArchivo() {
    return archivo.get();
  }

  public SimpleStringProperty archivoProperty() {
    return archivo;
  }
}
