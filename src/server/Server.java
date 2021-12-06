package server;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Server implements Runnable {

    private int iterations;
    private final SecretProvider secretProvider;
    private InetSocketAddress originalAddress;

    public Server(SecretProvider secretProvider) {
        this.secretProvider = secretProvider;
        iterations = 0;
        originalAddress = new InetSocketAddress(1000);
    }

    @Override
    public void run() {
        SctpServerChannel baseChannel;
        try {
            baseChannel = SctpServerChannel.open();
            baseChannel.bind(originalAddress);
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

            secretBuffer.put(secretProvider.generateSecret("ClientA"));
            secretBuffer.flip();
            MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);

            try {
                instanceChannel.send(secretBuffer, messageInfo);
                //on the third iteration, terminate prematurely without warning to trigger peer address change
                if (iterations == 2) {
                    return;
                }
                instanceChannel.close();
            } catch (IOException e) {
                System.out.println("Failed to send message: " + e);
                return;
            } finally {
                secretBuffer.clear();
            }
            iterations++;
        }
    }
}
