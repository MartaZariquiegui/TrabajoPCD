/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author martazariquiegui
 */
public class Dado {

    Random random = new Random();
    private final int numeroMax = 6;
    private final int numDados = 2;
    private boolean puedeSalir = false;

    int[] numeros = new int[numDados];

    public int tirada() {
        int posiciones = 0;
        puedeSalir = false;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < numDados; i++) {
                numeros[i] = random.nextInt(numeroMax) + 1;
                posiciones += numeros[i];
            }
            if ((numeros[0]+numeros[1]==5) || (numeros[0]==5) || (numeros[1]==5)){
                puedeSalir = true;
            }
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("En el dado 1 tenemos el numero: " + numeros[0] + ", y en el dado 2 el numero: " + numeros[1]);
            }
            if (numeros[0] != numeros[1]) {
                return posiciones;
            } else {
                for (PrintWriter writer : Servidor.getWriters()) {
                    writer.println("Como los numeros en ambos dados son iguales se lanza otra vez");
                }
            }
        }
        for (PrintWriter writer : Servidor.getWriters()) {
            writer.println("Tras tres tiradas con numeros repetidos la ficha se va a casa");
        }
        return 0;
    }

    public boolean isPuedeSalir() {
        return puedeSalir;
    }

    public void setPuedeSalir(boolean puedeSalir) {
        this.puedeSalir = puedeSalir;
    }
    
    
}

