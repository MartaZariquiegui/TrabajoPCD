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

    public Jugador(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
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
    
    

    @Override
    public String toString() {
        return "Jugador{" + "nombre=" + nombre + ", numero=" + numero + '}';
    }
    
    
    
}
