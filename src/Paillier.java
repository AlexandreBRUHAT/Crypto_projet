import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Random;

public class Paillier {

    private int bits;

    public Paillier(int bits) {
        this.bits = bits;
    }

    public Key KeygenPaillier() {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(bits, random);
        BigInteger q = BigInteger.probablePrime(bits, random);

        BigInteger n = p.multiply(q);
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        return new Key(n.modInverse(phiN),n, n);
    }

    public BigInteger paillierEncrypt(BigInteger message, BigInteger pk) {
        Random random = new Random();

        BigInteger r;

        r = new BigInteger(bits, random);

        return (message.multiply(pk).add(BigInteger.ONE)).multiply(r.modPow(pk, pk.pow(2))).mod(pk.pow(2));
    }

    public BigInteger paillierDecrypt(BigInteger message, BigInteger n, BigInteger sk) {
        BigInteger r = message.modPow(sk, n);

        return (message.multiply(r.modPow(n.negate(), n.pow(2))).mod(n.pow(2)).subtract(BigInteger.ONE)).divide(n);
    }

    public Pair<BigInteger, BigInteger> paillierDecryptPlus(BigInteger message, BigInteger n, BigInteger sk) {
        BigInteger r = message.modPow(sk, n);

        return new Pair<BigInteger, BigInteger>((message.multiply(r.modPow(n.negate(), n.pow(2))).mod(n.pow(2)).subtract(BigInteger.ONE)).divide(n), r);
    }
}
