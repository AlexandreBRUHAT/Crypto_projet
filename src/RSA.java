import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private int bits;

    public RSA(int bits) {
        this.bits = bits;
    }

    public Key keyGenRSA() {

        Random random = new Random();

        BigInteger p = BigInteger.probablePrime(bits, random);

        BigInteger q = BigInteger.probablePrime(bits, random);

        BigInteger n = p.multiply(q);
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;

        do {
            e = BigInteger.probablePrime(16, random);
        } while (phiN.mod(e).compareTo(BigInteger.ZERO) == 0);

        BigInteger d = e.modInverse(phiN);

        return new Key(d, e, n);
    }

    public BigInteger rsaEncrypt(BigInteger x, BigInteger e, BigInteger n) {
        return x.modPow(e, n);
    }

    public BigInteger rsaDecrypt(BigInteger X, BigInteger d, BigInteger n) {
        return X.modPow(d, n);
    }
}
