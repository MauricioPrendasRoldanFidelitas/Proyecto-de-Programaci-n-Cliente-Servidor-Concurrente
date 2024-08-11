/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fvgames;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Melvin Prendas
 */
public class CarritoCompras {
    private List<Producto> productos;

    public CarritoCompras() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }

    public void eliminarProducto(int id) {
        Producto productoAEliminar = null;

        for (Producto producto : productos) {
            if (producto.getId() == id) {
                productoAEliminar = producto;
                break;
            }
        }

        if (productoAEliminar != null) {
            productos.remove(productoAEliminar);
            System.out.println("Producto eliminado del carrito: " + productoAEliminar.getNombre());
        } else {
            System.out.println("Producto no encontrado en el carrito.");
        }
    }

    public void mostrarCarrito() {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacio.");
        } else {
            System.out.println("\n--- Carrito de Compras ---");
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getId() + " | Nombre: " + producto.getNombre() + " | Precio: " + producto.getPrecio() + " | Cantidad: " + producto.getStock());
            }
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio() * producto.getStock(); 
        }
        return total;
    }

    public void vaciarCarrito() {
        productos.clear();
        System.out.println("El carrito ha sido vaciado.");
    }
}