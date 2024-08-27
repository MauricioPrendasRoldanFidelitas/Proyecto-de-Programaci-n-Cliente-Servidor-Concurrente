/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.clientefvgames;
import com.mycompany.clientefvgames.vista.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Melvin Prendas
 */
public class ClienteFVGames {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
}
    
}