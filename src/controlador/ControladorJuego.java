/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import model.EstadisticasJuego;
import model.Tablero;
import vistas.InterfazGrafica;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author ingri
 */
public class ControladorJuego {
     // Componentes del patrón MVC
    private InterfazGrafica vista;
    private Tablero tablero;
    private EstadisticasJuego estadisticas;
    
    // Variables de control del juego
    private boolean juegoTerminado;
    private int tamaño;
    private int numeroMinas;
     public ControladorJuego() {
        this.estadisticas = new EstadisticasJuego();
        this.vista = new InterfazGrafica();
        
        mostrarMenuPrincipal();
    }
      private void mostrarMenuPrincipal() {
        int opcion = vista.mostrarMenuPrincipal(estadisticas);
        
        switch (opcion) {
            case 0: // Juego Nuevo
                iniciarJuegoNuevo();
                break;
            case 1: // Salir
            default:
                System.exit(0);
                break;
        }
    }
      private void iniciarJuegoNuevo() {
        int tamañoSolicitado = vista.solicitarTamañoTablero();
        
        // Si el usuario cancela, volver al menú
        if (tamañoSolicitado == -1) {
            mostrarMenuPrincipal();
            return;
        }
        
        // Configurar parámetros del juego
        this.tamaño = tamañoSolicitado;
        this.numeroMinas = 2 * tamaño;
        this.juegoTerminado = false;
        
        // Crear nuevo tablero y configurar vista
        this.tablero = new Tablero(tamaño, numeroMinas);
        configurarInterfazJuego();
    }
       private void configurarInterfazJuego() {
        vista.configurarJuego(tamaño, estadisticas);
        
        // Configurar listeners para cada casilla
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                configurarListenersCasilla(fila, columna);
            }
        }
        
        // Configurar listener del botón menú
        vista.agregarListenerBotonMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarBotonMenu();
            }
        });
    }
        private void configurarListenersCasilla(int fila, int columna) {
        // Listener para clic izquierdo (destapar)
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!juegoTerminado && !tablero.estaMarcada(fila, columna)) {
                    destaparCasilla(fila, columna);
                }
            }
        };
        
        // Listener para clic derecho (marcar/desmarcar)
        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!juegoTerminado && e.getButton() == MouseEvent.BUTTON3) {
                    manejarMarcadoCasilla(fila, columna);
                }
            }
        };
        
        vista.agregarListenerBotonCasilla(fila, columna, actionListener, mouseListener);
    }
        private void manejarBotonMenu() {
        if (!juegoTerminado) {
            estadisticas.incrementarJugados();
            vista.actualizarEstadisticas(estadisticas);
        }
        mostrarMenuPrincipal();
    }
    private void destaparCasilla(int fila, int columna) {
        if (tablero.esMina(fila, columna)) {
            // Juego perdido
            manejarJuegoPerdido();
        } else {
            // Casilla segura
            int minasVecinas = tablero.contarMinasVecinas(fila, columna);
            tablero.destapar(fila, columna);
            
            JButton boton = vista.obtenerBoton(fila, columna);
            actualizarBotonDestapado(boton, minasVecinas);
            
            // Si no hay minas vecinas, expandir automáticamente
            if (minasVecinas == 0) {
                expandirCasillasVacias(fila, columna);
            }
            
            // Verificar condición de victoria
            if (verificarVictoria()) {
                manejarJuegoGanado();
            }
        }
        
    }
     private void actualizarBotonDestapado(JButton boton, int minasVecinas) {
        boton.setEnabled(false);
        boton.setBackground(Color.LIGHT_GRAY);
        
        if (minasVecinas > 0) {
            boton.setText(String.valueOf(minasVecinas));
            boton.setForeground(vista.obtenerColorNumero(minasVecinas));
        }
    }
     private void manejarMarcadoCasilla(int fila, int columna) {
        JButton boton = vista.obtenerBoton(fila, columna);
        
        if (tablero.estaMarcada(fila, columna)) {
            // Desmarcar casilla
            tablero.desmarcar(fila, columna);
            boton.setText("");
            boton.setBackground(null);
        } else if (tablero.puedeMarcar()) {
            // Marcar casilla
            tablero.marcar(fila, columna);
            boton.setText("🚩");
            boton.setBackground(Color.YELLOW);
        } else {
            // Límite de marcas alcanzado
            vista.mostrarMensajeAdvertencia(
                "Límite de Marcas",
                "Ya has marcado todas las minas posibles (" + numeroMinas + ")\n" +
                "Desmarca alguna casilla para marcar otra."
            );
        }
    }
     private void expandirCasillasVacias(int fila, int columna) {
        // Revisar las 8 casillas vecinas
        for (int deltaFila = -1; deltaFila <= 1; deltaFila++) {
            for (int deltaColumna = -1; deltaColumna <= 1; deltaColumna++) {
                int nuevaFila = fila + deltaFila;
                int nuevaColumna = columna + deltaColumna;
                
                // Verificar si la casilla es válida y no está procesada
                if (tablero.esCasillaValida(nuevaFila, nuevaColumna) &&
                    !tablero.estaDestapada(nuevaFila, nuevaColumna) &&
                    !tablero.estaMarcada(nuevaFila, nuevaColumna)) {
                    
                    // Destapar recursivamente
                    destaparCasilla(nuevaFila, nuevaColumna);
                }
            }
        }
    }
    private void manejarJuegoPerdido() {
        juegoTerminado = true;
        estadisticas.incrementarJugados();
        estadisticas.incrementarPerdidos();
        
        // Mostrar todas las minas
        revelarTodasLasMinas();
        
        // Actualizar estadísticas en la vista
        vista.actualizarEstadisticas(estadisticas);
        
        // Mostrar mensaje de derrota
        vista.mostrarMensajeError(
            "¡Juego Perdido!",
            "Has pisado una mina y perdido el juego.\n" +
            "Las minas han sido reveladas en el tablero."
        );
        
        // Preguntar si desea jugar de nuevo
        if (vista.preguntarJugarDeNuevo()) {
            iniciarJuegoNuevo();
        } else {
            mostrarMenuPrincipal();
        }
    }
     private void manejarJuegoGanado() {
        juegoTerminado = true;
        estadisticas.incrementarJugados();
        estadisticas.incrementarGanados();
        
        // Actualizar estadísticas en la vista
        vista.actualizarEstadisticas(estadisticas);
        
        // Mostrar mensaje de victoria
        vista.mostrarMensajeInformacion(
            "¡Felicidades!",
            "Has ganado el juego.\n" +
            "Todas las casillas seguras han sido destapadas.\n\n" +
            estadisticas.obtenerResumen()
        );
        
        // Preguntar si desea jugar de nuevo
        if (vista.preguntarJugarDeNuevo()) {
            iniciarJuegoNuevo();
        } else {
            mostrarMenuPrincipal();
        }
    }
      private void revelarTodasLasMinas() {
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                if (tablero.esMina(fila, columna)) {
                    JButton boton = vista.obtenerBoton(fila, columna);
                    boton.setText("💣");
                    boton.setBackground(Color.RED);
                    boton.setEnabled(false);
                }
            }
        }
    }
      private boolean verificarVictoria() {
        int casillasDestapadas = 0;
        int totalCasillasSeguras = (tamaño * tamaño) - numeroMinas;
        
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                if (tablero.estaDestapada(fila, columna) && !tablero.esMina(fila, columna)) {
                    casillasDestapadas++;
                }
            }
        }
        
        return casillasDestapadas == totalCasillasSeguras;
    }
}

