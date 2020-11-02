package com.example.edmad.firebasemvp.Entidades;

import java.io.Serializable;

public class LocacionEntity implements Serializable {
    private String nombreLocacion;
    private String ubicacion;
    private double latitud;
    private double longitud;
    private String Imagen;

    public LocacionEntity() {
    }

    public String getNombreLocacion() {
        return nombreLocacion;
    }

    public void setNombreLocacion(String nombreLocacion) {
        this.nombreLocacion = nombreLocacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}