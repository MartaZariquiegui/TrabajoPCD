/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author martazariquiegui
 */
public class Tablero {
    //en esta clase guardamos el estado de las casillas, en cuales hay barreras y la posicion actual de las fichas
    
    private int[] casillas = new int[68];
    private HashMap<Ficha, Integer> posiciones = new HashMap<>();
    private int[] seguros = {4,11,16,21,28,33,38,45,50,55,62,67};
    private ArrayList<Integer> barreras = new ArrayList<>();
    
    public Tablero() {
        for(int i=0; i<68; i++){
            casillas[i] = 0;
        }
    }
    
    public void meterClaveValor(Ficha ficha){
        posiciones = new HashMap<>();
        posiciones.put(ficha, ficha.getCasilla());
    }
    
    public boolean esSeguro(int casilla){
        boolean seguro = false;
        for (int i=0; i<seguros.length; i++){
            if (seguros[i]==casilla){
                seguro = true;
            }
        }
        return seguro;
    }
    
    public Ficha getFichaDeCasilla(int casillaOcupada) {
        Ficha fichaAux = null;
        //Ya habremos comprobado antes que es esa casilla hay una unica ficha, porque es para la funcion comerFicha
        for(int i=1; i<=68; i++){
            if (i == casillaOcupada){
                fichaAux = new Ficha(getColorDeUnaFicha(casillaOcupada), this);
                fichaAux.setCasilla(casillaOcupada);
                fichaAux.setComible(true);
                fichaAux.setEnCasa(false);
            }
        }
        return fichaAux;
    }
    
    public boolean comerFichaTablero(int posicionFinal) {
        boolean comer = false;
        if(getEstadoCasilla(posicionFinal) == 1 && !esSeguro(posicionFinal) ){  //lo compruebo dos veces??? pq en ocuparCasilla tambien lo compruebo, pero no lo puedo quitar pq en moverFicha tambien utilizo sin comprobar
            for(Ficha ficha : posiciones.keySet()){
                for(int pos : posiciones.values()){
                    if (pos == posicionFinal){
                        if(!getColorDeUnaFicha(posicionFinal).equals(ficha.getColor())){
                            ficha.mandarFichaACasa();
                            comer = true;
                        }
                    }
                }
            }
        }
        return comer;
    }
    
    

    public void ocuparCasilla(int casilla, Ficha ficha){
        if (casillas[casilla]==0){
            casillas[casilla]=1;
            posiciones.put(ficha, casilla);
        }else if ((casillas[casilla]==1) && (esSeguro(casilla))){
            casillas[casilla]=2;
            barreras.add(casilla);
            posiciones.put(ficha, casilla);
        }else if((casillas[casilla]==1) && (!esSeguro(casilla))){
            //comerFicha
            comerFichaTablero(casilla);
            posiciones.put(ficha, casilla);
        }else{
            System.out.println("No se puede ocupar esta casilla");
        }
    }
    
    public int getEstadoCasilla(int indice) {
        return casillas[indice];
    }
    
    public boolean hayBarrera(int casilla){
        return barreras.contains(casilla);
    }
    
    public void quitarFichaDeCasilla(int casilla){
        if (casillas[casilla]==1){
            casillas[casilla]=0;
        }else if(casillas[casilla]==2){
            casillas[casilla]=1;
            barreras.remove(casilla);
        }
    }
    
    public Color getColorDeUnaFicha(int pos){
        Color color = null;
        for(Ficha ficha : posiciones.keySet()){
            for(int posicion : posiciones.values()){
                if (posicion == pos){
                    color = ficha.getColor();
                }
            }
        }
        return color;
    }
    
}

