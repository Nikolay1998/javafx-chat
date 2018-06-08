package sample;

import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class MonoThreadClientHandler implements Runnable {

    private static Socket client;

    public MonoThreadClientHandler(Socket client){
        this.client = client;
    }

    public void run() {
        try{
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            String password = "";
            String oldPassword = "";
            while(!client.isClosed()){
                while(password == oldPassword) {
                    password = in.readUTF();
                }
                if(password.equalsIgnoreCase("ssau")){
                    System.out.println("Client has enter right password");
                    Controller.log += "Client has enter right password\n";
                    out.writeBoolean(true);
                    break;
                }
                else{
                    Controller.log += "Access don't allow\n";
                    System.out.println("Access don't allow");
                    out.writeBoolean(false);
                }
                oldPassword = password;
            }
            String entry = "";
            String oldEntry = "";
            while(!client.isClosed()){
                while(entry == oldEntry) {
                    entry = in.readUTF();
                }
                Controller.log += entry + '\n';
                System.out.println(entry);
                if(entry.equalsIgnoreCase("quit")){
                    Controller.log += "Client initialize connections suicide ...\n";
                    System.out.println("Client initialize connections suicide ...");
                    break;
                }
                oldEntry = entry;
            }
            Controller.log += "Client disconnected\n";
            System.out.println("Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
