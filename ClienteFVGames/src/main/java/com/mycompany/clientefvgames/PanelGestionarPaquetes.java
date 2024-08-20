/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.clientefvgames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Melvin Prendas
 */
public class PanelGestionarPaquetes extends javax.swing.JPanel {
 private JComboBox<String> comboProductos;
    private DefaultListModel<Producto> modeloListaPaquete;
    private JList<Producto> listaPaquete;
    private JTextField txtDescuento;
    private JButton btnAgregarProducto;
    private JButton btnCrearPaquete;
    private JButton btnEliminarProducto;

    public PanelGestionarPaquetes() {
        setLayout(new BorderLayout());

        // Panel superior para la selección y gestión de productos
        JPanel panelSuperior = new JPanel(new FlowLayout());

        comboProductos = new JComboBox<>();
        panelSuperior.add(new JLabel("Seleccionar Producto:"));
        panelSuperior.add(comboProductos);

        btnAgregarProducto = new JButton("Agregar Producto");
        panelSuperior.add(btnAgregarProducto);

        btnEliminarProducto = new JButton("Eliminar Producto");
        panelSuperior.add(btnEliminarProducto);

        add(panelSuperior, BorderLayout.NORTH);

        // Lista para mostrar los productos añadidos al paquete
        modeloListaPaquete = new DefaultListModel<>();
        listaPaquete = new JList<>(modeloListaPaquete);
        add(new JScrollPane(listaPaquete), BorderLayout.CENTER);

        // Panel inferior para el descuento y la creación del paquete
        JPanel panelInferior = new JPanel(new FlowLayout());

        txtDescuento = new JTextField(5);
        panelInferior.add(new JLabel("Descuento (%):"));
        panelInferior.add(txtDescuento);

        btnCrearPaquete = new JButton("Crear Paquete");
        panelInferior.add(btnCrearPaquete);

        add(panelInferior, BorderLayout.SOUTH);

        // Escuchadores para los botones
        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnCrearPaquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPaquete();
            }
        });
    }

    private void agregarProducto() {
        Producto productoSeleccionado = (Producto) comboProductos.getSelectedItem();
        if (productoSeleccionado != null && modeloListaPaquete.getSize() < 5) {
            modeloListaPaquete.addElement(productoSeleccionado);
        }
    }

    private void eliminarProducto() {
        int selectedIndex = listaPaquete.getSelectedIndex();
        if (selectedIndex != -1) {
            modeloListaPaquete.remove(selectedIndex);
        }
    }

    private void crearPaquete() {
        // Aquí se construye el paquete y se envía al servidor
        String descuentoStr = txtDescuento.getText();
        double descuento = 0.15; // Descuento por defecto
        try {
            descuento = Double.parseDouble(descuentoStr) / 100;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Descuento no válido. Usando descuento por defecto.");
        }

        List<Producto> productosPaquete = new ArrayList<>();
        for (int i = 0; i < modeloListaPaquete.getSize(); i++) {
            productosPaquete.add(modeloListaPaquete.getElementAt(i));
        }

        // Verificar que se hayan agregado al menos 2 productos
        if (productosPaquete.size() < 2) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos 2 productos al paquete.");
            return;
        }

        // Crear un objeto Paquete (suponiendo que existe una clase Paquete)
        Paquete paquete = new Paquete(productosPaquete, descuento);

        try (Socket socket = new Socket("localhost", 12345); // Ajusta la dirección IP y el puerto según sea necesario
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Enviar el objeto Paquete al servidor
            out.writeObject(paquete);
            out.flush();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Paquete creado exitosamente.");

            // Limpiar el formulario después de crear el paquete
            modeloListaPaquete.clear();
            txtDescuento.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarProductos(String[] productos) {
        for (String producto : productos) {
            comboProductos.addItem(producto);
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
