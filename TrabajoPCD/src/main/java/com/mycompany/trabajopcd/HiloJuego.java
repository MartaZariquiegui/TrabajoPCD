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
            //ana
//            in = new Scanner(socket.getInputStream());
//            out = new PrintWriter(socket.getOutputStream(), true);
//            for(int i=0; i<4; i++) {
//                out.println("SUBMITNAME");
//                name = in.nextLine();
//                Jugador jugador = new Jugador(name, i);
//                if (name == null) {
//                    return;
//                }
//                synchronized (Servidor.getJugadores()) {
//                    if (!name.isBlank() && !Servidor.getJugadores().contains(jugador)) {
//                        Servidor.getJugadores().add(jugador);
//                        break;
//                    }
//                }
//            }
//            // Ya se ha incorporado un nuevo usuario, lo añadimos al conjunto de
//            // nombre. Pero antes de añadirlo mandamos un mensaje a todos los usuarios
//            // que un nuevo usuario se ha añadido al sistema
//            out.println("NAMEACCEPTED " + name);
//            for (PrintWriter writer : Servidor.getWriters()) {
//                writer.println("MESSAGE " + name + " has joined");
//            }

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

            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < Servidor.getJugadores().size(); i++) {
                    Jugador jugador = Servidor.getJugadores().get(i);
                    for (PrintWriter writer : Servidor.getWriters()) {
                        writer.println("Es el turno de " + jugador.getNombre());
                    }
                    Servidor.getWriters().get(i).println("Pulse enter para tirar los dados: ");
                    String tiradastr = Servidor.getReaders().get(i).nextLine();
                    int tirada = Integer.parseInt(tiradastr);
                    for (PrintWriter writer : Servidor.getWriters()) {
                        writer.println(jugador.getNombre() + " avanza " + tiradastr + " casillas.");
                        writer.println();
                    }
                    String puedeSalirStr = Servidor.getReaders().get(i).nextLine();
                    boolean puedeSalir = Boolean.parseBoolean(puedeSalirStr);

                    if (Servidor.getCasas().get(i).casaVacia() == false) { //si todavia tengo que sacar la ficha de casa

                        if (puedeSalir == true) {
                            Servidor.getFichas().get(i).sacarFichaDeCasa(jugador.getNumero());
                            Servidor.getCasas().get(i).eliminarDeCasa(Servidor.getFichas().get(i));
                            tirada -= 5;
                            for (PrintWriter writer : Servidor.getWriters()) {
                                writer.println(jugador.getNombre() + " ha sacado su ficha de casa.");
                            }
                        } else {
                            for (PrintWriter writer : Servidor.getWriters()) {
                                writer.println(jugador.getNombre() + " no ha conseguido sacar su ficha de casa.");
                            }
                        }
                    }
                    if (Servidor.getCasas().get(i).casaVacia()) {
                        Servidor.getFichas().get(i).moverFicha(jugador, Servidor.getFichas().get(i).getCasilla(), tirada, Servidor.getFichas().get(i).getPosPasillo());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

}
