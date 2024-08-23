/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames;

import com.mycompany.servidorfvgames.dao.ProductoDAO;
import com.mycompany.fvgames.modelo.Producto;
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

    public void agregarProducto(int idProducto, int cantidad) throws Exception {
        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = productoDAO.buscarPorId(idProducto);

        if (producto == null) {
            throw new Exception("Producto no encontrado con el ID proporcionado.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        producto.setStock(cantidad);
        productos.add(producto);
    }

    // Otros métodos relacionados al carrito...

    public List<Producto> obtenerProductos() {
        return productos;
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