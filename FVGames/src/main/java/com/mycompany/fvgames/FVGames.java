/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fvgames;
import java.util.Scanner;

/**
 *
 * @author Melvin Prendas
 */
public class FVGames {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorClientes gestorClientes = new GestorClientes();
        GestorProductos gestorProductos = new GestorProductos();
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    menuClientes(scanner, gestorClientes);
                    break;
                case 2:
                    menuProductos(scanner, gestorProductos);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcion != 3);
    }

    public static void menuClientes(Scanner scanner, GestorClientes gestorClientes) {
        int opcionClientes;
        do {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Buscar Cliente");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            opcionClientes = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcionClientes) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellidos: ");
                    String apellidos = scanner.nextLine();
                    System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();
                    System.out.print("Direccion: ");
                    String direccion = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    System.out.print("Saldo: ");
                    double saldo = scanner.nextDouble();
                    scanner.nextLine();  // Limpiar el buffer

                    boolean clienteAgregado = gestorClientes.agregarCliente(nombre, apellidos, cedula, direccion, email, contrasena, saldo);
                    if (clienteAgregado) {
                        System.out.println("Cliente agregado exitosamente.");
                    } else {
                        System.out.println("Error al agregar cliente.");
                    }
                    break;

                case 2:
                    System.out.print("Cédula del cliente a buscar: ");
                    String cedulaBuscar = scanner.nextLine();
                    Cliente cliente = gestorClientes.buscarClientePorCedula(cedulaBuscar);
                    if (cliente != null) {
                        System.out.println("Cliente encontrado: " + cliente.getNombre() + " con saldo: " + cliente.getSaldo());
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Cédula del cliente a actualizar: ");
                    String cedulaActualizar = scanner.nextLine();
                    Cliente clienteActualizar = gestorClientes.buscarClientePorCedula(cedulaActualizar);
                    if (clienteActualizar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo apellido: ");
                        String nuevoApellido = scanner.nextLine();
                        System.out.print("Nueva direccion: ");
                        String nuevaDireccion = scanner.nextLine();
                        System.out.print("Nueva contraseña: ");
                        String nuevaContrasena = scanner.nextLine();
                        System.out.print("Nuevo email: ");
                        String nuevoEmail = scanner.nextLine();
                        System.out.print("Nuevo saldo: ");
                        double nuevoSaldo = scanner.nextDouble();
                        scanner.nextLine();  // Limpiar el buffer

                        boolean clienteActualizado = gestorClientes.agregarCliente(nuevoNombre, nuevoApellido, cedulaActualizar, nuevaDireccion, nuevoEmail, nuevaContrasena, nuevoSaldo);
                        if (clienteActualizado) {
                            System.out.println("Cliente actualizado exitosamente.");
                        } else {
                            System.out.println("Error al actualizar cliente.");
                        }
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Cédula del cliente a eliminar: ");
                    String cedulaEliminar = scanner.nextLine();
                    Cliente clienteEliminar = gestorClientes.buscarClientePorCedula(cedulaEliminar);
                    if (clienteEliminar != null) {
                        boolean clienteEliminado = gestorClientes.eliminarCliente(clienteEliminar.getId());
                        if (clienteEliminado) {
                            System.out.println("Cliente eliminado exitosamente.");
                        } else {
                            System.out.println("Error al eliminar cliente.");
                        }
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("Volviendo al Menú Principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcionClientes != 5);
    }

    public static void menuProductos(Scanner scanner, GestorProductos gestorProductos) {
        int opcionProductos;
        do {
            System.out.println("\n--- Gestión de Productos ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            opcionProductos = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcionProductos) {
                case 1:
                    System.out.print("Nombre del producto: ");
                    String nombreProducto = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer

                    boolean productoAgregado = gestorProductos.agregarProducto(nombreProducto, descripcion, precio, cantidad);
                    if (productoAgregado) {
                        System.out.println("Producto agregado exitosamente.");
                    } else {
                        System.out.println("Error al agregar producto.");
                    }
                    break;

                case 2:
                    System.out.print("ID del producto a buscar: ");
                    int idProductoBuscar = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer

                    Producto producto = gestorProductos.buscarProductoPorId(idProductoBuscar);
                    if (producto != null) {
                        System.out.println("Producto encontrado: " + producto.getNombre() + " con cantidad: " + producto.getStock());
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("ID del producto a actualizar: ");
                    int idProductoActualizar = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer
                    Producto productoActualizar = gestorProductos.buscarProductoPorId(idProductoActualizar);
                    if (productoActualizar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombreProducto = scanner.nextLine();
                        System.out.print("Nueva descripción: ");
                        String nuevaDescripcion = scanner.nextLine();
                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = scanner.nextDouble();
                        System.out.print("Nueva cantidad: ");
                        int nuevaCantidad = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer

                        boolean productoActualizado = gestorProductos.actualizarProducto(idProductoActualizar, nuevoNombreProducto, nuevaDescripcion, nuevoPrecio, nuevaCantidad);
                        if (productoActualizado) {
                            System.out.println("Producto actualizado exitosamente.");
                        } else {
                            System.out.println("Error al actualizar producto.");
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("ID del producto a eliminar: ");
                    int idProductoEliminar = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer

                    Producto productoEliminar = gestorProductos.buscarProductoPorId(idProductoEliminar);
                    if (productoEliminar != null) {
                        boolean productoEliminado = gestorProductos.eliminarProducto(idProductoEliminar);
                        if (productoEliminado) {
                            System.out.println("Producto eliminado exitosamente.");
                        } else {
                            System.out.println("Error al eliminar producto.");
                        }
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("Volviendo al Menú Principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcionProductos != 5);
    }
}