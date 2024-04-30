/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajopcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author martazariquiegui
 */
public class RecepcionMensajes implements Runnable{
    
    private Socket socket;

    public RecepcionMensajes(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader entradaSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje;
            while((mensaje = entradaSocket.readLine()) !=null){
                System.out.println(mensaje);
            }
        }catch(IOException e){
            System.out.println("Exception: " + e);
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
    
}
