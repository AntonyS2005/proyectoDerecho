package org.example.demo;

public class clientes {
  String nombre;
  String apellido;
  String estadoCivil;
  String numero;
  String fechaNacimiento;
  String dpi;

  public clientes(String dpi, String fechaNacimiento, String numero, String apellido, String estadoCivil, String nombre) {
    this.dpi = dpi;
    this.fechaNacimiento = fechaNacimiento;
    this.numero = numero;
    this.apellido = apellido;
    this.estadoCivil = estadoCivil;
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDpi() {
    return dpi;
  }

  public void setDpi(String dpi) {
    this.dpi = dpi;
  }

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getEstadoCivil() {
    return estadoCivil;
  }

  public void setEstadoCivil(String estadoCivil) {
    this.estadoCivil = estadoCivil;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }
}
