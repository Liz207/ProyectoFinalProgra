/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.EstadisticasJuego;

/**
 *
 * @author ingri
 */
public class InterfazGrafica extends JFrame{
    // Componentes de la interfaz
    private JPanel panelJuego;
    private JLabel labelEstadisticas;
    private JButton botonMenu;
    private JButton[][] botonesTablero;
    private int tamaño;
    //Constructor grafico
    public InterfazGrafica() {
        inicializarVentana();
    }
     private void inicializarVentana() {
        setTitle("Buscaminas - Universidad Técnica Nacional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
     //Menu principal
     public int mostrarMenuPrincipal(EstadisticasJuego estadisticas) {
        String[] opciones = {"Juego Nuevo", "Salir del Juego"};
        
        return JOptionPane.showOptionDialog(
            this,
            "¡Bienvenido al Buscaminas!\n\n" + estadisticas.obtenerResumen(),
            "Menú Principal - Buscaminas UTN",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
    }
     
     public int solicitarTamañoTablero() {
        int tamaño = 0;
        String entrada;
        
        do {
            entrada = JOptionPane.showInputDialog(
                this,
                "Ingrese el tamaño del lado del tablero:\n" +
                "(Debe ser mayor a 2)\n\n" +
                "Ejemplo: 5 = tablero 5x5 con 10 minas",
                "Configuración del Juego",
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Si el usuario cancela
            if (entrada == null) {
                return -1;
            }
            
            try {
                tamaño = Integer.parseInt(entrada.trim());
                if (tamaño <= 2) {
                    mostrarMensajeError(
                        "Tamaño Inválido", 
                        "El tamaño debe ser mayor a 2.\nPor favor intente nuevamente."
                    );
                }
            } catch (NumberFormatException e) {
                mostrarMensajeError(
                    "Entrada Inválida", 
                    "Por favor ingrese un número válido."
                );
                tamaño = 0;
            }
        } while (tamaño <= 2);
        
        return tamaño;
    }
      public void configurarJuego(int tamaño, EstadisticasJuego estadisticas) {
        this.tamaño = tamaño;
        
        // Limpiar contenido anterior
        getContentPane().removeAll();
        
        // Panel superior con estadísticas
        labelEstadisticas = new JLabel(estadisticas.obtenerResumen(), JLabel.CENTER);
        labelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelEstadisticas.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelEstadisticas, BorderLayout.NORTH);
        
        // Panel central del juego
        panelJuego = new JPanel(new GridLayout(tamaño, tamaño, 2, 2));
        panelJuego.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelJuego.setBackground(Color.DARK_GRAY);
        
        // Crear matriz de botones
        botonesTablero = new JButton[tamaño][tamaño];
        
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                JButton boton = crearBotonCasilla();
                botonesTablero[fila][columna] = boton;
                panelJuego.add(boton);
            }
        }
        
        add(panelJuego, BorderLayout.CENTER);
        
        // Panel inferior con botón de menú
        JPanel panelInferior = new JPanel(new FlowLayout());
        botonMenu = new JButton("Menú Principal");
        botonMenu.setFont(new Font("Arial", Font.BOLD, 12));
        panelInferior.add(botonMenu);
        add(panelInferior, BorderLayout.SOUTH);
        
        // Ajustar tamaño y centrar ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
     private JButton crearBotonCasilla() {
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(35, 35));
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setFocusable(false);
        boton.setMargin(new Insets(0, 0, 0, 0));
        
        return boton;
    }
     public JButton obtenerBoton(int fila, int columna) {
        return botonesTablero[fila][columna];
    }
     public void actualizarEstadisticas(EstadisticasJuego estadisticas) {
        if (labelEstadisticas != null) {
            labelEstadisticas.setText(estadisticas.obtenerResumen());
        }
    }
      public void mostrarMensajeError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            JOptionPane.ERROR_MESSAGE
        );
    }
       public void mostrarMensajeInformacion(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
       public void mostrarMensajeAdvertencia(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            JOptionPane.WARNING_MESSAGE
        );
    }
        public boolean preguntarJugarDeNuevo() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Deseas jugar una nueva partida?",
            "Jugar de Nuevo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        return opcion == JOptionPane.YES_OPTION;
    }
         public Color obtenerColorNumero(int numero) {
        switch (numero) {
            case 1: return new Color(0, 0, 255);      // Azul
            case 2: return new Color(0, 128, 0);      // Verde
            case 3: return new Color(255, 0, 0);      // Rojo
            case 4: return new Color(128, 0, 128);    // Púrpura
            case 5: return new Color(255, 165, 0);    // Naranja
            case 6: return new Color(0, 255, 255);    // Cian
            case 7: return new Color(0, 0, 0);        // Negro
            case 8: return new Color(128, 128, 128);  // Gris
            default: return new Color(0, 0, 0);       // Negro por defecto
        }
    }
          public void agregarListenerBotonMenu(ActionListener listener) {
        if (botonMenu != null) {
            botonMenu.addActionListener(listener);
        }
    }
          public void agregarListenerBotonCasilla(int fila, int columna, 
                                          ActionListener actionListener, 
                                          MouseListener mouseListener) {
        JButton boton = botonesTablero[fila][columna];
        boton.addActionListener(actionListener);
        boton.addMouseListener(mouseListener);
    }
}

