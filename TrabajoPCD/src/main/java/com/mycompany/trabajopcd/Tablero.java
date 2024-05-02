/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author martazariquiegui
 */
public class Tablero {
    //En esta clase guardamos el estado de las casillas, en cuales hay barreras y la posicion actual de las fichas

    private int[] casillas = new int[69];
    private HashMap<Ficha, Integer> posiciones = new HashMap<>();
    private int[] seguros = {5, 12, 17, 22, 29, 34, 39, 46, 51, 56, 63, 68};
    private ArrayList<Integer> barreras = new ArrayList<>();

    public Tablero() {
        for (int i = 0; i < 69; i++) {
            casillas[i] = 0;
        }
    }

    public boolean esSeguro(int casilla) {
        boolean seguro = false;
        for (int i = 0; i < seguros.length; i++) {
            if (seguros[i] == casilla) {
                seguro = true;
            }
        }
        return seguro;
    }

    public void comerFichaTablero(int posicionFinal) {
        for (Ficha ficha : posiciones.keySet()) {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("La ficha está en " + ficha.getCasilla());
            }
            int pos = ficha.getCasilla();
            if (pos == posicionFinal) {
                ficha.mandarFichaACasa(ficha);
//                posiciones.remove(ficha, posicionFinal);
                posiciones.put(ficha, 0);
                for (PrintWriter writer : Servidor.getWriters()) {
                    writer.println("La ficha " + ficha.getColor() + " ha sido comida");
                }
            }
        }

    }

    public void ocuparCasilla(int casilla, Ficha ficha) {
        if (casillas[casilla] == 0) {
            casillas[casilla] = 1;
            posiciones.put(ficha, casilla);
        } else if ((casillas[casilla] == 1) && (esSeguro(casilla))) {
            casillas[casilla] = 2;
            barreras.add(casilla);
            posiciones.put(ficha, casilla);
        } else if ((casillas[casilla] == 1) && (!esSeguro(casilla))) {
            //comerFicha
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("La ficha " + ficha.getColor() + " ha comido otra ficha");
            }
            comerFichaTablero(casilla);
            posiciones.put(ficha, casilla);
        }
    }

    public int getEstadoCasilla(int indice) {
        return casillas[indice];
    }

    public boolean hayBarrera(int casilla) {
        return barreras.contains(casilla);
    }

    public void quitarFichaDeCasilla(int casilla) {
        if (casillas[casilla] == 1) {
            casillas[casilla] = 0;
        } else if (casillas[casilla] == 2) {
            casillas[casilla] = 1;
            barreras.remove(casilla);
        }
    }
}
