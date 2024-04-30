package com.mycompany.trabajopcd;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author martazariquiegui
 */
public class Servidor {
    
    private static List<Jugador> jugadores = Collections.synchronizedList(new ArrayList<>());
    private static List<Ficha> fichas = Collections.synchronizedList(new ArrayList<>());
    private static List<Casa> casas = Collections.synchronizedList(new ArrayList<>());
    private static Set<PrintWriter> writers = new CopyOnWriteArraySet<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Tablero tablero = new Tablero();
        Dado dados = new Dado();

        List<Color> colores = new ArrayList<>();
        for (Color color : Color.values()) {
            colores.add(color);
        }
        List<Socket> sockets = new ArrayList<>(); //lista para almacenar los sockets de los jugadores
        
        try{
            ServerSocket serversocket = new ServerSocket(44444);
            Thread hiloJuego = new Thread(new HiloJuego());
            hiloJuego.start();
        }catch (IOException e) {
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    public static List<Jugador> getJugadores() {
        return jugadores;
    }

    public static void setJugadores(List<Jugador> jugadores) {
        Servidor.jugadores = jugadores;
    }

    public static List<Ficha> getFichas() {
        return fichas;
    }

    public static void setFichas(List<Ficha> fichas) {
        Servidor.fichas = fichas;
    }

    public static List<Casa> getCasas() {
        return casas;
    }

    public static void setCasas(List<Casa> casas) {
        Servidor.casas = casas;
    }

    public static Set<PrintWriter> getWriters() {
        return writers;
    }

    public static void setWriters(Set<PrintWriter> writers) {
        Servidor.writers = writers;
    }
    
    
    
}
