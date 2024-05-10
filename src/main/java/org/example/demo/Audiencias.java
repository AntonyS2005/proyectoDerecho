package org.example.demo;

public class Audiencias {
    private String id;
    private String ubicacion;
    private String fecha;
    private String hora;
    private String detalles;

    public Audiencias(){

    }

    public Audiencias(String id,String ubicacion, String fecha, String hora, String detalles){
        this.id=id;
        this.ubicacion=ubicacion;
        this.fecha=fecha;
        this.hora=hora;
        this.detalles=detalles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
