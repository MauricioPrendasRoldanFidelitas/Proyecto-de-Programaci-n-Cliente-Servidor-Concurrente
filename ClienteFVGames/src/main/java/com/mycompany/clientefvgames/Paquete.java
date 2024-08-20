/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientefvgames;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Melvin Prendas
 */
public class Paquete implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private List<Producto> productos;
    private double descuento; // En porcentaje, por defecto 15%
    private double precioFinal;
    
    public Paquete(List<Producto> productos, double descuento) {
        this.productos = productos;
        this.descuento = descuento == 0 ? 15 : descuento;
        this.precioFinal = calcularPrecioFinal();
    }
    
    private double calcularPrecioFinal() {
        double sumaPrecios = productos.stream()
                                      .mapToDouble(Producto::getPrecio)
                                      .sum();
        return sumaPrecios * (1 - (descuento / 100));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    
}
