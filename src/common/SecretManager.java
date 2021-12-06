package common;

import client.SecretValidator;
import server.SecretProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This class represents the externalities of the experiment. The assumption is that the clients aren't able to create their own secrets,
 *      and require the server to do so for them.
 */
public class SecretManager implements SecretProvider, SecretValidator {

    private static final int SECRET_SIZE = 5;
    private final Random random;

    private HashMap<String, ClientSecret> secretState;

    public SecretManager() {
        random = new Random();
        secretState = new HashMap<>();
    }

    @Override
    public byte[] generateSecret(String clientID) {
        byte[] secret = new byte[SECRET_SIZE];
        random.nextBytes(secret);
        updateState(clientID, secret);
        return secret;
    }

    @Override
    public int getSecretSize() {
        return SECRET_SIZE;
    }

    @Override
    public boolean isSecretCorrect(String clientID, byte[] secret) {
        return secretState.containsKey(clientID) &&
                secretIsRecentlyIssued(clientID, secret);
    }

    private boolean secretIsRecentlyIssued(String clientID, byte[] secret) {
        return Arrays.equals(secretState.get(clientID).getCurrentSecret(), secret)
                || Arrays.equals(secretState.get(clientID).getNextSecret(), secret);
    }

    private void updateState(String client, byte[] newSecret) {
        if (!secretState.containsKey(client)){
            secretState.put(client, new ClientSecret(newSecret, newSecret));
            return;
        }
        System.out.println("New secret requested, moving previous secret ");
        secretState.put(client, new ClientSecret(secretState.get(client).getNextSecret(), newSecret));
    }
}
