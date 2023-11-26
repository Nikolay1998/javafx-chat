package server;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

public class Controller {

    public TextField cc;
    public Button create;
    public TextArea msgViewer;
    public Button refrLog;

    public static String log = "";

    public void createAction(ActionEvent actionEvent) throws IOException {
        int port = 2005;
        ServerSocket server = new ServerSocket(port);
        //System.out.println("Enter numbers of clients: ");
        byte[] count = cc.getText().getBytes();
        Semaphore sem = new Semaphore(3, true);
        for (int i = 0; i < count[0] - 48; i++) {
            Runtime.getRuntime().exec("java -jar " +
                    "target/client-jar-with-dependencies.jar");
            new Thread(new MonoThreadClientHandler(server.accept(), this, sem)).start();
        }
        create.setDisable(true);
        cc.setDisable(true);
    }

    public void refreshMsg(ActionEvent event) {
        msgViewer.setText(log);
    }

}
