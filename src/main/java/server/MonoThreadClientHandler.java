package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class MonoThreadClientHandler implements Runnable {

    private static int refreshTimeout = 1000;
    private Controller controller;
    public Semaphore sem;
    private Socket client;
    private boolean isRightPasswordGet = false;
    private boolean isQuit = false;


    public MonoThreadClientHandler(Socket client, Controller controller, Semaphore sem) {
        this.client = client;
        this.controller = controller;
        this.sem = sem;
    }

    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            sem.acquire();
            out.writeBoolean(true);
            String password = "";
            while (!client.isClosed() && !isRightPasswordGet) {
                password = in.readUTF();
                if (password.equalsIgnoreCase("ssau")) {
                    //System.out.println("Client has enter right password");
                    Controller.log += "Client has enter right password\n";
                    out.writeBoolean(true);
                    isRightPasswordGet = true;
                } else {
                    Controller.log += "Access don't allow\n";
                    //System.out.println("Access don't allow");
                    out.writeBoolean(false);
                }
                controller.refreshMsg(null);
            }
            String entry = "";
            while (!client.isClosed() && !isQuit) {
                entry = in.readUTF();
                Controller.log += entry + '\n';
                System.out.println(entry);
                if (entry.equalsIgnoreCase("quit")) {
                    Controller.log += "Client initialize connections suicide ...\n";
                    System.out.println("Client initialize connections suicide ...");
                    isQuit = true;
                    sem.release();
                }
                controller.refreshMsg(null);
            }
            Controller.log += "Client disconnected\n";
            //System.out.println("Client disconnected");
            controller.refreshMsg(null);
            //toDO:check if there are't any clients, close window
        } catch (IOException e) {
            Controller.log += "Client disconnected\n";
            //System.out.println("Client disconnected");
            controller.refreshMsg(null);
            sem.release();
            //toDO:check if there are't any clients, close window
            //e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
