/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.util.ArrayList;

/**
 *
 * @author martazariquiegui
 */
public class Casa {

    //private ArrayList<Ficha> fichasEnCasa; Este seria un ArrayList para cuando se juego con más de una ficha por jugador
    private int numFichas;
    private Color color;
    private Tablero tablero;

    public Casa(int numFichas, Color color, Tablero tablero) {
        this.numFichas = numFichas;
        this.color = color;
        this.tablero = tablero;
        //fichasEnCasa = new ArrayList<>();
        /*for (int i = 1; i <= this.numFichas; i++) {
            Ficha ficha = new Ficha(color, tablero,this);
            fichasEnCasa.add(ficha);
        }*/
    }

    public int getNumFichas() {
        return numFichas;
    }

    public Color getColor() {
        return color;
    }

    public boolean casaVacia() {
        return (numFichas == 0);
    }
    
    public void eliminarDeCasa(Ficha ficha) {
        if (!casaVacia()) {
            //fichasEnCasa.remove(ficha);
            numFichas--;
        }
    }

    public void meterFichaEnACasa(Ficha ficha) {
        //fichasEnCasa.add(ficha);
        ficha.setCasilla(0);
        ficha.setPosAcumulada(0);
        ficha.setEstaPasillo(false);
        numFichas++;

    }

}
