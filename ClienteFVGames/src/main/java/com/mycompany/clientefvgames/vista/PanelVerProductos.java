/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.clientefvgames.vista;

import com.mycompany.fvgames.modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
public class PanelVerProductos extends JPanel {
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton botonActualizar;

    public PanelVerProductos() {
        setLayout(new BorderLayout(10, 10));

        // Crear la tabla de productos con el modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Cantidad");

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Crear el botón para actualizar la lista de productos
        botonActualizar = new JButton("Actualizar");

        // Agregar la tabla y el botón al panel
        add(scrollPane, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // Acción del botón "Actualizar"
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarListaProductos();
            }
        });

        // Cargar la lista de productos al iniciar el panel
        actualizarListaProductos();
    }

    private void actualizarListaProductos() {
        try {
            // Limpiar la tabla antes de actualizar
            modeloTabla.setRowCount(0);

            // Enviar solicitud para obtener la lista de productos
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("OBTENER_PRODUCTOS");
            out.flush();

            // Recibir la lista de productos del servidor
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<Producto> productos = (List<Producto>) in.readObject();

            // Agregar los productos al modelo de la tabla
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getPrecio(),
                        producto.getStock()
                });
            }

            in.close();
            out.close();
            socket.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener la lista de productos: " + ex.getMessage());
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
