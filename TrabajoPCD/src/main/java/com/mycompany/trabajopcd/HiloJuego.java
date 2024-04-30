/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.BufferedReader;
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

    private ServerSocket socket;

    public HiloJuego(ServerSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 4; i++) {
                Socket SJugador = socket.accept();
                Servidor.getSockets().add(SJugador);
                Scanner in = new Scanner(new InputStreamReader(SJugador.getInputStream()));
                Servidor.getReaders().add(in);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(SJugador.getOutputStream()), true);
                Servidor.getWriters().add(out);

                String nombre = in.nextLine();
                int num = i + 1;
                Jugador jugador = new Jugador(nombre, num);
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
                    Servidor.getWriters().get(i).print("Pulse enter para tirar los dados: ");
                    int tirada = Servidor.getReaders().get(i).nextInt();
                    for (PrintWriter writer : Servidor.getWriters()) {
                        writer.println(jugador.getNombre() + " avanza " + tirada + " casillas.");
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
