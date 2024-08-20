/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames.dao;
import com.mycompany.servidorfvgames.modelo.Paquete;
import com.mycompany.servidorfvgames.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class PaqueteDAO {
    private Connection conexion;

    public PaqueteDAO() {
        this.conexion = DatabaseConnection.getConexion();
    }

    public void crearPaquete(Paquete paquete) throws SQLException {
        String sqlPaquete = "INSERT INTO Paquete (descuento, precio_final) VALUES (?, ?)";
        String sqlProductoPaquete = "INSERT INTO Paquete_Producto (paquete_id, producto_id) VALUES (?, ?)";

        try (PreparedStatement stmtPaquete = conexion.prepareStatement(sqlPaquete, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmtPaquete.setDouble(1, paquete.getDescuento());
            stmtPaquete.setDouble(2, paquete.getPrecioFinal());
            stmtPaquete.executeUpdate();

            // Obtener el ID generado para el paquete
            ResultSet generatedKeys = stmtPaquete.getGeneratedKeys();
            if (generatedKeys.next()) {
                int paqueteId = generatedKeys.getInt(1);

                // Insertar cada producto en la tabla Paquete_Producto
                try (PreparedStatement stmtProductoPaquete = conexion.prepareStatement(sqlProductoPaquete)) {
                    for (Producto producto : paquete.getProductos()) {
                        stmtProductoPaquete.setInt(1, paqueteId);
                        stmtProductoPaquete.setInt(2, producto.getId());
                        stmtProductoPaquete.addBatch();
                    }
                    stmtProductoPaquete.executeBatch();
                }
            }
        }
    }

    public Paquete obtenerPaquetePorId(int id) throws SQLException {
        String sqlPaquete = "SELECT * FROM Paquete WHERE id = ?";
        String sqlProductos = "SELECT p.id, p.nombre, p.categoria, p.precio, p.stock FROM Producto p " +
                              "INNER JOIN Paquete_Producto pp ON p.id = pp.producto_id WHERE pp.paquete_id = ?";

        try (PreparedStatement stmtPaquete = conexion.prepareStatement(sqlPaquete);
             PreparedStatement stmtProductos = conexion.prepareStatement(sqlProductos)) {
            
            stmtPaquete.setInt(1, id);
            ResultSet rsPaquete = stmtPaquete.executeQuery();

            if (rsPaquete.next()) {
                double descuento = rsPaquete.getDouble("descuento");
                double precioFinal = rsPaquete.getDouble("precio_final");

                // Obtener los productos del paquete
                List<Producto> productos = new ArrayList<>();
                stmtProductos.setInt(1, id);
                ResultSet rsProductos = stmtProductos.executeQuery();
                while (rsProductos.next()) {
                    String nombre = rsProductos.getString("nombre");
                    String categoria = rsProductos.getString("categoria");
                    double precio = rsProductos.getDouble("precio");
                    int stock = rsProductos.getInt("stock");

                    Producto producto = new Producto(nombre, categoria, precio, stock);
                    productos.add(producto);
                }

                return new Paquete(productos, descuento);
            } else {
                return null;
            }
        }
    }

    public List<Paquete> obtenerTodosLosPaquetes() throws SQLException {
        String sqlPaquetes = "SELECT * FROM Paquete";
        List<Paquete> paquetes = new ArrayList<>();

        try (PreparedStatement stmtPaquetes = conexion.prepareStatement(sqlPaquetes);
             ResultSet rsPaquetes = stmtPaquetes.executeQuery()) {

            while (rsPaquetes.next()) {
                int id = rsPaquetes.getInt("id");
                double descuento = rsPaquetes.getDouble("descuento");
                double precioFinal = rsPaquetes.getDouble("precio_final");

                Paquete paquete = obtenerPaquetePorId(id);
                if (paquete != null) {
                    paquetes.add(paquete);
                }
            }
        }
        return paquetes;
    }

    public void actualizarPaquete(Paquete paquete) throws SQLException {
        String sqlPaquete = "UPDATE Paquete SET descuento = ?, precio_final = ? WHERE id = ?";
        String sqlEliminarProductos = "DELETE FROM Paquete_Producto WHERE paquete_id = ?";
        String sqlProductoPaquete = "INSERT INTO Paquete_Producto (paquete_id, producto_id) VALUES (?, ?)";

        try (PreparedStatement stmtPaquete = conexion.prepareStatement(sqlPaquete);
             PreparedStatement stmtEliminarProductos = conexion.prepareStatement(sqlEliminarProductos);
             PreparedStatement stmtProductoPaquete = conexion.prepareStatement(sqlProductoPaquete)) {

            // Actualizar el paquete
            stmtPaquete.setDouble(1, paquete.getDescuento());
            stmtPaquete.setDouble(2, paquete.getPrecioFinal());
            stmtPaquete.setInt(3, paquete.getId());
            stmtPaquete.executeUpdate();

            // Eliminar los productos actuales del paquete
            stmtEliminarProductos.setInt(1, paquete.getId());
            stmtEliminarProductos.executeUpdate();

            // Insertar los nuevos productos
            for (Producto producto : paquete.getProductos()) {
                stmtProductoPaquete.setInt(1, paquete.getId());
                stmtProductoPaquete.setInt(2, producto.getId());
                stmtProductoPaquete.addBatch();
            }
            stmtProductoPaquete.executeBatch();
        }
    }

    public void eliminarPaquete(int id) throws SQLException {
        String sqlEliminarProductos = "DELETE FROM Paquete_Producto WHERE paquete_id = ?";
        String sqlEliminarPaquete = "DELETE FROM Paquete WHERE id = ?";

        try (PreparedStatement stmtEliminarProductos = conexion.prepareStatement(sqlEliminarProductos);
             PreparedStatement stmtEliminarPaquete = conexion.prepareStatement(sqlEliminarPaquete)) {

            // Eliminar los productos del paquete
            stmtEliminarProductos.setInt(1, id);
            stmtEliminarProductos.executeUpdate();

            // Eliminar el paquete
            stmtEliminarPaquete.setInt(1, id);
            stmtEliminarPaquete.executeUpdate();
        }
    }
}
