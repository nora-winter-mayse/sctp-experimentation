package client;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Client implements Runnable {

    private final String clientID;
    private final SecretValidator secretValidator;

    public Client(String clientID, SecretValidator secretValidator) {
        this.clientID = clientID;
        this.secretValidator = secretValidator;
    }

    @Override
    public void run() {

        ByteBuffer secretBuffer = ByteBuffer.allocate(5);
        SecretNotificationHandler secretNotificationHandler = new SecretNotificationHandler();
        while(true) {
            SctpChannel channel;
            try {
                channel = SctpChannel.open(new InetSocketAddress("localhost", 1000), 0, 1);
            } catch (IOException e) {
                System.out.println("Failed to init client: " + e);
                return;
            }

            MessageInfo messageInfo;
            try {
                messageInfo = channel.receive(secretBuffer, new SecretChangeNotificationContext(clientID), secretNotificationHandler);
                channel.close();
            } catch (IOException e) {
                System.out.println("Failed while receiving message: " + e);
                return;
            }
            secretBuffer.flip();
            byte[] bytes = new byte[5];
            secretBuffer.get(bytes, 0, 5);
            if (secretValidator.isSecretCorrect(clientID, bytes)) {
                System.out.println("The correct secret was presented for client " + clientID);
            } else {
                System.out.println("An incorrect secret was presented for client " + clientID);
            }

            secretBuffer.clear();
            System.out.println("Client is finished");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Client sleep was interrupted...");
            }
        }

    }
}
