/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author martazariquiegui
 */
public class Cliente {

    private static Dado dado = new Dado();
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 44444;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            Scanner sc = new Scanner(new InputStreamReader(System.in));

            Scanner schilo = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            String recibido = schilo.nextLine();
            System.out.print(recibido);
            String nombre = sc.nextLine();
            pw.println(nombre);

            
            while ((recibido = schilo.nextLine()) != null) {
                System.out.println(recibido);
                if (recibido.equals(Servidor.getSolicitarDados())) {
                    String salto = sc.nextLine();
                    int tirada = dado.tirada();
                    pw.println(tirada);
                    pw.println(dado.isPuedeSalir());
                }
            }

        } catch (IOException e) {
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }

}
