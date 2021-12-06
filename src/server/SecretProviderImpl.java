package server;

import java.util.Random;

public class SecretProviderImpl implements SecretProvider {

    private static final int SECRET_SIZE = 5;
    private final Random random;

    public SecretProviderImpl() {
        random = new Random();
    }

    @Override
    public byte[] generateSecret() {
        byte[] secret = new byte[SECRET_SIZE];
        random.nextBytes(secret);
        return secret;
    }

    @Override
    public int getSecretSize() {
        return SECRET_SIZE;
    }
}
