/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

/**
 *
 * @author martazariquiegui
 */
public class Jugador {
    
    private String nombre;
    private int numero;
    private Color color;
    private Ficha ficha;
    private Tablero tablero;
    private int[] limites = {68,85,102,119};

    public Jugador(String nombre, int numero, Color color, Ficha ficha, Tablero tablero) {
        this.nombre = nombre;
        this.numero = numero;
        this.color = color;
        this.ficha = ficha;
        this.tablero = tablero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public int getLimite(){
        return limites[numero-1];
    }

    @Override
    public String toString() {
        return "Jugador{" + "nombre=" + nombre + ", numero=" + numero + ", color=" + color + '}';
    }
    
    
    
    
}
