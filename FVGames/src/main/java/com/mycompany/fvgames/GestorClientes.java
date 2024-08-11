/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fvgames;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class GestorClientes {
    private Connection connection;

    public GestorClientes() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean agregarCliente(String nombre, String apellidos, String cedula, String direccion, String email, String contrasena, double saldo) {
        String sql = "INSERT INTO Cliente (nombre, apellidos, cedula, direccion, email, contrasena, saldo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, cedula);
            statement.setString(4, direccion);
            statement.setString(5, email);
            statement.setString(6, contrasena);
            statement.setDouble(7, saldo);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Cliente autenticarCliente(String cedula, String contrasena) {
        String sql = "SELECT * FROM Cliente WHERE cedula = ? AND contrasena = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("cedula"), rs.getString("direccion"), rs.getString("email"), rs.getString("contrasena"), rs.getDouble("saldo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Autenticación fallida
    }

    public void recargarSaldo(int id, double monto) {
        String sql = "UPDATE Cliente SET saldo = saldo + ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarDatosCliente(int id, String nuevoNombre, String nuevaContrasena) {
        String sql = "UPDATE Cliente SET nombre = ?, contrasena = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaContrasena);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean eliminarCliente(int id) {
    String sql = "DELETE FROM Cliente WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);

        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public Cliente buscarClientePorCedula(String cedula) {
    String sql = "SELECT * FROM Cliente WHERE cedula = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, cedula);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            String apellidos = resultSet.getString("apellidos");
            String contrasena = resultSet.getString("contrasena");
            String direccion = resultSet.getString("direccion");
            String email = resultSet.getString("email");
            double saldo = resultSet.getDouble("saldo");

            return new Cliente(id, nombre, apellidos, cedula, direccion, email, contrasena, saldo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
}