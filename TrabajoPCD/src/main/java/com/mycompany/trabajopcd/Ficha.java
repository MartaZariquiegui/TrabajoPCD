/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.PrintWriter;

/**
 *
 * @author martazariquiegui
 */
public class Ficha {

    //En esta clase actualizamos el estado de las fichas, si estan en casa o no, su posicion y si pueden ser comidas
    private Tablero tablero;

    private Color color;
    private int casilla = 0;
    private boolean ganar = false;
    private boolean estaPasillo;
    private int posPasillo;
    private int posAcumulada = 0;

    private Casa casa;

    public Ficha(Color color, Tablero tablero) { // cada vez que creamos una ficha, está en casa y no se puede comer
        this.tablero = tablero;
        this.color = color;
        this.estaPasillo = false;
        this.posPasillo = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCasilla() {
        return casilla;
    }

    public void setCasilla(int casilla) {
        this.casilla = casilla;
    }

    public boolean isGanar() {
        return ganar;
    }

    public void setGanar(boolean ganar) {
        this.ganar = ganar;
    }

    public boolean isEstaPasillo() {
        return estaPasillo;
    }

    public void setEstaPasillo(boolean estaPasillo) {
        this.estaPasillo = estaPasillo;
    }
    
    

    public int getPosPasillo() {
        return posPasillo;
    }

    public void setPosPasillo(int posPasillo) {
        this.posPasillo = posPasillo;
    }

    public int getPosAcumulada() {
        return posAcumulada;
    }

    public void setPosAcumulada(int posAcumulada) {
        this.posAcumulada = posAcumulada;
    }
    
    
    public void sacarFichaDeCasa(int numJugador) {
        switch (numJugador) {
            case 1 -> {
                casilla = 5;
                posAcumulada = 5;
                tablero.ocuparCasilla(5, this);
            }
            case 2 -> {
                casilla = 22;
                posAcumulada = 22;
                tablero.ocuparCasilla(22, this);
            }
            case 3 -> {
                casilla = 39;
                posAcumulada = 39;
                tablero.ocuparCasilla(39, this);
            }
            case 4 -> {
                casilla = 56;
                posAcumulada = 56;
                tablero.ocuparCasilla(56, this);
            }
            default -> {
            }
        }
    }

    public int nuevaPos(int posInicial, int posiciones) {
        int posicionFinal = posInicial + posiciones;
        for (int i = 1; i <= posiciones; i++) {
            if (tablero.hayBarrera((posInicial + i) % 69)) {
                posicionFinal = (posInicial + i) - 1;
                return posicionFinal;
            }
        }
        return posicionFinal;
    }

    public void moverFicha(Jugador jugador, int posInicial, int posiciones) {

        for (PrintWriter writer : Servidor.getWriters()) {
            writer.println("Moviendo la ficha... ");
        }

        int posFinal = nuevaPos(posInicial, posiciones)%68;
        posAcumulada = nuevaPos(posAcumulada, posiciones);

        if (estaPasillo == false && posAcumulada < jugador.getLimite()) {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("No está en el pasillo ");
            }
            if (posiciones == 0) {
                mandarFichaACasa(this);//Significa que habra sacado 3 veces seguidas dados dobles.
            }
            //actualizamos el estado del tablero
            tablero.ocuparCasilla(posFinal, this); //ya actualiza la posicion del color
            tablero.quitarFichaDeCasilla(posInicial); //en la función no movemos el color porque se hace en ocuparCasilla
            //actualizamos el estado de la ficha
            casilla = posFinal;
            
        } else if (estaPasillo == false && posAcumulada > jugador.getLimite()) { //si entra en pasillo
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("Entra al pasillo ");
            }
            posPasillo = entra_pasillo(jugador.getNumero(), posFinal);
            estaPasillo = true;
        } else {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("Está en el pasillo ");
            }
            posPasillo += posiciones;
        }

        if (estaPasillo && posPasillo >= 8) {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("¡Enhorabuena jugador " + jugador.getNombre() + " has ganado!");
            }
            Servidor.setPartidaTerminada(true);
        } else if (estaPasillo && posPasillo < 8) {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println(jugador.getNombre() + " te faltan " + (8 - posPasillo) + " casillas para ganar\n");
            }
        } else { 
            mostrarDatos();
        }
    }

    public void mandarFichaACasa(Ficha ficha) {
        casa.meterFichaEnACasa(ficha);
    }

    public boolean vaAComer(int posicion) {
        return tablero.getEstadoCasilla(posicion) == 1;
    }

    public int entra_pasillo(int numJugador, int nueva_posicion) {
        switch (numJugador) {
            case 1 -> {
                if (nueva_posicion > 68) {
                    estaPasillo = true;
                    nueva_posicion = nueva_posicion - 68;
                }
            }
            case 2 -> {
                if (nueva_posicion > 17) {
                    estaPasillo = true;
                    nueva_posicion = nueva_posicion - 17;
                }
            }
            case 3 -> {
                if (nueva_posicion > 34) {
                    estaPasillo = true;
                    nueva_posicion = nueva_posicion - 34;
                }
            }
            case 4 -> {
                if (nueva_posicion > 51) {
                    estaPasillo = true;
                    nueva_posicion = nueva_posicion - 51;
                }
            }
        }
        return nueva_posicion;
    }

    public void mostrarDatos() {
        for (PrintWriter writer : Servidor.getWriters()) {
            writer.println("Color: " + getColor());
        }
        if (casilla == 0) {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("Casilla: 68");
            }
        } else {
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("Casilla: " + casilla + "\n");
            }
        }
    }

    public void rondaJugador(Jugador jugador, boolean puedeSalir, int i, int tirada) {
        if (Servidor.getCasas().get(i).casaVacia() == false) { //si todavia tengo que sacar la ficha de casa

            if (puedeSalir == true) {
                Servidor.getFichas().get(i).sacarFichaDeCasa(jugador.getNumero());
                Servidor.getCasas().get(i).eliminarDeCasa(Servidor.getFichas().get(i));
                tirada -= 5;
                for (PrintWriter writer : Servidor.getWriters()) {
                    writer.println(jugador.getNombre() + " ha sacado su ficha de casa.\n");
                }
            } else {
                for (PrintWriter writer : Servidor.getWriters()) {
                    writer.println(jugador.getNombre() + " no ha conseguido sacar su ficha de casa.\n");
                }
            }
        }
        if ((Servidor.getCasas().get(i).casaVacia()) && (tirada != 0)) {
            Servidor.getFichas().get(i).moverFicha(jugador, Servidor.getFichas().get(i).getCasilla(),tirada);
        }
    }

}
