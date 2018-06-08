package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

public class Controller {

    public TextField cc;
    public Button create;
    public TextArea msgViewer;
    public Button refrLog;

    public static String log = "";


    public void createAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        int port = 2005;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Enter numbers of clients: ");
        byte[] count = cc.getText().getBytes();
        for(int i = 0; i < count[0]-48; i++){
            System.out.println("Launch client...");
            Runtime.getRuntime().exec("java -jar " +
                    "C:\\Users\\User\\IdeaProjects\\ClientFX\\out\\artifacts\\ClientFX\\ClientFX.jar");
            new Thread(new MonoThreadClientHandler(server.accept())).start();
        }
        create.setDisable(true);
        cc.setDisable(true);
    }

    public void refrashLog(ActionEvent event) {
        msgViewer.setText(log);
    }
}
