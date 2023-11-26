package client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    boolean access;
    public PasswordField passwordField;
    public Button ok;
    public String log = "";
    public TextField textField;
    public Button sendMessage;
    public TextArea flo;

    public static Socket socket;
    public static DataOutputStream out;

    public static DataInputStream in;

    static {
        try {
            socket = new Socket("localhost", 2005);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPassword(ActionEvent actionEvent) throws IOException {
        int port = 2005;
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String password;
        password = passwordField.getText();
        out.writeUTF(password);
        access = in.readBoolean();
        if (access) {
            passwordField.setDisable(true);
            ok.setDisable(true);
            textField.setVisible(true);
            sendMessage.setVisible(true);
        } else {
            System.out.println("Access denied, try again");
            log += "Access denied, try again\n";
            flo.setText(log);
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String message;
        message = textField.getText();
        log += "Message: '" + message + "' sended\n";
        flo.setText(log);
        out.writeUTF(message);
        if (message.equalsIgnoreCase("quit")) {

        }
    }
}
