/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ingri
 */
public class EstadisticasJuego {
      // Atributos de estadísticas
    private int juegosJugados;
    private int juegosGanados;
    private int juegosPerdidos;
     public EstadisticasJuego() {
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
    }
     //Incrementador de juegos jugados 
     public void incrementarJugados() {
        juegosJugados++;
    }
     //Incrementador de juegos ganados 
      public void incrementarGanados() {
        juegosGanados++;
    }
      //Incrementador de juegos perdidos 
        public void incrementarPerdidos() {
            juegosPerdidos++;
    }
        //Calcular porcentaje por victorias
         public double calcularPorcentajeVictoria() {
        if (juegosJugados == 0) {
            return 0.0;
        }
        return ((double) juegosGanados / juegosJugados) * 100.0;
    }
         //Resumen de estadisticas
     public String obtenerResumen() {
        return String.format(
            "Estadísticas - Jugados: %d | Ganados: %d | Perdidos: %d | Éxito: %.1f%%",
            juegosJugados, juegosGanados, juegosPerdidos, calcularPorcentajeVictoria()
        );
    }
     //Reinicia estadisticas
     public void reiniciarEstadisticas() {
        juegosJugados = 0;
        juegosGanados = 0;
        juegosPerdidos = 0;
    }
     // Métodos getter
    public int getJuegosJugados() { 
        return juegosJugados; 
    }
    
    public int getJuegosGanados() { 
        return juegosGanados; 
    }
    
    public int getJuegosPerdidos() { 
        return juegosPerdidos; 
    }
}

