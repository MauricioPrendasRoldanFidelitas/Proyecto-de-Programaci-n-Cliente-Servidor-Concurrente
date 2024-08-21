/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.clientefvgames;
import com.mycompany.clientefvgames.vista.MainFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Melvin Prendas
 */
public class ClienteFVGames {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClienteFVGames() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerProductos() {
        try {
            out.writeObject("GET_PRODUCTOS");
            List<Producto> productos = (List<Producto>) in.readObject();
            return productos;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cerrarConexion() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
}
    
}