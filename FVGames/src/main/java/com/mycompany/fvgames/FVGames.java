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
        GestorClientes gestor = new GestorClientes();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Autenticar Cliente");
            System.out.println("3. Recargar Saldo");
            System.out.println("4. Modificar Datos Cliente");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el newline

            switch (opcion) {
                case 1:
                    System.out.println("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Cédula: ");
                    String cedula = scanner.nextLine();
                    System.out.println("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    System.out.println("Saldo Inicial: ");
                    double saldo = scanner.nextDouble();
                    gestor.registrarCliente(new Cliente(0, nombre, cedula, contrasena, saldo));
                    break;
                case 2:
                    System.out.println("Cédula: ");
                    cedula = scanner.nextLine();
                    System.out.println("Contraseña: ");
                    contrasena = scanner.nextLine();
                    Cliente cliente = gestor.autenticarCliente(cedula, contrasena);
                    if (cliente != null) {
                        System.out.println("Bienvenido " + cliente.getNombre());
                    } else {
                        System.out.println("Credenciales incorrectas.");
                    }
                    break;
                case 3:
                    System.out.println("ID Cliente: ");
                    int id = scanner.nextInt();
                    System.out.println("Monto a recargar: ");
                    double monto = scanner.nextDouble();
                    gestor.recargarSaldo(id, monto);
                    break;
                case 4:
                    System.out.println("ID Cliente: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consumir el newline
                    System.out.println("Nuevo Nombre: ");
                    nombre = scanner.nextLine();
                    System.out.println("Nueva Contraseña: ");
                    contrasena = scanner.nextLine();
                    gestor.modificarDatosCliente(id, nombre, contrasena);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
