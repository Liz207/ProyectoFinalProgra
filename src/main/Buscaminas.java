/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import controlador.ControladorJuego;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author ingri
 */
public class Buscaminas {
 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer el Look and Feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Error al establecer Look and Feel: " + e.getMessage());
            }
            
            // Crear e iniciar el controlador del juego
            new ControladorJuego();
        });
    }
}
