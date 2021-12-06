package server;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Server implements Runnable {

    private SecretProvider secretProvider;

    public Server(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
    }

    @Override
    public void run() {
        SctpServerChannel baseChannel;
        try {
            baseChannel = SctpServerChannel.open();
            baseChannel.bind(new InetSocketAddress(1000));
        } catch (IOException e) {
            System.out.println("Unrecoverable IOException has occurred on server during startup: " + e);
            return;
        }

        int secretSize = secretProvider.getSecretSize();
        ByteBuffer secretBuffer = ByteBuffer.allocate(secretSize);
        while (true) {
            SctpChannel instanceChannel;
            try {
                instanceChannel = baseChannel.accept();
            } catch (IOException e) {
                System.out.println("Unrecoverable IOException has occurred on server when preparing to send message: " + e);
                return;
            }

            secretBuffer.put(secretProvider.generateSecret());
            secretBuffer.flip();
            MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);

            try {
                instanceChannel.send(secretBuffer, messageInfo);
                instanceChannel.close();
            } catch (IOException e) {
                System.out.println("Failed to send message: " + e);
                return;
            } finally {
                secretBuffer.clear();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sleep was interrupted...");
            }
        }
    }
}