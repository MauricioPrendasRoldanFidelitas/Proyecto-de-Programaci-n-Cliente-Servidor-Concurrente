/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidorfvgames.controlador;
import com.mycompany.servidorfvgames.dao.ClienteDAO;
import com.mycompany.servidorfvgames.modelo.Cliente;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class ClienteControlador {
    private ClienteDAO clienteDAO;

    public ClienteControlador() {
        this.clienteDAO = new ClienteDAO();
    }

    public boolean agregarCliente(Cliente cliente) {
        return clienteDAO.insertar(cliente);
    }

    public Cliente buscarClientePorCedula(String cedula) {
        return clienteDAO.buscarPorCedula(cedula);
    }

    public boolean actualizarCliente(Cliente cliente) {
        return clienteDAO.actualizar(cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminar(id);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.obtenerTodos();
    }
}
