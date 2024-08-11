/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fvgames;

import java.sql.*;

/**
 *
 * @author Melvin Prendas
 */
public class GestorProductos {
    private Connection connection;
    
    public boolean agregarProducto(String nombre, String descripcion, double precio, int cantidad) {
    String sql = "INSERT INTO Producto (nombre, descripcion, precio, cantidad) VALUES (?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setDouble(3, precio);
        statement.setInt(4, cantidad);

        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
 public Producto buscarProductoPorId(int id) {
    String sql = "SELECT * FROM Producto WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String nombre = resultSet.getString("nombre");
            String descripcion = resultSet.getString("descripcion");
            double precio = resultSet.getDouble("precio");
            int cantidad = resultSet.getInt("cantidad");

            return new Producto(id, nombre, descripcion, precio, cantidad);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
public boolean actualizarProducto(int id, String nombre, String descripcion, double precio, int cantidad) {
    String sql = "UPDATE Producto SET nombre = ?, descripcion = ?, precio = ?, cantidad = ? WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setDouble(3, precio);
        statement.setInt(4, cantidad);
        statement.setInt(5, id);

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean eliminarProducto(int id) {
    String sql = "DELETE FROM Producto WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);

        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
