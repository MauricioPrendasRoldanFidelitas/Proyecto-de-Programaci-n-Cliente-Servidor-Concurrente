/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fvgames;

/**
 *
 * @author Melvin Prendas
 */
public class Cliente {
    private int id;
    private String nombre;
    private String cedula;
    private String contrasena;
    private double saldo;

    public Cliente(int id, String nombre, String cedula, String contrasena, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.contrasena = contrasena;
        this.saldo = saldo;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

