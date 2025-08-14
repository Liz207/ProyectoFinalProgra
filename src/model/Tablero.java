/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Random;
/**
 *
 * @author ingri
 */
public class Tablero {
      // Atributos de la clase
    private int tamaño;
    private int numeroMinas;
    private boolean[][] minas;
    private boolean[][] destapadas;
    private boolean[][] marcadas;
    private int marcasUsadas;  
    public Tablero(int tamaño, int numeroMinas) {
        this.tamaño = tamaño;
        this.numeroMinas = numeroMinas;
        this.minas = new boolean[tamaño][tamaño];
        this.destapadas = new boolean[tamaño][tamaño];
        this.marcadas = new boolean[tamaño][tamaño];
        this.marcasUsadas = 0;
        
        generarMinas();
    }
     private void generarMinas() {
        Random generador = new Random();
        int minasColocadas = 0;
        
        while (minasColocadas < numeroMinas) {
            int fila = generador.nextInt(tamaño);
            int columna = generador.nextInt(tamaño);
            
            // Solo colocar mina si la casilla está vacía
            if (!minas[fila][columna]) {
                minas[fila][columna] = true;
                minasColocadas++;
            }
        }
    }
      public boolean esMina(int fila, int columna) {
        return esCasillaValida(fila, columna) && minas[fila][columna];
    }
        public boolean estaDestapada(int fila, int columna) {
        return esCasillaValida(fila, columna) && destapadas[fila][columna];
    }
     public boolean estaMarcada(int fila, int columna) {
        return esCasillaValida(fila, columna) && marcadas[fila][columna];
    }
      public void destapar(int fila, int columna) {
        if (esCasillaValida(fila, columna)) {
            destapadas[fila][columna] = true;
        }
    }
        public void marcar(int fila, int columna) {
        if (esCasillaValida(fila, columna) && !marcadas[fila][columna]) {
            marcadas[fila][columna] = true;
            marcasUsadas++;
        }
    }
         public void desmarcar(int fila, int columna) {
        if (esCasillaValida(fila, columna) && marcadas[fila][columna]) {
            marcadas[fila][columna] = false;
            marcasUsadas--;
        }
    }
          public boolean puedeMarcar() {
        return marcasUsadas < numeroMinas;
    }
     public int contarMinasVecinas(int fila, int columna) {
        int contador = 0;
        
        // Revisar las 8 casillas vecinas
        for (int deltaFila = -1; deltaFila <= 1; deltaFila++) {
            for (int deltaColumna = -1; deltaColumna <= 1; deltaColumna++) {
                // Saltar la casilla central
                if (deltaFila == 0 && deltaColumna == 0) {
                    continue;
                }
                
                int nuevaFila = fila + deltaFila;
                int nuevaColumna = columna + deltaColumna;
                
                if (esMina(nuevaFila, nuevaColumna)) {
                    contador++;
                }
            }
        }
        
        return contador;
    }
     public boolean esCasillaValida(int fila, int columna) {
        return fila >= 0 && fila < tamaño && 
               columna >= 0 && columna < tamaño;
    }
    
    // Métodos getter
    public int getTamaño() { 
        return tamaño; 
    }
    
    public int getNumeroMinas() { 
        return numeroMinas; 
    }
    
    public int getMarcasUsadas() { 
        return marcasUsadas; 
    }
}

