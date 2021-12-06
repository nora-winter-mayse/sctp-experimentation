package client;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Client implements Runnable {

    public Client() {

    }

    @Override
    public void run() {

        SctpChannel sctpChannel;
        try {
            sctpChannel = SctpChannel.open(new InetSocketAddress("localhost", 1000), 0, 0);
        } catch (IOException e) {
            System.out.println("Failed to init client: " + e);
            return;
        }

        ByteBuffer secretBuffer = ByteBuffer.allocate(5);
        SecretNotificationHandler secretNotificationHandler = new SecretNotificationHandler();
        MessageInfo messageInfo;
        try {
            messageInfo = sctpChannel.receive(secretBuffer, new SecretChangeNotificationContext(), secretNotificationHandler);
        } catch (IOException e) {
            System.out.println("Failed while receiving message: " + e);
            return;
        }
        while(messageInfo != null) {
            secretBuffer.flip();
            byte[] bytes = new byte[128];
            secretBuffer.get(bytes, 0, 5);
            printBytes(bytes);

            try {
                messageInfo = sctpChannel.receive(secretBuffer, new SecretChangeNotificationContext(), secretNotificationHandler);
            } catch (IOException e) {
                System.out.println("Failed while receiving message: " + e);
                return;
            }
        }

    }

    private void printBytes(byte[] bytes) {
        for (byte element : bytes) {
            System.out.print(Integer.toBinaryString(element));
        }
    }
}
