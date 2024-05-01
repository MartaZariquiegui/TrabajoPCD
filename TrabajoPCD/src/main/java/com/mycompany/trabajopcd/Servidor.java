package com.mycompany.trabajopcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
    
    private static List<Socket> sockets = Collections.synchronizedList(new ArrayList<>());
    private static List<PrintWriter> writers = Collections.synchronizedList(new ArrayList<>());
    private static List<Scanner> readers = Collections.synchronizedList(new ArrayList<>());
    private static List<Color> colores = Collections.synchronizedList(new ArrayList<>());
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        colores.add(Color.AMARILLO);
        colores.add(Color.AZUL);
        colores.add(Color.ROJO);
        colores.add(Color.VERDE);
        
        try{
            ServerSocket serversocket = new ServerSocket(44444);
            System.out.println("Se ha iniciado el servidor");
            Thread hiloJuego = new Thread(new HiloJuego(serversocket));
            hiloJuego.start();
            hiloJuego.join();
        }catch (IOException e) {
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }catch (InterruptedException ex) {
            System.err.println("InterruptedException. Mensaje: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
        
        for (int i=0; i<jugadores.size(); i++){
            System.out.println(jugadores.get(i).toString());
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

    
    public static List<Socket> getSockets() {
        return sockets;
    }

    public static void setSockets(List<Socket> sockets) {
        Servidor.sockets = sockets;
    }

    public static List<PrintWriter> getWriters() {
        return writers;
    }

    public static void setWriters(List<PrintWriter> writers) {
        Servidor.writers = writers;
    }

    public static List<Scanner> getReaders() {
        return readers;
    }

    public static void setReaders(List<Scanner> readers) {
        Servidor.readers = readers;
    }

    public static List<Color> getColores() {
        return colores;
    }

    public static void setColores(List<Color> colores) {
        Servidor.colores = colores;
    }

    
    

    
    
    
    
}
