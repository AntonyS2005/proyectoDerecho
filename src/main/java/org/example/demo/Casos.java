package org.example.demo;

public class Casos {
  private String cliente;
  private String tipo;
  private String costo;
  private String saldoPendiente;
  private String estadoDelProceso;
  private String Detalles;

  public Casos(String cliente,String tipo,String costo,String saldoPendiente,String estadoDelProceso,String detalles) {
    this.cliente=cliente;
    this.tipo=tipo;
    this.costo=costo;
    this.saldoPendiente=saldoPendiente;
    this.estadoDelProceso=estadoDelProceso;
    this.Detalles=detalles;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getSaldoPendiente() {
    return saldoPendiente;
  }

  public void setSaldoPendiente(String saldoPendiente) {
    this.saldoPendiente = saldoPendiente;
  }

  public String getEstadoDelProceso() {
    return estadoDelProceso;
  }

  public void setEstadoDelProceso(String estadoDelProceso) {
    this.estadoDelProceso = estadoDelProceso;
  }

  public String getDetalles() {
    return Detalles;
  }

  public void setDetalles(String detalles) {
    Detalles = detalles;
  }

  public String getCosto() {
    return costo;
  }

  public void setCosto(String costo) {
    this.costo = costo;
  }
}
