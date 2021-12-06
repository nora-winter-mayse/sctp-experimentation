package common;

import java.util.Arrays;

class ClientSecret {

    private final byte[] currentSecret;
    private final byte[] nextSecret;

    public ClientSecret(byte[] currentSecret, byte[] nextSecret) {
        this.currentSecret = currentSecret;
        this.nextSecret = nextSecret;
    }

    public byte[] getCurrentSecret() {
        return currentSecret;
    }

    public byte[] getNextSecret() {
        return nextSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSecret that = (ClientSecret) o;
        return Arrays.equals(currentSecret, that.currentSecret) && Arrays.equals(nextSecret, that.nextSecret);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(currentSecret);
        result = 31 * result + Arrays.hashCode(nextSecret);
        return result;
    }

    @Override
    public String toString() {
        return "ClientSecret{" +
                "currentSecret=" + Arrays.toString(currentSecret) +
                ", nextSecret=" + Arrays.toString(nextSecret) +
                '}';
    }
}
