/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames.controlador;

import com.mycompany.servidorfvgames.dao.ProductoDAO;
import com.mycompany.servidorfvgames.modelo.Producto;
import java.util.List;

/**
 *
 * @author Melvin Prendas
 */
public class ProductoControlador {
    private ProductoDAO productoDAO;

    public ProductoControlador() {
        this.productoDAO = new ProductoDAO();
    }

    public boolean agregarProducto(Producto producto) {
        return productoDAO.insertar(producto);
    }

    public Producto buscarProductoPorId(int id) {
        return productoDAO.buscarPorId(id);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoDAO.actualizar(producto);
    }

    public boolean eliminarProducto(int id) {
        return productoDAO.eliminar(id);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.obtenerTodos();
    }
    
}