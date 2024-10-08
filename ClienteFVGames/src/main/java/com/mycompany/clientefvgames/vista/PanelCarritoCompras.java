/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.clientefvgames.vista;

import com.mycompany.fvgames.modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class PanelCarritoCompras extends JPanel {
    private JTextField idProductoField;
    private JTextField cantidadField;
    private JButton agregarProductoButton;
    private JButton verCarritoButton;
    private JButton calcularTotalButton;
    private JButton eliminarProductoButton;
    private JButton vaciarCarritoButton;
    private JTextArea carritoArea;

    public PanelCarritoCompras() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JLabel idProductoLabel = new JLabel("ID Producto:");
        idProductoField = new JTextField();
        
        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadField = new JTextField();
        
        agregarProductoButton = new JButton("Agregar al Carrito");
        verCarritoButton = new JButton("Ver Carrito");
        calcularTotalButton = new JButton("Calcular Total");
        eliminarProductoButton = new JButton("Eliminar Producto");
        vaciarCarritoButton = new JButton("Vaciar Carrito");

        inputPanel.add(idProductoLabel);
        inputPanel.add(idProductoField);
        inputPanel.add(cantidadLabel);
        inputPanel.add(cantidadField);
        inputPanel.add(new JLabel());
        inputPanel.add(agregarProductoButton);

        carritoArea = new JTextArea();
        carritoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(carritoArea);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(verCarritoButton);
        buttonPanel.add(calcularTotalButton);
        buttonPanel.add(eliminarProductoButton);
        buttonPanel.add(vaciarCarritoButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Acción del botón "Agregar al Carrito"
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAlCarrito();
            }
        });

        // Acción del botón "Ver Carrito"
        verCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCarrito();
            }
        });

        // Acción del botón "Calcular Total"
        calcularTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });

        // Acción del botón "Eliminar Producto"
        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoDelCarrito();
            }
        });

        // Acción del botón "Vaciar Carrito"
        vaciarCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCarrito();
            }
        });
    }

    private void agregarProductoAlCarrito() {
        try {
            int idProducto = Integer.parseInt(idProductoField.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());

            Socket socket = new Socket("localhost", 12345); 
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("AGREGAR_AL_CARRITO");
            out.writeInt(idProducto);
            out.writeInt(cantidad);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String respuesta = (String) in.readObject();

            JOptionPane.showMessageDialog(this, respuesta);

            in.close();
            out.close();
            socket.close();

            idProductoField.setText("");
            cantidadField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto al carrito: " + ex.getMessage());
        }
    }

    private void verCarrito() {
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("VER_CARRITO");
            out.flush();

            List<Producto> carrito = (List<Producto>) in.readObject();
            carritoArea.setText("");
            for (Producto producto : carrito) {
                carritoArea.append("ID: " + producto.getId() + ", Nombre: " + producto.getNombre() +
                        ", Categoría: " + producto.getCategoria() + ", Precio: " + producto.getPrecio() +
                        ", Cantidad: " + producto.getStock() + "\n");
            }

            in.close();
            out.close();
            socket.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al ver el carrito: " + ex.getMessage());
        }
    }

    private void calcularTotal() {
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("CALCULAR_TOTAL");
            out.flush();

            double total = in.readDouble();

            JOptionPane.showMessageDialog(this, "Total a pagar: " + total);

            in.close();
            out.close();
            socket.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al calcular el total: " + ex.getMessage());
        }
    }

    private void eliminarProductoDelCarrito() {
        try {
            int idProducto = Integer.parseInt(idProductoField.getText());

            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("ELIMINAR_DEL_CARRITO");
            out.writeInt(idProducto);
            out.flush();

            String respuesta = (String) in.readObject();

            JOptionPane.showMessageDialog(this, respuesta);

            in.close();
            out.close();
            socket.close();

            idProductoField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto del carrito: " + ex.getMessage());
        }
    }

    private void vaciarCarrito() {
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("VACIAR_CARRITO");
            out.flush();

            String respuesta = (String) in.readObject();

            JOptionPane.showMessageDialog(this, respuesta);

            in.close();
            out.close();
            socket.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al vaciar el carrito: " + ex.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
