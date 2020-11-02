package com.example.edmad.firebasemvp.Modelo;

public class UserModel {
    private String email;
    private  String nick;
    private String nombre;

    public UserModel(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return nick;
    }

    public void setUsuario(String usuario) {
        this.nick = usuario;
    }
}
