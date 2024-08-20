/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidorfvgames;
import com.mycompany.servidorfvgames.dao.ClienteDAO;
import com.mycompany.servidorfvgames.dao.CompraDAO;
import com.mycompany.servidorfvgames.dao.PaqueteDAO;
import com.mycompany.servidorfvgames.dao.ProductoDAO;
import com.mycompany.servidorfvgames.modelo.Cliente;
import com.mycompany.servidorfvgames.modelo.Compra;
import com.mycompany.servidorfvgames.modelo.Paquete;
import com.mycompany.servidorfvgames.modelo.Producto;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Melvin Prendas
 */
public class ServidorFVGames {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado en el puerto 12345...");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> {
                        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                            ClienteDAO clienteDAO = new ClienteDAO();
                            ProductoDAO productoDAO = new ProductoDAO();
                            CompraDAO compraDAO = new CompraDAO();
                            CarritoCompras carrito = new CarritoCompras();
                            PaqueteDAO paqueteDAO = new PaqueteDAO();

                            String request;
                            while ((request = (String) in.readObject()) != null) {
                                switch (request) {
                                    case "OBTENER_CLIENTES":
                                        List<Cliente> clientes = clienteDAO.obtenerTodos();
                                        out.writeObject(clientes);
                                        break;

                                    case "OBTENER_PRODUCTOS":
                                        List<Producto> productos = productoDAO.obtenerTodos();
                                        out.writeObject(productos);
                                        break;

                                    case "OBTENER_COMPRAS":
                                        List<Compra> compras;
                                    try {
                                        compras = compraDAO.obtenerTodasLasCompras();
                                        out.writeObject(compras);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        break;

                                    case "AGREGAR_CLIENTE":
                                        System.out.println("Esperando recibir objeto Cliente...");
                                        Cliente nuevoCliente = (Cliente) in.readObject();
                                        clienteDAO.insertar(nuevoCliente);
                                        out.writeObject("Cliente agregado con éxito");
                                        System.out.println("Objeto Cliente recibido: " + nuevoCliente);
                                        break;

                                    case "ELIMINAR_CLIENTE":
                                        int idCliente = in.readInt();
                                        clienteDAO.eliminar(idCliente);
                                        out.writeObject("Cliente eliminado con éxito");
                                        break;

                                    case "AGREGAR_PRODUCTO":
                                        Producto nuevoProducto = (Producto) in.readObject();
                                        productoDAO.insertar(nuevoProducto);
                                        out.writeObject("Producto agregado con éxito");
                                        break;

                                    case "ELIMINAR_PRODUCTO":
                                        int idProducto = in.readInt();
                                        productoDAO.eliminar(idProducto);
                                        out.writeObject("Producto eliminado con éxito");
                                        break;

                                    case "AGREGAR_COMPRA":
                                        Compra nuevaCompra = (Compra) in.readObject();
                                    {
                                        try {
                                            compraDAO.agregarCompra(nuevaCompra);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                        out.writeObject("Compra agregada con éxito");
                                        break;


                                    case "ELIMINAR_COMPRA":
                                        int idCompra = in.readInt();
                                    {
                                        try {
                                            compraDAO.eliminarCompra(idCompra);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                        out.writeObject("Compra eliminada con éxito");
                                        break;


                                    case "AGREGAR_AL_CARRITO":
                                        int idProductoCarrito = in.readInt();
                                        int cantidadCarrito = in.readInt();
                                    {
                                        try {
                                            carrito.agregarProducto(idProductoCarrito, cantidadCarrito);
                                        } catch (Exception ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                        out.writeObject("Producto agregado al carrito.");
                                        break;


                                    case "ELIMINAR_DEL_CARRITO":
                                        int idProductoEliminarCarrito = in.readInt();
                                        carrito.eliminarProducto(idProductoEliminarCarrito);
                                        out.writeObject("Producto eliminado del carrito.");
                                        break;

                                    case "VER_CARRITO":
                                        List<Producto> productosCarrito = carrito.obtenerProductos();
                                        out.writeObject(productosCarrito);
                                        break;

                                    case "REALIZAR_COMPRA":
                                        int idClienteCompra = in.readInt();
                                    {
                                        try {
                                            compraDAO.realizarCompra(idClienteCompra, carrito);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                        out.writeObject("Compra realizada con éxito.");
                                        break;

                                       
                                    case "CREAR_PAQUETE":
                                        try {
                                            Paquete nuevoPaquete = (Paquete) in.readObject();
                                            paqueteDAO.crearPaquete(nuevoPaquete);
                                            out.writeObject("Paquete creado con éxito");
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                            out.writeObject("Error al crear el paquete: " + ex.getMessage());
                                        } catch (Exception ex) {
                                            Logger.getLogger(ServidorFVGames.class.getName()).log(Level.SEVERE, null, ex);
                                            out.writeObject("Error inesperado: " + ex.getMessage());
                                        }
                                        break;


                                    case "SALIR":
                                        clientSocket.close();
                                        break;

                                    default:
                                        out.writeObject("Solicitud no reconocida.");
                                }
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println("Error al procesar la solicitud del cliente: " + e.getMessage());
                        }
                    }).start();

                } catch (IOException e) {
                    System.out.println("Error al aceptar la conexión del cliente: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("No se pudo iniciar el servidor: " + e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("El servidor se ha cerrado correctamente.");
                } catch (IOException e) {
                    System.out.println("Error al cerrar el servidor: " + e.getMessage());
                }
            }
        }
    }
}
