/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author martazariquiegui
 */
public class HiloJuego implements Runnable {

    //private ServerSocket socket;
    private String name;
    private ServerSocket socket;
    private Scanner in;
    private PrintWriter out;

    public HiloJuego(ServerSocket socket) {
        this.socket = socket;
    }

    //ana
//    public HiloJuego(Socket socket) {
//        this.socket = socket;
//    }
    @Override
    public void run() {

        Tablero tablero = new Tablero();

        try {

            for (int i = 0; i < 4; i++) {
                Socket SJugador = socket.accept();
                Servidor.getSockets().add(SJugador);
                Scanner in = new Scanner(new InputStreamReader(SJugador.getInputStream()));
                Servidor.getReaders().add(in);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(SJugador.getOutputStream()), true);
                Servidor.getWriters().add(out);

                out.println("Introduce tu nombre: ");
                String nombre = in.nextLine();
                int num = i + 1;
                Ficha ficha = new Ficha(Servidor.getColores().get(i), tablero);
                Servidor.getFichas().add(ficha);
                Servidor.getCasas().add(new Casa(1, Servidor.getColores().get(i), tablero));
                Jugador jugador = new Jugador(nombre, num, Servidor.getColores().get(i), ficha, tablero);
                Servidor.getJugadores().add(jugador);
            }
            for (PrintWriter writer : Servidor.getWriters()) {
                writer.println("Se han conectado los 4 jugadores. Comienza la partida");
            }

            while(true) {
                for (int i = 0; i < Servidor.getJugadores().size(); i++) {
                    Jugador jugador = Servidor.getJugadores().get(i);
                    for (PrintWriter writer : Servidor.getWriters()) {
                        writer.println("Es el turno de " + jugador.getNombre());
                    }
                    Servidor.getWriters().get(i).println(Servidor.getSolicitarDados());
                    String tiradastr = Servidor.getReaders().get(i).nextLine();
                    int tirada = Integer.parseInt(tiradastr);
                    for (PrintWriter writer : Servidor.getWriters()) {
                        writer.println(jugador.getNombre() + " avanza " + tirada + " casillas.");
                        writer.println();
                    }
                    String puedeSalirStr = Servidor.getReaders().get(i).nextLine();
                    boolean puedeSalir = Boolean.parseBoolean(puedeSalirStr);

                    Servidor.getFichas().get(i).rondaJugador(jugador, puedeSalir, i, tirada);
                }
            }

        } catch (IOException e) {
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

}
