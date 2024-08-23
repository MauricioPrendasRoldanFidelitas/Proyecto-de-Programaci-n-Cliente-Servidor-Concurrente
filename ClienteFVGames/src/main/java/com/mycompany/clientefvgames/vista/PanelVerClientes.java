/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.clientefvgames.vista;
import com.mycompany.fvgames.modelo.Cliente;
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
public class PanelVerClientes extends JPanel {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton botonActualizar;

    public PanelVerClientes() {
        setLayout(new BorderLayout(10, 10));

        // Crear la tabla de clientes con el modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Saldo");

        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);

        // Crear el botón para actualizar la lista de clientes
        botonActualizar = new JButton("Actualizar");

        // Agregar la tabla y el botón al panel
        add(scrollPane, BorderLayout.CENTER);
        add(botonActualizar, BorderLayout.SOUTH);

        // Acción del botón "Actualizar"
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarListaClientes();
            }
        });

        // Cargar la lista de clientes al iniciar el panel
        actualizarListaClientes();
    }

    private void actualizarListaClientes() {
        try {
            // Limpiar la tabla antes de actualizar
            modeloTabla.setRowCount(0);

            // Enviar solicitud para obtener la lista de clientes
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("OBTENER_CLIENTES");
            out.flush();

            // Recibir la lista de clientes del servidor
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<Cliente> clientes = (List<Cliente>) in.readObject();

            // Agregar los clientes al modelo de la tabla
            for (Cliente cliente : clientes) {
                modeloTabla.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getCedula(),
                        cliente.getDireccion(),
                        cliente.getEmail(),
                        cliente.getSaldo()
                });
            }

            in.close();
            out.close();
            socket.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener la lista de clientes: " + ex.getMessage());
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
