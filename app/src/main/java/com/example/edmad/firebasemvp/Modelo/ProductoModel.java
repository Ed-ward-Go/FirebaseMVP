package com.example.edmad.firebasemvp.Modelo;

public class ProductoModel {
    private String nombreProducto;
    private float precioProducto;

    public ProductoModel(){}

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }
}
