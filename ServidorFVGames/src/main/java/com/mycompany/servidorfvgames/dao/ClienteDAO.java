/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames.dao;
import com.mycompany.fvgames.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class ClienteDAO {
    private Connection conexion;

    public ClienteDAO() {
        this.conexion = DatabaseConnection.getConexion();
    }

    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, apellido, cedula, direccion, email, contrasena, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCedula());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getContrasena());
            ps.setDouble(7, cliente.getSaldo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente buscarPorCedula(String cedula) {
        String sql = "SELECT * FROM Cliente WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    rs.getDouble("saldo")
                );
                cliente.setId(rs.getInt("id"));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, apellido = ?, direccion = ?, email = ?, contrasena = ?, saldo = ? WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getContrasena());
            ps.setDouble(6, cliente.getSaldo());
            ps.setString(7, cliente.getCedula());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> obtenerTodos() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("cedula"),
                    rs.getString("direccion"),
                    rs.getString("email"),
                    rs.getString("contrasena"),
                    rs.getDouble("saldo")
                );
                cliente.setId(rs.getInt("id"));
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }
}