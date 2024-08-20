/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames.dao;
import com.mycompany.servidorfvgames.CarritoCompras;
import com.mycompany.servidorfvgames.modelo.Compra;
import com.mycompany.servidorfvgames.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class CompraDAO {
    private Connection conexion;

    public CompraDAO() {
        this.conexion = DatabaseConnection.getConexion();
    }

    public void agregarCompra(Compra compra) throws SQLException {
        String query = "INSERT INTO Compra (id_cliente, id_producto, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, compra.getIdCliente());
            stmt.setInt(2, compra.getIdProducto());
            stmt.setInt(3, compra.getCantidad());
            stmt.setTimestamp(4, new Timestamp(compra.getFecha().getTime()));
            stmt.executeUpdate();
        }
    }

    public Compra obtenerCompraPorId(int id) throws SQLException {
        String query = "SELECT * FROM Compra WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Compra(rs.getInt("id_cliente"),
                        rs.getInt("id_producto"), rs.getInt("cantidad"));
            }
        }
        return null;
    }

    public List<Compra> obtenerTodasLasCompras() throws SQLException {
        String query = "SELECT * FROM Compra";
        List<Compra> compras = new ArrayList<>();
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                compras.add(new Compra(rs.getInt("id_cliente"),
                        rs.getInt("id_producto"), rs.getInt("cantidad")));
            }
        }
        return compras;
    }

    public void eliminarCompra(int id) throws SQLException {
        String query = "DELETE FROM Compra WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public void realizarCompra(int idCliente, CarritoCompras carrito) throws SQLException {
        String sqlCompra = "INSERT INTO Compra (id_cliente, id_producto, cantidad, fecha) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlCompra)) {

            for (Producto producto : carrito.obtenerProductos()) {
                stmt.setInt(1, idCliente);
                stmt.setInt(2, producto.getId());
                stmt.setInt(3, producto.getStock());
                stmt.addBatch(); 
            }

            stmt.executeBatch(); 
        }
    }
}
