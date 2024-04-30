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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("127.0.0.1", 44444);
            Scanner sc = new Scanner(System.in);
            
            Scanner shilo = new Scanner(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            System.out.print("Introduce tu nombre: ");
            String nombre = sc.nextLine();
            pw.println(nombre);
            
            //crear un hilo para manejar los mensajes que llegan del grupo
            Thread hiloRecepcion = new Thread(new RecepcionMensajes(socket));
            hiloRecepcion.start();
            
            while(true){
                String recibido = shilo.nextLine();
                System.out.println(recibido);
                String salto = sc.nextLine();
                int tirada = dado.tirada();
                pw.write(tirada);
            }
            
        }catch(IOException e){
            System.err.println("IOException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
    }
    
}
